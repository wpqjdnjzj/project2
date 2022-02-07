package com.shop.biz;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.shop.model.*;

public class NikonProductDAO {
		private Connection con = null;
		private PreparedStatement stmt = null;
		private ResultSet rs = null;
		public ArrayList<NikonProductVO> getProList() { 
			ArrayList<NikonProductVO> proList = null;
			try {
				con = DBConn.getConnection();
				String sql = "select * from nikonproduct order by pcode desc, ptype asc";
				stmt = con.prepareStatement(sql);
				rs = stmt.executeQuery();
				proList = new ArrayList<NikonProductVO>();
				while(rs.next()) {
					String pcode = rs.getString("pcode");
					String pname = rs.getString("pname");
					String pinfo = rs.getString("pinfo");
					int pprice = rs.getInt("pprice");
					String pimg = rs.getString("pimg");
					String ptype = rs.getString("ptype");
					int pcnt = rs.getInt("pcnt");
					NikonProductVO pro = new NikonProductVO();

					pro.setPcode(pcode);
					pro.setPname(pname);
					pro.setPinfo(pinfo);
					pro.setPprice(pprice);
					pro.setPimg(pimg);
					pro.setPtype(ptype);
					pro.setPcnt(pcnt);
					proList.add(pro);
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
			return proList;
		}
		
		public NikonProductVO getPro(NikonProductVO vo) {
			NikonProductVO pro = null;
			try {
				con = DBConn.getConnection();
				String sql;
					sql = "select * from nikonproduct where pcode = ?";
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
						
//					}
				}	
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
				DBConn.close(rs, stmt, con);
			}
			return pro;
		}
		public int updatePro(NikonProductVO vo) {
			int num = 0;
			try {
				con = DBConn.getConnection();
				String sql = "update prolist set pname=?, pinfo=?, pprice=?, pimg=?, ptype=?, pcnt=? where pcode=?";
				stmt = con.prepareStatement(sql);
				stmt.setString(1, vo.getPname());
				stmt.setString(2, vo.getPinfo());
				stmt.setInt(3, vo.getPprice());
				stmt.setString(4, vo.getPimg());
				stmt.setString(5, vo.getPtype());
				stmt.setInt(6, vo.getPcnt());
				stmt.setString(7, vo.getPcode());
				num = stmt.executeUpdate();
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
				DBConn.close(stmt, con);
			}
			return num;
		}
		public int deletePro(NikonProductVO vo) {
			int num=0;
			try {
				con = DBConn.getConnection();
				String sql = "delete from nikonproduct where pcode=?";
				stmt = con.prepareStatement(sql);
				stmt.setString(1, vo.getPcode());
				num = stmt.executeUpdate();
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
				DBConn.close(stmt, con);
			}
			return num;
		}
		
		public int addPro(NikonProductVO vo) {
			int num = 0;
			try {
				con = DBConn.getConnection();
				String sql = "insert into nikonproduct values(nikonproduct_seq.nextval, ?, ?, ?, ?, ?, ?)";
				stmt = con.prepareStatement(sql);
				stmt.setString(1, vo.getPname());
				stmt.setString(2, vo.getPinfo());
				stmt.setInt(3, vo.getPprice());
				stmt.setString(4, vo.getPimg());
				stmt.setString(5, vo.getPtype());
				stmt.setInt(6, vo.getPcnt());
				num = stmt.executeUpdate();
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
				DBConn.close(stmt, con);
			}
			return num;
		}
}
