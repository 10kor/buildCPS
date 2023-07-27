<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>QNA_add</title>
	<script src="http://code.jquery.com/jquery-latest.js"></script>
	<script src="//cdn.quilljs.com/1.3.6/quill.js"></script>
	<link href="https://cdn.quilljs.com/1.3.6/quill.snow.css" rel="stylesheet">
	<script type="text/javascript" src="./resources/js/qna.js"/></script>
	<style>
    .input-group-text {
        width: 100px;
    }
	</style>
	<%@ include file="navigation.jsp"%>
</head>
<body> 
	<div class="container text-center">
	<div class="row">
	    <div class="col-lg-1"></div>
	    <div class="col-lg-10">
			<h3>게시물 작성</h3>
			<br>
			<form method="post" action="qna_add_action">
				<input type="hidden" id="id" name="id" value="${id}"/>
				<div class="input-group">
				  <span class="input-group-text">제목</span>
				  <input type="text" id="qsubject" name="qsubject" class="form-control" aria-label="With textarea" placeholder="제목 입력" required>
				</div>
				<br>
                <div class="input-group">
                    <span class="input-group-text">비밀번호</span>
                    <input type="password" class="form-control" id="qpasswd" name="qpasswd" placeholder="비밀번호 입력" required>
                </div>
				<br>
 				<div id="editor"></div>
					<textarea style="display: none" id="qcontent" name="qcontent"></textarea>
				<br>
				<input type="submit" value='작성' class="btn btn-warning btn-sm">
			</form>
		</div>
	    <div class="col-lg-1"></div>
	</div>
	</div>
</body>
<script type="text/javascript" src="./resources/js/quill.js"/></script>
</html>