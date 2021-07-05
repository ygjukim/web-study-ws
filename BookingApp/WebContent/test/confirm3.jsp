<%@ page language="java" contentType="text/html; charset=UTF-8"
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

/* styles for JQuery modal dialog */ 
label, input { display:block; }
input.text { margin-bottom:12px; width:95%; padding: .4em; }
fieldset { padding:0; border:0; margin-top:25px; }
</style>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
  var deleteDialog;
  
  $( function() {
    var deleteForm;
    var passwd = $( "#passwd" ), allFields = $( [] ).add( passwd );

    function checkLength( o, min, max ) {
    	console
		if ( o.val().length > max || o.val().length < min ) {
		  o.addClass( "ui-state-error" );
		  return false;
		} else {
		  return true;
		}
    }
    
	deleteDialog = $( "#delete-dialog-form" ).dialog({
		autoOpen: false,
		height: 280,
		width: 350,	
		modal: true,
		buttons: {
		   "확인": function() {
			   deleteForm.trigger("submit");
			},
		   "취소": function() {
			   deleteDialog.dialog( "close" );
		  }
		},
		close: function() {
	        allFields.removeClass( "ui-state-error" );
		}
	});
	
 	deleteForm = deleteDialog.find( "form" ).on( "submit", function( event ) {
		var valid = true;
		allFields.removeClass( "ui-state-error" );
		
		valid = valid && checkLength( passwd, 3, 30 );
		
		if ( valid ) {
			deleteDialog.dialog( "close" );
		}
		return valid;
	});	
	
  } );
  
  function doDelete( seq ) {
	$("#rsv-seq").val(seq);
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
	    	
			<div id="delete-dialog-form" title="예약 취소">
			  <form action="/reservation/delete" method="POST">
			    <p>예약할 때에 등록한 비밀번호를 입력하여주세요.</p>
			    <input type="hidden" id="rsv-seq" name="seq" />
			    <fieldset>
			      <label for="name">예약 비밀번호:</label>
			      <input type="password" name="passwd" id="passwd" class="text ui-widget-content ui-corner-all">
			 
			      <!-- Allow form submission with keyboard without duplicating the dialog button -->
			      <input type="submit" tabindex="-1" style="position:absolute; top:-1000px">
			    </fieldset>
			  </form>
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