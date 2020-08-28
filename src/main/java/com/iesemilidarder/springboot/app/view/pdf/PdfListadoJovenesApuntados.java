package com.iesemilidarder.springboot.app.view.pdf;

import java.awt.Color;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.iesemilidarder.springboot.app.entity.Taller;
import com.iesemilidarder.springboot.app.entity.UserXTaller;
import com.lowagie.text.Document;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Component("talleresApuntados/administracioTallerApuntats")
public class PdfListadoJovenesApuntados extends AbstractPdfView{

	//Se genera una función para convertir en una página en un documento PDF
	
	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//Se recoge a través del TallerController, de la función: verListado
		Taller taller = (Taller) model.get("taller");
		
		//Se crea las celdas
		PdfPCell cell = null;
		
		//se crea una tabla. 
		PdfPTable titulo = new PdfPTable(1);
		
		//Podemos personalizar la celda
		cell = new PdfPCell(new Phrase(taller.getPublicacion().getTitulo()));
		cell.setBackgroundColor(new Color(184, 218, 255));
		cell.setPadding(8f);
		titulo.addCell(cell);
		
		//Se crea 3 columnas
		PdfPTable tabla = new PdfPTable(3);
		tabla.addCell("Nom i llinatge");
		tabla.addCell("Genere");
		tabla.addCell("Asistencia (Firma)");
		
		//Se generara la información que tienes guardado en la BD
		for(UserXTaller userXTaller: taller.getUserXTaller()) {
			tabla.addCell(userXTaller.getUsuarioJoven().getUsuario().getNombre() + userXTaller.getUsuarioJoven().getUsuario().getApellido());
			tabla.addCell(userXTaller.getUsuarioJoven().getGenero());
			tabla.addCell(" ");
		}
		
		//Se añade en el documento
		document.add(titulo);
		document.add(tabla);
		
	}

}
