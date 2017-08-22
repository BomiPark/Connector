package controller;

import controller.action.Action;
import controller.action.ContestCreateAction;
import controller.action.ContestModifyAction;
import controller.action.ContestRemoveAction;
import controller.action.ContestViewAction;
import controller.action.DeleteAction;
import controller.action.IllegalCommandException;
import controller.action.InsertAction;
import controller.action.InsertFormAction;
import controller.action.ListAction;
import controller.action.LoginAction;
import controller.action.LoginFormAction;
import controller.action.LogoutAction;
import controller.action.MainAction;
import controller.action.MemberRatingAction;
import controller.action.MyTeamListAction;
import controller.action.TeamApplyAction;
import controller.action.TeamApplyAllowAction;
import controller.action.TeamCreateAction;
import controller.action.TeamListAction;
import controller.action.TeamMemberListAction;
import controller.action.TeamViewAction;
import controller.action.UpdateAction;
import controller.action.UpdateFormAction;
import controller.action.UserRecommendAction;
import controller.action.ViewAction;

public class UserCommandFactory {

	private UserCommandFactory() {}

	public static UserCommandFactory getInstance() {
		return new UserCommandFactory();
	}

	/*
	 * 수행해야할 명령어에 해당하는 Action객체를 생성.
	*/
	public Action getAction(String command) throws IllegalCommandException {
		Action action = null;

		if (command.equals("list")) {
			action = new ListAction();
		} else if (command.equals("view")) {
			action = new ViewAction();
		} else if (command.equals("insert")) {
			action = new InsertAction();
		} else if (command.equals("update")) {
			action = new UpdateAction();
		} else if (command.equals("delete")) {
			action = new DeleteAction();
		} else if (command.equals("login")) {
			action = new LoginAction();
		} else if (command.equals("updateForm")) {
			action = new UpdateFormAction();
		} else if (command.equals("insertForm")) {
			action = new InsertFormAction();
		} else if (command.equals("loginForm")) {
			action = new LoginFormAction();
		} else if (command.equals("logout")) {
			action = new LogoutAction();
		} else if (command.equals("teamList")) {
			action = new TeamListAction();
		} else if (command.equals("teamCreate")) {
			action = new TeamCreateAction();
		} else if (command.equals("teamView")) {
			action = new TeamViewAction();
		} else if (command.equals("teamApply")) {
			action = new TeamApplyAction();
		} else if (command.equals("applyAllow")) {
			action = new TeamApplyAllowAction();
		} else if (command.equals("myteamlist")) {
			action = new MyTeamListAction();
		} else if (command.equals("team_member_list")) {
			action = new TeamMemberListAction();
		} else if (command.equals("memberRating")) {
			action = new MemberRatingAction();
		} else if (command.equals("recommend")) {
			action = new UserRecommendAction();
		} else if (command.equals("main")) {
			action = new MainAction();
		} else if (command.equals("contestCreate")) {
			action = new ContestCreateAction();
		} else if (command.equals("contestView")) {
			action = new ContestViewAction();
		} else if (command.equals("contestModify")) {
			action = new ContestModifyAction();
		} else if (command.equals("contestRemove")) {
			action = new ContestRemoveAction();
		} else {
			throw new IllegalCommandException("잘못된 실행명령입니다. 다른 명령을 실행해 주십시요");
		}

		return action;
	}
}
