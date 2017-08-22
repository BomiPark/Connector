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

public class TeamDAO {
	private DataSource ds;
	
	public TeamDAO() throws Exception {
			Context init = new InitialContext();
			ds = (DataSource)init.lookup("java:comp/env/jdbc/OracleDB");
	}	
	

		public int createTeam(Team team) throws SQLException {
			Connection con = null;
			PreparedStatement pstmt1 = null;
			PreparedStatement pstmt2 = null;
			try {
				con = ds.getConnection();
				con.setAutoCommit(false);
				
				StringBuffer insertQuery = new StringBuffer();
				insertQuery.append("INSERT INTO team_building(team_code, leader_code, team_board_title, team_board_content, subject_code, contest_code, start_day, end_day, max_member, current_member, insert_date, complete_state) VALUES ");
				insertQuery.append("('team'||team_seq.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");		
			
				pstmt1 = con.prepareStatement(insertQuery.toString());	
				pstmt1.setString(1, team.getLeaderCode());
				pstmt1.setString(2, team.getTeamBoardTitle());
				pstmt1.setString(3, team.getTeamBoardContent());	
				pstmt1.setString(4, team.getSubjectCode());	
				pstmt1.setString(5, team.getContestCode());	
				pstmt1.setDate(6, new java.sql.Date(team.getStartDay().getTime()));	
				pstmt1.setDate(7, new java.sql.Date(team.getEndDay().getTime()));	
				pstmt1.setInt(8,team.getMaxMember());	
				pstmt1.setInt(9, team.getCurrentMember());
				pstmt1.setDate(10, new java.sql.Date(team.getInsertDate().getTime()));
				pstmt1.setInt(11, team.getCompleteState());
				
				//날짜  참고
				//pstmt.setDate(4, new java.sql.Date(java.util.Date.getTime()) );
				
				int result1 = pstmt1.executeUpdate();
				
				StringBuffer assignInsertQuery = new StringBuffer();
				assignInsertQuery.append("INSERT INTO member_assign(team_code, member_code, avg_rating) VALUES ");
				assignInsertQuery.append("('team'||(team_seq.nextval-1), ?, 0)");
			
				pstmt2 = con.prepareStatement(assignInsertQuery.toString());	
				//pstmt.setString(1, team.getTeamCode());
				pstmt2.setString(1, team.getLeaderCode());
				
				int result2 = pstmt2.executeUpdate();
				
				if(result1 == 1 && result2 == 1)
					con.commit();
				else
					con.rollback();
					
				return result1;
				
				
			} finally {
				
				con.setAutoCommit(true);
				
				if ( pstmt1 != null ){
					pstmt1.close();
				}
				if( pstmt2 != null ){
					pstmt2.close();
				}
				if ( con != null ){
					con.close();
				}
			}
		}
		
	/**
	 * 마감임박 팀 List를 가져옴
	 * 마감되지 않은 팀 && ( ( 현재멤버수 >= 최대멤버수*70%) or ( 마감일이 지나지 않은 팀 && 마감 14이내인 팀 ) )
	 */
	public List<Team> findUrgentTeam() throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			StringBuffer findQuery = new StringBuffer();
			findQuery.append("SELECT team_code, leader_code, team_board_title, team_board_content, subject_code, contest_code, start_day, end_day, max_member, current_member, insert_date, complete_state ");
			findQuery.append("FROM TEAM_BUILDING ");
			findQuery.append("WHERE (current_member < max_member) AND ");
			findQuery.append("( ");
			findQuery.append("(current_member >= max_member*0.7) ");
			findQuery.append("or ");
			findQuery.append("( (end_day > sysdate) and (end_day - sysdate <= 14) ) ");
			findQuery.append(") ");
			findQuery.append("ORDER BY insert_date");
		
			con = ds.getConnection();
			pstmt = con.prepareStatement(findQuery.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY );
			rs = pstmt.executeQuery();

			UserManager manager = UserManager.getInstance();
				
			List<Team> teamList = new ArrayList<Team>();
				
