package Algorithm;

import Modelo.Dataset;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author dhernandezl
 */
public class Fitness {
    
    public Fitness(){
    }
    
    //Problem
    public void setFitness(Population MyPop, int size, ArrayList variable) {
        String[] ItemDt;
        String[] ItemS;
        int nsupport = 0;
        ArrayList d = Dataset.dataset;
        for (int i = 0; i < size; i++) {
            ItemS = MyPop._individuals[i].getItemS();
            for (int j = 0; j < Dataset.getSize(); j++) {
                ItemDt = getRow(variable, j);   
                if (Arrays.equals(ItemS, ItemDt)) {
                    nsupport += 1;
                }
            }
            double sprt = (double) nsupport/Dataset.getSize();
            nsupport = 0;
            MyPop._individuals[i].setSoporte(sprt);
        }    
    }
    
    public String[] getRow(ArrayList variable, int index){
        String[] var = new String[variable.size()];
        ArrayList rr_var;
        for (int j = 0; j < variable.size(); j++) {
            int nvar = (int) variable.get(j);
            rr_var = Dataset.dataset.get(nvar);
            var[j] = String.valueOf(rr_var.get(index));                
        }       
        return var;
    }
}
