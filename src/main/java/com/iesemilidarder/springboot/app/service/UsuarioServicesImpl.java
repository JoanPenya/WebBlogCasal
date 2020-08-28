package com.iesemilidarder.springboot.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iesemilidarder.springboot.app.dao.IUsuarioDao;
import com.iesemilidarder.springboot.app.entity.Usuario;

@Service
public class UsuarioServicesImpl implements IUsuarioServices{
	
	@Autowired 
	private IUsuarioDao usuarioDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Usuario> findAll() {
		// TODO Auto-generated method stub
		return (List<Usuario>) usuarioDao.findAll();
	}

	@Override
	@Transactional
	public void save(Usuario usuario) {
		usuarioDao.save(usuario);
		
	}

	@Override
	@Transactional(readOnly = true)
	public Usuario findOne(Integer idUsuario) {
		// TODO Auto-generated method stub
		return usuarioDao.findById(idUsuario).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Integer idUsuario) {
		usuarioDao.deleteById(idUsuario);
		
	}

	@Override
	public Usuario buscarPorUsername(String nombre) {
		return usuarioDao.findByDni(nombre);
	}
	
	
}
