import java.lang.Math;
import java.util.Scanner;
import java.util.Vector;

public class GA_Knapsack {

    private Vector<boolean[]> generationPopulation;

    private int tempNum = 0;

    private int currentGen;
    private int maxGen;
    private int populationSize;
    private int maxPossibleWeight;

    int[] easyWeights = new int[10];
    int[] easyValues = new int[10];

    int[] mediumWeights = new int[15];
    int[] mediumValues = new int[15];

    int[] largeWeights = new int[20];
    int[] largeValues = new int[20];
    
    public GA_Knapsack(int max_gen, int pop_size, int knapsack_weight, Scanner dataFile) {

        this.currentGen = 0;
        this.maxGen = max_gen;
        this.populationSize = pop_size;
        this.maxPossibleWeight = knapsack_weight;

        // read initial reference data from datafile
        this.readDataFromFile(dataFile);

        // generates temp placeholder of populations with 10 chromosomes
        this.findBestCombinations(10);

        this.findBestCombinations(15);

        this.findBestCombinations(20);
    }

    public void readDataFromFile(Scanner dataFile){
        String[] initialDataStrings = dataFile.nextLine().split(" ");

        String[] easyWeightsString = initialDataStrings[0].split(",");
        String[] easyValuesString = initialDataStrings[1].split(",");

        for(int i = 0; i < 10; i++){
            this.easyWeights[i] = Integer.parseInt(easyWeightsString[i]);
            this.easyValues[i] = Integer.parseInt(easyValuesString[i]);
        }

        String[] mediumWeightString = initialDataStrings[2].split(",");
        String[] mediumValueStrings = initialDataStrings[3].split(",");

        for(int i = 0; i < 15; i++){
            this.mediumWeights[i] = Integer.parseInt( mediumWeightString[i]);
            this.mediumValues[i] = Integer.parseInt( mediumValueStrings[i] );
        }

        String[] largeWeightString = initialDataStrings[4].split(",");
        String[] largeValueString = initialDataStrings[5].split(",");

        for(int i = 0; i < 20; i++){
            this.largeWeights[i] = Integer.parseInt( largeWeightString[i] );
            this.largeValues[i] = Integer.parseInt( largeValueString[i] );
        }
        
    }

    public void findBestCombinations(int length_of_gene){
        
        // generate the initial population
        this.generateInitialPopulation(length_of_gene);
        // for each of the population sizes
        for(int i = 0; i < this.maxGen; i++){
            // find and cross the best parents for the next generation, 5 placeholder for number of potential parents to return
            Vector<boolean[]> parentGroup = this.findParentGroup(this.generationPopulation, 5);
            // cross the best parents
            this.generateNewGeneration(parentGroup);
        }
        // TODO print the best candidate and info

        System.out.println("Best solution found");
        this.findBestCandidate(this.generationPopulation);
        
    }

    private void findBestCandidate(Vector<boolean[]> group){
        int bestFitness = 0;
        boolean[] candidate = null;

        for(int i = 0; i < group.size(); i++){
            if( this.evaluateCandidateFitness(group.get(i)) > bestFitness){
                bestFitness = this.evaluateCandidateFitness(group.get(i));
                candidate = group.get(i);
            }
        }

        for(int i = 0; i < candidate.length; i++){
            System.out.print(candidate[i] + " ");

        }
        System.out.println("");
        System.out.println("Best candidate fitness is : " + this.evaluateCandidateFitness(candidate));
        System.out.println("Best candidate weight is : " + this.evaluateCandidateWeight(candidate));
        
    }

    /*
        called by findBestCombination

        @description: function to generate the next generation of candidates using the group of parents deemed the most fit
    */
    private void generateNewGeneration(Vector<boolean[]> parents){
        Vector<boolean[]> newGeneration = new Vector<boolean[]>();

        // find the best "parent" and add it to the next generation vector as a "child"
        // TODO: crate the new function to save the best candidate in the previous generation and move it to the next generation
        
        // for the remainder of the population group create and add new children
        while(newGeneration.size() < this.populationSize){
            boolean[] newChild = this.generateChildFromRandomParents(parents);

            
            newGeneration.add(newChild);
        }

        // save the new population as the object current population
        this.generationPopulation = newGeneration;

    }

    /*
        called by generateNewGeneration

        description: from the parents group select two random parenst cross them and return thier offspring
    */
    private boolean[] generateChildFromRandomParents(Vector<boolean[]> parents){
        // gets the location of potential parents from the parent group
        int parent1Location = (int) ((Math.random() * (parents.size() -1 - 0)) + 0 );
        int parent2Location = (int) ((Math.random() * (parents.size() -1 - 0)) + 0 );

        // ensure there is no occrance of the same parent appeating twice
        while(parent1Location == parent2Location){
            parent2Location = (int) ((Math.random() * (parents.size() - 0)) + 0 );

        }

        /*
        while(this.tempNum < 50){
            System.out.println("Parent 1: ");
            for(int i = 0; i < parents.get(parent1Location).length; i++){
                System.out.print(parents.get(parent1Location)[i] + " ");
            }
            System.out.println("");

            System.out.println("Parent 2: ");
            for(int i = 0; i < parents.get(parent2Location).length; i++){
                System.out.print(parents.get(parent2Location)[i] + " ");
            }
            System.out.println(""); 

            this.tempNum++;
        }
        */
        

        return this.crossParents(parents.get(parent1Location), parents.get(parent2Location));
    }

