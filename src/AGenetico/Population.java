/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AGenetico;

import java.util.ArrayList;
import Modelo.Dataset;
/**
 *
 * @author DHL-SIS-ING
 */
public final class Population {
    
    Individual[] _individuals;
    Dataset dataset;
    int size_population;
    int value = 0;
    public static ArrayList item_data;
    
    public Population(int size){
        _individuals = new Individual[size];
        size_population = size;
    }
    
    // Crear población Inicial
    public Population(int size, ArrayList variable) 
    {
        try{
            _individuals = new Individual[size];
            size_population = size;
            item_data = Modelo.Dataset.dataset;
            for (int i = 0; i < size_population; i++) {
                Individual newIndividual = getIndividual(item_data, variable);
                saveIndividual(i, newIndividual);
            }
        }catch(Exception e){
            String s = e.getMessage();
            System.out.println(s);
        }
    }

    //Get the best individual
    public Individual getFittest() {
        Individual fittest = _individuals[0];
        // Loop through individuals to find fittest
        for (int i = 0; i < size_population; i++) {
            if (fittest.getSoporte() <= getIndividuals(i).getSoporte()) {
                fittest = getIndividuals(i);
            }
        }
        return fittest;
    }
    
    //Generate Index for Initial population
    public Individual getIndividual(ArrayList item_data, ArrayList variable){
        ArrayList src = null;
        Individual ind = new Individual();
        int numero = binaryToDecimal(ind.toString());
         if ((numero < Dataset.getSize()) && (numero >= 0)) {
             ind.setIndex(numero);
             ind.Inicializar_ItemV(variable.size());
             for (int i = 0; i < variable.size(); i++) {
                 int indx = Integer.valueOf(String.valueOf(variable.get(i)));
                 src = (ArrayList) Dataset.dataset.get(indx);
                 ind.setSingleItemS(i, String.valueOf(src.get(numero)));
             }
         }else{ind = getIndividual(item_data, variable);}
         return ind;
    }

    public static int binaryToDecimal(String numeroBinario) {
        int longitud = numeroBinario.length();//Number of digits that our binary has
        int resultado = 0; //Here we will store our final answer
        int potencia = longitud - 1;
        for (int i = 0; i < longitud; i++) { //loop through the string of numbers
            if (numeroBinario.charAt(i) == '1') {
                resultado += Math.pow(2, potencia);
            }
            potencia--;//decrease in power
        }
        return resultado;
    }
    
    public void saveIndividual(int index, Individual indiv) {
        _individuals[index] = indiv;
    }

    public Individual getIndividuals(int index) {
        return _individuals[index];
    }

    public void setIndividuals(Individual[] _individuals) {
        this._individuals = _individuals;
    }
    
    public int getSize_population() {
        return size_population;
    }

    public void setSize_population(int size_population) {
        this.size_population = size_population;
    }

}
