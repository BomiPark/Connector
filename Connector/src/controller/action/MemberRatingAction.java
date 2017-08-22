package controller.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.ActionForward;
import model.MemberAssign;
import model.UserManager;

public class MemberRatingAction implements Action{
	
	public ActionForward execute(
			HttpServletRequest request,	HttpServletResponse response)
			throws Exception {
			
				// user = new User();
				List<MemberAssign> teamMemberList = null;
				 
				String member_code = request.getParameter("member_code0");
				String teamCode = request.getParameter("team_code");
				Double avg_rating = Double.parseDouble(request.getParameter("avg_rating0"));
				String index = request.getParameter("index");
				String newMember_code = "member_code";
				String newAvg_rating = "avg_rating";
				int n;
				
				HttpSession session = request.getSession();
				String rateCode = (String)session.getAttribute("userId");
				
				if(index.equals("0")){
					member_code = request.getParameter("member_code0");
					avg_rating = Double.parseDouble(request.getParameter("avg_rating0"));}
				if(index.equals("1")){
					member_code = request.getParameter("member_code1");
					avg_rating = Double.parseDouble(request.getParameter("avg_rating1"));}
				if(index.equals("2")){
					member_code = request.getParameter("member_code2");
					avg_rating = Double.parseDouble(request.getParameter("avg_rating2"));}
				if(index.equals("3")){
					member_code = request.getParameter("member_code3");
					avg_rating = Double.parseDouble(request.getParameter("avg_rating3"));}
				if(index.equals("4")){
					member_code = request.getParameter("member_code4");
					avg_rating = Double.parseDouble(request.getParameter("avg_rating4"));}
				if(index.equals("5")){
					member_code = request.getParameter("member_code5");
					avg_rating = Double.parseDouble(request.getParameter("avg_rating5"));}
				if(index.equals("6")){
					member_code = request.getParameter("member_code6");
					avg_rating = Double.parseDouble(request.getParameter("avg_rating6"));}
				if(index.equals("7")){
					member_code = request.getParameter("member_code7");
					avg_rating = Double.parseDouble(request.getParameter("avg_rating7"));}

				
				UserManager manager = UserManager.getInstance();
				n =  manager.isRating(member_code, rateCode, teamCode);
				// 함수 수정하기 

				manager.UpdateRating(member_code, rateCode, teamCode, avg_rating);

				double aa = manager.setAvgRating(member_code, teamCode);
				
				if(aa < 2)
					manager.isWarning(member_code, teamCode);
				manager.setMemberAssign(member_code, teamCode, aa);
				
				manager.isBlackList(member_code);
				

				
				//request.setAttribute("teamMemberList", teamMemberList);		
			
				ActionForward forward = new ActionForward();
				forward.setPath("team_member_list.m2?command=team_member_list&userId="+ rateCode + "&team_code=" + teamCode);
				
				//forward.setPath("index.jsp");
						
				return forward;
		}

}
