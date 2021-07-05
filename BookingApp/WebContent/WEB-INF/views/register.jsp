<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ page import="java.util.*, com.fullstack.booking.model.*" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공연 예약 프로그램-예약하기</title>
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
	height: 35px; 
	background-color: #E5E7E9;
}
.detail_content_field {
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

td {
	width: 30px;
	text-align: center;
}
tr {
	height: 25px;
}

</style>
<script>
function checkAndSubmit() {
	const pw = document.getElementById("passwd").value;
	const pw2 = document.getElementById("passwd2").value;
	if (pw === "" || pw2 === "" || pw !==  pw2) {
		alert("예약 확인 비밀번호가 입력되지 않았거나 다르게 입력되었습니다!\n다시 입력하여 주세요.");
	}
	else {
		document.getElementById("rsv_form").submit();
	}
};
</script>
</head>
<body>
	<header>
		<h1>신라 티켓 링크</h1>	
	</header>
	<div class="main">
	    <div class="detail_content_head">
	        <h2>예약 등록</h2>
	    </div>
	    <form id="rsv_form" action="/reservation/register" method="POST">
	    <div class="detail_content_body">
	    	<div>
	    		<div class="detail_content_title">1. 좌석 선택</div>
	    		<div class="detail_content_field" style="height: auto;">
	    			<table align="center">
	    				<tr>
	    					<td></td><td>1</td><td>2</td><td>3</td><td>4</td><td>5</td><td>6</td><td>7</td><td>8</td><td>9</td><td>10</td>
	    				</tr>
<%
							List<Seat> seats = (List<Seat>)request.getAttribute("seats");
							String[] rows = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J" };
							int index = 0;
							for (int k=0; k<2; k++) {
								out.print("<tr><td>" + rows[k] + "</td>");
								for (int i=0; i< 10; i++) {
									if (seats.get(index++).getRsvSeq() > 0)
										out.print("<td><input type='checkbox' value='"+ (rows[k]+i) +"' name='seats' checked disabled /></td>");
									else
										out.print("<td><input type='checkbox' value='"+ (rows[k]+i) +"' name='seats' /></td>");
								}
								out.print("</tr>");
							}
							out.print("<tr><td colspan=11>  </td></tr>");
							
							for (int k=2; k<5; k++) {
								out.print("<tr><td>" + rows[k] + "</td>");
								for (int i=0; i< 10; i++) {
									if (seats.get(index++).getRsvSeq() > 0)
										out.print("<td><input type='checkbox' value='"+ (rows[k]+i) +"' name='seats' checked disabled /></td>");
									else
										out.print("<td><input type='checkbox' value='"+ (rows[k]+i) +"' name='seats' /></td>");
								}
								out.print("</tr>");
							}
							out.print("<tr><td colspan=11>  </td></tr>");

							for (int k=5; k<10; k++) {
								out.print("<tr><td>" + rows[k] + "</td>");
								for (int i=0; i< 10; i++) {
									if (seats.get(index++).getRsvSeq() > 0)
										out.print("<td><input type='checkbox' value='"+ (rows[k]+i) +"' name='seats' checked disabled /></td>");
									else
										out.print("<td><input type='checkbox' value='"+ (rows[k]+i) +"' name='seats' /></td>");
								}
								out.print("</tr>");
							}
%>
	    			</table>
	    		</div>
	    	</div>
	    	<div>
		    	<div  class="detail_content_title">2. 예약 정보 입력</div>
		    	<div  class="detail_content_field">
		    		<div>
		    			<label>예약자 이름   :</label>
		    			<input type="text" name="name" placeholder="예약자 성함" required />
		    		</div>
		    		<div>
		    			<label>예약자 연락처 :</label>
		    			<input type="text" name="phone" placeholder="전화번호: 010-1111-2222" required />
		    		</div>
		    	</div>
	    	</div>
	    	<div>
	    		<div class="detail_content_title">3. 예약 비밀번호 입력</div>
	    		<div class="detail_content_field">
	    			<div>
		    			<label>예약 확인 비밀번호   :</label>
			    		<input type="password" id="passwd" name="passwd" required />
	    			</div>
	    			<div>
		    			<label>예약 비밀번호 재입력 :</label>
			    		<input type="password" id="passwd2" name="passwd2" required />
	    			</div>
	    		</div>
	    	</div>
	    </div>
	    <div class="menubar">
	    	<div class="button" onclick="checkAndSubmit();"><strong>확인</strong></div>
	    	<div class="button" onclick="location.href='/index';"><strong>취소</strong></div>
	    </div>
	    </form>
	</div>	
	<footer>
		<h4>공연 예약 샘플 프로그램</h4>
	</footer>
</body>
</html>