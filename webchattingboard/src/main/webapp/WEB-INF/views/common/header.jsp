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
     					return;
     				}else{
     				 	console.log(""+result);
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
				<li ><a href="${path }/find.do">친구찾기</a></li>
				<li ><a href="${path }/chat.do">채팅</a></li>
				<li ><a href="${path }/box.do">메시지함<span id="unread" class="label label-info"></span></a></li>
				<li class="active"><a href="${path }/boardView.do">자유게시판</a></li>
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
								<li><a href="${path }/update.do?userID=<c:out value="${userID}"/>">회원정보수정</a></li>
								<li class=""><a href="${path }/profileUpdate.do?userID=<c:out value="${userID}"/>">프로필 수정</a></li>
								<li><a href="${path }/logoutAction.do">로그아웃</a></li>
						
							</ul>
						</li>
					</ul>
			</c:if>
		</div>
	</nav>
	<c:if test="${userID != null }">
		<script type="text/javascript">
			$(document).ready(function(){
				getInfiniteUnread();
			});
		</script>
	</c:if>