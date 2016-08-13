package com.proyecto.olimpo.commons;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class PropertiesWS {
	
	//OBTENIENDO DATOS DESDE PROPERTIES
	
		//Modo Desarrollo
	
		@Value("${modo.desarrollo}") public boolean MODO_DESARROLLO;
		
		//DATOS DE CONFIGURACION DEL JDBC
		//Modo Desarrollo - JDBC
		@Value("${JDBC_URL}") public String JDBC_URL;
		@Value("${JDBC_DATABASE}") public String JDBC_DATABASE;
		@Value("${JDBC_USERNAME}") public String JDBC_USERNAME;
		@Value("${JDBC_PASSWORD}") public String JDBC_PASSWORD;

		
		

}
