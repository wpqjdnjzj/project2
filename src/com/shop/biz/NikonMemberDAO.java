package com.shop.biz;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.shop.model.DBConn;
import com.shop.model.NikonMemberVO;

public class NikonMemberDAO {
	private Connection con = null;
	private PreparedStatement stmt = null;
	private ResultSet rs = null;
	public ArrayList<NikonMemberVO> getMemberList() { 
		ArrayList<NikonMemberVO> memberList = null;
		try {
			con = DBConn.getConnection();
			String sql = "select * from nikonmember";
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			memberList = new ArrayList<NikonMemberVO>();
			while(rs.next()) {
				String mid = rs.getString("mid");
				String mpw = rs.getString("mpw");
				String mname = rs.getString("mname");
				String mtel = rs.getString("mtel");
				String memail = rs.getString("memail");
				String mzipcode = rs.getString("mzipcode");
				String maddr1 = rs.getString("maddr1");
				String maddr2 = rs.getString("maddr2");

				NikonMemberVO member = new NikonMemberVO();
				member.setMid(mid);
				member.setMpw(mpw);
				member.setMname(mname);
				member.setMtel(mtel);
				member.setMemail(memail);
				member.setMzipcode(mzipcode);
				member.setMaddr1(maddr1);
				member.setMaddr2(maddr2);

				memberList.add(member);
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
		return memberList;
	}
	
	public NikonMemberVO getMember(NikonMemberVO vo) {
		NikonMemberVO member = null;
		try {
			con = DBConn.getConnection();
			String sql = "update nikonmember set visited=visited+1 where mid = ?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, vo.getMid());
			int cnt = stmt.executeUpdate();
			stmt.close();
			if(cnt>0) {
				sql = "select * from nikonmember where mid = ?";
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
			}	
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			DBConn.close(rs, stmt, con);
		}
		return member;
	}
	public int updatemember(NikonMemberVO vo) {
		int cnt = 0;
		try {
			con = DBConn.getConnection();
			String sql = "update nikonmember set mpw=?, mname=?, memail=?, mtel=?, mzipcode=?, maddr1=?, maddr2=? where mid=?";
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
			String sql = "delete from member where mid=?";
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
}