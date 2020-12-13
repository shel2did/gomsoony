<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- 패스워드 검증을 마친 후 결과에 따라 alert 메세지를 띄워주고
	mode값에 따라 수정 혹은 삭제 페이즈로 이동한다.
	delete는 이미 앞에서 패스워드 검증이 끝났으므로 
	추가확인 없이 즉시 삭제처리 한다 -->
<c:choose>
<c:when test="${param.mode=='edit' }">
<c:set var="moveUrl" 
value="/DataRoom/DataEdit?idx=${param.idx }&nowPage=${param.nowPage }&searchColumn=${param.searchColumn}&searchWord=${param.searchWord}"/>
</c:when>
<c:otherwise>
<c:set var="moveUrl" 
value="/DataRoom/DataDelete?idx=${param.idx }&nowPage=${param.nowPage }&searchColumn=${param.searchColumn}&searchWord=${param.searchWord}"/>
</c:otherwise>
</c:choose>   
<script>
<c:choose>
<c:when test="${PASS_CORRECT}">
alert("패스워드 검증완료");
location.href="<c:url value='${moveUrl}'/>";
</c:when>
<c:otherwise>
alert("패스워드 검증실패. 뒤로이동");
history.back();
</c:otherwise>
</c:choose>  
</script>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

</head>
<body>
</body>
</html>