package controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.*;
import model.UserManager;


public class LoginAction implements Action {
	public ActionForward execute(
		HttpServletRequest request,	HttpServletResponse response)
		throws Exception {
		String userId = request.getParameter("userId");
		String password = request.getParameter("password");
		ActionForward forward = new ActionForward();

		try {
			//�𵨿� �α��� ó���� ����.
			UserManager manager = UserManager.getInstance();
			manager.login(userId, password);
	
			//���ǿ� ����� ���̵� ����.
			HttpSession session = request.getSession();
			session.setAttribute("userId", userId);
			
			//�̵��� �������� ����.
			//forward.setPath("user_list.m2");
			forward.setPath("index.jsp");
			forward.setRedirect(true);
			
		} catch (Exception e) {
			/* ExistedUserException�̳� PasswordMismatchException�� �߻� ��
			 * �ٽ� login form (login.jsp)�� ����ڿ��� �����ϰ� ���� �޼����� ���
			 */
			request.setAttribute("exception", e);
			forward.setPath("login.jsp");
			forward.setRedirect(false);					
		}		
		
		return forward;
	}
}
