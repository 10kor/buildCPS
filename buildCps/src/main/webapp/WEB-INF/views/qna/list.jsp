<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>QNA_list</title>
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
			<h3>QnA 게시판</h3>
			<br>
			<table class="table">
				<thead>
					<tr class="table-light">
						<th scope="col" style="text-align: center;vertical-align: middle;">no</th>
						<th scope="col" style="text-align: center;vertical-align: middle;">제목</th>
						<th scope="col" style="text-align: center;vertical-align: middle;">작성일</th>
						<th scope="col" style="text-align: center;vertical-align: middle;">작성자</th>
						<th scope="col" style="text-align: center;vertical-align: middle;">조회수</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${empty qnaList}">
						<tr>
							<td colspan="5" style="text-align: center;vertical-align: middle;">QNA 게시글이 없습니다.</td>
						</tr>
					</c:if>
					<c:if test="${not empty qnaList}">
					<c:forEach var="v" items="${qnaList}">
						<c:if test="${v.qdeldate eq null}">
							<tr>
						      <td style="text-align: center;vertical-align: middle;">${v.qno}</td>
						      <c:if test="${v.qreply eq 0}">
						      	<td style="text-align: left;vertical-align: middle;">
						      		<a href="qna_view?qno=${v.qno}&pageNum=${pp.currentPage}">${v.qsubject}</a>
						      	</td>
						      </c:if>
						      <c:if test="${v.qreply ne 0}">
							      <td style="text-align: left;vertical-align: middle;">
							      	<a href="qna_view?qno=${v.qno}&pageNum=${pp.currentPage}">${v.qsubject}</a><a style="font-size: 13px;">&nbsp;[${v.qreply}]</a>
							      </td>
						      </c:if>
						      <td style="text-align: center;vertical-align: middle;">
						        <fmt:formatDate value="${v.qdate}" pattern="yy.MM.dd" /></td>
						      <td style="text-align: center;vertical-align: middle;">${v.id}</td>
						      <td style="text-align: center;vertical-align: middle;">${v.qcount}</td>
							</tr>
						</c:if>
						<c:if test="${v.qdeldate ne null}">
							<tr>
						   		<td style="text-align: center;vertical-align: middle;">${v.qno}</td>
								<td colspan="4" style="text-align: center;vertical-align: middle;">삭제된 게시글 입니다.</td>
							</tr>
						</c:if>
					</c:forEach>
					</c:if>
				</tbody>
			</table>
			
			<!-- 글쓰기 버튼 -->
			<div class="d-flex justify-content-end">
				<c:if test="${not empty sessionScope.md}">
					<input 	type="button" value='글쓰기' onclick="location.href='/qna_add'" 
						class="btn btn-warning btn-sm" style="float:right;">
				</c:if>
			</div>
			
			<!-- 페이징 처리 -->
			<div class="d-flex justify-content-center">
			<div id="save" style="display:block; visibility: visible;" align="center">
				<ul class="pagination">
					<c:if test="${pp.startPage > pp.pagePerBlk }">
						 <li class="page-item"><a class="page-link" href="qna_list?pageNum=${pp.startPage - 1}">이전</a></li>
					</c:if>
			
					<c:forEach var="i" begin="${pp.startPage}" end="${pp.endPage}">
						<c:if test="${pp.currentPage==i}"></c:if>
						 <li class="page-item"><a class="page-link" href="qna_list?pageNum=${i}">${i}</a></li>
					</c:forEach>
			
					<c:if test="${pp.endPage < pp.totalPage}">
						 <li class="page-item"><a class="page-link" href="qna_list?pageNum=${pp.endPage + 1}">다음</a></li>
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