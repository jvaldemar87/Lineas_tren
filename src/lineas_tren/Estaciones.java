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
public class Estaciones {
    private int pk_estacionid;
    private int linea;
    private int numero;
    private String nombre;
    private int tipo;

    public Estaciones() {
    }

    public Estaciones(int pk_estacionid, int linea, int numero, String nombre, int tipo) {
        this.pk_estacionid = pk_estacionid;
        this.linea = linea;
        this.numero = numero;
        this.nombre = nombre;
        this.tipo = tipo;
    }

    public int getPk_estacionid() {
        return pk_estacionid;
    }

    public int getLinea() {
        return linea;
    }

    public int getNumero() {
        return numero;
    }

    public String getNombre() {
        return nombre;
    }

    public int getTipo() {
        return tipo;
    }

    public void setPk_estacionid(int pk_estacionid) {
        this.pk_estacionid = pk_estacionid;
    }

    public void setLinea(int linea) {
        this.linea = linea;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
}
