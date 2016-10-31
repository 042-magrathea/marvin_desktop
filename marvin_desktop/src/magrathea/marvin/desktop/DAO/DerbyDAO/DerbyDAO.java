/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magrathea.marvin.desktop.DAO.DerbyDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import magrathea.marvin.desktop.DAO.DAO;
import org.apache.commons.dbutils.QueryRunner;

/**
 *
 * @author boscalent
 */
public class DerbyDAO implements DAO{
    
    protected Connection con;
    protected QueryRunner dbAccess = new QueryRunner();   // Trhead Safe Query from Apache DBUtils
    
    @Override
    public void setup() throws Exception {
        // Create the DB
        con = DriverManager.getConnection("jdbc:derby://localhost:1527/users;create=true");
        
        dbAccess.update(con, 
                "CREATE TABLE \"User\" ( id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY"
                + " (START WITH 1, INCREMENT BY 1), nickname VARCHAR(255), password VARCHAR(255), "
                + " email VARCHAR(255), administrator BOOLEAN );"
        );
    }

    @Override
    public void connect() throws Exception {
        // Don't create the DB
        con = DriverManager.getConnection("jdbc:derby://localhost:1527/users");
    }

    @Override
    public void close() throws Exception {
        con.close();
        try {
            DriverManager.getConnection("jdbc:derby://localhost:1527/users;shutdown=true");
        } catch (Exception e) {
        }
    }
}