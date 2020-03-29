package com.carteira.sercurity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.carteira.entity.TipoFuncao;
import com.carteira.entity.Usuario;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class JwtUserFactory {

	public static JwtUser create(Usuario user) {
		return new JwtUser(user.getId(), user.getEmail(), user.getSenha(), createGrantedAuthorities(user.getFuncao()));
	}
	
	private static List<GrantedAuthority> createGrantedAuthorities(TipoFuncao role) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(role.toString()));
		return authorities;
	}

}
