package com.iesemilidarder.springboot.app.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity(name = "usuarioJoven")
@Table(name = "usuarioJoven")
public class UsuarioJoven implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idUsuarioJoven;
	
	@NotEmpty
	private String genero;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(unique = true)
	private Usuario usuario;
	
	@OneToMany(
			mappedBy = "usuarioJoven",
			cascade = CascadeType.ALL,
			fetch = FetchType.LAZY
	)
	private Set<Comentarios> comentarios;
	
	@OneToMany(
			mappedBy = "usuarioJoven",
			cascade = CascadeType.ALL,
			fetch = FetchType.LAZY
	)
	private Set<UserXTaller> userXTaller;
	
	@OneToMany(
			mappedBy = "usuarioJoven",
			cascade = CascadeType.ALL,
			fetch = FetchType.LAZY
	)
	private Set<Propuesta> propuesta;

	public UsuarioJoven(@NotEmpty String genero, Set<Comentarios> comentarios,
			Set<UserXTaller> userXTaller, 
			Set<Propuesta> propuesta) {
		this.genero = genero;
		this.comentarios = new HashSet<>();
		this.userXTaller = new HashSet<>();
		this.propuesta = new HashSet<>();
	}
	
	public UsuarioJoven() {
		this.comentarios = new HashSet<>();
		this.userXTaller = new HashSet<>();
		this.propuesta = new HashSet<>();
	}
	
	
	public Integer getIdUsuarioJoven() {
		return idUsuarioJoven;
	}

	public void setIdUsuarioJoven(Integer idUsuarioJoven) {
		this.idUsuarioJoven = idUsuarioJoven;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public Set<Comentarios> getComentarios() {
		return comentarios;
	}

	public void setComentarios(Set<Comentarios> comentarios) {
		this.comentarios = comentarios;
		for(Comentarios comentario : comentarios) {
			comentario.setUsuarioJoven(this);
		}
	}
	
	public Set<UserXTaller> getUserXTaller() {
		return userXTaller;
	}

	public void setUserXTaller(Set<UserXTaller> userXTaller) {
		this.userXTaller = userXTaller;
		for(UserXTaller userXTalleres : userXTaller) {
			userXTalleres.setUsuarioJoven(this);
		}
	}
	
	public Set<Propuesta> getPropuesta() {
		return propuesta;
	}

	public void setPropuesta(Set<Propuesta> propuesta) {
		this.propuesta = propuesta;
		for(Propuesta propuestas : propuesta) {
			propuestas.setUsuarioJoven(this);
		}
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	

	@Override
	public String toString() {
		
		String usuarioJoven = idUsuarioJoven.toString();
		
		return usuarioJoven;
	}



	private static final long serialVersionUID = 1L;

	
	
}
