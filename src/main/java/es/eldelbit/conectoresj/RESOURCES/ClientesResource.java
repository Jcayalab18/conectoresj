/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.eldelbit.conectoresj.RESOURCES;

import es.eldelbit.conectoresj.DB.DB;
import es.eldelbit.conectoresj.MODEL.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alex
 */
public class ClientesResource {
    
    synchronized public String crearCliente(){
         
        Cliente cli = new Cliente();
        String info = null;
        Connection con = null;
        PreparedStatement stmtIns = null;
        int id = 0;
        
        PreparedStatement stmtSel = null;
        ResultSet rsSel = null;
        ResultSet rsKeys = null;
        
        try {
            con = DB.getConnection();
            
            con.setAutoCommit(false);
            
            //INSERT
            stmtIns = con.prepareStatement("INSERT INTO clientes (nombre, edad, direccion, fecha_nacimiento) VALUES (?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
            stmtIns.setString(1, cli.getNombre());
            stmtIns.setObject(2, cli.getEdad());
            stmtIns.setString(3, cli.getDireccion());
            stmtIns.setTimestamp(4, cli.getFechaNacimiento());
            
            
            if(stmtIns.executeUpdate() == 1){
                
                rsKeys = stmtIns.getGeneratedKeys();
                
                while(rsKeys.next())
                    id = rsKeys.getInt(1);

            }
            
            if(id > 0){
                
                stmtSel = con.prepareStatement("SELECT id, nombre, edad, direccion, fecha_nacimiento, created_at, updated_at FROM clientes WHERE id = ?");
                
                stmtSel.setInt(1, id);
                rsSel = stmtSel.executeQuery();
                
                
                 if (rsSel.next()) {
                    cli= new Cliente(
                            DB.getInt(rsSel, "id"),
                            rsSel.getString("nombre"),
                            DB.getInt(rsSel, "edad"),
                            rsSel.getString("direccion"),
                            rsSel.getTimestamp("fecha_nacimiento"),
                            rsSel.getTimestamp("created_at"),
                            rsSel.getTimestamp("updated_at")
                    );
                    // SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    // String fecha = dateFormat.format(cli.getFechaNacimiento());
                    info = "Nombre: "+cli.getNombre()+" Edad: "
                            + cli.getEdad().toString()+" Direccion: "
                            + cli.getDireccion()+".";
                            
                    //        +  fecha+".";
                }
            
            }
            
            con.commit();
            
        } catch (SQLException ex) {
            Logger.getLogger(Varios.class.getName()).log(Level.SEVERE, null, ex);
            
            
            info = "ERROR";
        }finally{
            DB.closeResultSet(rsSel);
            DB.closeStatement(stmtSel);

            DB.closeResultSet(rsKeys);

            DB.closeStatement(stmtIns);
            DB.closeConnection(con);
        }
        
        return info;
        
        }
    
    synchronized public String listar(){
        
        String info = "";
        var clientes = new ArrayList<Cliente>();
        
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        
                try {

            conn = DB.getConnection();
            stmt = conn.createStatement();

            String sql = "SELECT id, nombre, edad, direccion, fecha_nacimiento, created_at, updated_at FROM clientes";
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                var cliente = new Cliente(
                        DB.getInt(rs, "id"),
                        rs.getString("nombre"),
                        DB.getInt(rs, "edad"),
                        rs.getString("direccion"),
                        rs.getTimestamp("fecha_nacimiento"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at")
                );

                clientes.add(cliente);
                info = info + "Nombre: "+cliente.getNombre()+" Edad: "
                            + cliente.getEdad().toString()+" Direccion: "
                            + cliente.getDireccion()+".\n";
            }

           
            
            // Thread.sleep(3000);

        } catch (SQLException /*| InterruptedException*/ ex) {
           Logger.getLogger(ClientesResource.class.getName()).log(Level.SEVERE, null, ex);
            info = "ERROR";
            
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(stmt);
            DB.closeConnection(conn);
        }

      return info;  
    }
    
    synchronized public String mostrar(int id){
       
        Object entity = null;
        String info = "";
        Cliente cliente = null;

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            conn = DB.getConnection();
            stmt = conn.prepareStatement("SELECT id, nombre, edad, direccion, fecha_nacimiento, created_at, updated_at FROM clientes WHERE id = ?");

            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                cliente = new Cliente(
                        DB.getInt(rs, "id"),
                        rs.getString("nombre"),
                        DB.getInt(rs, "edad"),
                        rs.getString("direccion"),
                        rs.getTimestamp("fecha_nacimiento"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at")
                );

                entity = cliente;
                
                info = "Id: " +cliente.getId().toString()
                        + "Nombre: "+cliente.getNombre()+" Edad: "
                            + cliente.getEdad().toString()+" Direccion: "
                            + cliente.getDireccion()+".";
            }

            
            
            if (entity == null) {
               
                info = "NO ENCONTRADO";
            }
            
            // Thread.sleep(3000);

        } catch (SQLException /*| InterruptedException*/ ex) {
            
            info = "ERROR";
            Logger.getLogger(ClientesResource.class.getName()).log(Level.SEVERE, null, ex);
            
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(stmt);
            DB.closeConnection(conn);
        }

        return info;
        
    }
    
