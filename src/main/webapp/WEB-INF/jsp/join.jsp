<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

<html>
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
</head>
<script type="text/javascript">
	$(document).ready(function(){
		$("#logoutBtn").on("click", function(){
			location.href="/logout";
		})
	})
</script>
<body>
<div class="container">
<br><br>
	<form class="form-inline" name='homeForm' method="post" action="/login">
		<c:if test="${member == null}">
			<div class="form-group">
				<label for="userId">ID</label>
				<input type="text" class="form-control" id="id" name="id">
			</div>
			<div class="form-group">
				<label for="userPass">PWD</label>
				<input type="password" class="form-control" id="pwd" name="pwd">
			</div>
			<div class="form-group">
				<label for="userPass">PWD</label>
				<input type="password" class="form-control" id="pwd" name="pwd">
			</div>
			<div class="form-group">
				<label for="userPass">PWD</label>
				<input type="password" class="form-control" id="pwd" name="pwd">
			</div>
			<div class="form-group">
				<label for="userPass">PWD</label>
				<input type="password" class="form-control" id="pwd" name="pwd">
			</div>
			<div class="form-group">
				<label for="userPass">PWD</label>
				<input type="password" class="form-control" id="pwd" name="pwd">
			</div>												
			<div>
				<br>
				<button class="btn btn-default" type="submit">로그인</button>
				<a href="/board/list.do" class="btn btn-default">첫 화면으로 돌아가기</a>
				
			</div>
		</c:if>
		<c:if test="${member != null }">
			<div>
				<p>${member}님 환영 합니다.</p>
				
				<button id="logoutBtn"  class="btn btn-default" type="button">로그아웃</button>
				<a href="/board/list.do" class="btn btn-default">첫 화면으로 돌아가기</a>
				
			</div>
		</c:if>
		<c:if test="${msg == false}">
			<p style="color: red;">아이디와 비밀번호 확인해주세요.</p>
		</c:if>
	</form>
	</div>
</body>
</html>
