package com.proyecto.olimpo.conexionSQL;

import com.proyecto.olimpo.commons.PropertiesWS;
import com.proyecto.olimpo.dao.ConexionSucursalDao;
import com.proyecto.olimpo.utils.UtileriasHelper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;

@Component
public class ConexionSucursal {

    private final Logger logger = Logger.getLogger(ConexionSucursal.class);

    @Autowired
    private ConexionSucursalDao datosconexion;
    @Autowired
    private UtileriasHelper utileriasHelper;
    @Autowired
    private PropertiesWS propertiesWS;

    //Abre conexion y transaccion
    public Connection getConexionSucursal(int sucursal) throws SQLException {
        logger.info("[OBTENER DATOS CONEXION SUCURSAL]:" + sucursal);
        Connection connection = null;
        HashMap<String, Object> resultados = new HashMap<String, Object>();
        String url, database, username, password;
        try {

            if (propertiesWS.MODO_DESARROLLO) {
//					url="jdbc:sqlserver://"+datosConexion.get(0).getIpsucursal()+":1433;"+"databaseName=ADN2";
                url = propertiesWS.JDBC_URL;
                database = propertiesWS.JDBC_DATABASE;
                username = propertiesWS.JDBC_USERNAME;
                password = propertiesWS.JDBC_PASSWORD;

                logger.debug("Datos de conexion [url]:" + url);
                logger.debug("Datos de conexion [databse]:" + database);
                logger.debug("Datos de conexion [us]:" + username);
                logger.debug("Datos de conexion [pas]:" + password);

                connection = getConexion(url, database, username, password);
            }

        } catch (Exception e) {
            throw new SQLException("Fallo obtener datos Conexion: " + utileriasHelper.getTraceFromExcepcion(e));
        }
        return connection;
    }

