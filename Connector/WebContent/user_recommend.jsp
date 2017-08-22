<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.*" %>
<%@page import="model.*" %>

<%@ include file="loginCheck.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="ko">
<head>
<!-- 메인 스타일 -->
<link rel="stylesheet" href="css/team.css">
<link rel="stylesheet" href="css/button.css">
<link rel="stylesheet" href="css/main.css">
<link rel="stylesheet" href="css/font.css">
<link rel="stylesheet" href="css/simpleBanner.css">
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
<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="js/simpleBanner.js"></script>
</head>

<body>

	<div class="row">
		<div class="col-md-2 col-xs-2 col-sm-2 col-lg-2"></div>
		<div class="col-md-8 col-xs-8 col-sm-8 col-lg-8">
			<div class="header">
				<div class="row">
					<div class="col-md-3 col-xs-3 col-sm-3 col-lg-3">
						<a href="index.m2?command=main&type=all"><img src="images/logo.png" height=80px></a>
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
			
						<br> <a href="index.m2?command=main&type=all" style="padding: 30px">MAIN</a> <a
							href="team_list.jsp" style="padding: 30px">TEAM</a> 
							<a href="user_recommend.m2?command=recommend" style="padding: 30px">PEOPLE</a> 
							<%if(session.getAttribute("userId")==null) {
							%>
							<a href="login.jsp" style="padding: 30px" class="user">MYPAGE</a>
							<%
							}
							else{%>
							<a href="user_view.m2?userId=<%=session.getAttribute("userId") %>&command=view" style="padding: 30px" class="user">MYPAGE</a>
							<%
							}%>
					</div>
				</div>
			</div>
			<div class="content">
			<!-- 하위메뉴 제목  section -->
			<div> <!-- <div class="margin_bottom">  -->
				<span class="btitle"> 팀원추천 </span> <br>
				<span class="bcontent">팀을 모집하고 함께 하고싶은 팀을 찾을 수 있습니다.</span>
			<hr>
			</div>
				<div>
			<% int i=1; %>
				<c:forEach var="recomend_User" items="${RecomUser}">
					<!-- form box 표시 방식  -->
					
						<% if(i%3==0) {%>
							<div class="form">
						<%} else {%>
							<div class="form" style="margin-right:5%">
						<% } i++; %>
							<ul>
								<li>
								ID : ${recomend_User.userId}
							</li>
							<li> 
								이름 : ${recomend_User.name}
							</li>
							<li>
								성별 : ${recomend_User.gender}
							</li>
							<li> 
								소속 : ${recomend_User.assign}
							</li>
							<li> 
								나이 : ${recomend_User.age}
							</li>
							<li>
								경력 : ${recomend_User.portfolio}
							</li>
							</ul>
						</div>
					</a>
				</c:forEach>
				<br><hr><br>
			</div>
			
			</div>
			<div class="footer" style="clear:both">
				<div class="sitemap">
					<div class="stitle">site map</div>
					<div class="row" style="padding-left: 60px;">
						<div class="col-md-3 col-xs-3 col-sm-3 col-lg-3 sitemap">
							<a href="index.m2?command=main&type=all">Main</a><br>
							<a href="index.m2?command=main&type=marketing">Marketing</a><br>
							<a href="index.m2?command=main&type=develop">Develop</a><br>
							<a href="index.m2?command=main&type=plan">Plan</a><br>
							<a href="index.m2?command=main&type=culture">Culture</a><br>
							<a href="index.m2?command=main&type=design">Design</a><br>
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
							<a href="user_recommend.m2?command=recommend" style="padding: 30px">PEOPLE</a><br>
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