package com.gamsung.service;

import java.util.List;

import com.gamsung.vo.Comment;

public interface CommentService {

	void writeComment(Comment comment);

	void deleteComment(int commentNo);

	void updateComment(Comment comment);

	List<Comment> findCommentListByProductNo(int productNo);

	void writeRecomment(Comment comment);

}
