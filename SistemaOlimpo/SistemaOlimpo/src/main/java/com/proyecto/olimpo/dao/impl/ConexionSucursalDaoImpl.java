package com.proyecto.olimpo.dao.impl;

import com.proyecto.olimpo.dao.ConexionSucursalDao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import oracle.jdbc.OracleTypes;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;



@Repository
public class ConexionSucursalDaoImpl implements ConexionSucursalDao{
	private final Logger logger  = Logger.getLogger(ConexionSucursalDaoImpl.class);
	
	@Autowired
	private UtileriasHelper utileriasHelper;
	
	@Autowired
	private JdbcDataSource jdbcDataSource;
	
	@Override
	public HashMap<String,Object> getDatosConexionSucursal(int sucursal){
		HashMap<String, Object> resultados = new HashMap<String, Object>();
		try{
		 SimpleJdbcCall jdbCall = jdbcDataSource.getJdbcCall();
		 jdbCall.withSchemaName(ConstantsWS.BD_SCHEMA)
		 .withCatalogName(ConstantsWS.PAQ_OBTENER_DATOS_CONEXION_SUCURSAL)  
		 .withProcedureName(ConstantsWS.SP_OBTENER_DATOS_CONEXION_SUCURSAL)
		 .declareParameters(
			     new SqlOutParameter("PO_C_SUCURSAL", OracleTypes.CURSOR, new RowMapper<Object>() {
					@Override
					public Object mapRow(ResultSet rs, int arg1)
							throws SQLException {
						CursorDatosConexionSucursal cursor = new CursorDatosConexionSucursal();
						cursor.setIpsucursal(rs.getString("FCIPSUCURSAL"));
						cursor.setUsuario(rs.getString("FCUSUARIODB"));
						cursor.setPassword(rs.getString("FCPASSWORDDB"));
						return cursor;
					}
				}),
				new SqlParameter("PI_SUCURSALID", OracleTypes.INTEGER),
				new SqlOutParameter("PO_RESULTADO", OracleTypes.INTEGER),
				new SqlOutParameter("PO_MENSAJE", OracleTypes.VARCHAR)
				);
		 
		 Map<String, Object> results = jdbCall.execute(
				 new MapSqlParameterSource().addValue("PI_SUCURSALID", sucursal)
				 );
		 
			 resultados.put("datosConexion",((ArrayList<CursorDatosConexionSucursal>)results.get("PO_C_SUCURSAL")));
			 resultados.put("estatus",String.valueOf(results.get("PO_RESULTADO")));
			 resultados.put("mensaje",(String) results.get("PO_MENSAJE"));	
			 
		}catch(Exception e){
			logger.error(utileriasHelper.getTraceFromExcepcion(e));
			resultados.put("estatus", "1" );
			resultados.put("mensaje", e.getMessage());	
		}
		return resultados;
	}
}
