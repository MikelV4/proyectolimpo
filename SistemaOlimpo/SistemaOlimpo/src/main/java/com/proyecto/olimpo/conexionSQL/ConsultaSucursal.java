package com.canales.terceros.solicitud.prestamos.helper.spTiendas;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.canales.terceros.solicitud.prestamos.constants.PropertiesWS;
import com.canales.terceros.solicitud.prestamos.dao.BuroDao;
import com.canales.terceros.solicitud.prestamos.dao.GuardarIdSolicitudTiendaDao;
import com.canales.terceros.solicitud.prestamos.dao.SolicitudesEnProcesoDao;
import com.canales.terceros.solicitud.prestamos.enums.SeccionSolicitud;
import com.canales.terceros.solicitud.prestamos.enums.StatusSolicitud;
import com.canales.terceros.solicitud.prestamos.enums.TipoPersona;
import com.canales.terceros.solicitud.prestamos.helper.AltaCUAvalHelper;
import com.canales.terceros.solicitud.prestamos.helper.ConsultarHomonimosCUHelper;
import com.canales.terceros.solicitud.prestamos.helper.GenerarClienteTiendaDigitalizacion;
import com.canales.terceros.solicitud.prestamos.helper.UtileriasHelper;
import com.canales.terceros.solicitud.prestamos.model.ContenedorResponse;
import com.canales.terceros.solicitud.prestamos.model.HomonimosCU;
import com.canales.terceros.solicitud.prestamos.model.Huellas;
import com.canales.terceros.solicitud.prestamos.model.Solicitud;
import com.canales.terceros.solicitud.prestamos.model.buro.DetalleConsultaBuroYScore;


@Component
public class ConsultaSucursal {
	private final Logger log  = Logger.getLogger(ConsultaSucursal.class);
	
	@Autowired
	private ConexionSucursal conexionservice;
	@Autowired
	private AltaCUAvalHelper altaCUAvalHelper;
	
	@Autowired
	private UtileriasHelper utilerias;	
	@Autowired
	private ConsultarHomonimosCUHelper consultarHomonimosCUHelper;	
	@Autowired
	private GenerarClienteTiendaDigitalizacion generarClienteTiendaDigitalizacion;	
	@Autowired
	private BuroDao buroDao;
	
	@Autowired
	private SolicitudesEnProcesoDao solicitudesEnProcesoDao;
	@Autowired
	private GuardarIdSolicitudTiendaDao guardarIdSolicitudTiendaDao;
	@Autowired
	private PropertiesWS propertiesWS;
	
	
	////////////////////////////////////////////////////////////////////////////////////
    //////// 			METODO MAESTRO PARA ALTA CLIENTE UNICO
    ////////////////////////////////////////////////////////////////////////////////////
	
