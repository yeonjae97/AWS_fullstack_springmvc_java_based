<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>2023. 4. 11.오전 10:35:18</title>
</head>
<style>
	table {border-collapse: collapse; width: 800px;  text-align: center; margin: 0 auto;}
	table, th ,td{ border: 1px solid; text-align: center}
	.receiveList tr {cursor: pointer;}
	.receiveList tr:hover{text-decoration:underline;}
	
</style>
<body>

	<!-- 	로그인 및 세션 -->
	<c:if test="${empty member }">
		<form method="post">
			<input name="username"> 
			<input name="pasword" type="password">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
			<button>로그인</button>
			<p>${msg}</p>
			<label>remember-me <input type="checkbox" name="remember-me"></label>
		</form>
	</c:if>
	<c:if test="${not empty member }">
	<p>로그인 됨</p>
	<p>${member.name }/ ${member.id }</p>
	<p>미확인 쪽지의 갯수 <span id="uncheckedNoteCount">0</span></p>
	<a href="logout">로그아웃</a>
	
	<hr>
	<!-- 	발송할 회원의 목록이 들어갈 곳 -->
	<select size="5" id="receiverList">

<!-- 		<!-- 	비동기 형태로 처리할 예정 --> 
<!-- 		<option>1</option> -->
<!-- 		<option>1</option> -->
<!-- 		<option>1</option> -->
	</select>
	<input type="text" id="textMessage">
	<button id="btnSend">발송</button>
	<hr>
	<h4>${member.name }의 발송 목록</h4>
	<table class="sendList">
	</table>
	<hr>
	<h4>${member.name }의 수신 목록</h4>
	<table class="receiveList">
	</table>
	<hr>
	<h4>${member.name }의 수신(미확인) 목록</h4>
	<table class="receiveCheckedList">
	</table>
	
	</c:if>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js" integrity="sha512-pumBsjNRGGqkPzKHndZMaAG+bir374sORyzM3uulLV14lN5LyykqNk8eEeUlUkB3U0M4FApyaHraT65ihJhDpQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
	<script>let cp = '${pageContext.request.contextPath}';</script>
	<script src="${pageContext.request.contextPath }/resources/js/note.js"></script>	
	<script>
		console.log(cp);
		$(function() {
			
			var ws = new WebSocket("ws://localhost/note");
			let sender = "${member.id}";
			ws.onopen = function(ev){
				console.log("연결 완료",ev);
			}
			ws.onclose = function(ev){
				console.log("연결 종료",ev);		
			}
			ws.onmessage = function(ev){
				console.log(ev.data);
				var obj = JSON.parse(ev.data);
				console.log(obj);
				// JSON 으로 변환한 객체를 가져왔으니...
				if(obj.sender === sender){
					// 발송 관련 이벤트 처리
					$(".sendList").html(getTableText(obj.sendList));
					
				}else{
					// 수신 과련 이벤트 처리
					$("#uncheckedNoteCount").text(obj.receiveUncheckedList.length);
					$(".receiveCheckedList").html(getTableText(obj.receiveUncheckedList));
					$(".receiveList").html(getTableText(obj.receiveList));
					
				}
			}
			
			
			$.getJSON("getList").done(function(data) {
				let str = "";
				for (let i in data) {
					str += `<option>\${data[i].id}</option>`;
				}
				$("#receiverList").html(str)
			})
			
			$("#btnSend").click(function(){
				let receiver = $("#receiverList").val();
				let message = $("#textMessage").val();
				console.log(sender);
				console.log(receiver);
				console.log(message);
				let obj = {sender: sender, receiver:receiver, message:message};
				if(!sender || !receiver || !message){
					alert("오류");
					return false;
				}
				noteService.send(obj, function(data){
					ws.send(receiver);
				});
			
			});
			
			
			// 콜백 지옥 => 이 문제를 해결하기 위한 방법으로 Promise가 있다.
// 			noteService.getSendList('javaman', function (data){
// 				console.log("getSendList")
// 				console.log(data);
// 				noteService.getRecieveList('javaman', function (data){
// 					console.log("getReceiveList")
// 					console.log(data);
// 					noteService.getReceiveCheckedList('javaman', function (data){
// 						console.log("getReceiveCheckedList")
// 						console.log(data);
// 						noteService.get(159, function (data){
// 							console.log("get")
// 							console.log(data);
							
// 						})
// 					})
// 				})
// 			})
			
			noteService.getSendList(sender, function (data){
				$(".sendList").html(getTableText(data));
			
			})
			noteService.getReceiveList(sender, function (data){
				$(".receiveList").html(getTableText(data));
			
			})
			noteService.getReceiveCheckedList(sender, function (data){
				$("#uncheckedNoteCount").text(data.length);
				$(".receiveCheckedList").html(getTableText(data));
			})
			
			$(".receiveList").on("click", "tr", function(){
				var noteno = $(this).find("td:first").text();
// 				alert(noteno);
				var rdate = $(this).find("td:last").text();
				console.log(rdate);
				console.log(typeof rdate);
				if(rdate && rdate === 'null'){
					console.log('event');
					noteService.receive(noteno, function(data){
						console.log(data);
					});
				}
			})
			function getTableText(data){
				var str="<tr>";
				str += "<th>쪽지번호</th>";
				str += "<th>발송자</th>";
				str += "<th>수신자</th>";
				str += "<th>발송시간</th>";
				str += "<th>수신시간</th>";
				for(var i in data){
					str += "</tr>"
					str += `<td>\${data[i].noteno}</td>`;
					str += `<td>\${data[i].sender}</td>`;
					str += `<td>\${data[i].receiver}</td>`;
					str += `<td>\${data[i].sdate}</td>`;
					str += `<td>\${data[i].rdate}</td>`;
					str += "</tr>";
// 					str += `<li>noteno : \${data[i].noteno}, <li>수신자 : \${data[i].receive}, 발송시간 : \${data[i].sdate}, 수신시간 : \${date[i].rdate}</li>`;
				}			
				// html() 은 문자열을 덮어씌우는 속성 중 하나 (표 하나밖에 안뜰 것 => 반복문 중 마지막 데이터)
				// 그래서 return을 str로 받음
				return str;
			}
			

			
// 			// 단일 조회
// 			noteService.get(1, function (data){
// 				console.log("get")
// 				console.log(data);
				
// 			})
			
			
// 			// 업데이트 => 메세지의 내용은 수정하지 않는데 수신 시간에 대해서는 수정 필요
// 			noteService.getRecieveList("javaman", function (data){
// 				console.log("getReceiveList")
// 				console.log(data);
// 			})
			
			
// 			// 쪽지 발송 => 쪽지 수신 => 쪽지 삭제 ( 그래도 쪽지는 발송됨 )
// 			noteService.getReceiveCheckedList("javaman", function (data){
// 				console.log("getReceiveCheckedList")
// 				console.log(data);
// 			})
		});
	</script>
</body>
</html>