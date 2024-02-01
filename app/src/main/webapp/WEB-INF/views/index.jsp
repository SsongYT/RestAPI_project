<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script type="text/javascript" src="https://cdn.jsdelivr.net/jquery/latest/jquery.min.js"></script>	
<script type="text/javascript" src="https://cdn.jsdelivr.net/momentjs/latest/moment.min.js"></script>
<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.min.js"></script>
<link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.css" />

<title>Index</title>

<script>
$(function() {
		let today = new Date();
		let today_ts = Date.parse(today);
		
		let minDay = new Date(today_ts + (24 * 60 * 60 * 1000));

		 $('#daterange').daterangepicker({
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

  
});

</script>

</head>
<body>
	<%@ include file="/WEB-INF/views/comm/header.jsp" %>
	
	<div>index</div>
	<div>${sessionScope.loginMember}</div>
	<div>
		<div class="example">
        	<p class="mb-1">Date Range Pick</p>
        	<input type="text" id="daterange" name="daterange" value="01/01/2018 - 01/15/2018" />
        </div>
	</div>

	<%@ include file="/WEB-INF/views/comm/footer.jsp" %>

</body>
</html>