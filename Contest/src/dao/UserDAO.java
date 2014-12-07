package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import model.PageResult;
import model.User;

public class UserDAO {
	public static DataSource getDataSource() throws NamingException {
		Context initCtx = null;
		Context envCtx = null;

		// Obtain our environment naming context
		initCtx = new InitialContext();
		envCtx = (Context) initCtx.lookup("java:comp/env");

		// Look up our data source
		return (DataSource) envCtx.lookup("jdbc/WebDB");
	}
	
	public static PageResult<User> getPage(int page, int numItemsInPage) 
			throws SQLException, NamingException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;		

		if (page <= 0) {
			page = 1;
		}
		
		DataSource ds = getDataSource();
		PageResult<User> result = new PageResult<User>(numItemsInPage, page);
		
		
		int startPos = (page - 1) * numItemsInPage;
		
		try {
			conn = ds.getConnection();
			stmt = conn.createStatement();
			
			// users 테이블: user 수 페이지수 개산
	 		rs = stmt.executeQuery("SELECT COUNT(*) FROM Tusers");
			rs.next();
			
			result.setNumItems(rs.getInt(1));
			
			rs.close();
			rs = null;
			stmt.close();
			stmt = null;
			
	 		// users 테이블 SELECT
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM Tusers ORDER BY name LIMIT " + startPos + ", " + numItemsInPage);
			
			while(rs.next()) {
				result.getList().add(new User(rs.getInt("usernumber"),
							rs.getString("userid"),
							rs.getString("name"),
							rs.getString("photo"),
							rs.getString("pwd"),
							rs.getString("gender"),
							rs.getString("major"),
							rs.getString("phone"),
							rs.getString("email")
						));
			}
		} finally {
			// 무슨 일이 있어도 리소스를 제대로 종료
			if (rs != null) try{rs.close();} catch(SQLException e) {}
			if (stmt != null) try{stmt.close();} catch(SQLException e) {}
			if (conn != null) try{conn.close();} catch(SQLException e) {}
		}
		
		return result;		
	}
	
	public static User findById(int id) throws NamingException, SQLException{
		User user = null;
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		DataSource ds = getDataSource();
		
		try {
			conn = ds.getConnection();

			// 질의 준비
			stmt = conn.prepareStatement("SELECT * FROM Tusers WHERE usernumber = ?");
			stmt.setInt(1, id);
			
			// 수행
			rs = stmt.executeQuery();

			if (rs.next()) {
				user = new User(rs.getInt("usernumber"),
						rs.getString("userid"),
						rs.getString("name"),
						rs.getString("photo"),
						rs.getString("pwd"),
						rs.getString("gender"),
						rs.getString("major"),
						rs.getString("phone"),
						rs.getString("email"));
			}	
		} finally {
			// 무슨 일이 있어도 리소스를 제대로 종료
			if (rs != null) try{rs.close();} catch(SQLException e) {}
			if (stmt != null) try{stmt.close();} catch(SQLException e) {}
			if (conn != null) try{conn.close();} catch(SQLException e) {}
		}
		
		return user;
	}
	
	public static boolean create(User user) throws SQLException, NamingException {
		int result;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		DataSource ds = getDataSource();
		
		try {
			conn = ds.getConnection();

			// 질의 준비
			stmt = conn.prepareStatement(
					"INSERT INTO Tusers(userid, name, photo, pwd, gender, major, phone, email) " +
					"VALUES(?, ?, ?, ?, ?, ?, ?, ?)"
					);
			stmt.setString(1,  user.getUserid());
			stmt.setString(2,  user.getName());
			stmt.setString(3,  user.getPhoto());
			stmt.setString(4,  user.getPwd());
			stmt.setString(5,  user.getGender());
			stmt.setString(6,  user.getMajor());
			stmt.setString(7,  user.getPhone());
			stmt.setString(8,  user.getEmail());
			
			// 수행
			result = stmt.executeUpdate();
		} finally {
			// 무슨 일이 있어도 리소스를 제대로 종료
			if (rs != null) try{rs.close();} catch(SQLException e) {}
			if (stmt != null) try{stmt.close();} catch(SQLException e) {}
			if (conn != null) try{conn.close();} catch(SQLException e) {}
		}
		
		return (result == 1);
	}
	
	public static boolean update(User user) throws SQLException, NamingException {
		int result;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		DataSource ds = getDataSource();
		
		try {
			conn = ds.getConnection();

			// 질의 준비
			stmt = conn.prepareStatement(
					"UPDATE Tusers " +
					"SET userid=?, name=?, photo=?, gender=?, major=?, phone=?, email=? " +
					"WHERE usernumber=?"
					);
			stmt.setString(1,  user.getUserid());
			stmt.setString(2,  user.getName());
			stmt.setString(3,  user.getPhoto());
			stmt.setString(4,  user.getGender());
			stmt.setString(5,  user.getMajor());
			stmt.setString(6,  user.getPhone());
			stmt.setString(7,  user.getEmail());
			stmt.setInt(8,  user.getId());
			// 수행
			result = stmt.executeUpdate();
		} finally {
			// 무슨 일이 있어도 리소스를 제대로 종료
			if (rs != null) try{rs.close();} catch(SQLException e) {}
			if (stmt != null) try{stmt.close();} catch(SQLException e) {}
			if (conn != null) try{conn.close();} catch(SQLException e) {}
		}
		
		return (result == 1);		
	}
	
	public static boolean remove(int id) throws NamingException, SQLException {
		int result;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		DataSource ds = getDataSource();
		
		try {
			conn = ds.getConnection();

			// 질의 준비
			stmt = conn.prepareStatement("DELETE FROM Tusers WHERE usernumber=?");
			stmt.setInt(1,  id);
			// 수행
			result = stmt.executeUpdate();
		} finally {
			// 무슨 일이 있어도 리소스를 제대로 종료
			if (rs != null) try{rs.close();} catch(SQLException e) {}
			if (stmt != null) try{stmt.close();} catch(SQLException e) {}
			if (conn != null) try{conn.close();} catch(SQLException e) {}
		}
		return (result == 1);		
	}
	
	public static User findByuserid(String userid) throws NamingException, SQLException{
		User user = null;
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		DataSource ds = getDataSource();
		
		try {
			conn = ds.getConnection();

			// 질의 준비
			stmt = conn.prepareStatement("SELECT * FROM Tusers WHERE userid = ?");
			stmt.setString(1, userid);
			
			// 수행
			rs = stmt.executeQuery();

			if (rs.next()) {
				user = new User(rs.getInt("usernumber"),
						rs.getString("userid"),
						rs.getString("name"),
						rs.getString("photo"),
						rs.getString("pwd"),
						rs.getString("gender"),
						rs.getString("major"),
						rs.getString("phone"),
						rs.getString("email"));
			}	
		} finally {
			// 무슨 일이 있어도 리소스를 제대로 종료
			if (rs != null) try{rs.close();} catch(SQLException e) {}
			if (stmt != null) try{stmt.close();} catch(SQLException e) {}
			if (conn != null) try{conn.close();} catch(SQLException e) {}
		}
		return user;
	}
}
