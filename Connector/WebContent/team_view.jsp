<%@page contentType="text/html; charset=euc-kr"%>
<%@ include file="loginCheck.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.*"%>
<%@page import="model.*"%> 
<% List<User> userList = (List<User>)request.getAttribute("userList");%>
<% Team team = (Team)request.getAttribute("team");%>
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

<title>팀 보기</title>

<script type="text/javascript">
 function team_apply() {
	 if (confirm("이 팀에 참가 신청을 하시겠습니까?")) {
		 form.command.value = "teamApply";
		 form.action = "teamApply.m2";
		 form.submit();
	 }
 }
 //팀장이 참가신청한 사용자를 승인해줄 때 
 function apply_ok() {
	 if (confirm("참가 신청을 수락하시겠습니까?")) {
		 f.command.value = "applyAllow";
		 f.action = "applyAllow.m2";
		 f.submit();
	 }
 }
 //버튼활성화
 function btn_on(btn) {
  btn = document.getElementById(btn);
  btn.disabled = false;
 }
 //버튼비활성화
 function btn_off(btn) {
  btn = document.getElementById(btn);
  btn.disabled = 'disabled';
 }
 function hidden(val) {
	 val = document.getElementById(val);
	 val.style.visibility="hidden";
 }
 //모집 완료시
 function apply_complete(text) {
	 text = document.getElementById(text);
	 text.innerHTML = "팀원 모집이 완료되었습니다."; 
 }
//모집 기간 만료시
 function date_expired(text) {
	 text = document.getElementById(text);
	 text.innerHTML = "모집 기간이 만료되었습니다."; 
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
				<span class="btitle"> ${ team.teamBoardTitle }</span> <br>
				<span class="bcontent">함께하고 싶은 팀을 찾아보세요.</span>
			<hr>
			</div>
			
			<!-- /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// -->
			
			<form name="f" class="form-horizontal" method="POST">
			  <div class="form-group">
			    <label for="team_board_title" class="col-sm-2">제목</label>
			    <div class="col-sm-10">
			      ${ team.teamBoardTitle }
			    </div>
			  </div>
			  <div class="form-group">
			    <label for="team_board_title" class="col-sm-2">모집자</label>
			    <div class="col-sm-10">
			      ${ team.leaderName }&nbsp;&nbsp;&nbsp;(${ team.leaderId })
			    </div>
			  </div>
			  <div class="form-group">
			    <label for="team_subject" class="col-sm-2">분야</label>
			    <div class="col-sm-10">
			      ${ team.subjectName }
			    </div>
			  </div>
			  <div class="form-group">
			    <label for="team_contest_title" class="col-sm-2">공모전</label>
			    <div class="col-sm-10">
			      <b> ${ team.contestTitle } </b>
			    </div>
			  </div>
			  
			  <div class="form-group">
			  	<label for="team_member_count" class="col-sm-2">모집인원</label>
			  	<div class="col-sm-4">
			    	${ team.maxMember }
			    </div>
			    
			    <label for="team_member_count" class="col-sm-2">현재인원</label>
			  	<div class="col-sm-4">
			    	<b> ${ team.currentMember } </b>
			    </div>
			  </div>
			  <div class="form-group">
			    <label for="team_board_contest" class="col-sm-2">내용</label>
			    <div class="col-sm-10">
			      ${ team.teamBoardContent }
			    </div>
			  </div>
			  
			  <hr>
			  <div class="form-group">
			    <label for="team_board_contest" class="col-sm-2">참가자</label>
			    <div class="col-sm-10">
			   	 	<c:forEach var="user" items="${userList}">
			   	 		${user.name} (${ user.userId })&nbsp;&nbsp;&nbsp;
			   	 	</c:forEach>
			   	 	
			    </div>
			  </div>
			  
			  <!-- 모집자일 경우 승인 대기자 처리 -->
			  <c:if test = "${team.leaderId == sessionScope.userId}">
				  <hr>
				  <div class="form-group" id="waiting">
				    <label for="team_board_contest" class="col-sm-2">승인 대기</label>
				    <div class="col-sm-10">
				   	 	<c:forEach var="applyList" items="${applyList}">
				   	 		<c:if test = "${applyList.agree == 0 }">
				   	 		<div>
					   	 		${applyList.memberName} <a href="user_view.m2?userId=${ applyList.memberId }&command=view"> (${ applyList.memberId }) </a> &nbsp;
					   	 		<input type="hidden" name="applyCode" value="${applyList.applyCode}" />
					   	 		<input type="hidden" name="teamCode" value="${team.teamCode}" />
					   	 		<input type="hidden" name="command" value="applyAllow" />
					   	 		<input type="button" value="수락" class="btn okbtn" onClick="apply_ok()"><br><br>
				   	 		</div>
				   	 		</c:if>
				   	 	</c:forEach>
				    </div>
				  </div>
			  </c:if>
			  
			</form>
			
			<hr>
			<!-- text section -->
			<div id="complete_text" align="center" style="font-size:17px; font-weight:bold;"> </div><br>
			<div id="date_expired_text" align="center" style="font-size:17px; font-weight:bold;"> </div><br>
			
			<!-- button section -->
			<form name="form" class="form-horizontal" id="button_section" method="POST" align="center">
				<input type="hidden" name="command" />
				<input type="hidden" name="teamCode" value="${team.teamCode}" />
				<input type="button" value="참가신청" id="btn_apply" class="btn sbtn" onClick="team_apply()"> &nbsp;
				<input type="button" value="승인대기중" id="btn_waiting" class="btn cbtn" onClick="team_waiting()"> &nbsp;
				<input type="button" value="참가완료" id="btn_complete" class="btn bbtn" onClick="team_complete()"> &nbsp;
			</form>			
			
			<!-- 팀장(모집자) 처리 -->
			<c:if test = "${team.leaderId == sessionScope.userId}">
				<script> hidden('button_section'); </script>
			</c:if>
			
			
			<!-- request객체에 따른 버튼 처리 -->
			<%if(userList.size() == team.getMaxMember()) {%>
				<script>
					btn_off('btn_apply');
					btn_off('btn_waiting');
					btn_off('btn_complete');
					apply_complete('complete_text');
				</script>
			<%} %>
			
			<!-- 모집기간 완료시  -->
			<%
				Calendar calendar = Calendar.getInstance();
				Date current = calendar.getTime();
			%>
			<%if(current.getTime() > team.getEndDay().getTime() ) {%>
				<script>
					btn_off('btn_apply');
					btn_off('btn_waiting');
					btn_off('btn_complete');
					date_expired("date_expired_text");
				</script>
			<%} %>
			
			<!-- 승인 대기중인 상태일때 처리 화면 처리 -->
			<c:forEach var="applyList" items="${applyList}">
				<c:if test = "${applyList.memberId == sessionScope.userId}">
					<c:if test = "${applyList.agree == 0 }"> <!-- 현재 ID가 참가 신청 상태이면 -->
						<script>
							hidden('btn_apply');	hidden('btn_complete');
						</script>
					</c:if>
						
					<c:if test = "${applyList.agree == 1}"> <!-- 현재 ID가 참가 완료 상태이면 -->
						<script>
							hidden('btn_apply');	hidden('btn_waiting');
						</script>
					</c:if>
				</c:if>
			</c:forEach>
			
			
			
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