package controller.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import controller.ActionForward;
import model.Team;
import model.User;
import model.UserManager;

public class TeamListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		UserManager manager = UserManager.getInstance();
		List<Team> teamList = null;
		
		String type = request.getParameter("type");
		if(type.equals("recruiting")) {
			
		}
		else if(type.equals("urgent")) {
			
		}
		else if(type.equals("finished")) {
			
		}
		else if(type.equals("myteam")) {
			
		}
		else { //command가 잘못됐을 경우 or command=all
			teamList = manager.findAllTeamList();
		}
		
		request.setAttribute("teamList", teamList);
		
		ActionForward forward = new ActionForward();
		forward.setPath("team_list.jsp");
				
		return forward;
	}
	
}
