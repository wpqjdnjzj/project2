package com.shop.biz;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.shop.model.DBConn;
import com.shop.model.NikonMemberVO;
public class NikonMemberDAO2 {
	private Connection con = null;
	private PreparedStatement stmt = null;
	private ResultSet rs = null;
	
	public NikonMemberVO getMember(NikonMemberVO vo) {
		NikonMemberVO member = null;
		try {
			con = DBConn.getConnection();
			String	sql = "select * from nikonmember where mid = ? and mpw = ?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, vo.getMid());
			stmt.setString(2, vo.getMpw());
			rs = stmt.executeQuery();
			member = new NikonMemberVO();
			if(rs.next()) {				
				member.setMid(rs.getString("mid"));
				member.setMpw(rs.getString("mpw"));
				member.setMname(rs.getString("mname"));
				member.setMemail(rs.getString("memail"));
				member.setMtel(rs.getString("mtel"));
				member.setMzipcode(rs.getString("mzipcode"));
				member.setMaddr1(rs.getString("maddr1"));
				member.setMaddr2(rs.getString("maddr2"));
				
			} else {
				System.out.println("회원이 아닙니다.");
			}	
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			DBConn.close(rs, stmt, con);
		}
		return member;
	}
	
	public NikonMemberVO myPage(NikonMemberVO vo) {
		NikonMemberVO member = null;
		try {
			con = DBConn.getConnection();
			String sql = "select * from nikonmember where mid = ?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, vo.getMid());
			rs = stmt.executeQuery();
			if(rs.next()) {				
				member = new NikonMemberVO();
				
				member.setMid(rs.getString("mid"));
				member.setMpw(rs.getString("mpw"));
				member.setMname(rs.getString("mname"));
				member.setMemail(rs.getString("memail"));
				member.setMtel(rs.getString("mtel"));
				member.setMzipcode(rs.getString("mzipcode"));
				member.setMaddr1(rs.getString("maddr1"));
				member.setMaddr2(rs.getString("maddr2"));
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			DBConn.close(rs, stmt, con);
		}
		return member;
	}
	
	public int updateMember(NikonMemberVO vo) {
		int cnt = 0;
		try {
			con = DBConn.getConnection();
			String sql = "update nikonmember set mpw=?, mname=?, mtel=?, memail=?, mzipcode=?, maddr1=?, maddr2=? where mid=?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, vo.getMpw());
			stmt.setString(2, vo.getMname());
			stmt.setString(3, vo.getMtel());
			stmt.setString(4, vo.getMemail());
			stmt.setString(5, vo.getMzipcode());
			stmt.setString(6, vo.getMaddr1());
			stmt.setString(7, vo.getMaddr2());
			stmt.setString(8, vo.getMid());
			cnt = stmt.executeUpdate();
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			DBConn.close(stmt, con);
		}
		return cnt;
	}
	public int deletemember(NikonMemberVO vo) {
		int cnt=0;
		try {
			con = DBConn.getConnection();
			String sql = "delete from nikonmember where mid=?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, vo.getMid());
			cnt = stmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			DBConn.close(stmt, con);
		}
		return cnt;
	}
	public int addMember(NikonMemberVO vo) {
		int cnt = 0;
		try {
			con = DBConn.getConnection();
			String sql = "insert into nikonmember values (?, ?, ?, ?, ?, ?, ?, ?)";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, vo.getMid());
			stmt.setString(2, vo.getMpw());
			stmt.setString(3, vo.getMname());
			stmt.setString(4, vo.getMtel());
			stmt.setString(5, vo.getMemail());
			stmt.setString(6, vo.getMzipcode());
			stmt.setString(7, vo.getMaddr1());
			stmt.setString(8, vo.getMaddr2());
			cnt = stmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			DBConn.close(stmt, con);
		}
		return cnt;
	}
	public int dupCheck(NikonMemberVO vo) {
		int cnt = 0;
		try {
			con = DBConn.getConnection();
			String sql = "select * from nikonmember where mid = ?";
			System.out.println("입력한 아이디 : "+vo.getMid());
			stmt = con.prepareStatement(sql);
			stmt.setString(1, vo.getMid());
			rs = stmt.executeQuery();
			if(rs.next()) {
				cnt = 1;		
			} else {
				cnt = 0;		
			}
		} catch(Exception e) {
			System.out.println("데이터를 비교하지 못했습니다.");
			e.printStackTrace();
		}
		return cnt;
	}
}