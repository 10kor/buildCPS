<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
	<title>NOTICE_update</title>
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
			<h3 align=center>aboutCPS 수정</h3>
			<h5 align=center>작성일 : <fmt:formatDate value="${list.ndate}" pattern="yyyy년 MM월 dd일 (a HH:mm:ss) 작성" /> | 조회수 : ${list.ncount}</h5>
			<br>
			<form method="post" action="admin_noticeUpdate_action">
				<textarea style="display: none" id="ncontent" name="ncontent" required>${list.ncontent}</textarea>
 				<div id="editor">${list.ncontent}</div>
				<br>
				<input type="submit" value='작성완료' class="btn btn-warning btn-sm">
			</form>
		</div>
	</div>
	</div>
</body>
<script type="text/javascript" src="./resources/js/quill.js"/></script>
</html>