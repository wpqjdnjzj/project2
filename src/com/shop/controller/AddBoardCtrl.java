package com.shop.controller;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.shop.biz.NikonBoardDAO;
import com.shop.model.NikonBoardVO;

//글쓰기
@WebServlet("/AddBoardCtrl")
public class AddBoardCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int fileSize= 5*1024*1024;
        try {
			String saveDirectory = "C:\\dev\\jmj\\FileUpload";
			MultipartRequest multi = new MultipartRequest(request, saveDirectory, fileSize, "utf-8");		//덮어쓰기
            String fileName = "";
            Enumeration<String> names = multi.getFileNames();
            if(names.hasMoreElements()) {
                String name = names.nextElement();
                fileName = multi.getFilesystemName(name);
            }
            
            NikonBoardDAO dao = new NikonBoardDAO();
            NikonBoardVO board = new NikonBoardVO();
            
            board.setNum(dao.getSeq()); 
            board.setId(multi.getParameter("board_id")); // 히든값
            board.setSubject(multi.getParameter("board_subject"));
            board.setContent(multi.getParameter("board_content"));
            board.setFile(multi.getParameter("board_file"));
        
    		int cnt = dao.addBoard(board);
    		
    		if(cnt != 0) {
    			response.sendRedirect("GetBoardListCtrl");
    		} else {
    			response.sendRedirect("addBoard.jsp");
    		} 
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("글 작성 오류 : " + e.getMessage());
        }
	}
}