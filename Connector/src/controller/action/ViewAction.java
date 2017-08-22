package controller.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.*;
import model.User;
import model.UserManager;


public class ViewAction implements Action {
	/**
	 * UserManager의 findUser메써드를 호출하여 반환된 
	 * User를 response에 저장하는 소스코드가 들어간다. 
	 * user_view.jsp에서 response에 저장된 User를 사용하게 된다.
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
