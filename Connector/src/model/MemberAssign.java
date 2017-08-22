package model;

public class MemberAssign {

	
	private String teamCode;
	private String memberCode;
	private Double avgRating;
	private String id;
	private String teamName;
	private String userName;
	private String rateCode;

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getTeamCode() {
		return teamCode;
	}
	public void setTeamCode(String teamCode) {
		this.teamCode = teamCode;
	}
	
	public String getRateCode() {
		return rateCode;
	}
	public void setRateCode(String rateCode) {
		this.rateCode = rateCode;
	}
	
	public String getMemberCode() {
		return memberCode;
	}
	
	
	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}
	
	public Double getAvgRating() {
		return avgRating;
	}
	public void setAvgRating(double avgRating) {
		this.avgRating = avgRating;
	}
	
	
}
