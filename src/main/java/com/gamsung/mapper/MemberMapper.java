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

	void deleteById(String id);

	void rebackById(String id);

	void activateBlackList(String id);

	void deactivateBlackList(String id);

	void updateProfileImg(Member member);

	Member selectProfileImgById(String memberId);

	void updateIntroduction(Member member);

	Member selectStoreById(String id);

}
