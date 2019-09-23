package com.gamsung.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.gamsung.mapper.MemberMapper;
import com.gamsung.vo.Member;

public class MemberService {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private MemberMapper memberMapper;
	
	public String save(Member member) {
		String id = null;
		try {
			member.setPassword(passwordEncoder.encode(member.getPassword()));
			memberMapper.save(member);
			id = member.getId();
		} catch(Exception ex){
			ex.printStackTrace();
		}
		return id;
	}

}
