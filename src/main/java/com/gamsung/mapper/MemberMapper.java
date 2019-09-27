package com.gamsung.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gamsung.vo.Member;

@Mapper
public interface MemberMapper {

	void save(Member member);

	Member findByUsername(String username);

	Member findMemberById(String id);

	List<Member> findUserList();

}
