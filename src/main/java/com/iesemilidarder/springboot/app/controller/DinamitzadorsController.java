package com.iesemilidarder.springboot.app.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.iesemilidarder.springboot.app.entity.Dinamizador;
import com.iesemilidarder.springboot.app.entity.Usuario;
import com.iesemilidarder.springboot.app.service.IDinamizadorServices;
import com.iesemilidarder.springboot.app.service.IUsuarioServices;
import com.iesemilidarder.springboot.app.util.paginator.PageRender;

@Controller
@SessionAttributes("dinamizador")
public class DinamitzadorsController {

	//Invocamos las clases services, para que hagan su trabajo:
	
	@Autowired
	private IDinamizadorServices dinamizadorServices;
	
	@Autowired
	private IUsuarioServices serviceUsuario;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	//Administración de dinamizadores
	@RequestMapping(value = "/admin/dinamitzadors", method = RequestMethod.GET)
	@Secured({ "ROLE_ADMIN" })
	public String verListado(@RequestParam(name="page", defaultValue="0") int page, Model model, Authentication authentication) {
		
		Pageable pageRequest = PageRequest.of(page, 5);
		
		Page<Dinamizador> dinamizador = dinamizadorServices.findAll(pageRequest);
		
		Usuario usuario = serviceUsuario.buscarPorUsername(authentication.getName());

		model.addAttribute("nombre", usuario.getNombre() + " " + usuario.getApellido());
		
		PageRender<Dinamizador> pageRender = new PageRender<Dinamizador>("/admin/dinamitzadors", dinamizador);
		model.addAttribute("titol", "administración de dinamizador");
		model.addAttribute("dinamizador", dinamizador);
		model.addAttribute("page", pageRender);

		return "/dinamitzadors/administracioDinamitzadors";
	}
	
	//Se envia un formulario en blanco en el creacion de Dinamizadores
	@GetMapping("/admin/dinamitzadors/crear")
	@Secured({"ROLE_ADMIN"})	
	public String crear(Map<String, Object> model, Authentication authentication) {
		Dinamizador dinamizador = new Dinamizador();
		Usuario usuario = serviceUsuario.buscarPorUsername(authentication.getName());

		model.put("nombre", usuario.getNombre() + " " + usuario.getApellido());
		model.put("dinamizador", dinamizador);
		model.put("titol", "Nou dinamitzador");
		return "dinamitzadors/form";
	}
	
	//Para editar dinamizadores
	@GetMapping("/admin/dinamitzadors/editar/{id}")
	@Secured({"ROLE_ADMIN"})
	public String editarUsuariJove(@PathVariable(value = "id") Integer idDinamizador, Map<String, Object> model,
			RedirectAttributes flash, Authentication authentication) {

		Dinamizador dinamizador = null;

		if (idDinamizador > 0) {
			dinamizador = dinamizadorServices.findOne(idDinamizador);
			if (dinamizador == null) {
				flash.addFlashAttribute("error", "El ID que estabas buscando, no existe.");
				return "redirect:dinamitzadors/form";
			}
		} else {
			flash.addFlashAttribute("error", "El ID que estabas buscando, no existe.");
			return "redirect:dinamitzadors/form";
		}
		
		Usuario usuario = serviceUsuario.buscarPorUsername(authentication.getName());

		model.put("nombre", usuario.getNombre() + " " + usuario.getApellido());
		
		model.put("dinamizador", dinamizador);
		model.put("titol",
				"Editar: " + dinamizador.getUsuario().getNombre() + ' ' + dinamizador.getUsuario().getApellido());
		return "dinamitzadors/form";
	}
	
	//Para guardar dinamizadores
	@Secured({ "ROLE_ADMIN"})
	@RequestMapping(value = "/admin/dinamitzadors/", method = RequestMethod.POST)
	public String guardar(@Valid Dinamizador dinamitzador, BindingResult result, Model model, 
			RedirectAttributes flash, SessionStatus status) {
		
		//Funcion para encriptar contrasenyas
		String pwdPlano = dinamitzador.getUsuario().getContrasenya();
		
		String pwdEncriptado = passwordEncoder.encode(pwdPlano);
		
		dinamitzador.getUsuario().setContrasenya(pwdEncriptado);

		String mensajeFlash = (dinamitzador.getUsuario().getIdUsuario() != null) ? "S'ha fet canvis la informació del dinamitzador"
				: "Dinamitzador creat amb exit!";

		dinamizadorServices.save(dinamitzador);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:/admin/dinamitzadors/";
	}
	
	//Eliminar usuario.
	@Secured({ "ROLE_ADMIN"})
	@RequestMapping(value = "/admin/dinamitzadors/eliminar/{id}")
	public String eliminarDinamizador(@PathVariable(value = "id") Integer idDinamizador, RedirectAttributes flash) {

		if (idDinamizador > 0) {
			dinamizadorServices.delete(idDinamizador);
			
			flash.addFlashAttribute("success", "Dinamizador eliminado con éxito!");
		}
		return "redirect:/admin/dinamitzadors";
	}

}
