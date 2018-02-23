/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassFiles;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author jvald
 */
public class Connector {
    //Class fields
    private String url;
    private String server;
    private String puerto;
    private String bd;
    private String user;
    private String pwd;
    private String driver;
    private static Connector instancia;
    private Connection con;
    //Methods
    public static Connector getInstance(){
        if(instancia == null){
            instancia = new Connector();
        }
        return instancia;
    }
    
    private Connector(){
        server = "localhost";
        puerto="3306";
        bd="estacionesfinal";
        user="admin";
        pwd = "admin123";
        driver="com.mysql.jdbc.Driver";
        
        url="jdbc:mysql://"+server+"/" +bd + "?autoReconnect=true&useSSL=false";
        
        try {
            Class.forName(driver);
             con = DriverManager.getConnection(url,user,pwd);
        } catch (ClassNotFoundException ex) {
            System.out.println("Error a: " + ex.getMessage());
        } catch (SQLException ex) {
            System.out.println("Error b: " + ex.getMessage());
        }
    }
    
    public Connection getConnection(){
            return con;
        }  
}
