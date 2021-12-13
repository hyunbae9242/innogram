package com.innogram.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.innogram.service.MembershipService;
import com.innogram.utils.StringUtils;

public class MembershipServiceImpl implements MembershipService {

	@Override
	public boolean setIdAndPasswordIntoSession(HttpServletRequest request) {
		String userId = request.getParameter("userId");
		String password = request.getParameter("password");
		if(StringUtils.isBlank(userId) || StringUtils.isBlank(password)) {
			return false;
		}
		HttpSession session = request.getSession();
		session.setAttribute("postUserId", userId);
		session.setAttribute("postPassword", password);
		System.out.println("login ! User Id : " + userId + ", password : " + password);
		return true;
	}

	@Override
	public boolean removeIdAndPasswordInSession(HttpServletRequest request) {
		return false;
	}
	
	@Override
	public boolean checkLogin(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String postUserId = safeToString(session.getAttribute("postUserId"));
		String postPassword = safeToString(session.getAttribute("postPassword"));
		if(StringUtils.isBlank(postUserId) || StringUtils.isBlank(postPassword)) {
			return false;
		}
		return true;
	}
	private String safeToString(Object obj) {
		return obj != null ? obj.toString() : "";
	}
}
