package model;

import java.sql.SQLException;
import java.util.List;

/**
 * 사용자 관리 API를 사용하는 개발자들이 직접 접근하게 되는 클래스.
 * UserDAO를 이용하여 데이터베이스에 데이터 조작 작업이 가능하도록 하며,
 * 데이터베이스의 데이터들을 이용하여 비지니스 로직을 수행하는 역할을 한다.
 * 비지니스 로직이 복잡한 경우에는 비지니스 로직만을 전담하는 클래스를 
 * 별도로 둘 수 있다.
 */
public class UserManager {
	private static UserManager userMan = new UserManager();
	private UserDAO userDAO;
	private TeamDAO teamDAO;
	private ContestDAO contestDAO;
	private UserViewcountDAO userViewcountDAO;
	private UserRecommendDAO userRecommendDAO;
	
	private UserManager() {
		try {
			userDAO = new UserDAO();
			teamDAO = new TeamDAO();
			contestDAO = new ContestDAO();
			userViewcountDAO = new UserViewcountDAO();
			userRecommendDAO = new UserRecommendDAO();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
	}
	
	public static UserManager getInstance() {
		return userMan;
	}
	
	public int create(User user) throws SQLException, ExistedUserException {
		if (userDAO.existedUser(user.getUserId())) {
			throw new ExistedUserException(user.getUserId() + "는 존재하는 아이디입니다.");
		}
		return userDAO.create(user);
	}

	public int update(User user) throws SQLException {
		return userDAO.update(user);
	}	

	public int remove(String userId) throws SQLException {
		return userDAO.remove(userId);
	}

	public User findUser(String userId)
		throws SQLException, UserNotFoundException {
		User user = userDAO.findUser(userId);
		
		if (user == null) {
			throw new UserNotFoundException(userId + "는 존재하지 않는 아이디입니다.");
		}		
		return user;
	}

	public List<User> findUserList(int currentPage, int countPerPage)
		throws SQLException {
		return userDAO.findUserList(currentPage, countPerPage);
	}

	public boolean login(String userId, String password)
		throws SQLException, UserNotFoundException, PasswordMismatchException {
		User user = findUser(userId);

		if (!user.isMatchPassword(password)) {
			throw new PasswordMismatchException("비밀번호가 일치하지 않습니다.");
		}
		return true;
	}


	public UserDAO getUserDAO() {
		return this.userDAO;
	}
	
	
	//team section - 은영
	//teamCreate transaction 실행
	public void teamCreate(Team team) throws SQLException {
		teamDAO.createTeam(team);
	}
	
	public List<Team> findUrgentTeamList() throws SQLException {
		return teamDAO.findUrgentTeam();
	}
	
	public List<Team> findAllTeamList() throws SQLException {
		return teamDAO.findAllTeam();
		
	}
	
	public List<Team> findMarketingTeamList() throws SQLException {
		return teamDAO.findMarketingTeam();
	}
	
	public List<Team> findDevelopTeamList() throws SQLException {
		return teamDAO.findDevelopTeam();
	}
	
	public List<Team> findPlanTeamList() throws SQLException {
		return teamDAO.findPlanTeam();
	}
	
	public List<Team> findCultureTeamList() throws SQLException {
		return teamDAO.findCultureTeam();
	}
	
	public List<Team> findDesignTeamList() throws SQLException {
		return teamDAO.findDesignTeam();
	}
	
	
	public String getSubjectName(String subject_code)throws SQLException {
		return teamDAO.getSubjectNameBySubjectCode(subject_code);
	}
	
	public String getContestTitle(String contest_code)throws SQLException {
		return teamDAO.getContestTitleByContestCode(contest_code);
	}
	
	public Team findTeam(String teamCode) throws SQLException {
		return teamDAO.findTeam(teamCode);
	}
	
	public List<User> findTeamMember(String teamCode) throws SQLException {
		return teamDAO.findTeamMember(teamCode);
	}
	
	public int insertApply(TeamApply teamApply) throws SQLException {
		return teamDAO.insertTeamApply(teamApply);
	}
	
	public List<TeamApply> getTeamApplyList(String teamCode) throws SQLException {
		return teamDAO.getTeamApplyList(teamCode);
	}
	
	public int applyUpdateAgree(String applyCode) throws SQLException {
		return teamDAO.applyUpdate(applyCode);
	}
	
	//end team section
	
	//user dao 추가 - 은영
	public String getMemberCode(String userId) throws SQLException {
		return userDAO.getMemberCodeByUserId(userId);
	}
	
	public String getUserId(String member_code) throws SQLException {
		return userDAO.getUserIdByMemberCode(member_code);
	}
	
	public String getUserName(String member_code) throws SQLException {
		return userDAO.getUserNameByMemberCode(member_code);
	}
	
	//end user dao 추가 - 은영
	
	
	/**
	 * 보미거야
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public List<Team> getMyTeamList(String userId)
			throws SQLException {
		return userDAO.getMyTeamList(userId);
	}
	
	// 보미거야 추가한거양
	public List<MemberAssign> getTeamMemberList(String userId, String teamCode) {
		// TODO Auto-generated method stub
		return userDAO.getTeamMemberList(userId, teamCode);
	}
	
	 public List<MemberAssign> getMyRatingList(String userId, String teamCode) throws SQLException {
		  // TODO Auto-generated method stub
		  return userDAO.getMyRatingList(userId, teamCode);
		 } 

	public int memberRating(String member_code, String teamCode, Double avg_rating, String rateCode)
			throws SQLException {

		return userDAO.memberRating(member_code, teamCode, avg_rating, rateCode);

	}

	public int insertRating(String member_code, String rateCode, String teamCode, Double avg_rating)
			throws SQLException {
		// TODO Auto-generated method stub
		return userDAO.insertRating(member_code, rateCode, teamCode, avg_rating);

	}

	public double setAvgRating(String member_code, String team_code) throws SQLException {
		// TODO Auto-generated method stub
		return userDAO.setAvgRating(member_code, team_code);

	}

	public int setMemberAssign(String member_code, String teamCode, Double avg_rating) throws SQLException {
		// TODO Auto-generated method stub
		return userDAO.setMemberAssign(member_code, teamCode, avg_rating);

	}

	public int isRating(String member_code, String rateCode, String teamCode) throws SQLException {
		return userDAO.isRating(member_code, rateCode, teamCode);
	}

	public int UpdateRating(String member_code, String rateCode, String teamCode, Double avg_rating)
			throws SQLException {
		// TODO Auto-generated method stub
		return userDAO.UpdateRating(member_code, rateCode, teamCode, avg_rating);

	}

	public int isWarning(String member_code, String teamCode) throws SQLException {

		return userDAO.isWarning(member_code, teamCode);
	}

	public int isBlackList(String member_code) throws SQLException {
		// TODO Auto-generated method stub
		return userDAO.isBlackList(member_code);
	}

	public List<ContestDTO> findAllContest() throws SQLException{
		return contestDAO.findAllContest();
	}
	
	public List<ContestDTO> findContest(String subject) throws SQLException{
		return contestDAO.findContest(subject);
	}
	
	public int updateViewCount(String userId, String subject) throws SQLException{
		return userViewcountDAO.updateViewCount(userId, subject);
	}
	
	//사람추천
	public List<User> UserRecommend(String userId) throws SQLException{
		return userRecommendDAO.UserRecommend(userId);
	}
	
	public String getSubjectCode(String subjectName) throws SQLException {
		return contestDAO.getSubjectCodeBySubjectName(subjectName);
	}
	
	public int createContest(ContestDTO contestDTO) throws SQLException {
		return contestDAO.createContest(contestDTO);
	}
	public ContestDTO viewContest(String contestCode) throws SQLException {
		return contestDAO.viewContest(contestCode);
	}

	public int updateContest(ContestDTO contestDTO) throws SQLException{
		return contestDAO.updateContest(contestDTO);
	}
	public int removeContest(String contestCode) throws SQLException {
		return contestDAO.removeContest(contestCode);
	}
	public List<User> findManagerList() throws SQLException{
		return userDAO.findManagerList();
	}
	
}
