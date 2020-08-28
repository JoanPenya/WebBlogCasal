package com.iesemilidarder.springboot.app.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iesemilidarder.springboot.app.dao.IUsuarioDao;
import com.iesemilidarder.springboot.app.entity.Role;
import com.iesemilidarder.springboot.app.entity.Usuario;


@Service("jpaUserDetailsService")
public class JpaUserDetailsService implements UserDetailsService {
	
	@Autowired
	private IUsuarioDao usuarioDao;
	
	private Logger logger = LoggerFactory.getLogger(JpaUserDetailsService.class);
	
	@Override
	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String dni) throws UsernameNotFoundException {
		
		Usuario usuario = usuarioDao.findByDni(dni);
		
		if(usuario == null) {
        	logger.error("Error en el Login: no existe el usuario '" + dni + "' en el sistema!");
        	throw new UsernameNotFoundException("DNI: " + dni + " no existe en el sistema!");
        }
        
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        
        for(Role role: usuario.getRole()) {
        	logger.info("Role: ".concat(role.getRol()));
        	authorities.add(new SimpleGrantedAuthority(role.getRol()));
        }
        
        if(authorities.isEmpty()) {
        	logger.error("Error en el Login: Usuario '" + usuario.getNombre() + ' '+ usuario.getApellido() + "' no tiene roles asignados!");
        	throw new UsernameNotFoundException("Error en el Login: usuario '" + usuario.getNombre() + ' '+ usuario.getApellido() + "' no tiene roles asignados!");
        }
        
		return new User(usuario.getDni(), usuario.getContrasenya(), true, true, true, true, authorities);
		
	}

}
