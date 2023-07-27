<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="header.jsp"%>
<ul class="nav justify-content-center">
	<c:if test="${sessionScope.admin != null }">
		<a class="nav-link" href="/admin_noticeUpdate">aboutCPS</a>
		<a class="nav-link" href="/admin_userList">Client</a>
		<a class="nav-link" href="/admin_logList">CpsLog</a>
		<a class="nav-link" href="/bfileList">BFile</a>
		<a class="nav-link" href="/qna_list">QNA</a>
		<a class="nav-link" href="/">Logout</a>
	</c:if>
</ul>
<hr>