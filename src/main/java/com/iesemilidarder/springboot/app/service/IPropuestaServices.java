package com.iesemilidarder.springboot.app.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.iesemilidarder.springboot.app.entity.Propuesta;

public interface IPropuestaServices {

	public List<Propuesta> findAll();
	
	public Page<Propuesta> findAll(Pageable pageable);
	
	public void save(Propuesta propuesta);
	
	public Propuesta findOne(Integer idPropuesta);
	
	public void delete(Integer idPropuesta);
	
	
}
