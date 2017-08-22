package controller.action;

import java.util.Date;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.ActionForward;
import controller.action.Action;
import model.ContestDTO;
import model.User;
import model.UserManager;

public class ContestModifyAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// contest update form 夸没老 版快
		if (request.getMethod().equals("GET")) {

			String contestCode = request.getParameter("contestCode");
			UserManager manager = UserManager.getInstance();
			ContestDTO contestDTO = manager.viewContest(contestCode);
			request.setAttribute("contestDTO", contestDTO);
			ActionForward forward = new ActionForward();
			forward.setPath("contest_update.jsp");
			forward.setRedirect(false);
			return forward;

		}
		// contest update submit 夸没老 版快

		UserManager manager = UserManager.getInstance();
		
		ContestDTO contestDTO = new ContestDTO();

		contestDTO.setContestTitle(request.getParameter("contestTitle"));
		contestDTO.setContestCode(request.getParameter("contestCode"));
		contestDTO.setContestContent(request.getParameter("contestContent"));
		String start = request.getParameter("startDay");
		String end = request.getParameter("endDay");
		
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
		
		java.util.Date sdt = format.parse(start);
		java.util.Date edt = format.parse(end);	
		contestDTO.setStartDay(sdt);
		contestDTO.setEndDay(edt);
		contestDTO.setSubjectName(request.getParameter("subjectName"));
		contestDTO.setSubjectCode(manager.getSubjectCode(contestDTO.getSubjectName()));
		
		manager.updateContest(contestDTO);
		request.setAttribute("contestDTO", contestDTO);

		ActionForward forward = new ActionForward();
		forward.setPath("index.jsp?command=main");
		// forward.setRedirect(true);

		return forward;

	}
}

