 function check(){
	 if($.trim($("#qsubject").val())==""){
	 Swal.fire	({
			title: '제목을 입력하세요',
		    icon: 'warning',
		    showConfirmButton: false,
		    timer: 1500,
		    timerProgressBar: true
		    }).then(() => {
				 $("#qsubject").val("").focus();
				 return false;
		    })
 }
 if($.trim($("#qcontent").val())==""){
	 Swal.fire	({
			title: '내용을 입력하세요',
		    icon: 'warning',
		    showConfirmButton: false,
		    timer: 1500,
		    timerProgressBar: true
		    }).then(() => {
				 $("#qcontent").val("").focus();
					 return false;
			    })
	 }
 }
 async function qnadelete(qno, qid) {
	  const { value: qpasswd } = await Swal.fire({
	    icon: 'warning',
	    title: '게시글을 삭제합니다.',
	    input: 'text',
	    inputPlaceholder: '게시글의 비밀번호를 입력해주세요.',
	    inputValidator: (value) => {
	      if (!value) {
	        return '비밀번호를 입력해주세요.'
	      }
	    }
	  });
	  if (qpasswd) {
	    $.ajax({
	      url: 'qnaDelete',
	      type: 'POST',
	      dataType: 'text',
	      data: { qno: qno, qid: qid, qpasswd: qpasswd },
	      success: function (result) {
	        if (result == 1) {
	          Swal.fire({
	            title: '삭제 성공',
	            icon: 'success',
	            showConfirmButton: false,
	            timer: 1500,
	            timerProgressBar: true
	          }).then(() => {
	            location.href = 'qna_list';
	          });
	        } else if (result == 0) {
	          Swal.fire({
	            icon: 'warning',
	            title: '비밀번호가 틀렸습니다.'
	          });
	        } else {
	          Swal.fire({
	            icon: 'warning',
	            title: '서버 오류 : 삭제실패'
	          });
	        }
	      },
	      error: function (xhr, status, error) {
	        Swal.fire({
	          icon: 'warning',
	          title: '통신 오류'
	        });
	      }
	    });
	  }
	}
 async function replyUpdate(rno, id) {
	  const { value: text } = await Swal.fire({
	    title: '댓글 내용을 수정합니다.',
	    input: 'textarea',
	    inputPlaceholder: '댓글 내용을 입력해주세요.',
	    showCancelButton: true,
	    inputValidator: (value) => {
	      if (!value) {
	        return '내용을 입력해주세요.'
	      }
	    }
	  });
	  if (text) {
	    $.ajax({
	      url: 'ReplyUpdate',
	      type: 'POST',
	      dataType: 'text',
	      data: { rno: rno, id: id, text: text },
	      success: function (result) {
	        if (result == 1) {
	          Swal.fire({
	            title: '수정 성공',
	            icon: 'success',
	            showConfirmButton: false,
	            timer: 1500,
	            timerProgressBar: true
	          }).then(() => {
	        	  location.reload();
	          });
	        } else if (result == 0) {
	          Swal.fire({
	            icon: 'warning',
	            title: '작성자가 아닙니다.'
	          });
	        } else {
	          Swal.fire({
	            icon: 'warning',
	            title: '서버 오류 : 삭제실패'
	          });
	        }
	      },
	      error: function (xhr, status, error) {
	        Swal.fire({
	          icon: 'warning',
	          title: '통신 오류'
	        });
	      }
	    });
	  }
	}
 async function replyDelete(rno, id) {
	 const { value: accept } = await Swal.fire({
		    icon: 'warning',
		    title: '댓글을 삭제합니다.',
		    input: 'checkbox',
		    inputValue: 0,
		    inputPlaceholder: '해당 댓글을 삭제합니다.',
		    confirmButtonText: '확인 <i class="fa fa-arrow-right"></i>',
		    inputValidator: (result) => {
		      return !result && '체크박스를 선택해주세요.';
		    }
		  });
	if (accept) {
	    $.ajax({
	      url: 'ReplyDelete',
	      type: 'POST',
	      data: { rno:rno, id:id },
	      success: function(result) {
	        if (result==1) {
	          Swal.fire({
	            title: '삭제 성공',
	            icon: 'success',
	            showConfirmButton: false,
	            timer: 1500,
	            timerProgressBar: true
	          }).then(() => {
	        	  location.reload();
	          });
	        } else if (result == 0) {
		          Swal.fire({
		            icon: 'warning',
		            title: '작성자가 아닙니다.'
		          });
	        } else {
			Swal.fire({
				  icon: 'warning',
				  title: '서버 오류 :삭제를 실패했습니다.'
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