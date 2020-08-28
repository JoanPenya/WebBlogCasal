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

import com.iesemilidarder.springboot.app.entity.Usuario;
import com.iesemilidarder.springboot.app.entity.UsuarioJoven;
import com.iesemilidarder.springboot.app.service.IUsuarioJovenServices;
import com.iesemilidarder.springboot.app.service.IUsuarioServices;
import com.iesemilidarder.springboot.app.util.paginator.PageRender;

@Controller
@SessionAttributes("usuarioJoven")
public class UsuarisController {
	
	//Invocamos las clases services, para que hagan su trabajo:
	
	@Autowired
	private IUsuarioJovenServices usuarioJovenServices;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Autowired
	private IUsuarioServices serviceUsuario;
	
	//Administración de usuarios
	@Secured({"ROLE_ADMIN", "ROLE_DINAMIZADOR"})
	@RequestMapping(value="/admin/users", method = RequestMethod.GET)
	public String verListado(@RequestParam(name="page", defaultValue="0") int page, Model model, Authentication authentication) {
		
		Pageable pageRequest = PageRequest.of(page, 5);
		
		//Uso de pagina
		Page<UsuarioJoven> usuarioJoven = usuarioJovenServices.findAll(pageRequest);
		
		PageRender<UsuarioJoven> pageRender = new PageRender<UsuarioJoven>("/admin/users", usuarioJoven);
		
		Usuario usuario = serviceUsuario.buscarPorUsername(authentication.getName());

		model.addAttribute("nombre", usuario.getNombre() + " " + usuario.getApellido());
		model.addAttribute("titol","administración de usuarios");
		model.addAttribute("usuarioJoven", usuarioJoven);
		model.addAttribute("page", pageRender);
		
		return "/users/administracioUsuaris";
	}
	
	//Se envia un formulario en blanco en el creacion de usuarios
	@Secured({"ROLE_ADMIN", "ROLE_DINAMIZADOR"})
	@GetMapping("/admin/users/crear")
	public String crear(Map<String, Object> model, Authentication authentication) {
		
		Usuario usuario = serviceUsuario.buscarPorUsername(authentication.getName());

		model.put("nombre", usuario.getNombre() + " " + usuario.getApellido());
		
		UsuarioJoven usuarioJoven = new UsuarioJoven();
		model.put("usuarioJoven", usuarioJoven);
		model.put("titol", "Nou usuari");
		return "users/form";
	}
	
	//Para editar usuario
	@Secured({"ROLE_ADMIN", "ROLE_DINAMIZADOR"})
	@GetMapping("/admin/users/editar/{id}")
	public String editarUsuariJove(@PathVariable(value="id") Integer idUsuarioJoven, Map<String, Object> model, 
			RedirectAttributes flash, Authentication authentication) {
    	
    	UsuarioJoven usuarioJoven = usuarioJovenServices.findOne(idUsuarioJoven);
    	
    	if(idUsuarioJoven > 0) {
    		if (usuarioJoven == null) {
    			flash.addFlashAttribute("error", "El ID que estabas buscando, no existe.");
    			return "redirect:users/form";
    		}
    	} else {
    		flash.addFlashAttribute("error", "El ID que estabas buscando, no existe.");
			return "redirect:users/form";
    	}
    	
    	Usuario usuario = serviceUsuario.buscarPorUsername(authentication.getName());

		model.put("nombre", usuario.getNombre() + " " + usuario.getApellido());
    	
    	model.put("usuarioJoven", usuarioJoven);
    	model.put("titol", "Editar: " + usuarioJoven.getUsuario().getNombre() + ' ' + usuarioJoven.getUsuario().getApellido());
    	return "users/form";
    }
	
	//Para guardar usuario
	@Secured({ "ROLE_ADMIN", "ROLE_DINAMIZADOR" })
	@RequestMapping(value = "/admin/users/", method = RequestMethod.POST)
	public String guardar(@Valid UsuarioJoven usuarioJoven, BindingResult result, Model model, 
			RedirectAttributes flash, SessionStatus status) {
		
		//Funcion para encriptar contrasenyas
		String pwdPlano = usuarioJoven.getUsuario().getContrasenya();
		
		String pwdEncriptado = passwordEncoder.encode(pwdPlano);
		
		usuarioJoven.getUsuario().setContrasenya(pwdEncriptado);

		String mensajeFlash = (usuarioJoven.getUsuario().getIdUsuario() != null) ? "Cliente editado con éxito!"
				: "Cliente creado con éxito!";

		usuarioJovenServices.save(usuarioJoven);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:/admin/users";
	}
	
	//Eliminar usuario.
	@Secured({ "ROLE_ADMIN", "ROLE_DINAMIZADOR" })
	@RequestMapping(value = "/admin/users/eliminar/{id}")
	public String eliminarUsuario(@PathVariable(value = "id") Integer idUsuarioJoven, RedirectAttributes flash) {

		if (idUsuarioJoven > 0) {
			usuarioJovenServices.delete(idUsuarioJoven);

			flash.addFlashAttribute("success", "Usuario eliminado con éxito!");
		}
		return "redirect:/admin/users";
	}
	
}
