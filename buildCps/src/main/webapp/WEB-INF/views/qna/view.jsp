<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
	<title>QNA_view</title>
	<script type="text/javascript" src="./resources/js/qna.js"/></script>
	<script src="//cdn.quilljs.com/1.3.6/quill.js"></script>
	<link href="//cdn.quilljs.com/1.3.6/quill.bubble.css" rel="stylesheet" >
	<script src="http://code.jquery.com/jquery-latest.js"></script>
	<style>
		textarea.form-control { min-height: 50px; }
	</style>
	<script>
		function resize(obj) {
		  obj.style.height = "1px";
		  obj.style.height = (12+obj.scrollHeight)+"px";
		}
	</script>
	<%@ include file="navigation.jsp"%>
</head>
<body>
	<div class="container">
	<div class="row">
	<div class="col-lg-1"></div>
	<div class="col-lg-10">
		<c:forEach var="bv" items="${qnaview}">
			<h3 align=center>QnA</h3>
			<h3 align=left>[${bv.qno}]&nbsp;&nbsp;${bv.qsubject}</h3>
			<c:if test="${bv.qupdate eq null}">
				<div align=left>${bv.id}&nbsp;&nbsp;|&nbsp;&nbsp;<fmt:formatDate value="${bv.qdate}" pattern="yyyy년 MM월 dd일 (a HH:mm:ss) 작성" />&nbsp;&nbsp;|&nbsp;&nbsp;조회 : ${bv.qcount}</div>
			</c:if>
			<c:if test="${bv.qupdate ne null}">
				<div align=left>${bv.id}&nbsp;&nbsp;|&nbsp;&nbsp;<fmt:formatDate value="${bv.qupdate}" pattern="yyyy년 MM월 dd일 (a HH:mm:ss) 수정" />&nbsp;&nbsp;|&nbsp;&nbsp;조회 : ${bv.qcount}</div>
			</c:if>
			<div id="editor" style="border: 1px solid #DCDCDC;padding: 20px;">${bv.qcontent}</div>
			<br>
			<div align=center>
				<c:if test="${bv.id eq id}">
					<input type="button" value='수정' class="btn btn-outline-warning btn-sm" onclick="location.href='qna_update?qno=${bv.qno}&pageNum=${pageNum}'" />
					<input type="button" value='삭제' class="btn btn-outline-secondary btn-sm" onclick="qnadelete('${bv.qno}','${bv.id}')"/>
				</c:if>
			</div>
			<br><br>
			
			<c:if test="${sessionScope.md == null }">
				<form>
					<div class="input-group">
						<span class="input-group-text">댓글</span>
						<textarea class="form-control" disabled="disabled" style="resize:none;" placeholder="로그인 후 작성 가능합니다."></textarea>
						<button class="btn btn-outline-secondary" disabled>작성</button>

					</div> 
				</form>
			</c:if>
			<c:if test="${sessionScope.md != null }">
				<form method="post" action="/replyAdd">
					<input type="hidden" name="qno" value="${bv.qno}">
					<input type="hidden" name="id" value="${id}">
					<input type="hidden" name="pageNum" value="${pageNum}">
					
					<div class="input-group">
						<span class="input-group-text">댓글</span>
						<textarea class="form-control" rows="3" name="rcontent" style="overflow-x:hidden; resize:none;" 
							onkeydown="resize(this)" onkeyup="resize(this)" required></textarea>
						<button class="btn btn-outline-secondary" type="submit" id="button-addon2">작성</button>
					</div> 
				</form>
			</c:if>
		</c:forEach>

	<table class="table table-borderless">
	<c:forEach var="rv" items="${ReplyList}">
		<c:if test="${rv.rdeldate != null}"><tr><td colspan=2 align="center" class="table-active"><a style="font-size : 20px;">삭제된 댓글입니다.</a></td></tr></c:if>
		<c:if test="${rv.rdeldate == null}">
		<tr>
			<td>
				<a style="font-size : 20px;">&nbsp;${rv.id}&nbsp;</a>
				<c:if test="${rv.rupdate == null}">
					<a style="font-size : 10px;">|&nbsp;&nbsp;<fmt:formatDate value="${rv.rdate}" pattern="yy.MM.dd a HH:mm:ss"/></a>	
				</c:if>
				<c:if test="${rv.rupdate != null}">
					<a style="font-size : 10px;">|&nbsp;&nbsp;<fmt:formatDate value="${rv.rupdate}" pattern="yy.MM.dd a HH:mm:ss"/>(수정)</a>	
				</c:if>
			</td>
			<td align="right">
				<c:if test="${rv.id eq id}">
					<a href="javascript:void(0)" onclick="replyUpdate('${rv.rno}','${id}')" style="font-size: 15px;">수정</a>&nbsp;/&nbsp;<a href="javascript:void(0)" onclick="replyDelete('${rv.rno}','${id}')" style="font-size: 15px;">삭제</a>
				</c:if>
			</td>
		</tr>
		<tr>
		<td colspan="2">
			<a style="font-size : 15px;">${rv.rcontent}</a>
		</td></tr>
		</c:if>	
	</c:forEach>
	</table>
	<br><br><br><br>
	</div>
	<div class="col-lg-1"></div>
	</div></div>
</body>
<script>
	var quill = new Quill('#editor', {
		readOnly: true,
		theme: 'bubble'
	});
</script>
</html>
