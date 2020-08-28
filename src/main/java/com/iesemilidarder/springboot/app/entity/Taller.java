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
import javax.validation.constraints.NotNull;

@Entity(name = "taller")
@Table(name = "taller")
public class Taller implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idTaller;
	
	
	private String diaHoraTaller;
	
	@NotNull
	private Integer plaza;
	
	@NotEmpty
	private String sitio;
	
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(unique = true, name="publicacion_id_publicacion")
	private Publicacion publicacion;

	@OneToMany(
			mappedBy = "taller",
			cascade = CascadeType.ALL,
			fetch = FetchType.LAZY
	)
	private Set<UserXTaller> userXTaller;
	
	
	
	public Taller(@NotNull String diaHoraTaller, @NotNull Integer plaza, @NotEmpty String sitio, 
			Publicacion publicacion, Set<UserXTaller> userXTaller) {
		this.diaHoraTaller = diaHoraTaller;
		this.plaza = plaza;
		this.sitio = sitio;
		this.publicacion = publicacion;
		this.userXTaller = new HashSet<>();
	}
	
	public Taller() {
		this.userXTaller = new HashSet<>();
	}

	public Integer getIdTaller() {
		return idTaller;
	}

	public void setIdTaller(Integer idTaller) {
		this.idTaller = idTaller;
	}

	public String getDiaHoraTaller() {
		return diaHoraTaller;
	}

	public void setDiaHoraTaller(String diaHoraTaller) {
		this.diaHoraTaller = diaHoraTaller;
	}

	public Integer getPlaza() {
		return plaza;
	}

	public void setPlaza(Integer plaza) {
		this.plaza = plaza;
	}

	public String getSitio() {
		return sitio;
	}

	public void setSitio(String sitio) {
		this.sitio = sitio;
	}

	public Publicacion getPublicacion() {
		return publicacion;
	}

	public void setPublicacion(Publicacion publicacion) {
		this.publicacion = publicacion;
	}

	public Set<UserXTaller> getUserXTaller() {
		return userXTaller;
	}

	public void setUserXTaller(Set<UserXTaller> userXTaller) {
		this.userXTaller = userXTaller;
	}	
	
}
