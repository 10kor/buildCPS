<%@ include file="header.jsp"%>

<ul class="nav justify-content-center">
	<c:if test="${sessionScope.md == null }">
		<a class="nav-link" href=/noticeView>aboutCPS</a>
		<a class="nav-link" href="/qna_list">QNA</a>
		<a class="nav-link" href="/">Login</a>
		<a class="nav-link" href="join">Sign</a>
	</c:if>
	<c:if test="${sessionScope.md != null }">
		<a class="nav-link" href=/noticeView>aboutCPS</a>
		<a class="nav-link" href="/qna_list">QNA</a>
		<a class="nav-link" href="/mypage">Account</a>
		<a class="nav-link" href="/logout">Logout</a>
	</c:if>
</ul>
<hr>