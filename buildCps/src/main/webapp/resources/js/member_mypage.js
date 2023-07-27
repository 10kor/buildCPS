async function pc_drop(hostName, memberId) {
		  const { value: accept } = await Swal.fire({
		    icon: 'warning',
		    title: 'PC 등록 취소',
		    input: 'checkbox',
		    inputValue: 0,
		    inputPlaceholder: '취소된 PC는 다시 등록할 수 없습니다.',
		    confirmButtonText: '확인 <i class="fa fa-arrow-right"></i>',
		    inputValidator: (result) => {
		      return !result && '체크박스를 선택해주세요.';
		    }
		  });
		  if (accept) {
		    $.ajax({
		      url: 'pcCancel',
		      type: 'POST',
		      data: { hostName, memberId },
		      success: function(result) {
		        if (result > 0) {
		          Swal.fire({
		            title: 'PC가 성공적으로 취소되었습니다.',
		            icon: 'success',
		            showConfirmButton: false,
		            timer: 1500,
		            timerProgressBar: true
		          }).then(() => {
		            location.href = "/mypage";
		          });
		        } else {
		          Swal.fire({
		            icon: 'question',
		            title: '서버 오류 : PC 등록 취소에 실패했습니다.'
		          });
		        }
		      },
		      error: function(xhr, status, error) {
		        Swal.fire({
		          icon: 'question',
		          title: '통신 오류 : PC 등록 취소에 실패했습니다.'
		        });
		      }
		    });
		  }
		}

async function member_drop(memberId) {
	  const { value: password } = await Swal.fire({
	    icon: 'warning',
	    title: '회원탈퇴를 진행합니다.',
	    input: 'password',
	    inputLabel: '❗️탈퇴한 아이디는 재사용이 불가능합니다.❗',
	    inputPlaceholder: '비밀번호를 입력해주세요.',
	    inputAttributes: {
	        maxlength: 20,
	        autocapitalize: 'off',
	        autocorrect: 'off'
	    }
	  });
	  if (password) {
	    $.ajax({
	      url: 'memberDrop',
	      type: 'POST',
	      data: { memberId: memberId, passwd: password },
	      success: function(result) {
	        if (result == 1) {
	          Swal.fire({
	            title: '탈퇴되셨습니다.',
	            icon: 'success',
	            showConfirmButton: false,
	            timer: 1500,
	            timerProgressBar: true
	          }).then(() => {
	            location.href = "/";
	          });
	        } else if (result == 0) {
	          Swal.fire({
	            icon: 'error',
	            title: '서버 오류 : 탈퇴에 실패했습니다.'
	          });
	        } else if (result == 2) {
		          Swal.fire({
			            icon: 'error',
			            title: '비밀번호가 틀립니다.'
			            	});
			}
	      },
	      error: function(xhr, status, error) {
	        Swal.fire({
	          icon: 'error',
	          title: '통신 오류 : 탈퇴에 실패했습니다.'
	        });
	      }
	    });
	  }
	}