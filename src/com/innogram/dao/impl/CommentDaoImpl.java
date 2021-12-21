package com.innogram.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.innogram.common.SqlSessionManager;
import com.innogram.dao.CommentDao;
import com.innogram.vo.CommentVO;

public class CommentDaoImpl implements CommentDao {

	SqlSessionFactory sqlSessionFactory = SqlSessionManager.getSqlSessionFactory();
	
	@Override
	public int insertComment(CommentVO comment) {
		try(SqlSession session =  sqlSessionFactory.openSession(true)) {
			return session.insert("com.innogram.dao.CommentDao.insertComment",comment);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int updateComment(CommentVO comment) {
		try(SqlSession session =  sqlSessionFactory.openSession(true)) {
			return session.insert("com.innogram.dao.CommentDao.updateComment",comment);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public CommentVO selectComment(CommentVO comment) {
		return null;
	}

	@Override
	public List<CommentVO> selectCommentList(CommentVO comment) {
		try(SqlSession session =  sqlSessionFactory.openSession()) {
			return session.selectList("com.innogram.dao.CommentDao.selectCommentList",comment);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int deleteComment(CommentVO comment) {
		return 0;
	}
	public static void main(String[] args) {
		CommentDao commentDao = new CommentDaoImpl();
		CommentVO comment = new CommentVO();
		comment.setPostId(10);
		comment.setCommentUserId("asdqw");
		comment.setCommentDepth(0);
		comment.setCommentContents("zzzzzzzzzzz");
		System.out.println(commentDao.insertComment(comment));
	}

}
