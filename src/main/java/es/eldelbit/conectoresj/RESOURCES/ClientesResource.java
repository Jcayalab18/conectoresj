/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.eldelbit.conectoresj.RESOURCES;

import es.eldelbit.conectoresj.DB.DB;
import es.eldelbit.conectoresj.MODEL.Cliente;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
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
    
    
    public String procedimiento(String search){
        
        String entity = null;

        Integer total;

        Connection conn = null;
        CallableStatement stmt = null;

        try {

            conn = DB.getConnection();

            stmt = conn.prepareCall("{CALL ContarClientesP(?, ?)}");

            stmt.setString(1, search);

            stmt.registerOutParameter(2, Types.NUMERIC);

            stmt.executeUpdate();

            total = stmt.getInt(2);

            entity = total.toString();

        } catch (SQLException ex) {
            entity = "ERROR";
            Logger.getLogger(Varios.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DB.closeStatement(stmt);
            DB.closeConnection(conn);
        }

        return entity;
        
    }
    
    public String funcion(String search){
        
        
        String entity = null;

        Integer total;

        Connection conn = null;
        CallableStatement stmt = null;

        try {

            conn = DB.getConnection();

            stmt = conn.prepareCall("{? = CALL ContarClientesF(?)}");

            stmt.setString(2, search);

            stmt.registerOutParameter(1, Types.NUMERIC);

            stmt.execute();

            total = stmt.getInt(1);

            entity = total.toString();

        } catch (SQLException ex) {
            entity = "ERROR";
            Logger.getLogger(Varios.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DB.closeStatement(stmt);
            DB.closeConnection(conn);
        }

        return entity;
    }
    
    public String recorrer(){
        String entity = null;

        var clientes = new ArrayList<Cliente>();

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {

            conn = DB.getConnection();

            // Muestra en el log qué opciones están soportadas
            var dbmd = conn.getMetaData();

            Logger.getLogger("resultSetActualizable").log(Level.INFO, "TYPE_FORWARD_ONLY: {0}", dbmd.supportsResultSetType(ResultSet.TYPE_FORWARD_ONLY));
            Logger.getLogger("resultSetActualizable").log(Level.INFO, "TYPE_FORWARD_ONLY+CONCUR_READ_ONLY: {0}", dbmd.supportsResultSetConcurrency(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY));
            Logger.getLogger("resultSetActualizable").log(Level.INFO, "TYPE_FORWARD_ONLY+CONCUR_UPDATABLE: {0}", dbmd.supportsResultSetConcurrency(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE));

            Logger.getLogger("resultSetActualizable").log(Level.INFO, "TYPE_SCROLL_INSENSITIVE: {0}", dbmd.supportsResultSetType(ResultSet.TYPE_SCROLL_INSENSITIVE));
            Logger.getLogger("resultSetActualizable").log(Level.INFO, "TYPE_SCROLL_INSENSITIVE+CONCUR_READ_ONLY: {0}", dbmd.supportsResultSetConcurrency(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY));
            Logger.getLogger("resultSetActualizable").log(Level.INFO, "TYPE_SCROLL_INSENSITIVE+CONCUR_UPDATABLE: {0}", dbmd.supportsResultSetConcurrency(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE));

            Logger.getLogger("resultSetActualizable").log(Level.INFO, "TYPE_SCROLL_SENSITIVE: {0}", dbmd.supportsResultSetType(ResultSet.TYPE_SCROLL_SENSITIVE));
            Logger.getLogger("resultSetActualizable").log(Level.INFO, "TYPE_SCROLL_SENSITIVE+CONCUR_READ_ONLY: {0}", dbmd.supportsResultSetConcurrency(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY));
            Logger.getLogger("resultSetActualizable").log(Level.INFO, "TYPE_SCROLL_SENSITIVE+CONCUR_UPDATABLE: {0}", dbmd.supportsResultSetConcurrency(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE));

            Logger.getLogger("resultSetActualizable").log(Level.INFO, "HOLD_CURSORS_OVER_COMMIT: {0}", dbmd.supportsResultSetHoldability(ResultSet.HOLD_CURSORS_OVER_COMMIT));
            Logger.getLogger("resultSetActualizable").log(Level.INFO, "CLOSE_CURSORS_AT_COMMIT: {0}", dbmd.supportsResultSetHoldability(ResultSet.CLOSE_CURSORS_AT_COMMIT));

            // ResultSet.TYPE_FORWARD_ONLY
            // ResultSet.TYPE_SCROLL_INSENSITIVE
            // ResultSet.TYPE_SCROLL_SENSITIVE
            int resultSetType = ResultSet.TYPE_SCROLL_INSENSITIVE;

            // ResultSet.CONCUR_READ_ONLY
            // ResultSet.CONCUR_UPDATABLE
            int resultSetConcurrency = ResultSet.CONCUR_UPDATABLE;

            // ResultSet.HOLD_CURSORS_OVER_COMMIT
            // ResultSet.CLOSE_CURSORS_AT_COMMIT
            int resultSetHoldability = ResultSet.HOLD_CURSORS_OVER_COMMIT;

            stmt = conn.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);

            String sql = "SELECT id, nombre, edad, direccion, fecha_nacimiento, created_at, updated_at FROM clientes";
            rs = stmt.executeQuery(sql);

            // mover el cursor
            rs.next(); // ir al siguiente
            rs.previous(); // ir al anterior
            rs.first(); // ir al primero
            rs.last(); // ir al último

            rs.beforeFirst(); // ir a antes del primero
            rs.afterLast(); // ir a después del último

            rs.relative(-1); // avanzar x posiciones
            rs.absolute(1); // ir a la posición x

            // recorrer datos
            rs.beforeFirst();
            while (rs.next()) {
                var id = DB.getInt(rs, "id");

                // modificación
                if (id % 2 == 0) {
                    rs.updateString("nombre", rs.getString("nombre") + '_' + id);
                    rs.updateRow();
                }

                // borrado               
                if (id > 4) {
                    rs.deleteRow();
                    continue;
                }

                var cliente = new Cliente(
                        id,
                        rs.getString("nombre"),
                        DB.getInt(rs, "edad"),
                        rs.getString("direccion"),
                        rs.getTimestamp("fecha_nacimiento"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at")
                );

                clientes.add(cliente);
                
                entity = entity + "Nombre: "+cliente.getNombre()+" Edad: "
                + cliente.getEdad().toString()+" Direccion: "
                + cliente.getDireccion()+".\n";
            }
            
            // insercción
            rs.moveToInsertRow();
            rs.updateString("nombre", "nuevo nombre");           
            rs.updateInt("edad", 18);           
            rs.insertRow();


            
        } catch (SQLException ex) {
            entity = "ERROR";
            Logger.getLogger(ClientesResource.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(stmt);
            DB.closeConnection(conn);
        }

        return entity;
    }
}
