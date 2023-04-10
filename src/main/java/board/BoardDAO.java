package board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import common.JdbcUtil;
public class BoardDAO {

	private JdbcUtil ju;
	
	public BoardDAO() {
		ju = JdbcUtil.getInstance();
	}
	
	public int insert(BoardVo vo) {
		Connection con;
		PreparedStatement pstmt;
		
		// 삽입
		String sql = "insert into \"BOARD\" (\"NUM\", \"TITLE\", \"WRITER\", \"CONTENT\", \"REGDATE\", \"CNT\") values (\"BOARD_SEQ\".nextval, ?, ?, ?, sysdate, 0)";
		int result = -1;
		try {
			con = ju.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getWriter());
			pstmt.setString(3, vo.getContent());
			
			result = pstmt.executeUpdate();
			
			return result;
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public BoardVo selectOne(int num){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT \"NUM\", \"TITLE\", \"WRITER\", \"CONTENT\", \"REGDATE\", \"CNT\" FROM \"BOARD\" where \"NUM\" = ?";
				 
		BoardVo vo = null;
		
		try {
			con = ju.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				 vo = new BoardVo(
						rs.getInt(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						new Date(rs.getDate(5).getTime()),
						rs.getInt(6));
				 
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs != null) {
				try {

					rs.close(); // 
				} catch(SQLException e) {
					e.printStackTrace();
				}
				}
			if(pstmt != null) {
				try {

					pstmt.close(); // 
				} catch(SQLException e) {
					e.printStackTrace();
				}
				}
			if(con != null) {
				try {

					con.close(); // 
				} catch(SQLException e) {
					e.printStackTrace();
				}
				}
		}
				return vo;
	}
	
	
	
	public List<BoardVo> selectAll(){
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT \"NUM\", \"TITLE\", \"WRITER\", \"CONTENT\", \"REGDATE\", \"CNT\" FROM \"BOARD\" order by \"NUM\" desc";
		
		ArrayList<BoardVo> list = new ArrayList<BoardVo>();
		
		try {
			con = ju.getConnection();
			stmt = con.createStatement();
			 			
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				BoardVo vo = new BoardVo(
						rs.getInt(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						new Date(rs.getDate(5).getTime()),
						rs.getInt(6));
				list.add(vo);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs != null) {
				try {

					rs.close(); // 
				} catch(SQLException e) {
					e.printStackTrace();
				}
				}
			if(stmt != null) {
				try {

					stmt.close(); // 
				} catch(SQLException e) {
					e.printStackTrace();
				}
				}
			if(con != null) {
				try {

					con.close(); // 
				} catch(SQLException e) {
					e.printStackTrace();
				}
				}
		}
				return list;
	}
	
	
	
}
