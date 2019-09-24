package com.gamsung.service;

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



}
