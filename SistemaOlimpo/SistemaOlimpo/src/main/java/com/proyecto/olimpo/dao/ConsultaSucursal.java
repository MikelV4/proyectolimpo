package com.proyecto.olimpo.dao;

import com.proyecto.olimpo.dao.commons.ConexionBaseDatos;
import com.proyecto.olimpo.utils.UtileriasHelper;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component
public class ConsultaSucursal {
	private final Logger log  = Logger.getLogger(ConsultaSucursal.class);
	
	@Autowired
	private ConexionBaseDatos ConexionBD;
	@Autowired
	private UtileriasHelper utilerias;
	
	
	private HashMap<String,Integer> altaSolicitudTiendaSP(){
		Connection con=null;
		HashMap<String,Integer> respuesta = new HashMap<String,Integer>();
		try{
			con=ConexionBD.getConexionBD();
			
                        //Proceso de Conexion
                        
			if(con!=null){
				ConexionBD.commitConexion(con);
			}else{
                            
				throw new Exception("CONEXION PERDIDA AL MOMENTO DE HACER COMMIT]");	
			}
			respuesta.put("respuesta",0);			
			respuesta.put("salida",0);							
			return respuesta;
		}catch(Exception e){			
			if(con!=null){
				ConexionBD.rollbackConexion(con);
			}
			log.error(utilerias.getTraceFromExcepcion(e));
			return null;
		}
	}
        
}