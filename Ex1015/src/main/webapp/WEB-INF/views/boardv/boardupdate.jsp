<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.5.1.js"></script>
<script>
	
		$(document).ready(function(){
			$("#upbtn").click(function(){
				
				var bnumber1 =$("#bnumber").val();
				var bwriter1=$("#bwriter").val();
				var btitle1=$("#btitle").val();
				var bcontents1=$("#bcontents").val();
				var bfile1=$("#bfile").val();
				var page1=$("#page").val();
				var boardjsonstring = {
						
						"bnumber":bnumber1,
						"bwriter":bwriter1,
						"btitle":btitle1,
						"bcontents":bcontents1,
						"bfile":bfile1,
						"page":page1
				};
				
				var boardjsonobj =JSON.parse(boardjsonstring);
				
				location.href="boardupdatep?bjson="+boardjsonobj;
				
			})
		})
		
</script>
</head>
<body>
	<form action="boardupdatep" method="post" name="upform" enctype="multipart/form-data">
			<input type="hidden" name="bnumber" id="bnumber" value="${bdto.bnumber}"><br>
			작성자<input type="text" name="bwriter" id="bwriter" value="${bdto.bwriter}" readonly><br>
			글 제목<input type="text" name="btitle" id="btitle" value="${bdto.btitle}"><br>
			글 내용<textarea rows="10" cols="5" name="bcontents" id="bcontents" >${bdto.bcontents}</textarea><br>
			<input type="hidden" name="page" value="${page}" id="page" >
			첨부파일<input type="file" name="bfile" value="${bdto.bfile}" id="bfile" ><br>
	</form>
	<button id="upbtn">수정하기</button>
</body>
</html>