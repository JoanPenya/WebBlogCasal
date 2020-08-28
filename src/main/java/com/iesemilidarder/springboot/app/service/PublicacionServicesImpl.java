package com.iesemilidarder.springboot.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iesemilidarder.springboot.app.dao.IPublicacionDao;
import com.iesemilidarder.springboot.app.entity.Publicacion;

@Service
public class PublicacionServicesImpl implements IPublicacionServices{
	
	@Autowired 
	private IPublicacionDao publicacionDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Publicacion> findAll() {
		// TODO Auto-generated method stub
		return (List<Publicacion>) publicacionDao.findAll();
	}

	@Override
	@Transactional
	public void save(Publicacion publicacion) {
		publicacionDao.save(publicacion);
		
	}

	@Override
	@Transactional(readOnly = true)
	public Publicacion findOne(Integer idPublicacion) {
		// TODO Auto-generated method stub
		return publicacionDao.findById(idPublicacion).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Integer idPublicacion) {
		publicacionDao.deleteById(idPublicacion);
		
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Publicacion> findAll(Pageable pageable) {
		return publicacionDao.buscarlosPorId(pageable);
	}
	
}
