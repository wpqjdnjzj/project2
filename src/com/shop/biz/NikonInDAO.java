package com.shop.biz;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.shop.model.DBConn;
import com.shop.model.NikonInVO;
import com.shop.model.NikonProductVO;

public class NikonInDAO {
	private Connection con = null;
	private PreparedStatement stmt = null;
	private ResultSet rs = null;
	public ArrayList<NikonInVO> getUserInList(NikonInVO vo){
		ArrayList<NikonInVO> nikoninList = null;
		String sql;
		try {
			con = DBConn.getConnection();
			sql = "select * from nikonbasket where mid=?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, vo.getMid());
			rs = stmt.executeQuery();
			nikoninList = new ArrayList<NikonInVO>();
			while(rs.next()) {
				NikonInVO nikonin = new NikonInVO();
				nikonin.setBasketno(rs.getInt("basketno"));
				nikonin.setPcode(rs.getString("pcode"));
				nikonin.setMid(rs.getString("mid"));
				nikoninList.add(nikonin);
			}
			rs.close();
			stmt.close();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			DBConn.close(rs, stmt, con);
		}
		return nikoninList;
	}
	public int addIn(NikonInVO vo) { //장바구니 추가
		int cnt=0;
		String sql;
		try {
			con = DBConn.getConnection();
			sql = "insert into nikonbasket values(nikonbasket_seq.nextval, ?, ?)";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, vo.getPcode());
			stmt.setString(2, vo.getMid());
			cnt = stmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			DBConn.close(stmt, con);
		}
		return cnt;
	}
	public int changeIn(NikonInVO vo) {
		int cnt=0, ret=0;
		String sql;
		try {
			if(ret>0) {
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			DBConn.close(stmt, con);
		}
		return cnt;
	}
	public int deleteIn(NikonInVO vo) {
		int cnt=0;
		String sql;
		try {
			con = DBConn.getConnection();
			sql = "delete from nikonbasket where basketno=?";
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, vo.getBasketno());
			cnt = stmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			DBConn.close(stmt, con);
		}
		return cnt;
	}
	public NikonProductVO getPro(int pcode) {
		NikonProductVO pro = null;
		try {
			String sql;
			sql = "select * from nikonproduct where pcode = ?";
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, pcode);
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