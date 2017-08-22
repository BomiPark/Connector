package controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.*;
import model.User;
import model.UserManager;


public class InsertAction implements Action {
	/**
	 * request에 저장되어 있는 인자값으로 User객체를 생성하여 
	 * UserManager의 create메써드를 호출하여 새로운 게시물을
	 * 입력한다. 
	 * 입력을 완료한 후 
	 */
	public ActionForward execute(
		HttpServletRequest request,	HttpServletResponse response)
		throws Exception {

		User user = new User();
		
		user.setUserId(request.getParameter("userId"));
		//user.setMember_code(request.getParameter("member_code"));
		user.setPassword(request.getParameter("password"));
		user.setName(request.getParameter("name"));
		user.setGender(request.getParameter("gender"));
		user.setPhone(request.getParameter("phone"));
		user.setAssign(request.getParameter("assign"));
		user.setAge(Integer.parseInt(request.getParameter("age")));
		user.setPortfolio(request.getParameter("portfolio"));
		//user.setWarning(Integer.parseInt(request.getParameter("warning")));

		UserManager manager = UserManager.getInstance();
		manager.create(user);

		ActionForward forward = new ActionForward();
		forward.setPath("user_list.m2");
		forward.setRedirect(true);
				
		return forward;
	}

}
