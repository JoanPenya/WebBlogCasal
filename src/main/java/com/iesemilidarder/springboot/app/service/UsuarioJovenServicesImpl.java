package com.iesemilidarder.springboot.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iesemilidarder.springboot.app.dao.IUsuarioJovenDao;
import com.iesemilidarder.springboot.app.entity.Taller;
import com.iesemilidarder.springboot.app.entity.UserXTaller;
import com.iesemilidarder.springboot.app.entity.UsuarioJoven;

@Service
public class UsuarioJovenServicesImpl implements IUsuarioJovenServices{
	
	@Autowired 
	private IUsuarioJovenDao usuarioJovenDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<UsuarioJoven> findAll() {
		// TODO Auto-generated method stub
		return (List<UsuarioJoven>) usuarioJovenDao.findAll();
	}

	@Override
	@Transactional
	public void save(UsuarioJoven usuarioJoven) {
		usuarioJovenDao.save(usuarioJoven);
		
	}

	@Override
	@Transactional(readOnly = true)
	public UsuarioJoven findOne(Integer idUsuarioJoven) {
		// TODO Auto-generated method stub
		return usuarioJovenDao.findById(idUsuarioJoven).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Integer idUsuarioJoven) {
		usuarioJovenDao.deleteById(idUsuarioJoven);
		
	}

	@Override
	@Transactional(readOnly = true)
	public Page<UsuarioJoven> findAll(Pageable pageable) {
		return usuarioJovenDao.findAll(pageable);
	}
	
	
}
