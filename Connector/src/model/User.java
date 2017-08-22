package model;

/**
 * 사용자 관리를 위하여 필요한 도메인 클래스.
 * USERINFO 테이블의 각 칼럼에 해당하는 setter와 getter를 가진다. 
 */
public class User {
	private String userId = null;
	private String member_code = null;
	private String password = null;
	private String name = null;
	private String gender = null;
	private String phone = null;
	private String assign = null;
	private int age = 0;
	private String portfolio = null;
	private int warning = 0;
	private String image = null;
	private String interFirst = null;
	private String interSecond = null;
	private String interThird = null;
	
	
	public String getInterFirst() {
		return interFirst;
	}

	public void setInterFirst(String interFirst) {
		this.interFirst = interFirst;
	}

	public String getInterSecond() {
		return interSecond;
	}

	public void setInterSecond(String interSecond) {
		this.interSecond = interSecond;
	}

	public String getInterThird() {
		return interThird;
	}

	public void setInterThird(String interThird) {
		this.interThird = interThird;
	}
	
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getMember_code() {
	
		return member_code;
	}

	public void setMember_code(String member_code) {
		this.member_code = member_code;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAssign() {
		return assign;
	}

	public void setAssign(String assign) {
		this.assign = assign;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getPortfolio() {
		return portfolio;
	}

	public void setPortfolio(String portfolio) {
		this.portfolio = portfolio;
	}

	public int getWarning() {
		return warning;
	}

	public void setWarning(int warning) {
		this.warning = warning;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * 비밀번호가 일치하는지 여부를 결정하는 메써드.
	 */
	public boolean isMatchPassword(String inputPassword){
		if ( getPassword().equals(inputPassword)){
			return true;
		} else {
			return false;
		}
	}
}
