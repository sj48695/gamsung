package com.gamsung.vo;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Data
public class MemberUserDetails implements UserDetails{
	
	private Member member;
	
	public MemberUserDetails() {}
	public MemberUserDetails(Member member) {
		this.member = member;
	}
	
	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		//return AuthorityUtils.commaSeparatedStringToAuthorityList(member.getType());
		ArrayList<SimpleGrantedAuthority> grants = new ArrayList<>();
		grants.add(new SimpleGrantedAuthority(member.getType()));
		return grants;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return member.getPwd();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return member.getId();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return member.isActive();
	}
}
