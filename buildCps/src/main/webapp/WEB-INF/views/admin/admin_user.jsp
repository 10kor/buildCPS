<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>admin_user</title>
	<script src="http://code.jquery.com/jquery-latest.js"></script>
	<script type="text/javascript" src="./resources/js/admin_pay.js"/></script>
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
			<h3>${members.id} :: 고객정보</h3>
			<table class="table">
				<thead>
					<tr class="table-light">
						<th scope="col" style="text-align: center;vertical-align: middle;">회사명</th>
						<th scope="col" style="text-align: center;vertical-align: middle;">담당자</th>
						<th scope="col" style="text-align: center;vertical-align: middle;">Email</th>
						<th scope="col" style="text-align: center;vertical-align: middle;">연락처</th>
						<th scope="col" style="text-align: center;vertical-align: middle;">가입일</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${empty members}">
						<tr>
							<td colspan="5" style="text-align: center;vertical-align: middle;">고객정보가 존재하지 않습니다.</td>
						</tr>
					</c:if>
					<c:if test="${not empty members}">
						<tr>
						    <td style="text-align: center;vertical-align: middle;">${members.company}</td>
						    <td style="text-align: center;vertical-align: middle;">${members.name}</td>
						    <td style="text-align: center;vertical-align: middle;">${members.mail}</td>
						    <td style="text-align: center;vertical-align: middle;">${members.phone}</td>
						    <td style="text-align: center;vertical-align: middle;">
						      <fmt:formatDate value="${members.join_date}" pattern="yy.MM.dd(HH:mm)" /></td>
						</tr>
					</c:if>
				</tbody>
			</table>
		</div>
	    <div class="col-lg-1"></div>
	</div>
	
	<br>
	
	<div class="row">
	    <div class="col-lg-1"></div>
		<div class="col-lg-10">
			<h3>${members.id} :: 결제정보</h3>
			<table class="table">
				<thead>
					<tr class="table-light">
						<th scope="col" style="text-align: center;vertical-align: middle;">PC수량</th>
						<th scope="col" style="text-align: center;vertical-align: middle;">시작일</th>
						<th scope="col" style="text-align: center;vertical-align: middle;">종료일</th>
						<th scope="col" style="text-align: center;vertical-align: middle;">계약관리</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${members.hasPayment eq true}">
						<tr>
						    <td style="text-align: center;vertical-align: middle;">${pays.pc_amount}</td>
						    <td style="text-align: center;vertical-align: middle;">
						      <fmt:formatDate value="${pays.startdate}" pattern="yy.MM.dd(HH:mm)" /></td>
						    <td style="text-align: center;vertical-align: middle;">
						      <fmt:formatDate value="${pays.enddate}" pattern="yy.MM.dd(HH:mm)" /></td>
						    <td style="text-align: center;vertical-align: middle;">
						      		<a href="javascript:void(0)" onclick="payupdate('${members.id}')">수정</a> / 
						      		<a href="javascript:void(0)" onclick="paydelete('${members.id}')">삭제</a></td>
						</tr>
					</c:if>
					<c:if test="${members.hasPayment eq false}">	
						<tr>
							<td colspan="4" style="text-align: center;vertical-align: middle;">
						      		<a href="javascript:void(0)" onclick="payinsert('${members.id}')">결제정보 생성</a></td>
						</tr>	
					</c:if>
				</tbody>
			</table>
		</div>
	    <div class="col-lg-1"></div>
	</div>		
	
	<br>
	
	<div class="row">
	    <div class="col-lg-1"></div>
		<div class="col-lg-10">
			<h3>${members.id} :: CPS 접속기록</h3>
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
							<td colspan="6" style="text-align: center;vertical-align: middle;">CPS 접속기록이 존재하지 않습니다.</td>
						</tr>
					</c:if>
					<c:if test="${not empty logs}">
					<c:forEach var="v" items="${logs}">
						  <tr>
						      <td style="text-align: center;vertical-align: middle;">${v.id}</td>
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
		</div>
	    <div class="col-lg-1"></div>
	</div>
	</div>
	<br><br><br><br>
</body>
</html>