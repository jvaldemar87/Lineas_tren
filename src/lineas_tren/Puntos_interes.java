/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lineas_tren;

/**
 *
 * @author jvald
 */



public class Puntos_interes {
    
    private int fk_estacionid;
    private String nombre;

    public Puntos_interes() {
    }

    public Puntos_interes(int fk_estacionid, String nombre) {
        this.fk_estacionid = fk_estacionid;
        this.nombre = nombre;
    }

    public int getFk_estacionid() {
        return fk_estacionid;
    }

    public String getNombre() {
        return nombre;
    }

    public void setFk_estacionid(int fk_estacionid) {
        this.fk_estacionid = fk_estacionid;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
    
}