	//Enviar Solicitud de Base de Datos
	public HashMap<String, Object> maestroAltaClienteUnico(int sucursal, Solicitud solicitud, Huellas huellas, int idTipoPersona){
		HashMap<String, Object> mapa = new HashMap  <String, Object>();
		int solicitudActual = propertiesWS.MODO_DESARROLLO.equals("true")?673:solicitud.getIdSucursal();
		
		int estatusSolicitud = (solicitud.getIdCondicion()!=null&&!solicitud.getIdCondicion().isEmpty())?Integer.parseInt(solicitud.getIdCondicion()):StatusSolicitud.PROCESO.getIdStatus();
		
		if(estatusSolicitud != StatusSolicitud.RECHAZADA.getIdStatus()){
			if(idTipoPersona == TipoPersona.CLIENTE.getIdTipoPersona() && 
					solicitud.getCotizacion().getClientes().get(0).getPorcentaje() == 100 &&
					solicitud.getCotizacion().getClientes().get(0).getDomicilios().get(0).getPorcentaje() == 100 ){
				
				if(solicitud.getFolioCallCenter().isEmpty()){
					log.debug("Entrando a flujo de Alta de Cliente Unico");
					
					if(solicitud.getCotizacion().getClientes().get(0).getClienteUnico().isEmpty() && huellas!=null){
						//Nuevo Cliente Unico 
						mapa = generaAltaCU(sucursal, solicitud, huellas, idTipoPersona);
						
					}else{
						//Cliente Unico Foraneo
						String datosCU[] = solicitud.getCotizacion().getClientes().get(0).getClienteUnico().split("-");
						if(solicitud.getClienteForaneoGuardado()==0 && solicitudActual!=Integer.parseInt(datosCU[2])){
							mapa = generarAltaCUForaneo(sucursal, solicitud, idTipoPersona);		
						}
					}
					
				} else {
					
					if(solicitud.getRespuestaCallCenter()==0){
						log.debug("[NO SE PUEDE CONTINUAR CON ALTA DE CLIENTE UNICO, ESPERANDO RESPUESTA DE CALL CENTER]");				
						
					}else if(solicitud.getRespuestaCallCenter()==1){
						if(!solicitud.getCotizacion().getClientes().get(0).getClienteUnico().isEmpty()){
							String datosCU[] = solicitud.getCotizacion().getClientes().get(0).getClienteUnico().split("-");
							if(solicitud.getClienteForaneoGuardado()==0 && solicitudActual!=Integer.parseInt(datosCU[2])){
								log.debug("[Entrando a flujo de Alta de Cliente Unico FORANEO con Folio Call Center]");
								mapa = generarAltaCUForaneo(sucursal, solicitud, idTipoPersona);
							}
						}
						
					}else if(solicitud.getRespuestaCallCenter()==2){
						
						if(solicitud.getCotizacion().getClientes().get(0).getClienteUnico().isEmpty() && huellas!=null){
							//Cliente Unico Foraneo
							log.debug("[Entrando a flujo de Alta de Cliente Unico con Folio Call Center]");
							mapa = generaAltaCU(sucursal, solicitud, huellas, idTipoPersona);											
						}
						
					}else{
						log.debug("[RESPUESTA CALL CENTER NO CONTROLADA]");
						
					}
				}
			}else if (idTipoPersona==TipoPersona.AVAL.getIdTipoPersona() && 
					solicitud.getAvales().get(0).getPorcentajeBasico() == 100 &&
					solicitud.getAvales().get(0).getDatosHogar().getPorcentaje() == 100 ){
				
				if(solicitud.getAvales().get(0).getClienteUnico().isEmpty() && huellas!=null){
					mapa = generaAltaCU(sucursal, solicitud, huellas, idTipoPersona);
				}else{
					String datosCU[] = solicitud.getAvales().get(0).getClienteUnico().split("-");
					if(solicitud.getClienteForaneoGuardado()==0 && solicitudActual!=Integer.parseInt(datosCU[2])){
						mapa = generarAltaCUForaneo(sucursal, solicitud, idTipoPersona);					
					}
				}
			}
		}
		//Flujo Normal
		if(mapa==null||mapa.isEmpty()){
			return null;
		}
		return mapa;
	}

	
	
	//Enviar Solicitud de Base de Datos
	public HashMap<String, Object> altaSolicitudTienda(int sucursal, Solicitud solicitud){
		HashMap<String, Object> mapaTienda = new HashMap<String, Object>(); 
		mapaTienda.put("status", 0);
		try{
			if(!solicitud.getCotizacion().getClientes().get(0).getClienteUnico().isEmpty() &&
				solicitud.getExisteCita() !=0 && solicitud.getIdSolicitudTienda()==0){
				log.debug("[ALTA DE SOLICITUD EN TIENDA ]"+solicitud.getIdSolicitudTienda());			
				if(solicitud.getIdSolicitudTienda()!=0){
					log.debug("[EXISTE SOLICITUD EN TIENDA]");		
					mapaTienda.put("solicitud", solicitud);
				}else{
					log.debug("DANDO DE ALTA SOLICITUD EN TIENDA");			
					HashMap<String,Integer> idsSolicitudTienda = altaSolicitudTiendaSP(sucursal, solicitud);
					if(idsSolicitudTienda!=null){
						solicitud.setIdSolicitudTienda(idsSolicitudTienda.get("idSolicitudTienda"));
						solicitud.setIdSolicitudTiendaCredInm(idsSolicitudTienda.get("idSolicitudTiendaCredInm"));
					}
					if(solicitud.getIdSolicitudTienda()!=0){
						if(guardarSolicitudBD(solicitud)){			
							log.debug("{SOLICITUD GUARDADA CORRECTAMENTE DESPUES DE BAJAR A TIENDA]");		
							mapaTienda.put("solicitud", solicitud);
						}else{
							mapaTienda.put("status", 1);
							log.error("[ERROR AL GUARDAR SOLICITUD DESPUES DE BAJAR A TIENDA]");	
						}
					}						
				}
			}else{
				mapaTienda.put("solicitud", solicitud);
				return mapaTienda;
			}
		}catch(Exception e){
			log.error("[Error generando Alta de Solicitud en Tienda]"+utilerias.getTraceFromExcepcion(e));	
			mapaTienda.put("status", 1);
		}
		mapaTienda.put("solicitud", solicitud);
		return mapaTienda;
	}
	
