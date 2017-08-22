package controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.ActionForward;
import model.TeamApply;
import model.User;
import model.UserManager;

public class TeamApplyAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		UserManager manager = UserManager.getInstance();
		
		HttpSession session = request.getSession();
		
		TeamApply teamApply = new TeamApply();
		
		teamApply.setAgree(0);
		teamApply.setTeamCode(request.getParameter("teamCode"));
		teamApply.setMemberId((String)session.getAttribute("userId"));
		teamApply.setMemberCode(manager.getMemberCode(teamApply.getMemberId()));
		teamApply.setMemberName(manager.getUserName(teamApply.getMemberCode()));

		manager.insertApply(teamApply);
		
		//team_apply table에 insert후 다시 team_view를 보여줌
		ActionForward forward = new ActionForward();
		forward.setPath("team_view.m2?teamCode="+request.getParameter("teamCode")+"&command=teamView");
		forward.setRedirect(true);
				
		
		return forward;
		
	}

}
