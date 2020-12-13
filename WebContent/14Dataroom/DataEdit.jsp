<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
<!DOCTYPE html>
<html>
<jsp:include page="../common/boardHead.jsp" />
<body>
<div class="container">
	<div class="row">		
		<jsp:include page="../common/boardTop.jsp" />
	</div>
	<div class="row">		
		<jsp:include page="../common/boardLeft.jsp" />
		<div class="col-9 pt-3">
		<!-- ### 게시판의 body 부분 start ### -->
			<h3>자료실 - <small>Edit(글수정하기)</small></h3>
<script>
	function checkValidate(fm){
		if(fm.name.value==""){
			alert("작성자의 이름을 입력하세요."); 
			fm.title.focus(); 
			return false; 
		}
	function checkValidate(fm){
		if(fm.pass.value==""){
			alert("비밀번호를 입력하세요."); 
			fm.title.focus(); 
			return false; 
		}
	function checkValidate(fm){
		if(fm.title.value==""){
			alert("제목을 입력하세요."); 
			fm.title.focus(); 
			return false; 
		}
		if(fm.content.value==""){
			alert("내용을 입력하세요."); 
			fm.content.focus(); 
			return false;
		}
	}
</script>
<div class="row mt-3 mr-1">
	<table class="table table-bordered table-striped">
	
	<!-- 파일 업로드를 위해서는 반드시 enctype을 선언해야한다ㅣ
		해당 선언문장이 없으면 파일은 서버로 전송되지 않는다. -->
	<form name="writeFrm" method="post" action="../DataRoom/DataEdit" 
		onsubmit="return checkValidate(this);" enctype="multipart/form-data">
		<input type="hid-den" name="idx" value="${dto.idx }"> 
		<input type="hid-den" name="nowPage" value="${param.nowPage }"> 
		<input type="hid-den" name="originalfile" value="${dto.attachedfile }"> 
<!-- 기존에 등록한 파일이 있는경우 수정시 파일을 첨부하지 않으면 기존파일을
	유지해야하므로 별도의 hidden폼이 필요하다. -->
	<colgroup>
		<col width="20%"/>
		<col width="*"/>
	</colgroup>
	<tbody>
		<tr>
			<th class="text-center align-middle">작성자</th>
			<td>
				<input type="text" class="form-control"	name="name" style="width:100px;" value="${dto.name }"/>
			</td>
		</tr>
		<tr>
			<th class="text-center" 
				style="vertical-align:middle;">패스워드</th>
			<td>
				<input type="password" class="form-control" name="pass"
					style="width:200px;" value="${dto.pass }"/>
			</td>
		</tr>
		<tr>
			<th class="text-center"
				style="vertical-align:middle;">제목</th>
			<td>
				<input type="text" class="form-control" 
					name="title" value="${dto.title }" />
			</td>
		</tr>
		<tr>
			<th class="text-center"
				style="vertical-align:middle;">내용</th>
			<td>
				<textarea rows="10" 
					class="form-control" name="content">"${dto.content }"</textarea>
			</td>
		</tr>
		<tr>
			<th class="text-center"
				style="vertical-align:middle;">첨부파일</th>
			<td>
				첨부파일명 : ${dto.attachedfile }<br/>
				<input type="file" class="form-control" name="attachedfile"/>
			</td>
		</tr>
	</tbody>
	</table>
</div>
<div class="row mb-3">
	<div class="col text-right">		
		<button type="submit" class="btn btn-danger">작성완료</button>
		<button type="reset" class="btn btn-dark">Reset</button>
		<button type="button" class="btn btn-warning"
			onclick="location.href='../DataRoom/DataList?nowPage=${param.nowPage}';">리스트 바로가기</button>
	</div>
	</form>
</div> 	
		<!-- ### 게시판의 body 부분 end ### -->
		</div>
	</div>
	<div class="row border border-dark border-bottom-0 border-right-0 border-left-0"></div>
	<jsp:include page="../common/boardBottom.jsp" />
</div>
</body>
</html>