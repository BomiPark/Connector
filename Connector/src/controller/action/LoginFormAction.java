package controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.*;


public class LoginFormAction implements Action {
	public ActionForward execute(
		HttpServletRequest request,	HttpServletResponse response)
		throws Exception {			
		ActionForward forward = new ActionForward();
		forward.setPath("login.jsp");
		forward.setRedirect(true);

		return forward;		
	}
}
