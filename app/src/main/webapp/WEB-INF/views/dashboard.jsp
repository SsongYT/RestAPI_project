<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

<!-- jquery -->
<script type="text/javascript" src="https://cdn.jsdelivr.net/jquery/latest/jquery.min.js"></script>	
<!-- dater_picker -->
<script type="text/javascript" src="https://cdn.jsdelivr.net/momentjs/latest/moment.min.js"></script>

<title>RMSOFT_구독변경</title>

<script type="text/javascript">
	$(document).ready(function(){
		dashboardData();
		
		
		
	});
	
	function dashboardData() {
		$.ajax({
	    	  url : 'dashboard/data',
	          type : 'get',
	          headers : {"content-type":"application/json"},
	          dataType : 'json',
	          async : false,
	          success: function(data) {
	        	if(data.code == "000") {
	        		outputDashboard(data.data);
		       	} 
	          },
	          error: function(error) {
	        	  console.log(error);
	        	  if(error.responseJSON.code == "E005") {
		        	  	alert("DB에 문제가 있습니다. 다시 시도해 주세요.");
	        	  } else if(error.responseJSON.code == "S01") {
	        		  alert("로그인 정보를 확인해주세요.");
	        	  } else if(error.responseJSON.code == "001") {
	        		alert("구독 정보가 없습니다.");	  
	        	  }
	          }
		});
	}
	
	function outputDashboard(data) {
		let solution = data.solution;
		$('#solution').val(solution);
		
		let volumeUsage = data.volumeUsage;
		let solutionVolume = data.solutionVolume;
		let percentVolume = 0;
		if(volumeUsage != 0) {
			percentVolume = Math.round((volumeUsage/(solutionVolume * 1000000000000))* 100);
		}
		console.log(solutionVolume);
		console.log(volumeUsage);
		console.log(percentVolume);
		let volumeOutput = `<label for=volume class="form-label">스토리지 용량<span class="mr-3"> ( \${volumeUsage}B / \${solutionVolume}TB )</span></label>`;
		volumeOutput += `<div class="progress"><div class="progress-bar" style="width:\${percentVolume}%">\${percentVolume}%</div></div>`;
		$('#volume').html(volumeOutput);
		
		let now = moment();
		let endDt = moment(data.endDt);
		let diffDays = endDt.diff(now, "days");
		
		let dateOutput = `<input type="text" class="form-control" value="종료일 : \${endDt.format('YYYY년/MM월/DD일')} (D-\${diffDays})" readonly="readonly" />`;
		
		$('#date').html(dateOutput);
	}

	
</script>

<style>
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
	 align-items: center;  /* 수직 정렬 */
	-webkit-box-pack: center;
	-moz-box-pack: center;
	-ms-flex-pack: center;
	justify-content: center; /* 수평 정렬 */
}
</style>

</head>
<body>
	<%@ include file="/WEB-INF/views/comm/header.jsp"%>
	
	<div class="container main">
		<div class="col-xl-6">
			<h1>구독변경</h1>

			<div class="mb-3 mt-3">
				<label for="userId" class="form-label">아이디</label> 
				<input type="text" class="form-control" id="userId" value="${sessionScope.loginMember}" readonly="readonly" />
			</div>

			<div class="mb-3 mt-3">
				<label for=solution class="form-label">솔루션</label>
				<input type="text" class="form-control" id=solution value="" readonly="readonly" />
			</div>

			<div class="mb-3 mt-3" id="volume">

			</div>

			<div class="mb-3 mt-3">
				<label for="date" class="form-label">구독기간</label>
				<div id="date">
  					
				</div>
			</div>

			<div class="mb-3 mt-3">
				<button type="button" class="btn btn-primary" onclick="subscribe()">변경/취소</button>
				<button type="button" class="btn btn-secondary" onClick="location.href='/'">돌아가기</button>
			</div>
		</div>
	</div>

	<%@ include file="/WEB-INF/views/comm/footer.jsp"%>
</body>
</html>