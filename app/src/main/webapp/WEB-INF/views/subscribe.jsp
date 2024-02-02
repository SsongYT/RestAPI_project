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
		getSolutionType();
		daterangepicker();
		
		//데이터피커 변경시 포멧변경
		$('#subscribeDate').on('apply.daterangepicker', (e, picker) => {
			let diffMonths = picker.chosenLabel.split('개월')[0];
			$('#diffMonths').val(diffMonths);
			formatDate(picker.startDate, picker.endDate);
			outputPrice();
		});
		
		// 솔루션 선택시
		$('#solution').change(function() {
			let choiceSolution = $('#solution').val();
			if(choiceSolution != "") {
				getSolutionInfo(choiceSolution);
			}
		});
		
	});
	
	//데이터피커함수
	function daterangepicker() {
		let start = moment();
		let end = moment().add(1, 'M');
		
		//데이터피커
		$('#subscribeDate').daterangepicker({
			startDate: start,
		    endDate: end,
		    showCustomRangeLabel: false,
		    ranges: {
		    	'1개월': [moment(), moment().add(1, 'M')],
		    	'3개월': [moment(), moment().add(3, 'M')],
		        '6개월': [moment(), moment().add(6, 'M')],
		        '12개월': [moment(), moment().add(1, 'y')]
		    }
		});
		//처음 날짜 포멧변경
		formatDate(start, end);
	}
	
	//데이터피커 날짜 포멧함수
	function formatDate(start, end) {
		$('#subscribeDate').val(start.format('YYYY/MM/DD') + ' ~ ' + end.format('YYYY/MM/DD'));
	}
	
	
	// 솔루션 타입 받아오기
	function getSolutionType(){
		$.ajax({
	    	  url : 'subscribe/solution',
	          type : 'get',
	          headers : {"content-type":"application/json"},
	          dataType : 'json',
	          async : false,
	          success: function(data) {
	        	if(data.code == "000") {
	        		outputSolutionType(data.data);
		       	} 
	          },
	          error: function(request, status, error) {
	        	  console.log(status);
	        	  console.log(error);
	        	  alert("DB에 문제가 있습니다. 다시 시도해 주세요.");
	          }
		});
	}
	
	//솔루션 선택시 정보 받아오는 함수
	function getSolutionInfo(choiceSolution) {
		$.ajax({
    	  url : 'subscribe/solution/' + choiceSolution,
          type : 'get',
          headers : {"content-type":"application/json"},
          dataType : 'json',
          async : false,
          success: function(data) {
        	console.log(data)
        	if(data.code == "000") {
        		// 성공했을때 할일
        		outputSolutionInfo(data.data);
	       	} 
          },
          error: function(request, status, error) {
        	  console.log(status);
        	  console.log(error);
        	  alert("DB에 문제가 있습니다. 다시 시도해 주세요.");
          }
		});
	};
	
	// 구독하기
	function subscribe() {
		let data = {
				solutionType: $('#solution').val(),
				subscribeDate : $('#subscribeDate').val(),
				solutionPrice : $('#priceInt').val(),
				diffMonths :$('#diffMonths').val()
		}
		console.log(data);
		
		$.ajax({
	    	  url : 'subscribe',
	          type : 'POST',
	          headers : {"content-type":"application/json"},
	          data : JSON.stringify(data),
	          dataType : 'json',
	          async : false,
	          success: function(data) {
	        	console.log(data)
	        	if(data.code == "000") {
	        		// 성공했을때 할일
		       	} 
	          },
	          error: function(request, status, error) {
	        	  console.log(status);
	        	  console.log(error);
	        	  alert("DB에 문제가 있습니다. 다시 시도해 주세요.");
	          }
			});
	}
	
	//솔루션타입 표시
	function outputSolutionType(solutionTypeList) {
		let output = '<option value="">솔루션을 선택하세요.</option>';

		$.each(solutionTypeList, function(i, solutionType) {
			output +=`<option value="\${solutionType}">\${solutionType}</option>`;
		})
		
		$('#solution').html(output);
	}
	
	//솔루션정보 표시
	function outputSolutionInfo(solutionInfo) {
		let outputVolume = `\${solutionInfo.solution_volume}TB`;
		$('#volume').val(outputVolume);
		$('#priceInt').val(solutionInfo.solution_price);
		outputPrice();
	}
	
	//가격 표시
	function outputPrice() {
		let price = Number($('#priceInt').val());
		let date = $('#subscribeDate').val();
		let startDate = date.split(' ~ ')[0];
		let endDate = date.split(' ~ ')[1];
		let diffMonths = $('#diffMonths').val();
		
		let outputSolutionPrice = `\${price.toLocaleString('ko-KR')}원 * \${diffMonths}개월 = \${(price * diffMonths).toLocaleString('ko-KR')}원`;
		$('#price').val(outputSolutionPrice);
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
			<h1>구독신청</h1>

			<div class="mb-3 mt-3">
				<label for="userId" class="form-label">아이디</label> 
				<input type="text" class="form-control" id="userId" value="${sessionScope.loginMember}" readonly="readonly" />
			</div>

			<div class="mb-3 mt-3">
				<label for=solution class="form-label">솔루션</label>
				<select class="form-select" id="solution">
				</select>
			</div>

			<div class="mb-3 mt-3 volume">
				<label for=volume class="form-label">스토리지 용량</label>
				<input type="text" class="form-control" id="volume" value="" readonly="readonly" />
			</div>

			<div class="mb-3 mt-3">
				<label for="subscribeDate" class="form-label">구독기간</label>
				<input type="text" class="form-control" id="subscribeDate" value="구독기간을 선택하세요." />
				<input type="hidden" id="diffMonths" value="1" />
			</div>
			
			<div class="mb-3 mt-3">
				<label for="price" class="form-label">총 가격</label>
				<input type="text" class="form-control" id="price" value="" readonly="readonly"/>
				<input type="hidden" id="priceInt" value="" />
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