package com.iesemilidarder.springboot.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iesemilidarder.springboot.app.dao.IPropuestasDao;
import com.iesemilidarder.springboot.app.entity.Propuesta;

@Service
public class PropuestaServicesImpl implements IPropuestaServices{
	
	@Autowired 
	private IPropuestasDao propuestaDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Propuesta> findAll() {
		// TODO Auto-generated method stub
		return (List<Propuesta>) propuestaDao.findAll();
	}

	@Override
	@Transactional
	public void save(Propuesta propuesta) {
		propuestaDao.save(propuesta);
		
	}

	@Override
	@Transactional(readOnly = true)
	public Propuesta findOne(Integer idPropuesta) {
		// TODO Auto-generated method stub
		return propuestaDao.findById(idPropuesta).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Integer idPropuesta) {
		propuestaDao.deleteById(idPropuesta);
		
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Propuesta> findAll(Pageable pageable) {
		return propuestaDao.buscarlosPorId(pageable);
	}

	
	
}
