package model;

public class UserViewcountDTO {
	private String member_code;
	
	public String getMember_code() {
		return member_code;
	}
	public void setMember_code(String member_code) {
		this.member_code = member_code;
	}
	private int count_marketing;
	private int count_develop;
	private int count_plan;
	private int count_culture;
	private int count_design;
	
	public int getCount_marketing() {
		return count_marketing;
	}
	public void setCount_marketing(int count_marketing) {
		this.count_marketing = count_marketing;
	}
	public int getCount_develop() {
		return count_develop;
	}
	public void setCount_develop(int count_develop) {
		this.count_develop = count_develop;
	}
	public int getCount_plan() {
		return count_plan;
	}
	public void setCount_plan(int count_plan) {
		this.count_plan = count_plan;
	}
	public int getCount_culture() {
		return count_culture;
	}
	public void setCount_culture(int count_culture) {
		this.count_culture = count_culture;
	}
	public int getCount_design() {
		return count_design;
	}
	public void setCount_design(int count_design) {
		this.count_design = count_design;
	}
	

}
