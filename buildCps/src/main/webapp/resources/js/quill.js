var toolbarOptions = [
	  [{ 'font': [] }],
	  [{ 'size': ['small', false, 'large', 'huge'] }],  // 글씨 크기
	  [{ 'color': [] }, { 'background': [] }],          // dropdown with defaults from theme
	  ['bold', 'underline'],        // 굵게,밑줄
	  [{ 'align': [] }],
	  [{ 'list': 'ordered'}, { 'list': 'bullet' }], // 숫자,점 정렬
	  [{ 'indent': '-1'}, { 'indent': '+1' }],          // outdent/indent
	  ['blockquote', 'code-block'], // 블럭,코드블록 
	  ['link', 'image']
	];
	var quill = new Quill('#editor', {
	  modules: {
		    toolbar: toolbarOptions
	  },
	  placeholder: '내용을 작성해주세요.',
	  theme: 'snow'  // or 'bubble'
	});
	quill.on('text-change', function(delta, oldDelta, source) {
	    if ($('#qcontent').length > 0) {
	        $('#qcontent').val(quill.container.firstChild.innerHTML);
	    }else{
	    	$('#ncontent').val(quill.container.firstChild.innerHTML);	    	
	    }
	});
    quill.getModule('toolbar').addHandler('image', function () {
        selectLocalImage();
    });
function selectLocalImage() {
	const fileInput = document.createElement('input');
    fileInput.setAttribute('type', 'file');
    console.log(fileInput.type);
    fileInput.click();
	fileInput.onchange = function() {
        const formData = new FormData();
        const file = fileInput.files[0];
        formData.append('uploadFile', file);
		$.ajax({
            type: 'post',
            enctype: 'multipart/form-data',
			url: 'imageUpload',
			data: formData,
			processData: false,
			contentType: false,
			success: function (data) {
				console.log("data 업로드 성공 : "+data)
                const range = quill.getSelection();
                quill.insertEmbed(range.index, 'image', "/display?fileName="+data.fileName+"&datePath="+data.datePath);
			},
			error: function(err) {
				console.error("Err :: "+err);
			}
		});
	};
}