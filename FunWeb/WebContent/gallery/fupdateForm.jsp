<%@page import="gallery.galleryBean"%>
<%@page import="gallery.galleryDAO"%>
<%@page import="board.BoardBean"%>
<%@page import="board.BoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
int num = Integer.parseInt(request.getParameter("num"));
galleryDAO gDAO= new galleryDAO();
galleryBean gb= gDAO.getboardContent(num);
%>
<form action="fupdatePro.jsp" method="post" enctype="multipart/form-data">
<table border="1">

<tr><td>글번호</td><td><input type="text" name="num" readonly="readonly" value="<%=gb.getNum()%>"></td></tr>
<tr><td>작성일</td><td><%=gb.getDate() %></td></tr>
<tr><td>내용</td><td colspan="3"><input type="text" name="content" value="<%=gb.getContent() %>"></td></tr>
<tr><td>파일</td><td colspan="3" >
<%-- <a href="../upload/<%=gb.getFile()%>"><%=gb.getFile()%></a> --%>
<img src="../upload/<%=gb.getFile()%>" width="100" height="100">
<a href="file_down.jsp?file_name=<%=gb.getFile()%>"></a></td>
</tr>
<tr><td colspan="2"><input type="file" name="file" id="file" value="<%=gb.getFile()%>"></td></tr>
<tr><td colspan="4"> <input type="submit" value="확인">
<input type="button" value="취소" onclick="location.href='../center/notice.jsp'"></td></tr>
	
</table>
</form>
</body>
</html>