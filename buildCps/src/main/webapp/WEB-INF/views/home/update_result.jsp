<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11.7.12/dist/sweetalert2.all.min.js"></script>
	<link href="https://cdn.jsdelivr.net/npm/sweetalert2@11.7.12/dist/sweetalert2.min.css"rel="stylesheet">
	<meta charset="UTF-8">
	<title>login_error</title>
</head>
<body>
<c:if test="${result == 1}">
	<script>
		Swal.fire	({
			title: '업데이트 실패',
		    icon: 'error',
		    showConfirmButton: false,
		    timer: 1500,
		    timerProgressBar: true
		    }).then(() => {
		    	history.go(-1);
		    })
	</script>
</c:if>
<c:if test="${result == 2}">
	<script>
		Swal.fire	({
			title: '비밀번호가 틀렸습니다.',
		    icon: 'error',
		    showConfirmButton: false,
		    timer: 1500,
		    timerProgressBar: true
		    }).then(() => {
		    	history.go(-1);
		    })
	</script>
</c:if>
</body>