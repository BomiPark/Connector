package model;

import java.util.Date;

public class ContestDTO {
	private String subjectCode;
	private String subjectName;
	
	private String contestCode;
	private String contestTitle;
	private String contestContent;
	private Date startDay;
	private Date endDay;
	
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
	public String getContestContent() {
		return contestContent;
	}
	public void setContestContent(String contestContent) {
		this.contestContent = contestContent;
	}
	public Date getStartDay() {
		return startDay;
	}
	public void setStartDay(Date sdt) {
		this.startDay = sdt;
	}
	public Date getEndDay() {
		return endDay;
	}
	public void setEndDay(Date endDay) {
		this.endDay = endDay;
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

}
