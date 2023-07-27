<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>fusion360 cps</title>
	<script src="http://code.jquery.com/jquery-latest.js"></script>
	<script>
	 function check(){
		 if($.trim($("#id").val())==""){
			 alert("아이디를 입력하세요");
			 $("#id").val("").focus();
			 return false;
		 }
		 if($.trim($("#passwd").val())==""){
			 alert("비밀번호를 입력하세요");
			 $("#passwd").val("").focus();
			 return false;
		 }
	 }
	</script>
	<style>
    .input-group-text {
        width: 100px;
    }
	</style>
</head>
<body>
	<br><br><br><br>
	<div class="container text-center">
	<div class="row">
		 <div class="col-lg-12">
			<h1>EDNC CPS Service</h1>
			<br><br>
		 </div>
	</div>
	<div class="row" style="height: 100px;">
	    <div class="col-lg-4"></div>
	    <div class="col-lg-4">
			<form method="post" action="login" onsubmit="return check()">
				<div class="row mb-3">
				    <div class="input-group col-sm-8">
  						<span class="input-group-text">　ID　</span>
				    	<input type="text" class="form-control" id="id" name="id" placeholder="아이디 입력">
				    </div>
			    </div>
			    
				<div class="row mb-3">
					<div class="input-group col-sm-8">
  						<span class="input-group-text"> Password </span>
				    	<input type="password" class="form-control" id="passwd" name="passwd" placeholder="비밀번호 입력">
					</div>
				</div>
				<br>
				<input type="submit" value="로그인" class="btn btn-outline-primary"/>
				<input type="button" value="회원가입" class="btn btn-outline-warning" onclick="location.href='./join'"/>
			</form>
		</div>
	    <div class="col-lg-4"></div>
	</div>
	</div>
	
<br>

</body>
</html>