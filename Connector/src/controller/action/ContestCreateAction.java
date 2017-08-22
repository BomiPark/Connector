package controller.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.ActionForward;
import model.ContestDTO;
import model.UserManager;

public class ContestCreateAction implements Action{
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		//contest create form 夸没老 版快
		if(request.getMethod().equals("GET")) {
			ActionForward forward = new ActionForward();
			forward.setPath("contest_create.jsp");
			forward.setRedirect(false);
			return forward;
		}
		//contest create submit 夸没老 版快
		
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
				
				manager.createContest(contestDTO);
				request.setAttribute("contestDTO", contestDTO);

				ActionForward forward = new ActionForward();
				forward.setPath("index.jsp?command=main");
				// forward.setRedirect(true);

				return forward;
				
			}
}
