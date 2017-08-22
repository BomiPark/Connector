package model;

public class UserRateDTO {
	private String userCode;
	private double rateMarketing;
	private double rateDevelop;
	private double ratePlan;
	private double rateCulture;
	private double rateDesign;
	
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public double getRateMarketing() {
		return rateMarketing;
	}
	public void setRateMarketing(double rateMarketing) {
		this.rateMarketing = rateMarketing;
	}
	public double getRateDevelop() {
		return rateDevelop;
	}
	public void setRateDevelop(double rateDevelop) {
		this.rateDevelop = rateDevelop;
	}
	public double getRatePlan() {
		return ratePlan;
	}
	public void setRatePlan(double ratePlan) {
		this.ratePlan = ratePlan;
	}
	public double getRateCulture() {
		return rateCulture;
	}
	public void setRateCulture(double rateCulture) {
		this.rateCulture = rateCulture;
	}
	public double getRateDesign() {
		return rateDesign;
	}
	public void setRateDesign(double rateDesign) {
		this.rateDesign = rateDesign;
	}

}
