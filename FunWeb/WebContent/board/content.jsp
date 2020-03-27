<%@page import="board.BoardBean"%>
<%@page import="board.BoardDAO"%>
<%@page import="comment.commentBean"%>
<%@page import="java.util.List"%>
<%@page import="comment.commentDAO"%>
<%@page import="com.sun.xml.internal.txw2.Document"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>content</title>
<link href="../css/default.css" rel="stylesheet" type="text/css">
<link href="../css/subpage.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
function showhide(){
	var obj=document.getElementById("showtable");
	if(obj.style.display=="none"){
		obj.style.display="block";
	}else{
		obj.style.display="none";
	}
}

function commentUpdate(id,comment,p_num,r_num){
	
	var update=document.getElementById("updatetable");
	document.getElementById("comment_id").value=id;
	document.getElementById("comment").value=comment;
	document.getElementById("p_num").value=p_num;
	document.getElementById("r_num").value=r_num;
	if(update.style.display=="none"){
		update.style.display="block";
	}else{
		update.style.display="none";
	}
}
function hide(){
	var update=document.getElementById("updatetable");
	if(update.style.display=="block"){
		update.style.display="none";
	}
}

function commentDelete(){
	var p_num =document.getElementById("p_num").value;
	var r_num =document.getElementById("r_num").value;
	location.href="../comment/deletePro.jsp?p_num="+p_num;
}

</script> 
</head>
<body>
<div id="wrap">
<!-- 헤더들어가는 곳 -->
<jsp:include page="../inc/top.jsp"></jsp:include>
<!-- 헤더들어가는 곳 -->

<!-- 본문들어가는 곳 -->
<!-- 메인이미지 -->
<div id="sub_img_center"></div>
<!-- 메인이미지 -->

<!-- 왼쪽메뉴 -->
<nav id="sub_menu">
<ul>
<li><a href="#">Notice</a></li>
<li><a href="#">Public News</a></li>
<li><a href="#">Driver Download</a></li>
<li><a href="#">Service Policy</a></li>
</ul>
</nav>
<!-- 왼쪽메뉴 -->

<!-- 게시판 -->
<article>





<%
int p_num = Integer.parseInt(request.getParameter("num"));
String pageNum = request.getParameter("pageNum"); // 계산할것이 아니므로 String으로 받아도 됨
System.out.println("num : "+p_num);
BoardDAO jbDAO= new BoardDAO();
BoardBean jbb= jbDAO.getboardContent(p_num);
String id = (String)session.getAttribute("id");
/* int num2 = jbb.getNum(); */
%>

<table border="1">
<tr><td>글번호</td><td><%= jbb.getNum() %></td></tr>
<tr><td>작성일</td><td><%=jbb.getDate() %></td></tr>
<tr><td>글쓴이</td><td><%= jbb.getName() %></td></tr>
<tr><td>조회수</td><td><%=jbb.getReadcount() %></td></tr>
<tr><td>제목</td><td colspan="3"><%=jbb.getSubject() %></td></tr>
<tr><td>내용</td><td colspan="3"><%=jbb.getContent() %></td></tr>
<tr><td>파일</td><td colspan="3"><a href="../upload/<%=jbb.getFile()%>"><%=jbb.getFile()%></a>
<img src="../upload/<%=jbb.getFile()%>" width="100" height="100">
<a href="file_down.jsp?file_name=<%=jbb.getFile()%>"><%=jbb.getFile()%></a></td>
</tr>
<tr><td colspan="4">
<input type="button" value="글목록" onclick="location.href='../notice.jsp?pageNum=<%=pageNum%>'">
<%if(jbb.getId().equals(id)){%>
<input type="button" value="글수정" onclick="location.href='updateForm.jsp?num=<%=jbb.getNum()%>&pageNum=<%=pageNum%>'">
<input type="button" value="글삭제" onclick="showhide();">
<%}%>
</td></tr>
</table>

<!-- delete code -->
<div id="showtable" style="display:none;">
<form action="deletePro.jsp?pageNum=<%=pageNum%>" method="post">
<table>
<tr><td>비밀번호</td><td><input type="password" name="pass"></td>
<% session.setAttribute("num", jbb.getNum());%>
<td><button type="submit" value="확인">확인</button></td></tr>
</table>
</form>
</div>

<hr>
<!-- comment code -->
<% if(id!=null){ %>
<form action="commentPro.jsp">
<input type="hidden" name="id" value="<%=id%>">
<textarea name="content" cols="50" rows="2"></textarea>
<input type="hidden" name="p_num" value="<%=p_num%>">
<input type="submit">
</form>

<%}
commentDAO comDAO = new commentDAO();
List comList= comDAO.getCommentList(p_num);%>
<table>
<% for(int i=0;i<comList.size();i++){
	commentBean cb = (commentBean)comList.get(i);
	%>
<tr><td><%=cb.getId() %></td><td><%=cb.getContent() %></td>
<% if(cb.getId().equals(id)){ %>
<td><input type="button" value="수정" onclick="commentUpdate('<%=cb.getId()%>','<%=cb.getContent()%>',
'<%=cb.getP_num()%>','<%=cb.getR_num()%>');"></td>
<td><a href="../comment/deletePro.jsp?num=<%=cb.getNum()%>&p_num=<%=cb.getP_num()%>">x</a></td> <%} %></tr>


<%


} %>
</table>



<!-- comment update code -->
<div id="updatetable" style="display:none;">
<form action="../comment/updatePro.jsp" method="get">

<input type="text" name="comment_id" id="comment_id"><input type="text" name="comment" id="comment">
<input type="hidden" name="p_num" id="p_num"><input type="hidden" name="r_num" id="r_num">
<input type="submit" value="수정" >
<input type="button" value="취소" onclick="hide();">
</form>
</div>


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