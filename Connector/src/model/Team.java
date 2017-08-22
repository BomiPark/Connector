package model;

import java.util.Date;
//Team ฐüทร DTO Class

public class Team {
	
	private String teamCode = null;
	private String leaderCode = null;
	private String leaderId = null;
	private String leaderName = null;
	private String teamBoardTitle = null;
	private String teamBoardContent = null;
	private String subjectCode = null;
	private String subjectName = null;
	private String contestCode = null;
	private String contestTitle = null;
	private Date startDay = null;
	private Date endDay = null;
	private int maxMember = 0;
	private int currentMember = 0;
	private Date insertDate = null;
	private int completeState = 0;
	
	public String getTeamCode() {
		return teamCode;
	}
	public void setTeamCode(String teamCode) {
		this.teamCode = teamCode;
	}
	public String getLeaderCode() {
		return leaderCode;
	}
	public void setLeaderCode(String leaderCode) {
		this.leaderCode = leaderCode;
	}
	public String getLeaderId() {
		return leaderId;
	}
	public void setLeaderId(String leaderId) {
		this.leaderId = leaderId;
	}
	public String getLeaderName() {
		return leaderName;
	}
	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}
	public String getTeamBoardTitle() {
		return teamBoardTitle;
	}
	public void setTeamBoardTitle(String teamBoardTitle) {
		this.teamBoardTitle = teamBoardTitle;
	}
	public String getTeamBoardContent() {
		return teamBoardContent;
	}
	public void setTeamBoardContent(String teamBoardContent) {
		this.teamBoardContent = teamBoardContent;
	}
	public String getSubjectCode() {
		return subjectCode;
	}
	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public String getContestCode() {
		return contestCode;
	}
	public void setContestCode(String contestCode) {
		this.contestCode = contestCode;
	}
	public String getContestTitle() {
		return contestTitle;
	}
	public void setContestTitle(String contestTitle) {
		this.contestTitle = contestTitle;
	}
	public Date getStartDay() {
		return startDay;
	}
	public void setStartDay(Date startDay) {
		this.startDay = startDay;
	}
	public Date getEndDay() {
		return endDay;
	}
	public void setEndDay(Date endDay) {
		this.endDay = endDay;
	}
	public int getMaxMember() {
		return maxMember;
	}
	public void setMaxMember(int maxMember) {
		this.maxMember = maxMember;
	}
	public int getCurrentMember() {
		return currentMember;
	}
	public void setCurrentMember(int currentMember) {
		this.currentMember = currentMember;
	}
	public Date getInsertDate() {
		return insertDate;
	}
	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}
	public int getCompleteState() {
		return completeState;
	}
	public void setCompleteState(int completeState) {
		this.completeState = completeState;
	}
	
	
}
