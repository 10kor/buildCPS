<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
	<title>aboutCPS</title>
	<script type="text/javascript" src="./resources/js/qna.js"/></script>
	<script src="//cdn.quilljs.com/1.3.6/quill.js"></script>
	<link href="//cdn.quilljs.com/1.3.6/quill.bubble.css" rel="stylesheet" >
	<script src="http://code.jquery.com/jquery-latest.js"></script>
	<%@ include file="navigation.jsp"%>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-lg-1"></div>
			<div class="col-lg-10">
					<div id="editor" style="border: 0px solid #DCDCDC;padding: 20px;">${noticeview.ncontent}</div>
			</div>
			<div class="col-lg-1"></div>
		</div>
	</div>
</body>
<script>
	var quill = new Quill('#editor', {
		readOnly: true,
		theme: 'bubble'
	});
</script>
</html>
