<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>mypage</title>
	<script src="http://code.jquery.com/jquery-latest.js"></script>
	<script type="text/javascript" src="./resources/js/member_mypage.js"/></script>
 	<script>
	
	</script>
	
	<style>
    .input-group-text {
        width: 120px;
    }
	</style>
	<%@ include file="navigation.jsp"%>
</head>
<body>
	<br>
	<div class="container text-center">
	<div class="row">
	    <div class="col-lg-2"></div>
	    <div class="col-lg-8">
			<h3>회원정보</h3>
		    <table class="table">
		    	<thead>
					<tr class="table-light">
						<th scope="col" style="text-align: center;">ID</th>
						<th scope="col" style="text-align: center;">회사명</th>
						<th scope="col" style="text-align: center;">담당자명</th>
						<th scope="col" style="text-align: center;">Email</th>
						<th scope="col" style="text-align: center;">연락처</th>
						<th scope="col" style="text-align: center;">가입일</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td style="text-align: center;vertical-align: middle;">${members.id}</td>
						<td style="text-align: center;vertical-align: middle;">${members.company}</td>
						<td style="text-align: center;vertical-align: middle;">${members.name}</td>
						<td style="text-align: center;vertical-align: middle;">${members.mail}</td>
						<td style="text-align: center;vertical-align: middle;">${members.phone}</td>
						<td style="text-align: center;vertical-align: middle;"><fmt:formatDate value="${members.join_date}" pattern="yy년 MM월 dd일" /></td>
					</tr>
				</tbody>
		    </table>
			<br>
		    
	    	<c:if test="${empty pays}">
		    	<div class="row mb-3">
				    <div class="input-group col-sm-8 d-flex justify-content-center">
				    	<h5>❗️결제 : 내역이 없습니다.❗</h5>
				    </div>
			    </div>
			</c:if>
	    	<c:if test="${not empty pays}">
			<h3>결제현황</h3>
		    <table class="table">
		    	<thead>
					<tr class="table-light">
						<th scope="col" style="text-align: center;">등록가능 PC수</th>
						<th scope="col" style="text-align: center;">시작일</th>
						<th scope="col" style="text-align: center;">종료일</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td style="text-align: center;vertical-align: middle;">${pays.pc_amount}</td>
						<td style="text-align: center;vertical-align: middle;"><fmt:formatDate value="${pays.startdate}" pattern="yy년 MM월 dd일" /></td>
						<td style="text-align: center;vertical-align: middle;"><fmt:formatDate value="${pays.enddate}" pattern="yy년 MM월 dd일" /></td>
					</tr>
				</tbody>
		    </table>
		    </c:if>
			<br>
		    
			<c:if test="${empty pcs}">
		    	<div class="row mb-3">
				    <div class="input-group col-sm-8 d-flex justify-content-center">
				    	<h5>❗등록된 pc가 존재하지 않습니다.❗</h5>
				    </div>
			    </div>
			</c:if>
			<c:if test="${not empty pcs}">
		    <h3>등록 PC</h3>
			<table class="table">
				<thead>
					<tr class="table-light">
						<th scope="col" style="text-align: center;">pc이름</th>
						<th scope="col" style="text-align: center;">등록일</th>
						<th scope="col" colspan="2" style="text-align: center;">등록상태</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="v" items="${pcs}">
						  <tr>
						    <c:if test="${v.status eq 0}">
						      <td style="text-align: center;vertical-align: middle;">${v.host_name}</td>
						      <td style="text-align: center;vertical-align: middle;">
						        <fmt:formatDate value="${v.registration}" pattern="yy.MM.dd HH:mm:ss" /></td>
						      <td style="text-align: right;vertical-align: middle;">등록된 PC</td>
						      <td style="text-align: center;vertical-align: middle;">
						        <button type="button" class="btn btn-outline-secondary btn-sm" onclick="pc_drop('${v.host_name}', '${members.id}')">등록취소</button>
						      </td>
						    </c:if>
						    <c:if test="${v.status eq 1}">
						      <td style="text-align: center;vertical-align: middle;">${v.host_name}</td>
						      <td style="text-align: center;vertical-align: middle;">
						        <fmt:formatDate value="${v.registration}" pattern="yy.MM.dd HH:mm:ss" /></td>
						      <td colspan="2" style="text-align: center;vertical-align: middle;">등록취소된 PC입니다.</td>
						    </c:if>
						  </tr>
						</c:forEach>
						
					<tr>
						<td class="table-light" colspan="4" style="text-align: center;vertical-align: middle;">❗️️한번 등록취소된 PC는 되돌릴 수 없습니다❗</td>
					</tr>
				</tbody>
			</table>
			</c:if>
			<br>
			<div>
				<input type="button" class="btn btn-outline-warning" onClick="location.href='/memberupdate'" value="정보수정">
			    <input type="button" class="btn btn-outline-danger" onclick="member_drop('${members.id}')" value="회원탈퇴">
		    </div>
	    </div>
	    <div class="col-lg-2"></div>
	</div>
	</div>
	<br><br><br><br>
</body>
</html>