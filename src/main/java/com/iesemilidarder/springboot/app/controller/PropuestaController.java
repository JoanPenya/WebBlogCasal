package com.iesemilidarder.springboot.app.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
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

import com.iesemilidarder.springboot.app.entity.Propuesta;
import com.iesemilidarder.springboot.app.entity.Usuario;
import com.iesemilidarder.springboot.app.service.IPropuestaServices;
import com.iesemilidarder.springboot.app.service.IUsuarioServices;
import com.iesemilidarder.springboot.app.util.paginator.PageRender;

@Controller
@SessionAttributes("propuesta")
public class PropuestaController {
	
	//Se generara las clase que se debe de trabajar.
	@Autowired
	private IPropuestaServices propuestaServices;
	
	@Autowired
	private IUsuarioServices serviceUsuario;
	
	//Se generara el listado de propuestas.
	@Secured({"ROLE_ADMIN", "ROLE_DINAMIZADOR"})
	@RequestMapping(value="/admin/propostes", method = RequestMethod.GET)
	public String verListado(@RequestParam(name="page", defaultValue="0") int page, Model model, Authentication authentication) {
		
		Pageable pageRequest = PageRequest.of(page, 5);
		
		Page<Propuesta> propuesta = propuestaServices.findAll(pageRequest);
		
		PageRender<Propuesta> pageRender = new PageRender<Propuesta>("/admin/propostes", propuesta);
		
		Usuario usuario = serviceUsuario.buscarPorUsername(authentication.getName());

		model.addAttribute("nombre", usuario.getNombre() + " " + usuario.getApellido());
		
		model.addAttribute("titol","administración de propostes");
		model.addAttribute("propuestas", propuesta);
		model.addAttribute("page", pageRender);
		
		return "/propostes/administracioPropostes";
	}
	
	//Se generara una pagina, donde el usuario escriba su propuesta.
	@Secured({"ROLE_ADMIN", "ROLE_DINAMIZADOR", "ROLE_USER"})
	@GetMapping("/propostes")
	public String propuestas(Map<String, Object> model, Authentication authentication) {
		
		if(authentication != null) {
			Usuario usuario = serviceUsuario.buscarPorUsername(authentication.getName());
			model.put("navBar", usuario.getNombre() + " " + usuario.getApellido());
		}
		
		Propuesta propuesta = new Propuesta();
			
		model.put("propuesta", propuesta);
		model.put("titol","administración de propostes");
		
		return "/propuestas";
	}
	
	//Para enviar la propuesta a la base de datos
	@Secured({"ROLE_USER"})
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String guardar(@Valid Propuesta propuesta, BindingResult result, Model model,
			 RedirectAttributes flash, SessionStatus status, Authentication authentication) {

		Usuario usuario = serviceUsuario.buscarPorUsername(authentication.getName());
		propuesta.setUsuarioJoven(usuario.getUsuarioJoven());

		String mensajeFlash = "Moltíssimes gracies per enviar la teva proposta ;)";

		propuestaServices.save(propuesta);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:/";
	}
	
	//Para eliminar la propuesta que puso el usuario
	@Secured({"ROLE_ADMIN", "ROLE_DINAMIZADOR"})
    @RequestMapping(value="/admin/propostes/eliminar/{id}")
	public String eliminar(@PathVariable(value="id") Integer idPropuesta, RedirectAttributes flash) {
		
		if(idPropuesta > 0) {
			propuestaServices.delete(idPropuesta);
			flash.addFlashAttribute("success", "Propuesta eliminado con éxito!");
		}
		return "redirect:/admin/propostes";
	}

}
