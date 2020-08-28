package com.iesemilidarder.springboot.app.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.iesemilidarder.springboot.app.entity.Publicacion;

public interface IPublicacionServices {

	public List<Publicacion> findAll();
	
	public Page<Publicacion> findAll(Pageable pageable);

	public void save(Publicacion publicacion);

	public Publicacion findOne(Integer idPublicacion);

	public void delete(Integer idPublicacion);
	
	

}
