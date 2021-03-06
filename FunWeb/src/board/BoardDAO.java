package board;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;



public class BoardDAO {
	
	private Connection getConnection() throws Exception{
		
		Context init = new InitialContext();
	    DataSource ds=(DataSource)init.lookup("java:comp/env/jdbc/MysqlDB"); //장소/jdbc/MysqlDB
		Connection con = ds.getConnection(); //java.sql
		return con;
	}
	

	public void write(BoardBean bb) {
		ResultSet rs=null;
		PreparedStatement pre=null;
		Connection con=null;
		try {
			System.out.println(bb.getName());

			int num = 0;
			int readcount = 0;

			con = getConnection();

			
			 String sql = "select max(num) from board"; 
			 pre = con.prepareStatement(sql); 
			 rs= pre.executeQuery();
			 
			 if(rs.next()) { num = rs.getInt("max(num)")+1; }
				if(pre!=null) try{pre.close();}catch(SQLException ex) {}

			sql = "insert into board(name,pass,subject,content,num,readcount,date,id,file,category) values(?,?,?,?,?,?,?,?,?,?)";
			pre = con.prepareStatement(sql);
			pre.setString(1, bb.getName());
			pre.setString(2, bb.getPass());
			pre.setString(3, bb.getSubject());
			pre.setString(4, bb.getContent());
			pre.setInt(5, num);
			pre.setInt(6, readcount);
			pre.setTimestamp(7, bb.getDate());
			pre.setString(8, bb.getId());
			pre.setString(9, bb.getFile());
			pre.setString(10,bb.getCategory());

			pre.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(rs!=null) try {rs.close();}catch(SQLException ex) {}
			if(pre!=null) try{pre.close();}catch(SQLException ex) {}
			if(con!=null) try {con.close();}catch(SQLException ex) {}
		}
	}//write
	
	public List getboardList(int startRow,int pageSize,String category) {
		List jbblist = new ArrayList();
		ResultSet rs=null;
		PreparedStatement pre=null;
		Connection con=null;
		
		try {
			
			Class.forName("com.mysql.jdbc.Driver");

			con = getConnection();

			
//			 String sql = "select * from board order by num desc";
			String sql = "select * from board where category=? order by num desc limit ?,?";
			 pre = con.prepareStatement(sql); 
			 pre.setString(1, category);
			 pre.setInt(2, startRow-1); //startRow 시작을 포함하지 않기때문에 -1
			 pre.setInt(3, pageSize);
			 rs= pre.executeQuery();
			 while(rs.next()) {
				 BoardBean jbb = new BoardBean();
//				 String s = new SimpleDateFormat("yyyy-MM-dd").format(rs.getTimestamp("date"));
				 jbb.setNum(rs.getInt("num"));
				 jbb.setName(rs.getString("name"));
				 jbb.setPass(rs.getString("pass"));
				 jbb.setSubject(rs.getString("subject"));
				 jbb.setContent(rs.getString("content"));
				 jbb.setDate(rs.getTimestamp("date"));
				 jbb.setReadcount(rs.getInt("readcount"));
				 jbb.setFile(rs.getString("file"));
				 jbb.setId(rs.getString("id"));
				 jbb.setCategory(rs.getString("category"));
				 
				 jbblist.add(jbb);
			 }
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(rs!=null) try {rs.close();}catch(SQLException ex) {}
			if(pre!=null) try{pre.close();}catch(SQLException ex) {}
			if(con!=null) try {con.close();}catch(SQLException ex) {}
		}
		
		return jbblist;
	}//getboardList
	
	// 내글 가져오기
	public List getboardList_mylst(int startRow,int pageSize,String id) {
		List jbblist = new ArrayList();
		ResultSet rs=null;
		PreparedStatement pre=null;
		Connection con=null;
		
		try {
			
			con = getConnection();

			 String sql = "select * from board where id=? order by num desc limit ?,?";
			 pre = con.prepareStatement(sql); 
			 pre.setString(1, id);
			 pre.setInt(2, startRow-1); //startRow 시작을 포함하지 않기때문에 -1
			 pre.setInt(3, pageSize);
			 
			 rs= pre.executeQuery();
			 while(rs.next()) {
				 BoardBean jbb = new BoardBean();
//				 String s = new SimpleDateFormat("yyyy-MM-dd").format(rs.getTimestamp("date"));
				 jbb.setNum(rs.getInt("num"));
				 jbb.setName(rs.getString("name"));
				 jbb.setPass(rs.getString("pass"));
				 jbb.setSubject(rs.getString("subject"));
				 jbb.setContent(rs.getString("content"));
				 jbb.setDate(rs.getTimestamp("date"));
				 jbb.setReadcount(rs.getInt("readcount"));
				 
				 jbblist.add(jbb);
			 }
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(rs!=null) try {rs.close();}catch(SQLException ex) {}
			if(pre!=null) try{pre.close();}catch(SQLException ex) {}
			if(con!=null) try {con.close();}catch(SQLException ex) {}
		}
		
		return jbblist;
	}//getboardList(String id)
	