			while(rs.next()) {
				Team team = new Team();
				team.setTeamCode(rs.getString("team_code"));
				team.setLeaderCode(rs.getString("leader_code"));
				team.setLeaderId(manager.getUserId(team.getLeaderCode()));
				team.setLeaderName(manager.getUserName(team.getLeaderCode()));
				team.setTeamBoardTitle(rs.getString("team_board_title"));
				team.setTeamBoardContent(rs.getString("team_board_content"));
				team.setSubjectCode(rs.getString("subject_code"));
				team.setSubjectName(manager.getSubjectName(team.getSubjectCode()));
				team.setContestCode(rs.getString("contest_code"));
				team.setContestTitle(manager.getContestTitle(team.getContestCode()));
				team.setStartDay(rs.getDate("start_day"));
				team.setEndDay(rs.getDate("end_day"));
				team.setMaxMember(rs.getInt("max_member"));
				team.setCurrentMember(rs.getInt("current_member"));
				team.setInsertDate(rs.getDate("insert_date"));
				team.setCompleteState(rs.getInt("complete_state"));
				
				teamList.add(team);
			}
			return teamList;
		
		}finally {
			if ( pstmt != null ){
				pstmt.close();
			}			
			if ( con != null ){
				con.close();
			}
		}
	}
	
	/**
	 * 모든 팀을 찾아와 작성시간 기준으로 정렬하여 List형태로 반환
	 */
	public List<Team> findAllTeam() throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			StringBuffer findQuery = new StringBuffer();
			findQuery.append("SELECT team_code, leader_code, team_board_title, team_board_content, subject_code, contest_code, start_day, end_day, max_member, current_member, insert_date, complete_state ");
			findQuery.append("FROM TEAM_BUILDING ");
			findQuery.append("ORDER BY insert_date");
		
			con = ds.getConnection();
			pstmt = con.prepareStatement(findQuery.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY );
			rs = pstmt.executeQuery();

			UserManager manager = UserManager.getInstance();
				
			List<Team> teamList = new ArrayList<Team>();
				
			while(rs.next()) {
				Team team = new Team();
				team.setTeamCode(rs.getString("team_code"));
				team.setLeaderCode(rs.getString("leader_code"));
				team.setLeaderId(manager.getUserId(team.getLeaderCode()));
				team.setLeaderName(manager.getUserName(team.getLeaderCode()));
				team.setTeamBoardTitle(rs.getString("team_board_title"));
				team.setTeamBoardContent(rs.getString("team_board_content"));
				team.setSubjectCode(rs.getString("subject_code"));
				team.setSubjectName(manager.getSubjectName(team.getSubjectCode()));
				team.setContestCode(rs.getString("contest_code"));
				team.setContestTitle(manager.getContestTitle(team.getContestCode()));
				team.setStartDay(rs.getDate("start_day"));
				team.setEndDay(rs.getDate("end_day"));
				team.setMaxMember(rs.getInt("max_member"));
				team.setCurrentMember(rs.getInt("current_member"));
				team.setInsertDate(rs.getDate("insert_date"));
				team.setCompleteState(rs.getInt("complete_state"));
				
				teamList.add(team);
			}
			return teamList;
		
		}finally {
			if ( pstmt != null ){
				pstmt.close();
			}			
			if ( con != null ){
				con.close();
			}
		}
	}
	
	public List<Team> findMarketingTeam() throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			StringBuffer findQuery = new StringBuffer();
			findQuery.append("SELECT team_code, leader_code, team_board_title, team_board_content, subject_code, contest_code, start_day, end_day, max_member, current_member, insert_date, complete_state ");
			findQuery.append("FROM TEAM_BUILDING ");
			findQuery.append("WHERE subject_code='sub1' ");
			findQuery.append("ORDER BY insert_date");
		
			con = ds.getConnection();
			pstmt = con.prepareStatement(findQuery.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY );
			rs = pstmt.executeQuery();

			UserManager manager = UserManager.getInstance();
				
			List<Team> teamList = new ArrayList<Team>();
				
			while(rs.next()) {
				Team team = new Team();
				team.setTeamCode(rs.getString("team_code"));
				team.setLeaderCode(rs.getString("leader_code"));
				team.setLeaderId(manager.getUserId(team.getLeaderCode()));
				team.setLeaderName(manager.getUserName(team.getLeaderCode()));
				team.setTeamBoardTitle(rs.getString("team_board_title"));
				team.setTeamBoardContent(rs.getString("team_board_content"));
				team.setSubjectCode(rs.getString("subject_code"));
				team.setSubjectName(manager.getSubjectName(team.getSubjectCode()));
				team.setContestCode(rs.getString("contest_code"));
				team.setContestTitle(manager.getContestTitle(team.getContestCode()));
				team.setStartDay(rs.getDate("start_day"));
				team.setEndDay(rs.getDate("end_day"));
				team.setMaxMember(rs.getInt("max_member"));
				team.setCurrentMember(rs.getInt("current_member"));
				team.setInsertDate(rs.getDate("insert_date"));
				team.setCompleteState(rs.getInt("complete_state"));
				
				teamList.add(team);
			}
			return teamList;
		
		}finally {
			if ( pstmt != null ){
				pstmt.close();
			}			
			if ( con != null ){
				con.close();
			}
		}
	}
	public List<Team> findDevelopTeam() throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			StringBuffer findQuery = new StringBuffer();
			findQuery.append("SELECT team_code, leader_code, team_board_title, team_board_content, subject_code, contest_code, start_day, end_day, max_member, current_member, insert_date, complete_state ");
			findQuery.append("FROM TEAM_BUILDING ");
			findQuery.append("WHERE subject_code='sub2' ");
			findQuery.append("ORDER BY insert_date");
		
			con = ds.getConnection();
			pstmt = con.prepareStatement(findQuery.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY );
			rs = pstmt.executeQuery();

			UserManager manager = UserManager.getInstance();
				
			List<Team> teamList = new ArrayList<Team>();
				
			while(rs.next()) {
				Team team = new Team();
				team.setTeamCode(rs.getString("team_code"));
				team.setLeaderCode(rs.getString("leader_code"));
				team.setLeaderId(manager.getUserId(team.getLeaderCode()));
				team.setLeaderName(manager.getUserName(team.getLeaderCode()));
				team.setTeamBoardTitle(rs.getString("team_board_title"));
				team.setTeamBoardContent(rs.getString("team_board_content"));
				team.setSubjectCode(rs.getString("subject_code"));
				team.setSubjectName(manager.getSubjectName(team.getSubjectCode()));
				team.setContestCode(rs.getString("contest_code"));
				team.setContestTitle(manager.getContestTitle(team.getContestCode()));
				team.setStartDay(rs.getDate("start_day"));
				team.setEndDay(rs.getDate("end_day"));
				team.setMaxMember(rs.getInt("max_member"));
				team.setCurrentMember(rs.getInt("current_member"));
				team.setInsertDate(rs.getDate("insert_date"));
				team.setCompleteState(rs.getInt("complete_state"));
				
				teamList.add(team);
			}
			return teamList;
		
		}finally {
			if ( pstmt != null ){
				pstmt.close();
			}			
			if ( con != null ){
				con.close();
			}
		}
	}
	
	public List<Team> findPlanTeam() throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			StringBuffer findQuery = new StringBuffer();
			findQuery.append("SELECT team_code, leader_code, team_board_title, team_board_content, subject_code, contest_code, start_day, end_day, max_member, current_member, insert_date, complete_state ");
			findQuery.append("FROM TEAM_BUILDING ");
			findQuery.append("WHERE subject_code='sub3' ");
			findQuery.append("ORDER BY insert_date");
		
			con = ds.getConnection();
			pstmt = con.prepareStatement(findQuery.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY );
			rs = pstmt.executeQuery();

			UserManager manager = UserManager.getInstance();
				
			List<Team> teamList = new ArrayList<Team>();
				
			while(rs.next()) {
				Team team = new Team();
				team.setTeamCode(rs.getString("team_code"));
				team.setLeaderCode(rs.getString("leader_code"));
				team.setLeaderId(manager.getUserId(team.getLeaderCode()));
				team.setLeaderName(manager.getUserName(team.getLeaderCode()));
				team.setTeamBoardTitle(rs.getString("team_board_title"));
				team.setTeamBoardContent(rs.getString("team_board_content"));
				team.setSubjectCode(rs.getString("subject_code"));
				team.setSubjectName(manager.getSubjectName(team.getSubjectCode()));
				team.setContestCode(rs.getString("contest_code"));
				team.setContestTitle(manager.getContestTitle(team.getContestCode()));
				team.setStartDay(rs.getDate("start_day"));
				team.setEndDay(rs.getDate("end_day"));
				team.setMaxMember(rs.getInt("max_member"));
				team.setCurrentMember(rs.getInt("current_member"));
				team.setInsertDate(rs.getDate("insert_date"));
				team.setCompleteState(rs.getInt("complete_state"));
				
				teamList.add(team);
			}
			return teamList;
		
		}finally {
			if ( pstmt != null ){
				pstmt.close();
			}			
			if ( con != null ){
				con.close();
			}
		}
	}
	
	public List<Team> findCultureTeam() throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			StringBuffer findQuery = new StringBuffer();
			findQuery.append("SELECT team_code, leader_code, team_board_title, team_board_content, subject_code, contest_code, start_day, end_day, max_member, current_member, insert_date, complete_state ");
			findQuery.append("FROM TEAM_BUILDING ");
			findQuery.append("WHERE subject_code='sub4' ");
			findQuery.append("ORDER BY insert_date");
		
			con = ds.getConnection();
			pstmt = con.prepareStatement(findQuery.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY );
			rs = pstmt.executeQuery();

			UserManager manager = UserManager.getInstance();
				
			List<Team> teamList = new ArrayList<Team>();
				
			while(rs.next()) {
				Team team = new Team();
				team.setTeamCode(rs.getString("team_code"));
				team.setLeaderCode(rs.getString("leader_code"));
				team.setLeaderId(manager.getUserId(team.getLeaderCode()));
				team.setLeaderName(manager.getUserName(team.getLeaderCode()));
				team.setTeamBoardTitle(rs.getString("team_board_title"));
				team.setTeamBoardContent(rs.getString("team_board_content"));
				team.setSubjectCode(rs.getString("subject_code"));
				team.setSubjectName(manager.getSubjectName(team.getSubjectCode()));
				team.setContestCode(rs.getString("contest_code"));
				team.setContestTitle(manager.getContestTitle(team.getContestCode()));
				team.setStartDay(rs.getDate("start_day"));
				team.setEndDay(rs.getDate("end_day"));
				team.setMaxMember(rs.getInt("max_member"));
				team.setCurrentMember(rs.getInt("current_member"));
				team.setInsertDate(rs.getDate("insert_date"));
				team.setCompleteState(rs.getInt("complete_state"));
				
				teamList.add(team);
			}
			return teamList;
		
		}finally {
			if ( pstmt != null ){
				pstmt.close();
			}			
			if ( con != null ){
				con.close();
			}
		}
	}
	
	public List<Team> findDesignTeam() throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			StringBuffer findQuery = new StringBuffer();
			findQuery.append("SELECT team_code, leader_code, team_board_title, team_board_content, subject_code, contest_code, start_day, end_day, max_member, current_member, insert_date, complete_state ");
			findQuery.append("FROM TEAM_BUILDING ");
			findQuery.append("WHERE subject_code='sub5' ");
			findQuery.append("ORDER BY insert_date");
		
			con = ds.getConnection();
			pstmt = con.prepareStatement(findQuery.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY );
			rs = pstmt.executeQuery();

			UserManager manager = UserManager.getInstance();
				
			List<Team> teamList = new ArrayList<Team>();
				
			while(rs.next()) {
				Team team = new Team();
				team.setTeamCode(rs.getString("team_code"));
				team.setLeaderCode(rs.getString("leader_code"));
				team.setLeaderId(manager.getUserId(team.getLeaderCode()));
				team.setLeaderName(manager.getUserName(team.getLeaderCode()));
				team.setTeamBoardTitle(rs.getString("team_board_title"));
				team.setTeamBoardContent(rs.getString("team_board_content"));
				team.setSubjectCode(rs.getString("subject_code"));
				team.setSubjectName(manager.getSubjectName(team.getSubjectCode()));
				team.setContestCode(rs.getString("contest_code"));
				team.setContestTitle(manager.getContestTitle(team.getContestCode()));
				team.setStartDay(rs.getDate("start_day"));
				team.setEndDay(rs.getDate("end_day"));
				team.setMaxMember(rs.getInt("max_member"));
				team.setCurrentMember(rs.getInt("current_member"));
				team.setInsertDate(rs.getDate("insert_date"));
				team.setCompleteState(rs.getInt("complete_state"));
				
				teamList.add(team);
			}
			return teamList;
		
		}finally {
			if ( pstmt != null ){
				pstmt.close();
			}			
			if ( con != null ){
				con.close();
			}
		}
	}
	

	//teamCode로 team객체를 찾아서 반환
	public Team findTeam(String teamCode) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			StringBuffer findQuery = new StringBuffer();
			findQuery.append("SELECT ");
			findQuery.append("team_code, leader_code, team_board_title, team_board_content, subject_code, contest_code, start_day, end_day, max_member, current_member, insert_date, complete_state ");
			findQuery.append("FROM TEAM_BUILDING ");
			findQuery.append("WHERE team_code=? ");		
		
			con = ds.getConnection();
			pstmt = con.prepareStatement(findQuery.toString());
			pstmt.setString(1, teamCode);
			
			rs = pstmt.executeQuery();
			
			UserManager manager = UserManager.getInstance();
			
			Team team = null;
			
			if ( rs.next() ){
				team = new Team();
				team.setTeamCode(rs.getString("team_code"));
				team.setLeaderCode(rs.getString("leader_code"));
				team.setLeaderId(manager.getUserId(team.getLeaderCode()));
				team.setLeaderName(manager.getUserName(team.getLeaderCode()));
				team.setTeamBoardTitle(rs.getString("team_board_title"));
				team.setTeamBoardContent(rs.getString("team_board_content"));
				team.setSubjectCode(rs.getString("subject_code"));
				team.setSubjectName(manager.getSubjectName(team.getSubjectCode()));
				team.setContestCode(rs.getString("contest_code"));
				team.setContestTitle(manager.getContestTitle(team.getContestCode()));
				team.setStartDay(rs.getDate("start_day"));
				team.setEndDay(rs.getDate("end_day"));
				team.setMaxMember(rs.getInt("max_member"));
				team.setCurrentMember(rs.getInt("current_member"));
				team.setInsertDate(rs.getDate("insert_date"));
				team.setCompleteState(rs.getInt("complete_state"));
			}
			return team;
		} finally {
			if ( pstmt != null ){
				pstmt.close();
			}			
			if ( con != null ){
				con.close();
			}
		}
	}
	
	//teamCode에 와 같은 팀에 속해있는 멤버들을 찾아 user형태로 반환
	public List<User> findTeamMember(String teamCode) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			StringBuffer findQuery = new StringBuffer();
			findQuery.append("SELECT ");
			findQuery.append("id, member_code, name, gender, phone, assign, age, portfolio, image ");
			findQuery.append("FROM MEMBER ");
			findQuery.append("WHERE member_code ");
			findQuery.append("in ( ");
			findQuery.append("SELECT member_code ");
			findQuery.append("FROM member_assign ");
			findQuery.append("WHERE team_code=? ");
			findQuery.append(") ");
		
			con = ds.getConnection();
			pstmt = con.prepareStatement(findQuery.toString());
			pstmt.setString(1, teamCode);
			
			rs = pstmt.executeQuery();
			
			UserManager manager = UserManager.getInstance();
			
			List<User> userList = new ArrayList<User>();
			
			while ( rs.next() ){
				User user = new User();
				user.setUserId(rs.getString("id"));
				user.setMember_code(rs.getString("member_code"));
				user.setName(rs.getString("name"));
				user.setGender(rs.getString("gender"));
				user.setPhone(rs.getString("phone"));
				user.setAssign(rs.getString("assign"));
				user.setAge(rs.getInt("age"));
				user.setPortfolio(rs.getString("portfolio"));
				//user.setImage(rs.getBlob("image")); 서히야 부탁해 이미지처리
				
				userList.add(user);
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
	
	//subjectCode로 subjectName을 구해오는 함수
	public String getSubjectNameBySubjectCode(String subject_code) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String subject_name = null;
		
		try {
			StringBuffer existedQuery = new StringBuffer();
			existedQuery.append("SELECT subject_name FROM contest_subject ");
			existedQuery.append("WHERE subject_code=? ");		
		
			con = ds.getConnection();
			pstmt = con.prepareStatement(existedQuery.toString());
			pstmt.setString(1, subject_code);
			
			rs = pstmt.executeQuery();
			if ( rs.next() ){
				subject_name = rs.getString("subject_name");
			}
			return subject_name;
			
		} finally {
			if ( pstmt != null ){
				pstmt.close();
			}			
			if ( con != null ){
				con.close();
			}
		}
	}
	
	//contestCode로 contestTitle을 구해오는 함수
		public String getContestTitleByContestCode(String contest_code) throws SQLException {
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			String contest_title = null;
			
			try {
				StringBuffer existedQuery = new StringBuffer();
				existedQuery.append("SELECT contest_title FROM contest_info ");
				existedQuery.append("WHERE contest_code=? ");		
			
				con = ds.getConnection();
				pstmt = con.prepareStatement(existedQuery.toString());
				pstmt.setString(1, contest_code);
				
				rs = pstmt.executeQuery();
				if ( rs.next() ) {
					contest_title = rs.getString("contest_title");
				}
				return contest_title;
				
			} finally {
				if ( pstmt != null ){
					pstmt.close();
				}			
				if ( con != null ){
					con.close();
				}
			}
		}
		
		//참가신청 시 team_apply table로 insert
		public int insertTeamApply(TeamApply teamApply) throws SQLException {
			Connection con = null;
			PreparedStatement pstmt = null;
			try {				
				StringBuffer insertQuery = new StringBuffer();
				insertQuery.append("INSERT INTO team_apply(apply_code, team_code, member_code, agree) VALUES ");
				insertQuery.append("('apply'||apply_seq.nextval, ?, ?, ?)");		
			
				con = ds.getConnection();
				
				pstmt = con.prepareStatement(insertQuery.toString());	
				pstmt.setString(1, teamApply.getTeamCode());
				pstmt.setString(2, teamApply.getMemberCode());
				pstmt.setInt(3, teamApply.getAgree());	
				
				int result = pstmt.executeUpdate();
				return result;
				
			} finally {
				if( pstmt != null ){
					pstmt.close();
				}
				if ( con != null ){
					con.close();
				}
			}
		}
		
		//해당 팀에 참가 신청한 TeamApply 리스트를 반환
		public List<TeamApply> getTeamApplyList(String teamCode) throws SQLException {
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			try {
				StringBuffer findQuery = new StringBuffer();
				findQuery.append("SELECT ");
				findQuery.append("apply_code, team_code, member_code, agree ");
				findQuery.append("FROM TEAM_APPLY ");
				findQuery.append("WHERE team_code = ? ");
			
				con = ds.getConnection();
				pstmt = con.prepareStatement(findQuery.toString());
				pstmt.setString(1, teamCode);
				
				rs = pstmt.executeQuery();
				
				UserManager manager = UserManager.getInstance();
				
				List<TeamApply> applyList = new ArrayList<TeamApply>();
				
				while ( rs.next() ){
					TeamApply teamApply = new TeamApply();
					teamApply.setApplyCode(rs.getString("apply_code"));
					teamApply.setTeamCode(rs.getString("team_code"));
					teamApply.setMemberCode(rs.getString("member_code"));
					teamApply.setMemberId(manager.getUserId(teamApply.getMemberCode()));
					teamApply.setMemberName(manager.getUserName(teamApply.getMemberCode()));
					teamApply.setAgree(rs.getInt("agree"));
					applyList.add(teamApply);
				}
				return applyList;
				
			} finally {
				if ( pstmt != null ){
					pstmt.close();
				}			
				if ( con != null ){
					con.close();
				}
			}
		}
		
		public int applyUpdate(String applyCode) throws SQLException {
			Connection con = null;
			PreparedStatement pstmt = null;
			try {
				StringBuffer updateQuery = new StringBuffer();
				updateQuery.append("UPDATE team_apply SET ");
				updateQuery.append("agree=? ");
				updateQuery.append("WHERE apply_code=? ");		
			
				con = ds.getConnection();
				pstmt = con.prepareStatement(updateQuery.toString());
				
				pstmt.setInt(1, 1);
				pstmt.setString(2, applyCode);
				
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
}
