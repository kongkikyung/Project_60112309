<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="view
	port" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="">
	<meta name="author" content="">
	<link rel="shortcut icon" href="../../assets/ico/favicon.png">
<title>로그인</title>
	<link href="css/bootstrap.min.css" rel="stylesheet">
	<link href="css/base.css" rel="stylesheet">
	<link href="css/signin.css" rel="stylesheet">
	<script src="js/jquery-1.8.2.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
</head>
<body>
	<jsp:include page="share/header.jsp">
		<jsp:param name="current" value="" />
	</jsp:include>
	    <div class="container">
     	 <form class="form-signin" name="login" method="post" action="login">
       	 <input type="hidden" name="op" value="login">
       	 <h2 class="form-signin-heading">Please sign in</h2>
      	 
      	  <input type="text" class="form-control" name="userid" placeholder="Id" value="${user.userid}" autofocus>
      	  <input type="password" class="form-control" name="pwd" placeholder="Password" value="${user.pwd}">
      	  
        <label class="checkbox">
          <input type="checkbox" value="remember-me"> Remember me
        </label>
        
        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</a>
        <button class="btn btn-lg btn-primary btn-block" type="submit">ID찾기</button>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Password찾기</button>
      </form>


	</div>
	<jsp:include page="share/footer.jsp" />
</body>
</html>