<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
<h2>간단한 MVC패턴 만들기</h2>
<!-- 사용자가 요청할때 type 파라미터에 따라 서블릿은
	각기 다른 처리를 하게된다. -->
<ul>
<li> 
<a href="./SimpleMVC">SimpleMVC?type=</a>
</li>
<li> 
<a href="SimpleMVC?type=greeting&id=shely&pw=1324">SimpleMVC?type=greeting&id=shely&pw=1324</a>
</li>
<li> 
<a href="../13Servlet/SimpleMVC?type=date">SimpleMVC?type=date</a>
</li>
<li> 
<a href="<%=request.getContextPath() %>/13Servlet/SimpleMVC?type=noparam">SimpleMVC?type=noparam</a>
</li>
</ul>
<h3>요청결과
<span style="color:red; font-size: 2em;">${result }</span>

</h3>
<h3>이미지와 요청명</h3>
<img alt="구름하튜" src="../images/3.jpg">
<img alt="구름하튜" src="<%request.getContextPath()%>/images/3.jpg">
</body>
</html>