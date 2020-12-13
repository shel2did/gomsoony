package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.sql.DataSource;

import model.BbsDTO;

public class DataroomDAO {
	Connection con;
	PreparedStatement psmt;
	ResultSet rs;
	
	//기본생성자에서 DBCP(커넥션풀)을 통해 DB연결
	public DataroomDAO() {
		try {
			Context initCtx = new InitialContext();
			Context ctx = (Context)initCtx.lookup("java:comp/env");
			DataSource source = (DataSource)ctx.lookup("jdbc/myoracle");
			con = source.getConnection();
			System.out.println("DBCP연결성공");
		} catch (Exception e) {
			System.out.println("DBCP연결실패");
			e.printStackTrace();
		}

	}
	public DataroomDAO(ServletContext ctx) {
		try {
			Class.forName(ctx.getInitParameter("JDBCDriver"));
			String id = "kosmo";
			String pw = "1234";
			con = DriverManager.getConnection(
					ctx.getInitParameter("ConnectionURL"),id,pw);
			System.out.println("DB연결성공");
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("DB연결실패ㅠㅠ");
		}

	}
	public void close() {
		try {
			//사용된 자원이 있다면 자원해제 해준다.
			if(rs!=null) rs.close();
			if(psmt!=null) psmt.close();
			if(con!=null) con.close();
		}
		catch (Exception e) {
			System.out.println("자원반납시 예외발생");
		}
	}
	public int getTotalRecordCount(Map map) {
		int totalCount = 0;
		try {
			String sql = "SELECT COUNT(*) FROM dataroom ";
			if(map.get("Word")!=null) {
				sql+=" WHERE "+map.get("Column")+" "+" LIKE '%"+map.get("Word")+"%' ";}
			psmt = con.prepareStatement(sql);
			rs=psmt.executeQuery();
			rs.next();
			totalCount = rs.getInt(1);
		} catch (Exception e) {
		}
		return totalCount;
	}
	//게시물을 가져오서 RewultSet형태로 반환
	public List<DataroomDTO> selectList(Map map){
		List<DataroomDTO> bbs = new Vector<DataroomDTO>();
		String sql = "SELECT * FROM dataroom ";
		if(map.get("Word")!=null) {
			sql+=" WHERE "+map.get("Column")+" "+" LIKE '%"+map.get("Word")+"%' ";}
		sql +=" ORDER BY idx DESC";
		
		System.out.println(sql);
		try {
			psmt = con.prepareStatement(sql);
			rs=psmt.executeQuery();
			while(rs.next()) {
				DataroomDTO dto = new DataroomDTO();
				dto.setIdx(rs.getString(1));
				dto.setName(rs.getString(2));
				dto.setTitle(rs.getString(3));
				dto.setContent(rs.getString(4));
				dto.setPostdate(rs.getDate(5));
				dto.setAttachedfile(rs.getString(6));
				dto.setDowncount(rs.getInt(7));
				dto.setPass(rs.getString(8));
				dto.setVisitcount(rs.getInt(9));
				
				bbs.add(dto);
				System.out.println(dto.getName());
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return bbs;
	}
	public int insert(DataroomDTO dto) {
		int affected=0;
		try {
			String sql = "INSERT INTO dataroom ("+
		" idx,title,name,content,attachedfile,pass,downcount) "+
					" VALUES ("+" dataroom_seq.NEXTVAL,?,?,?,?,?,0)";
			psmt = con.prepareStatement(sql);
			psmt.setString(1, dto.getTitle());
			psmt.setString(2, dto.getName());
			psmt.setString(3, dto.getContent());
			psmt.setString(4, dto.getAttachedfile());
			psmt.setString(5, dto.getPass());
			
			affected = psmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return affected;
	}
	public void updateVisitCount(String idx) {
		String sql = "UPDATE dataroom SET "+" visitcount=visitcount+1 "+" WHERE idx=? ";
		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, idx);
			psmt.executeUpdate();
		} catch (Exception e) {
		}
	}
	//자료실 게시물 상세보기
	public DataroomDTO selectView(String idx) {
		DataroomDTO dto = null;
		String sql = "SELECT * FROM dataroom "+" WHERE idx=?";
		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, idx);
			rs=psmt.executeQuery();
			while(rs.next()) {
				dto = new DataroomDTO();
				dto.setIdx(rs.getString(1));
				dto.setName(rs.getString(2));
				dto.setTitle(rs.getString(3));
				dto.setContent(rs.getString(4));
				dto.setPostdate(rs.getDate(5));
				dto.setAttachedfile(rs.getString(6));
				dto.setDowncount(rs.getInt(7));
				dto.setPass(rs.getString(8));
				dto.setVisitcount(rs.getInt(9));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}
	//게시물의 일련번호, 패스워드를 통한 검증(수정, 삭제시 호출됨)
	public boolean isCurrectPassword(String pass, String idx) {
		boolean isCorr = true;
		try {
			String sql = "SELECT COUNT(*) FROM dataroom "+ " WHERE pass=? AND idx=?";
			psmt = con.prepareStatement(sql);
			psmt.setString(1, pass);
			psmt.setString(2, idx);
			rs=psmt.executeQuery();
			rs.next();
			if(rs.getInt(1)==0) {
				isCorr = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isCorr;	
	}
	public int delete(String idx) {
		int affected = 0;
		try {
			String query = "DELETE FROM dataroom"+" WHERE idx=?";
			
			psmt = con.prepareStatement(query);
			psmt.setString(1, idx);
			affected=psmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return affected;
	}
	
	public int update(DataroomDTO dto) {
		int affected=0;
		try {
			String query = "UPDATE dataroom SET title=?, name=?, "
					+ " content=?, attachedfile=?, pass=? WHERE idx=?";
			
			psmt = con.prepareStatement(query);
			psmt.setString(1, dto.getTitle());
			psmt.setString(2, dto.getName());
			psmt.setString(3, dto.getContent());
			psmt.setString(4, dto.getAttachedfile());
			psmt.setString(5, dto.getPass());
			
			psmt.setString(6, dto.getIdx());
			
			affected = psmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("update중 예외발생");
			e.printStackTrace();
		}
		return affected;
	}
	public void downCountPlus(String idx) {
		String sql = "UPDATE dataroom SET downcount=downcount+1 WHERE idx=? ";
		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, idx);
			psmt.executeUpdate();
		} catch (Exception e) {
		}
	}
	public List<DataroomDTO> selectListPage(Map map) {
		List<DataroomDTO> bbs = new Vector<DataroomDTO>();
		
		String sql = " "
				+" SELECT * FROM ( "
				+"	 SELECT Tb.*, ROWNUM rNum FROM ( "
				+"	    SELECT * FROM dataroom ";
			if(map.get("Word")!=null)
			{
				sql +=" WHERE "+ map.get("Column") +" "
				  +" LIKE '%"+ map.get("Word") +"%' "; 
			}
			sql += " ORDER BY idx DESC "
			    +"    ) Tb "
			    +" ) "
			    +" WHERE rNum BETWEEN ? AND ?";
			System.out.println("쿼리문:"+ sql);//테이블이 바뀌면 컬럼도 바뀌자노... ㅡㅡ;
			
		try {
			psmt = con.prepareStatement(sql);
			psmt.setInt(1, Integer.parseInt(map.get("start").toString()));
			psmt.setInt(2, Integer.parseInt(map.get("end").toString()));
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				DataroomDTO dto = new DataroomDTO();

				dto.setIdx(rs.getString(1));
				dto.setName(rs.getString(2));
				dto.setTitle(rs.getString(3));
				dto.setContent(rs.getString(4));
				dto.setPostdate(rs.getDate(5));
				dto.setAttachedfile(rs.getString(6));
				dto.setDowncount(rs.getInt(7));
				dto.setPass(rs.getString(8));
				dto.setVisitcount(rs.getInt(9));
				bbs.add(dto);
			}	
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();//요건 항상 있는게 좋아..그래야 에러를 찾지...
		}
		
		return bbs;
		
	}
}
