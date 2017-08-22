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

public class ContestDAO {

private DataSource ds;
	
	public ContestDAO() throws Exception {
			Context init = new InitialContext();
			ds = (DataSource)init.lookup("java:comp/env/jdbc/OracleDB");
	}	
	
	
	/**
	 * 모든 공모전 정보를 찾아와 마감시간 기준으로 정렬하여 List형태로 반환
	 */
	public List<ContestDTO> findAllContest() throws SQLException{
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			StringBuffer findQuery = new StringBuffer();
			findQuery.append("SELECT subject_code, contest_code, contest_title, contest_content, start_day, end_day ");
			findQuery.append("FROM CONTEST_INFO ");
			findQuery.append("ORDER BY end_day");
	
			con = ds.getConnection();
			pstmt = con.prepareStatement(findQuery.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY );
			rs = pstmt.executeQuery();

			List<ContestDTO> ContestDto = new ArrayList<ContestDTO>();
			
			while(rs.next()) {
				ContestDTO contest = new ContestDTO();
				contest.setSubjectCode(rs.getString("subject_code"));
				contest.setContestCode(rs.getString("contest_code"));
				contest.setContestTitle(rs.getString("contest_title"));
				contest.setContestContent(rs.getString("contest_content"));
				contest.setStartDay(rs.getDate("start_day"));
				contest.setEndDay(rs.getDate("end_day"));
				
				ContestDto.add(contest);
			}
			
			return ContestDto;
			
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

	
	public List<ContestDTO> findContest(String subject) throws SQLException{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			StringBuffer findQuery = new StringBuffer();
			findQuery.append("SELECT subject_code, contest_code, contest_title, contest_content, start_day, end_day ");
			findQuery.append("FROM CONTEST_INFO natural join CONTEST_SUBJECT ");
			findQuery.append("WHERE subject_name=? ");
			findQuery.append("ORDER BY end_day");
		
			con = ds.getConnection();
			pstmt = con.prepareStatement(findQuery.toString());	
			pstmt.setString(1, subject);
			
			rs = pstmt.executeQuery();
			
			List<ContestDTO> ContestDto = new ArrayList<ContestDTO>();
			
			while(rs.next()) {
				ContestDTO contest = new ContestDTO();
				contest.setSubjectCode(rs.getString("subject_code"));
				contest.setContestCode(rs.getString("contest_code"));
				contest.setContestTitle(rs.getString("contest_title"));
				contest.setContestContent(rs.getString("contest_content"));
				contest.setStartDay(rs.getDate("start_day"));
				contest.setEndDay(rs.getDate("end_day"));
				
				ContestDto.add(contest);
			}
			
			return ContestDto;
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


	public int createContest(ContestDTO contestDTO) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			StringBuffer insertQuery = new StringBuffer();
			insertQuery.append("INSERT INTO CONTEST_INFO(contest_code, contest_title, contest_content, start_day, end_day, subject_code ) VALUES ");
			insertQuery.append("('con'||contest_seq.nextval, ?, ?, ?, ?, ?) ");
		
			con = ds.getConnection();
			pstmt = con.prepareStatement(insertQuery.toString());
			pstmt.setString(1, contestDTO.getContestTitle());		
			pstmt.setString(2, contestDTO.getContestContent());
			pstmt.setDate(3,  new java.sql.Date(contestDTO.getStartDay().getTime()));
			pstmt.setDate(4,  new java.sql.Date(contestDTO.getEndDay().getTime()));
			pstmt.setString(5, contestDTO.getSubjectCode());	

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
	


	public ContestDTO viewContest(String contestCode) throws SQLException{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			StringBuffer findQuery = new StringBuffer();
			findQuery.append("SELECT ");
			findQuery.append("contest_code, contest_title, contest_content, start_day, end_day, subject_name ");
			findQuery.append("FROM CONTEST_INFO natural join CONTEST_SUBJECT ");
			findQuery.append("WHERE contest_code=? ");		
		
			con = ds.getConnection();
			pstmt = con.prepareStatement(findQuery.toString());
			pstmt.setString(1, contestCode);
			
			rs = pstmt.executeQuery();
			
			ContestDTO contest = null;
			if ( rs.next() ){
				contest = new ContestDTO();
				contest.setContestCode(contestCode);
				contest.setContestTitle(rs.getString("contest_title"));
				contest.setContestContent(rs.getString("contest_content"));	
				contest.setStartDay(rs.getDate("start_day"));
				contest.setEndDay(rs.getDate("end_day"));
				contest.setSubjectName(rs.getString("subject_name"));
			}
			return contest;
		} finally {
			if ( pstmt != null ){
				pstmt.close();
			}			
			if ( con != null ){
				con.close();
			}
		}
	
	}


	public int updateContest(ContestDTO contestDTO) throws SQLException {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			StringBuffer updateQuery = new StringBuffer();
			updateQuery.append("UPDATE CONTEST_INFO SET ");
			updateQuery.append("contest_title=?, contest_content=?, start_day=?, end_day=?, subject_code=? ");
			updateQuery.append("WHERE contest_code= ?");
			con = ds.getConnection();
			pstmt = con.prepareStatement(updateQuery.toString());
				
			pstmt.setString(1, contestDTO.getContestTitle());
			pstmt.setString(2, contestDTO.getContestContent());
			pstmt.setDate(3,new java.sql.Date(contestDTO.getStartDay().getTime()));	
			pstmt.setDate(4, new java.sql.Date(contestDTO.getEndDay().getTime()));	
			pstmt.setString(5, contestDTO.getSubjectCode());					
			pstmt.setString(6, contestDTO.getContestCode());
			
			
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
	
	public String getSubjectCodeBySubjectName(String subjectName) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			StringBuffer findQuery = new StringBuffer();
			findQuery.append("SELECT ");
			findQuery.append("subject_code ");
			findQuery.append("FROM contest_subject ");
			findQuery.append("WHERE subject_name = ? ");		
		
			con = ds.getConnection();
			pstmt = con.prepareStatement(findQuery.toString());
			pstmt.setString(1, subjectName);
			
			rs = pstmt.executeQuery();

			String subject_code = null;;
			
			if ( rs.next() ){
				subject_code = rs.getString("subject_code");
			}
			return subject_code;
			
		} finally {
			if ( pstmt != null ){
				pstmt.close();
			}			
			if ( con != null ){
				con.close();
			}
		}
	}


	public int removeContest(String contestCode)throws SQLException {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			StringBuffer removeQuery = new StringBuffer();
			removeQuery.append("DELETE FROM CONTEST_INFO ");
			removeQuery.append("WHERE contest_code=? ");		
		
			con = ds.getConnection();
			pstmt = con.prepareStatement(removeQuery.toString());
			pstmt.setString(1, contestCode);
			
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
	
