package com.iesemilidarder.springboot.app.entity;

import java.io.Serializable;
import java.util.List;

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
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Entity(name = "usuario")
@Table(name = "usuarios")
public class Usuario implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idUsuario;
	
	@NotEmpty
	private String nombre;
	
	@NotEmpty
	private String apellido;
	
	@NotEmpty
	private String paisOrigen;
	
	@NotEmpty
	private String provincia;
	
	@NotEmpty
	private String barri;
	
	@NotEmpty
	private String dni;
	
	@NotNull
	private Integer telefono;
	
	@NotEmpty
	@Email
	private String email;
	
	@NotEmpty
	private String contrasenya;
	
	@OneToOne(mappedBy = "usuario")
	private Dinamizador dinamizador;
	
	@OneToOne(mappedBy = "usuario")
	private UsuarioJoven usuarioJoven;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "idUsuario")
	private List<Role> role;

	public Usuario(@NotEmpty String nombre, @NotEmpty String apellido,
			@NotEmpty String paisOrigen, @NotEmpty String provincia, @NotEmpty String barri, @NotEmpty String dni,
			@NotNull Integer telefono, @NotEmpty @Email String email, @NotEmpty String contrasenya,  Dinamizador dinamizador, UsuarioJoven usuarioJoven) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.paisOrigen = paisOrigen;
		this.provincia = provincia;
		this.barri = barri;
		this.dni = dni;
		this.telefono = telefono;
		this.email = email;
		this.contrasenya = contrasenya;
		this.dinamizador = dinamizador;
		this.dinamizador.setUsuario(this);
		this.usuarioJoven = usuarioJoven;
		this.usuarioJoven.setUsuario(this);

	}

	public Usuario() {
	}



	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	
	public String getPaisOrigen() {
		return paisOrigen;
	}

	public void setPaisOrigen(String paisOrigen) {
		this.paisOrigen = paisOrigen;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getBarri() {
		return barri;
	}

	public void setBarri(String barri) {
		this.barri = barri;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public Integer getTelefono() {
		return telefono;
	}

	public void setTelefono(Integer telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContrasenya() {
		return contrasenya;
	}

	public void setContrasenya(String contrasenya) {
		this.contrasenya = contrasenya;
	}

	public Dinamizador getDinamizador() {
		return dinamizador;
	}

	public void setDinamizador(Dinamizador dinamizador) {
		this.dinamizador = dinamizador;
	}

	public UsuarioJoven getUsuarioJoven() {
		return usuarioJoven;
	}

	public void setUsuarioJoven(UsuarioJoven usuarioJoven) {
		this.usuarioJoven = usuarioJoven;
	}
	
	public List<Role> getRole() {
		return role;
	}

	public void setRole(List<Role> role) {
		this.role = role;
	}
	
	
}
