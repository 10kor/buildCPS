<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<c:set var="path" value="${pageContext.request.contextPath }" />
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
		<script type="text/javascript" src="./resources/js/qna_add.js"/></script>
	<meta charset="UTF-8">
	<title>QNA_update_action</title>
	<%@ include file="header.jsp"%>
</head>
<body>
	<c:if test="${result == 1 }">
		<script>
			Swal.fire	({
				title: '수정 성공',
			    icon: 'success',
			    showConfirmButton: false,
			    timer: 1500,
			    timerProgressBar: true
			    }).then(() => {
			    	location.href = "qna_view?qno=${qno}&pageNum=${pageNum}";
			    })
		</script>
	</c:if>
	<c:if test="${result == 0 }">
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
	<c:if test="${result == 2 }">
		<script>
			Swal.fire	({
				title: '수정 실패',
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
</html>