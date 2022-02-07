package com.shop.biz;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.shop.model.DBConn;
import com.shop.model.NikonNoticeVO;

public class NikonNoticeDAO2 {
	private Connection con = null;
	private PreparedStatement stmt = null;
	private ResultSet rs = null;
	public List<NikonNoticeVO> getList(int startRow, int endRow) {
		List<NikonNoticeVO> list = null;	
		try {
			con = DBConn.getConnection(); 
			String sql = "select * from (select rownum as rn, seq, nname, ntit, ncon, regdate visited from (select * from nikonnotice order by seq desc)) where rn between ? and ?";
			stmt = con.prepareStatement(sql); 
			stmt.setInt(1, startRow); 
			stmt.setInt(2, endRow);
			rs = stmt.executeQuery();
			if (rs.next()) {
				list = new ArrayList<>(); 
				do {
					NikonNoticeVO notice = new NikonNoticeVO();
					notice.setSeq(rs.getInt("seq"));
					notice.setNname(rs.getString("nname"));
					notice.setNtit(rs.getString("ntit"));
					notice.setNcon(rs.getString("ncon"));
					notice.setRegdate(rs.getDate("regdate"));
					notice.setVisited(rs.getInt("visited"));
					list.add(notice); 
				} while (rs.next());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConn.close(rs, stmt, con);
		}
	return list; 
	}
	public int getCount(){
		int count = 0;
		String sql = "select count(*) from nikonnotice";
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
}