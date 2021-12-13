package com.innogram.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.innogram.common.SqlSessionManager;
import com.innogram.dao.LikeDao;
import com.innogram.dao.PostDao;
import com.innogram.dao.impl.LikeDaoImpl;
import com.innogram.dao.impl.PostDaoImpl;
import com.innogram.service.PostService;
import com.innogram.utils.StringUtils;
import com.innogram.vo.LikeVO;
import com.innogram.vo.PostVO;

public class PostServiceImpl implements PostService {
	private PostDao postDao = new PostDaoImpl();
	private LikeDao likeDao = new LikeDaoImpl();
	private Gson gson = new Gson();

	@Override
	public Map<String,Object> insertPost(HttpServletRequest request) {
		Map<String,Object> result = new HashMap<>();
		result.put("result", "fail");
		result.put("reason", "post is null!");
		PostVO post = parsePostVOFromReqeust(request);
		if(post != null) {
			HttpSession session = request.getSession();
			String postIp = getRemoteAddr(request);
			System.out.println(postIp);
			String postUserId = safeToString(session.getAttribute("postUserId"));
			String postPassword = safeToString(session.getAttribute("postPassword"));
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
		PostVO post =parsePostVOFromReqeust(request);
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
		Object idObj = request.getParameter("postId");
		if(idObj instanceof Integer) {
			Integer postId = Integer.parseInt(request.getParameter("postId"));
			post.setPostId(postId);
			return postDao.selectPost(post);	
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
		return postDao.selectPostList(post);
	}

	@Override
	public Map<String,Object> deletePost(HttpServletRequest request) {
		Map<String,Object> result = new HashMap<>();
		result.put("result", "fail");
		result.put("reason", "post is null!");
		PostVO post = parsePostVOFromReqeust(request);
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
		PostVO post =parsePostVOFromReqeust(request);
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
	
	
	private PostVO parsePostVOFromReqeust(HttpServletRequest request) {
		JsonParser parser = new JsonParser();
		try {
			BufferedReader br = request.getReader();
			String line = null;
			StringBuffer sb = new StringBuffer();
			while((line = br.readLine()) != null) {
				sb.append(line);
			}
			return gson.fromJson(parser.parse(safeToString(sb)), PostVO.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	private String getRemoteAddr(HttpServletRequest request) {
		return request.getHeader("X-FORWARDED-FOR") != null ? request.getHeader("X-FORWARDED-FOR") : request.getRemoteAddr();
	}
	
	private String safeToString(Object obj) {
		return obj != null ? obj.toString() : "";
	}
}
