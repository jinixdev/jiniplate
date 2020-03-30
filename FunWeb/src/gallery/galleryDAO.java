package gallery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import board.BoardBean;


public class galleryDAO {
	
private Connection getConnection() throws Exception{
		
		Context init = new InitialContext();
	    DataSource ds=(DataSource)init.lookup("java:comp/env/jdbc/MysqlDB"); //장소/jdbc/MysqlDB
		Connection con = ds.getConnection(); //java.sql
		return con;
	}
	

	public void write(galleryBean gb) {
		ResultSet rs=null;
		PreparedStatement pre=null;
		Connection con=null;
		try {

			int num = 0;

			con = getConnection();

			
			 String sql = "select max(num) from g_board"; 
			 pre = con.prepareStatement(sql); 
			 rs= pre.executeQuery();
			 
			 if(rs.next()) { num = rs.getInt("max(num)")+1; }
			 else {
				 num =1;
			 }
			 if(pre!=null) try{pre.close();}catch(SQLException ex) {}

			sql = "insert into g_board(num,id,img,content,date) values(?,?,?,?,?)";
			pre = con.prepareStatement(sql);
			pre.setInt(1, num);
			pre.setString(2, gb.getId());
			pre.setString(3, gb.getFile());
			pre.setString(4, gb.getContent());
			pre.setTimestamp(5, gb.getDate());
			
			

			pre.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(rs!=null) try {rs.close();}catch(SQLException ex) {}
			if(pre!=null) try{pre.close();}catch(SQLException ex) {}
			if(con!=null) try {con.close();}catch(SQLException ex) {}
		}
	}//write
	
	
	public List getboardList(int startRow,int pageSize) {
		List gblist = new ArrayList();
		ResultSet rs=null;
		PreparedStatement pre=null;
		Connection con=null;
		
		try {
			
			Class.forName("com.mysql.jdbc.Driver");

			con = getConnection();

			
//			 String sql = "select * from board order by num desc";
			String sql = "select * from g_board order by num desc limit ?,?";
			 pre = con.prepareStatement(sql); 
			 pre.setInt(1, startRow-1); //startRow 시작을 포함하지 않기때문에 -1
			 pre.setInt(2, pageSize);
			 rs= pre.executeQuery();
			 while(rs.next()) {
				 galleryBean gb = new galleryBean();
//				 String s = new SimpleDateFormat("yyyy-MM-dd").format(rs.getTimestamp("date"));
				 gb.setNum(rs.getInt("num"));
				 gb.setContent(rs.getString("content"));
				 gb.setDate(rs.getTimestamp("date"));
				 gb.setFile(rs.getString("img"));
				 gb.setId(rs.getString("id"));
				 
				 gblist.add(gb);
			 }
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(rs!=null) try {rs.close();}catch(SQLException ex) {}
			if(pre!=null) try{pre.close();}catch(SQLException ex) {}
			if(con!=null) try {con.close();}catch(SQLException ex) {}
		}
		
		return gblist;
	}//getboardList
	
	public galleryBean getboardContent(int num) {
		galleryBean gb = new galleryBean();
		ResultSet rs=null;
		PreparedStatement pre=null;
		Connection con=null;
		
		try {
			
			con = getConnection();
			
//			 String sql = "update g_board set readcount=readcount+1 where num=?"; 
//			 pre = con.prepareStatement(sql); 
//			 pre.setInt(1, num);
//			 pre.executeUpdate();
			
			 String sql = "select * from g_board where num=?"; 
			 pre = con.prepareStatement(sql); 
			 pre.setInt(1, num);
			 rs= pre.executeQuery();
			 if(rs.next()) {
				 gb.setNum(rs.getInt("num"));
				 gb.setId(rs.getString("id"));
				 gb.setContent(rs.getString("content"));
				 gb.setDate(rs.getTimestamp("date"));
				 gb.setFile(rs.getString("img"));
				 
			 }
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(rs!=null) try {rs.close();}catch(SQLException ex) {}
			if(pre!=null) try{pre.close();}catch(SQLException ex) {}
			if(con!=null) try {con.close();}catch(SQLException ex) {}
		}
		
		return gb;
	}//getboardList
	
	public void updateBoard(int num,galleryBean gb) {
		PreparedStatement pre=null;
		Connection con=null;
		
		try {
			con = getConnection();
			String sql="update g_board set img=?,content=? where num=?";
			pre = con.prepareStatement(sql);
			pre.setString(1, gb.getFile());
			pre.setString(2, gb.getContent());
			pre.setInt(3, num);
			
			pre.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(pre!=null) try{pre.close();}catch(SQLException ex) {}
			if(con!=null) try {con.close();}catch(SQLException ex) {}
		}
	}//updateBoard
	
	
	
	
	
	
	
	
	
	public int getBoardCount() {
		int count=0;
		ResultSet rs=null;
		PreparedStatement pre=null;
		Connection con=null;
		
		try {
			con = getConnection();
			String sql="select count(num) from board";
			pre = con.prepareStatement(sql);
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

}