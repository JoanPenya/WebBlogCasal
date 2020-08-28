package com.iesemilidarder.springboot.app.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.iesemilidarder.springboot.app.entity.Taller;

public interface ITallerDao extends PagingAndSortingRepository<Taller, Integer>{
	
	@Query(value = "select u from taller u ORDER BY idTaller desc")
	public Page<Taller> buscarTallerId(Pageable pageable);
	
}
