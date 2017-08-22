<%@page contentType="text/html; charset=euc-kr"%>
<%@page import="java.util.*"%>
<%@page import="model.*"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="loginCheck.jsp"%>
<%
	User user = (User) request.getAttribute("user");
	List<User> userList = (List<User>)request.getAttribute("userList");
%>
<html lang="ko">
<head>
<!-- ���� ��Ÿ�� -->
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

<title>����� ����</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel=stylesheet href="../css/user.css" type="text/css">
<script>
function userList() {
	f.command.value = "list";
	f.action = "user_list.m2";
	f.submit();
}
function userModify() {
	f.command.value = "updateForm";
	f.action = "user_modify.m2";
	f.submit();
}

function userRemove() {
	if (confirm("���� �����Ͻðڽ��ϱ�?")) {
		f.command.value = "delete";
		f.action = "user_remove.m2";
		f.submit();
	}
}
	
	function userRate() {
		f.command.value = "myteamlist";
		f.action = "myteamlist.m2";
		f.submit();
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
				<!--  **************************************************************************************  -->
				<!-- �����޴� ����  section -->
				<c:choose>
					<c:when test="${user.userId == sessionScope.userId}">
						<div>
							<!-- <div class="margin_bottom">  -->
							<span class="btitle"> My ���� ���� </span> <br> <span
								class="bcontent">������ ����, Ż���� �� �ֽ��ϴ�.</span>
						</div>
					</c:when>

					<c:otherwise>
						<!-- �� ���� ��û���� ������ Ȯ���� ��� -->
						<div>
							<!-- <div class="margin_bottom">  -->
							<span class="btitle"> ��û�� ���� Ȯ�� </span> <br> <span
								class="bcontent">�� ���� ��û�ڸ� Ȯ���ϰ� �Բ��� ������ ã�ƺ�����.</span>
						</div>
					</c:otherwise>
				</c:choose>

				<hr>

				<form name="f" method="POST" class="form-horizontal">
					<input type="hidden" name="command" /> <input type="hidden"
						name="userId" value="${user.userId}" />

					<div class="form-group">
						<label for="team_board_title" class="col-sm-2 orange">ID</label>
						<div class="col-sm-10">${user.userId}</div>
					</div>
					<div class="form-group">
						<label for="team_board_title" class="col-sm-2 orange">�̸�</label>
						<div class="col-sm-10">${user.name}</div>
					</div>
					<div class="form-group">
						<label for="team_board_title" class="col-sm-2 orange">����</label>
						<div class="col-sm-10">${user.gender}</div>
					</div>
					<div class="form-group">
						<label for="team_board_title" class="col-sm-2 orange">��ȭ��ȣ</label>
						<div class="col-sm-10">${user.phone}</div>
					</div>
					<div class="form-group">
						<label for="team_board_title" class="col-sm-2 orange">�Ҽ�</label>
						<div class="col-sm-10">${user.assign}</div>
					</div>
					<div class="form-group">
						<label for="team_board_title" class="col-sm-2 orange">����</label>
						<div class="col-sm-10">${user.age}</div>
					</div>
					<div class="form-group">
						<label for="team_board_title" class="col-sm-2 orange">��Ʈ������</label>
						<div class="col-sm-10">${user.portfolio}</div>
					</div>

					<c:if test="${user.userId == sessionScope.userId}">
						<table style="width: 100%">
							<tr>
								<td align="center"><input type="button" id="btn_mod"
									value="����" onClick="userModify()"> &nbsp; <input
									type="button" id="btn_rem" value="Ż��" onClick="userRemove()">&nbsp;
									<input type="button" value="�� ����Ʈ����" onClick="userRate()"> &nbsp;
									&nbsp; <c:forEach var="manager" items="${userList}">
										<c:if test="${manager.userId == sessionScope.userId}">
											<input type="button" id="btn_lis" value="���" onClick="userList()">
											
										</c:if>
									</c:forEach></td>
							</tr>
						</table>
					</c:if>
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