package controller.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.ActionForward;
import model.Team;
import model.TeamApply;
import model.User;
import model.UserManager;

public class TeamViewAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		String teamCode = request.getParameter("teamCode");

		UserManager manager = UserManager.getInstance();
		Team team = manager.findTeam(teamCode);
		List<User> userList = manager.findTeamMember(teamCode);
		List<TeamApply> applyList = manager.getTeamApplyList(teamCode);
		
		request.setAttribute("team", team);
		request.setAttribute("userList", userList);
		request.setAttribute("applyList", applyList);
		
		ActionForward forward = new ActionForward();
		forward.setPath("team_view.jsp");
				
		return forward;
	}
	
}
