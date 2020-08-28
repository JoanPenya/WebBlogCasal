package com.iesemilidarder.springboot.app.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;

@Entity(name = "publicacion")
@Table(name = "publicacion")
public class Publicacion implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idPublicacion;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "diaHoraPublicacion")
	private Date diaHoraPublicacion;

	@NotEmpty
	private String titulo;

	@NotEmpty
	private String descripcion;

	// @NotEmpty(message = "Por favor, inserte imagen ;)")
	private String foto;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idDinamizador", referencedColumnName = "idDinamizador")
	private Dinamizador dinamizador;

	@OneToMany(mappedBy = "publicacion", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Comentarios> comentarios;

	@OneToOne(mappedBy = "publicacion")
	private Taller taller;

	public Publicacion() {

	}

	public Publicacion(Date diaHoraPublicacion, String titulo,
			String descripcion, String foto, Taller taller) {
		this.diaHoraPublicacion = diaHoraPublicacion;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.foto = foto;
		this.taller.setPublicacion(this);
	}

	public Integer getIdPublicacion() {
		return idPublicacion;
	}

	public void setIdPublicacion(Integer idPublicacion) {
		this.idPublicacion = idPublicacion;
	}

	public Date getDiaHoraPublicacion() {
		return diaHoraPublicacion;
	}

	public void setDiaPublicacion(Date diaHoraPublicacion) {
		this.diaHoraPublicacion = diaHoraPublicacion;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public Dinamizador getDinamizador() {
		return dinamizador;
	}

	public void setDinamizador(Dinamizador dinamizador) {
		this.dinamizador = dinamizador;
		dinamizador.getPublicacion().add(this);
	}

	public Set<Comentarios> getComentarios() {
		return comentarios;
	}

	public void setComentarios(Set<Comentarios> comentarios) {
		this.comentarios = comentarios;
		for (Comentarios comentario : comentarios) {
			comentario.setPublicacion(this);
		}
	}

	public Taller getTaller() {
		return taller;
	}

	public void setTaller(Taller taller) {
		this.taller = taller;
	}
	
	

	@Override
	public String toString() {
		
		String publicacion = idPublicacion.toString();
		
		return  publicacion;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private static final long serialVersionUID = 1L;

}
