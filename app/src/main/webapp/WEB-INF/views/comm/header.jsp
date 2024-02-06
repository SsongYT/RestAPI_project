<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<title>Header</title>
</head>
<body>
	<c:set var="contextPath" value="<%=request.getContextPath()%>" />
	<nav class="navbar navbar-expand-sm navbar-dark bg-dark">
		<div class="container-fluid">
			<a class="navbar-brand" href="${contextPath}/">
				<img
				src="https://static.wixstatic.com/media/6dedfe_ba1ac1618d0042b09b51df464bf1d6cf~mv2.png/v1/fill/w_90,h_60,al_c,q_85,usm_0.66_1.00_0.01,enc_auto/%EC%95%8C%EC%97%A0%EC%86%8C%ED%94%84%ED%8A%B8_w_edited_edited.png"
				alt="알엠소프트_w_edited_edited.png"
				style="width: 90px; height: 60px; object-fit: cover" width="90"
				height="60">
			</a>
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#mynavbar">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="mynavbar">

				<ul class="navbar-nav me-auto">
					<c:choose>
						<c:when test="${sessionScope.loginMember != null}">
							<li class="nav-item"><a class="nav-link" href="/subscribe">구독하기</a></li>
							<li class="nav-item"><a class="nav-link" href="/dashboard">대시보드</a></li>
						</c:when>
					</c:choose>
				</ul>
				
				<div class="d-flex">
					<ul class="navbar-nav me-auto">
					<c:choose>
						<c:when test="${sessionScope.loginMember == null}">
							<li class="nav-item">
								<a class="nav-link" href="${contextPath}/signup">회원가입</a>
							</li>
							<li class="nav-item">
								<a class="nav-link" href="${contextPath}/login">로그인</a>
							</li>
						</c:when>
						<c:otherwise>
							<li class="nav-item">
								<a class="nav-link">${sessionScope.loginMember}님 환영합니다.</a>
							</li>
							<li class="nav-item">
								<a class="nav-link" href="${contextPath}/logout">로그아웃</a>
							</li>
						</c:otherwise>
					</c:choose>
					</ul>
				</div>

			</div>
		</div>
	</nav>
</body>
</html>