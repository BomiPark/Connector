<%@page contentType="text/html; charset=euc-kr"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.*"%>
<%@page import="model.*"%>

<%
	List<Team> teamList = (List<Team>)request.getAttribute("teamList");
%>
<html lang="ko">
<head>
<link rel="stylesheet" href="css/main.css">
<link rel="stylesheet" href="css/team.css">
<link rel="stylesheet" href="css/font.css">
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

<title>�� ����Ʈ</title>

<script>
</script>

</head>

<body>
<div class="row">
	<div class="col-md-2 col-sm-2 col-lg-2"></div>
	<div class="col-md-8 col-sm-8 col-lg-8">
		<div class="header">
			<div class="row">
				<div class="col-md-3 col-sm-3 col-lg-3">
					<a href="index.jsp"><img src="images/logo.png" height=80px></a>
				</div>
				<div class="col-md-9 col-sm-9 col-lg-9 subtitle">
					<%
						if (session.getAttribute("userId") != null) {//userid ������
							out.println(
									"<a href='logout.m2?command=logout' style='font-size: 15px; padding-right: 30px;'>logout</a><br>");
						} else {//userid ���� �����̸� 
							out.println("<a href='login.jsp' style='font-size: 15px; padding-right: 30px;'>login</a><br>");
							//session.getAttribute("userId");
						}
					%>
					<br> <a href="index.jsp" style="padding: 30px">MAIN</a>
					<a href="team_list.m2?command=teamList&type=all" style="padding: 30px">TEAM</a>
					<a href="" style="padding: 30px">PEOPLE</a>
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
			<!-- �����޴� ����  section -->
			<div> <!-- <div class="margin_bottom">  -->
				<span class="btitle"> ������Ʈ </span> <br>
				<span class="bcontent">���� �����ϰ� �Բ� �ϰ���� ���� ã�� �� �ֽ��ϴ�.</span>
				<!-- ����� team�޴������� ���� �� -->
				<span style="float:right"><span class="glyphicon glyphicon-pencil" style="color:#7DB1BC; font-size:17px"></span>&nbsp;
				<a href="team_create.m2?command=teamCreate" class="team_create">�������</a></span>
			<hr>
			</div>
			
			<!-- SUB MENU SECTION -->
			<div>
				<a href="team_list.m2?command=teamList&type=urgent" id="urgent" class="team_sub_menu">�����ӹ���</a>
				<a href="team_list.m2?command=teamList&type=all" id="all" class="team_sub_menu">��ü����</a>
				<a href="team_list.m2?command=teamList&type=marketing" id="marketing" class="team_sub_menu">Marketing</a>
				<a href="team_list.m2?command=teamList&type=develop" id="develop" class="team_sub_menu">Develop</a>
				<a href="team_list.m2?command=teamList&type=plan" id="plan" class="team_sub_menu">Plan</a>
				<a href="team_list.m2?command=teamList&type=culture" id="culture" class="team_sub_menu">Culture</a>
				<a href="team_list.m2?command=teamList&type=design" id="design" class="team_sub_menu">Design</a>
			</div>
					
			<hr>
			
			<!-- CONTENT SECTION -->
			
			<div>
				<% int i=1; %>
				<c:forEach var="team" items="${teamList}">
					<!-- form box ǥ�� ���  -->
					
					<a class="selectForm" href="team_view.m2?teamCode=${team.teamCode}&command=teamView">
					
						<% if(i%3==0) {%>
							<div class="form">
						<%} else {%>
							<div class="form" style="margin-right:5%">
						<% } i++; %>
							<ul>
								<!-- image -->
								<li>������ ����</li>		
							
								<!-- board title -->
								<li>${team.teamBoardTitle}</li>
								
								<!-- contest title -->
								<li>${team.contestTitle}</li>
								
								<!-- contest subject -->
								<li>${team.subjectName}</li>
								
								<!-- contest date -->
								<li>${team.startDay} ~ ${team.endDay}</li>
								
								<!-- leader -->
								<li>${team.leaderName}</li>
								
								<!-- recruiting member count -->
								<li>${team.currentMember} / <b>${team.maxMember}</b></li>
								
							</ul>
						</div>
					
					</a>
				</c:forEach>
			</div>
			
			
				
		</div>
		
		<!-- FOOTER SECTION -->
		<div class="footer" style="clear:both">
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
							href="list_roco.html">��ü ����</a><br> <a
							href="list_horror.html">�����ӹ��� ����</a><br> <a
							href="list_etc.html">���������� ����</a><br> <a
							href="list_etc.html">�������� ����</a><br> <a
							href="list_etc.html">�� �����</a><br>
					</div>
					<div class="col-md-3 col-xs-3 col-sm-3 col-lg-3 sitemap">
						<a href="board.jsp">PEOPLE</a><br> <a href="board.jsp">�����޴����ּ�</a><br>
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