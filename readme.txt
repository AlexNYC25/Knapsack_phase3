compile instructions
    javac Knapsack.java GA_Knapsack.java   

execution instructions
    java Knapsack -i data.txt -o output.txt

summery:
    The genetic algorithm solution for the knapsack problem, using the same data from phase 1 and phase 2 of the
    project, this approach makes use of a generic algorithm that creates multiple generations of data to find the optimal
    solution with respect to the max weight and value of the set of items in the knapsack. Instead a fitness function is used to
    determine if a solution is valid. And using the fitness function data sets are crossed to generated a better set.