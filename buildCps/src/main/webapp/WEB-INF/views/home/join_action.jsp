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
	<title>join_after</title>
</head>
<body>

	<c:if test="${result == 1}">
		<script>
		Swal.fire	({
				title: '가입이 완료되었습니다.',
			    icon: 'success',
			    showConfirmButton: false,
				timer: 1200,
				timerProgressBar: true
				}).then(() => {
					location.href = "/";
			    })
		</script>
	</c:if>
	<c:if test="${result != 1}">
		<script>
			Swal.fire	({
				title: '실패',
			    icon: 'error',
			    showCancelButton: false,
			    confirmButtonText: '확인'
				}).then((result) => {
			    	history.go(-1);
			    })
		</script>
	</c:if>

</body>
</html>