package com.innogram.service;

import javax.servlet.http.HttpServletRequest;

public interface MembershipService {
	public boolean setIdAndPasswordIntoSession(HttpServletRequest request);
	public boolean removeIdAndPasswordInSession(HttpServletRequest request);
	public boolean checkLogin(HttpServletRequest request);
}
