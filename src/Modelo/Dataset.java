/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.util.ArrayList;

/**
 *
 * @author DHL-SIS-ING
 */
public class Dataset {

    
    public static ArrayList<ArrayList<String>> dataset;
    private static ArrayList<String> rotulo;
    public static ArrayList<Integer> Index_V = new ArrayList<>();
    
    public Dataset(){
    }

    public Dataset(int dimension){
        dataset = new ArrayList<ArrayList<String>>();
        for (int i = 0; i < dimension; i++) {
            dataset.add(new ArrayList<String>());
        }
    }
    
    public void Llenar_Data(String[] data){
        for (int i = 0; i < data.length; i++) {
            dataset.get(i).add(data[i]);
        }
    }
    
    public void Rotulo_Data(String[] data){
        rotulo = new ArrayList<String>();
        for (int i = 0; i < data.length; i++) {
            rotulo.add(data[i]);
        }
    }

    public ArrayList<ArrayList<String>> getDataset() {
        return dataset;
    }

    public void setDataset(ArrayList<ArrayList<String>> dataset) {
        this.dataset = dataset;
    }
    
    public ArrayList<String> getRotulo() {
        return rotulo;
    }

    public void setRotulo(ArrayList<String> rotulo) {
        this.rotulo = rotulo;
    }
    
    public static int getSize(){
        return dataset.get(0).size();
    }
}
