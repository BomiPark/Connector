package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class UserViewcountDAO {
	private DataSource ds;
	
	public UserViewcountDAO() throws Exception {
			Context init = new InitialContext();
			ds = (DataSource)init.lookup("java:comp/env/jdbc/OracleDB");
	}	
	
	
	/**
	 * user_view_count update
	 */
	public int updateViewCount(String userCode, String subject) throws SQLException{
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
//		UserManager manager = UserManager.getInstance();
//		String user = manager.getMemberCode(userId).toLowerCase();
		
		String countSubject = null;
		if(subject.equals("marketing")){
			countSubject = "count_marketing";
		} else if(subject.equals("develop")){
			countSubject = "count_develop";
		} else if(subject.equals("paln")){
			countSubject = "count_plan";
		} else if(subject.equals("culture")){
			countSubject = "count_culture";
		} else if(subject.equals("design")){
			countSubject = "count_design";
		}
		try {
			StringBuffer findCountQuery = new StringBuffer();
			findCountQuery.append("SELECT " + countSubject + " ");
			findCountQuery.append("FROM MEMBER_VIEW_COUNT ");
			findCountQuery.append("WHERE member_code=" + "'" + userCode.toLowerCase()+ "'");
	
			con = ds.getConnection();
			pstmt = con.prepareStatement(findCountQuery.toString());
			rs = pstmt.executeQuery();
			rs.next();
			int counting = rs.getInt(countSubject) + 1;
			
			int rslt = countupdate(countSubject, counting, userCode.toLowerCase());
			
			return rslt;
			
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
	
	public int countupdate(String countSubject, int counting, String userCode) throws SQLException{
		
		Connection con = null;
		PreparedStatement pstmt = null;
		try{
		StringBuffer countQuery = new StringBuffer();
		countQuery.append("UPDATE MEMBER_VIEW_COUNT ");
		countQuery.append("SET " + countSubject + "=" + counting + " ");
		countQuery.append("WHERE member_code=" + "'" + userCode+ "'");

		con = ds.getConnection();
		pstmt = con.prepareStatement(countQuery.toString());
		
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
