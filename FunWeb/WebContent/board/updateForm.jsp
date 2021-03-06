<%@page import="board.BoardBean"%>
<%@page import="board.BoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="../css/default.css" rel="stylesheet" type="text/css">
<link href="../css/subpage.css" rel="stylesheet" type="text/css">
</head>
<body>
<div id="wrap">
<!-- 헤더들어가는 곳 -->
<jsp:include page="../inc/top.jsp"></jsp:include>
<!-- 헤더들어가는 곳 -->

<!-- 본문들어가는 곳 -->
<!-- 메인이미지 -->
<div id="sub_img_notice"></div>
<!-- 메인이미지 -->

<!-- 왼쪽메뉴 -->
<jsp:include page="../inc/notice_sub_menu.jsp"></jsp:include>
<!-- 왼쪽메뉴 -->

<!-- 게시판 -->
<article>


<%
String category ="board";
int num = Integer.parseInt(request.getParameter("num"));
BoardDAO jbDAO= new BoardDAO();
BoardBean jbb= jbDAO.getboardContent(num,category);
%>
<form action="updatePro.jsp" method="post">
<table id="content">


<tr><th>작성일</th><td><%=jbb.getDate() %></td></tr>
<tr><th>글쓴이</th><td><%= jbb.getName() %></td></tr>
<tr><th>조회수</th><td><%=jbb.getReadcount() %></td></tr>
<tr><th>제목</th><td colspan="3"><input type="text" name="subject" value="<%=jbb.getSubject() %>"></td></tr>
<tr><th>내용</th><td colspan="3"><textarea name="content" rows="10" cols="50"><%=jbb.getContent() %></textarea></td></tr>
<tr><td colspan="4"> <input type="submit" value="확인">
<input type="button" value="취소" onclick="location.href='../center/notice.jsp'"></td></tr>
</table>
<input type="hidden" name="category" value="<%=category %>">
<input type="hidden" name="num" value="<%=jbb.getNum()%>">
</form>

</article>
<!-- 게시판 -->
<!-- 본문들어가는 곳 -->
<div class="clear"></div>
<!-- 헤더들어가는 곳 -->
<jsp:include page="../inc/bottom.jsp"></jsp:include>
<!-- 헤더들어가는 곳 -->
</div>
</body>
</html>