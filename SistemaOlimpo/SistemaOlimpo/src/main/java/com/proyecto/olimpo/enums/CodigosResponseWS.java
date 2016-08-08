package com.proyecto.olimpo.enums;


public enum CodigosResponseWS {

	/**
	 * Puntos a conciderar para el nombramiento de codigos y tipos en el enum-
	 * 
	 * -Usar como primer palabra para cada tipo de codigo el tema del que se esta tratando
	 * ejemplo:
	 * ERROR_AL_GENERAR_CITA(160L,"Error al guardar la cita")
	 * el tema del que trata este codigo gira entorno de la CITA por tanto quedaria algo parecido a lo siguiente
	 * CITA_ERROR_AL_GUARDAR(160L,"Error al guardar la cita en la BD")
	 * 
	 * -El objetivo de los codigos es identificar el lugar donde ocurre un error o un evento especifico,
	 * para facilitar el mantenimiento del codigo
	 * -Es necesario describir el tipo de codigo de forma que ni pueda ajustar para otros eventos, esceptuando 
	 * los codigos genericos
	 * -El nombre del tipo debe de concordar con la descripcion 
	 * -Si el tipo de codigo ingresado puede ser separado en varios, es necesario dividirlo.
	 * 
	 * ejemplo:
	 * CATEGORIA_INEXISTENTE(16L,"CategoriasModeloConfigurador no disponible"),//---->AMBIGUA, NO DESCRIPTIVA, NO CONCUERDA CON DESCRIPCION
	 * La anterior podria convertirse en las siguientes:
	 * 
	 *  CATEGORIA_OBTENIDA_SATISFACTORIAMENTE(100L,"Categoria recibida"),
		CATEGORIA_INEXISTENTE(101L,"La Categoria no existe"),
		CATEGORIA_ERROR_USUARIO_O_PASSWORD(102L,"Usuario y/o password usados en el Modelo Configurador son incorrectos"),
		CATEGORIA_MODELO_CONFIGURADOR_NO_RESPONDE(103L,"El Servidor Cache del Modelo Configurador no responde"),
		CATEGORIA_SERVICIO_WEB_MODELO_CONFIGURADOR_NO_RESPONDE(104L,"El Servicio Web del Modelo Configurador no responde"), 
	 * 
	 * -si vas a ingresar nuevos tipos en el enum es necesario seguir el ultima bloque numerico el cual va de 20 en 20
	 * -iniciar a usar codigos a partir del codigo en el que inicia el bloque
	 * ejemplo:
	 * un bloque que va del 700 al 720 empezar a usar el numero 700 no el 701
	 * -dentro de lo que sea posible usar la palabra GUARDAR para referirce a la capa del DAO
	 * 
	 */

		//****************************COMUNES***********************************
		
		PROCESO_TERMINDO_CORRECTAMENTE(2L,"Proceso terminado correctamente"),
		GUARDAR_SOLICITUD_EN_BD(3l,"Error al guardar solicitud en BD"),
		ERROR_INESPERADO(5L,"Error inesperado"),
		ERROR_PROXY(7L, "Error en proxy"),
		ERROR_TIPO_DATO(8L, "Error en tipo de dato"),//CAMBIARIA A---> ERROR_PARAMETROS_INCORRECTOS  (USADOS SOLO EN LOS CONTROLLERS)
		TRANSACCION_EXITOSA(9L,"La solicitud ha sido guardada correctamente."),	
		XML_NO_VALIDO(10L,"El archivo XML no es valido."),
		JSON_NO_VALIDO(11L,"El json no es valido"),
		ERROR_AL_CONSULTAR_SP_DINAMICO(12L,"Falla inesperado"),
		DIRECCIONIP_PETICION_INVALIDA(13l,"La direccion ip del servidor que realizo la peticion no es valida."),		
		TERMINAL_NO_VALIDA(14L,"La terminal no es vlida"),
		
		DATOS_SERVICIO_INCOMPLETOS(15L,"Los Datos Para La Consulta del Servicio estan Incompletos.");
		
		private CodigosResponseWS(Long idCodigo, String descripcion) {
			this.idCodigo = idCodigo;
			this.descripcion = descripcion;
		}

		private  Long idCodigo;
		
		private  String descripcion;

		public Long getIdCodigo() {
			return idCodigo;
		}

		public void setIdCodigo(Long idCodigo) {
			this.idCodigo = idCodigo;
		}

		public String getDescripcion() {
			return descripcion;
		}

		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}
		
	}