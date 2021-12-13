package com.innogram.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.innogram.service.MembershipService;
import com.innogram.service.PostService;
import com.innogram.service.impl.MembershipServiceImpl;
import com.innogram.service.impl.PostServiceImpl;
import com.innogram.utils.StringUtils;

/**
 * Servlet implementation class PostServlet
 */
@WebServlet("/ajax/post/*")
public class PostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Gson gson = new Gson();
	private PostService postService = new PostServiceImpl();
	private MembershipService membershipService = new MembershipServiceImpl();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pattern = request.getRequestURI().replace("/ajax/post/", "");
		if("getPost".equals(pattern)) {
			response.getWriter().write(gson.toJson(postService.selectPost(request)));
		}else if("getPostList".equals(pattern)) {
			response.getWriter().write(gson.toJson(postService.selectPostList(request)));
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pattern = request.getRequestURI().replace("/ajax/post/", "");
		if(!membershipService.checkLogin(request)) {
			Map<String,Object> result = new HashMap<>();
			result.put("result", "fail");
			result.put("reason", "need login!");
			response.getWriter().write(gson.toJson(result)); 
		}
		if("insertPost".equals(pattern)) {
			response.getWriter().write(gson.toJson(postService.insertPost(request)));
		}else if("updatePost".equals(pattern)) {
			response.getWriter().write(gson.toJson(postService.updatePost(request)));
		}else if("deletePost".equals(pattern)) {
			response.getWriter().write(gson.toJson(postService.deletePost(request)));
		}else if("updateLike".equals(pattern)) {
			response.getWriter().write(gson.toJson(postService.updateLike(request)));
		}
	}

	
}
