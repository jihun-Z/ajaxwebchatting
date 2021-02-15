<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
     <c:set var="path" value="${pageContext.request.contextPath }"/>
     
	 <%
		String userID= null;
		if(session.getAttribute("userID") != null){
			userID =(String) session.getAttribute("userID");
			
		}
		String toID=null;
		if(request.getParameter("toID") != null){
			toID=(String) request.getParameter("toID");
		
		} 
		if(userID == null ){
			session.setAttribute("messageType","오류메세지");
			session.setAttribute("messageContent","현재 로그인이 되어 있지 않습니다.");
			response.sendRedirect("index.do");
			return;
		}
	/* 	if(toID == null ){
			session.setAttribute("messageType","오류메세지");
			session.setAttribute("messageContent","대화상대가 지정되어 있지않습니다.");
			response.sendRedirect("index.do");
			return;
		}   */
	%> 
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<link rel="stylesheet" href="${path }/resources/css/bootstrap.css">
	<link rel="stylesheet" href="${path }/resources/css/custom.css">
   <%--  <link rel="stylesheet" href="${path }/resources/css/style.css"/> --%>
<title>ajax 실시간 채팅 서비스</title>
     <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
     <script src="${path }/resources/js/bootstrap.js"></script>
     <script type="text/javascript">
     	function autoClosingAlert(selector,delay){
     		let alert =$(selector).alert();
     		console.log("alert:"+alert);
     		alert.show();
     		console.log(alert.show());
     		window.setTimeout(function() { alert.hide() },delay);
     	};
     	function submitFunction(){
     		let fromID="<c:out value='${userID}'/>";
     		console.log("fromID:"+fromID);
     		let toID="11111";
     		let chatContent= $("#chatContent").val();
     		$.ajax({
     			type:"POST",
     			url:"${path}/chatSubmit.do",
     			data:{
     				fromID:encodeURIComponent(fromID),
     				toID:encodeURIComponent(toID),
     				chatContent:encodeURIComponent(chatContent)
     			},
     				success:function(result){
     					if(result == 1){
     						autoClosingAlert("#successMessage",3000);
     					}else if(result== 0){
     						autoClosingAlert("#dangerMessage",3000);
     					}else{
     						autoClosingAlert("#worningMessage",3000);
     					}
     				}
     		});
     		$('#chatContent').val("");//다하면 메시지창 비워주기
     	}
     	let lastID= 0;
     	function chatListFunction(type){
     		let fromID='<c:out value='${userID}'/>';
     		//let toID='${toID}';
     		let toID='11111';
     		console.log("fromID:"+fromID);
     		alert("실행!");
     		$.ajax({
     			type:"post",
     			url:"${path}/chatList.do",
     			data:{
     				fromID:encodeURIComponent(fromID),
     				toID:encodeURIComponent(toID),
     				listType:type
     			},
     			success:function(data){
     				console.log("반환된 데이터 실행: "+data);
     				if(data == null) return;
     				let parsed=JSON.parse(data);
     				let result=parsed.result;
     				console.log("result"+result);
     				for(var i= 0; i <result.length; i++){
     					if(result[i][0].value == fromID){
     						result[i][0].value= '나';
     					}
     					addChat(result[i][0].value,result[i][2].value,result[i][3].value);
     				}
     				lastID = Number(parsed.last);//chatList중 마지막으로 전달받은 아이디
     			}
     		});
     	}
     	function addChat(chatName, chatContent,chatTime){
     		
     		console.log("addchat 실행");
     		$('#chatList').append('<div class="row">'+
     		'<div class="col-lg-12">' +
     		'<div class="media">'+
     		'<a class="pull-left" href="#">'+
     		'<img class="media-object img-circle" style="width:30px; height:30px;" src="${path}/resources/images/icon.png" alt="">'+
     		'</a>'+
     		'<div class="media-body">'+
     		'<h4 class="media-heading">'+
     		chatName +
     		'<span class="small pull-right">'+
     		chatTime +
     		'</span>'+
     		'</h4>'+
     		'<p>'+
     		chatContent + 
     		'</p>'+
     		'</div>'+
     		'</div>'+
     		'</div>'+
     		'</div>');
     		$("#chatList").scrollTop('#chatList')[0].scrollHeight;
     	};
     	
     	/* 새로운 메시지를 가져올때마다 시간체크하는 함수 */
     	function getInfiniteChat(){
     	 	setInterval(function(){
     			chatListFunction(lastID);
     		},30000); 
     	}
     </script>
