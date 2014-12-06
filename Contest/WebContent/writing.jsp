<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>글 작성</title>
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
			<form class="form-horizontal" action="contest" method="POST">
			<input type="hidden" name="cmd" value="writing" /> 
				<fieldset>
					<legend class="legend">글 작성</legend>
					<c:if test="${method == 'PUT'}">
						<input type="hidden" name="id" value="${writingContent.id }" />
						<input type="hidden" name="_method" value="PUT" />
					
						<div class="form-group ">
						<label class="col-sm-2 control-label" for="title">글 제목</label>
						<div class="col-sm-3">
							<input type="text" class="form-control" name="title"
								value="${writingContent.title}"/>
						</div>
						</div>
						
						<div class="form-group ">
						<label class="col-sm-2 control-label" for="userName">작성자</label>
						<div class="col-sm-3">
							<input type="text" class="form-control" name="userName" readonly
								value="${writingContent.userName}" />
						</div>
						</div>

					<div class="form-group">
						<label class="col-sm-2 control-label" for="text">글 내용</label>
						<div class="col-sm-3">
							<textarea class="input-sm" name="text" cols="80" rows="5">
							<c:out value="${writingContent.text}"/>
							</textarea>
						</div>
					</div>
					</c:if>
					

					<c:if test="${method == 'POST'}">
						<div class="form-group ">
						<label class="col-sm-2 control-label" for="title">글 제목</label>
						<div class="col-sm-3">
							<input type="text" class="form-control" name="title">
							<c:out value="${writingContent.title }"/>
						</div>
					</div>

					<div class="form-group ">
						<label class="col-sm-2 control-label" for="userName">작성자</label>
						<div class="col-sm-3">
							<input type="text" class="form-control" name="userName" readonly value="${name }">
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label" for="text">글 내용</label>
						<div class="col-sm-3">
							<textarea class="input-sm" name="text" cols="80" rows="5">
							<c:out value="${writingContent.text }"/>
							</textarea>
						</div>
					</div>
					</c:if>

					<div class="form-group">
						<a href="contest" class="col-sm-offset-2 btn btn-default">목록으로</a>
						<c:choose>
							<c:when test="${method=='POST'}">
								<input type="submit" class="btn btn-default btn-primary"
									value="등록">
							</c:when>
							<c:otherwise>
								<input type="submit" class="btn btn-default btn-primary"
									value="수정">
							</c:otherwise>
						</c:choose>
					</div>
				</fieldset>
			</form>
		</div>
	</div>
</body>
</html>