package com.innogram.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.innogram.common.SqlSessionManager;
import com.innogram.dao.LikeDao;
import com.innogram.dao.PostDao;
import com.innogram.dao.impl.LikeDaoImpl;
import com.innogram.dao.impl.PostDaoImpl;
import com.innogram.service.PostService;
import com.innogram.utils.ParseUtils;
import com.innogram.utils.StringUtils;
import com.innogram.vo.LikeVO;
import com.innogram.vo.PostVO;

public class PostServiceImpl implements PostService {
	private PostDao postDao = new PostDaoImpl();
	private LikeDao likeDao = new LikeDaoImpl();

	@Override
	public Map<String,Object> insertPost(HttpServletRequest request) {
		Map<String,Object> result = new HashMap<>();
		result.put("result", "fail");
		result.put("reason", "post is null!");
		PostVO post = ParseUtils.parseVOFromReqeust(request, PostVO.class);
		if(post != null) {
			HttpSession session = request.getSession();
			String postIp = getRemoteAddr(request);
			System.out.println(postIp);
			String postUserId = StringUtils.safeToString(session.getAttribute("postUserId"));
			String postPassword = StringUtils.safeToString(session.getAttribute("postPassword"));
			if(StringUtils.isBlank(postUserId) || StringUtils.isBlank(postPassword)) {
				result.put("reason", "need login!");
				return result;
			}
			post.setPostIp(postIp);
			post.setPostUserId(postUserId);
			post.setPostPassword(postPassword);
			if(postDao.insertPost(post) > 0) {
				result.put("result", "success");
			}else {
				result.put("reason", "insertPost fail!");
			}
			return result;
		}
		return result;
	}

	@Override
	public Map<String,Object> updatePost(HttpServletRequest request) {
		Map<String,Object> result = new HashMap<>();
		result.put("result", "fail");
		result.put("reason", "post is null!");
		PostVO post =ParseUtils.parseVOFromReqeust(request, PostVO.class);
		if(post != null) {
			if(postDao.updatePost(post) > 0) {
				result.put("result", "success");
				return result;
			}else {
				result.put("reason", "updatePost fail!");
			}
		}
		return result;
	}

	@Override
	public PostVO selectPost(HttpServletRequest request) {
		PostVO post = new PostVO();
		try {
			Integer postId = Integer.parseInt(request.getParameter("postId"));
			post.setPostId(postId);
			return postDao.selectPost(post);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return post;
	}

	@Override
	public List<PostVO> selectPostList(HttpServletRequest request) {
		PostVO post = new PostVO();
		String postTitle = request.getParameter("postTitle");
		String postUserId = request.getParameter("postUserId");
		Object deleteYnOjb = request.getParameter("postDeleteYn");
		Integer postDeleteYn = 0;
		if(deleteYnOjb instanceof Integer) {
			postDeleteYn = Integer.parseInt(deleteYnOjb.toString());
		}
		if(!StringUtils.isBlank(postTitle)) {
			post.setPostTitle(postTitle);
		}
		if(!StringUtils.isBlank(postUserId)) {
			post.setPostUserId(postUserId);
		}
		if(postDeleteYn != null) {
			post.setPostDeleteYn(postDeleteYn);
		} 
		List<PostVO> result = postDao.selectPostList(post);
		System.out.println(result);
		return result;
	}

	@Override
	public Map<String,Object> deletePost(HttpServletRequest request) {
		Map<String,Object> result = new HashMap<>();
		result.put("result", "fail");
		result.put("reason", "post is null!");
		PostVO post = ParseUtils.parseVOFromReqeust(request, PostVO.class);
		if(post != null) {
			post.setPostDeleteYn(1);
			if(postDao.updatePost(post) > 0) {
				result.put("result", "success");
			}else {
				result.put("reason", "deletePost fail!");
			}
		}
		return result;
	}
	
	@Override
	public Map<String,Object> updateLike(HttpServletRequest request) {
		SqlSessionFactory sqlSessionFactory = SqlSessionManager.getSqlSessionFactory();
		SqlSession session = null;
		Map<String,Object> result = new HashMap<>();
		result.put("result", "fail");
		result.put("reason", "post is null!");
		PostVO post =ParseUtils.parseVOFromReqeust(request, PostVO.class);
		if(post != null) {
			try {
				session = sqlSessionFactory.openSession(false);
				LikeVO like = new LikeVO();
				like.setPostId(post.getPostId());
				like.setLikeIp(getRemoteAddr(request));
				if(likeDao.insertLike(like,session) > 0) {
					if(postDao.updateLikePost(post,session) > 0) {
						result.put("result", "success");
						session.commit();
						return result;
					}else {
						result.put("reason", "updatePost fail!");
						session.rollback();
					}
				}else {
					result.put("reason", "insertLike fail!");
					session.rollback();
				}	
			}catch(Exception e) {
				
			}finally {
				if(session != null) {
					session.close();
					
				}
			}
		}
		return result;
	}
	private String getRemoteAddr(HttpServletRequest request) {
		return request.getHeader("X-FORWARDED-FOR") != null ? request.getHeader("X-FORWARDED-FOR") : request.getRemoteAddr();
	}
}
