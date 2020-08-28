package com.iesemilidarder.springboot.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iesemilidarder.springboot.app.dao.IUserXTallerDao;
import com.iesemilidarder.springboot.app.entity.UserXTaller;

@Service
public class UserXTallerServicesImpl implements IUserXTallerServices{
	
	@Autowired 
	private IUserXTallerDao userXTallerDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<UserXTaller> findAll() {
		// TODO Auto-generated method stub
		return (List<UserXTaller>) userXTallerDao.findAll();
	}

	@Override
	@Transactional
	public void save(UserXTaller userXTaller) {
		userXTallerDao.save(userXTaller);
		
	}

	@Override
	@Transactional(readOnly = true)
	public UserXTaller findOne(Integer idUxT) {
		// TODO Auto-generated method stub
		return userXTallerDao.findById(idUxT).orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<UserXTaller> findAll(Pageable pageable) {
		return userXTallerDao.findAll(pageable);
	}
	
}
