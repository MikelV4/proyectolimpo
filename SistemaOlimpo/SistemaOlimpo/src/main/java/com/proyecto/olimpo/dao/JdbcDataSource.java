package com.proyecto.olimpo.dao;

import com.proyecto.olimpo.commons.PropertiesWS;
import javax.sql.DataSource;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;


@Component
public class JdbcDataSource {
	private final Logger logger  = Logger.getLogger(JdbcDataSource.class);
	@Autowired
	PropertiesWS propertiesWS;
	
	public SimpleJdbcCall getJdbcCall (){
		DataSource  dataSource =getOracleDataSource();
		return (new SimpleJdbcCall(dataSource));
	}
	
    public DataSource getOracleDataSource() {
        MysqlDataSource mysqlDS = null;
        String url=propertiesWS.JDBC_URL_PRODUCCION;
        String user=propertiesWS.JDBC_USERNAME_PRODUCCION; 
        String password=propertiesWS.JDBC_PA_PRODUCCION;
        
        try {
        	if(propertiesWS.MODO_DESARROLLO){
        		url=propertiesWS.JDBC_URL_DESARROLLO;
                user=propertiesWS.JDBC_USERNAME_DESARROLLO; 
                password=propertiesWS.JDBC_PA_DESARROLLO;
        	}
          mysqlDS = new MysqlDataSource();            
          mysqlDS.setURL(url);
          mysqlDS.setUser(user);
          mysqlDS.setPassword(password);
        } catch (Exception e) {
        	logger.error(e.getMessage());
        }
        return mysqlDS;
    }
}
