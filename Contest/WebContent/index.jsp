<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글 목록</title>
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/base.css" rel="stylesheet">
<script src="js/jquery-1.8.2.min.js"></script>
<script src="js/bootstrap.min.js"></script>
</head>
<body>
	<jsp:include page="share/header.jsp">
		<jsp:param name="current" value="" />
	</jsp:include>

	<div class="container">
 
  <!-- Wrapper for slides -->
  <div class="carousel-inner">
    <div class="item active">
      <img src="img/photo/kong.jpg" style="margin-left: auto; margin-right: auto;">
       		<div style="text-align:center;"><h2>공모전을 위한</h2></div>
    </div>
  </div>
 
</div>
	<jsp:include page="share/footer.jsp" />
</body>
</html>