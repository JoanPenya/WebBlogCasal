package com.iesemilidarder.springboot.app.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.iesemilidarder.springboot.app.dao.IUsuarioJovenDao;
import com.iesemilidarder.springboot.app.entity.Comentarios;
import com.iesemilidarder.springboot.app.entity.Publicacion;
import com.iesemilidarder.springboot.app.entity.Taller;
import com.iesemilidarder.springboot.app.entity.UserXTaller;
import com.iesemilidarder.springboot.app.entity.Usuario;
import com.iesemilidarder.springboot.app.entity.UsuarioJoven;
import com.iesemilidarder.springboot.app.service.IPublicacionServices;
import com.iesemilidarder.springboot.app.service.ITallerServices;
import com.iesemilidarder.springboot.app.service.IUploadFileService;
import com.iesemilidarder.springboot.app.service.IUserXTallerServices;
import com.iesemilidarder.springboot.app.service.IUsuarioJovenServices;
import com.iesemilidarder.springboot.app.service.IUsuarioServices;
import com.iesemilidarder.springboot.app.util.paginator.PageRender;

@Controller
@SessionAttributes("taller")
public class TallerController {
	
	//Invocamos las clases services, para que hagan su trabajo:
	
	@Autowired
	private ITallerServices tallerServices;
	
	@Autowired
	private IUserXTallerServices usertallerServices;

	@Autowired
	private IUploadFileService uploadFotoService;

	@Autowired
	private IUsuarioServices serviceUsuario;
	
	@Autowired
	private IUsuarioJovenServices serviceUsuarioJoven;
	
	@Autowired
	private IPublicacionServices servicePublicacion;
	
