package co.sukbinggo.domain;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;

public class CustomUser extends User{
	
	@Getter
	private MemberVO member;
	
	public CustomUser(MemberVO vo) {
		// (
		super(vo.getUserid(), vo.getUserpw(), vo.getAuths().stream().map(auth -> new SimpleGrantedAuthority(auth.getAuth())).collect(Collectors.toSet()));
		member = vo;
	}

//	public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
//		super(username, password, authorities);
//		// TODO Auto-generated constructor stub
//	}
	
}
