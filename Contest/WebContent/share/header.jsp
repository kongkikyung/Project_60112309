<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String[][] menu = {
		{"./index.jsp", "Home" },
		{"./contest", "게시판" },
		{"./join", "회원관리" }
	};


  String currentMenu = request.getParameter("current");
	
%>    
	<!-- Navbar
  ================================================== -->
  <div class="container navbar navbar-inverse navbar-fixed-top" role="navigation">
      <div class="container-fluid">
      <div class="navbar-header">
        <a class="navbar-brand" href="./index.jsp">KikyungKong</a>
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
        </div>
      </div>
  </div>
  <div class="container container-fluid" style="padding-top:50px">
  		<div class="form-group">
			<a href="join?op=signup" class="btn btn-default btn-primary" style="float:right">Sign Up</a>
		</div>
		<h1>WP_HW4</h1>
 	</div>