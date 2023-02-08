/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.eldelbit.conectoresj.MODEL;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author alex
 */
public class Cliente {
    
    private ArrayList<String> nombreA;
    
    private ArrayList<String> direccionA;
    

    
    private Integer id;
    
    private String nombre;
        
    private Integer edad;
    
    private String direccion;
    
   
    private Timestamp fechaNacimiento;
    
    
    private Timestamp createdAt;
    
   
    private Timestamp updatedAt;

    public Cliente() {

        
        direccionA = new ArrayList<>();
        direccionA.add("Diego Jimenez Castellanos");
        direccionA.add("Plaza España");
        direccionA.add("Gran Via");
        direccionA.add("Calle Mayor");
        direccionA.add("El Rio");
        direccionA.add("Ronda Del Madroño");
        
        nombreA = new ArrayList<>();
        nombreA.add("Pedro");
        nombreA.add("Manu");
        nombreA.add("Antonio");
        nombreA.add("Juan");
        nombreA.add("Borja");
        nombreA.add("Sofia");
        
        nombre = nombreA.get((int) (Math.random()*5)).toString();
        edad = (int) (10 + Math.random()*90);
        direccion = direccionA.get((int) (Math.random()*5)).toString();
        //Instant timestamp = Instant.now();
        //Instant minusFiveYears = timestamp.minus(edad, ChronoUnit.YEARS);
        //fechaNacimiento = Timestamp.from(minusFiveYears);
        
        fechaNacimiento = null;
        
    }

    public Cliente(Integer id, String nombre, Integer edad, String direccion, Timestamp fechaNacimiento, Timestamp createdAt, Timestamp updatedAt) {

        
        this.id = id;
        this.nombre = nombre;       
        this.edad = edad;
        this.direccion = direccion;
        this.fechaNacimiento = fechaNacimiento;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
 

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }
    
    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    
    public Timestamp getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Timestamp FechaNacimiento) {
        this.fechaNacimiento = FechaNacimiento;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    
}
