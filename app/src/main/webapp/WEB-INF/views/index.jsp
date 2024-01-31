<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Index</title>
<style type="text/css">
	.row {
		min-height: 150px;
	}
	.main {
		border: solid 1px;
	}
</style>
</head>
<body>
	<%@ include file="/WEB-INF/views/comm/header.jsp" %>
	<div class="container">
		<div class="row"></div>
		<div class="main">
			<div class="col-xl-3"></div>
			<div class="col-xl-6">
				<h1>로그인</h1>
		
		<form action="login" method="post">		
			<div class="mb-3 mt-3">
				<label for="userId" class="form-label">UserId:</label>
				<input type="text" class="form-control" id="userId" name="userId" />
			</div>
		
			<div class="mb-3 mt-3">
				<label for="userPwd" class="form-label">Password:</label>
				<input type="password" class="form-control" id="userPwd" name="userPwd" />
			</div>
			
			<div class="form-check mb-3">
   				<label class="form-check-label" for="remember">
      				<input class="form-check-input" type="checkbox" name="remember"> 아이디 저장
    			</label>
  			</div>

			<div class="mb-3 mt-3">
				<button type="reset" class="btn btn-secondary">취소</button>
				<button type="submit" class="btn btn-success">로그인</button>
			</div>
		</form>
			</div>
			<div class="col-xl-3"></div>
		</div>
		<div class="row"></div>
	</div>
	<%@ include file="/WEB-INF/views/comm/footer.jsp" %>
</body>
</html>