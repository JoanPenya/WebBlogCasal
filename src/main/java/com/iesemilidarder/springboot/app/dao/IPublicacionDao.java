package com.iesemilidarder.springboot.app.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.iesemilidarder.springboot.app.entity.Publicacion;

public interface IPublicacionDao extends PagingAndSortingRepository<Publicacion, Integer>{
	
	@Query(value = "select u from publicacion u ORDER BY idPublicacion desc")
	public Page<Publicacion> buscarlosPorId(Pageable pageable);
	
}