</head>
<body>
	<nav class="navbar navbar-default">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
			data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
			aria-expanded="false">
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="index.jsp">실시간 채팅서비스</a>
		</div>
		<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<li ><a href="${path }/index.do">메인</a></li>
				<li ><a href="${path }/find.do">친구찾기</a></li>
			</ul>
			<c:if test="${userID != null}">
			<ul class="nav navbar-nav navbar-right">
					<li class="dropdown">
						<a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="buton" aria-haspopup="true"
						aria-expanded="false">회원관리<span class="caret"></span>
						</a>
						<ul class="dropdown-menu">
							<li><a href="${path }/logoutAction.do">로그아웃</a></li>
					
						</ul>
			</c:if>
		</div>
	</nav>
	<div class="container bootstrap snippet">
		<div class="row">
			<div class="portlet partlet-default">
				<div class="portlet-heading">
				
					<div class="portlet-title">
						<h4><i class="fa fa-circle text-green"></i>실시간 채팅창</h4>
					</div>
					<div class="clearfix"></div>
				</div>

			<div id="chat" class="panel-collapse collapse in">
				<div id="chatList" class="portlet-body chat-widget" style="overflow-y:auto; width:auto;height:400px;background: khaki;">
				</div>
				<div class="portlet-footer">
					<div class="row" style="height:90px;">
						<div class="form-group col-xs-10">
							<textarea style="height:80px;" id="chatContent" class="form-control" placeholder="메시지를 입력하세요" maxlength="100"></textarea>
						</div>
						<div class="form-group col-xs-2">
							<button type="button" class="btn btn-default pull-right" onclick="submitFunction();">전송</button>
							<div class="clearfix"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	</div>
	<div class="alert alert-success" id="successMessage" style="display:none;">
		<strong>메세지 전송에 성공했습니다.</strong>
	</div>
	<div class="alert alert-danger" id="dangerMessage" style="display:none;">
		<strong>이름과 내용을 모두 입력해주세요.</strong>
	</div>
	<div class="alert alert-worning" id="worningMessage" style="display:none;">
		<strong>데이터베이스 오류가 발생했습니다.</strong>
	</div>
<%
	String messageContent = null;
	if(session.getAttribute("messageContent") != null){
		messageContent = (String) session.getAttribute("messageContent");
	}
	String messageType= null;
	if(session.getAttribute("messageType") != null ){
		messageType=(String) session.getAttribute("messageType");
	}
%> 
<c:if test="${messageContent != null}">
<c:if test="${messageType != null}">
	<div class="modal fade" id="messageModal" tabindex="-1" role="dialog" arid-hidden="true">
		<div class="vertical-alignment-helper">
			<div class="modal-dialog vertical-align-center">
				<div class="modal-content <c:if test="${messageType.equals('오류메시지')}"><c:out value="panel-warning"/></c:if><c:if test="${!messageType.equals('오류메시지')}"><c:out value="panel-success"/></c:if><%-- <%if (messageType.equals("오류메시지")) out.println("panel-warning");else out.println("panel-success"); %> --%>">
					<div class="modal-header panel-heading">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times</span>
							<span class="sr-only">Close</span>
						</button>
						<h4 class="modal-title">
							<c:out value="${messageType }"/>
						</h4>
					</div>
					<div class="modal-body">
						<c:out value="${messageContent }"/>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary" data-dismiss="modal">확인</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	</c:if>
	</c:if>
	<script>
		$("#messageModal").modal("show");
	</script>
	<%
		session.removeAttribute("messageContent");
		session.removeAttribute("messageType");
	%>
	<script type="text/javascript">
	$(document).ready(function(){
		chatListFunction('ten');
		getInfiniteChat();
	});
	</script>
</body>
</html>