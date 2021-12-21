package com.innogram.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.innogram.service.CommentService;
import com.innogram.service.MembershipService;
import com.innogram.service.impl.CommentServiceImpl;
import com.innogram.service.impl.MembershipServiceImpl;

@WebServlet("/ajax/comment/*")
public class CommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Gson gson = new Gson();
	private CommentService commentService = new CommentServiceImpl();
	private MembershipService membershipService = new MembershipServiceImpl();
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pattern = request.getRequestURI().replace("/ajax/comment/", "");
		if("getCommentList".equals(pattern)) {
			System.out.println("getCommentList");
			response.getWriter().write(gson.toJson(commentService.selectCommentList(request)));
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pattern = request.getRequestURI().replace("/ajax/comment/", "");
		if(!membershipService.checkLogin(request)) {
			Map<String,Object> result = new HashMap<>();
			result.put("result", "fail");
			result.put("reason", "need login!");
			response.getWriter().write(gson.toJson(result)); 
		}
		if("setComment".equals(pattern)) {
			response.getWriter().write(gson.toJson(commentService.insertComment(request)));
		}else if("deleteComment".equals(pattern)) {
			response.getWriter().write(gson.toJson(commentService.updateComment(request)));
		}
	}

}
