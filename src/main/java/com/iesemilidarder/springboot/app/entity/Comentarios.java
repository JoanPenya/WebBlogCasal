package com.iesemilidarder.springboot.app.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;

@Entity( name = "comentarios")
@Table( name = "comentarios")
public class Comentarios implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idComentario;
	
	@NotEmpty
	private String comentario;
	
	@Temporal(TemporalType.DATE)
	private Date diaHoraComentario;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idUsuarioJoven", referencedColumnName = "idUsuarioJoven")
	private UsuarioJoven usuarioJoven;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idPublicacion", referencedColumnName = "idPublicacion")
	private Publicacion publicacion;
	
	public Comentarios() {
		
	}

	public Comentarios(@NotEmpty String comentario, Date diaHoraComentario, 
			Publicacion publicacion, UsuarioJoven usuarioJoven) {
		super();
		this.comentario = comentario;
		this.diaHoraComentario = diaHoraComentario;
		this.usuarioJoven = usuarioJoven;
		this.publicacion = publicacion;
	}
	
	public Integer getIdComentario() {
		return idComentario;
	}

	public void setIdComentario(Integer idComentario) {
		this.idComentario = idComentario;
	}

	public UsuarioJoven getUsuarioJoven() {
		return usuarioJoven;
	}

	public void setUsuarioJoven(UsuarioJoven usuarioJoven) {
		this.usuarioJoven = usuarioJoven;
		usuarioJoven.getComentarios().add(this);
	}

	public Publicacion getPublicacion() {
		return publicacion;
	}

	public void setPublicacion(Publicacion publicacion) {
		this.publicacion = publicacion;
		publicacion.getComentarios().add(this);
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public Date getDiaHoraComentario() {
		return diaHoraComentario;
	}

	public void setDiaHoraComentario(Date diaHoraComentario) {
		this.diaHoraComentario = diaHoraComentario;
	}

	@Override
	public String toString() {
		return comentario;
	}
	
	
	
	
}
