async function fileDelete(bno) {
	  const { value: accept } = await Swal.fire({
		    icon: 'warning',
		    title: '파일데이터 삭제',
		    input: 'checkbox',
		    inputValue: 0,
		    inputPlaceholder: '해당 파일을 삭제합니다. 이전 데이터는 저장되지 않습니다.',
		    confirmButtonText: '확인 <i class="fa fa-arrow-right"></i>',
		    inputValidator: (result) => {
		      return !result && '체크박스를 선택해주세요.';
		    }
		  });
	if (accept) {
	    $.ajax({
	      url: 'bfileDelete',
	      type: 'POST',
	      data: { bno:bno },
	      success: function(result) {
	        if (result==1) {
	          Swal.fire({
	            title: '성공적으로 삭제었습니다.',
	            icon: 'success',
	            showConfirmButton: false,
	            timer: 1500,
	            timerProgressBar: true
	          }).then(() => {
	        	  location.reload();
	          });
	        } else {
				Swal.fire({
					  icon: 'warning',
					  title: '서버 오류로 삭제를 실패했습니다.'
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
async function fileAdd() {
	const { value: bfile } = await Swal.fire({
	  icon: 'info',
	  title: '엑셀파일 업로드',
	  text: '(업로드 가능 확장자 : .xls, .xlsx)',
	  input: 'file',
	  inputAttributes: {
	    'accept': '.xls, .xlsx',
	    'aria-label': 'Upload Excel File'
	  }
	})
	if (bfile) {
        const formData = new FormData();
        formData.append('bfile', bfile);
	    $.ajax({
		      url: 'bfileUpload',
		      type: 'POST',
		      data: formData,
		      processData: false,
		      contentType: false,
		      success: function(result) {
		        if (result==1) {
		          Swal.fire({
		            title: '성공적으로 등록되었습니다.',
		            icon: 'success',
		            showConfirmButton: false,
		            timer: 1500,
		            timerProgressBar: true
		          }).then(() => {
		        	  location.reload();
		          });
		        } else {
					Swal.fire({
						  icon: 'warning',
						  title: '서버 오류로 등록을 실패했습니다.'
						});
				}
		      },
		      error: function(xhr, status, error) {
		        Swal.fire({
		          icon: 'warning',
		          title: '통신 오류로 등록을 실패했습니다.'
		        });
		      }
		    });
	}
}
async function fileID(bno) {
	const { value: id } = await Swal.fire({
	    icon: 'info',
		title: 'ID 관리',
	    input: 'text',
	    inputLabel: '아이디를 입력하세요.',
	    inputValidator: (value) => {
	        if (!value) {
	          return '아이디를 입력하세요.'
	        }
	      }
		})
	if (id) {
	    $.ajax({
		      url: 'fileIdAdd',
		      type: 'POST',
		      data: { bno:bno, id:id },
		      success: function(result) {
		    if (result==1) {
		      Swal.fire({
		        title: '성공적으로 등록되었습니다.',
		        icon: 'success',
		        showConfirmButton: false,
		        timer: 1500,
		        timerProgressBar: true
		      }).then(() => {
		    	  location.reload();
		      });
		    } else {
				Swal.fire({
					  icon: 'warning',
					  title: '서버 오류로 등록을 실패했습니다.'
					});
			}
		  },
		  error: function(xhr, status, error) {
		    Swal.fire({
		      icon: 'warning',
		      title: '통신 오류로 등록을 실패했습니다.'
		    });
		  }
		});
	}
}
