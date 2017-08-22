package controller.action;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.*;
import model.ContestDTO;
import model.UserManager;

public class MainAction implements Action{
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		HttpSession session = request.getSession();
		Object user = session.getAttribute("userId");
		
		UserManager manager = UserManager.getInstance();
		String userCode = null;
		
		if(user==null)
		{
			userCode = null;
		}
		else
		{
			String userst = user.toString().toLowerCase();
			userCode = manager.getMemberCode(userst);
		}
		
		ActionForward forward = new ActionForward();
		
		List<ContestDTO> contestDto = null;
		
		String type = request.getParameter("type");
		if(type.equals("design")) {
			contestDto = manager.findContest("Design");
			if(userCode!=null){
				manager.updateViewCount(userCode, type);
			}
		}
		else if(type.equals("marketing")) {
			contestDto = manager.findContest("Marketing");
			if(userCode!=null){
				manager.updateViewCount(userCode, type);
			}
		}
		else if(type.equals("develop")) {
			contestDto = manager.findContest("Develop");
			if(userCode!=null){
				manager.updateViewCount(userCode, type);
			}
		}
		else if(type.equals("plan")) {
			contestDto = manager.findContest("Plan");
			if(userCode!=null){
				manager.updateViewCount(userCode, type);
			}
		}
		else if(type.equals("culture")) {
			contestDto = manager.findContest("Culture");
			if(userCode!=null){
				manager.updateViewCount(userCode, type);
			}
		}
		else { //command가 잘못됐을 경우 or command=all
			contestDto = manager.findAllContest();
		}
		
		request.setAttribute("contestDto", contestDto);
		forward.setPath("index.jsp");
	
		return forward;
			
	}

}
