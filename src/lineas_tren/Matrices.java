/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lineas_tren;

import java.util.LinkedList;

/**
 *
 * @author jvald
 */
public class Matrices {

    private int[][] matrizM;
    private int[][] matrizT;
    LinkedList<Integer> intermedio;
    int gastoDeRuta;

    public Matrices() {
        this.gastoDeRuta = 0;
        this.intermedio = new LinkedList<>();
        this.matrizM = new int[][]{
            {0, 1, 99, 2, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99},
            {1, 0, 2, 2, 99, 99, 7, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99},
            {99, 2, 0, 3, 2, 99, 99, 5, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99},
            {2, 2, 3, 0, 2, 99, 99, 99, 99, 3, 99, 99, 99, 99, 99, 99, 99, 99},
            {99, 99, 2, 2, 0, 3, 99, 99, 2, 99, 99, 99, 99, 99, 99, 99, 99, 99},
            {99, 99, 99, 99, 3, 0, 99, 99, 99, 3, 99, 99, 99, 99, 99, 99, 99, 99},
            {99, 7, 99, 99, 99, 99, 0, 4, 99, 99, 3, 99, 99, 99, 99, 99, 99, 99},
            {99, 99, 5, 99, 99, 99, 4, 0, 3, 99, 99, 2, 99, 99, 99, 99, 99, 99},
            {99, 99, 99, 99, 2, 99, 99, 3, 0, 2, 99, 99, 2, 99, 99, 99, 99, 99},
            {99, 99, 99, 3, 99, 3, 99, 99, 2, 0, 99, 99, 99, 4, 3, 99, 99, 99},
            {99, 99, 99, 99, 99, 99, 3, 99, 99, 99, 0, 3, 99, 99, 99, 2, 99, 99},
            {99, 99, 99, 99, 99, 99, 99, 2, 99, 99, 3, 0, 2, 99, 99, 3, 99, 99},
            {99, 99, 99, 99, 99, 99, 99, 99, 2, 99, 99, 2, 0, 1, 99, 99, 2, 99},
            {99, 99, 99, 99, 99, 99, 99, 99, 99, 4, 99, 99, 1, 0, 2, 99, 99, 4},
            {99, 99, 99, 99, 99, 99, 99, 99, 99, 3, 99, 99, 99, 2, 0, 99, 99, 99},
            {99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 2, 3, 99, 99, 99, 0, 3, 99},
            {99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 2, 99, 99, 3, 0, 3},
            {99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 4, 99, 99, 3, 0}
        };

        this.matrizT = new int[][]{
            {0, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99},
            {99, 0, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99},
            {99, 99, 0, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99},
            {99, 99, 99, 0, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99},
            {99, 99, 99, 99, 0, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99},
            {99, 99, 99, 99, 99, 0, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99},
            {99, 99, 99, 99, 99, 99, 0, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99},
            {99, 99, 99, 99, 99, 99, 99, 0, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99},
            {99, 99, 99, 99, 99, 99, 99, 99, 0, 99, 99, 99, 99, 99, 99, 99, 99, 99},
            {99, 99, 99, 99, 99, 99, 99, 99, 99, 0, 99, 99, 99, 99, 99, 99, 99, 99},
            {99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 0, 99, 99, 99, 99, 99, 99, 99},
            {99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 0, 99, 99, 99, 99, 99, 99},
            {99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 0, 99, 99, 99, 99, 99},
            {99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 0, 99, 99, 99, 99},
            {99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 0, 99, 99, 99},
            {99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 0, 99, 99},
            {99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 0, 99},
            {99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 0}
        };

        int ady = matrizM.length;
        for (int k = 0; k < ady; k++) {
            for (int i = 0; i < ady; i++) {
                for (int j = 0; j < ady; j++) {
                    if ((matrizM[i][k] + matrizM[k][j]) < matrizM[i][j]) {
                        matrizM[i][j] = matrizM[i][k] + matrizM[k][j];
                        matrizT[i][j] = k;
                    }

                }
            }
        }

    }

    public void mostrarTodos() {
        for (int i = 0; i < 18; i++) {
            for (int j = 0; j < 18; j++) {
                System.out.print(matrizM[i][j] + "\t");
            }
            System.out.println("");
        }
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        for (int i = 0; i < 18; i++) {
            for (int j = 0; j < 18; j++) {
                System.out.print(matrizT[i][j] + "\t");
            }
            System.out.println("");
        }
    }

    public int[][] getMatrizM() {
        return matrizM;
    }

    public int[][] getMatrizT() {
        return matrizT;
    }

    public void setMatrizM(int[][] matrizM) {
        this.matrizM = matrizM;
    }

    public void setMatrizT(int[][] matrizT) {
        this.matrizT = matrizT;
    }

    public LinkedList<Integer> getIntermedio() {
        return intermedio;
    }

        public int getGastoDeRuta() {
        return gastoDeRuta;
    }

    public void setGastoDeRuta(int gastoDeRuta) {
        this.gastoDeRuta = gastoDeRuta;
    }
    
    public void contarGastos(int inicio, int fin) {
        int gastoTotal = 0;
        gastoTotal = matrizM[inicio][intermedio.get(0)];
        
        for(int i = 0; i < intermedio.size() - 1; i++) {
            gastoTotal = gastoTotal + matrizM[intermedio.get(i)][intermedio.get(i+1)];
            System.out.println("Entro aqui");
        }
        gastoTotal = matrizM[intermedio.getLast()][fin];
        
        this.gastoDeRuta = gastoTotal;
    }
    
    
    public void resuelveFloyd(int inicio, int fin) {
        
        //Condicion base
        if (matrizT[inicio][fin] == 99) {
            //terminamos
            //gastoDeRuta = gastoDeRuta + matrizM[inicio][fin];
        } //condicion recursiva
        else {
            //gastoDeRuta = gastoDeRuta + matrizM[inicio][fin];
            //System.out.println("Inicio: " + inicio + " Fin: " + fin);
            //System.out.println("Gasto agregado: " + matrizM[inicio][fin]);
            // ---- Guardamos el punto medio
            if (intermedio.size() == 0) {
                this.intermedio.add(matrizT[inicio][fin]);
            } else {
                int contador = 0;
                int size = intermedio.size();
                for (int i = 0; i < size; i++) {
                    if (intermedio.get(i) == fin || intermedio.get(i) == inicio) {
                        if (intermedio.get(i) == fin) {
                            this.intermedio.add(contador, matrizT[inicio][fin]);
                        } else {
                            this.intermedio.add(contador + 1, matrizT[inicio][fin]);
                        }
                        break;
                    }
                    contador++;
                }
            }

            //--------
            resuelveFloyd(inicio, matrizT[inicio][fin]);
            //primer llamada
            resuelveFloyd(matrizT[inicio][fin], fin);
            //segunda llamada
        }
    }

    public void imprimeIntermedios() {
        for (int valor : this.intermedio) {
            System.out.println(valor);
        }
    }

}
