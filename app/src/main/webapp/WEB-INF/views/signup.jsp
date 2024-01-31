<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
<!-- 아이콘 -->
<script src="https://kit.fontawesome.com/5970d3684d.js"></script>
<title>RMSOFT/회원가입</title>

<script type="text/javascript">
	let isIdValid = false;
	let isPasswordValid = false;
	let isNameValid = false;
	let isPhoneNumValid = false;
	let isEmailValid = false;

	$(document).ready(function(){
		
		// 아이디 유효성검사
		$('#userId').blur(function() { 
			validUserId();
		});
		// 비밀번호 유효성검사
		$('#userPassword').blur(function() { 
			validUserPassword();
		});
		// 이름 유효성검사
		$('#userName').blur(function() { 
			validUserName();
		});
		// 전화번호 유효성검사
		$('#userPhoneNum').blur(function() { 
			validUserPhoneNum();
		});
		
		// 비밀번호 보이기/감추기
		 $('.password-control i').on('click',function(){
		 	$('input').toggleClass('active');
		    if($('input').hasClass('active')){
		    	$(this).attr('class',"fa-solid fa-eye fa-lg").next('input').attr('type',"text");
		    } else {
		    	console.log($(this))
		        $(this).attr('class',"fa-solid fa-eye-slash fa-lg").next('input').attr('type','password');
		    }
		 });
	});

	//아이디 유효성 검사
	function validUserId() {
		if ($('#userId').val().length > 2 && $('#userId').val().length < 9) {
			let checkId = $('#userId').val();
			$.ajax({
	    	   	  url: 'signup/id/'+ checkId,
	        	  type: 'get',
	        	  headers : {"content-type":"application/json"},
	        	  dataType: 'json',
	        	  async : false,
	        	  success: function(data) {
	        		  console.log("성공");
	        		  console.log(data);
	        		  if(data.code == "CI000") {
	        			isIdValid = true;
        			  	printMessages('userId', data.solution, 'succesMsg');
	        		  } else if(data.code == "CI001") {
	        			isIdValid = false;
	        			printMessages('userId', data.solution, 'errorMsg');
	        		  }
	        	  },
	        	  error: function(request, status, error) {
	        		  console.log("실패");
	        		  console.log(status);
	        		  console.log(error);
	        		  isIdValid = false;
	        		  alert("DB에 문제가 있습니다. 다시 시도해 주세요.");
	        	  }
			});
			
		} else {
			printMessages('userId', '아이디는 3자 이상 8자 이하로 필수 입니다!', 'errorMsg');
			isIdValid = false;
		}

	}
	
	// 비밀번호 유효성 검사
	function validUserPassword() {
		const regPassword = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,12}$/;
		let checkPassword = $('#userPassword').val();
		if (regPassword.test(checkPassword)) {
			isPasswordValid = true;
			printMessages('userPassword', '비밀번호 입력완료', 'succesMsg');
		} else {
			printMessages('userPassword', '문자,숫자,특수문자가 포함된 8자 이상 12자 이하로 입력하세요.', 'errorMsg');
		}
	}
	
	// 이름 유효성 검사
	function validUserName() {
		const regName =  /^[가-힣]{2,10}$/;
		let checkName = $('#userName').val();
		if (regName.test(checkName)) {
			isNameValid = true;
			printMessages('userName', '이름 확인완료', 'succesMsg');
		} else {
			printMessages('userName', '2자 이상 10자 이하의 한글을 입력해주세요.', 'errorMsg');
		}
	}
	
	// 전화번호 유효성 검사
	function validUserPhoneNum() {
		const regPhoneNum = /^(01[016789]{1})-[0-9]{3,4}-[0-9]{4}$/;
		let checkPhoneNum = $('#userPhoneNum').val();
		let chagePhoneNum = checkPhoneNum.replace(/[^0-9]/g, '').replace(/^(\d{3})(\d{3,4})(\d{4})$/, `$1-$2-$3`);
		if (regPhoneNum.test(chagePhoneNum)) {
			isPhoneNumValid = true;
			$('#userPhoneNum').val(chagePhoneNum);
			printMessages('userPhoneNum', '휴대전화 확인완료', 'succesMsg');
		} else {
			printMessages('userPhoneNum', '정상적인 휴대전화번호를 입력해주세요.', 'errorMsg');	
		}
	}
	
	// 이메일 유효성 검사
	function validUserEmail() {
		const regEmail = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
		let checkEmail = $('#userEmail').val() + "@" + $('#userEmailDomain').val();
		console.log(checkEmail);
		if (regEmail.test(checkEmail)) {
			isEmailValid = true;
			printMessages('email-control', '이메일 확인완료', 'succesMsg');
		} else {
			printMessages('email-control', '정상적인 이메일을 입력해주세요.', 'errorMsg');
		}
	}
	
	// 회원가입
	function register() {
		if(isIdValid && isPasswordValid && isNameValid && isPhoneNumValid && isEmailValid) {
			
			let data = {
				userId: $('#userId').val(),
				userPassword : $('#userPassword').val(),
				userName : $('#userName').val(),
				userPhoneNo : $('#userPhoneNum').val(),
				userEmail : $('#userEmail').val() + "@" + $('#userEmailDomain').val()
			}
			
			console.log(data);
			console.log(JSON.stringify(data));
			
			$.ajax({
	    	   	  url : 'signup',
	        	  type : 'post',
	        	  headers : {"content-type":"application/json"},
	        	  data : JSON.stringify(data),
	        	  dataType : 'json',
	        	  async : false,
	        	  success: function(data) {
	        		if(data.code == "S000") {
		        		alert(data.messages + ": " +  data.solution);
		        		location.replace("/login");
		        	} else if(data.code == "S001") {
		        		alert(data.messages + ": " +  data.solution);
		        	}
	        	  },
	        	  error: function(request, status, error) {
	        		  alert("DB에 문제가 있습니다. 다시 시도해 주세요.");
	        	  }
			});
			
		} else {
			alert("유효성 검사후 다시 시도해주세요.");
		}
	}
	
	// 메시지 출력
	function printMessages(id, msg, type) {
		$('.errorMsg').remove();
		$('.succesMsg').remove();
		let messages = `<div class="\${type}">\${msg}</div>`;
		
		$(messages).insertAfter(`#\${id}`);
	}
	
	
