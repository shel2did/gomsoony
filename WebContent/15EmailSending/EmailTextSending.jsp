<%@page import="smtp.SMTPAuth"%>
<%@page import="java.util.MaP"%>
<%@page import="java.util.HashMap"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
request.setCharacterEncoding("UTF-8");
//메일발송을 위한 객체생성
SMTPAuth smtp = new SMTPAuth();
String mailContents=""
+"<table border=1>"
+"<tr> "
+"<td>제목</td>"
+"<td>제에목</td>"
+"</tr>"
+"<tr> "
+"<td>내용</td>"
+"<td>"+request.getParameter("content")+"</td>"
+"</tr>"
+"<tr> "
+"<td>이미지</td>"
+"<td><img src=\"https://postfiles.pstatic.net/20150515_216/21tnxs84xh_1431668526728pqpmQ_JPEG/%B4%D9%B6%F7%C1%E33.jpg?type=w1\"></td>"
+"</tr>"
+"</table>";
//메일을 보내기위한 여러가지 폼값을 Map컬렉션에 저장
Map<String,String> emailContent = new HashMap<String,String>();
emailContent.put("from", request.getParameter("from"));
emailContent.put("to", request.getParameter("to"));
emailContent.put("subject", request.getParameter("subject"));
//emailContent.put("content", request.getParameter("content"));
emailContent.put("content", mailContents);
//내용이 null값 아니라면 이메일 발송
if(request.getParameter("content")!=null){
	
	boolean emailResult = smtp.emailSending(emailContent);
	
	if(emailResult==true){
		out.print("메일발송성공");
	}else{
		out.print("메일발송실패");
	}
	return;
} 
%> 
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
<h2>이메일 발송하기</h2>
<form method="post" name="mailForm">
<table border=1>
	<tr>	
		<td> 
			보내는사람 : 
			<input type="text" name="from" value="본인네이버아이디@naver.com" />
		</td>
	</tr>
	<tr>	
		<td>
			받는사람 : 
			<input type="text" name="to" value="본인gmail아이디@gmail.com" />
		</td>
	</tr>
	<tr>	
		<td>
			제목 : 
			<input type="text" name="subject" value="" />
		</td>
	</tr>
	<tr>
		<td>
			<textarea name="content" cols="30" rows="10"></textarea>
		</td>
	</tr>
	<tr>
		<td>
			<button type="submit">이메일발송</button>
		</td>
	</tr>
</table>
</form>



</body>

</html>