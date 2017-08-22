package controller.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.*;
import model.User;
import model.UserManager;


public class ViewAction implements Action {
	/**
	 * UserManager�� findUser�޽�带 ȣ���Ͽ� ��ȯ�� 
	 * User�� response�� �����ϴ� �ҽ��ڵ尡 ����. 
	 * user_view.jsp���� response�� ����� User�� ����ϰ� �ȴ�.
	 */
	public ActionForward execute(
		HttpServletRequest request,	HttpServletResponse response)
		throws Exception {
			
		String userId = request.getParameter("userId");

		UserManager manager = UserManager.getInstance();
		User user = manager.findUser(userId);
		List<User> userList = manager.findManagerList();
		request.setAttribute("user", user);		
		request.setAttribute("userList", userList);	
		
		ActionForward forward = new ActionForward();
		forward.setPath("user_view.jsp");
				
		return forward;
	}
}
