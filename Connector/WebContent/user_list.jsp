<%@page contentType="text/html; charset=euc-kr"%>
<%@page import="java.util.*"%>
<%@page import="model.*"%>
<%@ include file="loginCheck.jsp"%>
<%
	@SuppressWarnings("unchecked")
	List<User> userList = (List<User>) request.getAttribute("userList");
%>
<html lang="ko">
<head>
<!-- 메인 스타일 -->
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
<title>사용자 관리</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel=stylesheet href="../css/user.css" type="text/css">
<!-- script language="JavaScript">
function userCreate() {
	f.command.value = "insertForm";
	f.action = "user_write.m2";
	f.submit();
}
</script -->
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
						<br> <a href="index.jsp" style="padding: 30px">MAIN</a> <a
							href="team_list.m2?command=teamList&type=all" style="padding: 30px">TEAM</a> <a
							href="user_recommend.jsp" style="padding: 30px">PEOPLE</a>
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
				<form name="f" method="post" action="user_write.m2">
					<input type="hidden" name="command" value="insertForm">
					<table style="width: 100%">
						<tr>
							<td width="20"></td>
							<!--  <td><a href="logout.m2?command=logout">로그아웃</a><br /></td> -->
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td width="20"></td>
							<td>
								<!--contents-->
								<table style="width: 100%">
									<tr>
										<td bgcolor="f4f4f4" height="22">&nbsp;&nbsp;<b>사용자
												관리 - 리스트</b></td>
									</tr>
								</table> <br> <!-- list -->
								<table style="width: 100%; background-color: YellowGreen">
									<tr>
										<td width="190" align="center" bgcolor="E6ECDE" height="22">사용자ID</td>
										<td width="200" align="center" bgcolor="E6ECDE">이름</td>
										<td width="200" align="center" bgcolor="E6ECDE">나이</td>
									</tr>
									<%
										if (userList != null) {
											Iterator<User> userIter = userList.iterator();

											//사용자 리스트를 클라이언트에게 보여주기 위하여 출력.
											while (userIter.hasNext()) {
												User user = (User) userIter.next();
									%>
									<tr>
										<td width="190" align="center" bgcolor="ffffff" height="20">
											<%=user.getUserId()%>
										</td>
										<td width="200" bgcolor="ffffff" style="padding-left: 10">
											<a
											href="user_view.m2?userId=<%=user.getUserId()%>&command=view"
											class="user"> <%=user.getName()%>
										</a>
										</td>
										<td width="190" align="center" bgcolor="ffffff" height="20">
											<%=user.getAge()%>
										</td>
									</tr>
									<%
										}
										}
									%>
								</table> <!-- /list --> <br> <!-- button -->
								<table style="width: 100%">
									<tr>
										<td align="right"><input type="submit" value="사용자 추가" />
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</form>
			</div>
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