package com.shop.biz;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.shop.model.*;

public class ProlistDAO2 {
		private Connection con = null;
		private PreparedStatement stmt = null;
		private ResultSet rs = null;
		public List<ProlistVO> getProList(int startRow, int endRow) { 
			List<ProlistVO> list = null;	
			try {
				con = DBConn.getConnection();
				String sql = "select * from (select rownum as rn, proid, protype, proname, procom, price, cnt, imgdata, visited from (select * from prolist order by proid desc)) where rn between ? and ?";
				stmt = con.prepareStatement(sql);
				stmt.setInt(1, startRow);
				stmt.setInt(2, endRow);
				rs = stmt.executeQuery();
				if (rs.next()) { 
					list = new ArrayList<>(); 
					do {
						
						ProlistVO pro = new ProlistVO();
						pro.setProid(rs.getInt("proid"));
						pro.setProtype(rs.getString("protype"));
						pro.setProname(rs.getString("proname"));
						pro.setProcom(rs.getString("procom"));
						pro.setPrice(rs.getInt("price"));
						pro.setCnt(rs.getInt("cnt"));
						pro.setImgData(rs.getString("imgdata"));
						pro.setVisited(rs.getInt("visited"));
		
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
			String sql = "select count(*) from prolist";
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
		
		public ProlistVO getPro(ProlistVO vo) {
			ProlistVO pro = null;
			try {
				con = DBConn.getConnection();
				String sql = "update prolist set visited=visited+1 where proid = ?";
				stmt = con.prepareStatement(sql);
				stmt.setInt(1, vo.getProid());
				int num = stmt.executeUpdate();
				stmt.close();
				if(num>0) {
					sql = "select * from prolist where seq = ?";
					stmt = con.prepareStatement(sql);
					stmt.setInt(1, vo.getProid());
					rs = stmt.executeQuery();
					if(rs.next()) {
						pro = new ProlistVO();
						pro.setProid(rs.getInt("proid"));
						pro.setProtype(rs.getString("protype"));
						pro.setProname(rs.getString("proname"));
						pro.setProcom(rs.getString("procom"));
						pro.setPrice(rs.getInt("price"));
						pro.setCnt(rs.getInt("cnt"));
						pro.setImgData(rs.getString("imgdata"));
						pro.setVisited(rs.getInt("visited"));
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
