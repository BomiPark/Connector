package controller.action;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.ActionForward;
import model.Team;
import model.UserManager;

public class TeamCreateAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		//team create form ��û�� ���
		if(request.getMethod().equals("GET")) {
			ActionForward forward = new ActionForward();
			forward.setPath("team_create.jsp");
			forward.setRedirect(true);
			return forward;
		}
		
		
		//team create submit ��û�� ���
		
		UserManager manager = UserManager.getInstance();
		
		Team team = new Team();
		
		//team create �ð��� dto�� insert �ϱ� ���� ����
		Calendar calendar = Calendar.getInstance();
		Date insertDate = calendar.getTime();
				
		HttpSession session = request.getSession();
		
		team.setLeaderCode(manager.getMemberCode((String)session.getAttribute("userId"))); //id�� code�� ���� dto�� ����
		team.setTeamBoardTitle(request.getParameter("team_board_title"));
		team.setTeamBoardContent(request.getParameter("team_board_contest"));
		//team.setSubjectCode(request.getParameter("subjectCode"));
		team.setSubjectCode("sub2"); //Subject DAO�� �����ؾ���
		team.setSubjectName(request.getParameter("team_subject"));
		//team.setContestCode(request.getParameter("contestCode"));
		team.setContestCode("con1"); //contest dao�� �����ؾ���
		team.setContestTitle(request.getParameter("team_contest_title"));
		team.setStartDay(insertDate); //������ ���� DAO�� 
		team.setEndDay(insertDate); //������ ���� DAO�� 
		team.setMaxMember(Integer.parseInt(request.getParameter("team_member_count"))+1); //int�� ��ȯ �� ����
		team.setCurrentMember(0); //team create�� �ʱⰪ�� ���常 ������ 1
		team.setInsertDate(insertDate); //�Խñ� �ۼ� �ð��� ���� �ð����� 
		team.setCompleteState(0); //team create�� �� ���� �Ϸ���� false

		
		manager.teamCreate(team);
		
		ActionForward forward = new ActionForward();
		forward.setPath("team_list.m2?command=teamList&type=all");
		forward.setRedirect(true);
		
		return forward;
		
	}
}
