package controller.action;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.ActionForward;
import model.Team;
import model.MemberAssign;
import model.UserManager;

import controller.*;
import model.Team;
import model.User;
import model.UserManager;


public class TeamMemberListAction implements Action{

	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// user = new User();
		List<MemberAssign> teamMemberList = null;
		List<MemberAssign> myList = null;
		 
		String userId = request.getParameter("userId");
		String teamCode = request.getParameter("team_code");


		UserManager manager = UserManager.getInstance();
		 int n;
		// 함수 수정하기 
		teamMemberList = manager.getTeamMemberList(userId, teamCode);
		
		if (teamMemberList != null) {
			Iterator<MemberAssign> memIter = teamMemberList.iterator();
			int i = 0;
			while (memIter.hasNext()) {
				MemberAssign mem = (MemberAssign) memIter.next();
				
				n =  manager.isRating(mem.getMemberCode(), mem.getRateCode(), teamCode);
				if (n == 0)
					manager.insertRating(mem.getMemberCode(), userId, mem.getTeamCode(), 0.0);
	
				}}
		 
		myList = manager.getMyRatingList(userId, teamCode);
		request.setAttribute("teamMemberList", myList);		
		request.setAttribute("myList", myList);	
		
		ActionForward forward = new ActionForward();
		forward.setPath("team_member_list.jsp");
				
		return forward;
		
	}
}
