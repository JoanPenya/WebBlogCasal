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

@Entity(name = "dinamizador")
@Table(name = "dinamizador")
public class Dinamizador implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idDinamizador;
	
	@NotEmpty
	private String tipo;
	
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(unique = true)
	private Usuario usuario;	
	
	@OneToMany(
			mappedBy = "dinamizador",
			cascade = CascadeType.ALL,
			fetch = FetchType.LAZY
	)
	private Set<Publicacion> publicacion;
	
	public Dinamizador() {
		this.publicacion = new HashSet<>();
	}
	
	public Dinamizador(@NotEmpty String tipo) {
		this.tipo = tipo;
	}

	public Integer getIdDinamizador() {
		return idDinamizador;
	}

	public void setIdDinamizador(Integer idDinamizador) {
		this.idDinamizador = idDinamizador;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Set<Publicacion> getPublicacion() {
		return publicacion;
	}

	public void setPublicacion(Set<Publicacion> publicacion) {
		this.publicacion = publicacion;
		for(Publicacion publicaciones : publicacion) {
			publicaciones.setDinamizador(this);
		}
	}

	@Override
	public String toString() {
		
		String dinamizador = idDinamizador.toString();
		
		return dinamizador;
	}

	
	
	

}