	public BoardBean getboardContent(int num,String category) {
		BoardBean jbb = new BoardBean();
		ResultSet rs=null;
		PreparedStatement pre=null;
		Connection con=null;
		
		try {
			
			con = getConnection();
			
			 String sql = "update board set readcount=readcount+1 where num=?"; 
			 pre = con.prepareStatement(sql); 
			 pre.setInt(1, num);
			 pre.executeUpdate();
			
			 sql = "select * from board where num=?&&category=?"; 
			 pre = con.prepareStatement(sql); 
			 pre.setInt(1, num);
			 pre.setString(2, category);
			 rs= pre.executeQuery();
			 if(rs.next()) {
				 jbb.setNum(rs.getInt("num"));
				 jbb.setId(rs.getString("id"));
				 jbb.setName(rs.getString("name"));
				 jbb.setPass(rs.getString("pass"));
				 jbb.setSubject(rs.getString("subject"));
				 jbb.setContent(rs.getString("content"));
				 jbb.setDate(rs.getTimestamp("date"));
				 jbb.setReadcount(rs.getInt("readcount"));
				 jbb.setFile(rs.getString("file"));
				 
			 }
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(rs!=null) try {rs.close();}catch(SQLException ex) {}
			if(pre!=null) try{pre.close();}catch(SQLException ex) {}
			if(con!=null) try {con.close();}catch(SQLException ex) {}
		}
		
		return jbb;
	}//getboardList
	
	public void updateBoard(int num,BoardBean jbb) {
		PreparedStatement pre=null;
		Connection con=null;
		
		try {
			con = getConnection();
			String sql="update board set subject=?,content=? where num=?&&category=?";
			pre = con.prepareStatement(sql);
			
			pre.setString(1, jbb.getSubject());
			pre.setString(2, jbb.getContent());
			pre.setInt(3, num);
			pre.setString(4, jbb.getCategory());
			pre.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(pre!=null) try{pre.close();}catch(SQLException ex) {}
			if(con!=null) try {con.close();}catch(SQLException ex) {}
		}
	}//updateBoard
	
