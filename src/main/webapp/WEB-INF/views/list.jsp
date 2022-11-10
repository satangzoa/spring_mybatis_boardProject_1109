<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>자유게시판</title>
</head>
<body>
	<h2>글 목록</h2>
	<hr>
	<table border="1" cellspacing="0" cellpadding="0" width="1000">
		<tr bgcolor="yellow" height="40">
			<th>번호</th>
			<th>아이디</th>
			<th >글쓴이</th>
			<th width="600">글제목</th>
			<th>조회수</th>
			<th>등록일</th>
		</tr>
		
			총 게시글 수 : ${boardCount }개<br>
		<c:forEach items="${list }" var="fbdto">
		<tr align="right">
			<td>${fbdto.fnum }</td>
			<td>${fbdto.fid }</td>
			<td>${fbdto.fname }</td>
			<td align="left">&nbsp;
			<a href="content_view?fnum=${fbdto.fnum }">${fbdto.ftitle }</a>	
			</td>
			<td>${fbdto.fhit }</td>
			<td>${fbdto.fdate }</td>
		
		</tr>
		</c:forEach>
			<td colspan="5" align="right"><a href="writeForm">글쓰기</a></td>
	</table>
	
</body>
</html>