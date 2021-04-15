package Algorithm;

import static Algorithm.Population.binaryToDecimal;
import Modelo.Dataset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * @author dhernandezl
 */

public class Algorithm {
    
    private ArrayList _variables;
    public Runtime _itrruntime;
    private int _datazise = 1024 * 1024; //To convert in Megabyte (MB)
    public ArrayList _memories;
    public Map<ArrayList<String>, Double> _solutionList = new HashMap<ArrayList<String>, Double>();;
    
    private double _memoryTotal = 0.0;
    private double _timeRunTotal = 0.0;
    private double _timeRun;
    private long _startTimeRun;
    private long _endTimeRun;
        /* Genetic Algorithm parameters */
    private final double uniformRate = 0.5;
    private final double mutationRate = 0.015;
    private final int tournamentSize = 5;
    private final boolean elitism = true;

    public Algorithm(){
    }
    
    public void Init(int iteration, int size_population, ArrayList variable, float fitness, boolean no_solution){
        _variables = variable;
        _memories = new ArrayList<Double>();
        Fitness fitnessCalculator = new Fitness();
        
        //Init population
        _itrruntime = Runtime.getRuntime();
        _startTimeRun = System.currentTimeMillis();
        Population current_population = new Population(size_population, variable);
        fitnessCalculator.setFitness(current_population, size_population, variable);
        double use_memory = (double)(_itrruntime.totalMemory() - _itrruntime.freeMemory()) / _datazise ;
        _endTimeRun = System.currentTimeMillis();
        _timeRun = _endTimeRun - _startTimeRun;
        Calculate_Solution(current_population);
        
        int generationCount = 1;
        while(generationCount < iteration){
            Formulario.frame.txt_result.append("Generation: " + generationCount + " Fitness: " + current_population.getFittest().getSoporte()+"\n"); 
            _memoryTotal += use_memory;
            _timeRunTotal += _timeRun;
            Formulario.frame.txt_result.append("Used memory: " + use_memory + " MB"+"\n" ); 
            Formulario.frame.txt_result.append("Tiempo Ejecución: " + _timeRun + " ms"+"\n"); 
            _memories.add(use_memory);
            _startTimeRun = System.currentTimeMillis();
            current_population = Evaluate_Population(current_population);
            fitnessCalculator.setFitness(current_population,size_population, variable);
            _endTimeRun = System.currentTimeMillis();
            use_memory = (double)(_itrruntime.totalMemory() - _itrruntime.freeMemory()) / _datazise;
            _timeRun = _endTimeRun - _startTimeRun;
            Calculate_Solution(current_population);
            generationCount++;
        }
        
        boolean solution = Solution(fitness);
        Formulario.frame.txt_result.append("Generation: " + generationCount + " Soporte: "+ current_population.getFittest().getSoporte()+"\n"); 
        _memoryTotal += use_memory;
        _timeRunTotal += _timeRun;
        Formulario.frame.txt_result.append("Used memory: " + use_memory + " MB"+"\n" ); 
        Formulario.frame.txt_result.append("Tiempo Ejecución: " + _timeRun + " ms"+"\n"); 
        _memories.add(use_memory);
        
        if (solution) {
            Formulario.frame.txt_result.append("\n Solution: \n"); 
            ArrayList<ArrayList<String>> keys = new ArrayList<ArrayList<String>>(_solutionList.keySet());
            for (int i = _solutionList.size() - 1; i >= 0; i--) {
                Formulario.frame.txt_result.append("Items: " + keys.get(i) + " => Fitness: " + _solutionList.get(keys.get(i)) + "\n");
            }
        }
        else{
            if (no_solution) {
                Formulario.frame.txt_result.append("\n Solution: \n"); 
                ArrayList<ArrayList<String>> keys = new ArrayList<ArrayList<String>>(_solutionList.keySet());
                for (int i = _solutionList.size()-1; i >= _solutionList.size()-3; i--) {
                    Formulario.frame.txt_result.append("Items: "+keys.get(i)+" => Fitness: "+_solutionList.get(keys.get(i))+"\n");
                }
            }else{
                Formulario.frame.txt_result.append("\n Without solution \n"); 
            }
        }
        
        Formulario.frame.txt_result.append("Total Used Memory: " + _memoryTotal + " Mb \n");
        Formulario.frame.txt_result.append("Execution Time: " + _timeRunTotal + " ms"+"\n");     
        _memoryTotal = 0; _timeRunTotal = 0;
        _solutionList.clear();
    }
    
