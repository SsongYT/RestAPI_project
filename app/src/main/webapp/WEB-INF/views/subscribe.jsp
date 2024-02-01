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
<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.min.js"></script>
<link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.css" />

<title>RMSOFT_구독하기</title>

<script type="text/javascript">
	$(document).ready(function(){
		let today = new Date();
		let today_ts = Date.parse(today);
		let minDay = new Date(today_ts + (24 * 60 * 60 * 1000));
		//달력
		$('#subscribeDate').daterangepicker({
			//buttonClasses: ['btn', 'btn-sm'],
			//applyClass: 'btn-danger',
			//cancelClass: 'btn-inverse',
			startDate: today,
			endDate: today,
			locale: {
				format: 'YYYY-MM-DD',
			    separator: ' ~ ',
			    applyLabel: '선택',
			    cancelLabel: '취소',
			    daysOfWeek: ['일', '월', '화', '수', '목', '금', '토'],
			    monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
			},
			showDropdowns: true,
			minDate: minDay,
			//maxDate:,    //최대제한날짜
		  });

		// 솔루션 선택
		$('#solution').change(function() {
			let choiceSolution = $('#solution').val();
			if(choiceSolution != "") {
				$.ajax({
	    	   	  url : 'subscribe/solution/' + choiceSolution,
	        	  type : 'get',
	        	  headers : {"content-type":"application/json"},
	        	  dataType : 'json',
	        	  async : false,
	        	  success: function(data) {
	        		console.log(data)
	        		if(data.code == "000") {
	        			
		        	} 
	        	  },
	        	  error: function(request, status, error) {
	        		  console.log(status);
	        		  console.log(error);
	        		  alert("DB에 문제가 있습니다. 다시 시도해 주세요.");
	        	  }
				});
			}
		});
	});

	
	
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
			<h1>구독신청</h1>

			<div class="mb-3 mt-3">
				<label for="userId" class="form-label">아이디</label> 
				<input type="text" class="form-control" id="userId" value="${sessionScope.loginMember}" readonly="readonly" />
			</div>

			<div class="mb-3 mt-3">
				<label for=solution class="form-label">솔루션</label>
				<select class="form-select" id="solution">
						<option value="">솔루션을 선택하세요.</option>
						<option value="Basic">Basic</option>
						<option value="Standard">Standard</option>
						<option value="Premium">Premium</option>
				</select>
			</div>

			<div class="mb-3 mt-3 volume">
				<label for=volume class="form-label">스토리지 용량</label>
				<select class="form-select" id="volume">
						<option value="10TB">10TB</option>
						<option value="30TB">30TB</option>
						<option value="50TB">50TB</option>
						<option value="100TB">100TB</option>
				</select>
			</div>

			<div class="mb-3 mt-3">
				<label for="price" class="form-label">단가(1일기준)</label>
				<input type="text" class="form-control" id="price" value="" readonly="readonly"/>
			</div>

			<div class="mb-3 mt-3">
				<label for="subscribeDate" class="form-label">구독기간</label>
				<input type="text" class="form-control" id="subscribeDate" value="" />
			</div>
			
			<div class="mb-3 mt-3">
				<label for="price" class="form-label">총 가격</label>
				<input type="text" class="form-control" id="price" value="" readonly="readonly"/>
			</div>

			<div class="mb-3 mt-3">
				<button type="button" class="btn btn-primary" onclick="subscribe()">구독신청</button>
				<button type="button" class="btn btn-secondary" onClick="location.href='/'">취소</button>
			</div>
		</div>
	</div>

	<%@ include file="/WEB-INF/views/comm/footer.jsp"%>
</body>
</html>