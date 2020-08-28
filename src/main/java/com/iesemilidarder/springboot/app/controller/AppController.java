package com.iesemilidarder.springboot.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.iesemilidarder.springboot.app.entity.Usuario;
import com.iesemilidarder.springboot.app.service.IUsuarioServices;

@Controller
public class AppController {

	//Invocamos las clases services, para que hagan su trabajo:
	@Autowired
	private IUsuarioServices serviceUsuario;
	
	//Solo nos dirige el dashboard de la administracion de la pagina
	@GetMapping("/admin")
	@Secured({ "ROLE_ADMIN", "ROLE_DINAMIZADOR" })
	public String admin(Model model, Authentication authentication) {

		Usuario usuario = serviceUsuario.buscarPorUsername(authentication.getName());

		model.addAttribute("nombre", usuario.getNombre() + " " + usuario.getApellido());
		
		model.addAttribute("titol", "Dashboard");
		return "administrador";
	}

}
