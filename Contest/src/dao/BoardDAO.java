package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import model.Note;
import model.PageResult;
import model.Reply;
import model.WritingContent;

public class BoardDAO {
	public static DataSource getDataSource() throws NamingException {
		Context initCtx = null;
		Context envCtx = null;

		// Obtain our environment naming context
		initCtx = new InitialContext();
		envCtx = (Context) initCtx.lookup("java:comp/env");

		// Look up our data source
		return (DataSource) envCtx.lookup("jdbc/WebDB");
	}

	public static PageResult<WritingContent> getPage(int page, int numItemsInPage) 
			throws SQLException, NamingException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;		

		if (page <= 0) {
			page = 1;
		}

		DataSource ds = getDataSource();
		PageResult<WritingContent> result = new PageResult<WritingContent>(numItemsInPage, page);


		int startPos = (page - 1) * numItemsInPage;

		try {
			conn = ds.getConnection();
			stmt = conn.createStatement();

			// writings 테이블: writings 수 페이지수 개산
			rs = stmt.executeQuery("SELECT COUNT(*) FROM Twritings");
			rs.next();

			result.setNumItems(rs.getInt(1));

			rs.close();
			rs = null;
			stmt.close();
			stmt = null;

			// writings 테이블 SELECT
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM Twritings ORDER BY time DESC LIMIT " + startPos + ", " + numItemsInPage);

			while(rs.next()) {
				result.getList().add(new WritingContent(rs.getInt("id"),
						rs.getString("title"),
						rs.getString("userid"),
						rs.getString("text1"),
						rs.getString("time")
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

	public static WritingContent findById(int id) throws NamingException, SQLException{
		WritingContent writingContent = null;

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		DataSource ds = getDataSource();

		try {
			conn = ds.getConnection();

			// 질의 준비
			stmt = conn.prepareStatement("SELECT * FROM Twritings WHERE id = ?");
			stmt.setInt(1, id);

			// 수행
			rs = stmt.executeQuery();

			if (rs.next()) {
				writingContent = new WritingContent(rs.getInt("id"),
						rs.getString("title"),
						rs.getString("userid"),
						rs.getString("text1"),
						rs.getString("time"));
			}	
		} finally {
			// 무슨 일이 있어도 리소스를 제대로 종료
			if (rs != null) try{rs.close();} catch(SQLException e) {}
			if (stmt != null) try{stmt.close();} catch(SQLException e) {}
			if (conn != null) try{conn.close();} catch(SQLException e) {}
		}
		return writingContent;

	}

	public static boolean create(WritingContent writingContent) throws SQLException, NamingException {
		int result;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		DataSource ds = getDataSource();

		try {
			conn = ds.getConnection();

			// 질의 준비
			stmt = conn.prepareStatement(
					"INSERT INTO Twritings(title, userid, text1) " +
							"VALUES(?, ?, ?)"
					);
			stmt.setString(1,  writingContent.getTitle());
			stmt.setString(2,  writingContent.getUserName());
			stmt.setString(3,  writingContent.getText());

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

	public static boolean update(WritingContent writingContent) throws SQLException, NamingException {
		int result;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		DataSource ds = getDataSource();

		try {
			conn = ds.getConnection();

			// 질의 준비
			stmt = conn.prepareStatement(
					"UPDATE Twritings " +
							"SET title=?, userid=?, text1=?" +
							"WHERE id=?"
					);
			stmt.setString(1,  writingContent.getTitle());
			stmt.setString(2,  writingContent.getUserName());
			stmt.setString(3,  writingContent.getText());
			stmt.setInt(4,  writingContent.getId());

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
			stmt = conn.prepareStatement("DELETE FROM Twritings WHERE id=?");
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
	
	public static List<Reply> getReply(int id) throws SQLException, NamingException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;		

		DataSource ds = getDataSource();
		List<Reply> reply = new ArrayList<Reply>();

		try {
			conn = ds.getConnection();
			// writings 테이블 SELECT
			stmt = conn.prepareStatement("select treply.id, writingnumber, treply.userid, reply, replytime from treply inner join twritings on treply.writingnumber = twritings.id where writingnumber=? order by replytime");
			stmt.setInt(1, id);
			
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				reply.add(new Reply(rs.getInt("treply.id"),
						rs.getInt("writingnumber"),
						rs.getString("treply.userid"),
						rs.getString("reply"),
						rs.getString("replytime")
						));
			}
		} finally {
			// 무슨 일이 있어도 리소스를 제대로 종료
			if (rs != null) try{rs.close();} catch(SQLException e) {}
			if (stmt != null) try{stmt.close();} catch(SQLException e) {}
			if (conn != null) try{conn.close();} catch(SQLException e) {}
		}
		return reply;		
	}

	public static Reply rfindById(int id) throws NamingException, SQLException{
		Reply reply = null;

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		DataSource ds = getDataSource();

		try {
			conn = ds.getConnection();

			// 질의 준비
			stmt = conn.prepareStatement("SELECT * FROM Treply WHERE id = ?");
			stmt.setInt(1, id);

			// 수행
			rs = stmt.executeQuery();

			if (rs.next()) {
				reply = new Reply(rs.getInt("id"),
						rs.getInt("writingnumber"),
						rs.getString("userid"),
						rs.getString("reply"),
						rs.getString("replytime"));
			}	
		} finally {
			// 무슨 일이 있어도 리소스를 제대로 종료
			if (rs != null) try{rs.close();} catch(SQLException e) {}
			if (stmt != null) try{stmt.close();} catch(SQLException e) {}
			if (conn != null) try{conn.close();} catch(SQLException e) {}
		}
		return reply;

	}

	public static boolean rcreate(Reply reply) throws SQLException, NamingException {
		int result;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		DataSource ds = getDataSource();

		try {
			conn = ds.getConnection();

			// 질의 준비
			stmt = conn.prepareStatement(
					"INSERT INTO Treply(writingnumber, userid, reply) " +
							"VALUES(?, ?, ?)"
					);
			stmt.setInt(1,  reply.getWritingNumber());
			stmt.setString(2,  reply.getUserid());
			stmt.setString(3,  reply.getReply());

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

	public static boolean rupdate(Reply reply) throws SQLException, NamingException {
		int result;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		DataSource ds = getDataSource();

		try {
			conn = ds.getConnection();

			// 질의 준비
			stmt = conn.prepareStatement(
					"UPDATE Treply " +
							"SET writingnumber=?, userid=?, reply=?" +
							"WHERE id=?"
					);
			stmt.setInt(1,  reply.getWritingNumber());
			stmt.setString(2,  reply.getUserid());
			stmt.setString(3,  reply.getReply());
			stmt.setInt(4,  reply.getId());

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
	
	public static boolean rremove(int id) throws NamingException, SQLException {
		int result;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		DataSource ds = getDataSource();

		try {
			conn = ds.getConnection();

			// 질의 준비
			stmt = conn.prepareStatement("DELETE FROM Treply WHERE id=?");
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
	
	public static boolean noteCreate(Note note) throws SQLException, NamingException {
		int result;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		DataSource ds = getDataSource();

		try {
			conn = ds.getConnection();

			// 질의 준비
			stmt = conn.prepareStatement(
					"INSERT INTO Tnote(myid, yourid, notetitle, content) " +
							"VALUES(?, ?, ?, ?)"
					);
			stmt.setInt(1,  note.getMyid());
			stmt.setInt(2,  note.getYourid());
			stmt.setString(3,  note.getNotetitle());
			stmt.setString(4, note.getContent());

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
}
