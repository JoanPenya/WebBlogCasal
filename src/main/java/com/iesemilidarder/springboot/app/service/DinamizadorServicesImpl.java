package com.iesemilidarder.springboot.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iesemilidarder.springboot.app.dao.IDinamizadorDao;
import com.iesemilidarder.springboot.app.entity.Dinamizador;

@Service
public class DinamizadorServicesImpl implements IDinamizadorServices{
	
	@Autowired 
	private IDinamizadorDao dinamizadorDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Dinamizador> findAll() {
		// TODO Auto-generated method stub
		return (List<Dinamizador>) dinamizadorDao.findAll();
	}

	@Override
	@Transactional
	public void save(Dinamizador dinamizador) {
		dinamizadorDao.save(dinamizador);
		
	}

	@Override
	@Transactional(readOnly = true)
	public Dinamizador findOne(Integer idDinamizador) {
		// TODO Auto-generated method stub
		return dinamizadorDao.findById(idDinamizador).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Integer idDinamizador) {
		dinamizadorDao.deleteById(idDinamizador);
		
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Dinamizador> findAll(Pageable pageable) {
		return dinamizadorDao.findAll(pageable);
	}

	
	
}
