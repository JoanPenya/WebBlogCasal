package com.iesemilidarder.springboot.app.service;

import java.util.List;

import com.iesemilidarder.springboot.app.entity.Usuario;

public interface IUsuarioServices {

	public List<Usuario> findAll();
	
	public void save(Usuario usuarios);
	
	public Usuario findOne(Integer idUsuario);
	
	public void delete(Integer idUsuario);
	
	public Usuario buscarPorUsername(String nombre);
	
}
