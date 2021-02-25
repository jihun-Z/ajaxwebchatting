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
		if(userID == null){
				session.setAttribute("messageType","오류메시지");
				session.setAttribute("messageContent","현재 로그인이 되어있지 않습니다.");
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
      function findFunction(){
    	  let userID=$("#findID").val();
    	  $.ajax({
    		  type:"post",
    		  url:'${path}/userRegisterCheck.do',
    		  contentType: 'application/x-www-form-urlencoded; charset=euc-kr',
    		  data:{userID:userID},
    		  success:function(result){
    			  if(result == 1 ){
    				  $("#checkMessage").html('친구찾기에 성공했습니다.');
    				  $("#checkType").attr("class","modal-content panel-success");
    				  getFriend(userID);
    			  }else{
    				  $("#checkMessage").html('친구를 찾을 수 없습니다.');
    				  $("#checkType").attr("class","modal-content panel-warning");
    				  failFriend();
    			  }
    			  
    			  $("#checkModal").modal("show");
    		  }
    	  });
      }
      function getFriend(findID){
    	  console.log("findID:"+findID);
    	  $("#friendResult").html('<thead'+
    			'<tr>'+
    			'<th><h4>검색 결과</h4></th>'+
    			'</tr>'+
    			'</thead>'+
    			'<tbody>'+
    			'<tr>'+
    			'<td style="text-align:center;"><h3>'+ findID+
    			'</h3><a href="${path}/chat.do?toID='+encodeURIComponent(findID)+
    					'" class="btn btn-primary pull-right">'+
    					'메세지보내기</a></td>'+
    			'</tr>'+
    			'</tbody>'
    	  );
      }
    	function failFriend(){
    		  $("#friendResult").html("");
    	  };
    	  //안 읽은 메세지함 불러오기 
       	function getUnread(){
     		$.ajax({
     			type:"post",
     			url:'${path}/chatUnread.do',
     			data:{
     				userID:'<c:out value="${userID}"/>'
     			},
     			success:function(result){
     				if(result == 0){
     					return;
     				}else{
     					showUnread(result);
     				}
     			}
     		});
     	}
     	function getInfiniteUnread(){
				getUnread();
     	}
     	function showUnread(result){
     		$("#unread").html(result);
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
				<li class="active" ><a href="${path }/find.do">친구찾기</a></li>
				<li ><a href="${path }/box.do">메시지함<span id="unread" class="label label-info"></span></a></li>
				<li class=""><a href="${path }/boardView.do">자유게시판</a></li>
			</ul>
				<ul class="nav navbar-nav navbar-right">
						<li class="dropdown">
							<a href="#" class="dropdown-toggle"
							data-toggle="dropdown" role="buton" aria-haspopup="true"
							aria-expanded="false">회원관리<span class="caret"></span>
							</a>
							<ul class="dropdown-menu">
								<li><a href="${path }/update.do?userID=<c:out value="${userID}"/>">회원정보수정</a></li>
								<li class=""><a href="${path }/profileUpdate.do?userID=<c:out value="${userID}"/>">프로필 수정</a></li>
								<li><a href="${path }/logoutAction.do">로그아웃</a></li>
							</ul>
						</li>
					</ul>
		</div>
	</nav>
	<div class="container">
		<table class="table table-bordered table-hover" style="text-align:center;border:1px solid #dddddd;">
			<thread>
				<tr>
					<th colspan="2"><h4>검색으로 친구찾기</h4></th>
				</tr>
			</thread>
			<tbody>
				<tr>
					<td style="width: 110px;"><h5>친구 아이디</h5></td>
					<td><input class="form-control" type="text" id="findID" maxlength="20" placeholder="찾을 아이디를 입력하세요."/></td>
				</tr>
				<tr>
					<td colspan="2"><button class="btn btn-primary pull-right" onclick="findFunction();">찾기</button></td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="container">
		<table id="friendResult" class="table table-bordered table-hover" style="text-align: center; border: 1px solid #dddddd;">
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
	<div class="modal fade" id="checkModal" tabindex="-1" role="dialog" arid-hidden="true">
		<div class="vertical-alignment-helper">
			<div class="modal-dialog vertical-align-center">
				<div id="checkType" class="modal-content panel-info">
					<div class="modal-header panel-heading">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times</span>
							<span class="sr-only">Close</span>
						</button>
						<h4 class="modal-title">
							확인 메세지
						</h4>
					</div>
					<div id="checkMessage" class="modal-body">
					<p></p>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary" data-dismiss="modal">확인</button>
					</div>
				</div>
			</div>
		</div>
	</div>
		<c:if test="${userID != null }">
		<script type="text/javascript">
			$(document).ready(function(){
				getUnread();
				getInfiniteUnread();
			});
		</script>
	</c:if>
</body>
</html>