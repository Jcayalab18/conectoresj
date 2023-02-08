/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.eldelbit.conectoresj.RESOURCES;

import es.eldelbit.conectoresj.DB.DB;
import es.eldelbit.conectoresj.MODEL.Cliente;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alex
 */
public class Varios {
    
    public File getScript(){
        File url = new File("/home/alex/NetBeansProjects/conectoresj/src/main/java/es/eldelbit/conectoresj/DB/script.sql");
        
        if(url == null){
                throw new IllegalArgumentException(url + " is not found 1");
                }
        
        return url;
    }
    
    public String script(){
        
        String info="";
        Connection con = null;
        Statement stmt = null;
        
        
        try {
            String sql = new String(Files.readAllBytes(getScript().toPath()));
            
            con = DB.getConnection();
            stmt = con.createStatement();
            
            stmt.executeUpdate(sql);
            
            info = "Script realizado";
            
       } catch (SQLException | IOException ex) {
            Logger.getLogger(Varios.class.getName()).log(Level.SEVERE, null, ex);
            info = "ERROR";
           
        } finally{
            DB.closeStatement(stmt);
            DB.closeConnection(con);
        }
        
        return info;
    }
    
    
    
}