    /*
        called from gererateChildFromRandomParents

        description: takes two parents and using one point cross over create a child and return it
    */
    private boolean[] crossParents(boolean[] parent1, boolean[] parent2){
        boolean[] newChild = new boolean[parent1.length];
        int crossoverPoint = (int) ((Math.random() * (parent1.length-1 - 1)) + 1 );

        for(int i = 0; i < crossoverPoint; i++){
            newChild[i] = parent1[i];
        }

        for(int j = crossoverPoint; j < parent2.length; j++){
            newChild[j] = parent2[j];
        }

        return newChild;
    }

    /*
        called by findBestCombination

        description: creates a random population with the same number as members as the number of population, with the lenght of the population chromosome as the numChromosomes
    */
    private void generateInitialPopulation(int numChromosomes){
        // empty population before creating a new initial 
        if(generationPopulation != null){
            generationPopulation.clear();
        }

        generationPopulation = new Vector<boolean[]>();
        
        
        // for loop for how many in initial generation 
        for(int i = 0; i < populationSize; i++){
            boolean[] tempSequence = new boolean[numChromosomes];
            // for the length of how many items create the random binary interperation of items in knapsack
            for(int j = 0; j < numChromosomes; j++){
                tempSequence[j] = this.generateRandomBoolean();
            }
            // for each finished combination add to the global generation population
            generationPopulation.add(tempSequence);
        }
        
    }

    /*
        called by findBestCombination

        description: returns a random boolean value using math.random
    */
    private boolean generateRandomBoolean(){
        return (Math.round(Math.random())) == 1;
    }

    // takes population and returns the best candidates to cross for the best potential offspring
    private Vector<boolean[]> findParentGroup( Vector<boolean[]> candidates, int numOfParents){
        // evaluate all of their fitnesses
        int[] candidatesFitness = this.evaluateAllCanidateFitnesses(candidates);
        // sort by thier fitness
        Vector<boolean[]> sortedCandidates = this.sortCandidateFitness(candidates, candidatesFitness);
        // return the best "X" number candidates as a vector of boolean[]
        return this.getNumberOfCandidates(sortedCandidates, numOfParents);
    }

    private Vector<boolean[]> sortCandidateFitness(Vector<boolean[]> candidates, int[] scores){
        Vector<boolean[]> sortedCandidates = new Vector<boolean[]>();
        int[] candidateScores = scores;

        // while there is a non zero score in the scores array
        while(this.isTherePositiveScore(candidateScores)){        
            // find the position of the candidate with the largest score
            int bestScoreLocation = this.positionOfBestScore(candidateScores);
            // push the canidate at the position found to local candidate Vector 
            sortedCandidates.add(candidates.get(bestScoreLocation));
            // set scores position to 0
            candidateScores[bestScoreLocation] = 0;
        }

        return sortedCandidates;
    }

    private boolean isTherePositiveScore(int[] scores){
        for(int i = 0; i < scores.length; i++){
            if(scores[i] > 0){
                return true;
            }
        }

        return false;
    }

    private int positionOfBestScore(int[] scores){
        int bestScoreTemp = 0;
        int bestScoreTempPosition = -1;

        for(int i = 0; i < scores.length; i++){
            if(scores[i] > bestScoreTemp){
                bestScoreTemp = scores[i];
                bestScoreTempPosition = i;
            }
        }

        return bestScoreTempPosition;
    }

    private Vector<boolean[]> getNumberOfCandidates(Vector<boolean[]> candidates, int numberOfCandidates){
        Vector<boolean[]> parentGroup = new Vector<boolean[]>();

        for(int i = 0; i < numberOfCandidates; i++){
            parentGroup.add(candidates.get(i));
        }

        return parentGroup;
    }

    private int[] evaluateAllCanidateFitnesses(Vector<boolean[]> candidates){
        // create an array of the same length as the number of candidates
        int[] fitnessScores = new int[candidates.size()];
        // iterate through each candidate and call the fitness function to get its score and save it to the local array
        for(int i = 0; i < candidates.size(); i++){
            fitnessScores[i] = evaluateCandidateFitness(candidates.get(i));
        }
    
        return fitnessScores;
    }

    private int evaluateCandidateFitness(boolean[] candidate){
        int totalValue = 0;
        int totalWeight = 0;

        // iterate through array adding respective value and weight to local variables 
        if( candidate.length == 10){
            for(int i = 0; i < 10; i++){
                if(candidate[i]){
                    totalWeight += easyWeights[i];
                    totalValue += easyValues[i];
                }
            }
        }

        if(candidate.length == 15){
            for(int i = 0; i < 15; i++){
                if(candidate[i]){
                    totalWeight += mediumWeights[i];
                    totalValue += mediumValues[i];
                }
            }

        }

        if(candidate.length == 20){
            for(int i = 0; i < 20; i++){
                if(candidate[i]){
                    totalWeight += largeWeights[i];
                    totalValue += largeValues[i];
                }
            }
        }

        // if weight is more then the maximum allowed return 0
        // else return value
        if(totalWeight > this.maxPossibleWeight) return 0;

        return totalValue;
    }

    private int evaluateCandidateWeight (boolean[] candidate){
        int totalValue = 0;
        int totalWeight = 0;

        // iterate through array adding respective value and weight to local variables 
        if( candidate.length == 10){
            for(int i = 0; i < 10; i++){
                if(candidate[i]){
                    totalWeight += easyWeights[i];
                    totalValue += easyValues[i];
                }
            }
        }

        if(candidate.length == 15){
            for(int i = 0; i < 15; i++){
                if(candidate[i]){
                    totalWeight += mediumWeights[i];
                    totalValue += mediumValues[i];
                }
            }

        }

        if(candidate.length == 20){
            for(int i = 0; i < 20; i++){
                if(candidate[i]){
                    totalWeight += largeWeights[i];
                    totalValue += largeValues[i];
                }
            }
        }

        // if weight is more then the maximum allowed return 0
        // else return value

        return totalWeight;
    }
}
