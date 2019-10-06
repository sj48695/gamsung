package com.gamsung.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamsung.mapper.CommentMapper;
import com.gamsung.vo.Comment;

@Service
public class CommentServiceImpl implements CommentService{
	
	@Autowired
	private CommentMapper commentMapper;
	
	@Override
	public void writeComment(Comment comment) {
		
		commentMapper.insertComment(comment);
		
	}
	
	@Override
	public void deleteComment(int commentNo) {
		commentMapper.deleteComment(commentNo);
	}

	@Override
	public void updateComment(Comment comment) {
		commentMapper.updateComment(comment);		
	}

	@Override
	public List<Comment> findCommentListByProductNo(int productNo) {
		List<Comment> comments = commentMapper.selectCommentsByProductNo(productNo);
		return comments;
	}
	
	@Override
	public void writeRecomment(Comment comment) {		
		
		Comment parent = commentMapper.selectCommentByCommentNo(comment.getCommentNo());
		commentMapper.updateCommentStep(parent);
		
		comment.setGroupNo(parent.getGroupNo());
		comment.setDepth(parent.getDepth() + 1);
		comment.setStep(parent.getStep() + 1);		
		
		commentMapper.insertRecomment(comment);
	}
	
}
