package com.cos.blog.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cos.blog.model.User;

//스프링 시큐리티가 로그인 요청을 가로채서 로그인을 진행하고 완료되면 UserDetails타입의 오브젝트를
//스프링 시큐리티의 고유한 세션 장소에 저장해줌
public class PrincipalDetail  implements UserDetails{
	private User user; //콤포지션
	
	public PrincipalDetail(User user) {
		this.user = user;
	}
	
	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}
	
	//계정이 만료되었는지의 상태 리턴(true : 만료안됨)
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	//계정이 잠겨있는지의 상태 리턴(true : 잠기지 않음)
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	//비밀번호가 만료되어있는지 상태 리턴(true : 만료안됨)
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	//계정 활성화(사용가능) 상태 여부 리턴(true : 활성화)
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	//계정이 갖고 있는 권한 목록을 리턴(권한이 여러개일 경우 루프를 돌아야함)
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		Collection<GrantedAuthority> collectors = new ArrayList<>();
		collectors.add(()-> {return "ROLE_"+user.getRole();});
		
		return collectors;
	}

}
