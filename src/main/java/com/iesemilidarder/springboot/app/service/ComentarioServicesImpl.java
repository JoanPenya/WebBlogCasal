package com.iesemilidarder.springboot.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iesemilidarder.springboot.app.dao.IComentariosDao;
import com.iesemilidarder.springboot.app.entity.Comentarios;

@Service
public class ComentarioServicesImpl implements IComentarioServices{
	
	@Autowired 
	private IComentariosDao comentarioDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Comentarios> findAll() {
		return (List<Comentarios>) comentarioDao.buscarlosPorId();
	}

	@Override
	@Transactional(readOnly = true)
	public Comentarios findOne(Integer idComentario) {
		return comentarioDao.findById(idComentario).orElse(null);
	}

	@Override
	@Transactional
	public void save(Comentarios comentario) {
		comentarioDao.save(comentario);
		
	}

	@Override
	@Transactional
	public void delete(Integer idComentario) {
		comentarioDao.deleteById(idComentario);
		
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Comentarios> findAll(Pageable pageable) {
		return comentarioDao.buscarlosPorId(pageable);
	}
	
}
