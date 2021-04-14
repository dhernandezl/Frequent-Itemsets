/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AGenetico;

import java.util.ArrayList;
import java.util.Random;
import Modelo.Dataset;
import java.math.BigInteger;
import java.security.SecureRandom;
/**
 *
 * @author DHL-SIS-ING
 */
public final class Population {
    
    Individuo[] _Individuos;
    Dataset dataset;
    int size_population;
    int value = 0;
    public static ArrayList item_data;
    
    public Population(int size){
        _Individuos = new Individuo[size];
        size_population = size;
    }
    
    // Crear población Inicial
    public Population(int size, ArrayList variable) 
    {
        try{
            _Individuos = new Individuo[size];
            size_population = size;
            item_data = Modelo.Dataset.dataset;
            for (int i = 0; i < size_population; i++) {
                Individuo newIndividual = Obtener_Individuo(item_data, variable);
                saveIndivido(i, newIndividual);
            }
        }catch(Exception e){
            String s = e.getMessage();
            System.out.println(s);
        }
    }

    //Obtener el Mejor individuo
    public Individuo getFittest() {
        Individuo fittest = _Individuos[0];
        // Loop through individuals to find fittest
        for (int i = 0; i < size_population; i++) {
            if (fittest.getSoporte() <= getIndividuos(i).getSoporte()) {
                fittest = getIndividuos(i);
            }
        }
        return fittest;
    }
    
    //Generar Index para poblacion Inicial
    public Individuo Obtener_Individuo(ArrayList item_data, ArrayList variable){
        ArrayList src = null;
        Individuo ind = new Individuo();
        int numero = binarioADecimal(ind.toString());
         if ((numero < Dataset.getSize()) && (numero >= 0)) {
             ind.setIndex(numero);
             ind.Inicializar_ItemV(variable.size());
             for (int i = 0; i < variable.size(); i++) {
                 int indx = Integer.valueOf(String.valueOf(variable.get(i)));
                 src = (ArrayList) Dataset.dataset.get(indx);
                 ind.setSingleItemS(i, String.valueOf(src.get(numero)));
             }
         }else{ind = Obtener_Individuo(item_data, variable);}
         return ind;
    }

    public static int binarioADecimal(String numeroBinario) {
        int longitud = numeroBinario.length();//Numero de digitos que tiene nuestro binario
        int resultado = 0;//Aqui almacenaremos nuestra respuesta final
        int potencia = longitud - 1;
        for (int i = 0; i < longitud; i++) {//recorremos la cadena de numeros
            if (numeroBinario.charAt(i) == '1') {
                resultado += Math.pow(2, potencia);
            }
            potencia--;//drecremantamos la potencia
        }
        return resultado;
    }
    
    public void saveIndivido(int index, Individuo indiv) {
        _Individuos[index] = indiv;
    }

    public Individuo getIndividuos(int index) {
        return _Individuos[index];
    }

    public void setIndividuos(Individuo[] _Individuos) {
        this._Individuos = _Individuos;
    }
    
    public int getSize_population() {
        return size_population;
    }

    public void setSize_population(int size_population) {
        this.size_population = size_population;
    }

}
