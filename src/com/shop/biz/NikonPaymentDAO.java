package com.shop.biz;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.shop.model.DBConn;
import com.shop.model.NikonPaymentVO;
import com.shop.model.NikonProductVO;

public class NikonPaymentDAO {
	private Connection con = null;
	private PreparedStatement stmt = null;
	private ResultSet rs = null;
	public ArrayList<NikonPaymentVO> getPaymentList(){
		ArrayList<NikonPaymentVO> payList = null;
		String sql;
		try {
			con = DBConn.getConnection();
			sql = "select * from payment order by paydate desc";
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			payList = new ArrayList<NikonPaymentVO>();
			while(rs.next()) {
				NikonPaymentVO payment = new NikonPaymentVO();
				payment.setMid(rs.getString("mid"));
				payment.setMname(rs.getString("mname"));
				payment.setMemail(rs.getString("memail"));
				payment.setMtel(rs.getString("mtel"));
				payment.setMaddr1(rs.getString("maddr1"));
				payment.setMaddr2(rs.getString("maddr2"));
				payment.setContact(rs.getString("contact"));
				payment.setMzipcode(rs.getString("mzipcode"));
				payment.setPprice(rs.getInt("pprice"));
				payment.setPaytype(rs.getString("paytype"));
				payment.setCreditnum(rs.getString("creditnum"));
				payment.setCredipw(rs.getString("credipw"));
				payList.add(payment);
			}
			rs.close();
			stmt.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			DBConn.close(rs, stmt, con);
		}
		return payList;
	}
	public ArrayList<NikonPaymentVO> getUserPaymentList(NikonPaymentVO vo){
		ArrayList<NikonPaymentVO> payList = null;
		String sql;
		try {
			con = DBConn.getConnection();
			sql = "select * from nikonpayment where mid = ?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, vo.getMid());
			rs = stmt.executeQuery();
			payList = new ArrayList<NikonPaymentVO>();
			while(rs.next()) {
				NikonPaymentVO payment = new NikonPaymentVO();
				payment.setPayid(rs.getInt("payid"));
				payment.setMid(rs.getString("mid"));
				payment.setCreditnum(rs.getString("creditnum"));
				payment.setPaytype(rs.getString("paytype"));
				payment.setPprice(rs.getInt("pprice"));				
				payList.add(payment);
			}
			rs.close();
			stmt.close();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			DBConn.close(rs, stmt, con);
		}
		return payList;
	}
	
	public int addPayment(NikonPaymentVO vo) {
		int cnt=0, upcnt=0;
		String sql;
		try {
			con = DBConn.getConnection();
			sql = "insert into nikonpayment values (nikonpayment_seq.nextval,?,?,?,?,?,?,?,?,?,?,?,?)";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, vo.getMid());
			stmt.setString(2, vo.getMname());
			stmt.setString(3, vo.getMemail());
			stmt.setString(4, vo.getMtel());
			stmt.setString(5, vo.getMaddr1());
			stmt.setString(6, vo.getMaddr2());
			stmt.setString(7, vo.getContact());
			stmt.setString(8, vo.getMzipcode());
			stmt.setInt(9, vo.getPprice());
			stmt.setString(10, vo.getPaytype());
			stmt.setString(11, vo.getCreditnum());
			stmt.setString(12, vo.getCredipw());
			cnt = stmt.executeUpdate();
			stmt.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			DBConn.close(stmt, con);
		}
		return cnt;
	}
	public int deletePayment(NikonPaymentVO vo) {
		int cnt=0, upcnt=0;
		String sql;
		try {
			con = DBConn.getConnection();
			sql = "delete payment where payid=?";
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, vo.getPayid());
			cnt = stmt.executeUpdate();
			stmt.close();
			
			sql = "update prolist set cnt=cnt+? where proid=?";
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, vo.getProcnt());
			stmt.setInt(2, vo.getProid());
			upcnt = stmt.executeUpdate();
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			DBConn.close(stmt, con);
		}
		return cnt;
	}	
	public NikonProductVO getPro(String pcode) {
		NikonProductVO pro = null;
		try {
			String sql;
			sql = "select * from nikonproduct where pcode = ?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, pcode);
			rs = stmt.executeQuery();
			if(rs.next()) {
				pro = new NikonProductVO();
				pro.setPcode(rs.getString("pcode"));
				pro.setPname(rs.getString("pname"));
				pro.setPinfo(rs.getString("getPinfo"));
				pro.setPprice(rs.getInt("pprice"));
				pro.setPimg(rs.getString("pimg"));
			}	
		} catch(Exception e) {
			e.printStackTrace();
		}
		return pro;
	}
}
