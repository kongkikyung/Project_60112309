package kr.ac.mju.dislab.user2.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.ac.mju.dislab.user2.BoardDAO;
import kr.ac.mju.dislab.user2.PageResult;
import kr.ac.mju.dislab.user2.WritingContent;

/**
 * Servlet implementation class User
 */
@WebServlet("/contest")
public class BoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardServlet() {
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
		boolean ret;
		
		int id = getIntFromParameter(request.getParameter("id"), -1);
		
		if (op == null && id > 0) {
			op = "show";
		}
		
		try {
			if (op == null || op.equals("index")) {
				int page = getIntFromParameter(request.getParameter("page"), 1);
				
				PageResult<WritingContent> writings = BoardDAO.getPage(page, 10);
				request.setAttribute("writings", writings);
				request.setAttribute("page", page);
				actionUrl = "index.jsp";
			} else if (op.equals("show")) {
				WritingContent writing = BoardDAO.findById(id);
				request.setAttribute("writingContent", writing);

				actionUrl = "show.jsp";
			} else if (op.equals("update")) {
				WritingContent writing = BoardDAO.findById(id);
				request.setAttribute("writingContent", writing);
				request.setAttribute("method", "PUT");
				
				actionUrl = "writing.jsp";
			} else if (op.equals("delete")) {
				ret = BoardDAO.remove(id);
				request.setAttribute("result", ret);
				
				if (ret) {
					request.setAttribute("msg", "글이 삭제되었습니다.");
					actionUrl = "writingsuccess.jsp";
				} else {
					request.setAttribute("error", "글 삭제에 실패했습니다.");
					actionUrl = "error.jsp";
				}
					
			} else if (op.equals("writing")) {
				request.setAttribute("method", "POST");
				request.setAttribute("writing", new WritingContent());
				actionUrl = "writing.jsp";
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


	private boolean isRegisterMode(HttpServletRequest request) {
		String method = request.getParameter("_method");
		return method == null || method.equals("POST");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean ret = false;
		String actionUrl;
		String msg = null;
		WritingContent writingContent = new WritingContent();

		request.setCharacterEncoding("utf-8");
		String id = request.getParameter("id");
		String title = request.getParameter("title");
		String userName = request.getParameter("userName");
		String text = request.getParameter("text");
		
		List<String> errorMsgs = new ArrayList<String>();
		

		if (title == " " || title.trim().length() == 0) {
			errorMsgs.add("글 제목을 반드시 입력해주세요.");
		}
		
		if (userName == " " || userName.trim().length() == 0) {
			errorMsgs.add("작성자를 반드시 입력해주세요.");
		}
		
		if (text == null || text.trim().length() == 0) {
			errorMsgs.add("글 내용을 반드시 입력해주세요.");
		}
		
		writingContent.setId(getIntFromParameter(id,-1));
		writingContent.setTitle(title);
		writingContent.setUserName(userName);
		writingContent.setText(text);
	
		try {
			if (isRegisterMode(request)) {
				ret = BoardDAO.create(writingContent);
				msg ="글이 등록되었습니다.";
			} else {
				ret = BoardDAO.update(writingContent);
				actionUrl = "writingsuccess.jsp";
				msg = "글이 수정되었습니다.";
			}
			
			if (ret != true || errorMsgs.size()!=0) {
				errorMsgs.add("변경에 실패했습니다.");
				actionUrl = "error.jsp";
			} else {
				request.setAttribute("msg", msg);
				actionUrl = "success.jsp";
				
			}
		} catch (SQLException | NamingException e) {
			errorMsgs.add(e.getMessage());
			actionUrl = "error.jsp";
		}
		
		request.setAttribute("errorMsgs", errorMsgs);
		RequestDispatcher dispatcher = request.getRequestDispatcher(actionUrl);
		dispatcher.forward(request,  response);
	}

}
