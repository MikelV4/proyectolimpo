/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proyecto.olimpo.controller;

import com.proyecto.olimpo.commons.RutasWS;
import com.proyecto.olimpo.enums.CodigosResponseWS;
import com.proyecto.olimpo.model.commons.ContenedorResponse;
import com.proyecto.olimpo.utils.UtileriasHelper;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    private UtileriasHelper utileriasHelper;
    
	static final Logger log = Logger.getLogger(TestController.class);
        
        

	@RequestMapping(value=RutasWS.CTR_SERVICIO_TEST , method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody ContenedorResponse<?>obtenerCalificacionBuro(
			HttpServletRequest request){
		ContenedorResponse<String> contenedor= new ContenedorResponse<String>();
			try{
//				log.info("[cONSULTADO METODO TEST]");
				contenedor= utileriasHelper.llenarContenedorResponse(CodigosResponseWS.PROCESO_TERMINDO_CORRECTAMENTE, false, null,"LOS SERVICIOS ESTAN OK");
				
			}catch(Exception e){
				log.error(contenedor.getDescripcion()
						+" Detalle Error: "+utileriasHelper.getTraceFromExcepcion(e));
				contenedor.setDescripcion(contenedor.getDescripcion()
						+" Detalle Error: "+utileriasHelper.getTraceFromExcepcion(e));
			}
		
		return contenedor; 
	}	
}
