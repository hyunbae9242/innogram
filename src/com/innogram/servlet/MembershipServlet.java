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
import com.innogram.service.MembershipService;
import com.innogram.service.impl.MembershipServiceImpl;

@WebServlet("/ajax/membership/*")
public class MembershipServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Gson gson = new Gson();
	private MembershipService membership = new MembershipServiceImpl();
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String,Object> result = new HashMap<>();
		result.put("result", "error");
		String pattern = request.getRequestURI().replace("/ajax/membership/", "");
		if("login".equals(pattern)) {
			if(membership.setIdAndPasswordIntoSession(request)) {
				result.put("result", "success");
			}
		}else if("logout".equals(pattern)) {
			if(membership.removeIdAndPasswordInSession(request)) {
				result.put("result", "success");
			}
		}
		response.getWriter().write(gson.toJson(result));
	}

}
