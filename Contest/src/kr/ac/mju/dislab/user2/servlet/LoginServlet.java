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
import javax.servlet.http.HttpSession;

import kr.ac.mju.dislab.user2.User;
import kr.ac.mju.dislab.user2.UserDAO;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	public LoginServlet() {
		super();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String op = request.getParameter("op");
		String actionUrl = "";
		boolean ret;
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(60 * 60); 
		
		if (op.equals("login") && op != null) {
			request.setAttribute("method", "POST");
			actionUrl = "login.jsp";
		} else if (op.equals("logout") && op != null) {
			session.invalidate();
			actionUrl = "index.jsp";
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(actionUrl);
		dispatcher.forward(request,  response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean ret = false;
		String actionUrl = null;
		String msg;
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(60 * 60); 
		
		List<String> errorMsgs = new ArrayList<String>();
		
		request.setCharacterEncoding("utf-8");
		
		String userid = request.getParameter("userid");
		String pwd = request.getParameter("pwd");
		
		try {
			User userMatch = UserDAO.findByuserid(userid);
			if(userMatch != null && pwd.equals(userMatch.getPwd())) {
				session.setAttribute("id", userMatch.getId());
				session.setAttribute("userid", userMatch.getUserid());
				session.setAttribute("name", userMatch.getName());
				actionUrl = "index.jsp";
			}
			actionUrl = "index.jsp";
		} catch (SQLException | NamingException e) {
			errorMsgs.add(e.getMessage());
			actionUrl = "error.jsp";
		}
		request.setAttribute("errorMsgs", errorMsgs);
		RequestDispatcher dispatcher = request.getRequestDispatcher(actionUrl);
		dispatcher.forward(request,  response);
	}
}
