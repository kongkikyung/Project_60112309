<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String[][] menu = {
		{"./main.jsp", "Home" },
		{"./contest", "게시판" },
		{"./join", "UserList" }
	};

  String currentMenu = request.getParameter("current");
	
%>    
	<!-- Navbar
  ================================================== -->
  <div class="container navbar-default navbar-fixed-top" role="navigation">
      <div class="container-fluid">
      <div class="navbar-header">
        <div class="navbar-brand">공모전</div>
      </div>
        <div class="collapse navbar-collapse">
          <ul class="nav navbar-nav">
          <%
          	for(String[] menuItem : menu) {
          		if (currentMenu != null && currentMenu.equals(menuItem[1])) {
          			out.println("<li class='active'>");
          		} else {
          			out.println("<li class=''>");
          		}
          		
          		out.println("<a href='"+menuItem[0]+"'>"+menuItem[1]+"</a>");
          		out.println("</li>");
          	}
          %>
          </ul>
          <div id="loginBtn">
          	 <c:choose>
  				<c:when test="${userid==null}">
					<a href="join?op=signup" class="btn btn-default btn-primary">Sign Up</a>
				</c:when>
				<c:otherwise>
				</c:otherwise>
			 </c:choose>
			 
         	 <c:choose>
         		 <c:when test="${userid==null}">
					<a href="login?op=login" class="btn btn-primary">Login</a>
				</c:when>
				<c:otherwise>
					<a href="login?op=logout" class="btn btn-primary">Logout</a>
				</c:otherwise>
			</c:choose>
			</div>
        </div>
      </div>
  </div>
  <div class="container container-fluid" style="padding-top:50px">
  		<div class="form-group">
			
		</div>
 	</div>