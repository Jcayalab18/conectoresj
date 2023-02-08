/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.eldelbit.conectoresj.MODEL;

import es.eldelbit.conectoresj.RESOURCES.ClientesResource;
import es.eldelbit.conectoresj.RESOURCES.Varios;
import es.eldelbit.conectoresj.VIEW.menu;
import java.awt.TextArea;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author alex
 */
public class Hilo extends Thread{

    private int i;
    private JTextArea texto;
    private JTextField idText;
    private Varios varios;
    private ClientesResource clientesResource;
    
    public Hilo(int i, JTextArea texto, JTextField id) {
        
        this.i = i;
        this.texto = texto;
        this.idText = id;
        varios = new Varios();
        clientesResource = new ClientesResource();
           
    }
    
    
    

    @Override
    public void run() {
        
        switch (i) {
            case 1:
                
               texto.append(varios.script() + "\n");
                
                break;
                
            case 2:
                texto.append(clientesResource.crearCliente()+"\n");
                
                break;
                
            case 3:
                texto.append(clientesResource.listar());
                break;
                
            case 4:
                texto.append((idText.getText().isEmpty())?"FALTA LA ID\n":clientesResource.mostrar(Integer.parseInt(idText.getText()))+"\n");
                break;
                
                
            case 5:
                texto.append((idText.getText().isEmpty()?"FALTA LA ID":clientesResource.borrar(Integer.parseInt(idText.getText())))+"\n");

                break;
                
                
            case 6:
                texto.append((idText.getText().isEmpty()?"FALTA LA ID":clientesResource.modificar(Integer.parseInt(idText.getText())))+"\n");

                break;
            default:
                throw new AssertionError();
        }
        
        
        
    }
    
}
