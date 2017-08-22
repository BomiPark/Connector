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
			//모델에 로그인 처리를 위임.
			UserManager manager = UserManager.getInstance();
			manager.login(userId, password);
	
			//세션에 사용자 이이디 저장.
			HttpSession session = request.getSession();
			session.setAttribute("userId", userId);
			
			//이동할 페이지를 결정.
			//forward.setPath("user_list.m2");
			forward.setPath("index.jsp");
			forward.setRedirect(true);
			
		} catch (Exception e) {
			/* ExistedUserException이나 PasswordMismatchException이 발생 시
			 * 다시 login form (login.jsp)을 사용자에게 전송하고 오류 메세지도 출력
			 */
			request.setAttribute("exception", e);
			forward.setPath("login.jsp");
			forward.setRedirect(false);					
		}		
		
		return forward;
	}
}