	// Para guardar el taller
	@Secured({ "ROLE_ADMIN", "ROLE_DINAMIZADOR" })
	@RequestMapping(value = "/admin/tallers/", method = RequestMethod.POST)
	public String guardarTaller(@Valid Taller taller, BindingResult result, Model model, 
			@RequestParam("foto") MultipartFile foto, RedirectAttributes flash, SessionStatus status,
			Authentication authentication) {
		
		//Para guardar las imagenes que vamos a subir
		if (!foto.isEmpty()) {

			if (taller.getPublicacion().getIdPublicacion() != null && taller.getPublicacion().getIdPublicacion() > 0 && taller.getPublicacion().getFoto() != null 
					&& taller.getPublicacion().getFoto().length() > 0) {

				uploadFotoService.delete(taller.getPublicacion().getFoto());
			}

			String uniqueFilename = null;
			try {
				uniqueFilename = uploadFotoService.copy(foto);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			flash.addFlashAttribute("info", "Has subido correctamente '" + uniqueFilename + "'");
			
			
			taller.getPublicacion().setFoto(uniqueFilename);
			
		}
		
		//Para convertir la autenticación del usuario por información
		Usuario usuario = serviceUsuario.buscarPorUsername(authentication.getName());
		
		//Se generara automaticamente la id del dinamizador
		if(taller.getPublicacion().getDinamizador() == null) {
			taller.getPublicacion().setDinamizador(usuario.getDinamizador());
		}
		
		//Se generara el dia de la publicacion
		if (taller.getPublicacion().getDiaHoraPublicacion() == null) {
			taller.getPublicacion().setDiaPublicacion(new Date());
		}
		
		System.out.println(taller.toString());
		tallerServices.save(taller);
		status.setComplete();
		return "redirect:/admin/tallers/";
	}
	
	//Se envia un formulario en blanco en el creacion de talleres
	@Secured({ "ROLE_ADMIN", "ROLE_DINAMIZADOR" })
	@GetMapping("/admin/tallers/form")
	public String crearTaller(Map<String, Object> model, Authentication authentication) {

		Taller taller = new Taller();

		Usuario usuario = serviceUsuario.buscarPorUsername(authentication.getName());

		model.put("nombre", usuario.getNombre() + " " + usuario.getApellido());

		model.put("taller", taller);
		model.put("titol", "Nou Taller");
		return "/tallers/form";
	}
	
	//Para ver el listado de los dinamizadores
	@Secured({ "ROLE_ADMIN", "ROLE_DINAMIZADOR" })
	@RequestMapping(value = "/admin/tallers", method = RequestMethod.GET)
	public String verListado(@RequestParam(name = "page", defaultValue = "0") int page, Model model,
			Authentication authentication) {

		Pageable pageRequest = PageRequest.of(page, 5);

		Page<Taller> taller = tallerServices.findTaller(pageRequest);

		PageRender<Taller> pageRender = new PageRender<Taller>("/admin/tallers", taller);

		Usuario usuario = serviceUsuario.buscarPorUsername(authentication.getName());

		model.addAttribute("nombre", usuario.getNombre() + " " + usuario.getApellido());

		model.addAttribute("titol", "administración de taller");
		model.addAttribute("taller", taller);
		model.addAttribute("page", pageRender);

		return "/tallers/administracioTaller";
	}
	
	//Para eliminar taller
	@Secured({ "ROLE_ADMIN", "ROLE_DINAMIZADOR" })
	@RequestMapping(value = "/admin/tallers/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Integer idTaller, RedirectAttributes flash) {

		if (idTaller > 0) {
			Taller taller = tallerServices.findOne(idTaller);

			if (taller.getPublicacion().getFoto() == null) {

				tallerServices.delete(idTaller);
				flash.addFlashAttribute("success", "Taller eliminado con éxito!");

			} else if (uploadFotoService.delete(taller.getPublicacion().getFoto())) {
				tallerServices.delete(idTaller);
				flash.addFlashAttribute("success", "Taller eliminado con éxito!");
			}

		}
		return "redirect:/admin/tallers";
	}
	
	
	//Para crear un taller
	@Secured({ "ROLE_ADMIN", "ROLE_DINAMIZADOR" })
	@GetMapping("/admin/tallers/form/{id}")
	public String editar(@PathVariable(value = "id") Integer idTaller, Map<String, Object> model,
			RedirectAttributes flash, Authentication authentication) {

		Taller taller = null;

		if (idTaller > 0) {
			taller = tallerServices.findOne(idTaller);
			if (taller == null) {
				flash.addFlashAttribute("error", "El ID que estabas buscando, no existe.");
				return "redirect:tallers/form";
			}
		} else {
			flash.addFlashAttribute("error", "El ID que estabas buscando, no existe.");
			return "redirect:tallers/form";
		}
		
		Usuario usuario = serviceUsuario.buscarPorUsername(authentication.getName());

		model.put("nombre", usuario.getNombre() + " " + usuario.getApellido());

		model.put("taller", taller);
		model.put("titol", "Editar: " + taller.getPublicacion().getTitulo());
		return "tallers/form";
	}
	
	//@GetMapping(value = "/admin/tallers/llista/{id}")
	
	//Para ver el listado de los usuarios apuntados a dicho taller
	@Secured({ "ROLE_ADMIN", "ROLE_DINAMIZADOR" })
	@GetMapping(value = "/talleresApuntados/administracioTallerApuntats/{id}") //Esta raíz, lo tenía que ponerlo por el convertidor PDF
	public String verListado(@PathVariable(value = "id") Integer idTaller, Map<String, Object> model,
			Authentication authentication) {
		Taller taller = null;

		if (idTaller > 0) {
			taller = tallerServices.findOne(idTaller);
		} else {
			return "/tallers/administracioTaller";
		}

		Usuario usuario = serviceUsuario.buscarPorUsername(authentication.getName());

		model.put("nombre", usuario.getNombre() + " " + usuario.getApellido());

		model.put("taller", taller);
		model.put("userXTaller", taller.getUserXTaller());
		model.put("titol", "llista: " + taller.getPublicacion().getTitulo().toString());
		return "talleresApuntados/administracioTallerApuntats";
	}
	
	//Para apuntar a un usuario en un dicho taller
	@Secured({ "ROLE_ADMIN", "ROLE_DINAMIZADOR" })
	@GetMapping(value = "/admin/tallers/llista/apuntar/{id}")
	public String apuntarParticipante(@PathVariable(value = "id") Integer idTaller, Map<String, Object> model,
			Authentication authentication) {
		
		UserXTaller userXTaller = new UserXTaller();
		
		List<UsuarioJoven> seleccion = serviceUsuarioJoven.findAll();
		
		model.put("seleccion", seleccion);
		model.put("userXTaller", userXTaller);
		model.put("titol", "afegir usuari en el taller: ");
		return "talleresApuntados/form";
	}
	
	//Listar la lista de los talleres
	@Secured({ "ROLE_ADMIN", "ROLE_DINAMIZADOR" })
	@PostMapping(value = "/admin/tallers/llista/{id}")
	public String registrarUsuario(@PathVariable(value = "id") Integer idTaller, Map<String, Object> model,
			Authentication authentication,SessionStatus status, @Valid UserXTaller userXTaller) {
		
		Usuario usuario = serviceUsuario.buscarPorUsername(authentication.getName());
		usuario.setUsuarioJoven(usuario.getUsuarioJoven());
		
		Taller taller = tallerServices.findOne(idTaller);
		userXTaller.setTaller(taller);
		
		
		usertallerServices.save(userXTaller);
		status.setComplete();
		return "redirect:/talleresApuntados/administracioTallerApuntats/{id}";
	}

	//Para eliminar taller
	@Secured({ "ROLE_ADMIN", "ROLE_DINAMIZADOR" })
	@RequestMapping(value = "/admin/tallers/llista/eliminar/{id}")
	public String eliminarUsuarioApuntado(@PathVariable(value = "id") Integer idUxT, RedirectAttributes flash) {

		if (idUxT > 0) {
			tallerServices.deleteUXT(idUxT);
		}
		return "redirect:/admin/tallers/";
	}
	
	//para registrar un usuario al apuntar dicho taller.
	@Secured({ "ROLE_USER" })
	@RequestMapping(value="/publicacion/{id}/apuntar", method = RequestMethod.POST)
	public String apuntarTaller(@PathVariable(value = "id") Integer idPublicacion, 
			@Valid UserXTaller userXTaller, BindingResult result, Model model, 
			 RedirectAttributes flash, SessionStatus status, Authentication authentication) {
		
		//Se genera el id del usuario por autenticacion
		Usuario usuario = serviceUsuario.buscarPorUsername(authentication.getName());
		userXTaller.setUsuarioJoven(usuario.getUsuarioJoven());
		
		//Se genera la id del taller a través del id de la publicacion
		Publicacion publicacion = servicePublicacion.findOne(idPublicacion);
		userXTaller.setTaller(publicacion.getTaller());
		
		String mensajeFlash =  "Has apuntat el taller que vols participar. Moltissimes gracies ;)";

		usertallerServices.save(userXTaller);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:/publicacion/{id}";
	}

}