</script>

<style>
.errorMsg {
	color: red;
	font-weight: 10px;
	font-size: 15px;
}
.succesMsg {
	color: blue;
	font-weight: 10px;
	font-size: 15px;
}

.main {
	width: 100%;
	height: 100vh;
	display: -webkit-box;
	display: -moz-box;
	display: -ms-flexbox;
	display: flex;
	-webkit-box-align: center;
	-moz-box-align: center;
	-ms-flex-align: center;
	/* align-items: center;  수직 정렬 */
	-webkit-box-pack: center;
	-moz-box-pack: center;
	-ms-flex-pack: center;
	justify-content: center; /* 수평 정렬 */
}

.email-control {
	display: flex;
}

.password-control {
	position: relative;
}

.password-control i {
	position: absolute;
    right: 10px;
    top: 18px;
}

</style>

</head>
<body>
	<%@ include file="/WEB-INF/views/comm/header.jsp"%>

	<div class="container main">
		<div class="col-xl-6">
			<h1>회원가입</h1>

			<div class="mb-3 mt-3">
				<label for="userId" class="form-label">아이디</label> <input
					type="text" class="form-control" id="userId" placeholder="2 ~ 9자의 아이디를 입력하세요." />
			</div>

			<div class="mb-3 mt-3">
				<label for="userPassword" class="form-label">비밀번호(문자,숫자,특수문자 포함 / 8~12자)</label>
				<div class="password-control">
					<i class="fa-solid fa-eye-slash fa-lg"></i>
					<input type="password" class="form-control" id="userPassword" placeholder="비밀번호를 입력하세요."/>
				</div>
			</div>

			<div class="mb-3 mt-3">
				<label for="userName" class="form-label">이름</label> <input
					type="text" class="form-control" id="userName" placeholder="2~10자의 한글이름을 입력하세요."/>
			</div>

			<div class="mb-3 mt-3">
				<label for="userPhoneNum" class="form-label">휴대폰번호</label>
				<input type="text" class="form-control" id="userPhoneNum" placeholder="휴대폰번호를 입력하세요."/>
			</div>

			<div class="mb-3 mt-3">
				<div class="email-control" >
					<label for="userEmail" class="form-label">이메일</label>
					<button type="button" class="btn btn-primary btn-sm" onclick="validUserEmail()">확인</button>
				</div>
				<div class="email-control" id="email-control">
					<input type="text" class="form-control" id="userEmail" />
					<div class="email" style="font-size: 20px">@</div>
					<input type="text" class="form-control" id="userEmailDomain" />
					<select class="form-select" id="domain-list">
						<option value="type">직접 입력</option>
					</select>
				</div>
			</div>

			<div class="mb-3 mt-3">
				<button type="button" class="btn btn-primary" onclick="register()">회원가입</button>
				<button type="button" class="btn btn-secondary" onClick="location.href='/'">취소</button>
			</div>
		</div>
	</div>

	<%@ include file="/WEB-INF/views/comm/footer.jsp"%>
</body>
</html>