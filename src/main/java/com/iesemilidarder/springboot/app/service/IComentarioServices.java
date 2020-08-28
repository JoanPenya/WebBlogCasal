package com.iesemilidarder.springboot.app.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.iesemilidarder.springboot.app.entity.Comentarios;

public interface IComentarioServices {
	
	public List<Comentarios> findAll();
	
	public Comentarios findOne(Integer idComentario);
	
	public void save(Comentarios comentario);
	
	public void delete(Integer idComentario);
	
	public Page<Comentarios> findAll(Pageable pageRequest);
	

}
