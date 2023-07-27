async function payinsert(id) {
		const { value: payInfo } = await Swal.fire({
		    icon: 'info',
		    title: '결재정보를 생성합니다.',
		    input: 'text',
		    inputLabel: 'pc수량과 결제 기간을 입력형식에 맞춰서 입력해주세요.',
		    inputPlaceholder: '입력형식 : 5,2000-01-01~2002-01-01',
		    inputValidator: (value) => {
		        if (!value) {
		          return 'pc수량과 결제기간을 입력해주세요.'
		        }
		      }
			});
		if (payInfo) {
		    $.ajax({
		      url: 'payInsert',
		      type: 'POST',
		      data: { id:id, pay:payInfo },
		      success: function(result) {
		        if (result==1) {
		          Swal.fire({
		            title: '결재정보가 성공적으로 생성되었습니다.',
		            icon: 'success',
		            showConfirmButton: false,
		            timer: 1500,
		            timerProgressBar: true
		          }).then(() => {
		            location.href = "/admin_user?id="+id+"&has=true";
		          });
		        } else if(result==9) {
					Swal.fire({
					  icon: 'warning',
					  title: '형식 오류 : 입력 형식이 틀립니다.'
					});
		        } else {
					Swal.fire({
						  icon: 'warning',
						  title: '서버 오류 : 결재정보 생성을 실패했습니다.'
						});
				}
		      },
		      error: function(xhr, status, error) {
		        Swal.fire({
		          icon: 'warning',
		          title: '통신 오류로 생성을 실패했습니다.'
		        });
		      }
		    });
		}
	}
async function payupdate(id) {
	const { value: payInfo } = await Swal.fire({
	    icon: 'info',
	    title: '결재정보를 수정합니다.',
	    input: 'text',
	    inputLabel: 'pc수량과 결제 기간을 입력형식에 맞춰서 입력해주세요.',
	    inputPlaceholder: '입력형식 : 5,2000-01-01~2002-01-01',
	    inputValidator: (value) => {
	        if (!value) {
	          return 'pc수량과 결제기간을 입력해주세요.'
	        }
	      }
		});
	if (payInfo) {
	    $.ajax({
	      url: 'payUpdate',
	      type: 'POST',
	      data: { id:id, pay:payInfo },
	      success: function(result) {
	        if (result==1) {
	          Swal.fire({
	            title: '결재정보가 성공적으로 수정되었습니다.',
	            icon: 'success',
	            showConfirmButton: false,
	            timer: 1500,
	            timerProgressBar: true
	          }).then(() => {
	            location.href = "/admin_user?id="+id+"&has=true";
	          });
	        } else if(result==9) {
				Swal.fire({
				  icon: 'warning',
				  title: '형식 오류 : 입력 형식이 틀립니다.'
				});
	        } else {
				Swal.fire({
					  icon: 'warning',
					  title: '서버 오류 : 결재정보를 수정했습니다.'
					});
			}
	      },
	      error: function(xhr, status, error) {
	        Swal.fire({
	          icon: 'warning',
	          title: '통신 오류로 수정을 실패했습니다.'
	        });
	      }
	    });
	}
}
async function paydelete(id) {
	  const { value: accept } = await Swal.fire({
		    icon: 'warning',
		    title: '결제정보 삭제',
		    input: 'checkbox',
		    inputValue: 0,
		    inputPlaceholder: '결제정보를 삭제합니다. 이전 데이터는 저장되지 않습니다.',
		    confirmButtonText: '확인 <i class="fa fa-arrow-right"></i>',
		    inputValidator: (result) => {
		      return !result && '체크박스를 선택해주세요.';
		    }
		  });
	if (accept) {
	    $.ajax({
	      url: 'payDelete',
	      type: 'POST',
	      data: { id:id },
	      success: function(result) {
	        if (result==1) {
	          Swal.fire({
	            title: '결재정보가 성공적으로 삭제었습니다.',
	            icon: 'success',
	            showConfirmButton: false,
	            timer: 1500,
	            timerProgressBar: true
	          }).then(() => {
	            location.href = "/admin_user?id="+id+"&has=false";
	          });
	        } else {
				Swal.fire({
					  icon: 'warning',
					  title: '서버 오류 : 결재정보 삭제를 실패했습니다.'
					});
			}
	      },
	      error: function(xhr, status, error) {
	        Swal.fire({
	          icon: 'warning',
	          title: '통신 오류로 삭제를 실패했습니다.'
	        });
	      }
	    });
	}
}