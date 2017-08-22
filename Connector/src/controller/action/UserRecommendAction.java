package controller.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.ActionForward;
import model.ContestDTO;
import model.User;
import model.UserManager;

public class UserRecommendAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub

		HttpSession session = request.getSession();
		Object user = session.getAttribute("userId");
		
		UserManager manager = UserManager.getInstance();
		String userCode = null;
		List<User> recomUser = new ArrayList<User>();
		
		if(user==null)
		{
			userCode = null;
		}
		else
		{
			String userst = user.toString().toLowerCase();
			userCode = manager.getMemberCode(userst);
			recomUser = manager.UserRecommend(userCode);
			request.setAttribute("RecomUser", recomUser);
		}
		
		ActionForward forward = new ActionForward();
		
		forward.setPath("user_recommend.jsp");

		return forward;
	}

}
