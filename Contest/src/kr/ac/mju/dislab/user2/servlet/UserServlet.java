package kr.ac.mju.dislab.user2.servlet;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.renderable.ParameterBlock;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.imageio.ImageIO;
import javax.media.jai.JAI;
import javax.media.jai.RenderedOp;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.ac.mju.dislab.user2.PageResult;
import kr.ac.mju.dislab.user2.User;
import kr.ac.mju.dislab.user2.UserDAO;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

/**
 * Servlet implementation class User
 */
@WebServlet("/join")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserServlet() {
        super();
    }

	private int getIntFromParameter(String str, int defaultValue) {
		int id;
		
		try {
			id = Integer.parseInt(str);
		} catch (Exception e) {
			id = defaultValue;
		}
		return id;
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String op = request.getParameter("op");
		String actionUrl = "";
		HttpSession session = request.getSession();
		boolean ret;
		
		int id = getIntFromParameter(request.getParameter("id"), -1);
		
		if (op == null && id > 0) {
			op = "userinfo";
		}
		
		try {
			if (op == null || op.equals("userlist")) {
				int page = getIntFromParameter(request.getParameter("page"), 1);
				
				PageResult<User> users = UserDAO.getPage(page, 10);
				request.setAttribute("users", users);
				request.setAttribute("page", page);
				if (session.getAttribute("userid")==null) {
					 actionUrl = "login.jsp";
				} else 
				actionUrl = "userlist.jsp";
			} else if (op.equals("userinfo")) {
				if(session.getAttribute("userid")==null) {
				 actionUrl = "login.jsp";
				} else {
				User user = UserDAO.findById(id);
				request.setAttribute("user", user);
				 actionUrl = "userinfo.jsp";
				}
			}  else if (op.equals("update")) {
				User user = UserDAO.findById(id);
				request.setAttribute("user", user);
				request.setAttribute("method", "PUT");
				
				actionUrl = "signup.jsp";
			} else if (op.equals("delete")) {
				ret = UserDAO.remove(id);
				request.setAttribute("result", ret);
				
				if (ret) {
					request.setAttribute("msg", "사용자 정보가 삭제되었습니다.");
					actionUrl = "usersuccess.jsp";
				} else {
					request.setAttribute("error", "사용자 정보 삭제에 실패했습니다.");
					actionUrl = "error.jsp";
				}
					
			} else if (op.equals("signup")) {
				request.setAttribute("method", "POST");
				request.setAttribute("user", new User());
				actionUrl = "signup.jsp";
			} else if (op.equals("login")) {
				request.setAttribute("method", "POST");
				actionUrl = "login.jsp";
			} else {
				request.setAttribute("error", "알 수 없는 명령입니다");
				actionUrl = "error.jsp";
			}
		} catch (SQLException | NamingException e) {
			request.setAttribute("error", e.getMessage());
			e.printStackTrace();
			actionUrl = "error.jsp";
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(actionUrl);
		dispatcher.forward(request,  response);
	}

	private boolean isRegisterMode(MultipartRequest request) {
		String method = request.getParameter("_method");
		return method == null || method.equals("POST");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean ret = false;
		boolean res = true;
		
		String actionUrl;
		String msg;
		User user = new User();
		
		String imagePath = getServletContext().getRealPath("/img/photo");
		int size = 2 * 1024 * 1024;
		MultipartRequest multi = new MultipartRequest(request, imagePath, size, "utf-8", new DefaultFileRenamePolicy());
		
		request.setCharacterEncoding("utf-8");
		
		String userid = multi.getParameter("userid");
		String name = multi.getParameter("name");
		String pwd = multi.getParameter("pwd");
		String pwd_confirm = request.getParameter("pwd_confirm");
		String gender = multi.getParameter("gender");
		String major = multi.getParameter("major");
		String phone = multi.getParameter("phone");
		String email = multi.getParameter("email");
		
		List<String> errorMsgs = new ArrayList<String>();
		
		if (isRegisterMode(multi)) {
			if (pwd == null || pwd.length() < 6) {
				errorMsgs.add("비밀번호는 6자 이상 입력해주세요.");
			} 
			if (!pwd.equals(pwd_confirm)) {
				errorMsgs.add("비밀번호가 일치하지 않습니다.");
			}
			user.setPwd(pwd);
		} else {
			user.setId(getIntFromParameter(multi.getParameter("id"), -1));
		}
		if (userid == null || userid.trim().length() == 0) {
			errorMsgs.add("ID를 반드시 입력해주세요.");
		}
		
		if (name == null || name.trim().length() == 0) {
			errorMsgs.add("이름을 반드시 입력해주세요.");
		}
		
		if (gender == null || !(gender.equals("M") || gender.equals("F") )) {
			errorMsgs.add("성별에 적합하지 않은 값이 입력되었습니다.");
		}
		user.setUserid(userid);
		user.setName(name);
		user.setPhoto(UploadPhoto(multi, response, imagePath));
		user.setGender(gender);
		user.setMajor(major);
		user.setEmail(email);
		user.setPhone(phone);
		
		try {
			if (isRegisterMode(multi)) {
				ret = UserDAO.create(user);
				actionUrl = "usersuccess.jsp";
				msg = "<b>" + name + "</b>님의 사용자 정보가 등록되었습니다.";
			} else {
				ret = UserDAO.update(user);
				actionUrl = "usersuccess.jsp";
				msg = "<b>" + name + "</b>님의 사용자 정보가 수정되었습니다.";
			}
			if (ret != true) {
				errorMsgs.add("변경에 실패했습니다.");
				actionUrl = "error.jsp";
			} else {
				request.setAttribute("msg", msg);
				actionUrl = "usersuccess.jsp";
			}
			
		} catch (SQLException | NamingException e) {
			errorMsgs.add(e.getMessage());
			actionUrl = "error.jsp";
		}
		request.setAttribute("errorMsgs", errorMsgs);
		RequestDispatcher dispatcher = request.getRequestDispatcher(actionUrl);
		dispatcher.forward(request,  response);
	}
	public static String UploadPhoto(MultipartRequest multi, HttpServletResponse response, String imagePath) {
		String userid 		= null;	
		String profilephoto = null;	
		String 	curTimeStr 	= Long.toString(System.currentTimeMillis()); //use Unix Time
	
		try {
			// 이미지 업로드
			// 실제 저장은 다른 경로에 저장되니 조심..여기서는 imagePath에 저장되니 이 경로를 추적하면 된다.			
			userid 		= multi.getParameter("userid");
			profilephoto = multi.getParameter("photo");
			
			// 업로드 된 이미지 이름 얻어옴!
			Enumeration files 	= multi.getFileNames();
			String 		file 	= (String) files.nextElement();
			
			/* 파일을 업로드 했다면 썸네일을 만든다 */
			if((multi.getOriginalFileName(file)) != null) {
				// 파일을 업로드 했으면 파일명을 얻음
				profilephoto = multi.getOriginalFileName(file);
				// 파일명 변경준비
				String changephoto 	= profilephoto;
				String fileExt 		= "";
				int i = -1;
				if ((i = changephoto.lastIndexOf(".")) != -1) {
					fileExt = changephoto.substring(i); // 확장자만 추출
					changephoto = changephoto.substring(0, i); // 파일명만 추출
				}
				// 사진명을 UNIXTIME_USERID로 설정
				changephoto = curTimeStr + "_" + userid;
				// 파일명 변경
				File oldFile = new File(imagePath + System.getProperty("file.separator") + profilephoto);
				File newFile = new File(imagePath + System.getProperty("file.separator") + changephoto + fileExt);	
			    oldFile.renameTo(newFile);			
				// 썸네일 파일명 준비용
				profilephoto = curTimeStr + "_" + userid + fileExt;
				// 이 클래스에 변환할 이미지를 담는다.(이미지는 ParameterBlock을 통해서만 담을수 있다.)
				ParameterBlock pb = new ParameterBlock();
				pb.add(imagePath + System.getProperty("file.separator") + profilephoto);
				
				RenderedOp rOp = JAI.create("fileload", pb);
		
				// 불러온 이미지를 BuffedImage에 담는다.
				BufferedImage bi = rOp.getAsBufferedImage();
				// thumb라는 이미지 버퍼를 생성, 버퍼의 사이즈는 180*180으로 설정.
				BufferedImage thumb = new BufferedImage(180, 180, BufferedImage.TYPE_INT_RGB);
		
				// 버퍼사이즈 180*180으로  맞춰  썸네일을 그림
				Graphics2D g = thumb.createGraphics();
				g.drawImage(bi, 0, 0, 180, 180, null);
		
				/* 출력할 위치와 파일이름을 설정하고 섬네일 이미지를 생성한다. 저장하는 타입을 jpg로 설정.*/
				File file1 = new File(imagePath + "/sm" + profilephoto);
				ImageIO.write(thumb, "jpg", file1);
			}
		} catch (Exception e) {
			// 저장에 실패하면 경로를 얻지못했기때문에 null을 리턴
			return null;
		}		
		return profilephoto;
	}
}