	private boolean guardarSolicitudBD(Solicitud solicitud){
		HashMap<String, String> resultados = null;		
		String xmlSolicitud = utilerias.convertObjectToXML(solicitud, Solicitud.class);
		resultados = solicitudesEnProcesoDao.actualizarSolicitud(xmlSolicitud, 
				SeccionSolicitud.COTIZACION.getCadenaSeccion(), null);
		if(resultados!=null && Integer.parseInt(resultados.get("estatus"))!=1){
//			xmlSolicitud = resultados.get("xml");
//			solicitud = (Solicitud) utilerias.convertXMLtoObject(xmlSolicitud, Solicitud.class);
			return true;
		}
		return false;
	}
	
	////////////////////////////////////////////////////////////////////////////////////
    //////// 			VINCULAR SOLICITUD TIENDA
    ////////////////////////////////////////////////////////////////////////////////////
	
	private boolean vincularIdSolicitudTienda(String idSolicitud, int idSolicitudTienda, int idSolicitudTiendaCredInm){
		HashMap<String, String> resultados = null;
		int idVincular =  idSolicitudTienda;		
		if(idSolicitudTiendaCredInm>0){
			idVincular =  idSolicitudTiendaCredInm;		
		}
		resultados = guardarIdSolicitudTiendaDao.guardarIdSolicitudTienda(idSolicitud, idVincular);
		if(resultados!=null && (Integer.parseInt(resultados.get("estatus"))!=1)){
				return true;
		}
		return false;
	}
	
