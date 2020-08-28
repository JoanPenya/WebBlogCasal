package com.iesemilidarder.springboot.app.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.iesemilidarder.springboot.app.entity.Comentarios;
import com.iesemilidarder.springboot.app.entity.Publicacion;
import com.iesemilidarder.springboot.app.entity.Usuario;
import com.iesemilidarder.springboot.app.service.IComentarioServices;
import com.iesemilidarder.springboot.app.service.IPublicacionServices;
import com.iesemilidarder.springboot.app.service.IUploadFileService;
import com.iesemilidarder.springboot.app.service.IUsuarioServices;
import com.iesemilidarder.springboot.app.util.paginator.PageRender;

@Controller
@SessionAttributes("publicacion")
public class PublicacionController {
	
	//Invocamos las clases services, para que hagan su trabajo:
	@Autowired
	private IUploadFileService uploadFotoService;
	
	@Autowired
	private IUsuarioServices serviceUsuario;
	
	@Autowired
	private IPublicacionServices publicacionServices;
	
	@Autowired
	private IComentarioServices comentarioServices;
	
	//Para generar los post a través del index.
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(@RequestParam(name = "page", defaultValue = "0") int page, Model model, Authentication authentication) {

		Pageable pageRequest = PageRequest.of(page, 6);
		
		Pageable carrousel = PageRequest.of(page, 5);

		Page<Publicacion> publicacion = publicacionServices.findAll(pageRequest);
		
		Page<Publicacion> carPublicacion = publicacionServices.findAll(carrousel);
		
		if(authentication != null) {
			Usuario usuario = serviceUsuario.buscarPorUsername(authentication.getName());
			model.addAttribute("navBar", usuario.getNombre() + " " + usuario.getApellido());
		}
		
		PageRender<Publicacion> pageRender = new PageRender<Publicacion>("/", publicacion);
		model.addAttribute("carPublicacion", carPublicacion);
		model.addAttribute("publicacion", publicacion);
		model.addAttribute("titol", "Espais Joves");
		model.addAttribute("page", pageRender);
		return "index";
	}
	
	//Para guardar el comentario
	@Secured({ "ROLE_USER" })
	@RequestMapping(value="/publicacion/{id}", method = RequestMethod.POST)
	public String guardarComentario(@PathVariable(value = "id") Integer idPublicacion, @Valid Comentarios comentarios, BindingResult result, Model model,
			 RedirectAttributes flash, SessionStatus status, Authentication authentication) {
		
		//Se genera el id del usuario
		Usuario usuario = serviceUsuario.buscarPorUsername(authentication.getName());
		comentarios.setUsuarioJoven(usuario.getUsuarioJoven());
		
		//Se genera el dia que se publico dicho comentario
		comentarios.setDiaHoraComentario(new Date());
		
		//se genera la id del post que comentas
		Publicacion publicacion = publicacionServices.findOne(idPublicacion);
		comentarios.setPublicacion(publicacion);
		
		String mensajeFlash =  "Comentari publicat amb exit!";

		comentarioServices.save(comentarios);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:/publicacion/{id}";
	}
	
	//Para visualizar la publicacion/taller en el post
	@RequestMapping(value="/publicacion/{id}", method = RequestMethod.GET)
	public String Mostrar(@PathVariable(value = "id") Integer idPublicacion,
			Map<String, Object> model, RedirectAttributes flash, SessionStatus status, 
			Authentication authentication) {
		
		Publicacion publicacion = null;
		
		Comentarios nuevoComentarios = new Comentarios();
		
		
		if (idPublicacion > 0) {
			publicacion = publicacionServices.findOne(idPublicacion);
		} else {
			return "error/publicacion";
		}
		
		if(authentication != null) {
			Usuario usuario = serviceUsuario.buscarPorUsername(authentication.getName());
			model.put("navBar", usuario.getNombre() + " " + usuario.getApellido());
		}
		
		model.put("titol", publicacion.getTitulo());
		model.put("verComentarios", publicacion.getComentarios());
		model.put("verTaller", publicacion.getTaller());
		model.put("nuevoComentarios", nuevoComentarios);
		model.put("publicacion", publicacion);
		model.put("nombre", publicacion.getDinamizador().getUsuario().getNombre().toString());
		model.put("apellido", publicacion.getDinamizador().getUsuario().getApellido().toString());
		model.put("espai", publicacion.getDinamizador().getTipo().toString());
		return "post";
	}
	
