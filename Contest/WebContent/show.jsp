<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*" import="java.sql.*"
	import="org.apache.commons.lang3.StringUtils"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>글 확인</title>
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
		<div>
			<h3>
				<c:out value="${writingContent.title}"></c:out>
			</h3>
			<ul>
				<li>작성자 : <c:out value="${writingContent.userName}" /></li>
				<li>글 내용 : <c:out value="${writingContent.text}" /></li>
			</ul>
		</div>

		<div class="form-group">
			<a href="contest" class="btn btn-default">목록으로</a> 
			<c:choose>
				<c:when test="${user.userid!=userid}">
				</c:when>
				<c:otherwise>
			<a
				href="contest?op=update&id=${writingContent.id}"
				class="btn btn-default btn-primary">수정</a> <a href="contest?op=delete&id=${writingContent.id }"
				class="btn btn-default btn-danger" data-action="delete"
				data-id="${writingContent.id}">삭제</a>
				</c:otherwise>
			</c:choose>
		</div>
		<script>
	  $(function{
	    $("a[data-action='delete']").click(function() {
	      if (confirm("정말로 삭제하시겠습니까?")) {
	        location = 'contest?op=delete&id=' + $(this).attr('data-id');
	      }
	      return false;
	    });
	  });
	</script>
	</div>
</body>
</html>