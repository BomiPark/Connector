package controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.ActionForward;
import controller.action.Action;
import model.ContestDTO;
import model.UserManager;

public class ContestViewAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		String contestCode = request.getParameter("contestCode");
		//contestCode  = new String(contest.getBytes("ISO8859_1"),"euc-kr");
		UserManager manager = UserManager.getInstance();
		ContestDTO contestDTO = manager.viewContest(contestCode);//공모전 이름 클릭하면 상세 페이지
		request.setAttribute("contestDTO", contestDTO);		
		
		ActionForward forward = new ActionForward();
		forward.setPath("contest_view.jsp");
				
		return forward;
	}

}
