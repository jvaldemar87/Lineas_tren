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
    private int [][] matrizM;
    private int [][] matrizT;
    LinkedList <Integer> intermedio;
    
    public Matrices(){
        this.intermedio = new LinkedList<>();
        this.matrizM = new int[][]{
        { 0 , 1 , 2 , 1 , 99 , 99 , 2 , 3 , 99 , 99 , 3 , 4 , 99 , 99 , 99 , 4 , 5 , 6 },
        { 1 , 0 , 1 , 1 , 2 , 3 , 1 , 99 , 99 , 99 , 2 , 99 , 99 , 99 , 99 , 3 , 4 , 5 },
        { 2 , 1 , 0 , 1 , 1 , 2 , 99 , 1 , 99 , 99 , 99 , 2 , 99 , 99 , 99 , 3 , 99 , 99 },
        { 1 , 99 , 1 , 0 , 1 , 99 , 99 , 2 , 2 , 1 , 99 , 3 , 3 , 99 , 2 , 4 , 4 , 99 },
        { 99 , 2 , 1 , 1 , 0 , 1 , 99 , 99 , 1 , 99 , 99 , 99 , 2 , 99 , 99 , 99 , 3 , 99 },
        { 99 , 3 , 2 , 99 , 1 , 0 , 99 , 99 , 99 , 1 , 99 , 99 , 99 , 2 , 99 , 99 , 99 , 3 },
        { 2 , 1 , 99 , 99 , 99 , 99 , 0 , 1 , 2 , 3 , 1 , 99 , 99 , 99 , 99 , 2 , 3 , 4 },
        { 3 , 99 , 1 , 2 , 99 , 99 , 1 , 0 , 1 , 2 , 99 , 1 , 99 , 99 , 99 , 2 , 99 , 99 },
        { 99 , 99 , 99 , 2 , 1 , 99 , 2 , 1 , 0 , 1 , 99 , 99 , 1 , 99 , 99 , 99 , 2 , 99 },
        { 99 , 2 , 99 , 1 , 99 , 1 , 3 , 2 , 1 , 0 , 99 , 99 , 99 , 1 , 1 , 99 , 99 , 2 },
        { 3 , 2 , 99 , 99 , 99 , 99 , 1 , 99 , 99 , 99 , 0 , 1 , 2 , 3 , 4 , 1 , 2 , 3 },
        { 4 , 99 , 2 , 3 , 99 , 99 , 99 , 1 , 99 , 99 , 1 , 0 , 1 , 2 , 3 , 1 , 99 , 99 },
        { 99 , 99 , 99 , 3 , 2 , 99 , 99 , 99 , 1 , 99 , 2 , 1 , 0 , 1 , 2 , 99 , 1 , 99 },
        { 99 , 99 , 99 , 99 , 99 , 2 , 99 , 99 , 99 , 1 , 3 , 2 , 1 , 0 , 1 , 99 , 99 , 1 },
        { 99 , 3 , 99 , 2 , 99 , 99 , 99 , 99 , 99 , 1 , 4 , 3 , 2 , 1 , 0 , 99 , 99 , 99 },
        { 4 , 3 , 3 , 4 , 99 , 99 , 2 , 2 , 99 , 99 , 1 , 1 , 99 , 99 , 99 , 0 , 1 , 2 },
        { 5 , 4 , 99 , 4 , 3 , 99 , 3 , 99 , 2 , 99 , 2 , 99 , 1 , 99 , 99 , 1 , 0 , 1 },
        { 6 , 5 , 99 , 99 , 99 , 3 , 4 , 99 , 99 , 2 , 3 , 99 , 99 , 1 , 99 , 2 , 1 , 0 }

        };

        this.matrizT = new int[][]{
        { 0 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 },
        { 99 , 0 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 },
        { 99 , 99 , 0 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 },
        { 99 , 99 , 99 , 0 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 },
        { 99 , 99 , 99 , 99 , 0 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 },
        { 99 , 99 , 99 , 99 , 99 , 0 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 },
        { 99 , 99 , 99 , 99 , 99 , 99 , 0 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 },
        { 99 , 99 , 99 , 99 , 99 , 99 , 99 , 0 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 },
        { 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 0 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 },
        { 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 0 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 },
        { 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 0 , 99 , 99 , 99 , 99 , 99 , 99 , 99 },
        { 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 0 , 99 , 99 , 99 , 99 , 99 , 99 },
        { 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 0 , 99 , 99 , 99 , 99 , 99 },
        { 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 0 , 99 , 99 , 99 , 99 },
        { 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 0 , 99 , 99 , 99 },
        { 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 0 , 99 , 99 },
        { 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 0 , 99 },
        { 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 99 , 0 }
        };

            int ady = matrizM.length;
            for (int k = 0; k < ady; k++) {
                for (int i = 0; i < ady; i++) {
                    for (int j = 0; j < ady; j++) {
                        if ((matrizM[i][k]+matrizM[k][j])<matrizM[i][j]) {
                            matrizM[i][j] = matrizM[i][k]+matrizM[k][j];
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
    
    
    
    public void resuelveFloyd(int inicio, int fin){
        
        //Condicion base
        if(matrizT[inicio][fin] == 99){
            //terminamos
        }
        
        
        //condicion recursiva
        else{
            // ---- Guardamos el punto medio
            if(intermedio.size() == 0){
                this.intermedio.add(matrizT[inicio][fin]);
            }
            else{
          
              int contador = 0;
               int size = intermedio.size();
                for(int i = 0; i < size; i++){
                    
                    if (intermedio.get(i) == fin) {
                        this.intermedio.add(contador,matrizT[inicio][fin]);
                    }
                    contador ++;
                    
                }
            }
            
            //--------
            
            resuelveFloyd(inicio, matrizT[inicio][fin]);
            
            //primer llamada
            
            
            resuelveFloyd(matrizT[inicio][fin], fin);
            //segunda llamada
            
            
            
        }
    }
    
    public void imprimeIntermedios(){
        for(int valor : this.intermedio){
            System.out.println(valor);
        }
    }
    
}
