import java.lang.Math;
import java.util.Vector;

public class GA_Knapsack {

    private Vector<boolean[]> generationPopulation;

    private int currentGen;
    private int maxGen;
    private int populationSize;
    
    public GA_Knapsack(int data, int max_gen, int pop_size) {

        this.currentGen = 0;
        this.maxGen = max_gen;
        this.populationSize = pop_size;

        // generates temp placeholder of populations with 10 chromosomes
        this.generateInitialPopulation(10);
    }

    public void findBestCombinations(){
        
        // for each of the population sizes
            // generate the initial population
            // for the number of max generation
                // find and cross the best parents for the next generation
            // print the generation_max best member

        this.generateInitialPopulation(10);
        for(int i = 0; i < this.maxGen; i++){
            Vector<boolean[]> parentGroup = this.findParentGroup(this.generationPopulation, 10);
            // cross the best parents
        }
    }

    private void populationCrossover(Vector<boolean[]> parents){
        // find the best parents

    }

    // generate initial population 
    private void generateInitialPopulation(int numChromosomes){
        // empty population before creating a new initial 
        generationPopulation.clear();
        
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

    private int evaluateCandidateFitness(boolean[] canidate){
        int totalValue = 0;
        int totalWeight = 0;

        // iterate through array adding respective value and weight to local variables 

        // if weight is more then the maximum allowed return 0
        // else return value
        return 0;
    }
}
