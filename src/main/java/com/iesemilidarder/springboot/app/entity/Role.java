package com.iesemilidarder.springboot.app.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity(name = "role")
@Table(name = "role", uniqueConstraints= {@UniqueConstraint(columnNames= {"idUsuario", "rol"})})
public class Role implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idRol;
	
	private String rol;
	
	public Role() {
	
	}
	
	public Role(String rol) {
		this.rol = rol;
	}

	public Integer getId() {
		return idRol;
	}

	public void setId(Integer idRol) {
		this.idRol = idRol;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	@Override
	public String toString() {
		return rol;
	}
	
	
	
}
