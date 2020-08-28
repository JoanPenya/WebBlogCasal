package com.iesemilidarder.springboot.app.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.iesemilidarder.springboot.app.entity.Taller;
import com.iesemilidarder.springboot.app.entity.UserXTaller;
import com.iesemilidarder.springboot.app.entity.UsuarioJoven;

public interface IUsuarioJovenServices {

	public List<UsuarioJoven> findAll();
	
	public Page<UsuarioJoven> findAll(Pageable pageable);
	
	public void save(UsuarioJoven usuarioJoven);
	
	public UsuarioJoven findOne(Integer idUsuarioJoven);
	
	public void delete(Integer idUsuarioJoven);
	
}
