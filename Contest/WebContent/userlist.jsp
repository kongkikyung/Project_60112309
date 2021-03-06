<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원목록</title>
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/base.css" rel="stylesheet">
<script src="js/jquery-1.8.2.min.js"></script>
<script src="js/bootstrap.min.js"></script>
</head>
<body>
	<jsp:include page="share/header.jsp">
		<jsp:param name="current" value="UserList" />
	</jsp:include>

	<div class="container">
		<div class="row">
			<div class="col-md-12 page-info">
				<div class="pull-left">
					Total <b>${users.numItems }</b> users
				</div>
				<div class="pull-right">
					<b>${users.page }</b> page / total <b>${users.numPages }</b> pages
				</div>
			</div>
		</div>
		
		<div class="main_content">
		<div class="row">
		<c:forEach var="user" items="${users.list}">
			<div class="col-sm-6 col-md-4">
		   		<div class="thumbnail">
		   		<c:choose>
		   			<c:when test="${user.photo == null }">
		   				<img src="img/photo/none.jpg" width="180px" height="230px">
		   			</c:when>
		   			<c:otherwise>
		   				<img src="img/photo/sm${user.photo}">
		   			</c:otherwise>
		   		</c:choose>
		      		<div class="caption">
		        		<h4>	<c:out value="${user.major }"/> : 
								<a href="join?id=${user.id}"><c:out value="${user.userid}" />
								</a>
						</h4>
		     		</div>
		    	</div>
		  	</div>
		  </c:forEach>
		</div>
		<jsp:include page="page.jsp">
			<jsp:param name="currentPage" value="${users.page}" />
			<jsp:param name="url" value="user" />
			<jsp:param name="startPage" value="${users.startPageNo}" />
			<jsp:param name="endPage" value="${users.endPageNo}" />
			<jsp:param name="numPages" value="${users.numPages}" />
		</jsp:include>
		</div>

	</div>
	<jsp:include page="share/footer.jsp" />
</body>
<script>
	$(function() {
		$("a[data-action='delete']").click(function() {
			if (confirm("정말로 삭제하시겠습니까?")) {
				location = 'join?op=delete&id=' + $(this).attr('data-id');
			}
			return false;
		});
	});
</script>
</html>