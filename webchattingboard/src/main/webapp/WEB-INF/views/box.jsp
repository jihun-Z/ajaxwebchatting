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
		if(userID == null ){
			session.setAttribute("messageType","오류메세지");
			session.setAttribute("messageContent","현재 로그인이 되어 있지 않습니다.");
			response.sendRedirect("index.do");
			return;
		}
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
     	function getUnread(){
     		$.ajax({
     			type:"post",
     			url:'${path}/chatUnread.do',
     			data:{
     				userID:'<c:out value="${userID}"/>'
     			},
     			success:function(result){
     				if(result == 0){
     					showUnread('0');
     				}else{
     					showUnread(result);
     				}
     			}
     		});
     	};
     	function getInfiniteUnread(){
     		getUnread();
     	}
     	function showUnread(result){
     		$("#unread").html(result);
     	}
     	function chatBoxFunction(){
     		const userID='<c:out value="${userID}"/>';
     		$.ajax({
     			type:'post',
     			url:'${path}/chatbox.do',
     			data:{
     				userID:userID
     			},
     			success: function(data){
     				if(data == "" ) return;
     				$("#boxTable").html('');
     				let parsed= JSON.parse(data);
     				let result=parsed.result;
     				console.log("data:"+data);
     				for( let i =0; i < result.length; i++){
	     				if(result[i][0].value == userID){
	     					//아이디를 대입하는코드
	     					result[i][0].value = result[i][1].value;
	     				}else{
	     					result[i][1].value = result[i][0].value;
	     				}
	     				//각각의 목록을 출력해주는  함수 
	     				addBox(result[i][0].value,result[i][1].value,result[i][2].value,result[i][3].value);
     				}
     			}
     		});
     	}
     	function addBox(lastID,toID,chatContent,chatTime){
     		console.log("lastID:"+lastID);
     		$('#boxTable').append('<tr onclick="location.href=\'${path}/chat.do?toID='+toID+'\'">'+
     				'<td style="width: 150px;"><h5>' + lastID + '</h5></td>' +
     				'<td>' +
     				'<h5>' + chatContent +'</h5>' +
     				'<div class="pull-right">' + chatTime + '</div>' +
     				'</td>'+
     				'</tr>');
     	}
     	function getInfiniteBox(){
     		chatBoxFunction();
     	}
     </script>
</head>
<body>
	<c:set value="${userID}" var="userID"/>
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
				<li ><a href="${path }/chat.do">채팅</a></li>
				<li class="active" ><a href="${path }/box.do">메시지함<span id="unread" class="label label-info"></span></a></li>
				
			</ul>
		<c:if test="${userID == null }">
			<ul class="nav navbar-nav navbar-right">
					<li class="dropdown">
						<a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="buton" aria-haspopup="true"
						aria-expanded="false">접속하기<span class="caret"></span>
						</a>
						<ul class="dropdown-menu">
							<li><a href="${path }/login.do">로그인</a></li>
							<li><a href="${path }/home.do">회원가입</a></li>
						</ul>
					</li>
			</ul>
		</c:if>
			<c:if test="${userID != null }">
				<ul class="nav navbar-nav navbar-right">
						<li class="dropdown">
							<a href="#" class="dropdown-toggle"
							data-toggle="dropdown" role="buton" aria-haspopup="true"
							aria-expanded="false">회원관리<span class="caret"></span>
							</a>
							<ul class="dropdown-menu">
								<li><a href="${path }/logoutAction.do">로그아웃</a></li>
						
							</ul>
						</li>
					</ul>
			</c:if>
		</div>
	</nav>
	<div class="container">
		<table class="table" style="margin: 0 auto;">
			<thead>
				<tr>
					<th><h4>주고받은 메세지 목록</h4></th>
				</tr>
			</thead>
			<div style="overflow-y:auto; width: 100%; max-height: 450px;">
				<table class="table table-bordered table-hover" style="text-align: center; border: 1px solid #dddddd; margin: 0 auto;">
					<tbody id="boxTable">
					</tbody>
				</table>
			</div>
		</table>
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
	<c:if test="${userID != null }">
		<script type="text/javascript">
			$(document).ready(function(){
				getUnread();
				getInfiniteUnread();
				chatBoxFunction();
				getInfiniteBox();
			});
		</script>
	</c:if>
</body>
</html>