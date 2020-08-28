package com.iesemilidarder.springboot.app.dao;

import org.springframework.data.repository.CrudRepository;

import com.iesemilidarder.springboot.app.entity.Usuario;


public interface IUsuarioDao extends CrudRepository<Usuario, Integer>{
	
	public Usuario findByDni(String dni);
	
}
