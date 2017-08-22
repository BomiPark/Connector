package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 * 사용자 관리에서 데이터베이스와의 작업을 전담하는 클래스.
 * UserInfo 테이블에 사용자를 추가, 수정, 삭제, 검색등의 작업을 한다. 
 */
public class UserDAO {
	private DataSource ds;
	
	public UserDAO() throws Exception {
			Context init = new InitialContext();
			ds = (DataSource)init.lookup("java:comp/env/jdbc/OracleDB");
	}	
	/**
	 * 사용자 관리 테이블에 새로운 사용자 생성.
	 */
	public int create(User user) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			StringBuffer insertQuery = new StringBuffer();
			insertQuery.append("INSERT INTO MEMBER(id, member_code, password, name, gender, phone, assign, age, portfolio, warning, image) VALUES ");
			insertQuery.append("(?, 'mem'||member_seq.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?)");		
		
			con = ds.getConnection();
			pstmt = con.prepareStatement(insertQuery.toString());
			pstmt.setString(1, user.getUserId());		
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getName());
			pstmt.setString(4, user.getGender());	
			pstmt.setString(5, user.getPhone());	
			pstmt.setString(6, user.getAssign());	
			pstmt.setInt(7, user.getAge());	
			pstmt.setString(8, user.getPortfolio());	
			pstmt.setInt(9,0);	
			pstmt.setString(10, null);	

			int result = pstmt.executeUpdate();
			createInter(user);
			return result;
		} finally {
			if ( pstmt != null ){
				pstmt.close();
			}
			if ( con != null ){
				con.close();
			}
		}
	}

	/**
	 * 기존의 사용자 사용자 정보를 수정.
	 */
	public int update(User user) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			StringBuffer updateQuery = new StringBuffer();
			updateQuery.append("UPDATE MEMBER SET ");
			updateQuery.append("password=?, name=?, gender=?, phone=?, assign=?, age=?, portfolio=?, warning=? ");
			updateQuery.append("WHERE id=? ");		
		
			con = ds.getConnection();
			pstmt = con.prepareStatement(updateQuery.toString());
				
			pstmt.setString(1, user.getPassword());
			pstmt.setString(2, user.getName());
			pstmt.setString(3, user.getGender());	
			pstmt.setString(4, user.getPhone());	
			pstmt.setString(5, user.getAssign());	
			pstmt.setInt(6, user.getAge());				
			pstmt.setString(7, user.getPortfolio());
			pstmt.setInt(8, 0);
			pstmt.setString(9, user.getUserId());	
			
			int result = pstmt.executeUpdate();
			return result;
		} finally {
			if ( pstmt != null ){
				pstmt.close();
			}
			if ( con != null ){
				con.close();
			}
		}
	}

	/**
	 * 사용자 아이디에 해당하는 사용자를 삭제.
	 */
	public int remove(String userId) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			StringBuffer removeQuery = new StringBuffer();
			removeQuery.append("DELETE FROM MEMBER ");
			removeQuery.append("WHERE id=? ");		
		
			con = ds.getConnection();
			pstmt = con.prepareStatement(removeQuery.toString());
			pstmt.setString(1, userId);
			
			int result = pstmt.executeUpdate();			
			return result;
		} finally {
			if ( pstmt != null ){
				pstmt.close();
			}
			if ( con != null ){
				con.close();
			}
		}
	}

	/**
	 * 사용자 아이디 정보를 데이터베이스에서 찾아 User 도메인 클래스에 
	 * 저장하여 반환.
	 */
	public User findUser(String userId) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			StringBuffer findQuery = new StringBuffer();
			findQuery.append("SELECT ");
			findQuery.append("id, password, name, gender, phone, assign, age, portfolio ");
			findQuery.append("FROM MEMBER ");
			findQuery.append("WHERE id=? ");		
		
			con = ds.getConnection();
			pstmt = con.prepareStatement(findQuery.toString());
			pstmt.setString(1, userId);
			
			rs = pstmt.executeQuery();
			
			User user = null;
			if ( rs.next() ){
				user = new User();
				user.setUserId(userId);
				user.setPassword(rs.getString("password"));
				user.setName(rs.getString("name"));
				user.setGender(rs.getString("gender"));
				user.setPhone(rs.getString("phone"));
				user.setAssign(rs.getString("assign"));
				user.setAge(rs.getInt("age"));
				user.setPortfolio(rs.getString("portfolio"));
			}
			return user;
		} finally {
			if ( pstmt != null ){
				pstmt.close();
			}			
			if ( con != null ){
				con.close();
			}
		}
	}

	/**
	 * 사용자 리스트를 만들기 위한 부분으로 현재 페이지와 
	 * 페이지당 카운트수를 이용하여 해당부분의 사용자만을 List콜렉션에
	 * 저장하여 반환.
	 */
	public List<User> findUserList(int currentPage, int countPerPage)
		throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			StringBuffer findQuery = new StringBuffer();
			findQuery.append("SELECT id, password, name, gender, phone, assign, age, portfolio ");
			findQuery.append("FROM MEMBER ORDER BY id");
	
			con = ds.getConnection();
			pstmt = con.prepareStatement(findQuery.toString(),
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY );		
			rs = pstmt.executeQuery();

			int start = ((currentPage-1) * countPerPage) + 1;
			
			List<User> userList = null;
			if ( (start >= 0) && rs.absolute(start) ) {
				userList = new ArrayList<User>();				
				do {
					User user = new User();
					user.setUserId(rs.getString("id"));
					user.setPassword(rs.getString("password"));
					user.setName(rs.getString("name"));
					user.setGender(rs.getString("gender"));
					user.setPhone(rs.getString("phone"));
					user.setAssign(rs.getString("assign"));
					user.setAge(rs.getInt("age"));
					user.setPortfolio(rs.getString("portfolio"));
					userList.add(user);					
				} while ( (rs.next()) && (--countPerPage > 0));				
			}
			return userList;
		} finally {
			if ( pstmt != null ){
				pstmt.close();
			}			
			if ( con != null ){
				con.close();
			}
		}
	}

	/**
	 * 인자로 전달되는 아이디를 가지는 사용자가 존재하는지의 
	 * 유무를 판별. 
	 */
	public boolean existedUser(String userId) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			StringBuffer existedQuery = new StringBuffer();
			existedQuery.append("SELECT count(*) FROM MEMBER ");
			existedQuery.append("WHERE id=? ");		
		
			con = ds.getConnection();
			pstmt = con.prepareStatement(existedQuery.toString());
			pstmt.setString(1, userId);
			
			rs = pstmt.executeQuery();
			
			int count = 0;
			if ( rs.next() ){
				count = rs.getInt(1);
			}
			if ( count == 1 ) {
				return true;
			} else {
				return false;
			}
		} finally {
			if ( pstmt != null ){
				pstmt.close();
			}			
			if ( con != null ){
				con.close();
			}
		}		
	}
	public int insertRating(String member_code, String rateCode, String teamCode, Double avg_rating) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		rateCode = getMemberCodeByUserId(rateCode);
		try {
			StringBuffer insertQuery = new StringBuffer();
			insertQuery.append("INSERT INTO MEMBER_RATING(eval_code, evaluated, evaluator, team_code, rating) VALUES ");
			insertQuery.append("('eval'||eval_seq.nextval, ?, ?, ?, ?)");		
		
						
			con = ds.getConnection();
			pstmt = con.prepareStatement(insertQuery.toString());
			pstmt.setString(1, member_code);		
			pstmt.setString(2, rateCode);
			pstmt.setString(3, teamCode);
			pstmt.setDouble(4, avg_rating);	


			int result = pstmt.executeUpdate();
			
			return result;
		} finally {
			if ( pstmt != null ){
				pstmt.close();
			}
			if ( con != null ){
				con.close();
			}
		}
	}
	
	public int createInter(User user) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String rs1 = findSubcodeUsingSub(user.getInterFirst());
			String rs2 = findSubcodeUsingSub(user.getInterSecond());
			String rs3 = findSubcodeUsingSub(user.getInterThird());
			
			String code = getMemberCodeByUserId(user.getUserId().toString());
			
			StringBuffer insertQuery = new StringBuffer();
			insertQuery.append("UPDATE MEMBER_INTEREST set rank_first=?, rank_second=?, rank_third=? ");
			insertQuery.append("WHERE member_code='" + code + "'");
		
			con = ds.getConnection();
			pstmt = con.prepareStatement(insertQuery.toString());
			pstmt.setString(1, rs1);
			pstmt.setString(2, rs2);
			pstmt.setString(3, rs3);

			int result = pstmt.executeUpdate();
			
			return result;
		} finally {
			if( rs != null ){
				rs.close();
			}
			if ( pstmt != null ){
				pstmt.close();
			}
			if ( con != null ){
				con.close();
			}
		}
	}
	
	public String findSubcodeUsingSub(String subject) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			StringBuffer findQuery = new StringBuffer();
			findQuery.append("SELECT subject_code ");
			findQuery.append("FROM contest_subject ");
			findQuery.append("WHERE subject_name='" + subject + "'");
		
			con = ds.getConnection();
			pstmt = con.prepareStatement(findQuery.toString());
			rs = pstmt.executeQuery();
			rs.next();
			String result = rs.getString("subject_code");
			
			return result;
		} finally {
			if ( rs != null ){
				rs.close();
			}
			if ( pstmt != null ){
				pstmt.close();
			}
			if ( con != null ){
				con.close();
			}
		}
	}
	
	/**
	 * userId로 memberCode를 얻어오는 함수
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public String getMemberCodeByUserId(String userId) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String member_code = null;
		
		try {
			StringBuffer existedQuery = new StringBuffer();
			existedQuery.append("SELECT MEMBER_CODE FROM MEMBER ");
			existedQuery.append("WHERE id=? ");		
		
			con = ds.getConnection();
			pstmt = con.prepareStatement(existedQuery.toString());
			pstmt.setString(1, userId);
			
			rs = pstmt.executeQuery();
			
			if ( rs.next() ){
				member_code = rs.getString("member_code");
			}
			
			return member_code;
			
		} finally {
			if ( pstmt != null ){
				pstmt.close();
			}			
			if ( con != null ){
				con.close();
			}
		}
	}
	
	/**
	 * member_code로 userId를 구해오는 함수
	 * @param member_code
	 * @return
	 * @throws SQLException
	 */
	public String getUserIdByMemberCode(String member_code) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String user_id = null;
		
		try {
			StringBuffer existedQuery = new StringBuffer();
			existedQuery.append("SELECT id FROM MEMBER ");
			existedQuery.append("WHERE member_code=? ");		
		
			con = ds.getConnection();
			pstmt = con.prepareStatement(existedQuery.toString());
			pstmt.setString(1, member_code);
			
			rs = pstmt.executeQuery();
			
			if ( rs.next() ){
				user_id = rs.getString("id");
			}
			
			return user_id;
			
		} finally {
			if ( pstmt != null ){
				pstmt.close();
			}			
			if ( con != null ){
				con.close();
			}
		}
	}
	
	public String getUserNameByMemberCode(String member_code) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String user_name = null;
		
		try {
			StringBuffer existedQuery = new StringBuffer();
			existedQuery.append("SELECT name FROM MEMBER ");
			existedQuery.append("WHERE member_code=? ");		
		
			con = ds.getConnection();
			pstmt = con.prepareStatement(existedQuery.toString());
			pstmt.setString(1, member_code);
			
			rs = pstmt.executeQuery();
			
			if ( rs.next() ){
				user_name = rs.getString("name");
			}
			
			return user_name;
			
		} finally {
			if ( pstmt != null ){
				pstmt.close();
			}			
			if ( con != null ){
				con.close();
			}
		}
	}
	
	
	/**
	 * 보미거야
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	
public List<Team> getMyTeamList(String userId){
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		//ArrayList<String> myTeamList = new ArrayList();
		
		List<Team> myTeamList =  new ArrayList<Team>();
		
		try {				
			StringBuffer findQuery = new StringBuffer();
			findQuery.append("SELECT ");
			findQuery.append("team_board_title , subject_name , contest_title, MEMBER_ASSIGN.team_code ");
			findQuery.append("FROM MEMBER_ASSIGN, TEAM_BUILDING, MEMBER, CONTEST_SUBJECT, CONTEST_INFO ");
			findQuery.append("WHERE CONTEST_INFO.contest_code = TEAM_BUILDING.contest_code and MEMBER.MEMBER_CODE = MEMBER_ASSIGN.MEMBER_CODE and CONTEST_SUBJECT.subject_code = TEAM_BUILDING.subject_code and TEAM_BUILDING.TEAM_CODE = MEMBER_ASSIGN.TEAM_CODE and id = ?" );	
	
			try {
				con = ds.getConnection();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				pstmt = con.prepareStatement(findQuery.toString());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			pstmt.setString(1, userId);
			
			rs = pstmt.executeQuery();

			while(rs.next()){
				Team team = new Team();
				team.setTeamBoardTitle(rs.getString("team_board_title"));
				team.setSubjectName(rs.getString("subject_name"));
				team.setContestTitle(rs.getString("contest_title"));
				team.setTeamCode(rs.getString("team_code"));
				myTeamList.add(team);	
			}
			
			return myTeamList;
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		finally {
			if ( pstmt != null ){
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}			
			if ( con != null ){
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

		
		return myTeamList;

		
	}
	
	// 보미거야 
public List<MemberAssign> getTeamMemberList(String userId, String teamCode) {

	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	List<MemberAssign> teamMemberList =  new ArrayList<MemberAssign>();
		
	UserManager userManager = UserManager.getInstance();
	String memberCode = "mem13";
	try {
		memberCode = userManager.getMemberCode(userId);
	} catch (SQLException e2) {
		// TODO Auto-generated catch block
		e2.printStackTrace();
	}
	
	try {				
		StringBuffer findQuery = new StringBuffer();
		//findQuery.append("select member.MEMBER_CODE, TEAM_CODE, AVG_RATING, id ");
		//findQuery.append("from member_assign, member ");
		//findQuery.append("where team_code = ? and member_code != ? and member.member_code = member_assign.member_code ");

		findQuery.append("select MEMBER_CODE, TEAM_CODE, AVG_RATING ");
		findQuery.append("from member_assign ");
		findQuery.append("where team_code = ? and member_code != ? ");

		
		try {
			con = ds.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			pstmt = con.prepareStatement(findQuery.toString());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		pstmt.setString(1, teamCode);
		pstmt.setString(2, memberCode);
		
		rs = pstmt.executeQuery();

		

		while(rs.next()){
			MemberAssign mem = new MemberAssign();
			mem.setMemberCode(rs.getString("member_code"));
			mem.setTeamCode(rs.getString("team_code"));
			mem.setAvgRating(rs.getDouble("avg_rating"));
			mem.setRateCode(memberCode);
			String id = getUserIdByMemberCode(rs.getString("member_code"));
			String name = getUserNameByMemberCode(rs.getString("member_code"));
			mem.setUserName(name);
			mem.setId(id);
			
			
			teamMemberList.add(mem);	
		}
	
	
	return teamMemberList;
	
	
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		finally {
			if ( pstmt != null ){
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}			
			if ( con != null ){
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

		
		return teamMemberList;

		
	}


//봄 

public int setMemberAssign(String member_code, String team_code, Double avg) throws SQLException {
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	try {
		StringBuffer existedQuery = new StringBuffer();
		existedQuery.append("update member_assign set avg_rating = ? ");
		existedQuery.append("where member_code = ? and team_code = ? ");		
		con = ds.getConnection();
		pstmt = con.prepareStatement(existedQuery.toString());
		pstmt.setDouble(1, avg);
		pstmt.setString(2, member_code);
		pstmt.setString(3,team_code);
		rs = pstmt.executeQuery();
		

	} finally {
		if ( pstmt != null ){
			pstmt.close();
		}		
		if ( con != null ){
			con.close();
		}
	}
	
	return 1;
}

public double setAvgRating(String member_code, String team_code) throws SQLException {
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	UserManager userManager = UserManager.getInstance();
	try {
		StringBuffer existedQuery = new StringBuffer();
		existedQuery.append("SELECT rating from member_rating ");
		existedQuery.append("where evaluated = ? and team_code = ? ");		
	

		con = ds.getConnection();
		pstmt = con.prepareStatement(existedQuery.toString());
		pstmt.setString(1, member_code);
		pstmt.setString(2, team_code);
		
		rs = pstmt.executeQuery();
		
		int count = 0;
		double sum = 0;
		double avg = 0;
		while ( rs.next() ){
			sum += rs.getDouble("rating");
			count++;
		}
		
		avg = sum/count;
		
		
		//userManager.setMemberAssign(member_code, team_code,avg);

		return avg;

			
	} finally {
		if ( pstmt != null ){
			pstmt.close();
		}			
		if ( con != null ){
			con.close();
		}
	}		
}


// 봄

public int memberRating(String member_code, String teamCode, Double avg_rating, String rateCode) throws SQLException {
	
	Connection con = null;
	PreparedStatement pstmt = null;
	try {
		StringBuffer updateQuery = new StringBuffer();
		updateQuery.append("update member_assign set AVG_RATING = ? ");
		updateQuery.append("where member_code = ? and team_code= ? ");

		con = ds.getConnection();
		pstmt = con.prepareStatement(updateQuery.toString());
		String member_code2= member_code;	
		
		pstmt.setDouble(1, avg_rating);
		pstmt.setString(2, member_code);
		pstmt.setString(3, teamCode);	

		int result = pstmt.executeUpdate();
		
	} finally {
		if ( pstmt != null ){
			pstmt.close();
		}
		if ( con != null ){
			con.close();
		}
	}
	
	return 1;

}
public int isRating(String member_code, String rateCode, String teamCode) throws SQLException {
	// TODO Auto-generated method stub

	int state =1; // 이미 있음 ㅣ
	int result;
	//rateCode = getMemberCodeByUserId(rateCode);
	Connection con = null;
	PreparedStatement pstmt = null;
	try {
		StringBuffer updateQuery = new StringBuffer();
		updateQuery.append("select EVALUATED from member_rating ");
		updateQuery.append("where EVALUATOR = ? and EVALUATED = ? and team_code = ? ");

		
		con = ds.getConnection();
		pstmt = con.prepareStatement(updateQuery.toString());
		String member_code2= member_code;	
		
		pstmt.setString(1, rateCode);
		pstmt.setString(2, member_code);
		pstmt.setString(3, teamCode);	

		result = pstmt.executeUpdate();} 
	
	finally {
			if ( pstmt != null ){
				pstmt.close();
			}
			if ( con != null ){
				con.close();
			}
		}
		
		return result;

	
}
public int UpdateRating(String member_code, String rateCode, String teamCode, Double avg_rating) throws SQLException {
	Connection con = null;
	PreparedStatement pstmt = null;
	
	rateCode = getMemberCodeByUserId(rateCode);
	try {
		StringBuffer insertQuery = new StringBuffer();
		insertQuery.append("update member_rating set rating = ? ");
		insertQuery.append("where team_code = ? and EVALUATED = ? and EVALUATOR =? ");		
	

					
		con = ds.getConnection();
		pstmt = con.prepareStatement(insertQuery.toString());
		pstmt.setDouble(1, avg_rating);	
		pstmt.setString(2, teamCode);		
		pstmt.setString(3, member_code);
		pstmt.setString(4, rateCode);



		int result = pstmt.executeUpdate();
		return result;
	} finally {
		if ( pstmt != null ){
			pstmt.close();
		}
		if ( con != null ){
			con.close();
		}
	}
}
public int isWarning(String member_code, String teamCode) throws SQLException {
	
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	UserManager userManager = UserManager.getInstance();
	
	try {
		StringBuffer existedQuery = new StringBuffer();
		existedQuery.append("update member ");
		existedQuery.append("set WARNING = WARNING+1 where member_code = ? ");		
	

		con = ds.getConnection();
		pstmt = con.prepareStatement(existedQuery.toString());
		pstmt.setString(1, member_code);
		
		rs = pstmt.executeQuery();
		
		//userManager.setMemberAssign(member_code, team_code,avg);

		return 1;

			
	} finally {
		if ( pstmt != null ){
			pstmt.close();
		}			
		if ( con != null ){
			con.close();
		}
	}		
}
//보미
public int getWarning(String member_code) throws SQLException {
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	UserManager userManager = UserManager.getInstance();
	
	try {
		StringBuffer existedQuery = new StringBuffer();
		existedQuery.append("select warning ");
		existedQuery.append("from member where member_code = ? ");		
	

		con = ds.getConnection();
		pstmt = con.prepareStatement(existedQuery.toString());
		pstmt.setString(1, member_code);
		
		rs = pstmt.executeQuery();
		int n =0;
		while ( rs.next() ){
		n = rs.getInt("warning");
		}
		return n;

			
	} finally {
		if ( pstmt != null ){
			pstmt.close();
		}			
		if ( con != null ){
			con.close();
		}
	}		
}

public int isBlackList(String member_code) throws SQLException {
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	UserManager userManager = UserManager.getInstance();
	
	int warn = getWarning(member_code);
	int check = nowBlackList(member_code);
	if(warn >= 2 && check != 1){
	
	try {
		StringBuffer existedQuery = new StringBuffer();
		existedQuery.append("insert into black_list values (SYSDATE, ? )  ");		

		con = ds.getConnection();
		pstmt = con.prepareStatement(existedQuery.toString());
		pstmt.setString(1, member_code);
		
		rs = pstmt.executeQuery();
		
		//userManager.setMemberAssign(member_code, team_code,avg);

		return 1;

			
	} finally {
		if ( pstmt != null ){
			pstmt.close();
		}			
		if ( con != null ){
			con.close();
		}
	}		
}
	return 1;

	
}

public int nowBlackList(String member_code) throws SQLException {
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	UserManager userManager = UserManager.getInstance();
	
	int warn = getWarning(member_code);
	
	if(warn >= 2){
	
	try {
		StringBuffer existedQuery = new StringBuffer();
		existedQuery.append("select member_code from black_list where member_code = ?  ");		

		con = ds.getConnection();
		pstmt = con.prepareStatement(existedQuery.toString());
		pstmt.setString(1, member_code);
		
		rs = pstmt.executeQuery();
		String n = null;
		
		while(rs.next() ){
		
		n = rs.getString("member_code");
}
		if(n != null){
			return 1;
}
		return 0;

			
	} finally {
		if ( pstmt != null ){
			pstmt.close();
		}			
		if ( con != null ){
			con.close();
		}
	}		
}
	return 1;

	
}
public List<MemberAssign> getMyRatingList(String userId, String teamCode) {

	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	List<MemberAssign> myRatingList =  new ArrayList<MemberAssign>();
		
	UserManager userManager = UserManager.getInstance();
	String memberCode = "mem13";
	try {
		memberCode = userManager.getMemberCode(userId);
	} catch (SQLException e2) {
		// TODO Auto-generated catch block
		e2.printStackTrace();
	}
	
	try {				
		StringBuffer findQuery = new StringBuffer();
		//findQuery.append("select member.MEMBER_CODE, TEAM_CODE, AVG_RATING, id ");
		//findQuery.append("from member_assign, member ");
		//findQuery.append("where team_code = ? and member_code != ? and member.member_code = member_assign.member_code ");

		findQuery.append("select EVALUATED, TEAM_CODE, RATING ");
		findQuery.append("from member_rating ");
		findQuery.append("where team_code = ? and EVALUATOR = ? ");


		try {
			con = ds.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			pstmt = con.prepareStatement(findQuery.toString());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		pstmt.setString(1, teamCode);
		pstmt.setString(2, memberCode);
		
		rs = pstmt.executeQuery();

		

		while(rs.next()){
			MemberAssign mem = new MemberAssign();
			mem.setMemberCode(rs.getString("EVALUATED"));
			mem.setTeamCode(rs.getString("team_code"));
			mem.setAvgRating(rs.getDouble("RATING"));
			String id = getUserIdByMemberCode(rs.getString("EVALUATED"));
			String name = getUserNameByMemberCode(rs.getString("EVALUATED"));
			mem.setUserName(name);
			mem.setId(id);
			
			
			myRatingList.add(mem);	
		}
	
	
	return myRatingList;
	
	
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		finally {
			if ( pstmt != null ){
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}			
			if ( con != null ){
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

		
		return myRatingList;

}
	
	public List<User> findManagerList() throws SQLException{
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			StringBuffer findQuery = new StringBuffer();
			findQuery.append("SELECT id ");
			findQuery.append("FROM MANAGER ");
			
	
			con = ds.getConnection();
			pstmt = con.prepareStatement(findQuery.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY );
			rs = pstmt.executeQuery();

			List<User> userList = new ArrayList<User>();
			
			while(rs.next()) {
				User user = new User();
				user.setUserId(rs.getString("id"));
				userList.add(user);
			}
			
			return userList;
			
		} finally {
			if(rs != null){
				rs.close();
			}
			if ( pstmt != null ){
				pstmt.close();
			}			
			if ( con != null ){
				con.close();
			}
		}
	}

}
