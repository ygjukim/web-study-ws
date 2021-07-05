RESERVATION<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ page import="java.util.*, com.fullstack.booking.model.*" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공연 예약 프로그램-예약확인</title>
<style>
body {
	height: 100%;
	margin: 0 auto;
	max-width: 900px;	
}
header {
	max-width: 100%;
	height: 150px;
	display: flex;
	flex-direction: column;
 	align-items: center;
	justify-content: center;
	background-color: #90CAF9;
}
.icon {
	width: 30px;
	height: 30px;
	cursor: pointer;
}
.detail_content_head {
	max-width: 100%;
	height: 75px;
	display: flex;
	align-items: center;
	justify-content: center;
	background-color: #E3F2FD;
}
.detail_content_body {
	display: flex;
	flex-direction: column;
	justify-content: center;
	max-width: 100%;
}
.detail_content_title {
	display: flex;
	justify-content: center;
	height: 35px; 
	background-color: #E5E7E9;
}
.detail_content_field {
	display: flex;
	justify-content: center;
	height: 100px; 
	background-color: #F2F3F4;
}
.menubar {
	display: flex;
	justify-content: center;
	margin-top: 10px;
	margin-bottom: 30px;
}
.button {
	width: 125px;
	height: 40px;
	display: flex;
	align-items: center;
	justify-content: center;
	color: white;
	background-color: #F44336;
	font-size: 1.25rem;
 	border-radius: 5px;
 	margin: 0 5px;
 	cursor: pointer;		
}
footer {
	max-width: 100%;
	height: 75px;
	display: flex;
	justify-content: center;
	background-color: #90CAF9;
}

td, th {
	text-align: center;
}
</style>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
	var deleteDialog;
	var rsvSeq = 0;
	
	$( function() {
	  
	  deleteDialog = $( "#delete-confirm" ).dialog({
		autoOpen: false,
	    resizable: false,
	    height: "auto",
	    width: 400,
	    modal: true,
	    buttons: {
	      "예약 취소": function() {
	    	  window.location.href = "/reservation/delete?seq=" + rsvSeq;
	        $( this ).dialog( "close" );
	      },
	      "취소": function() {
	        $( this ).dialog( "close" );
	      }
	    }
	  });
	  
/* 	  deleteBtn = $( "#delete-rsv" ).on( "click", function() {
	      deleteMsg.text("예약코드 " + rsvSeq.val() + " 예약을 정말로 취소하시겠습니까?")
	              .addClass( "ui-state-highlight" );
		  deleteDialog.dialog( "open" );
	  }); */
	} );
	
	function doDelete( seq ) {
		rsvSeq = seq;
		$( "#delete-msg" ).text("예약코드 " + seq + " 예약을 정말로 취소하시겠습니까?");
  		deleteDialog.dialog( "open" );
  	}
</script>
</head>
<body>
	<header>
		<h1>신라 티켓 링크</h1>	
	</header>
	<div class="main">
	    <div class="detail_content_head">
	        <h2>예약 확인</h2>
	    </div>
	    <div class="detail_content_body">
	    	<div>
	    		<div class="detail_content_title"><h3>${name}님 예약 현황</h3></div>
	    		<div class="detail_content_field" style="height: auto;">
<%
				List<Reservation> rsvList = (List<Reservation>)request.getAttribute("rsv_list");
				if (rsvList == null || rsvList.size() == 0) {
%>
					<p>예약 내용이 존재하지 않습니다!.</p>					
<%					
				}
				else {
%>	    		
	    			<table border="1">
	    				<tr><th>예약코드</th><th>이름</th><th>연락처</th><th>예약날짜</th><th>좌석 번호</th><th></th></tr>
<%
					for (Reservation rsv : rsvList) {
						out.print("<tr>");
						out.print("<td>" + rsv.getRsvSeq() + "</td>");
						out.print("<td>" + rsv.getName() + "</td>");
						out.print("<td>" + rsv.getPhone() + "</td>");
						out.print("<td>" + rsv.getRsvDate() + "</td>");
						out.print("<td>" + Arrays.toString(rsv.getSeatNumbers()) + "</td>");
						out.print("<td>");
						out.print("<input type='button' id='update-rsv' value='예약 수정' style='margin: 3px;' />");
						out.print("<input type='button' id='delete-rsv' value='예약 취소' style='margin: 3px;' onclick='doDelete("+rsv.getRsvSeq()+")'/>");
						out.print("</td>");
						out.print("<tr>");
					}
%>	    		
	    			</table>
<%
				}
%>	    			
	    		</div>
	    	</div>
			<div id="delete-confirm" title="예약 취소">
			  <p><span class="ui-icon ui-icon-alert" style="float:left; margin:12px 12px 12px 0;"></span>
			  	<span id="delete-msg" style="float:left; margin:12px 0px 12px 5px;"></span></p>
			</div>
	    </div>	    
	    <div class="menubar">
	    	<div class="button" onclick="location.href='/index';"><strong>확인</strong></div>
	    </div>
	</div>	
	<footer>
		<h4>공연 예약 샘플 프로그램</h4>
	</footer>
</body>
</html>