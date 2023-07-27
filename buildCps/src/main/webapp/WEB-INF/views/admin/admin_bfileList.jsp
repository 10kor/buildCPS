<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>admin_bfileList</title>
	<script src="http://code.jquery.com/jquery-latest.js"></script>
	<script type="text/javascript" src="./resources/js/admin_bfile.js"/></script>
	<style>
    .input-group-text {
        width: 120px;
    }
	</style>
	<script type="text/javascript">
	
	</script>
	<%@ include file="navigation.jsp"%>
</head>
<body>
	<div class="container text-center">
	<div class="row">
	    <div class="col-lg-1"></div>
		<div class="col-lg-10">
			<h3>파일 리스트</h3>
			<table class="table">
				<thead>
					<tr class="table-light">
						<th scope="col" style="text-align: center;vertical-align: middle;">No</th>
						<th scope="col" style="text-align: center;vertical-align: middle;">ID</th>
						<th scope="col" style="text-align: center;vertical-align: middle;">파일이름</th>
						<th scope="col" style="text-align: center;vertical-align: middle;">등록일</th>
						<th scope="col" style="text-align: center;vertical-align: middle;">파일크기</th>
						<th scope="col" style="text-align: center;vertical-align: middle;">관리</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${empty fileList}">
						<tr>
							<td colspan="6" style="text-align: center;vertical-align: middle;">등록된 고객리스트가 존재하지 않습니다.</td>
						</tr>
					</c:if>
					<c:if test="${not empty fileList}">
					<c:forEach var="v" items="${fileList}">
						  <tr>
						      <td style="text-align: center;vertical-align: middle;">${v.bno}</td>
						      <td style="text-align: center;vertical-align: middle;">
						      		<a href="admin_user?id=${v.id}&has=true">${v.id}</a></td>
						      <td style="text-align: center;vertical-align: middle;">
						      		<a href="bfileDownload?bno=${v.bno}">${v.bname}</a></td>
						      <td style="text-align: center;vertical-align: middle;">
						        <fmt:formatDate value="${v.bdate}" pattern="yy.MM.dd(HH:mm)" /></td>
						      <td style="text-align: center;vertical-align: middle;">${v.bsize}</td>
						      <td style="text-align: center;vertical-align: middle;"> 
						      		<a href="javascript:void(0)" onclick="fileID('${v.bno}')">
						      			<c:choose>
						                    <c:when test="${empty v.id}">ID등록</c:when>
						                    <c:otherwise>ID변경</c:otherwise>
						                </c:choose>
						      		</a>&nbsp;/&nbsp;
						      		<a href="javascript:void(0)" onclick="fileDelete('${v.bno}')">삭제</a></td>
						  </tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>

			<!-- 파일등록 버튼 -->
			<div class="d-flex justify-content-end">
				<c:if test="${not empty sessionScope.md}">
					<input 	type="button" value='파일등록' onclick="fileAdd()" 
						class="btn btn-warning btn-sm" style="float:right;">
				</c:if>
			</div>
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