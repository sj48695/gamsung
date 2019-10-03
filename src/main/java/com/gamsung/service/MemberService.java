package com.gamsung.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gamsung.mapper.MemberMapper;
import com.gamsung.vo.Member;
import com.gamsung.vo.MemberUserDetails;

@Service
public class MemberService {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private MemberMapper memberMapper;
	
	@Nullable
	public String save(Member member) {
		String id = null;
		try {
			member.setPwd(passwordEncoder.encode(member.getPwd()));
			memberMapper.save(member);
			id = member.getId();
		} catch(Exception ex){
			ex.printStackTrace();
		}
		return id;
	}
	
	@Nullable
	public UserDetails findByUsername(String username) {
		UserDetails userDetails = null;
		try {
			Member member = memberMapper.findByUsername(username);
			userDetails = new MemberUserDetails(member);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userDetails;
	}

	public Member findMemberById(String id) {
		Member member = memberMapper.findMemberById(id);
		return member;
	}

	public List<Member> findUserList() {
		List<Member> members = memberMapper.findUserList();
		return members;
	}

	public void deleteById(Member member, String id) {
			
		if(member.isActive() == true) {
			memberMapper.deleteById(member.getId());
		} else {
			memberMapper.rebackById(member.getId());
		}
	}

	public void activateBlackList(Member member, String id) {
		if(member.isBlackList() == false) {
			memberMapper.activateBlackList(id);
		} else {
			memberMapper.deactivateBlackList(id);
		}
		
	}

	public void UpdateUser(Member member) {
		memberMapper.Update(member);
	}
	public void updateProfileImg(Member member) {
		memberMapper.updateProfileImg(member);
		
	}

	public Member findProfileImgById(String memberId) {
		Member member = memberMapper.selectProfileImgById(memberId);
		return member;
	}

	public void updateIntroduction(Member member) {
		memberMapper.updateIntroduction(member);

	}




}