	public int passCheck(int num,String pass) {
		int check =0;
		ResultSet rs=null;
		PreparedStatement pre=null;
		Connection con=null;
		
		try {
			con = getConnection();
			String sql="select * from board where num=?";
			pre = con.prepareStatement(sql);
			pre.setInt(1, num);
			rs = pre.executeQuery();
			
			if(rs.next()) {
			if(pass.equals(rs.getString("pass"))){
				check = 1;
			}else {
				check =-1;
			}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(rs!=null) try {rs.close();}catch(SQLException ex) {}
			if(pre!=null) try{pre.close();}catch(SQLException ex) {}
			if(con!=null) try {con.close();}catch(SQLException ex) {}
		}
		
		return check;
	}//passCheck
	
	public void deleteBoard(int num,String p_num,String category) {
		PreparedStatement pre=null;
		Connection con=null;
		try {
			con = getConnection();
			String sql="delete from board where num=?;";
			pre = con.prepareStatement(sql);
			pre.setInt(1, num);
			pre.executeUpdate();
			if(pre!=null) try{pre.close();}catch(SQLException ex) {}
			
			sql="delete from comment where p_num=?&&category=?";
			pre = con.prepareStatement(sql);
			pre.setString(1, p_num);
			pre.setString(2, category);
			pre.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(pre!=null) try{pre.close();}catch(SQLException ex) {}
			if(con!=null) try {con.close();}catch(SQLException ex) {}
		}
		
		
	}//deleteBoard
	
	public int getBoardCount(String category) {
		int count=0;
		ResultSet rs=null;
		PreparedStatement pre=null;
		Connection con=null;
		
		try {
			con = getConnection();
			String sql="select count(num) from board where category=?";
			pre = con.prepareStatement(sql);
			pre.setString(1, category);
			rs= pre.executeQuery();
			if(rs.next()) {
				count = rs.getInt("count(num)"); 
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(rs!=null) try {rs.close();}catch(SQLException ex) {}
			if(pre!=null) try{pre.close();}catch(SQLException ex) {}
			if(con!=null) try {con.close();}catch(SQLException ex) {}
		}
		
		return count;
		
		
	}//getBoardCount
	
	// 내글 카운트
	public int getBoardCount_mylst(String id) {
		int count=0;
		ResultSet rs=null;
		PreparedStatement pre=null;
		Connection con=null;
		
		try {
			con = getConnection();
			String sql="select count(num) from board where id=?";
			pre = con.prepareStatement(sql);
			pre.setString(1, id);
			rs= pre.executeQuery();
			if(rs.next()) {
				count = rs.getInt("count(num)"); 
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(rs!=null) try {rs.close();}catch(SQLException ex) {}
			if(pre!=null) try{pre.close();}catch(SQLException ex) {}
			if(con!=null) try {con.close();}catch(SQLException ex) {}
		}
		
		return count;
		
		
	}//getBoardCount(String id)
	
	
	
	
	
	
	
	public int getBoardCommentCount(int p_num,String category) {
		int count=0;
		ResultSet rs=null;
		PreparedStatement pre=null;
		Connection con=null;
		
		try {
			con = getConnection();
			String sql="select count(num) from comment where p_num=?&&category=?";
			pre = con.prepareStatement(sql);
			pre.setInt(1, p_num);
			pre.setString(2, category);
			rs= pre.executeQuery();
			if(rs.next()) {
				count = rs.getInt("count(num)"); 
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(rs!=null) try {rs.close();}catch(SQLException ex) {}
			if(pre!=null) try{pre.close();}catch(SQLException ex) {}
			if(con!=null) try {con.close();}catch(SQLException ex) {}
		}
		
		return count;
		
		
	}//getBoardCount
	
	// ===================================search=====================================
	public int getBoardCount(String search,String category) {
		int count=0;
		ResultSet rs=null;
		PreparedStatement pre=null;
		Connection con=null;
		
		try {
			con = getConnection();
			String sql="select count(num) from board where category=?&&subject like ? ";
			pre = con.prepareStatement(sql);
			pre.setString(1, category);
			pre.setString(2, "%"+search+"%");
			rs= pre.executeQuery();
			if(rs.next()) {
				count = rs.getInt("count(num)"); 
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(rs!=null) try {rs.close();}catch(SQLException ex) {}
			if(pre!=null) try{pre.close();}catch(SQLException ex) {}
			if(con!=null) try {con.close();}catch(SQLException ex) {}
		}
		
		return count;
		
		
	}//getBoardCount

	
	public List getboardList_search(int startRow,int pageSize,String search) {
		List jbblist = new ArrayList();
		ResultSet rs=null;
		PreparedStatement pre=null;
		Connection con=null;
		
		try {
			
			Class.forName("com.mysql.jdbc.Driver");

			con = getConnection();

			
//			 String sql = "select * from board order by num desc";
			String sql = "select * from board where subject like ? order by num desc limit ?,?";
			 pre = con.prepareStatement(sql); 
			 pre.setString(1, "%"+search+"%");
			 pre.setInt(2, startRow-1); //startRow 시작을 포함하지 않기때문에 -1
			 pre.setInt(3, pageSize);
			 rs= pre.executeQuery();
			 while(rs.next()) {
				 BoardBean jbb = new BoardBean();
//				 String s = new SimpleDateFormat("yyyy-MM-dd").format(rs.getTimestamp("date"));
				 jbb.setNum(rs.getInt("num"));
				 jbb.setName(rs.getString("name"));
				 jbb.setPass(rs.getString("pass"));
				 jbb.setSubject(rs.getString("subject"));
				 jbb.setContent(rs.getString("content"));
				 jbb.setDate(rs.getTimestamp("date"));
				 jbb.setReadcount(rs.getInt("readcount"));
				 jbb.setFile(rs.getString("file"));
				 jbb.setId(rs.getString("id"));
				 
				 jbblist.add(jbb);
			 }
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(rs!=null) try {rs.close();}catch(SQLException ex) {}
			if(pre!=null) try{pre.close();}catch(SQLException ex) {}
			if(con!=null) try {con.close();}catch(SQLException ex) {}
		}
		
		return jbblist;
	}//getboardList
	
	
	
	

}
