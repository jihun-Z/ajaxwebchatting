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
		if(userID != null ){
			session.setAttribute("messageType","오류메세지");
			session.setAttribute("messageContent","현재 로그인이 되어 있는 상태입니다.");
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
	
</script>
<body>
	<c:set value="${userID}" var="uerID"/>
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
				<li class="active"><a href="${path }/index.do">메인</a></li>
				<li class="active"><a href="${path }/find.do">친구찾기</a></li>
			</ul>
			<c:if test="${userID == null }">
				<ul class="nav navbar-nav navbar-right">
						<li class="dropdown">
							<a href="#" class="dropdown-toggle"
							data-toggle="dropdown" role="buton" aria-haspopup="true"
							aria-expanded="false">접속하기<span class="caret"></span>
							</a>
							<ul class="dropdown-menu">
								<li><a href="${path }/login.jsp">로그인</a></li>
								<li><a href="${path }/home.jsp">회원가입</a></li>
							</ul>
						</li>
				</ul>
			</c:if>
		</div>
	</nav>
	<div class="container">
		<form method="post" action="${path }/userlogin.do">
			<table class="table table-bordered table-hover" style="text-align:center; border:1px solid #dddddd;">
				<thead>
					<tr>
						<th colspan="2"><h4>로그인 양식</h4></th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td style="width:110px;"><h5>아이디</h5></td>
						<td><input class="form-control" type="text" name="userID" maxlength="20" placeholer="아이디를  입력하세요."/></td>
					</tr>
					<tr>
						<td style="width:110px;"><h5>비밀번호</h5></td>
						<td><input class="form-control" type="password" name="userPassword" maxlength="20" placeholer="비밀번호를  입력하세요."/></td>
					</tr>
					<tr>
						<td style="text-align: left;" colspan="2"><input class="btn btn-primary pull-right" type="submit" value="로그인"/></td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
<%-- <%
	String messageContent = null;
	if(session.getAttribute("messageContent") != null){
		messageContent = (String) session.getAttribute("messageContent");
		
	}
	String messageType= null;
	if(session.getAttribute("messageType") != null ){
		messageType=(String) session.getAttribute("messageType");
	}
%> --%>
<c:set value="${messageContent }" var="messageContent"/>
<c:set value="${messageType }" var="messageType"/>
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
</body>

</html>