    //Obtener Conexion
    private Connection getConexion(String url, String database, String username, String password) throws SQLException {
        Connection con = null;
        String connectionUrl = url+"//"+database;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            DriverManager.setLoginTimeout(10);
            con = DriverManager.getConnection(url, username, password);
//			con = DriverManager.getConnection(connectionUrl);
            con.setAutoCommit(false);
        } catch (SQLException e) {
            if (con != null) {
                con.close();
            }
            logger.error("Error al realizar conexion con Base de Datos: " + utileriasHelper.getTraceFromExcepcion(e));
        } catch (Exception e) {
            logger.error("Fallo al iniciar la conexion: " + utileriasHelper.getTraceFromExcepcion(e));
            throw new SQLException("Fallo al iniciar conexion: " + utileriasHelper.getTraceFromExcepcion(e));
        }
        return con;
    }

    //Para  conuslta especifica
    synchronized public Object execSpTienda(String sp, Connection conn) throws SQLException {
        ResultSet rs = null;
        CallableStatement cstmt = null;
        int estatus = -1;

        try {
            sp = sp + ",?";
            cstmt = conn.prepareCall(sp);
            cstmt.registerOutParameter(1, Types.INTEGER);

            cstmt.execute();

            estatus = cstmt.getInt(1);

            if (estatus != 1) {
                throw new SQLException("Error en la ejecucion de SP: " + sp);
            }

        } catch (Exception e) {
            throw new SQLException("Fallo al consultar el SP: " + utileriasHelper.getTraceFromExcepcion(e));
        } finally {
            closeObjectsSql(rs, cstmt);
        }
        return null;
    }

    public ArrayList<HashMap<String, Object>> execSPConVtaPresup(String sp, Connection con) throws SQLException {
        PreparedStatement stmn = null;
        ResultSet rs = null;
        ArrayList<HashMap<String, Object>> resultados = null;
        try {
            sp += ",0";
            stmn = con.prepareStatement(sp);
            rs = stmn.executeQuery();

            resultados = mapResultSet(rs);
        } catch (SQLException e) {
            throw new SQLException("Fallo Ejecucion Query: " + utileriasHelper.getTraceFromExcepcion(e));
        } finally {
            closeObjectsSql(rs, stmn);
        }
        return resultados;
    }

    //Para  conuslta especifica
    synchronized public HashMap<String, String> execSpGeneraAltaCU(String sp, Connection conn) throws SQLException {
        ResultSet rs = null;
        CallableStatement cstmt = null;
        HashMap<String, String> map = new HashMap<String, String>();
        try {

            sp = sp + ",?,?,?,?,?,?,?,?,?,?";
            cstmt = conn.prepareCall(sp);
            cstmt.registerOutParameter(1, Types.VARCHAR);
            cstmt.registerOutParameter(2, Types.VARCHAR);
            cstmt.registerOutParameter(3, Types.VARCHAR);
            cstmt.registerOutParameter(4, Types.VARCHAR);
            cstmt.registerOutParameter(5, Types.VARCHAR);
            cstmt.registerOutParameter(6, Types.VARCHAR);
            cstmt.registerOutParameter(7, Types.VARCHAR);
            cstmt.registerOutParameter(8, Types.VARCHAR);
            cstmt.registerOutParameter(9, Types.VARCHAR);
            cstmt.registerOutParameter(10, Types.VARCHAR);
            cstmt.execute();

            String paisCU = cstmt.getString(1);
            String canalCU = cstmt.getString(2);
            String sucursalCU = cstmt.getString(3);
            String folioCU = cstmt.getString(4);
            String negocioCT = cstmt.getString(5);
            String tiendaCT = cstmt.getString(6);
            String clienteIdCT = cstmt.getString(7);
            String digitoVerCT = cstmt.getString(8);
            String biometrico = cstmt.getString(9);
            String estatus = cstmt.getString(10);

            map.put("paisCU", paisCU.trim());
            map.put("canalCU", canalCU.trim());
            map.put("sucursalCU", sucursalCU.trim());
            map.put("folioCU", folioCU.trim());
            map.put("negocioCT", negocioCT.trim());
            map.put("tiendaCT", tiendaCT.trim());
//			   map.put("tiendaCT", tiendaCT.trim());						
            map.put("clienteIdCT", clienteIdCT.trim());
            map.put("digitoVerCT", digitoVerCT.trim());
            map.put("biometrico", biometrico.trim());

            logger.info("[Respuesta Servicio Cliente Unico]: " + map);

            if (estatus.equals("1")) {
                throw new SQLException("Fallo al consultar el SP PACRLLIGenraAltaCU");
            }

        } catch (Exception e) {
            throw new SQLException("Fallo al consultar el SP PACRLLIGenraAltaCU: " + utileriasHelper.getTraceFromExcepcion(e));
        } finally {
            closeObjectsSql(rs, cstmt);
        }
        return map;
    }

    //Para  conuslta especifica AltaCUForaneo
    synchronized public HashMap<String, String> execSpAltaCUForaneo(String sp, Connection conn) throws SQLException {
        ResultSet rs = null;
        CallableStatement cstmt = null;
        HashMap<String, String> map = new HashMap<String, String>();
        try {

            sp = sp + ",?,?,?,?,?,?,?,?,?,?";
            cstmt = conn.prepareCall(sp);
            cstmt.registerOutParameter(1, Types.VARCHAR);
            cstmt.registerOutParameter(2, Types.VARCHAR);
            cstmt.registerOutParameter(3, Types.VARCHAR);
            cstmt.registerOutParameter(4, Types.VARCHAR);
            cstmt.registerOutParameter(5, Types.VARCHAR);
            cstmt.registerOutParameter(6, Types.VARCHAR);
            cstmt.registerOutParameter(7, Types.VARCHAR);
            cstmt.registerOutParameter(8, Types.VARCHAR);
            cstmt.registerOutParameter(9, Types.VARCHAR);
            cstmt.registerOutParameter(10, Types.VARCHAR);
            cstmt.execute();

            String paisCU = cstmt.getString(1);
            String canalCU = cstmt.getString(2);
            String sucursalCU = cstmt.getString(3);
            String folioCU = cstmt.getString(4);
            String negocioCT = cstmt.getString(5);
            String tiendaCT = cstmt.getString(6);
            String clienteIdCT = cstmt.getString(7);
            String digitoVerCT = cstmt.getString(8);
            String biometrico = cstmt.getString(9);
            String estatus = cstmt.getString(10);

            map.put("paisCU", paisCU.trim());
            map.put("canalCU", canalCU.trim());
            map.put("sucursalCU", sucursalCU.trim());
            map.put("folioCU", folioCU.trim());
            map.put("negocioCT", negocioCT.trim());
            map.put("tiendaCT", tiendaCT.trim());
            map.put("clienteIdCT", clienteIdCT.trim());
            map.put("digitoVerCT", digitoVerCT.trim());
            map.put("biometrico", biometrico.trim());

            logger.info("[Respuesta Servicio Cliente Unico]: " + map);

            if (estatus.equals("1")) {
                throw new SQLException("Fallo al consultar el SP PACRLLIAltaCUForaneo");
            }

        } catch (Exception e) {
            throw new SQLException("Fallo al consultar el SP PACRLLIAltaCUForaneo: " + utileriasHelper.getTraceFromExcepcion(e));
        } finally {
            closeObjectsSql(rs, cstmt);
        }
        return map;
    }

    //Llamar SP en Tienda que no retorna ResultSet
    public Object execSpTiendaNoRS(String sp, Connection con) throws SQLException {
        Statement stmn = null;
        try {
            stmn = con.createStatement();
            stmn.execute(sp);
        } catch (SQLException e) {
            throw new SQLException("Fallo Ejecucion Query: " + utileriasHelper.getTraceFromExcepcion(e));
        } finally {
            closeObjectsSql(null, stmn);
        }
        return null;
    }

    public ArrayList<HashMap<String, Object>> execSpTiendaRS(String sp, Connection con) throws SQLException {
        Statement stmn = null;
        ResultSet rs = null;
        ArrayList<HashMap<String, Object>> resultados = null;
        try {
            stmn = con.createStatement();
            rs = stmn.executeQuery(sp);
            resultados = mapResultSet(rs);
        } catch (SQLException e) {
            throw new SQLException("Fallo Ejecucion Query: " + utileriasHelper.getTraceFromExcepcion(e));
        } finally {
            closeObjectsSql(rs, stmn);
        }
        return resultados;
    }

    //Almacena los datos del ResultSet a un HashMap
    private ArrayList<HashMap<String, Object>> mapResultSet(ResultSet rs) throws SQLException {
        ArrayList<HashMap<String, Object>> listMap = new ArrayList<HashMap<String, Object>>();
        try {
            ResultSetMetaData rsMeta = rs.getMetaData();
            int cantColumns = rsMeta.getColumnCount();
            while (rs.next()) {
                HashMap<String, Object> map = new HashMap<String, Object>();
                for (int column = 1; column <= cantColumns; column++) {
                    map.put(rsMeta.getColumnName(column), rs.getObject(column));
                }
                listMap.add(map);
            }
        } catch (SQLException e) {
            throw new SQLException("Fallo conversion ResultSet a HashMap: " + utileriasHelper.getTraceFromExcepcion(e));
        }
        return listMap;
    }

    //Para  conuslta especifica "SolicitudOmniCanal"
    synchronized public HashMap<String, Integer> execSolicitudOmniCanal(String sp, Connection conn) throws SQLException {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        ResultSet rs = null;
        CallableStatement cstmt = null;
        int solicitud = -1;
        int tienda = -1;
        int negocio = -1;
        int cliente = -1;
        int estatus = -1;

        try {
            sp = sp + ",?,?,?,?,?";
            cstmt = conn.prepareCall(sp);
            cstmt.registerOutParameter(1, Types.INTEGER);
            cstmt.registerOutParameter(2, Types.INTEGER);
            cstmt.registerOutParameter(3, Types.INTEGER);
            cstmt.registerOutParameter(4, Types.INTEGER);
            cstmt.registerOutParameter(5, Types.INTEGER);

            cstmt.execute();

            solicitud = cstmt.getInt(1);
            tienda = cstmt.getInt(2);
            negocio = cstmt.getInt(3);
            cliente = cstmt.getInt(4);
            estatus = cstmt.getInt(5);

            map.put("solicitudId", solicitud);
            map.put("tienda", tienda);
            map.put("negocio", negocio);
            map.put("cliente", cliente);

            logger.debug("[DATOS OBTENIDOS PACRLISOLICITUDOMNICANAL]: " + map);

            if (estatus != 1) {
                throw new SQLException("Error en la ejecucion de SP PACRLISOLICITUDOMNICANAL");
            }

        } catch (Exception e) {
            throw new SQLException("Fallo al consultar el SP PACRLISOLICITUDOMNICANAL: " + utileriasHelper.getTraceFromExcepcion(e));
        } finally {
            closeObjectsSql(rs, cstmt);
        }
        return map;
    }

    //Para  conuslta especifica "SolicitudOmniCanal"
    synchronized public HashMap<String, String> execAltaClienteOmniCanal(String sp, Connection conn) throws SQLException {
        HashMap<String, String> map = new HashMap<String, String>();
        ResultSet rs = null;
        CallableStatement cstmt = null;
        String idNegocio = "";
        String noTienda = "";
        String idCliente = "";
        String digitoVer = "";
        int estatus = -1;

        try {
            sp = sp + ",?,?,?,?,?";
            cstmt = conn.prepareCall(sp);
            cstmt.registerOutParameter(1, Types.VARCHAR);
            cstmt.registerOutParameter(2, Types.VARCHAR);
            cstmt.registerOutParameter(3, Types.VARCHAR);
            cstmt.registerOutParameter(4, Types.VARCHAR);
            cstmt.registerOutParameter(5, Types.INTEGER);

            cstmt.execute();

            idNegocio = cstmt.getString(1).trim();
            noTienda = cstmt.getString(2).trim();
            idCliente = cstmt.getString(3).trim();
            digitoVer = cstmt.getString(4).trim();
            estatus = cstmt.getInt(5);

            map.put("idNegocio", idNegocio);
            map.put("noTienda", noTienda);
            map.put("idCliente", idCliente);
            map.put("digitoVer", digitoVer);

            logger.debug("[DATOS OBTENIDOS PACRLLIALTACLIENTEOMNI]: " + map);

            if (estatus != 1) {
                throw new SQLException("Error en la ejecucion de SP PACRLLIALTACLIENTEOMNI");
            }

        } catch (Exception e) {
            throw new SQLException("Fallo al consultar el SP PACRLLIALTACLIENTEOMNI: " + utileriasHelper.getTraceFromExcepcion(e));
        } finally {
            closeObjectsSql(rs, cstmt);
        }
        return map;
    }

    //Para  conuslta especifica "SolicitudOmniCanal" con "SolicitudTienda"
    synchronized public HashMap<String, Integer> execActualizarSolicitudOmniCanal(String sp, Connection conn, int idSolicitudTienda) throws SQLException {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        ResultSet rs = null;
        CallableStatement cstmt = null;
        int tienda = -1;
        int negocio = -1;
        int cliente = -1;
        int estatus = -1;

        try {
            sp = sp + ",?,?,?,?";
            cstmt = conn.prepareCall(sp);
            cstmt.registerOutParameter(1, Types.INTEGER);
            cstmt.registerOutParameter(2, Types.INTEGER);
            cstmt.registerOutParameter(3, Types.INTEGER);
            cstmt.registerOutParameter(4, Types.INTEGER);

            cstmt.execute();

            tienda = cstmt.getInt(1);
            negocio = cstmt.getInt(2);
            cliente = cstmt.getInt(3);
            estatus = cstmt.getInt(4);

            map.put("solicitudId", idSolicitudTienda);
            map.put("tienda", tienda);
            map.put("negocio", negocio);
            map.put("cliente", cliente);

            logger.debug("[DATOS OBTENIDOS PACRLISOLICITUDOMNICANAL AL ACTUALIZAR SOLICITUD]: " + map);

            if (estatus != 1) {
                throw new SQLException("Error en la ejecucion de SP PACRLISOLICITUDOMNICANAL con IdSolicitudTienda");
            }

        } catch (Exception e) {
            throw new SQLException("Fallo al consultar el SP PACRLISOLICITUDOMNICANAL con IdSolicitudTienda: " + utileriasHelper.getTraceFromExcepcion(e));
        } finally {
            closeObjectsSql(rs, cstmt);
        }
        return map;
    }

    //Para  ejecutar especifica "GeneraCreditoPP"
    synchronized public HashMap<String, Integer> execGeneraCreditoPP(String sp, Connection conn) throws SQLException {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        ResultSet rs = null;
        CallableStatement cstmt = null;
        int noPedido = -1;
        int noPedidoSeg = -1;
        int noPedidoSAC = -1;
        int estatus = -1;

        try {
            sp = sp + ",?,?,?,?";
            cstmt = conn.prepareCall(sp);
            cstmt.registerOutParameter(1, Types.INTEGER);
            cstmt.registerOutParameter(2, Types.INTEGER);
            cstmt.registerOutParameter(3, Types.INTEGER);
            cstmt.registerOutParameter(4, Types.INTEGER);

            cstmt.execute();

            estatus = cstmt.getInt(1);
            noPedido = cstmt.getInt(2);
            noPedidoSeg = cstmt.getInt(3);
            noPedidoSAC = cstmt.getInt(4);

            map.put("NoPedido", noPedido);
            map.put("NoPedidoSEG", noPedidoSeg);
            map.put("NoPedidoSAC", noPedidoSAC);

            if (estatus != 1) {
                throw new SQLException("Error en la ejecucion de SP PACRLLIGeneraCreditoPP");
            }

        } catch (Exception e) {
            throw new SQLException("Fallo al consultar el SP PACRLLIGeneraCreditoPP: " + utileriasHelper.getTraceFromExcepcion(e));
        } finally {
            closeObjectsSql(rs, cstmt);
        }
        return map;
    }

    //Realiza Commit a la Transaccion y cierra la conexion
    public void commitConexion(Connection conn) {
        if (conn != null) {
            try {
                conn.commit();
                conn.setAutoCommit(true);
                conn.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    //Realiza Rollback a la Transaccion y cierra la conexion
    public void rollbackConexion(Connection conn) {
        if (conn != null) {
            try {
                conn.rollback();
                conn.setAutoCommit(true);
                conn.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    //Cierra los objetos utilizados para la consulta por SP
    public void closeObjectsSql(ResultSet rs, Statement stmn) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
        }
        try {
            if (stmn != null) {
                stmn.close();
            }
        } catch (SQLException e) {
        }
    }
}
