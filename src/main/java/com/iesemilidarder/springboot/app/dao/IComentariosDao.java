package com.iesemilidarder.springboot.app.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.iesemilidarder.springboot.app.entity.Comentarios;
import com.iesemilidarder.springboot.app.entity.Propuesta;

public interface IComentariosDao extends PagingAndSortingRepository<Comentarios, Integer>{
	
	
	@Query(value = "select u from comentarios u ORDER BY idComentario desc")
	public Page<Comentarios> buscarlosPorId(Pageable pageable);
	
	@Query(value = "select u from comentarios u ORDER BY idComentario desc")
	public List<Comentarios> buscarlosPorId();
}
