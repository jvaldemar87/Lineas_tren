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
        LinkedList<Puntos_interes> puntosInt = new LinkedList<>();
        cargarLineas(linea1, linea2, linea3, linea4, linea5, linea6, linea7, linea8);
        
        int gastoDeRuta = 99;
        int transbordos = 0;

        //Hacemos algoritmo de Floy en matrices
        //Matrices matriz = new Matrices();
        //matriz.mostrarTodos();
        //LinkedList<Integer> intermedios = new LinkedList<>();
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
            
            rs = null;
            st = null;
            selectsql = "select* from puntosinteres";
            st = con.createStatement();
            rs = st.executeQuery(selectsql);
            
            while (rs.next()) {
                Puntos_interes punto = new Puntos_interes();
                punto.setFk_estacionid(rs.getInt("fk_estacionid"));
                punto.setNombre(rs.getString("nombre"));
                
                puntosInt.add(punto);

            }

        } catch (SQLException ex) {
            Logger.getLogger(Lineas_tren.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Ordenamos lista para usar busqueda binaria
        quickSort(todasEstacionesOrdenada, 0, todasEstacionesOrdenada.size() - 1);

        //Pedimos estaciones inicio y fin
        int repetir = 1;

        do {
            /*===============================  Empieza el ciclo  ===============================*/
            Matrices matriz = new Matrices();
            gastoDeRuta = 99;
            transbordos = 0;
            LinkedList<Integer> intermedios = new LinkedList<>();
            ruta.clear();
            ruta = new LinkedList<Estaciones>();
            Scanner input = new Scanner(System.in);
            //System.out.println((char)27 + "[34;43mEjemplo de texto azul y fondo amarillo");
            System.out.println("===========================================================================");
            System.out.println("dame la estacion inicial:");
            String inicio = input.nextLine();
            Estaciones estacionInicio = new Estaciones();
            int valorRetorno1 = binarySearch(todasEstacionesOrdenada, inicio, 0, todasEstacionesOrdenada.size() - 1);
            if (valorRetorno1 != -1) {

                if (todasEstacionesOrdenada.get(valorRetorno1 - 1).getTipo() != -1) {
                    //Es una estacion de cruce

                }
                estacionInicio.setPk_estacionid(valorRetorno1);
                estacionInicio.setNombre(todasEstaciones.get(valorRetorno1 - 1).getNombre());
                estacionInicio.setLinea(todasEstaciones.get(valorRetorno1 - 1).getLinea());
                estacionInicio.setNumero(todasEstaciones.get(valorRetorno1 - 1).getNumero());
                estacionInicio.setTipo(todasEstaciones.get(valorRetorno1 - 1).getTipo());

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
                estacionFinal.setNombre(todasEstaciones.get(valorRetorno2 - 1).getNombre());
                estacionFinal.setLinea(todasEstaciones.get(valorRetorno2 - 1).getLinea());
                estacionFinal.setNumero(todasEstaciones.get(valorRetorno2 - 1).getNumero());
                estacionFinal.setTipo(todasEstaciones.get(valorRetorno2 - 1).getTipo());

            } else {
                System.out.println("No esta la estacion");
            }

            //Preguntar son misma linea o no
            Estaciones estacionActual = new Estaciones();

            //esta es la liena donde vamos atrabajar
            int lineaActual = mismaLinea(todasEstaciones, estacionInicio, estacionFinal);

            //-------------------------------- abajo de este punto es misma linea -----------------------------------
            if (lineaActual != -1) {
                //si son de la misma linea
                gastoDeRuta = 0;
                for (Estaciones estacion : todasEstaciones) {
                    if (estacionInicio.getNombre().equals(estacion.getNombre()) && lineaActual == estacion.getLinea()) {
                        estacionInicio = estacion;
                    }
                }

                for (Estaciones estacion : todasEstaciones) {
                    if (estacionFinal.getNombre().equals(estacion.getNombre()) && lineaActual == estacion.getLinea()) {
                        estacionFinal = estacion;
                    }
                }

                //Direccion recta
                if (estacionInicio.getNumero() < estacionFinal.getNumero()) {
                    int indice1 = estacionInicio.getPk_estacionid() - 1;
                    int indice2 = estacionFinal.getPk_estacionid() - 1;
                    int contadorEstaciones = 0;
                    boolean banderaFloyd = false;
                    do {
                        ruta.add(todasEstaciones.get(indice1));
                        contadorEstaciones++;
                        indice1++;
                        gastoDeRuta++;
                        if (todasEstaciones.get(indice1).getTipo() != -1) {
                            
                            do {
                                if (todasEstaciones.get(indice2).getTipo() != -1) {
                                    //Agregamos estacion inicio floyd
                                    ruta.add(contadorEstaciones, todasEstaciones.get(indice1));
                                    //Agregamos estacion fin floyd

                                    //checamos si son el mismo punto de cruce para entrar o no a Floyd
                                    if (indice1 != indice2) {
                                        //En este punto iniciamos con Floyd
                                        ruta.add(contadorEstaciones + 1, todasEstaciones.get(indice2));
                                        matriz.resuelveFloyd(todasEstaciones.get(indice1).getTipo(), todasEstaciones.get(indice2).getTipo());
                                        intermedios = matriz.getIntermedio();
                                        gastoDeRuta = gastoDeRuta + matriz.getGastoDeRuta();                                        
                                        for (int i = intermedios.size(); i > 0; i--) {
                                            for (Estaciones estacion : todasEstaciones) {
                                                if (estacion.getTipo() == intermedios.get(i - 1)) {
                                                    ruta.add(contadorEstaciones + 1, estacion);
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                    banderaFloyd = true;
                                    break;
                                }
                                ruta.add(contadorEstaciones, todasEstaciones.get(indice2));
                                indice2--;
                                gastoDeRuta++;
                            } while (todasEstaciones.get(indice2).getNumero() != estacionInicio.getNumero());
                        }
                    } while (todasEstaciones.get(indice1).getNumero() != estacionFinal.getNumero() && banderaFloyd == false);
                    if (banderaFloyd == false){
                        ruta.add(contadorEstaciones, todasEstaciones.get(indice1));                        
                    }
                    //Direccion reversa
                } else {
                    
                    int indice1 = estacionInicio.getPk_estacionid() - 1;
                    int indice2 = estacionFinal.getPk_estacionid() - 1;
                    int contadorEstaciones = 0;
                    boolean banderaFloyd = false;
                    do {
                        ruta.add(todasEstaciones.get(indice1));
                        contadorEstaciones++;
                        indice1--;
                        gastoDeRuta++;
                        if (todasEstaciones.get(indice1).getTipo() != -1) {
                            do {
                                if (todasEstaciones.get(indice2).getTipo() != -1) {
                                    //Agregamos estacion inicio floyd
                                    ruta.add(contadorEstaciones, todasEstaciones.get(indice1));
                                    //Agregamos estacion fin floyd

                                    //checamos si son el mismo punto de cruce para entrar o no a Floyd
                                    if (indice1 != indice2) {
                                        //En este punto iniciamos con Floyd
                                        ruta.add(contadorEstaciones + 1, todasEstaciones.get(indice2));
                                        matriz.resuelveFloyd(todasEstaciones.get(indice1).getTipo(), todasEstaciones.get(indice2).getTipo());
                                        intermedios = matriz.getIntermedio();
                                        gastoDeRuta = gastoDeRuta + matriz.getGastoDeRuta();
                                        for (int i = intermedios.size(); i > 0; i--) {

                                            for (Estaciones estacion : todasEstaciones) {

                                                if (estacion.getTipo() == intermedios.get(i - 1)) {
                                                    ruta.add(contadorEstaciones + 1, estacion);
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                    banderaFloyd = true;
                                    break;
                                }
                                ruta.add(contadorEstaciones, todasEstaciones.get(indice2));
                                indice2++;
                                gastoDeRuta++;
                            } while (todasEstaciones.get(indice2).getNumero() != estacionInicio.getNumero());
                        }
                    } while (todasEstaciones.get(indice1).getNumero() != estacionFinal.getNumero() && banderaFloyd == false);
                      if (banderaFloyd == false){
                        ruta.add(contadorEstaciones, todasEstaciones.get(indice1));                        
                    }
                }
            } //---------------------------------------- arriba de este punto es para misma linea -----------------------------------------
            else {
                // no son de la misma linea

                Boolean[] banderaRutaInicio = {false, false};
                Boolean[] banderaRutaFinal = {false, false};

                int indice1 = estacionInicio.getPk_estacionid() - 1;
                int indice2 = estacionFinal.getPk_estacionid() - 1;

                LinkedList<Estaciones> inicioIzq = new LinkedList<>();
                LinkedList<Estaciones> inicioDer = new LinkedList<>();

                LinkedList<Estaciones> finalIzq = new LinkedList<>();
                LinkedList<Estaciones> finalDer = new LinkedList<>();

                //------ inicio -----------
                //inicio hacia la izquierda en la ruta

                while ((indice1 >= 0 && indice1 < todasEstaciones.size()  ) && (estacionInicio.getLinea() == todasEstaciones.get(indice1).getLinea()) ) {
                    //Si la estacion es un punto de cruce
                    if (todasEstaciones.get(indice1).getTipo() != -1) {
                        banderaRutaInicio[0] = true;
                        
                        break;
                    }

                    inicioIzq.add(todasEstaciones.get(indice1));
                    indice1--;
                }

                if(banderaRutaInicio[0]) inicioIzq.add(todasEstaciones.get(indice1));
                
                
                indice1 = estacionInicio.getPk_estacionid() - 1;
                
                
                while ((indice1 >= 0 && indice1 < todasEstaciones.size()   ) && (estacionInicio.getLinea() == todasEstaciones.get(indice1).getLinea()) ) {
                    //Si la estacion es un punto de cruce
                    if (todasEstaciones.get(indice1).getTipo() != -1) {
                        banderaRutaInicio[1] = true;
                        
                        break;
                    }

                    inicioDer.add(todasEstaciones.get(indice1));
                    indice1++;
                }
                
                if(banderaRutaInicio[1]) inicioDer.add(todasEstaciones.get(indice1));

                //------- fin -----------
                while ((indice2 >= 0 && indice2 < todasEstaciones.size() ) && (estacionFinal.getLinea() == todasEstaciones.get(indice2).getLinea()) ) {

                    //Si la estacion es un punto de cruce
                    if (todasEstaciones.get(indice2).getTipo() != -1) {

                        banderaRutaFinal[0] = true;
                        break;
                    }

                    finalIzq.add(0, todasEstaciones.get(indice2));
                    indice2--;
                }
                if(banderaRutaFinal[0])finalIzq.add(0, todasEstaciones.get(indice2));
                
                
                indice2 = estacionFinal.getPk_estacionid() - 1;
               
                while ((indice2 >= 0 && indice2 < todasEstaciones.size() ) && (estacionFinal.getLinea() == todasEstaciones.get(indice2).getLinea()) ) {
              
                    //Si la estacion es un punto de cruce
                    if (todasEstaciones.get(indice2).getTipo() != -1) {
                        
                        banderaRutaFinal[1] = true;
                        break;
                    }

                    finalDer.add(0, todasEstaciones.get(indice2));
                    
                    indice2++;
                }
                if(banderaRutaFinal[1]) finalDer.add(0, todasEstaciones.get(indice2));
            
                
                ///////////////////aqui comparamos las rutas para saber cuales son reales
                //Inicio Izquierda   -     Fin Izquierda
                LinkedList<Estaciones> rutaTemp = new LinkedList<>();
                int gastoTemp = 0;

                if (banderaRutaInicio[0] == true && banderaRutaFinal[0] == true) {

                    if (inicioIzq.getLast().getTipo() == finalIzq.getFirst().getTipo()) {
                        inicioIzq.removeLast();

                        for (Estaciones estacion : inicioIzq) {
                            rutaTemp.add(estacion);
                            gastoTemp++;
                        }
                        gastoTemp--;

                        for (Estaciones estacion : finalIzq) {
                            rutaTemp.add(estacion);
                            gastoTemp++;
                        }

                        if (gastoTemp < gastoDeRuta) {
                            ruta = rutaTemp;
                            gastoDeRuta = gastoTemp;
                        }

                    } else {

                        for (Estaciones estacion : inicioIzq) {
                            rutaTemp.add(estacion);
                            gastoTemp++;
                        }
                        gastoTemp--;

                        matriz.resuelveFloyd(inicioIzq.getLast().getTipo(), finalIzq.getFirst().getTipo());
                        LinkedList<Integer> inter = new LinkedList<>();
                        inter = matriz.getIntermedio();
                        for (int pMedio : inter) {
                            for (Estaciones estacion : todasEstaciones) {
                                if (pMedio == estacion.getTipo()) {
                                    rutaTemp.add(estacion);
                                    break;
                                }
                            }
                        }
                        for (Estaciones estacion : finalIzq) {
                            rutaTemp.add(estacion);
                            gastoTemp++;
                        }
                        gastoTemp--;

                        gastoTemp = gastoTemp + matriz.getGastoDeRuta();

                        if (gastoTemp < gastoDeRuta) {
                            ruta = rutaTemp;
                            gastoDeRuta = gastoTemp;
                        }
                    }
                }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                matriz = new Matrices();
                rutaTemp = new LinkedList<>();
                gastoTemp = 0;

                if (banderaRutaInicio[0] == true && banderaRutaFinal[1] == true) {

                    if (inicioIzq.getLast().getTipo() == finalDer.getFirst().getTipo()) {
                        inicioIzq.removeLast();

                        for (Estaciones estacion : inicioIzq) {
                            rutaTemp.add(estacion);
                            gastoTemp++;
                        }
                        gastoTemp--;

                        for (Estaciones estacion : finalDer) {
                            rutaTemp.add(estacion);
                            gastoTemp++;
                        }

                        if (gastoTemp < gastoDeRuta) {
                            ruta = rutaTemp;
                            gastoDeRuta = gastoTemp;
                        }

                    } else {

                        for (Estaciones estacion : inicioIzq) {
                            rutaTemp.add(estacion);
                            gastoTemp++;
                        }
                        gastoTemp--;

                        matriz.resuelveFloyd(inicioIzq.getLast().getTipo(), finalDer.getFirst().getTipo());
                        LinkedList<Integer> inter = new LinkedList<>();
                        inter = matriz.getIntermedio();
                        for (int pMedio : inter) {
                            for (Estaciones estacion : todasEstaciones) {
                                if (pMedio == estacion.getTipo()) {
                                    rutaTemp.add(estacion);
                                    break;
                                }
                            }
                        }
                        for (Estaciones estacion : finalDer) {
                            rutaTemp.add(estacion);
                            gastoTemp++;
                        }
                        gastoTemp--;

                        gastoTemp = gastoTemp + matriz.getGastoDeRuta();

                        if (gastoTemp < gastoDeRuta) {
                            ruta = rutaTemp;
                            gastoDeRuta = gastoTemp;
                        }
                    }

                }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                matriz = new Matrices();
                rutaTemp = new LinkedList<>();
                gastoTemp = 0;

                if (banderaRutaInicio[1] == true && banderaRutaFinal[0] == true) {

                    if (inicioDer.getLast().getTipo() == finalIzq.getFirst().getTipo()) {
                        inicioDer.removeLast();

                        for (Estaciones estacion : inicioDer) {
                            rutaTemp.add(estacion);
                            gastoTemp++;
                        }
                        gastoTemp--;

                        for (Estaciones estacion : finalIzq) {
                            rutaTemp.add(estacion);
                            gastoTemp++;
                        }

                        if (gastoTemp < gastoDeRuta) {
                            ruta = rutaTemp;
                            gastoDeRuta = gastoTemp;
                        }

                    } else {

                        for (Estaciones estacion : inicioDer) {
                            rutaTemp.add(estacion);
                            gastoTemp++;
                        }
                        gastoTemp--;

                        matriz.resuelveFloyd(inicioDer.getLast().getTipo(), finalIzq.getFirst().getTipo());
                        LinkedList<Integer> inter = new LinkedList<>();
                        inter = matriz.getIntermedio();
                        for (int pMedio : inter) {
                            for (Estaciones estacion : todasEstaciones) {
                                if (pMedio == estacion.getTipo()) {
                                    rutaTemp.add(estacion);
                                    break;
                                }
                            }
                        }
                        for (Estaciones estacion : finalIzq) {
                            rutaTemp.add(estacion);
                            gastoTemp++;
                        }
                        gastoTemp--;

                        gastoTemp = gastoTemp + matriz.getGastoDeRuta();

                        if (gastoTemp < gastoDeRuta) {
                            ruta = rutaTemp;
                            gastoDeRuta = gastoTemp;
                        }
                    }
                }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                matriz = new Matrices();
                rutaTemp = new LinkedList<>();
                gastoTemp = 0;
                if (banderaRutaInicio[1] == true && banderaRutaFinal[1] == true) {

                    if (inicioDer.getLast().getTipo() == finalDer.getFirst().getTipo()) {
                        inicioDer.removeLast();

                        for (Estaciones estacion : inicioDer) {
                            rutaTemp.add(estacion);
                            gastoTemp++;
                        }

                        for (Estaciones estacion : finalDer) {
                            rutaTemp.add(estacion);
                            gastoTemp++;
                        }
                        gastoTemp--;

                        if (gastoTemp < gastoDeRuta) {
                            ruta = rutaTemp;
                            gastoDeRuta = gastoTemp;
                        }

                    } else {

                        for (Estaciones estacion : inicioDer) {
                            rutaTemp.add(estacion);
                            gastoTemp++;
                        }
                        gastoTemp--;

                        matriz.resuelveFloyd(inicioDer.getLast().getTipo(), finalDer.getFirst().getTipo());
                        LinkedList<Integer> inter = new LinkedList<>();
                        inter = matriz.getIntermedio();
                        for (int pMedio : inter) {
                            for (Estaciones estacion : todasEstaciones) {
                                if (pMedio == estacion.getTipo()) {
                                    rutaTemp.add(estacion);
                                    break;
                                }
                            }
                        }
                        for (Estaciones estacion : finalDer) {
                            rutaTemp.add(estacion);
                            gastoTemp++;
                        }
                        gastoTemp--;

                        gastoTemp = gastoTemp + matriz.getGastoDeRuta();

                        if (gastoTemp < gastoDeRuta) {
                            ruta = rutaTemp;
                            gastoDeRuta = gastoTemp;
                        }
                    }
                }
            }
           

            imprimeRuta(ruta, todasEstaciones, linea1, linea2, linea3, linea4, linea5, linea6, linea7, linea8, puntosInt, gastoDeRuta, transbordos);
            
            System.out.println("===========================================================================");
            System.out.println("Quieres hacer otra ruta?");
            System.out.println("SI = 1 \tNO = 0");
            Scanner repeticion = new Scanner(System.in);
            repetir = repeticion.nextInt();
            for (int i = 0; i < 50; i++) {
                System.out.println("");
            }

        } while (repetir != 0);
        /*===============================  Termina el ciclo  ===============================*/
        
        ////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////
    }

    static void imprimirEstaciones(LinkedList<Estaciones> lista) {
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
            return list.get(mid).getPk_estacionid();
        } else if (list.get(mid).getNombre().compareTo(value) > 0) {
            return binarySearch(list, value, min, mid - 1);
        } else {
            return binarySearch(list, value, mid + 1, max);
        }
    }

    private static void imprimeRuta(
            LinkedList<Estaciones> ruta,
            LinkedList<Estaciones> todasEstaciones,
            LinkedList<Estaciones> linea1,
            LinkedList<Estaciones> linea2,
            LinkedList<Estaciones> linea3,
            LinkedList<Estaciones> linea4,
            LinkedList<Estaciones> linea5,
            LinkedList<Estaciones> linea6,
            LinkedList<Estaciones> linea7,
            LinkedList<Estaciones> linea8,
            LinkedList<Puntos_interes> puntosInt,
            int gastoDeRuta,
            int transbordos) {
        
        //aqui hacemos la comparacion de nuestras listas para obtener nuestros puntos de cruce
        for (int i = 0; i < ruta.size(); i++) {
            if(ruta.get(i).getTipo() != -1){
                if(i+1 < ruta.size()){
                    if(ruta.get(i+1).getTipo() != -1){
                        LinkedList<Estaciones> listaLocal1 = new LinkedList<>();
                        LinkedList<Estaciones> listaLocal2 = new LinkedList<>();
                        listaLocal1.clear();
                        listaLocal2.clear();
                        //aqui llenamos nuestras listas locales
                        for (int j = 0; j < todasEstaciones.size(); j++) {
                            if(ruta.get(i).getTipo() == todasEstaciones.get(j).getTipo()){
                                listaLocal1.add(todasEstaciones.get(j));
                            }
                            if(ruta.get(i+1).getTipo() == todasEstaciones.get(j).getTipo()){
                                listaLocal2.add(todasEstaciones.get(j));
                            }
                        }
                        //aqui comparamos nuestras listas locales para determinar en que direccion va a viajar
                        for (int j = 0; j < listaLocal1.size(); j++) {
                            for (int k = 0; k < listaLocal2.size(); k++) {
                                if(listaLocal1.get(j).getLinea() == listaLocal2.get(k).getLinea()){
                                    if(listaLocal1.get(j).getNumero() < listaLocal2.get(k).getNumero()){
                                        int indiceLocal=listaLocal1.get(j).getPk_estacionid()-1;
                                        while(indiceLocal != listaLocal2.get(k).getPk_estacionid()-2){
                                            indiceLocal++;
                                            ruta.add(i+1,todasEstaciones.get(indiceLocal));
                                            
                                            //aqui crecemos el avance de nuestra ruta
                                            i++;
                                        }
                                    }
                                    else{
                                        int indiceLocal=listaLocal1.get(j).getPk_estacionid()-1;
                                        while(indiceLocal != listaLocal2.get(k).getPk_estacionid()){
                                            indiceLocal--;                       
                                            ruta.add(i+1,todasEstaciones.get(indiceLocal));
                                            
                                            //aqui crecemos el avance de nuestra ruta
                                            i++;
                                        }
                                    }
                                }
                            }
                        }
                        
                    }
                }
            }
        }
        

        int posicion1 = 0;
        int posicion2 = 1;
        int sigLinea = 0;
        int lineActual = 0;
        boolean banderaCambioRuta = false;
        LinkedList<Estaciones> listaEstacionPosicion1 = new LinkedList<>();
        LinkedList<Estaciones> listaEstacionPosicion2 = new LinkedList<>();

        
        for (Estaciones estacion : todasEstaciones) {
            if (ruta.get(posicion1).getNombre().equals(estacion.getNombre())) {
                listaEstacionPosicion1.add(estacion);
            }
        }

        for (Estaciones estacion : todasEstaciones) {
            if (ruta.get(posicion2).getNombre().equals(estacion.getNombre())) {
                listaEstacionPosicion2.add(estacion);
            }
        }

        for (Estaciones estacion1 : listaEstacionPosicion1) {
            for (Estaciones estacion2 : listaEstacionPosicion2) {
                if (estacion1.getLinea() == estacion2.getLinea()) {
                    ruta.set(0, estacion1);
                    ruta.set(1, estacion2);
                    sigLinea = estacion1.getLinea();
                    lineActual = estacion1.getLinea();
                }
            }
        }
        
        
//        System.out.print((char)27 + "[34;46mTomar la linea: " + sigLinea + ", estacion " + ruta.get(posicion1).getNombre() + " con direccion: ");
//        System.out.print("Tomar la linea:");
//        System.out.println((char)27 + "[34;46m"+ sigLinea);
//        System.out.print(", estacion");
//        System.out.println((char)27 + "[34;46m"+ ruta.get(posicion1).getNombre());
//        System.out.print(" con direccion: ");
        System.out.print("Tomar la linea: " + sigLinea + ", estacion " + ruta.get(posicion1).getNombre() + " con direccion: ");
        System.out.println(direccion(linea1, linea2, linea3, linea4, linea5, linea6, linea7, linea8, sigLinea, ruta.get(posicion1).getNumero(), ruta.get(posicion2).getNumero()));

        posicion1++;
        posicion2++;

        
        System.out.println("Pasaras por las estaciones: ");
        for (int i = 1; i < ruta.size() -1; i++) {
            listaEstacionPosicion1 = new LinkedList<>();
            listaEstacionPosicion2 = new LinkedList<>();

            
            for (Estaciones estacion : todasEstaciones) {
                if (ruta.get(posicion1).getNombre().equals(estacion.getNombre())) {
                    listaEstacionPosicion1.add(estacion);
                }
            }

            for (Estaciones estacion : todasEstaciones) {
                if (ruta.get(posicion2).getNombre().equals(estacion.getNombre())) {
                    listaEstacionPosicion2.add(estacion);
                }
            }

            for (Estaciones estacion1 : listaEstacionPosicion1) {
                for (Estaciones estacion2 : listaEstacionPosicion2) {
                        
                      
                    if (estacion1.getLinea() == estacion2.getLinea()) {
                        
                        ruta.set(i, estacion1);
                        ruta.set(i + 1, estacion2);
                        sigLinea = estacion1.getLinea();
                    }
                }
            }
            
            
//            if(banderaCambioRuta = true)
//                System.out.println("pasara por las estaciones:");
                
            System.out.println("* " + ruta.get(posicion1).getNombre());
            
            for(Puntos_interes punto:puntosInt){
                if(punto.getFk_estacionid() == ruta.get(posicion1).getPk_estacionid()){
                    System.out.println("\t -"+punto.getNombre());
                }
            }
            //aqui es donde haciemos el transbordo
            if(lineActual != sigLinea){
                System.out.println("Bajar en la estacion " + ruta.get(posicion1).getNombre());
                System.out.print("Transbordar a la linea " + sigLinea + " con dirección ");
                System.out.println(direccion(linea1, linea2, linea3, linea4, linea5, linea6, linea7, linea8, sigLinea, ruta.get(posicion1).getNumero(), ruta.get(posicion2).getNumero()));
                transbordos++;
                lineActual = sigLinea;
                banderaCambioRuta = true;
            }
            else{
                banderaCambioRuta = false;
            }
            
            
            posicion1++;
            posicion2++;
            
        }
        //posicion1++;
        System.out.println("* " + ruta.get(posicion1).getNombre());

        System.out.println("Bajar en la estacion " + ruta.get(posicion1).getNombre());
        System.out.println("Has llegado a tu destino");
        System.out.println("Tiempo estimado: " + gastoDeRuta);
        System.out.println("Número de transbordos: " + transbordos);
    }

    private static String direccion(
            LinkedList<Estaciones> linea1,
            LinkedList<Estaciones> linea2,
            LinkedList<Estaciones> linea3,
            LinkedList<Estaciones> linea4,
            LinkedList<Estaciones> linea5,
            LinkedList<Estaciones> linea6,
            LinkedList<Estaciones> linea7,
            LinkedList<Estaciones> linea8, int linea, int inicio, int fin) {
        String direccion = null;
        
        if (linea == 1) {
            if (inicio < fin) {
                direccion = linea1.getLast().getNombre();
            } else {
                direccion = linea1.getFirst().getNombre();
            }
        }
        if (linea == 2) {
            if (inicio < fin) {
                direccion = linea2.getLast().getNombre();
            } else {
                direccion = linea2.getFirst().getNombre();
            }
        }
        if (linea == 3) {
            if (inicio < fin) {
                direccion = linea3.getLast().getNombre();
            } else {
                direccion = linea3.getFirst().getNombre();
            }
        }
        if (linea == 4) {
            if (inicio < fin) {
                direccion = linea4.getLast().getNombre();
            } else {
                direccion = linea4.getFirst().getNombre();
            }
        }
        if (linea == 5) {
            if (inicio < fin) {
                direccion = linea5.getLast().getNombre();
            } else {
                direccion = linea5.getFirst().getNombre();
            }
        }
        if (linea == 6) {
            if (inicio < fin) {
                direccion = linea6.getLast().getNombre();
            } else {
                direccion = linea6.getFirst().getNombre();
            }
        }
        if (linea == 7) {
            if (inicio < fin) {               
                direccion = linea7.getLast().getNombre();
            } else {
                direccion = linea7.getFirst().getNombre();
            }
        }
        if (linea == 8) {
            if (inicio < fin) {
                direccion = linea8.getLast().getNombre();
            } else {
                direccion = linea8.getFirst().getNombre();
            }
        }
        return direccion;
    }

    //aqui voy a comprobar si estan o no en la misma linea
    private static int mismaLinea(LinkedList<Estaciones> lista, Estaciones inicio, Estaciones fin) {
        Vector<Estaciones> estacionesInicioVector = new Vector<>();
        Vector<Estaciones> estacionesFinVector = new Vector<>();
        int mismaLinea = -1;

        for (Estaciones estacion : lista) {
            if (inicio.getNombre().equals(estacion.getNombre())) {
                estacionesInicioVector.add(estacion);
            }
        }

        for (Estaciones estacion : lista) {
            if (fin.getNombre().equals(estacion.getNombre())) {
                estacionesFinVector.add(estacion);
            }
        }

        for (Estaciones vector1 : estacionesInicioVector) {
            for (Estaciones vector2 : estacionesFinVector) {
                if (vector1.getLinea() == vector2.getLinea()) {
                    mismaLinea = vector1.getLinea();
                }
            }
        }
        return mismaLinea;
    }
}