   synchronized public String borrar(int id){
        Object entity = null;
        String info = "";
        Connection conn = null;

        PreparedStatement stmtDel = null;

        try {

            conn = DB.getConnection();
            
            conn.setAutoCommit(false);

            // borrar
            stmtDel = conn.prepareStatement("DELETE FROM clientes WHERE id = ?");

            stmtDel.setInt(1, id);

            if (stmtDel.executeUpdate() == 1) {
                
                info = "BORRADO";
            }else{
                
                info = "NO ENCONTRADO";
            }

            conn.commit();
            
            // Thread.sleep(3000);
        } catch (SQLException /*| InterruptedException*/ ex) {
            DB.rollback(conn);
            
            info = "ERROR";
            Logger.getLogger(ClientesResource.class.getName()).log(Level.SEVERE, null, ex);
        } finally {            
            DB.closeStatement(stmtDel);
            DB.closeConnection(conn);
        }

        return info;
    }
    
    
    synchronized public String modificar(int id){
        Object entity = null;
        String info="";
        Cliente cliente = new Cliente();
        Connection conn = null;

        PreparedStatement stmtIns = null;

        PreparedStatement stmtSel = null;
        ResultSet rsSel = null;

        try {

            conn = DB.getConnection();
            
            conn.setAutoCommit(false);

            // modificar
            stmtIns = conn.prepareStatement("UPDATE clientes SET nombre = ?, edad = ?, direccion = ?, fecha_nacimiento = ? WHERE id = ?");

            stmtIns.setString(1, cliente.getNombre());
            stmtIns.setObject(2, cliente.getEdad());
            stmtIns.setString(3, cliente.getDireccion());
            stmtIns.setTimestamp(4, cliente.getFechaNacimiento());
            stmtIns.setInt(5, id);

            if (stmtIns.executeUpdate() == 1) {

                // obtener datos
                stmtSel = conn.prepareStatement("SELECT id, nombre, edad, direccion, fecha_nacimiento, created_at, updated_at FROM clientes WHERE id = ?");

                stmtSel.setInt(1, id);
                rsSel = stmtSel.executeQuery();

                if (rsSel.next()) {
                    cliente = new Cliente(
                            DB.getInt(rsSel, "id"),
                            rsSel.getString("nombre"),
                            DB.getInt(rsSel, "edad"),
                            rsSel.getString("direccion"),
                            rsSel.getTimestamp("fecha_nacimiento"),
                            rsSel.getTimestamp("created_at"),
                            rsSel.getTimestamp("updated_at")
                    );
                    
                    entity = cliente;
                    info = "Id: " +cliente.getId().toString()
                        + " Nombre nuevo: "+cliente.getNombre()+" Edad nueva: "
                            + cliente.getEdad().toString()+" Direccion nueva: "
                            + cliente.getDireccion()+".";
                    
                }
            }

            if (entity == null) {
                info = "NO ENCONTRADO";
            }
            
            conn.commit();

            // Thread.sleep(3000);
        } catch (SQLException /*| InterruptedException*/ ex) {
            DB.rollback(conn);
            info = "ERROR";
            Logger.getLogger(ClientesResource.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DB.closeResultSet(rsSel);
            DB.closeStatement(stmtSel);

            DB.closeStatement(stmtIns);
            DB.closeConnection(conn);
        }

        return info;
    }
}
