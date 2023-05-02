<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<jsp:include page="../includes/header.jsp"></jsp:include>

                <!-- Begin Page Content -->
                <div class="container-fluid">

                    <!-- Page Heading -->
                    <h1 class="h3 mb-2 text-gray-800">Tables</h1>
                    <!-- DataTales Example -->
                    <div class="card shadow mb-4">
                        <div class="card-header py-3">
                            <h6 class="m-2 font-weight-bold text-primary float-left">Board Register</h6>
                        </div>
                        <div class="card-body">
                              <form method="post" id="boardform">
							    <div class="form-group">
							      <label for="title">title</label>
							      <input type="text" class="form-control" id="title" placeholder="Enter title" name="title">
							    </div>
							    <div class="form-group">
<!-- 							      <div id="editor"></div> ==> 프로젝트에서는 이렇게 사용되어짐 -->
							      <label for="content">content</label>
							      <textarea rows="10" class="form-control" id="comment" name="content"></textarea>
							    </div>
							    <div class="form-group">
							      <label for="writer">writer</label>
							      <input type="text" class="form-control" id="writer" placeholder="Enter writer" value='<sec:authentication property="principal.username"/>' readonly>
							      <input type="hidden" class="form-control"  placeholder="Enter writer" name="writer" value='<sec:authentication property="principal.username"/>' readonly>
							    </div>
							     <div class="form-group">
							      <label for="file">file <br> <span class="btn btn-primary">파일첨부</span> </label>
							      <input type="file" class="form-control d-none" id="file" name="file" multiple>
							      <div class="uploadResult my-3">
							       <ul class="list-group filenames my-3"></ul>
							       <ul class="nav thumbs"></ul>
							      </div>
							     </div>
							     
							    <button type="submit" class="btn btn-primary" id="btnReg">Submit</button>
							    <input type="hidden" name="pageNum" value="1">
							    <input type="hidden" name="amount" value="${cri.amount}">
							    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
							  </form>
                        </div>
                    </div>

                </div>
                <!-- /.container-fluid -->

<style>
	.ck-editor__editable[role="textbox"]{
			min-height: 600px;
	}
</style>
<script>

	var csrfHeader = '${_csrf_headerName}';
	var csrfToken = '${_csrf.token}';
	
	ClassicEditor.create($("#comment").get(0), {
		ckfinder : {
			uploadUrl : '/ckImage.json'
		},
		headers : {
			csrfHeader : csrfToken
		}
	})
	.then(function(a){
		editor = a;
	});


</script>


<script>
$(function(){
	
	var csrfHeader = '${_csrf_headerName}';
	var csrfToken = '${_csrf.token}';
	
	
	
	$("#btnReg").click(function(){
		event.preventDefault();
		// parent.preventDefault();
		// title, content, writer, attachs[0].uuid
		var str="";
		$(".filenames li").each(function(i, obj){
			console.log(i, obj, this);
			var uuid = $(this).data('uuid');
			str += `
	        <input type="hidden" name="attachs[\${i}].uuid" value="\${uuid}">
	        <input type="hidden" name="attachs[\${i}].name" value="\${$(this).data('name')}">
	        <input type="hidden" name="attachs[\${i}].path" value="\${$(this).data('path')}">
	        <input type="hidden" name="attachs[\${i}].image" value="\${$(this).data('image')}">
	        `;
		})
		console.log(str);
		$("#boardform").append(str).submit();
	})
	// 파일 확장자 검사 
	function checkExtension(files){
		const MAX_SIZE = 5 * 1024 * 1024; // 최대 크기
		const EXCLUDE_EXT = new RegExp("(.*?)\.(js|jsp|asp|php)");	// 제외할 대상 파일 확장자 
		
		for(let i in files){
			
			// 만일 최대 크기를 넘거나 제거해야할 확장자가 포함되어 있다면 실행을 멈춤
			if(files[i].size >= MAX_SIZE || EXCLUDE_EXT.test(files[i].name)){
				return false;
			}
		}
		return true;
	}
	$("#file").change(function(){
		event.preventDefault();
		let files = $(this).get(0).files;
		console.log(files);
		
		if(!checkExtension(files)){
			// 문제가 발생되면 실행을 멈추겠다.
			alert("조건 불일치!");
			return false;
		}
		
		let formData = new FormData();
		
		for(let i in files){
			formData.append("files", files[i]);
		}
		$.ajax({
			url : "/uploadAjax",
			processData : false,
			contentType : false,
			beforeSend : function(xhr){
				xhr.setRequestHeader(csrfHeader.csrfToken);
			},
			data : formData,
			method : "post",
			success : function(data){
	//				파일 업로드에 성공하면 form 리셋
				showUploadedFile(data);
// 				$("form").get(0).reset();
				
			}
		})
	})

	function showUploadedFile(uploadResultArr){
		var str = "";
		var imgStr = "";
		for(var i in uploadResultArr){
			let obj = uploadResultArr[i];
// 			obj.thumb = "on";
// 			var params = new URLSearchParams(obj).toString();
			str += `<li class="list-group-item" data-uuid="\${obj.uuid}" data-name="\${obj.name}"  data-path="\${obj.path}" data-image="\${obj.image}">
			<a href="/download\${obj.url}"><i class="far fa-file"></i>`; 
			str += obj.name + `</a> <i class="far fa-times-circle btn-remove float-right" data-file="\${obj.url}"></i> </li>`;
			if(obj.image){
// 				obj.thumb = "off";
// 				var params2 = new URLSearchParams(obj).toString();
				imgStr += `<li class="nav-item m-2" data-uuid="\${obj.uuid}"><a class="img-thumb" href="">
				<img class="img-thumbnail" src="/display\${obj.url}&thumb=on" data-src="\${obj.url}"></a></li>`;				
			}
		}
		console.log(str);
		// 내부적으로 스트림 사용(최종)
		$(".uploadResult .filenames").append(str);
		$(".uploadResult .thumbs").append(imgStr);
	}
	
	
	$(".uploadResult ul").on("click",".btn-remove", function(){
		var file = $(this).data("file");
		console.log(file);
		$.ajax({
			url : "/deleteFile" + file,
			success : function(data){
				console.log(data);
				$('[data-uuid=' + data + ']').remove();
			}
		})
		
	
// 		$.getJSON("/deleteFile?" + file).done(function(data){		
// 			console.log(data);
// 			$this.parent().remove();
// 		});
	});
	
	
	// 클릭 이벤트 태그 위임
	$(".uploadResult ul").on("click",".img-thumb", function(){
		event.preventDefault();
		var param = $(this).find("img").data("src");
		$("#showImageModal").find("img").attr("src", "/display" + param).end().modal("show");

	});
	
	$(".filenames").sortable({
//		sort : function(event, ui){
//			// console.log(event);
//		},
	change : function() {
		console.log(this);
	}
	}).css({cursor: "move"});
})


</script>

<jsp:include page="../includes/footer.jsp"></jsp:include>