	//Para editar el post
	@Secured({ "ROLE_ADMIN", "ROLE_DINAMIZADOR" })
	@GetMapping("/admin/post/form/{id}")
	public String editarPost(@PathVariable(value = "id") Integer idPublicacion, Map<String, Object> model,
			RedirectAttributes flash, Authentication authentication) {

		Publicacion publicacion = null;

		if (idPublicacion > 0) {
			publicacion = publicacionServices.findOne(idPublicacion);
			if (publicacion == null) {
				flash.addFlashAttribute("error", "El ID que estabas buscando, no existe.");
				return "redirect:post/form";
			}
		} else {
			flash.addFlashAttribute("error", "El ID que estabas buscando, no existe.");
			return "redirect:post/form";
		}
		
		Usuario usuario = serviceUsuario.buscarPorUsername(authentication.getName());
		
		model.put("nombre", usuario.getNombre() + " " + usuario.getApellido());
		model.put("publicacion", publicacion);
		model.put("titol", "Editar: " + publicacion.getTitulo());
		return "post/form";
	}
	
	//Para eliminar el post
	@Secured({ "ROLE_ADMIN", "ROLE_DINAMIZADOR" })
	@RequestMapping(value = "/admin/post/eliminar/{id}")
	public String eliminarPost(@PathVariable(value = "id") Integer idPublicacion, RedirectAttributes flash) {

		if (idPublicacion > 0) {
			Publicacion publicacion = publicacionServices.findOne(idPublicacion);
			
			publicacionServices.delete(idPublicacion);
			
			if (uploadFotoService.delete(publicacion.getFoto())) {
				flash.addFlashAttribute("info", "Foto " + publicacion.getFoto() + " eliminada con exito!");
			}
		}
		return "redirect:/admin/post";
	}
	
	//Para guardar el post
	@Secured({ "ROLE_ADMIN", "ROLE_DINAMIZADOR" })
	@RequestMapping(value = "/admin/post/", method = RequestMethod.POST)
	public String guardarPost(@Valid Publicacion publicacion, BindingResult result, Model model,
			@RequestParam("foto") MultipartFile foto, RedirectAttributes flash, SessionStatus status,
			Authentication authentication) {
		
		//Se generara la informacion y la subida de la imagen correspondiente
		if (!foto.isEmpty()) {

			if (publicacion.getIdPublicacion() != null && publicacion.getIdPublicacion() > 0 && publicacion.getFoto() != null 
					&& publicacion.getFoto().length() > 0) {

				uploadFotoService.delete(publicacion.getFoto());
			}

			String uniqueFilename = null;
			try {
				uniqueFilename = uploadFotoService.copy(foto);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			flash.addFlashAttribute("info", "Has subido correctamente '" + uniqueFilename + "'");

			publicacion.setFoto(uniqueFilename);
		}
		
		//Se pasa la autenticacion por la informacion que vamos a obtener a traves del usuario
		Usuario usuario = serviceUsuario.buscarPorUsername(authentication.getName());
		
		//Se generara el id del dinamizador
		if(publicacion.getDinamizador() == null) {
			publicacion.setDinamizador(usuario.getDinamizador());
		}
		
		//Se creara el dia que publico dicho post
		if(publicacion.getDiaHoraPublicacion() == null) {
			publicacion.setDiaPublicacion(new Date());
		}
		
		String mensajeFlash = (publicacion.getIdPublicacion() != null) ? "Publicació editado con éxito!"
				: "Publicació creado con éxito!";

		publicacionServices.save(publicacion);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:/admin/post";
	}
	
	//Generara un formulario para el post
	@Secured({ "ROLE_ADMIN", "ROLE_DINAMIZADOR" })
	@RequestMapping(value = "/admin/post/form")
	public String crearPost(Map<String, Object> model, Authentication authentication) {
		
		Publicacion publicacion = new Publicacion();
		
		Usuario usuario = serviceUsuario.buscarPorUsername(authentication.getName());

		model.put("nombre", usuario.getNombre() + " " + usuario.getApellido());
		model.put("publicacion", publicacion);
		model.put("titol", "Nou post");
		return "post/form";
	}
	
	//Se generara unas vistas o listas del post
	@Secured({ "ROLE_ADMIN", "ROLE_DINAMIZADOR" })
	@RequestMapping(value = "/admin/post", method = RequestMethod.GET)
	public String listarPost(@RequestParam(name = "page", defaultValue = "0") int page, Model model, Authentication authentication) {
				
		Pageable pageRequest = PageRequest.of(page, 5);

		Page<Publicacion> publicacion = publicacionServices.findAll(pageRequest);

		PageRender<Publicacion> pageRender = new PageRender<Publicacion>("/admin/post", publicacion);
		
		Usuario usuario = serviceUsuario.buscarPorUsername(authentication.getName());
		
		model.addAttribute("nombre", usuario.getNombre() + " " + usuario.getApellido());

		model.addAttribute("publicacion", publicacion);
		model.addAttribute("titol", "Administracio Post");
		model.addAttribute("page", pageRender);
		return "/post/administracioPosts";
	}
	
	//Se invocara las fotos a traves de la raiz de uploads.
	@GetMapping(value = "/uploads/{filename:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String filename) {

		Resource recurso = null;

		try {
			recurso = uploadFotoService.load(filename);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"")
				.body(recurso);
	}
	
}
