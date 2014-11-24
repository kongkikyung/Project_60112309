<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%  request.setCharacterEncoding("UTF-8"); %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>회원가입</title>
	<link href="css/bootstrap.min.css" rel="stylesheet">
	<link href="css/base.css" rel="stylesheet">
	<script src="js/jquery-1.8.2.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
</head>
<body>
	<jsp:include page="share/header.jsp">
		<jsp:param name="current" value="Sign Up" />
	</jsp:include>

	<div class="container">
		<div>
			<form class="form-horizontal" action="join" method="POST">
				<fieldset>
					<legend class="legend">Sign Up</legend>
					<c:if test="${method == 'PUT'}">
						<input type="hidden" name="id" value="${user.id }" />
						<input type="hidden" name="_method" value="PUT" />
					</c:if>
					<div class="form-group ">
						<label class="col-sm-2 control-label" for="userid">ID</label>
						<div class="col-sm-3">
							<input type="text" class="form-control" name="userid"
								value="${user.userid}">
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label" for="name">Name</label>
						<div class="col-sm-3">
							<input type="text" class="form-control" placeholder="홍길동"
								name="name" value="${user.name}">
						</div>
					</div>

					<c:if test="${method == 'POST'}">
						<%-- 신규 가입일 때만 비밀번호 입력창을 나타냄 --%>
						<div class="form-group">
							<label class="col-sm-2 control-label" for="pwd">Password</label>
							<div class="col-sm-3">
								<input type="password" class="form-control" name="pwd">
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-2 control-label" for="pwd_confirm">Password
								Confirmation</label>
							<div class="col-sm-3">
								<input type="password" class="form-control" name="pwd_confirm">
							</div>
						</div>
					</c:if>
					
					<div class="form-group">
						<label class="col-sm-2 control-label">Gender</label>
						<c:forEach var="gender" items="${user.genders}">
							<div class="col-sm-offset-2 radio">
								<label> <input type="radio" value="${gender[0]}"
									name="gender" ${user.checkGender(gender[0])} /> ${gender[1]}
								</label>
							</div>
						</c:forEach>
					</div>
					
					<div class="form-group">
						<label class="col-sm-2 control-label">Major</label>
						<div class="col-sm-3">
							<select name="major" class="form-control">
								<c:forEach var="majorName" items="${user.majorNames}">
									<option ${user.checkMajor(major)}>${majorName}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-sm-2 control-label" for="phone">Phone Number</label>
						<div class="col-sm-3">
							<input type="tel" class="form-control"
								placeholder="-를 제외하고 입력해주세요" name="phone" value="${user.phone}">
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-sm-2 control-label" for="email">E-mail</label>
						<div class="col-sm-3">
							<input type="email" class="form-control"
								placeholder="hong@abc.com" name="email" value="${user.email}">
						</div>
					</div>
					
					<div class="form-group">
						<a href="join" class="col-sm-offset-2 btn btn-default">목록으로</a>
						<c:choose>
							<c:when test="${method=='POST'}">
								<input type="submit" class="btn btn-default btn-primary"
									value="가입">
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