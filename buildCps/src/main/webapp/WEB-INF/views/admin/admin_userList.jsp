<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>admin_userList</title>
	<script src="http://code.jquery.com/jquery-latest.js"></script>
	<style>
    .input-group-text {
        width: 120px;
    }
	</style>
	<%@ include file="navigation.jsp"%>
</head>
<body>
	<div class="container text-center">
	<div class="row">
	    <div class="col-lg-1"></div>
		<div class="col-lg-10">
			<h3>고객 리스트</h3>
			<table class="table">
				<thead>
					<tr class="table-light">
						<th scope="col" style="text-align: center;vertical-align: middle;">아이디</th>
						<th scope="col" style="text-align: center;vertical-align: middle;">회사명</th>
						<th scope="col" style="text-align: center;vertical-align: middle;">담당자</th>
						<th scope="col" style="text-align: center;vertical-align: middle;">Email</th>
						<th scope="col" style="text-align: center;vertical-align: middle;">연락처</th>
						<th scope="col" style="text-align: center;vertical-align: middle;">가입일</th>
						<th scope="col" style="text-align: center;vertical-align: middle;">결제(PC)</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${empty members}">
						<tr>
							<td colspan="8" style="text-align: center;vertical-align: middle;">등록된 고객리스트가 존재하지 않습니다.</td>
						</tr>
					</c:if>
					<c:if test="${not empty members}">
					<c:forEach var="v" items="${members}">
						  <tr>
						    <c:if test="${v.drop_date == null}">
						    <c:if test="${v.hasPayment eq true}">
						      <td style="text-align: center;vertical-align: middle;">
						      		<a href="admin_user?id=${v.id}&has=${v.hasPayment}">${v.id}</a></td>
						      <td style="text-align: center;vertical-align: middle;">${v.company}</td>
						      <td style="text-align: center;vertical-align: middle;">${v.name}</td>
						      <td style="text-align: center;vertical-align: middle;">${v.mail}</td>
						      <td style="text-align: center;vertical-align: middle;">${v.phone}</td>
						      <td style="text-align: center;vertical-align: middle;">
						        <fmt:formatDate value="${v.join_date}" pattern="yy.MM.dd(HH:mm)" /></td>
						      <td style="text-align: center;vertical-align: middle;">O (${v.pcCount})</td>
						    </c:if>
						    <c:if test="${v.hasPayment eq false}">
						      <td style="text-align: center;vertical-align: middle;">
						      		<a href="admin_user?id=${v.id}&has=${v.hasPayment}">${v.id}</a></td>
						      <td style="text-align: center;vertical-align: middle;">${v.company}</td>
						      <td style="text-align: center;vertical-align: middle;">${v.name}</td>
						      <td style="text-align: center;vertical-align: middle;">${v.mail}</td>
						      <td style="text-align: center;vertical-align: middle;">${v.phone}</td>
						      <td style="text-align: center;vertical-align: middle;">
						        <fmt:formatDate value="${v.join_date}" pattern="yy.MM.dd(HH:mm)" /></td>
						      <td style="text-align: center;vertical-align: middle;">X</td>
						    </c:if>
						    </c:if>
						    <c:if test="${v.drop_date != null}">
						      <td style="text-align: center;vertical-align: middle;"
						      		class="table-light">${v.id}</td>
						      <td style="text-align: center;vertical-align: middle;"
						      		class="table-light"colspan="6">탈퇴한 고객입니다.</td>
						    </c:if>
						  </tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>

			<!-- 페이징 처리 -->
			<div class="d-flex justify-content-center">
			<div id="save" style="display:block; visibility: visible;" align="center">
				<ul class="pagination">
					<c:if test="${pp.startPage > pp.pagePerBlk }">
						 <li class="page-item"><a class="page-link" href="admin_userList?pageNum=${pp.startPage - 1}">이전</a></li>
					</c:if>
			
					<c:forEach var="i" begin="${pp.startPage}" end="${pp.endPage}">
						<c:if test="${pp.currentPage==i}"></c:if>
						 <li class="page-item"><a class="page-link" href="admin_userList?pageNum=${i}">${i}</a></li>
					</c:forEach>
			
					<c:if test="${pp.endPage < pp.totalPage}">
						 <li class="page-item"><a class="page-link" href="admin_userList?pageNum=${pp.endPage + 1}">다음</a></li>
					</c:if>
				</ul>
			</div>
			</div>
			
		</div>
	    <div class="col-lg-1"></div>
	</div>
	</div>
	<br><br><br><br>
</body>
</html>