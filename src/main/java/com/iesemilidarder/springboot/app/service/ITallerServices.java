package com.iesemilidarder.springboot.app.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.iesemilidarder.springboot.app.entity.Taller;
import com.iesemilidarder.springboot.app.entity.UserXTaller;

public interface ITallerServices {
	
	public List<Taller> findTaller();
	
	public Page<Taller> findTaller(Pageable pageable);
	
	public void save(Taller taller);
	
	public Taller findOne(Integer idTaller);
	
	public void delete(Integer idTaller);
	
	public UserXTaller usuariosApuntados(Integer idTaller);
	
	public void saveUXT(UserXTaller userXTaller);
	
	public void deleteUXT(Integer idUxT);
	
	

}
