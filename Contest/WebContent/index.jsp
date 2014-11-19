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
		<div class="row">
			<div class="col-md-12 page-info">
				<div class="pull-left">
					Total <b>${writings.numItems }</b> writing
				</div>
				<div class="pull-right">
					<b>${writings.page }</b> page / total <b>${writings.numPages }</b> pages
				</div>
			</div>
		</div>
		<table class="table table-bordered table-stripped">
			<thead>
				<tr>
					<th>title</th>
					<th>작성자</th>
					<th>content</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="writing" items="${writings.list }">
					<tr>
						<td><a href="contest?id=${writing.id}"><c:out
									value="${writing.title}" /></a></td>
						<td><c:out value="${writing.userName}"/></td>
						<td><c:out value="${writing.text}" /></td>
						<td><a href="contest?op=update&id=${writing.id}"
							class="btn btn-default btn-xs">modify</a> <a href="#"
							class="btn btn-default btn-xs btn-danger" data-action="delete"
							data-id="${writing.id}">delete</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

		<jsp:include page="page.jsp">
			<jsp:param name="currentPage" value="${writings.page}" />
			<jsp:param name="url" value="contest" />
			<jsp:param name="startPage" value="${writings.startPageNo}" />
			<jsp:param name="endPage" value="${writings.endPageNo}" />
			<jsp:param name="numPages" value="${writings.numPages}" />
		</jsp:include>

		<div class="form-group">
			<a href="contest?op=writing" class="btn btn-default btn-primary">글 작성</a>
		</div>
	</div>
	<jsp:include page="share/footer.jsp" />
</body>
<script>
	$(function() {
		$("a[data-action='delete']").click(function() {
			if (confirm("정말로 삭제하시겠습니까?")) {
				location = 'contest?op=delete&id=' + $(this).attr('data-id');
			}
			return false;
		});
	});
</script>
</html>