package com.gamsung.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gamsung.vo.Comment;

@Mapper
public interface CommentMapper {

	void insertComment(Comment comment);

	void deleteComment(int commentNo);

	void updateComment(Comment comment);

	List<Comment> selectCommentsByProductNo(int productNo);

	void updateCommentStep(Comment parent);

	void insertRecomment(Comment comment);

	Comment selectCommentByCommentNo(int commentNo);

}
