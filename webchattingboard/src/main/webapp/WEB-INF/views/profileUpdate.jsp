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
					<li><span><h3>${userID}</h3>님</span></li>
					<li class="dropdown">
						<a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="buton" aria-haspopup="true"
						aria-expanded="false">접속하기<span class="caret"></span>
						</a>
						<ul class="dropdown-menu">
							<li ><a href="${path }/update.do?userID=<c:out value="${userID}"/>">회원정보수정</a></li>
							<li class=""><a href="${path }/profileUpdate.do?userID=<c:out value="${userID}"/>">프로필 수정</a></li>
							<li><a href="${path }/logoutAction.do">로그아웃</a></li>
						</ul>
					</li>
			</ul>
		</div>
	</nav>
	<div class="container">
		<form method="POST" action="${path }/profileUpdateEnd.do" enctype="multipart/form-data">
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
						<td style="width:110px"><h5>사진 업로드</h5>
							<td colspan="2">
								<span class="btn btn-default btn-file">
									<input  class="custom-file-input" type="file" id="profile" name="profile" accept="image/*" />
									<label class="custom-file-label" for="profile">이미지를 업로드 하세요</label>
									
								</span>
							</td>
					</tr>
					<tr>
						<td style="width:110px"><h5>이미지</h5>
						<td id="imagePreview"></td>
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
					<div class="modal-content ${messageType.equals('오류메시지')?'panel-warning':'panel-success' }">
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
<script type="text/javascript">


$(function(){
	$("[name=profile]").on('change',function(e){
		var filename=$(this).prop('files')[0].name;
		$(this).next(".custom-file-label").html(filename);
		setThumbnail(e);
	});
});
//이미지 미리보기 함수 
function setThumbnail(event) { 
	var reader = new FileReader(); 
	reader.onload = function(event) { 
		var img = document.createElement("img");
		img.setAttribute("src", event.target.result); 
		document.querySelector("#imagePreview").appendChild(img);
		};
		reader.readAsDataURL(event.target.files[0]); 
		}


/* function showUpImg(e){
	let sel_files=[];
	
	$("#imagePreview").empty();
	let file=e.target.files;
	let fileArr=Array.prototype.slice.call(file);
	let index = 0;
	fileArr.forEach((e)=>{
		if(!f.type.match("image/.*")){
			alert("이미지 확장자만 업로드 가능합니다.");
			return;
		};
		if(file.length < 2){
			sel_files.push(f);
			let reader= new FileReader();
			reader.onlead = (e)=> {
				let html='<a id=img_id${index}><img src=${e.target.result} data-file=${f.name}/></a>';
				$("#imagePreview").append(html);
				index++;
			};
			reader.readAsDataURL(f);
		}
	});
	if(file.length >= 2){
		alert("최대 1장만 업로드할수있습니다.");
	}
} */

</script>
</html>