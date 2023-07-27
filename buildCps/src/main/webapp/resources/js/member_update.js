// 회원가입 데이터 유효성 검사
function check() {

	if ($.trim($("#id").val()) == "") {
		alert("아이디를 입력하세요.");
		$("#id").val("").focus();
		return false;
	}
	if ($.trim($("#passwd").val()) == "") {
		alert("비밀번호를 입력하세요.");
		$("#passwd").val("").focus();
		return false;
	}
	if ($.trim($("#passwd").val()).length < 4) {
		alert("비밀번호 4글자 이상 입력하세요.");
		$("#passwd").val("").focus();
		return false;
	}
	if ($.trim($("#passwd").val()).length > 20) {
		alert("비밀번호 20자 이내로 입력하세요.");
		$("#passwd").val("").focus();
		return false;
	}
	if ($.trim($("#company").val()) == "") {
		alert("회사명을 입력하세요.");
		$("#company").val("").focus();
		return false;
	}
	if ($.trim($("#name").val()) == "") {
		alert("이름을 입력하세요.");
		$("#name").val("").focus();
		return false;
	}
	if ($.trim($("#mail").val()) == "") {
		alert("이메일을 입력세요.");
		$("#location").val("").focus();
		return false;
	}
	if ($.trim($("#mail").val()).indexOf('@') !== -1
			&& $.trim($("#mail").val()).indexOf('@') < $.trim($("#mail").val()).lastIndexOf('.') - 1) {
		var afterDot = $.trim($("#mail").val()).substring($.trim($("#mail").val()).lastIndexOf('.') + 1);
		if (afterDot.length > 2) {
		} else {
			alert("이메일 주소가 유효하지 않습니다.");
			$("#mail").val("").focus();
			return false;
		}
	} else {
		alert("이메일 주소가 유효하지 않습니다.");
		$("#mail").val("").focus();
		return false;
	}
	if ($.trim($("#phone").val()) == "") {
		alert("핸드폰 번호를 입력하세요.");
		$("#phone").val("").focus();
		return false;
	}
	if ($.trim($("#phone").val()).length < 8
			|| $.trim($("#phone").val()).length > 14) {
		alert("핸드폰번호가 올바르지 않습니다.");
		$("#phone").val("").focus();
		return false;
	}
}