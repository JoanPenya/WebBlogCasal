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
import javax.validation.constraints.NotEmpty;

@Entity(name = "propuestas")
@Table(name = "propuestas")
public class Propuesta implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idPropuesta;
	
	private String propuesta;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idUsuarioJoven", referencedColumnName = "idUsuarioJoven")
	private UsuarioJoven usuarioJoven;

	public Propuesta() {

	}

	public Propuesta(@NotEmpty String propuesta) {
		this.propuesta = propuesta;
	}
	
	public Integer getIdPropuesta() {
		return idPropuesta;
	}

	public void setIdPropuesta(Integer idPropuesta) {
		this.idPropuesta = idPropuesta;
	}

	public String getPropuesta() {
		return propuesta;
	}

	public void setPropuesta(String propuesta) {
		this.propuesta = propuesta;
	}

	public UsuarioJoven getUsuarioJoven() {
		return usuarioJoven;
	}

	public void setUsuarioJoven(UsuarioJoven usuarioJoven) {
		this.usuarioJoven = usuarioJoven;
		usuarioJoven.getPropuesta().add(this);
	}

}
