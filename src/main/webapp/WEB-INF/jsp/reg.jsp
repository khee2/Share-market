<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" /> 
<link rel="stylesheet" href="/css/bootstrap.css" type="text/css" />
<link rel="stylesheet" href="/css/font-awesome-4.7.0/css/font-awesome.min.css" type="text/css" />
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<title>Register</title>
</head>
<body>
<div class="container">
<br><br>
<h3 style="text-align:center">게시글 등록</h3>
<form class="form-horizontal" method="post" action="/board/register.do" id="registForm">
  <div class="form-group">
    <label class="control-label col-sm-2" for="title">제목</label>
    <div class="col-sm-10">
    	<input name="title" placeholder="Title" type="text" class="form-control" />
    </div>
  </div>
  <div class="form-group">
    <label class="control-label col-sm-2" for="content">내용</label>
    <div class="col-sm-10">
    	<textarea name="content" rows="6" placeholder="Content" class="form-control"></textarea>
    </div>
  </div>
  <div class="form-group">
  	    <label class="control-label col-sm-2" for="writer">작성자</label>
  	    <div class="col-sm-10">
  		<input name="writer" placeholder="Writer" type="text" class="form-control" value="${member}" readonly/>
  </div>
  </div>
    <div class="form-group">
    <label class="control-label col-sm-2" for="price">가격</label>
    <div class="col-sm-10">
      <input name="price" placeholder="Price" type="text" class="form-control" />
    </div>
  </div>
  <select name="cateCode" class="form-select form-select-lg mb-3" aria-label=".form-select-lg example">
  <option selected>카테고리</option>
  	<option value="1">도서</option>
  	<option value="2">행사용품</option>
  	<option value="3">캠핑</option>
  	<option value="4">생활 공구</option>
  	<option value="5">건강 환경</option>
  	<option value="6">교구</option>
</select>
<select name="regiCode" class="form-select form-select-lg mb-3" aria-label=".form-select-lg example">
  <option selected>광역시도</option>
  	<option value="서울특별시">서울특별시</option>
  	<option value="부산광역시">부산광역시</option>
  	<option value="대구광역시">대구광역시</option>
  	<option value="인천광역시">인천광역시</option>
  	<option value="대전광역시">대전광역시</option>
  	<option value="울산광역시">울산광역시</option>
  	<option value="세종특별자치시">세종특별자치시</option>
  	<option value="경기도">경기도</option>
  	<option value="강원도">강원도</option>
   	<option value="충청북도">충청북도</option>
   	<option value="충청남도">충청남도</option>
   	<option value="전라북도">전라북도</option>
   	<option value="전라남도">전라남도</option>
   	<option value="경상북도">경상북도</option>
   	<option value="경상남도">경상남도</option>
   	<option value="제주특별자치도">제주특별자치도</option>
</select>
  <div class="form-group">
    <div class="col-sm-offset-2 col-sm-10">
      <div class="checkbox">
        <label><input type="checkbox"> Remember me</label>
      </div>
    </div>
  </div>
		<h4>첨부파일</h4>
		<div class="uploadDiv">
			<input name="uploadFile" type="file" multiple />
		</div>
		<div class="uploadResult">
			<ul></ul>
		</div>
		<button type="reset" class="btn btn-default">초기화</button>
		<input 	type="submit" class="btn btn-default" value="등록" />
		<input 	type="button" class="btn btn-default cancelButton" value="취소" onclick="goFirst()"/><!-- 눌러도 아무 반응 없음 -->
	</form>
