<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
     <c:set var="path" value="${pageContext.request.contextPath }"/>
<jsp:include page="/WEB-INF/views/common/header.jsp">
	<jsp:param name="title" value=" "/>
</jsp:include>

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
<div class="container">
	<table class="table table-bordered table-hover" style="text-align: center; border: 1px solid #dddddd">
		<thead>
			<tr>
				<td colspan="5"><h4>자유게시판</h4></td>
			</tr>
			<tr>
				<th style="background-color:#fafafa; color:#000000;width: 70px;"><h5>번호</h5></th>
				<th style="background-color:#fafafa; color:#000000;"><h5>제목</h5></th>
				<th style="background-color:#fafafa; color:#000000;"><h5>작성자</h5></th>
				<th style="background-color:#fafafa; color:#000000;width: 100px;"><h5>작성 날짜</h5></th>
				<th style="background-color:#fafafa; color:#000000;width: 70px;"><h5>조회수</h5></th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>1</td>
				<td>Hello!</td>
				<td>홍길동</td>
				<td>2018-01-01</td>
				<td>1</td>
			</tr>
			<tr>
				<td colspan="5"><a href="${path }/boardWrite.do" class="btn btn-primary pull-right" type="submit">글쓰기</a></td>
			</tr>
		</tbody>
	</table>
</div>
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
				getInfiniteUnread();
			});
		</script>
	</c:if>
</body>
</html>