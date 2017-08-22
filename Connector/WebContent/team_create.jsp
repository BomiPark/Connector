<%@page contentType="text/html; charset=euc-kr"%>
<%@ include file="loginCheck.jsp"%>
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

<title>팀 만들기</title>

<script>
function team_create() {
	if (form.team_board_title.value == "") {
		alert("제목을 입력해주세요.");
		form.team_board_title.focus();
		return false;
	}
	if (form.team_subject.value == "") {
		alert("분야를 입력해주세요.");
		form.team_subject.focus();
		return false;
	}
	if (form.team_contest_title.value == "") {
		alert("공모전을 입력해주세요.");
		form.team_contest_title.focus();
		return false;
	}
	if (form.team_member_count.value == "") {
		alert("모집인원을 입력해주세요.");
		return false;
	}
	if (form.team_board_contest.value == "") {
		alert("내용을 입력해주세요.");
		form.team_board_contest.focus();
		return false;
	}

	form.command.value = "teamCreate";
	form.action = "team_create.m2";
	form.submit();

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
						<br> <a href="index.jsp" style="padding: 30px">MAIN</a>
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
				<span class="btitle"> 팀원모집 </span> <br>
				<span class="bcontent">공모전을 함께할 팀원을 찾아보세요.</span>
			<hr>
			</div>
			
			<!-- /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// -->
			
			<form name="form" class="form-horizontal" method="POST">
			
			<input type="hidden" name="command" />

			  <div class="form-group">
			    <label for="team_board_title" class="col-sm-2">제목</label>
			    <div class="col-sm-10">
			      <input type="text" class="form-control" name="team_board_title" id="team_board_title" placeholder="title">
			    </div>
			  </div>
			  <div class="form-group">
			    <label for="team_subject" class="col-sm-2">분야</label>
			    <div class="col-sm-10">
			      <input type="text" class="form-control" name="team_subject" id="team_subject" placeholder="contest subject">
			    </div>
			  </div>
			  <div class="form-group">
			    <label for="team_contest_title" class="col-sm-2">공모전</label>
			    <div class="col-sm-10">
			      <input type="text" class="form-control" name="team_contest_title" id="team_contest_title" placeholder="contest">
			    </div>
			  </div>
			  
			  <div class="form-group">
			  	  <label for="team_member_count" class="col-sm-2">모집인원</label>
			  	  &nbsp;&nbsp;&nbsp;&nbsp;
				  <label class="radio-inline">
				  	<input type="radio" name="team_member_count" id="inlineRadio1" value="1"> 1
				  </label>
				  <label class="radio-inline">
				  	<input type="radio" name="team_member_count" id="inlineRadio2" value="2"> 2
				  </label>
				  <label class="radio-inline">
				  	<input type="radio" name="team_member_count" id="inlineRadio3" value="3"> 3
				  </label>
				  <label class="radio-inline">
				  	<input type="radio" name="team_member_count" id="inlineRadio1" value="4"> 4
				  </label>
				  <label class="radio-inline">
				  	<input type="radio" name="team_member_count" id="inlineRadio2" value="5"> 5
				  </label>
				  <label class="radio-inline">
				  	<input type="radio" name="team_member_count" id="inlineRadio3" value="6"> 6
				  </label>
				  <label class="radio-inline">
				  	<input type="radio" name="team_member_count" id="inlineRadio3" value="7"> 7
				  </label>
			  </div>
			  <div class="form-group">
			    <label for="team_board_contest" class="col-sm-2">내용</label>
			    <div class="col-sm-10">
			      <textarea class="form-control" rows="10" name="team_board_contest" placeholder="content"></textarea>
			    </div>
			  </div>
 			  <input type="button" value="저장" class="btn sbtn" style="float:right" onClick="team_create()"> &nbsp;
			</form>

				
			<!-- /////////////////////////////////////////////////////////////////////////////////////////////////////// -->				
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