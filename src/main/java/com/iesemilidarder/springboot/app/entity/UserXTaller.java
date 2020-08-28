package com.iesemilidarder.springboot.app.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity(name = "userxtaller")
@Table(name = "userxtaller")
public class UserXTaller implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idUxT;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idUsuarioJoven", referencedColumnName = "idUsuarioJoven")
	private UsuarioJoven usuarioJoven;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idTaller", referencedColumnName = "idTaller")
	private Taller taller;

	public UserXTaller() {
		
	}
	
	public Integer getIdUxT() {
		return idUxT;
	}

	public void setIdUxT(Integer idUxT) {
		this.idUxT = idUxT;
	}

	public UsuarioJoven getUsuarioJoven() {
		return usuarioJoven;
	}

	public void setUsuarioJoven(UsuarioJoven usuarioJoven) {
		this.usuarioJoven = usuarioJoven;
	}

	public Taller getTaller() {
		return taller;
	}

	public void setTaller(Taller taller) {
		this.taller = taller;
		taller.getUserXTaller().add(this);
	}
	
	
	
	
}
