package controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.*;
import model.User;
import model.UserManager;


public class UpdateFormAction implements Action {
	/**
	 * request�� ����Ǿ� �ִ� ���ڰ����� User��ü�� �����Ͽ� 
	 * UserManager�� update�޽�带 ȣ���Ͽ� ������ ����� ������ �����Ѵ�. 
	 */
	public ActionForward execute(
		HttpServletRequest request,	HttpServletResponse response)
		throws Exception {
		String userId = request.getParameter("userId");

		UserManager manager = UserManager.getInstance();
		User user = manager.findUser(userId);

		request.setAttribute("user", user);

		ActionForward forward = new ActionForward();
		forward.setPath("user_modify.jsp");

		return forward;
	}
}