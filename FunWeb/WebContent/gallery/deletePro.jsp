<%@page import="gallery.galleryDAO"%>
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
int num = (Integer)session.getAttribute("num");
// int num = Integer.parseInt(request.getParameter("num"));
int p_num = Integer.parseInt(request.getParameter("p_num"));
String category = request.getParameter("category");

galleryDAO gDAO = new galleryDAO();

	gDAO.deleteBoard(p_num, category);%>
	<script type="text/javascript">
	alert("작성하신 글이 삭제되었습니다.");
	location.href="../center/gallery.jsp";
	</script>

</body>
</html>