	public boolean relacionarIdSolicitudTienda(int sucursal, Solicitud solicitud){
		Connection con=null;
		try{
			con=conexionservice.getConexionSucursal(sucursal);
			consumoDeSPTienda.consumirRelSolOmnicanal(solicitud, con);		
			if(con!=null){
				conexionservice.commitConexion(con);
			}else{		
				return false;
			}
			return 	true;	
		}catch(Exception e){		
			if(con!=null){
				conexionservice.rollbackConexion(con);
			}
			log.error("Error al guardar ID Solicitud en BD Tienda"+utilerias.getTraceFromExcepcion(e));
			return false;
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////
    //////// 			ALTA SOLICITUD TIENDA
    ////////////////////////////////////////////////////////////////////////////////////
	
	private HashMap<String,Integer> altaSolicitudTiendaSP(int sucursal, Solicitud solicitud){
		Connection con=null;
		DetalleConsultaBuroYScore detalleBuroYScore = null; 
		HashMap<String,Integer> mapPACRLISOLICITUDOMNICANAL = new HashMap<String,Integer>(); 
		HashMap<String,Integer> idsSolicitudTienda = new HashMap<String,Integer>();
		String idSolicitudOMNI = "";
		int idSolicitudCredInm = 0;
	
//		log.debug("[ALTA DE SOLICITUD EN TIENDA]:"+utilerias.convertObjectToJson(solicitud));
		
		try{
			con=conexionservice.getConexionSucursal(sucursal);
			detalleBuroYScore = obtenerDatosBuro(solicitud.getIdSolicitud());
			mapPACRLISOLICITUDOMNICANAL = (HashMap<String,Integer>) consumoDeSPTienda.consumirPACRLISOLICITUDOMNICANAL(solicitud, con, detalleBuroYScore, null);
			idSolicitudOMNI = String.valueOf(mapPACRLISOLICITUDOMNICANAL.get("solicitudId"));			
			consumoTipoSolicitudAUT.execTipoSolYAut(idSolicitudOMNI, solicitud, con);
			
			consumoCapMaximaOMNICanal.execCapMaximaOMNICanal(idSolicitudOMNI, solicitud, detalleBuroYScore, con);
			
			consumoLigasOmniCanal.consumirPACRLILIGASOMNICANAL(solicitud, mapPACRLISOLICITUDOMNICANAL, detalleBuroYScore, con);
			//Solo se ejecuta si es Credito Inmediato
			idSolicitudCredInm = consumoRevCredInmediato.execRevCredInmediato(idSolicitudOMNI, solicitud, con);
			consumoDeSPTienda.consumirPACRLIREFERENCIASOMNICANAL(solicitud, mapPACRLISOLICITUDOMNICANAL, con);	
			consumoDeSPTienda.consumirPACRLIAvalesOMNICANAL(solicitud, mapPACRLISOLICITUDOMNICANAL, con);	
//			consumoDeSPTienda.consumirPACRLLIRECOMENDACIONES(solicitud, idSolicitudOMNI, con, detalleBuroYScore);	
			consumoMatricesScoreOMNI.execMatricesScoreOMNI(idSolicitudOMNI, solicitud, detalleBuroYScore, con);
			
			consumoClienteOMNICanal.execClienteOMNICanal(idSolicitudOMNI, solicitud, detalleBuroYScore, con);
//			consumoDeSPTienda.consumirSPCREDInsInvestigacionCRDX(solicitud, con, idSolicitudOMNI);						
			if(idSolicitudCredInm>0){
//				solicitud.setIdSolicitudTienda(idSolicitudCredInm);
				consumoDeSPTienda.consumirSPCREDInsInvestigacionCRDInmediato(solicitud, con, String.valueOf(idSolicitudCredInm));	
			}else{
				consumoDeSPTienda.consumirSPCREDInsInvestigacionCRDX(solicitud, con, idSolicitudOMNI);						
			}
			
			//Se agrega el Vinculo entre IdSolicitud y IdSolicitudTienda
			solicitud.setIdSolicitudTienda(Integer.parseInt(idSolicitudOMNI));
			solicitud.setIdSolicitudTiendaCredInm(idSolicitudCredInm);

			//Se actualiza Capacidad de Pago
			consumoDeSPTienda.consumirActualizarCapacidadPago(solicitud, detalleBuroYScore, con);			
			
			//Se agrega el Vinculo entre IdSolicitud y IdSolicitudTienda
			if(vincularIdSolicitudTienda(solicitud.getIdSolicitud(), solicitud.getIdSolicitudTienda(), solicitud.getIdSolicitudTiendaCredInm())){
				log.debug("VICULAR SOLICITUD OK");		
			}else{
				throw new Exception("[ERROR AL VINCULAR SOLICITUD Y SOLICITUD TIENDA]");	
			}
			consumoDeSPTienda.consumirRelSolOmnicanal(solicitud, con);			
			//////////

			if(con!=null){
				conexionservice.commitConexion(con);
			}else{
				solicitud.setIdSolicitudTienda(0);			
				solicitud.setIdSolicitudTiendaCredInm(0);			
				throw new Exception("CONEXION PERDIDA AL MOMENTO DE HACER COMMIT]");	
			}
			idsSolicitudTienda.put("idSolicitudTienda",mapPACRLISOLICITUDOMNICANAL.get("solicitudId"));			
			idsSolicitudTienda.put("idSolicitudTiendaCredInm",idSolicitudCredInm);							
			return idsSolicitudTienda;
		}catch(Exception e){
			solicitud.setIdSolicitudTienda(0);			
			if(con!=null){
				conexionservice.rollbackConexion(con);
			}
			log.error(utilerias.getTraceFromExcepcion(e)+"\n\n[Solicitud]: "+utilerias.convertObjectToJson(solicitud));
			return null;
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////
    //////// 			GENERAR PEDIDO
    ////////////////////////////////////////////////////////////////////////////////////

	public Solicitud generarCreditoPP(int sucursal, Solicitud solicitud){
		Connection con=null;
		HashMap<String,Integer> mapa = null;
		int resulPedido = 0 ;
		log.debug("[GENERAR CREDITO Y PEDIDO]:"+solicitud.getIdSeguimiento());
		try{
			con=conexionservice.getConexionSucursal(sucursal);
			mapa = consumoDeSPTienda.consumirPACRLLIGeneraCreditoPP(solicitud, con);					
			log.debug("[PEDIDOS GENERADOS]:\n"+mapa);
			//Se requiere que la conexion se mantega abierta para tener control sobre la transaccion a nivel aplicacion
			solicitud.setPedido(mapa.get("NoPedido"));	
			solicitud.setPedidoSEG(mapa.get("NoPedidoSEG"));	
//			resulPedido = mapa.get("NoPedido");
			if(con!=null){
				conexionservice.commitConexion(con);
			}else{
				solicitud.setPedido(0);			
				solicitud.setPedidoSEG(0);			
//				return 0;
			}
		}catch(Exception e){
			solicitud.setPedido(0);			
			solicitud.setPedidoSEG(0);			
			if(con!=null){
				conexionservice.rollbackConexion(con);
			}
			log.error("ERROR AL GENERAR PEDIDO: "+utilerias.getTraceFromExcepcion(e));
		}
		return solicitud;
	}
	
	////////////////////////////////////////////////////////////////////////////////////
    //////// 			ALTA DE CLIENTE UNICO EN TIENDA FORANEO
    ////////////////////////////////////////////////////////////////////////////////////

	private HashMap<String,Object> generarAltaCUForaneo(int sucursal, Solicitud solicitud, int idTipoPersona){
		Connection con=null;
		HashMap<String,Object> resultado = new HashMap<String,Object>();
		
		String clienteUnico = "";
		String clienteTiendaBackup = "";
		
		if(idTipoPersona == TipoPersona.CLIENTE.getIdTipoPersona()){
			clienteUnico = solicitud.getCotizacion().getClientes().get(0).getClienteUnico();	
			clienteTiendaBackup = solicitud.getCotizacion().getClientes().get(0).getClienteTienda();		
		}else if(idTipoPersona == TipoPersona.AVAL.getIdTipoPersona()){
			clienteUnico = solicitud.getAvales().get(0).getClienteUnico();
			clienteTiendaBackup = solicitud.getAvales().get(0).getClienteTienda();
		}
				String CU[] = clienteUnico.split("-");		
			try{
				log.debug("[ALTA CLIENTE UNICO FORANEO]: "+solicitud.getCotizacion().getClientes().get(0).getClienteUnico());
				con=conexionservice.getConexionSucursal(sucursal);
				
				HashMap<String,String> resCT =  consumoAltaClienteTiendaOMNI.execGeneraClienteTiendaForaneo(solicitud, con, idTipoPersona);				
				
				if(resCT.isEmpty()){
					throw new Exception("[Error obteniendo Cliente Tienda]");
				}
				
				String clienteTienda = resCT.get("idNegocio").trim() + "-" +
										resCT.get("noTienda").trim() + "-" +
										resCT.get("idCliente").trim() + "-" +
										resCT.get("digitoVer").trim();
				
				if(idTipoPersona == TipoPersona.CLIENTE.getIdTipoPersona()){
					solicitud.getCotizacion().getClientes().get(0).setClienteTienda(clienteTienda);		
				}else if(idTipoPersona == TipoPersona.AVAL.getIdTipoPersona()){
					solicitud.getAvales().get(0).setClienteTienda(clienteTienda);
				}
				
				
				HomonimosCU homonimosCU = obtenerDatosHomonimos(clienteUnico, clienteTiendaBackup);
				if(homonimosCU==null){
//					throw new Exception("[No se encontraron datos de los homonimos]");
				}
				
				
				HashMap<String,String> resCU = consumoAltaCUForaneo.execAltaCUForaneo(solicitud, homonimosCU, con, idTipoPersona);		
				
//				String xmlSolicitud = utilerias.convertObjectToXML(solicitud, Solicitud.class);
//				int seccion = (idTipoPersona==TipoPersona.CLIENTE.getIdTipoPersona())? 1 : 5;
//				HashMap<String, String> resGuardar = solicitudesEnProcesoDao.actualizarSolicitud(xmlSolicitud, seccion, null);
//				if(resGuardar.get("estatus").equals(<"1")){
//					throw new Exception("[Error al Guardar Solicitud para Cliente Unico Foraneo]");				
//				}
//				solicitud = (Solicitud) utilerias.convertXMLtoObject(resGuardar.get("xml"), Solicitud.class);
				
				if(con!=null){
						conexionservice.commitConexion(con);
						String biometrico = resCU.get("biometrico");

						if(idTipoPersona == TipoPersona.CLIENTE.getIdTipoPersona()){
							solicitud.setClienteForaneoGuardado(1);	
							solicitud.getCotizacion().getClientes().get(0).setClienteTienda(clienteTienda);		
						}else if(idTipoPersona == TipoPersona.AVAL.getIdTipoPersona()){
							solicitud.getAvales().get(0).setClienteForaneoGuardado(1);
							solicitud.getAvales().get(0).setClienteTienda(clienteTienda);
						}
						
						generarClienteTiendaDigitalizacion.generarClienteTinedaDigitalizacion(solicitud, clienteTiendaBackup);

						resultado.put("solicitud", solicitud);
						resultado.put("biometrico", biometrico);
			
						return resultado;
				}else{
					solicitud.getCotizacion().getClientes().get(0).setClienteTienda(clienteTiendaBackup);
					throw new Exception("[Conexion Perdida Con la Sucursal "+solicitud.getIdSucursal()+"]");				
				}
				
			}catch(Exception e){		
				if(con!=null){
					conexionservice.rollbackConexion(con);
				}
				
				log.error("[No se pudo realizar el Alta de CU Foraneo]: "+utilerias.getTraceFromExcepcion(e)+"\n\n[Solicitud]: "+utilerias.convertObjectToJson(solicitud));
				
				if(idTipoPersona == TipoPersona.CLIENTE.getIdTipoPersona()){
					solicitud.setClienteForaneoGuardado(0);	
					solicitud.getCotizacion().getClientes().get(0).setClienteTienda(clienteTiendaBackup);		
				}else if(idTipoPersona == TipoPersona.AVAL.getIdTipoPersona()){
					solicitud.getAvales().get(0).setClienteForaneoGuardado(0);
					solicitud.getAvales().get(0).setClienteTienda(clienteTiendaBackup);
				}
				
				return null;
			}
	}
	
	

	private HashMap<String,Object> generaAltaCU(int sucursal, Solicitud solicitud, Huellas huellas, int idTipoPersona){
		Connection con=null;
		HashMap<String,Object> resultado = new HashMap<String,Object>();
		try{
			con=conexionservice.getConexionSucursal(sucursal);
			
			HashMap<String,String> respCU = null;
			
			if(idTipoPersona == TipoPersona.CLIENTE.getIdTipoPersona()){
				respCU = consumoGeneraAltaCU.execGeneraAltaCU(solicitud, huellas, con, idTipoPersona);				
			}else if(idTipoPersona == TipoPersona.AVAL.getIdTipoPersona()){
				respCU = consumoGeneraAltaCU.execGeneraAltaCU(solicitud, huellas, con, idTipoPersona);
			}
			
			if(respCU==null){
				throw new Exception("[Error al Generar Cliente Unico]");
			}else{
				if(con!=null){
					conexionservice.commitConexion(con);
					String clienteUnico = respCU.get("paisCU").concat("-").concat(respCU.get("canalCU")).concat("-").concat(respCU.get("sucursalCU")).concat("-").concat(respCU.get("folioCU"));
					String clienteTienda = respCU.get("negocioCT").concat("-").concat(respCU.get("tiendaCT")).concat("-").concat(respCU.get("clienteIdCT")).concat("-").concat(respCU.get("digitoVerCT"));
					
					if(idTipoPersona == TipoPersona.CLIENTE.getIdTipoPersona()){
						solicitud.setClienteForaneoGuardado(1);
						solicitud.getCotizacion().getClientes().get(0).setClienteUnico(clienteUnico);
						solicitud.getCotizacion().getClientes().get(0).setClienteTienda(clienteTienda);
					}else if(idTipoPersona == TipoPersona.AVAL.getIdTipoPersona()){
						solicitud.getAvales().get(0).setClienteForaneoGuardado(1);
						solicitud.getAvales().get(0).setClienteUnico(clienteUnico);
						solicitud.getAvales().get(0).setClienteTienda(clienteTienda);				
					}
					
					String biometrico = respCU.get("biometrico");
					resultado.put("solicitud", solicitud);
					resultado.put("biometrico", biometrico);
					return resultado;
				}else{
					throw new Exception("[Conexion Perdida con la Sucursal: "+solicitud.getIdSolicitud()+"]");
				}
			}
		}catch(Exception e){
			if(con!=null){
				conexionservice.rollbackConexion(con);
			}
			log.error(utilerias.getTraceFromExcepcion(e)+"\n\n[Solicitud]: "+utilerias.convertObjectToJson(solicitud));
			if(idTipoPersona == TipoPersona.CLIENTE.getIdTipoPersona()){
				solicitud.setClienteForaneoGuardado(0);
				solicitud.getCotizacion().getClientes().get(0).setClienteUnico("");
				solicitud.getCotizacion().getClientes().get(0).setClienteTienda("");
			}else if(idTipoPersona == TipoPersona.AVAL.getIdTipoPersona()){
				solicitud.getAvales().get(0).setClienteForaneoGuardado(0);
				solicitud.getAvales().get(0).setClienteUnico("");
				solicitud.getAvales().get(0).setClienteTienda("");				
			}
			return null;
		}
	}
		
	////////////////////////////////////////////////////////////////////////////////////
    //////// 			ACTUALIZAR DATOS DOMICILIO EN TIENDA
    ////////////////////////////////////////////////////////////////////////////////////
	
	
	public boolean actualizarDatosDomicilioTienda(Solicitud solicitud){
		Connection con=null;
		DetalleConsultaBuroYScore detalleBuroYScore = null; 
		HashMap<String,Integer> mapPACRLISOLICITUDOMNICANAL = new HashMap<String,Integer>(); 
		try{
			con=conexionservice.getConexionSucursal(solicitud.getIdSucursal());
			detalleBuroYScore = obtenerDatosBuro(solicitud.getIdSolicitud());
			mapPACRLISOLICITUDOMNICANAL = (HashMap<String,Integer>) consumoDeSPTienda.consumirPACRLISOLICITUDOMNICANAL(solicitud, con, detalleBuroYScore, String.valueOf(solicitud.getIdSolicitudTienda()));
//			if(solicitud.getIdSolicitudTiendaCredInm()>0){
//				consumoDeSPTienda.consumirSPCREDInsInvestigacionCRDInmediato(solicitud, con, String.valueOf(solicitud.getCreditoInmediato()));	
//			}else{
//				consumoDeSPTienda.consumirSPCREDInsInvestigacionCRDX(solicitud, con, String.valueOf(solicitud.getIdSolicitudTienda()));						
//			}	
		
			if(con!=null){
				conexionservice.commitConexion(con);
			}else{		
				return false;
			}
			return 	true;	
		}catch(Exception e){		
			if(con!=null){
				conexionservice.rollbackConexion(con);
			}
			log.error("Error al actualizar Datos Domicilio en Tienda: "+utilerias.getTraceFromExcepcion(e));
			return false;
		}
	}
	////////////////////////////////////////////////////////////////////////////////////
    //////// 			ACTUALIZAR STATUS SOLICITUD MAL ZONIFICADA
    ////////////////////////////////////////////////////////////////////////////////////
	
	
	public boolean actualizarStatusMalZonificada(Solicitud solicitud){
		Connection con=null;
		DetalleConsultaBuroYScore detalleBuroYScore = null; 
		HashMap<String,Integer> mapPACRLISOLICITUDOMNICANAL = new HashMap<String,Integer>(); 
		try{
			con=conexionservice.getConexionSucursal(solicitud.getIdSucursal());
			
			consumoDeSPTienda.consumirSPCREDInsInvestigacionMalZonificada(solicitud, con, String.valueOf(solicitud.getIdSolicitudTienda()));
		
			if(con!=null){
				conexionservice.commitConexion(con);
			}else{		
				return false;
			}
			return 	true;	
		}catch(Exception e){		
			if(con!=null){
				conexionservice.rollbackConexion(con);
			}
			log.error("Error al actualizar Datos Domicilio en Tienda: "+utilerias.getTraceFromExcepcion(e));
			return false;
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////
    //////// 			Obtencion de DATOS HOMONIMOS
    ////////////////////////////////////////////////////////////////////////////////////
	
	
	private HomonimosCU obtenerDatosHomonimos(String clienteUnico, String clienteTienda){
		log.debug("[Obteniendo datos Homonimos]");
		log.debug("ClienteUnico: "+clienteUnico);
		log.debug("ClienteTienda: "+clienteTienda);
		ArrayList<String> listaCU = new ArrayList<String>();
		listaCU.add(clienteUnico);
		
		String CU[] = clienteUnico.split("-");
		String CT[] = clienteTienda.split("-");
		
		ContenedorResponse<ArrayList<HomonimosCU>> contenedor = (ContenedorResponse<ArrayList<HomonimosCU>>) consultarHomonimosCUHelper.obtenerDatosHomonimos(listaCU, CU[2]);
		ArrayList<HomonimosCU> listData = contenedor.getData();
		for (HomonimosCU homonimosCU : listData){
			
			if(clienteTienda.isEmpty() && CT.length<4){
				return homonimosCU;
			} else if(homonimosCU.getFIID_NEGOCIO().equals(CT[0].trim())&&
						homonimosCU.getFIID_TIENDA().equals(CT[1].trim())&&
						homonimosCU.getFIID_CLIENTE().equals(CT[2].trim())&&
						homonimosCU.getFIDIGITO_VERIFICADOR().equals(CT[3].trim())){
				
					log.debug("[DATOS HOMONIMOS ENCONTRADOS]:");
					log.debug(utilerias.convertObjectToJson(homonimosCU));
						return homonimosCU;
						
					}
		}
		return null;
	}
	
	private DetalleConsultaBuroYScore obtenerDatosBuro(String idSolicitud) throws SQLException {
		HashMap<String, Object> mapBuro = buroDao.getDetalleBuroConsultado(idSolicitud);
		DetalleConsultaBuroYScore detalleBuroYScore = null;
		if((int)mapBuro.get("PO_RESULTADO")==0){
			String xmlBuro = (String) mapBuro.get("PO_XMLBURO");
			detalleBuroYScore = (DetalleConsultaBuroYScore) utilerias.convertXMLtoObject(xmlBuro, DetalleConsultaBuroYScore.class);
		}else{
			log.error("Error al obtener datos del Buro_Score para la Solicitud["+idSolicitud+"] : "+(String) mapBuro.get("PO_MENSAJE"));			
			throw new SQLException ("Error al obtener datos del Buro_Score para la Solicitud["+idSolicitud+"] : "+(String) mapBuro.get("PO_MENSAJE"));			
		}
		return detalleBuroYScore;
	}
		
	public ArrayList<HashMap<String,Object>> obtenerInfoVtaPresup(int sucursal, int pedido)throws Exception{
		Connection con=null;
		ArrayList<HashMap<String,Object>> resultados = null;
		try{
			con=conexionservice.getConexionSucursal(sucursal);
			resultados = consumoDeSPTienda.consumirSPConVtaPresup(sucursal,pedido, con);
			if(con!=null){
				conexionservice.commitConexion(con);
			}
			return resultados;
		}catch(Exception e){
			if(con!=null){
				conexionservice.rollbackConexion(con);
			}
			log.error("Error al obtener Monto y enganche [pedido "+pedido+", sucursal:"+sucursal+"]: "+utilerias.getTraceFromExcepcion(e));
			throw new Exception(e);
		}
	}	

	public boolean enviarCapacidadesMaximasTienda(Solicitud solicitud){
		Connection con=null;
		DetalleConsultaBuroYScore detalleBuroYScore = null; 
		HashMap<String,Integer> mapPACRLISOLICITUDOMNICANAL = new HashMap<String,Integer>(); 
		try{
			con=conexionservice.getConexionSucursal(solicitud.getIdSucursal());
			detalleBuroYScore = obtenerDatosBuro(solicitud.getIdSolicitud());
			
			consumoCapMaximaOMNICanal.execCapMaximaOMNICanal(String.valueOf(solicitud.getIdSolicitudTienda()), solicitud, detalleBuroYScore, con);
		
			if(con!=null){
				conexionservice.commitConexion(con);
			}else{		
				return false;
			}
			return 	true;	
		}catch(Exception e){		
			if(con!=null){
				conexionservice.rollbackConexion(con);
			}
			log.error("[ERROR]: NO SE PUDIERON ENVIAR LAS CAPACIDADES DE PAGO "+utilerias.getTraceFromExcepcion(e));
			return false;
		}
	}
	
	public boolean actualizarStatusNormalToInmediato(Solicitud solicitud){
		Connection con=null;
		DetalleConsultaBuroYScore detalleBuroYScore = null; 
		HashMap<String,Integer> mapPACRLISOLICITUDOMNICANAL = new HashMap<String,Integer>(); 
		try{
			con=conexionservice.getConexionSucursal(solicitud.getIdSucursal());
			
			consumoDeSPTienda.consumirSPCREDInsInvestigacionNormalToInmediato(solicitud, con, String.valueOf(solicitud.getIdSolicitudTienda()));
		
			if(con!=null){
				conexionservice.commitConexion(con);
			}else{		
				return false;
			}
			return 	true;	
		}catch(Exception e){		
			if(con!=null){
				conexionservice.rollbackConexion(con);
			}
			log.error("Error al actualizar solicitud Norma a Credito Inmediato: "+utilerias.getTraceFromExcepcion(e));
			return false;
		}
	}
	
}