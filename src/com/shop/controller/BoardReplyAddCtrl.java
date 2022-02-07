package com.shop.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shop.biz.NikonBoardDAO;
import com.shop.model.NikonBoardVO;

@WebServlet("/BoardReplyAddCtrl")
public class BoardReplyAddCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        NikonBoardDAO dao = new NikonBoardDAO();
        NikonBoardVO boardData = new NikonBoardVO();
        String pageNum = request.getParameter("page");

        String id = request.getParameter("board_id");
        String subject = request.getParameter("board_subject");
        String content = request.getParameter("board_content");
        int ref = Integer.parseInt(request.getParameter("board_re_ref"));
        int lev = Integer.parseInt(request.getParameter("board_re_lev"));
        int seq = Integer.parseInt(request.getParameter("board_re_seq"));
        boardData.setReRef(ref);
        boardData.setReSeq(seq);
        dao.updateReSeq(boardData);

        boardData.setNum(dao.getSeq());
        boardData.setId(id);
        boardData.setSubject(subject);
        boardData.setContent(content);
        boardData.setReRef(ref);
        boardData.setReLevel(lev+1);
        boardData.setReSeq(seq+1);
        int cnt= dao.boardReplyInsert(boardData);
		if(cnt > 0) {
			response.sendRedirect("GetBoardListCtrl");
		} else {
			response.sendRedirect("BoardReplyFormCtrl");
		}
	}
}