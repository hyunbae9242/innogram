package com.innogram.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.innogram.dao.CommentDao;
import com.innogram.dao.impl.CommentDaoImpl;
import com.innogram.service.CommentService;
import com.innogram.utils.ParseUtils;
import com.innogram.utils.StringUtils;
import com.innogram.vo.CommentVO;
import com.innogram.vo.PostVO;

public class CommentServiceImpl implements CommentService {
	private CommentDao commentService = new CommentDaoImpl();
	
	@Override
	public int insertComment(HttpServletRequest request) {
		HttpSession session = request.getSession();
		CommentVO comment = ParseUtils.parseVOFromReqeust(request, CommentVO.class);
		String postUserId = StringUtils.safeToString(session.getAttribute("postUserId"));
		if(StringUtils.isBlank(postUserId)) {
			return 0;
		}
		comment.setCommentUserId(postUserId);
		return commentService.insertComment(comment);
	}

	@Override
	public int updateComment(HttpServletRequest request) {
		return commentService.updateComment(ParseUtils.parseVOFromReqeust(request, CommentVO.class));
	}

	@Override
	public CommentVO selectComment(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CommentVO> selectCommentList(HttpServletRequest request) {
		CommentVO comment = new CommentVO();
		Integer postId = Integer.parseInt(request.getParameter("postId"));
		comment.setPostId(postId);
		return commentService.selectCommentList(comment);
	}

	@Override
	public int deleteComment(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	private String getRemoteAddr(HttpServletRequest request) {
		return request.getHeader("X-FORWARDED-FOR") != null ? request.getHeader("X-FORWARDED-FOR") : request.getRemoteAddr();
	}
}