</div>	
</body>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
<script>
	function goFirst(){
		location.href='/board/list.do';
	}

	$(document).ready(function(){
		
		let regex = new RegExp("(.*?)\.(exe|zip|sh|alz)$");
		let maxSize = 5242880; // 5MB
		let inputFile = $(".uploadDiv input")
		
		let uploadResult = $(".uploadResult ul");
		
		// 4. upload가 완료되면 실행할 함수
		function showUploadFile(uploadResultArr){
			let str = "";
			$(uploadResultArr).each(function(i, obj){
				console.log("4. 이미지는 맞음? 아님? " + obj.image);
				if(!obj.image){
					str += "<li data-path='" + obj.uploadPath + "' data-uuid='" + obj.uuid + "' ";
					str += "data-filename='" + obj.fileName + "' data-type='" + obj.image + "'>";
					str += "<img src='/img/attach.png' width='20px'>"  + obj.fileName + "</li>";
					console.log(str);
				}
				else{
					let fileCallPath = encodeURIComponent(obj.uploadPath + "/s_" + obj.fileName);
					console.log(fileCallPath);
					str += "<li data-path='" + obj.uploadPath + "' data-uuid='" + obj.uuid + "' ";
					str += "data-filename='" + obj.fileName + "' data-type='" + obj.image + "'>";				
					str += "<img src='/upload/display?fileName=" + fileCallPath + "'> " + obj.fileName + "</li>";	
					console.log(str);
				}
			});
			
			uploadResult.append(str)
		}
		
		// 1. 첨부파일의 확장자 제한, 용량 제한
		function checkExtension(fileName, fileSize){
			if(regex.test(fileName)){
				alert('업로드할 수 없는 파일 형식입니다.');
				return false;
			}
			if(fileSize >= maxSize){
				alert('용량이 너무 큽니다.');
				return false;
			}
			return true; // 통과했다고 얘기해주기.
		}
		
		// 2. 사용자가 file을 클릭하든지 file이 들어오던지 뭔가 input에 변화가 생기면
		$("input[type='file']").change(function(e){
			// html의 form 태그와 유사. html 단이 아닌 자바스크립트 단에서 폼 데이터를 다루는 객체.
			// html에서의 submit 제출 동작은 Ajax를 통해 서버에 제출한다.
			let formData = new FormData();
			let inputFile =$("input[name='uploadFile']");
			let files = inputFile[0].files;
			console.log("2.1 무슨 파일이니?" + files); // 무슨 파일 들어왔는지 확인차 찍어보자
			
			// formData에 file추가
			for(let i=0; i<files.length; i++){
				if(!checkExtension(files[i].name, files[i].size)){ // append하기 전 check
					console.log("11" + files[i].name);
					return; // 실패야 
				}
				console.log("2.1 파일 이름은? " + files[i].name);
				formData.append("uploadFile", files[i])
				// 폼 객체 key 값을 순회
				let keys = formData.keys();
				for (const pair of keys) {
					console.log("2.2 formData에 저장한 key는? " + pair);
				}
				
				// 폼 객체 values 값을 순회
				let pairs = formData.values();
				for (const pair of pairs) {
					console.log("2.3 formData에 저장한 value는? " + pair);
				}
			}
			// 3. ajax : 자바스크립트를 이용해서 비동기식으로 xml을 이용하여 서버와 통신함. 서버가 받아서 처리하는 걸 기대함. 기다림.
			$.ajax({
				url: "/upload/uploadAjaxAction", // 요청이 전송되는 URL
				processData : false,  // false => 처리되지 않은 데이터를 보내기 
				contentType : false, // 서버로 데이터를 보낼 때 content 유형
				data : formData, // 서버로 보낼 데이터
				type : "POST", // http method // default: get
				success: function(result){
					alert("upload OK");
					console.log(result);
					showUploadFile(result); // 4. uploadFile을 보여줄 함수를 호출한다.
					inputFile.val("");
				}
				// 요청이 성공일 때 호출되는 Callback 함수, 요청이 실패일 때 호출되는 Callback 함수.
			});
		});
		
		// 5. 마지막 제출 버튼을 누르면 일어나는 일
		$("input[type='submit']").on("click", function(e){ // click하면 event 실행
			e.preventDefault(); // 제출하는 활동 일단 하지마 내가 할게 
			let form = $("form#registForm");
			let str = "";
			
			$(".uploadResult ul li").each(function(i, obj){
				console.log("1 " + $(obj).data("filename"));
				str += "<input type='hidden' name='attachList[" + i + "].fileName' value='" + $(obj).data("filename") + "'>";
				str += "<input type='hidden' name='attachList[" + i + "].uuid' value='" + $(obj).data("uuid") + "'>";
				str += "<input type='hidden' name='attachList[" + i + "].uploadPath' value='" + $(obj).data("path") + "'>";
				str += "<input type='hidden' name='attachList[" + i + "].image' value='" + $(obj).data("type") + "'>";
			});
			
			form.append(str).submit();
		});
	});
</script>
</html>
