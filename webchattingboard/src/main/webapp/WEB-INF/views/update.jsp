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
</head>
<script type="text/javascript">
	function passwordCheckFunction(){
		let userPassword1 = $("#userPassword").val(); 
		let userPassword2 = $("#userPassword1").val(); 
		if(userPassword1 != userPassword2 ){
			$("#passwordCheckMessage").html("비밀번호가 서로 일치하지않습니다.");
			
		}else{
			$('#passwordCheckMessage').html('');
		}
	}
</script>
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
				<li class=""><a href="${path }/boardView.do">자유게시판</a></li>
			</ul>
			
			<ul class="nav navbar-nav navbar-right">
					<li><span><h3>${user.userName }</h3>님</span></li>
					<li class="dropdown">
						<a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="buton" aria-haspopup="true"
						aria-expanded="false">접속하기<span class="caret"></span>
						</a>
						<ul class="dropdown-menu">
							<li class="active"><a href="${path }/update.do?userID=<c:out value="${userID}"/>">회원정보수정</a></li>
							<li class=""><a href="${path }/profileUpdate.do?userID=<c:out value="${userID}"/>">프로필 수정</a></li>
							<li><a href="${path }/logoutAction.do">로그아웃</a></li>
						</ul>
					</li>
			</ul>
		</div>
	</nav>
	<div class="container">
		<form method="post" action="${path }/updateEnd.do">
			<table class="table table-boardered table-hover" style="textalign:center; border:1px solid #dddddd">
				<thead>
					<tr>
						<th colspan="2"><h4>회원등록양식</h4> 
						</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td style="width:110px"><h5>아이디</h5>
						<td><input class="form-control" type="text" id="userID" name="userID" maxlength="20" placeholder="아이디를 입력하세요" value="${user.userID }" required readOnly/></td>
					</tr>
					<tr>
						<td style="width:110px"><h5>비밀번호</h5>
						<td colspan="2"><input onkeyup="passwordCheckFunction();" class="form-control" type="password" id="userPassword" name="userPassword" maxlength="20" placeholder="비밀번호를 입력하세요" value=""/></td>
					</tr>
					<tr>
						<td style="width:110px"><h5>비밀번호 확인</h5>
						<td colspan="2"><input onkeyup="passwordCheckFunction();" class="form-control" type="password" id="userPassword1" name="userPassword1" maxlength="20" placeholder="비밀번호 확인 를 입력하세요" value=""/></td>
					</tr>
					<tr>
						<td style="width:110px"><h5>이름</h5>
						<td colspan="2"><input  class="form-control" type="text" id="userName" name="userName" maxlength="20" placeholder="이름을 입력하세요" value=" ${user.userName }"/></td>
					</tr>
					<tr>
						<td style="width:110px"><h5>나이</h5>
						<td colspan="2"><input  class="form-control" type="text" id="userAge" name="userAge" maxlength="20" placeholder="나이을 입력하세요" value="${user.userAge }"/></td>
					</tr>
					<tr>
						<td style="width:110px"><h5>성별</h5>
						<td colspan="2">
							<div class="form-group" style="text-align:center ;margin: 0 auto;">
								<div class="btn-group" data-toggle="buttons">
									<label class="btn btn-primary active" ${user.userGender =='남자'?active:"" }>
										<input type="radio" name="userGender" autocomplete="off" value="남자" ${user.userGender =='남자'?checked:"" }>남자
									</label>
									<label class="btn btn-primary active" ${user.userGender =='여자'?active:"" }>
										<input type="radio" name="userGender" autocomplete="off" value="여자" ${user.userGender =='여자'?checked:"" }>여자
									</label>
								</div>
							</div>
						</td>
					</tr>
					<tr>
						<td style="width:110px"><h5>이메일</h5>
						<td colspan="2"><input  class="form-control" type="email" id="email" name="email" maxlength="20" placeholder="이메일을 입력하세요" value="<c:out value="${user.email }"/>"/></td>
					</tr>
					<tr>
						<td style="text-align: left;" colspan="3"><h5 style="color:red;" id="passwordCheckMessage"></h5><input class="btn btn-primary pull-right" type="submit" value="등록"></td>
					</tr>
				</tbody>
			</table>
		</form>
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
<c:if test="${messageContent != null }">
	<div class="modal fade" id="messageModal" tabindex="-1" role="dialog" arid-hidden="true">
		<div class="vertical-alignment-helper">
			<div class="modal-dialog vertical-align-center">
				<div class="modal-content <%if (messageType.equals("오류메시지")) out.println("panel-warning");else out.println("panel-success"); %>">
					<div class="modal-header panel-heading">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times</span>
							<span class="sr-only">Close</span>
						</button>
						<h4 class="modal-title">
							${messageType }
						</h4>
					</div>
					<div class="modal-body">
						${messageContent}
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary" data-dismiss="modal">확인</button>
					</div>
				</div>
			</div>
		</div>
	</div>
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
<%-- </c:if> --%>
</body>

</html>