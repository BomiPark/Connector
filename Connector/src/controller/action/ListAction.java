package controller.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.*;
import model.*;


public class ListAction implements Action {

	/**
	 * UserManager의 list메써드를 호출하여 
	 * List를 response에 저장하는 소스코드가 들어간다. 
	 * list.jsp에서 response에 저장된 List객체를 이용한다.
	 */
	public ActionForward execute(
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		String currentPageStr = request.getParameter("currentPage");		

		int currentPage = 1;
		if ( (currentPageStr != null) && (!currentPageStr.equals("")) ) {
			currentPage = Integer.parseInt(currentPageStr);
		}

		int countPerPage = 30;

		UserManager manager = UserManager.getInstance();
		List<User> userList = manager.findUserList(currentPage, countPerPage);
		
		//userList객체를 response에 저장하여 전달.
		request.setAttribute("userList", userList);		
		
		//이동할 페이지를 결정.
		ActionForward forward = new ActionForward();
		forward.setPath("user_list.jsp");
				
		return forward;
	}
}
