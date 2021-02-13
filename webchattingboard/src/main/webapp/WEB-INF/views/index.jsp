<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
     <c:set var="path" value="${pageContext.request.contextPath }"/>
     
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
<body>
<%-- 	<%
		String userID= null;
		if(session.getAttribute("userID") != null){
			userID =(String) session.getAttribute("userID");
		}
	%> --%>
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
				<li class="active"><a href="index.jsp">메인</a>
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
			</c:if>
		</div>
	</nav>
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
</body>
</html>