    public Population Evaluate_Population(Population pop){
        Population newPopulation = new Population(pop.size_population);
        // Keep our best individual
        if (elitism) {
            newPopulation.saveIndividual(0, pop.getFittest());
        }

        // Crossover population
        int elitismOffset;
        if (elitism) {
            elitismOffset = 1;
        } else {
            elitismOffset = 0;
        }
        
        // Loop over the population size and create new individuals with
        // crossover
        for (int i = elitismOffset; i < pop.size_population; i++) {
            Individual indiv1 = tournamentSelection(pop);
            Individual indiv2 = tournamentSelection(pop);
            Individual newIndiv = crossover(indiv1, indiv2);
            newPopulation.saveIndividual(i, newIndiv);
        }
        
        // Mutate population
        for (int i = elitismOffset; i < pop.size_population; i++) {
            mutate(newPopulation.getIndividuals(i));
            int numero = binaryToDecimal(newPopulation.getIndividuals(i).toString());
            if((numero < Dataset.getSize())&&( numero >=0)){
                newPopulation.getIndividuals(i).setIndex(numero);
                newPopulation.getIndividuals(i).Inicializar_ItemV(_variables.size());
                for (int j = 0; j < _variables.size(); j++) {
                    int indx = Integer.valueOf(String.valueOf(_variables.get(j)));
                    ArrayList src = (ArrayList) Dataset.dataset.get(indx);
                    newPopulation.getIndividuals(i).setSingleItemS(j,String.valueOf(src.get(numero)));
                }
            }
            else{
                i--;
            }
        }
        return newPopulation;
    }
    
    // Crossover individuals
    private Individual crossover(Individual indiv1, Individual indiv2) {
        Individual newSol = new Individual();
        for (int i = 0; i < newSol.getGenes().length; i++) {
            if (Math.random() <= uniformRate) {
                newSol.setSingleGene(i, indiv1.getGenes(i));
            } else {
                newSol.setSingleGene(i, indiv2.getGenes(i));
            }
        }
        return newSol;
    }

    // Mutate an individual
    private void mutate(Individual indiv) {
        for (int i = 0; i < indiv.getLargo(); i++) {
            if (Math.random() <= mutationRate) {
                byte gene = (byte) Math.round(Math.random());
                indiv.setSingleGene(i, gene);
            }
        }
    }

    private Individual tournamentSelection(Population pop) {
        // Create a tournament population
        Population tournament = new Population(tournamentSize);
        // For each place in the tournament get a random individual
        for (int i = 0; i < tournamentSize; i++) {
            int randomId = (int) (Math.random() * pop.size_population);
            tournament.saveIndividual(i, pop.getIndividuals(randomId));
        }
        // Get the fittest
        Individual fittest = tournament.getFittest();
        return fittest;
    }
    
    public void Calculate_Solution(Population poup) {
        try {
            for (int i = 0; i < poup.size_population; i++) {
                if (_solutionList.isEmpty()) {
                    ArrayList list = new ArrayList<>(Arrays.asList(poup._individuals[i].getItemS()));
                    _solutionList.put(list, poup._individuals[i].getSoporte());
                } else {
                    ArrayList list = new ArrayList<>(Arrays.asList(poup._individuals[i].getItemS()));
                    _solutionList.put(list, poup._individuals[i].getSoporte());
                }
            }
        } catch (Exception e) {
            String s = e.getMessage();
        }
    }
    
    public boolean Solution(float soporte){
        Map<ArrayList<String>, Double> sortedMap = _solutionList.entrySet().stream().sorted(Entry.comparingByValue()).collect(Collectors.toMap(Entry::getKey, Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        _solutionList = sortedMap;
        int c = 0;
        for (Map.Entry<ArrayList<String>, Double> entry : _solutionList.entrySet()) {
            if (entry.getValue() >= Double.valueOf(soporte)) {
                c++;
            }
        }
        return (c != 0);
    }
}
    
