package com.gamsung.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.gamsung.vo.Member;

@Mapper
public interface MemberMapper {

	void save(Member member);

	Member findByUsername(String username);

}
