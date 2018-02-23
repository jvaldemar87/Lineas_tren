/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lineas_tren;

import ClassFiles.Connector;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jvald
 */
public class Lineas_tren {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Cargar toddas las estaciones
        LinkedList<Estaciones> todasEstacionesOrdenada = new LinkedList<>();
        LinkedList<Estaciones> todasEstaciones = new LinkedList<>();
        LinkedList<Estaciones> linea1 = new LinkedList<>();
        LinkedList<Estaciones> linea2 = new LinkedList<>();
        LinkedList<Estaciones> linea3 = new LinkedList<>();
        LinkedList<Estaciones> linea4 = new LinkedList<>();
        LinkedList<Estaciones> linea5 = new LinkedList<>();
        LinkedList<Estaciones> linea6 = new LinkedList<>();
        LinkedList<Estaciones> linea7 = new LinkedList<>();
        LinkedList<Estaciones> linea8 = new LinkedList<>();
        LinkedList<Estaciones> ruta = new LinkedList<>();
        cargarLineas(linea1, linea2, linea3, linea4, linea5, linea6, linea7, linea8);
        
        //Hacemos algoritmo de Floy en matrices
        Matrices matriz = new Matrices();
        
        //matriz.mostrarTodos();
        
        
        LinkedList<Integer> intermedios = new LinkedList<>();
        
       
        
