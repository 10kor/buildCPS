<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Join</title>
	<script src="http://code.jquery.com/jquery-latest.js"></script>
	<script type="text/javascript" src="./resources/js/member_check.js"/></script>
	<style>
    .input-group-text {
        width: 120px;
    }
	</style>
</head>
<body>
	<br><br><br><br>
	<div class="container text-center">
    <div class="row">
        <div class="col-lg-12">
            <h1>회원가입</h1>
            <br><br>
        </div>
    </div>
    <div class="row">
        <div class="col-lg-3"></div>
        <div class="col-lg-6">
            <form method="post" action="join_action" onsubmit="return check()">
                <div class="row mb-3">
                    <div class="input-group justify-content-between">
                        <span class="input-group-text">ID</span>
                        <input type="text" class="form-control" id="id" name="id" placeholder="아이디 입력" >
                        <input type="button" value="중복체크" onclick="id_check()" class="btn btn-secondary">
                    </div>
                    <div id="idchecking" class="valid-feedback"></div>
                </div>
                <div class="row mb-3">
                    <div class="input-group">
                        <span class="input-group-text">비밀번호</span>
                        <input type="password" class="form-control" id="passwd" name="passwd" placeholder="비밀번호 입력">
                    </div>
                </div>
                <div class="row mb-3">
                    <div class="input-group">
                        <span class="input-group-text">비밀번호 확인</span>
                        <input type="password" class="form-control" id="passwdcheck" name="passwdcheck" placeholder="비밀번호 확인">
                    </div>
                </div>
                <div class="row mb-3">
                    <div class="input-group">
                        <span class="input-group-text">회사명</span>
                        <input type="text" class="form-control" id="company" name="company" placeholder="회사명 입력">
                    </div>
                </div>
                <div class="row mb-3">
                    <div class="input-group">
                        <span class="input-group-text">담당자 이름</span>
                        <input type="text" class="form-control" id="name" name="name" placeholder="이름 입력">
                    </div>
                </div>
                <div class="row mb-3">
                    <div class="input-group">
                        <span class="input-group-text">메일</span>
                        <input type="text" class="form-control" id="mail" name="mail" placeholder="이메일 입력">
                    </div>
                </div>
                <div class="row mb-3">
                    <div class="input-group">
                        <span class="input-group-text">Phone</span>
                        <input type="text" class="form-control" id="phone" name="phone" placeholder="연락가능 번호 입력">
                    </div>
                </div>
                <div class="row mb-3">
                    <input type="submit" value="작성완료" class="btn btn-outline-warning btn-sm">
                </div>
            </form>
        </div>
        <div class="col-lg-3"></div>
    </div>
</div>

</body>
</html>