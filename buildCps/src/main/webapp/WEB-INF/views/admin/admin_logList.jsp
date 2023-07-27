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
			<h3>전체 CPS 접속기록</h3>
			<table class="table">
				<thead>
					<tr class="table-light">
						<th scope="col" style="text-align: center;vertical-align: middle;">아이디</th>
						<th scope="col" style="text-align: center;vertical-align: middle;">유저명</th>
						<th scope="col" style="text-align: center;vertical-align: middle;">단말기</th>
						<th scope="col" style="text-align: center;vertical-align: middle;">IP주소</th>
						<th scope="col" style="text-align: center;vertical-align: middle;">접속시간</th>
						<th scope="col" style="text-align: center;vertical-align: middle;">접속결과</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${empty logs}">
						<tr>
							<td colspan="5" style="text-align: center;vertical-align: middle;">CPS 접속기록이 존재하지 않습니다.</td>
						</tr>
					</c:if>
					<c:if test="${not empty logs}">
					<c:forEach var="v" items="${logs}">
						  <tr>
						      <td style="text-align: center;vertical-align: middle;">
						      		<a href="admin_user?id=${v.id}&has=${v.hasPayment}&pcCount=${v.pcCount}">${v.id}</a></td>
						      <td style="text-align: center;vertical-align: middle;">${v.user_name}</td>
						      <td style="text-align: center;vertical-align: middle;">${v.host_name}</td>
						      <td style="text-align: center;vertical-align: middle;">${v.ip_address}</td>
						      <td style="text-align: center;vertical-align: middle;">
						        <fmt:formatDate value="${v.registration}" pattern="yy.MM.dd(HH:mm)" /></td>
						        <c:if test="${v.status == 0}">
						        	<td style="text-align: left;vertical-align: middle;">
						        		[ OK :: 등록된 PC ]
						        	</td>
						        </c:if>
						        <c:if test="${v.status == 1}">
						        	<td style="text-align: left;vertical-align: middle;">
						        		[ ERROR :: 잘못된 ID ]
						        	</td>
						        </c:if>
						        <c:if test="${v.status == 2}">
						        	<td style="text-align: left;vertical-align: middle;">
						        		[ ERROR :: 결제기간 OUT ]
						        	</td>
						        </c:if>
						        <c:if test="${v.status == 3}">
						        	<td style="text-align: left;vertical-align: middle;">
						        		[ ERROR :: 취소이력 PC ]
						        	</td>
						        </c:if>
						        <c:if test="${v.status == 4}">
						        	<td style="text-align: left;vertical-align: middle;">
						        		[ ERROR :: PC수량 OVER ]
						        	</td>
						        </c:if>
						        <c:if test="${v.status == 9}">
						        	<td style="text-align: left;vertical-align: middle;">
						        		[ OK :: PC등록 후 GO ]
						        	</td>
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
						 <li class="page-item"><a class="page-link" href="admin_logList?pageNum=${pp.startPage - 1}">이전</a></li>
					</c:if>
			
					<c:forEach var="i" begin="${pp.startPage}" end="${pp.endPage}">
						<c:if test="${pp.currentPage==i}"></c:if>
						 <li class="page-item"><a class="page-link" href="admin_logList?pageNum=${i}">${i}</a></li>
					</c:forEach>
			
					<c:if test="${pp.endPage < pp.totalPage}">
						 <li class="page-item"><a class="page-link" href="admin_logList?pageNum=${pp.endPage + 1}">다음</a></li>
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