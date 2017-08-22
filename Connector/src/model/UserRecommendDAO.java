package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class UserRecommendDAO {
	private DataSource ds;
	
	public UserRecommendDAO() throws Exception {
			Context init = new InitialContext();
			ds = (DataSource)init.lookup("java:comp/env/jdbc/OracleDB");
	}
	
	public List<User> UserRecommend(String userCode) throws SQLException{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String[] standard;
		
		try {
			StringBuffer findTotalQuery = new StringBuffer();
			findTotalQuery.append("SELECT total_count ");
			findTotalQuery.append("FROM MEMBER_VIEW_COUNT ");
			findTotalQuery.append("WHERE member_code=" + "'" + userCode.toLowerCase()+ "'");
	
			con = ds.getConnection();
			pstmt = con.prepareStatement(findTotalQuery.toString());
			rs = pstmt.executeQuery();
			rs.next();
			int counting = rs.getInt("total_count");
			
			if(counting >20) {
				standard = FindStandard_up(userCode);
			} else {
				standard = FindStandard_down(userCode);
			}
			
			return Recommend(standard);
			
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
	
	public List<User> Recommend(String[] standard) throws SQLException{
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List<User> recommendUser= new ArrayList<User>();
		
		try {
			StringBuffer findRecommendQuery = new StringBuffer();
			User user = new User();
			for(int i=0;i<standard.length;i++){
				findRecommendQuery = new StringBuffer();
				findRecommendQuery.append("SELECT ");
				findRecommendQuery.append("id, name, gender, assign, age, portfolio ");
				findRecommendQuery.append("FROM MEMBER ");
				findRecommendQuery.append("WHERE member_code in (");
				findRecommendQuery.append("SELECT member_code ");
				findRecommendQuery.append("FROM member_view_count ");
				findRecommendQuery.append("WHERE " + standard[i] + ">=50)");
		
				con = ds.getConnection();
				pstmt = con.prepareStatement(findRecommendQuery.toString());
				rs = pstmt.executeQuery();
	
				while(rs.next()) {
					user.setUserId(rs.getString("id"));
					user.setName(rs.getString("name"));
					user.setGender(rs.getString("gender"));
					user.setAssign(rs.getString("assign"));
					user.setAge(rs.getInt("age"));
					user.setPortfolio(rs.getString("portfolio"));
					recommendUser.add(user);
					user=new User();
				}
				if(i!=(standard.length-1)){
					con = null;
					pstmt = null;
					rs = null;
				}
			}
			
			return recommendUser;
			
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
	
	public String[] FindStandard_up(String userCode) throws SQLException{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
//		String standard = null;
		Double rate[] = new Double[5];
//		Double max = 0.0;
		UserRateDTO userRate = null;
		String standard[] = new String[5];
		
		try {
			StringBuffer findStandardQuery = new StringBuffer();
			findStandardQuery.append("SELECT member_code, ");
			findStandardQuery.append("rate_marketing, rate_develop, rate_plan, rate_culture, rate_design ");
			findStandardQuery.append("FROM MEMBER_VIEW_COUNT ");
			findStandardQuery.append("WHERE member_code='" + userCode + "' ");
	
			con = ds.getConnection();
			pstmt = con.prepareStatement(findStandardQuery.toString());
			rs = pstmt.executeQuery();

			while(rs.next()) {
				userRate = new UserRateDTO();
				userRate.setUserCode(rs.getString("member_code"));
				
				userRate.setRateMarketing(rs.getDouble("rate_marketing"));
				userRate.setRateDevelop(rs.getDouble("rate_develop"));
				userRate.setRatePlan(rs.getDouble("rate_plan"));
				userRate.setRateCulture(rs.getDouble("rate_culture"));
				userRate.setRateDesign(rs.getDouble("rate_design"));
				
				rate[0] = rs.getDouble("rate_marketing");
				rate[1] = rs.getDouble("rate_develop");
				rate[2] = rs.getDouble("rate_plan");
				rate[3] = rs.getDouble("rate_culture");
				rate[4] = rs.getDouble("rate_design");
			}
			
			for (int i = 0; i < rate.length; i++) {
	            for (int j = i + 1; j < rate.length; j++) {
	                if (rate[i] < rate[j]) {
	                    double tmep = rate[i];
	                    rate[i] = rate[j];
	                    rate[j] = tmep;
	                }
	            }
	        }
			
			
			for(int j=0; j<standard.length;j++) {
				if(userRate.getRateMarketing() == rate[j]) {
					standard[j] = "rate_marketing";
				} else if(userRate.getRateDevelop() == rate[j]) {
					standard[j] = "rate_develop";
				} else if(userRate.getRatePlan() == rate[j]) {
					standard[j] = "rate_plan";
				} else if(userRate.getRateCulture() == rate[j]) {
					standard[j] = "rate_culture";
				} else if(userRate.getRateDesign() == rate[j]) {
					standard[j] = "rate_design";
				}
			}
			
			
			return standard;
			
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
	
	public String[] FindStandard_down(String userCode) throws SQLException{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String[] standard = new String[3];
		String[] rsStandard = new String[3];
		
		try {
			StringBuffer findStandardQuery = new StringBuffer();
			findStandardQuery.append("SELECT rank_first, rank_second, rank_third ");
			findStandardQuery.append("FROM MEMBER_INTEREST ");
			findStandardQuery.append("WHERE member_code='" + userCode + "' ");
	
			con = ds.getConnection();
			pstmt = con.prepareStatement(findStandardQuery.toString());
			rs = pstmt.executeQuery();
			rs.next();
			
			standard[0] = rs.getString("rank_first").toString().toLowerCase();
			standard[1] = rs.getString("rank_second").toString().toLowerCase();
			standard[2] = rs.getString("rank_third").toString().toLowerCase();
			
			for(int j=0; j<standard.length;j++) {
				if(standard[j].equals("sub1")) {
					rsStandard[j] = "rate_marketing";
				} else if(standard[j].equals("sub2")) {
					rsStandard[j] = "rate_develop";
				} else if(standard[j].equals("sub3")) {
					rsStandard[j] = "rate_plan";
				} else if(standard[j].equals("sub4")) {
					rsStandard[j] = "rate_culture";
				} else if(standard[j].equals("sub5")) {
					rsStandard[j] = "rate_design";
				}
			}
			
			return rsStandard;
			
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
