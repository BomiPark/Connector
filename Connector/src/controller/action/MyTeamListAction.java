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
	 * request�� ����Ǿ� �ִ� ���ڰ����� User��ü�� �����Ͽ� 
	 * UserManager�� update�޽�带 ȣ���Ͽ� ������ ����� ������ �����Ѵ�. 
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