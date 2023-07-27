<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
	<title>QNA_update</title>
	<script src="http://code.jquery.com/jquery-latest.js"></script>
	<script src="//cdn.quilljs.com/1.3.6/quill.js"></script>
	<link href="https://cdn.quilljs.com/1.3.6/quill.snow.css" rel="stylesheet">
	<%@ include file="navigation.jsp"%>
</head>
<body>
	<div class="container text-center">
	<div class="row">
	    <div class="col-lg-1"></div>
	    <div class="col-lg-10">
			<h3 align=center>게시물 수정</h3>
			<br>
			<form method="post" action="qna_update_action">
				<input type="hidden" id="id" name="id" value="${id}"/>
				<input type="hidden" id="qno" name="qno" value="${list.qno}"/>
				<input type="hidden" id="pageNum" name="pageNum" value="${pageNum}"/>
				<textarea style="display: none" id="qcontent" name="qcontent" required>${list.qcontent}</textarea>
				<div class="input-group">
				  <span class="input-group-text">제목</span>
				  <input type="text" id="qsubject" name="qsubject" 
				  	class="form-control" aria-label="With textarea" value="${list.qsubject}" required>
				</div>
				<br>
 				<div id="editor">${list.qcontent}</div>
				<br>
                <div class="input-group">
                    <span class="input-group-text">비밀번호</span>
                    <input type="password" class="form-control" id="qpasswd" name="qpasswd" placeholder="게시물 비밀번호를 입력해주세요." required>
                </div>
				<br>
				<input type="submit" value='작성' class="btn btn-warning btn-sm">
			</form>
		</div>
	</div>
	</div>
</body>
<script type="text/javascript" src="./resources/js/quill.js"/></script>
</html>