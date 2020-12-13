<%@page import="model.BbsDAO"%>
<%@page import="model.BbsDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 글작성 완료 전 로그인 체크하기 -->    
<%@ include file="../common/isLogin.jsp" %>
<%
request.setCharacterEncoding("UTF-8");

//폼값 받기
String title = request.getParameter("title");//제목
String content = request.getParameter("content");//내용

//DTO객체에 폼값과 아이디 저장
BbsDTO dto = new BbsDTO();
dto.setTitle(title);
dto.setContent(content);
//세션영역에 저장된 회원인증정보를 가져와서 DTO에 삽입
dto.setId(session.getAttribute("USER_ID").toString());

//DAO객체 생성시 application내장객체를 파라미터로 전달
BbsDAO dao = new BbsDAO(application); 

//사용자의 입력값을 저장한 DTO객체를 DAO로 전달후 insert처리

/*
//테스트 데이터가 필요한 경우 아래 for문을 사용하세요. 
//100개가 한번에 입력됩니다.
int affected = 1; 
for(int i=1 ; i<=100 ; i++){	
	dto.setTitle(title+" "+i+"번째 게시물");
	dao.insertWrite(dto);
}*/

int affected = dao.insertWrite(dto);
if(affected==1){
	/*
	새로운 게시물이 작성되었으므로 확인을 위해
	리스트의 첫번째 페이지로 이동해야 한다. 
	*/
	response.sendRedirect("BoardList.jsp");
}
else{
%>
	<script>
		alert("글쓰기에 실패하였습니다.");
		history.go(-1);
	</script>
<%	
}
%>