package com.iesemilidarder.springboot.app.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.iesemilidarder.springboot.app.entity.Dinamizador;
public interface IDinamizadorServices {

	public List<Dinamizador> findAll();
	
	public Page<Dinamizador> findAll(Pageable pageable);
	
	public void save(Dinamizador dinamizador);
	
	public Dinamizador findOne(Integer idDinamizador);
	
	public void delete(Integer idDinamizador);
	
	
}
