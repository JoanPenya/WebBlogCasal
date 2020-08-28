package com.iesemilidarder.springboot.app.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class LoginController {
	
	//Para generar avisos de login
	@GetMapping("/login")
	public String login(@RequestParam(value="error", required=false) String error, 
						@RequestParam(value="logout", required=false) String logout,
						Model model, Principal principal, RedirectAttributes flash) {
		
		if(principal != null) {
			
			flash.addFlashAttribute("info", "Ya has iniciado sesión.");
			return "redirect:/";
		}
		if(error != null) {
			flash.addFlashAttribute("error", "Error login: Usuario o contraseña incorrecta, vuelve a intentarlo.");
			
		}
		
		if(logout != null) {
			flash.addFlashAttribute("success", "Ha cerrado sesión con éxito");
		}
		
		return "login";
	}
	
}
