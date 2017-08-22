package controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.ActionForward;
import controller.action.Action;
import model.UserManager;

public class ContestRemoveAction implements Action {

	public ActionForward execute(
			HttpServletRequest request,	HttpServletResponse response)
			throws Exception {
			String contestCode = request.getParameter("contestCode");
			
			UserManager manager = UserManager.getInstance();
			manager.removeContest(contestCode);

			ActionForward forward = new ActionForward();
			forward.setPath("index.jsp?command=main");
			forward.setRedirect(true);
				
			return forward;		
		}

}
