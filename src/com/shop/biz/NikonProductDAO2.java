package com.shop.biz;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.shop.model.*;

public class NikonProductDAO2 {
		private Connection con = null;
		private PreparedStatement stmt = null;
		private ResultSet rs = null;
		public List<NikonProductVO> getProList(int startRow, int endRow) { 
			List<NikonProductVO> list = null;	
			try {
				con = DBConn.getConnection();
				String sql = "select * from (select rownum as rn, pcode, pname, pinfo, pprice, pimg, ptype, pcnt from (select * from nikonproduct order by pcode desc)) where rn between ? and ?";
				stmt = con.prepareStatement(sql);
				stmt.setInt(1, startRow); 
				stmt.setInt(2, endRow);
				rs = stmt.executeQuery();
				if (rs.next()) { 
					list = new ArrayList<>(); 
					do {
						NikonProductVO pro = new NikonProductVO();
						pro.setPcode(rs.getString("pcode"));
						pro.setPname(rs.getString("pname"));
						pro.setPinfo(rs.getString("pinfo"));
						pro.setPprice(rs.getInt("pprice"));
						pro.setPimg(rs.getString("pimg"));
						pro.setPtype(rs.getString("ptype"));
						pro.setPcnt(rs.getInt("pcnt"));
						
		
						list.add(pro); 
					} while (rs.next());
				}
			} catch(ClassNotFoundException e) {
				System.out.println("드라이버 로딩이 되지 않았습니다.");
				e.printStackTrace();
			}  catch(SQLException e) {
				System.out.println("SQL 구문 또는 열이름, 변수명 등이 서로 맞지 않습니다.");
				e.printStackTrace();
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
				DBConn.close(rs, stmt, con);
			}
			return list;
		}

		public int getCount(){
			int count = 0;
			String sql = "select count(*) from nikonproduct";
			try {
				con = DBConn.getConnection();
				stmt = con.prepareStatement(sql);
				rs = stmt.executeQuery();
				if(rs.next()){
					count = rs.getInt(1);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				DBConn.close(rs, stmt, con);
			}
			return count;
		}
		
		public NikonProductVO getPro(NikonProductVO vo) {
			NikonProductVO pro = null;
			try {
				con = DBConn.getConnection();
				String sql = "update nikonproduct set visited=visited+1 where pcode = ?";
				stmt = con.prepareStatement(sql);
				stmt.setString(1, vo.getPcode());
				int num = stmt.executeUpdate();
				stmt.close();
				if(num>0) {
					sql = "select * from nikonproduct where seq = ?";
					stmt = con.prepareStatement(sql);
					stmt.setString(1, vo.getPcode());
					rs = stmt.executeQuery();
					if(rs.next()) {
						pro = new NikonProductVO();
						pro.setPcode(rs.getString("pcode"));
						pro.setPname(rs.getString("pname"));
						pro.setPinfo(rs.getString("pinfo"));
						pro.setPprice(rs.getInt("pprice"));
						pro.setPimg(rs.getString("pimg"));
						pro.setPtype(rs.getString("ptype"));
						pro.setPcnt(rs.getInt("pcnt"));
					}
				}	
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
				DBConn.close(rs, stmt, con);
			}
			return pro;
		}
}
