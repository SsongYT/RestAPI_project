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
		let today = moment();

		$('#reportrange').daterangepicker({
			startDate: today,
		    endDate: today,
		    showCustomRangeLabel: false,
		    ranges: {
		    	'1달': [moment(), moment().add(1, 'M')],
		    	'3달': [moment(), moment().add(3, 'M')],
		        '6달': [moment(), moment().add(6, 'M')],
		        '1년': [moment(), moment().add(1, 'y')]
		    }
		}, cb);
		cb(start, end);

	});

	function cb(start, end) {
		$('#reportrange span').html(start.format('YYYY-MM-DD') + ' ~ ' + end.format('YYYY-MM-DD'));
	}

</script>

</head>
<body>
	<%@ include file="/WEB-INF/views/comm/header.jsp" %>
	
	<div>index</div>
	<div>${sessionScope.loginMember}</div>
	<div>
		<div id="reportrange" style="background: #fff; cursor: pointer; padding: 5px 10px; border: 1px solid #ccc; width: 100%">
        	<i class="fa fa-calendar"></i>&nbsp;
    		<span></span>
    		<i class="fa fa-caret-down"></i>
        </div>
	</div>

	<%@ include file="/WEB-INF/views/comm/footer.jsp" %>

</body>
</html>