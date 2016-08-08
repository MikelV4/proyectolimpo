
package com.proyecto.olimpo.utils;

import com.proyecto.olimpo.commons.StatusPeticion;
import com.proyecto.olimpo.enums.CodigosResponseWS;
import com.proyecto.olimpo.model.commons.ContenedorResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 *
 * @author Infinity-PC
 */
@Component
public class UtileriasHelper {
    
    static final Logger logger = Logger.getLogger(UtileriasHelper.class);
        
    public ContenedorResponse llenarContenedorResponse(
			CodigosResponseWS codigo, boolean isError, String excpecion) {
		ContenedorResponse contenedor = new ContenedorResponse();
		contenedor.setCodigo(codigo.getIdCodigo());

		if (isError) {
			contenedor.setError(true);
			contenedor.setStatus(StatusPeticion.ERROR);
			if (excpecion != null) {
				contenedor.setDescripcion(codigo.getDescripcion()
						+ " Detalle del Error: " + excpecion);
			} else {
				contenedor.setDescripcion(codigo.getDescripcion());
			}

		} else {
			contenedor.setDescripcion(codigo.getDescripcion());
			contenedor.setError(false);
			contenedor.setStatus(StatusPeticion.EXITO);
		}

		return contenedor;
	}  
    
    public ContenedorResponse llenarContenedorResponse(
			CodigosResponseWS codigo, boolean isError, String excpecion,
			Object data) {
		ContenedorResponse contenedor = new ContenedorResponse();
		contenedor.setCodigo(codigo.getIdCodigo());

		if (isError) {
			contenedor.setError(true);
			contenedor.setStatus(StatusPeticion.ERROR);
			if (excpecion != null) {
				contenedor.setDescripcion(codigo.getDescripcion()
						+ " Detalle del Error: " + excpecion);
			} else {
				contenedor.setDescripcion(codigo.getDescripcion());
			}

		} else {
			contenedor.setDescripcion(codigo.getDescripcion());
			contenedor.setError(false);
			contenedor.setStatus(StatusPeticion.EXITO);
		}
		if (data != null) {
			contenedor.setData(data);
		}

		return contenedor;
	}
    
    public String getTraceFromExcepcion(Exception e) {
		StringWriter sWriter = new StringWriter();
		PrintWriter pWriter = new PrintWriter(sWriter);
		e.printStackTrace(pWriter);
		return sWriter.toString();
	}
                
}
