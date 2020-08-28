 package com.iesemilidarder.springboot.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.iesemilidarder.springboot.app.entity.Comentarios;
import com.iesemilidarder.springboot.app.entity.Usuario;
import com.iesemilidarder.springboot.app.service.IComentarioServices;
import com.iesemilidarder.springboot.app.service.IUsuarioServices;
import com.iesemilidarder.springboot.app.util.paginator.PageRender;

@Controller
@SessionAttributes("comentarios")
public class ComentarioController {
	
	//Invocamos las clases services, para que hagan su trabajo:
	@Autowired
	private IComentarioServices comentarioServices;
	
	@Autowired
	private IUsuarioServices serviceUsuario;
	
	//Para administrar los comentarios
	@Secured(value = { "ROLE_ADMIN", "ROLE_DINAMIZADOR" })
	@RequestMapping("/admin/comentaris")
	public String mostrarComentarios(@RequestParam(name = "page", defaultValue = "0") int page, Model model, Authentication authentication) {
		
		//Para generar la paginacion de los comentarios
		Pageable pageRequest = PageRequest.of(page, 5);

		Page<Comentarios> comentarios = comentarioServices.findAll(pageRequest);
		
		PageRender<Comentarios> pageRender = new PageRender<Comentarios>("/admin/comentaris", comentarios);
		Usuario usuario = serviceUsuario.buscarPorUsername(authentication.getName());

		model.addAttribute("nombre", usuario.getNombre() + " " + usuario.getApellido());
		model.addAttribute("comentarios", comentarios);
		model.addAttribute("titol", "administracio de comentaris");
		model.addAttribute("page", pageRender);

		return "comentaris/administracioComentaris";
	}
	
	//Para eliminar los comentarios
	@Secured({ "ROLE_ADMIN", "ROLE_DINAMIZADOR" })
	@RequestMapping(value = "/admin/comentaris/eliminar/{id}")
	public String eliminarComentario(@PathVariable(value = "id") Integer idComentario, RedirectAttributes flash) {

		if (idComentario > 0) {
			comentarioServices.delete(idComentario);
			flash.addFlashAttribute("success", "Comentario eliminado con éxito!");
		}
		return "redirect:/admin/comentaris";
	}
	
}
