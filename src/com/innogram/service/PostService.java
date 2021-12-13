package com.innogram.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.innogram.vo.PostVO;

public interface PostService {
	public Map<String,Object> insertPost(HttpServletRequest request);
	public Map<String,Object> updatePost(HttpServletRequest request);
	public PostVO selectPost(HttpServletRequest request);
	public List<PostVO> selectPostList(HttpServletRequest request);
	public Map<String,Object> deletePost(HttpServletRequest request);
	public Map<String,Object> updateLike(HttpServletRequest request);
}
