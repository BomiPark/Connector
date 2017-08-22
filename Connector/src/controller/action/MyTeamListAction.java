package controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.*;
import model.Team;
import model.User;
import model.UserManager;

import java.util.List;
import controller.ActionForward;

public class MyTeamListAction implements Action {
	/**
	 * request에 저장되어 있는 인자값으로 User객체를 생성하여 
	 * UserManager의 update메써드를 호출하여 기존의 사용자 정보를 수정한다. 
	 */
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// user = new User();
		List<Team> myTeamList = null;
		 
		HttpSession session = request.getSession();
		String userId = request.getParameter("userId");
		String userId2 = (String)session.getAttribute("userId");

		UserManager manager = UserManager.getInstance();
		 
		if(userId != null){
			myTeamList = manager.getMyTeamList(userId);}
		else
			myTeamList = manager.getMyTeamList(userId2);
		 			
		request.setAttribute("myTeamList", myTeamList);		
	
		ActionForward forward = new ActionForward();
		forward.setPath("myTeamList.jsp");
				
		return forward;
		
	}
}