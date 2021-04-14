/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AGenetico;

import Modelo.Dataset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author DHL-SIS-ING
 */
public class Fitness {
    
    public Fitness(){
    }
    
    //Problem
    public void setFitness(Population MyPop, int size, ArrayList variable) {
        String[] ItemDt = null;
        String[] ItemS = null;
        int nsupport = 0;
        ArrayList d = Dataset.dataset;
        for (int i = 0; i < size; i++) {
            ItemS = MyPop._Individuos[i].getItemS();
            for (int j = 0; j < Dataset.getSize(); j++) {
                ItemDt = getRow(variable, j);   
                if (Arrays.equals(ItemS, ItemDt)) {
                    nsupport += 1;
                }
            }
            double sprt = (double) nsupport/Dataset.getSize();
            nsupport = 0;
            MyPop._Individuos[i].setSoporte(sprt);
        }    
    }
    
    public String[] getRow(ArrayList variable, int index){
        String[] var = new String[variable.size()];
        ArrayList rr_var = null;
        for (int j = 0; j < variable.size(); j++) {
            int nvar = (int) variable.get(j);
            rr_var = Dataset.dataset.get(nvar);
            var[j] = String.valueOf(rr_var.get(index));                
        }       
        return var;
    }
}
