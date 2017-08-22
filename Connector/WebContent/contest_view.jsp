<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.*"%>
<%@page import="model.*"%>
<%
	ContestDTO contestDTO = (ContestDTO)request.getAttribute("contestDTO");
%>

<html lang="ko">
<head>

<link rel="stylesheet" href="css/main.css">
<link rel="stylesheet" href="css/font.css">
<link rel="stylesheet" href="css/team.css">
<link rel="stylesheet" href="css/button.css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"
	integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css"
	integrity="sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r"
	crossorigin="anonymous">
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"
	integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS"
	crossorigin="anonymous"></script>
<meta http-equiv="imagetoolbar" content="no" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no, target-densitydpi=medium-dpi" />
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">

<title>상세정보 보기</title>

<script>
function contest_modify() {
	form.method= "get";
	form.command.value = "contestModify";
	form.action = "contest_update.m2";
	form.submit();
}

function contest_remove() {
	if (confirm("정말 삭제하시겠습니까?")) {
		form.command.value = "contestRemove";
		form.action = "contest_remove.m2";
		form.submit();
	}
}
</script>

</head>
<body>
	<div class="row">
		<div class="col-md-2 col-xs-2 col-sm-2 col-lg-2"></div>
		<div class="col-md-8 col-xs-8 col-sm-8 col-lg-8">
			<div class="header">
				<div class="row">
					<div class="col-md-3 col-xs-3 col-sm-3 col-lg-3">
						<a href="index.jsp"><img src="images/logo.png" height=80px></a>
					</div>
					<div class="col-md-9 col-xs-9 col-sm-9 col-lg-9 subtitle">
						<%
							if (session.getAttribute("userId") != null) {//userid 있으면
								out.println(
										"<a href='logout.m2?command=logout' style='font-size: 15px; padding-right: 30px;'>logout</a><br>");
							} else {//userid 없는 상태이면 
								out.println("<a href='login.jsp' style='font-size: 15px; padding-right: 30px;'>login</a><br>");
								//session.getAttribute("userId");
							}
						%>
						<br> <a href="index.m2?command=main&type=all" style="padding: 30px">MAIN</a>
						<a href="team_list.m2?command=teamList&type=all" style="padding: 30px">TEAM</a>
						<a href="user_recommend.jsp" style="padding: 30px">PEOPLE</a>
						<%
							if (session.getAttribute("userId") == null) {
						%>
						<a href="login.jsp" style="padding: 30px" class="user">MYPAGE</a>
						<%
							} else {
						%>
						<a
							href="user_view.m2?userId=<%=session.getAttribute("userId")%>&command=view"
							style="padding: 30px" class="user">MYPAGE</a>
						<%
							}
						%>
					</div>
				</div>
			</div>
			<div class="content">
			
			<!--  **************************************************************************************  -->
			<!-- 하위메뉴 제목  section -->
			<div class="margin_bottom">
				<span class="btitle"> ${ contestDTO.contestTitle } </span> <br>
				<span class="bcontent">공모전 상세</span>
			<hr>
			</div>
			
			<!-- /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// -->
			
			<form name="form" class="form-horizontal" method="POST">
			<input type="hidden" name="command" value="contestModify"/>
			<input type="hidden"
						name="contestCode" value="${contestDTO.contestCode}" />
			  <div class="form-group">
			    <label for="contest_board_title" class="col-sm-2">제목</label>
			    <div class="col-sm-10">
			      ${ contestDTO.contestTitle }
			    </div>
			  </div>
			  <div class="form-group">
			    <label for="contest_subject" class="col-sm-2">분야</label>
			    <div class="col-sm-10">
			     ${ contestDTO.subjectName }
			    </div>
			  </div>
			  <div class="form-group">
			    <label for="contest_content" class="col-sm-2">내용</label>
			    <div class="col-sm-10">
			      ${ contestDTO.contestContent }
			    </div>
			  </div>
			  <div class="form-group">
			    <label for="contest_startday" class="col-sm-2">시작일</label>
			    <div class="col-sm-10">
			      <b> ${ contestDTO.startDay } </b>
			    </div>
			  </div>
			  <div class="form-group">
			    <label for="contest_endday" class="col-sm-2">마감일</label>
			    <div class="col-sm-10">
			      <b> ${ contestDTO.endDay } </b>
			    </div>
			  </div>
			  
			 
			<hr>
			<!-- button section -->
				
				<input type="button" value="공모전 수정" class="btn sbtn" onClick="contest_modify()"> &nbsp;
				<input type="button" value="공모전 삭제" class="btn cbtn" onClick="contest_remove()"> &nbsp;
			</form>
			<!-- /////////////////////////////////////////////////////////////////////////////////////////////////////// -->				
			</div>
			
			<hr>
			<div class="footer">
				<div class="sitemap">
					<div class="stitle">site map</div>
					<div class="row" style="padding-left: 60px;">
						<div class="col-md-3 col-xs-3 col-sm-3 col-lg-3 sitemap">
							<a href="index.jsp">Main</a><br> <a href="index.jsp">Marketing</a><br>
							<a href="index.jsp">Develop</a><br> <a href="index.jsp">Plan</a><br>
							<a href="index.jsp">Culture</a><br> <a href="index.jsp">Design</a><br>
						</div>
						<div class="col-md-3 col-xs-3 col-sm-3 col-lg-3 sitemap">
							<a href="list_all.html">TEAM</a><br> <a
								href="list_roco.html">전체 보기</a><br> <a
								href="list_horror.html">마감임박팀 보기</a><br> <a
								href="list_etc.html">모집중인팀 보기</a><br> <a
								href="list_etc.html">마감된팀 보기</a><br> <a
								href="list_etc.html">팀 만들기</a><br>
						</div>
						<div class="col-md-3 col-xs-3 col-sm-3 col-lg-3 sitemap">
							<a href="board.jsp">PEOPLE</a><br> <a href="board.jsp">하위메뉴써주셈</a><br>
						</div>
						<div class="col-md-3 col-xs-3 col-sm-3 col-lg-3 sitemap">
							<a href="calendar.jsp">MYPAGE</a><br>
						</div>
					</div>
				</div>

				<hr>
				<div class="copyright">
					<center>copyright &copy; 2016 by CONNECTOR</center>
				</div>
			</div>
		</div>
		<div class="col-md-2 col-xs-2 col-sm-2 col-lg-2"></div>
	</div>
</body>
</html>