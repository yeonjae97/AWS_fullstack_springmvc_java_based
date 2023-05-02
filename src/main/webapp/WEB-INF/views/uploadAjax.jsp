<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>2023. 4. 7.오전 10:35:32</title>
 <script src="${pageContext.request.contextPath}/resources/vendor/jquery/jquery.min.js"></script>
 <link href="${pageContext.request.contextPath}/resources/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
 <style>
 	body{margin: 0}
 	.bigPictureWrapper {
 		position: absolute;
 		display: none;
 		justify-content: center;
 		align-items:center;
 		top:0%;
 		width:100%;
 		height: 100%;
 		background-color:gray;
 		z-index: 100;
 		background: rgba(255,255,255,0.5);
 	}
 	
 	.bigPicture {
 		position: relative;
 		display: flex;
 		justify-content: center;
 		align-items: center;
 	}
 	
 	.bigPicture img{
 		max-width: 600px;
 	}
 </style>
</head>
<body>
	<form method="post" enctype="multipart/form-data">
<!-- 		<input name="test"> -->
		<label for="files"><i class="fas fa-folder-plus"></i></label>
		<input type="file" name="files" multiple accept="image/jpeg, image/png" id="files">
<!-- 		  style="display: none" -->
		<input type="reset" value="reset"/> 
		<button>submit</button>
		<div></div>
				
	</form>
	<div class="uploadResult">
			<ul></ul>
		</div>

	<div class="bigPictureWrapper">
		<div class="bigPicture">
		</div>
	</div>
	
<script>


$(function(){
	
	$(".bigPictureWrapper").click(function(){
		$(".bigPicture").animate({width:0, height:0}, 1000);
		setTimeout(function(){
			$(".bigPictureWrapper").hide();
		}, 1000);
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
	
	
// 	function showImage(param){
// //	 	alert(param);
// 		$(".bigPictureWrapper").css("display", "flex").show();
// 		$(".bigPicture")
// 		.html("<img src='/display?" + param + "'>")
// 		.animate({width:'100%', height: '100%'}, 1000);
// 		console.log(param);
// 	}

	// 클릭 이벤트 태그 위임
	$(".uploadResult ul").on("click",".img-thumb", function(){
		event.preventDefault();
		$(".bigPictureWrapper").css("display", "flex").show();
		var param = $(this).find("img").data("src");
		$(".bigPicture")
		.html("<img src='/display?" + param + "'>")
		.animate({width:'100%', height: '100%'}, 1000);
	});
	
	$(".uploadResult ul").on("click",".btn-remove", function(){
		event.preventDefault();
		
		var file = $(this).data("file");
		console.log(file);
		$.getJSON("/deleteFile?" + file).done(function(data){		
			console.log(data);
		});
	});
	
	// 업로드된 파일 보여주기
	function showUploadedFile(uploadResultArr){
		var str = "";
		for(var i in uploadResultArr){
			let obj = uploadResultArr[i];
			obj.thumb = "on";
			var params = new URLSearchParams(obj).toString();
			console.log(params);
			if(!obj.image){
	
				str += '<li><a href="/download?' + params + '""><i class="far fa-file"></i>'; 
			}else{
				obj.thumb = "off";
				var params2 = new URLSearchParams(obj).toString();
				str += '<li><a class="img-thumb" href=""><img src="/display?' + params + '" data-src="' + params2 + '">';
			}
			str += obj.name + '</a> <i class="far fa-times-circle btn-remove" data-file="' + params + '"></i> </li>';
		}
		// 내부적으로 스트림 사용(최종)
		$(".uploadResult ul").append(str);
	}
	
	// 버튼을 누르면 발생되는 이벤트 행동
	$("form button").click(function(){
		event.preventDefault();
		let files = $(":file").get(0).files;
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
			url : "uploadAjax",
			processData : false,
			contentType : false,
			data : formData,
			method : "post",
			success : function(data){
// 				파일 업로드에 성공하면 form 리셋
				$("form").get(0).reset();
				showUploadedFile(data);
			}
		})
	})
})

</script>	
</body>


</html>