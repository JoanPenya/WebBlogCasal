package com.iesemilidarder.springboot.app.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.iesemilidarder.springboot.app.entity.UserXTaller;

public interface IUserXTallerServices {

	public List<UserXTaller> findAll();
	
	public Page<UserXTaller> findAll(Pageable pageable);

	public void save(UserXTaller userXTaller);

	public UserXTaller findOne(Integer idUxT);

}
