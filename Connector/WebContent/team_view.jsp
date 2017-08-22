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

<title>�� ����</title>

<script type="text/javascript">
 function team_apply() {
	 if (confirm("�� ���� ���� ��û�� �Ͻðڽ��ϱ�?")) {
		 form.command.value = "teamApply";
		 form.action = "teamApply.m2";
		 form.submit();
	 }
 }
 //������ ������û�� ����ڸ� �������� �� 
 function apply_ok() {
	 if (confirm("���� ��û�� �����Ͻðڽ��ϱ�?")) {
		 f.command.value = "applyAllow";
		 f.action = "applyAllow.m2";
		 f.submit();
	 }
 }
 //��ưȰ��ȭ
 function btn_on(btn) {
  btn = document.getElementById(btn);
  btn.disabled = false;
 }
 //��ư��Ȱ��ȭ
 function btn_off(btn) {
  btn = document.getElementById(btn);
  btn.disabled = 'disabled';
 }
 function hidden(val) {
	 val = document.getElementById(val);
	 val.style.visibility="hidden";
 }
 //���� �Ϸ��
 function apply_complete(text) {
	 text = document.getElementById(text);
	 text.innerHTML = "���� ������ �Ϸ�Ǿ����ϴ�."; 
 }
//���� �Ⱓ �����
 function date_expired(text) {
	 text = document.getElementById(text);
	 text.innerHTML = "���� �Ⱓ�� ����Ǿ����ϴ�."; 
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
			<!-- �����޴� ����  section -->
			<div class="margin_bottom">
				<span class="btitle"> ${ team.teamBoardTitle }</span> <br>
				<span class="bcontent">�Բ��ϰ� ���� ���� ã�ƺ�����.</span>
			<hr>
			</div>
			
			<!-- /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// -->
			
			<form name="f" class="form-horizontal" method="POST">
			  <div class="form-group">
			    <label for="team_board_title" class="col-sm-2">����</label>
			    <div class="col-sm-10">
			      ${ team.teamBoardTitle }
			    </div>
			  </div>
			  <div class="form-group">
			    <label for="team_board_title" class="col-sm-2">������</label>
			    <div class="col-sm-10">
			      ${ team.leaderName }&nbsp;&nbsp;&nbsp;(${ team.leaderId })
			    </div>
			  </div>
			  <div class="form-group">
			    <label for="team_subject" class="col-sm-2">�о�</label>
			    <div class="col-sm-10">
			      ${ team.subjectName }
			    </div>
			  </div>
			  <div class="form-group">
			    <label for="team_contest_title" class="col-sm-2">������</label>
			    <div class="col-sm-10">
			      <b> ${ team.contestTitle } </b>
			    </div>
			  </div>
			  
			  <div class="form-group">
			  	<label for="team_member_count" class="col-sm-2">�����ο�</label>
			  	<div class="col-sm-4">
			    	${ team.maxMember }
			    </div>
			    
			    <label for="team_member_count" class="col-sm-2">�����ο�</label>
			  	<div class="col-sm-4">
			    	<b> ${ team.currentMember } </b>
			    </div>
			  </div>
			  <div class="form-group">
			    <label for="team_board_contest" class="col-sm-2">����</label>
			    <div class="col-sm-10">
			      ${ team.teamBoardContent }
			    </div>
			  </div>
			  
			  <hr>
			  <div class="form-group">
			    <label for="team_board_contest" class="col-sm-2">������</label>
			    <div class="col-sm-10">
			   	 	<c:forEach var="user" items="${userList}">
			   	 		${user.name} (${ user.userId })&nbsp;&nbsp;&nbsp;
			   	 	</c:forEach>
			   	 	
			    </div>
			  </div>
			  
			  <!-- �������� ��� ���� ����� ó�� -->
			  <c:if test = "${team.leaderId == sessionScope.userId}">
				  <hr>
				  <div class="form-group" id="waiting">
				    <label for="team_board_contest" class="col-sm-2">���� ���</label>
				    <div class="col-sm-10">
				   	 	<c:forEach var="applyList" items="${applyList}">
				   	 		<c:if test = "${applyList.agree == 0 }">
				   	 		<div>
					   	 		${applyList.memberName} <a href="user_view.m2?userId=${ applyList.memberId }&command=view"> (${ applyList.memberId }) </a> &nbsp;
					   	 		<input type="hidden" name="applyCode" value="${applyList.applyCode}" />
					   	 		<input type="hidden" name="teamCode" value="${team.teamCode}" />
					   	 		<input type="hidden" name="command" value="applyAllow" />
					   	 		<input type="button" value="����" class="btn okbtn" onClick="apply_ok()"><br><br>
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
				<input type="button" value="������û" id="btn_apply" class="btn sbtn" onClick="team_apply()"> &nbsp;
				<input type="button" value="���δ����" id="btn_waiting" class="btn cbtn" onClick="team_waiting()"> &nbsp;
				<input type="button" value="�����Ϸ�" id="btn_complete" class="btn bbtn" onClick="team_complete()"> &nbsp;
			</form>			
			
			<!-- ����(������) ó�� -->
			<c:if test = "${team.leaderId == sessionScope.userId}">
				<script> hidden('button_section'); </script>
			</c:if>
			
			
			<!-- request��ü�� ���� ��ư ó�� -->
			<%if(userList.size() == team.getMaxMember()) {%>
				<script>
					btn_off('btn_apply');
					btn_off('btn_waiting');
					btn_off('btn_complete');
					apply_complete('complete_text');
				</script>
			<%} %>
			
			<!-- �����Ⱓ �Ϸ��  -->
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
			
			<!-- ���� ������� �����϶� ó�� ȭ�� ó�� -->
			<c:forEach var="applyList" items="${applyList}">
				<c:if test = "${applyList.memberId == sessionScope.userId}">
					<c:if test = "${applyList.agree == 0 }"> <!-- ���� ID�� ���� ��û �����̸� -->
						<script>
							hidden('btn_apply');	hidden('btn_complete');
						</script>
					</c:if>
						
					<c:if test = "${applyList.agree == 1}"> <!-- ���� ID�� ���� �Ϸ� �����̸� -->
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