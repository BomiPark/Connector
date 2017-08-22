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
	
		//team create form 요청일 경우
		if(request.getMethod().equals("GET")) {
			ActionForward forward = new ActionForward();
			forward.setPath("team_create.jsp");
			forward.setRedirect(true);
			return forward;
		}
		
		
		//team create submit 요청일 경우
		
		UserManager manager = UserManager.getInstance();
		
		Team team = new Team();
		
		//team create 시간을 dto에 insert 하기 위한 변수
		Calendar calendar = Calendar.getInstance();
		Date insertDate = calendar.getTime();
				
		HttpSession session = request.getSession();
		
		team.setLeaderCode(manager.getMemberCode((String)session.getAttribute("userId"))); //id로 code를 얻어와 dto에 저장
		team.setTeamBoardTitle(request.getParameter("team_board_title"));
		team.setTeamBoardContent(request.getParameter("team_board_contest"));
		//team.setSubjectCode(request.getParameter("subjectCode"));
		team.setSubjectCode("sub2"); //Subject DAO로 수정해야함
		team.setSubjectName(request.getParameter("team_subject"));
		//team.setContestCode(request.getParameter("contestCode"));
		team.setContestCode("con1"); //contest dao로 수정해야함
		team.setContestTitle(request.getParameter("team_contest_title"));
		team.setStartDay(insertDate); //서히가 만든 DAO로 
		team.setEndDay(insertDate); //서히가 만든 DAO로 
		team.setMaxMember(Integer.parseInt(request.getParameter("team_member_count"))+1); //int로 변환 후 저장
		team.setCurrentMember(0); //team create시 초기값은 팀장만 포함한 1
		team.setInsertDate(insertDate); //게시글 작성 시간을 현재 시간으로 
		team.setCompleteState(0); //team create시 팀 구성 완료상태 false

		
		manager.teamCreate(team);
		
		ActionForward forward = new ActionForward();
		forward.setPath("team_list.m2?command=teamList&type=all");
		forward.setRedirect(true);
		
		return forward;
		
	}
}
