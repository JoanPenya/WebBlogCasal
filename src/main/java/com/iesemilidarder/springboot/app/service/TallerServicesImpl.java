package com.iesemilidarder.springboot.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iesemilidarder.springboot.app.dao.ITallerDao;
import com.iesemilidarder.springboot.app.dao.IUserXTallerDao;
import com.iesemilidarder.springboot.app.entity.Taller;
import com.iesemilidarder.springboot.app.entity.UserXTaller;

@Service
public class TallerServicesImpl implements ITallerServices{
	
	@Autowired 
	private ITallerDao tallerDao;
	
	@Autowired 
	private IUserXTallerDao userXtallerDao;

	@Override
	@Transactional(readOnly = true)
	public List<Taller> findTaller() {
		return (List<Taller>) tallerDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Taller> findTaller(Pageable pageable) {
		return tallerDao.buscarTallerId(pageable);
	}

	@Override
	@Transactional
	public void save(Taller taller) {
		tallerDao.save(taller);
		
	}

	@Override
	@Transactional(readOnly = true)
	public Taller findOne(Integer idTaller) {
		// TODO Auto-generated method stub
		return tallerDao.findById(idTaller).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Integer idTaller) {
		tallerDao.deleteById(idTaller);
		
	}

	@Override
	@Transactional(readOnly = true)
	public UserXTaller usuariosApuntados(Integer idTaller) {
		return userXtallerDao.findById(idTaller).orElse(null);
	}

	@Override
	@Transactional
	public void saveUXT(UserXTaller userXTaller) {
		userXtallerDao.save(userXTaller);
		
	}

	@Override
	@Transactional
	public void deleteUXT(Integer idUxT) {
		userXtallerDao.deleteById(idUxT);
		
	}
	
	
	
	
}
