package com.iesemilidarder.springboot.app.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

//import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.iesemilidarder.springboot.app.entity.Propuesta;

public interface IPropuestasDao extends PagingAndSortingRepository<Propuesta, Integer>{

	@Query(value = "select u from propuestas u ORDER BY idPropuesta desc")
	public Page<Propuesta> buscarlosPorId(Pageable pageable);
	
}