        //Cargando todas las estaciones y agregandolas a la LISTA que se llama todasEstaciones
        try {
            Connection con = Connector.getInstance().getConnection();

            ResultSet rs = null;
            Statement st = null;
            String selectsql = "select* from estaciones";
            st = con.createStatement();
            rs = st.executeQuery(selectsql);

            while (rs.next()) {
                Estaciones estacion = new Estaciones();
                estacion.setPk_estacionid(rs.getInt("pk_estacionid"));
                estacion.setLinea(rs.getInt("linea"));
                estacion.setNumero(rs.getInt("numero"));
                estacion.setNombre(rs.getString("nombre"));
                estacion.setTipo(rs.getInt("tipo"));
                todasEstacionesOrdenada.add(estacion);
                todasEstaciones.add(estacion);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Lineas_tren.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Ordenamos lista para usar busqueda binaria
        
        quickSort(todasEstacionesOrdenada, 0, todasEstacionesOrdenada.size() - 1);  
        
        //Pedimos estaciones inicio y fin
        
        Scanner input = new Scanner(System.in);
        System.out.println("dame la estacion inicial:");
        String inicio = input.nextLine();
        Estaciones estacionInicio = new Estaciones();
        int valorRetorno1 = binarySearch(todasEstacionesOrdenada, inicio, 0, todasEstacionesOrdenada.size() - 1);
        if (valorRetorno1 != -1) {
            
            if (todasEstacionesOrdenada.get(valorRetorno1 - 1).getTipo() != -1) {
               //Es una estacion de cruce
              
               
               
            }
            estacionInicio.setPk_estacionid(valorRetorno1);
            estacionInicio.setNombre(todasEstaciones.get(valorRetorno1-1).getNombre());
            estacionInicio.setLinea(todasEstaciones.get(valorRetorno1-1).getLinea());
            estacionInicio.setNumero(todasEstaciones.get(valorRetorno1-1).getNumero());
            estacionInicio.setTipo(todasEstaciones.get(valorRetorno1-1).getTipo());   
            
        } else {
            System.out.println("No esta la estacion");
        }

        
        
        
        System.out.println("Dame linea Final");
        String fin = input.nextLine();
        Estaciones estacionFinal = new Estaciones();
        int valorRetorno2 = binarySearch(todasEstacionesOrdenada, fin, 0, todasEstacionesOrdenada.size() - 1);
        if (valorRetorno2 != -1) {
            
            if (todasEstacionesOrdenada.get(valorRetorno2 - 1).getTipo() != -1) {
               //Es una estacion de cruce
              
               
               
            }
            estacionFinal.setPk_estacionid(valorRetorno2);
            estacionFinal.setNombre(todasEstaciones.get(valorRetorno2-1).getNombre());
            estacionFinal.setLinea(todasEstaciones.get(valorRetorno2-1).getLinea());
            estacionFinal.setNumero(todasEstaciones.get(valorRetorno2-1).getNumero());
            estacionFinal.setTipo(todasEstaciones.get(valorRetorno2-1).getTipo());

        } else {
            System.out.println("No esta la estacion");
        }

        
        //Preguntar son misma linea o no
        Estaciones estacionActual = new Estaciones();
        
        //esta es la liena donde vamos atrabajar
        int lineaActual = mismaLinea(todasEstaciones, estacionInicio, estacionFinal);
        
        //-------------------------------- abajo de este punto es misma linea -----------------------------------
        if(lineaActual != -1){
            //si son de la misma linea
            for(Estaciones estacion : todasEstaciones) {
                if(estacionInicio.getNombre().equals(estacion.getNombre()) && lineaActual == estacion.getLinea()) {
                    estacionInicio = estacion;
                }                 
            }
            
            for(Estaciones estacion : todasEstaciones){
                if(estacionFinal.getNombre().equals(estacion.getNombre()) && lineaActual == estacion.getLinea()){
                    estacionFinal = estacion;
                }
            }
            
            
            
            //Direccion recta
            if(estacionInicio.getNumero() < estacionFinal.getNumero()){
                int indice1 = estacionInicio.getPk_estacionid() - 1;
                int indice2 = estacionFinal.getPk_estacionid() -1;
                int contadorEstaciones = 0;
                boolean banderaFloyd = false;
                do {
                    ruta.add(todasEstaciones.get(indice1));
                    contadorEstaciones++;
                    indice1++;
                    if(todasEstaciones.get(indice1).getTipo() != -1){
                        do { 
                            if(todasEstaciones.get(indice2).getTipo() != -1){
                                //Agregamos estacion inicio floyd
                                ruta.add(contadorEstaciones,todasEstaciones.get(indice1));
                                //Agregamos estacion fin floyd
                                ruta.add(contadorEstaciones +1,todasEstaciones.get(indice2));
                                //En este punto iniciamos con Floyd
                                matriz.resuelveFloyd(todasEstaciones.get(indice1).getTipo(), todasEstaciones.get(indice2).getTipo());
                                matriz.imprimeIntermedios();
                                intermedios = matriz.getIntermedio();
                                for (int valor : intermedios) {
                                    for(Estaciones estacion: todasEstaciones){
                                        if(estacion.getTipo() == valor){
                                            ruta.add(contadorEstaciones +1, estacion);
                                            break;
                                        }
                                    }
                                }
                                banderaFloyd = true;
                                break;
                            }
                            ruta.add(contadorEstaciones, todasEstaciones.get(indice2));
                            indice2--;
                        } while (todasEstaciones.get(indice2).getNumero() != estacionInicio.getNumero());   
                    }
                } while (todasEstaciones.get(indice1).getNumero() != estacionFinal.getNumero() && banderaFloyd == false);
            //Direccion reversa
            } else {/*
                int indice1 = estacionInicio.getPk_estacionid() -1;
                int indice2 = estacionInicio.getPk_estacionid() -1;
                int contadorEstaciones = 0;
                boolean banderaFloyd = false;
                do {                    
                    ruta.add(todasEstaciones.get(indice1));
                    contadorEstaciones++;
                    indice1--;
                    if(todasEstaciones.get(indice1).getTipo() != -1){
                        do {                            
                            if(todasEstaciones.get(indice2).getTipo() != -1){
                                ruta.add(contadorEstaciones,todasEstaciones.get(indice1));
                                ruta.add(contadorEstaciones-1,todasEstaciones.get(indice2));
                                matriz.resuelveFloyd(todasEstaciones.get(indice1).getTipo(), todasEstaciones.get(indice2).getTipo());
                                matriz.imprimeIntermedios();
                                intermedios = matriz.getIntermedio();
                                for (int valor : intermedios) {
                                    for (Estaciones estacion: todasEstaciones) {
                                        if(estacion.getTipo() == valor){
                                            ruta.add(contadorEstaciones-1,estacion);
                                            break;
                                        }
                                    }
                                }
                                banderaFloyd = true;
                                break;
                            }
                            ruta.add(contadorEstaciones, todasEstaciones.get(indice2));
                            indice2++;
                        } while (todasEstaciones.get(indice2).getNumero() != estacionInicio.getNumero());
                    }
                } while (todasEstaciones.get(indice1).getNumero() != estacionFinal.getNumero() && banderaFloyd == false);
            */}
        }
        
        
        //---------------------------------------- arriba de este punto es para misma linea -----------------------------------------
        else{
            // si no son de la misma linea
        }
        
        
        
        
        for(Estaciones estacion : ruta){
            System.out.print("Estacion: " + estacion.getNombre() + "\t \t");
            System.out.print("Numero: " + estacion.getNumero() + "\t");
            System.out.println("Linea: " + estacion.getLinea());
        }
    }
    
    
    static void imprimirEstaciones(LinkedList<Estaciones> lista){
            for (Estaciones estacion : lista) {
            System.out.print(estacion.getPk_estacionid() + " ");
            System.out.print(estacion.getNombre() + " ");
            System.out.print(estacion.getLinea() + " ");
            System.out.print(estacion.getNumero() + " ");
            System.out.println(estacion.getTipo());
        }

    }

    static void cargarLineas(
            LinkedList<Estaciones> linea1,
            LinkedList<Estaciones> linea2,
            LinkedList<Estaciones> linea3,
            LinkedList<Estaciones> linea4,
            LinkedList<Estaciones> linea5,
            LinkedList<Estaciones> linea6,
            LinkedList<Estaciones> linea7,
            LinkedList<Estaciones> linea8) {
        String[] lineaListas = {"1", "2", "3", "4", "5", "6", "7", "8"};
        for (int i = 0; i < lineaListas.length; i++) {
            try {
                ResultSet rs = null;
                Statement st = null;
                String selectsql = "select * from estaciones where linea = " + lineaListas[i];
                Connection con = Connector.getInstance().getConnection();

                st = con.createStatement();
                rs = st.executeQuery(selectsql);
                while (rs.next()) {
                    Estaciones estacion = new Estaciones();
                    estacion.setPk_estacionid(rs.getInt("pk_estacionid"));
                    estacion.setLinea(rs.getInt("linea"));
                    estacion.setNumero(rs.getInt("numero"));
                    estacion.setNombre(rs.getString("nombre"));
                    estacion.setTipo(rs.getInt("tipo"));
                    if (i == 0) {
                        linea1.add(estacion);
                    }
                    if (i == 1) {
                        linea2.add(estacion);
                    }
                    if (i == 2) {
                        linea3.add(estacion);
                    }
                    if (i == 3) {
                        linea4.add(estacion);
                    }
                    if (i == 4) {
                        linea5.add(estacion);
                    }
                    if (i == 5) {
                        linea6.add(estacion);
                    }
                    if (i == 6) {
                        linea7.add(estacion);
                    }
                    if (i == 7) {
                        linea8.add(estacion);
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(Lineas_tren.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
    //aqui se ordena de forma alfabetica todasEstaciones

    private static void quickSort(LinkedList<Estaciones> list, int start, int end) {
        int i = start;
        int k = end;
        if (end - start >= 1) {
            Estaciones pivot = list.get(start);
            while (k > i) {
                while (list.get(i).getNombre().compareTo(pivot.getNombre()) <= 0 && i <= end && k > i) {
                    i++;
                }
                while (list.get(k).getNombre().compareTo(pivot.getNombre()) > 0 && k >= start && k >= i) {
                    k--;
                }
                if (k > i) {
                    swap(list, i, k);
                }
            }
            swap(list, start, k);
            quickSort(list, start, k - 1);
            quickSort(list, k + 1, end);
        }
    }

    private static void swap(LinkedList<Estaciones> list, int index1, int index2) {
        Estaciones temp = list.get(index1);
        list.set(index1, list.get(index2));
        list.set(index2, temp);
    }

    //estafuncion Busca dentro de lista todasEstaciones
    private static int binarySearch(LinkedList<Estaciones> list, String value, int min, int max) {
        if (min > max) {
            return -1;
        }

        int mid = (max + min) / 2;

        if (list.get(mid).getNombre().equals(value)) {
            System.out.println("Estacion ID: " + list.get(mid).getPk_estacionid());
            System.out.println("Tipo: " + list.get(mid).getTipo());
            return list.get(mid).getPk_estacionid();
        } else if (list.get(mid).getNombre().compareTo(value) > 0) {
            return binarySearch(list, value, min, mid - 1);
        } else {
            return binarySearch(list, value, mid + 1, max);
        }
    }

    //aqui voy a comprobar si estan o no en la misma linea
    private static int mismaLinea(LinkedList<Estaciones> lista, Estaciones inicio, Estaciones fin) {
        Vector<Estaciones> estacionesInicioVector = new Vector<>();
        Vector<Estaciones> estacionesFinVector = new Vector<>();
        int mismaLinea = -1;
        
        
        for (Estaciones estacion : lista) {
            if(inicio.getNombre().equals(estacion.getNombre())){
                estacionesInicioVector.add(estacion);
            }
        }
        
        for (Estaciones estacion : lista){
            if(fin.getNombre().equals(estacion.getNombre())){

                estacionesFinVector.add(estacion);
            }
        }
        
        for(Estaciones vector1 : estacionesInicioVector){
            for(Estaciones vector2 : estacionesFinVector){
                if(vector1.getLinea() == vector2.getLinea()){
                    mismaLinea = vector1.getLinea();
                }
            }
        }
        return mismaLinea;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
