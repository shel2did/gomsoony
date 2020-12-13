<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="../common/bootstrap4.5.3/css/bootstrap.css">
<link rel="stylesheet" href="../common/bootstrap3.3.7/css/bootstrap.css">
<link rel="preconnect" href="https://fonts.gstatic.com">
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@500&display=swap" rel="stylesheet">
<link rel="stylesheet" href="css/style.css"/>
<script src="../common/jquery/jquery-3.5.1.js"></script>
</head>
<body>
<div class="container"><!-- 버튼바 윗줄 -->
<jsp:include page="./GSParts/topNav.jsp"/>


</div>

 <div class="container"><!-- 중간 -->
<jsp:include page="./GSParts/leftNav.jsp"/>
 <div class="right"><!-- 오른쪽패널 -->
<table class="table table-striped table-hover table-bordered table-striped">
<tr class="table-primary"> 
<th>제목</th>
<th>글쓴이</th>
<th>작성일</th>
<th>조회수</th>
<th>첨부</th>
</tr>
<tr>
<td>test</td>
<td>gom1</td>
<td>오늘</td>
<td>0</td>
<td>ㅋㅋ</td>
</tr>
<tr>
<td>test</td>
<td>gom1</td>
<td>오늘</td>
<td>0</td>
<td>ㅋㅋ</td>
</tr>
</table>
 </div>
 </div>



<div><!-- 맨아래 -->
</div>
</body>
</html>