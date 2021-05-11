import java.io.File;
import java.util.Scanner;

public class Knapsack{

    public static int doesItInclude(String[] arr, String word){

        for(int x = 0; x < arr.length; x++){
            if(arr[x].matches(word)){
                return x;
            }
        }

        return -1;
    }

    public static void main(String[] args){
        Scanner sc = null;


        // argument flags
        int inputPosition = doesItInclude(args, "-i") + 1;
        int outputPosition = doesItInclude(args, "-o") + 1;

        if(inputPosition >= args.length || outputPosition >= args.length || inputPosition == -1 || outputPosition == -1){
            System.out.println("There is a missing parameter");
            return;
        }
        
        // initial data file
        try{
            File file = new File(args[inputPosition]); 
            sc = new Scanner(file); 
        } catch( Exception e){
            System.out.println("Error found");
        }

        GA_Knapsack geneticKnapsack = new GA_Knapsack(10,1000, 30, sc);
        

    }
}