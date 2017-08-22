package controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.ActionForward;
import model.UserManager;

public class TeamApplyAllowAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		UserManager manager = UserManager.getInstance();
		manager.applyUpdateAgree(request.getParameter("applyCode"));
		
		
		ActionForward forward = new ActionForward();
		forward.setPath("team_view.m2?teamCode="+request.getParameter("teamCode")+"&command=teamView");
		forward.setRedirect(true);
		
		return forward;
	}
	
}
