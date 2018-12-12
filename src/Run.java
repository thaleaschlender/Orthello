import javafx.application.Application;

import java.util.ArrayList;
import java.util.Collection;

public class Run {
    static int[] weights;
    static int numberOfRuns;
    static Othello o = new Othello();
    public static void main (String[] args){
        //runTest();
       // System.out.println("avg winner & better eval " + (winner/numberOfRuns) );
        hillclimbing();
    }
    public static void runTest(){
            numberOfRuns= 0;
            int[][] results = new int[1000][2];
           //Othello o = new Othello();
           //o.run();
        o.white.getEvalFunction().setW1(weights[0]);
        o.white.getEvalFunction().setW2(weights[1]);
        o.white.getEvalFunction().setW3(weights[2]);
           while (numberOfRuns < 1000) {
               int[] temp = o.gameLoop();
               results[numberOfRuns][0] = temp[0];
               results[numberOfRuns][1] = temp[1];

               numberOfRuns++;
           }
           int averageB = 0;
           int averageW = 0;
           for(int i = 0; i < results.length; i++){
               averageB += results[i][0];
               averageW += results[i][1];
               System.out.println(" b " + results[i][0] + " w " + results[i][1]);
           }
           averageB = averageB/ results.length;
           averageW = averageW/ results.length;
           System.out.println(" average b " + averageB + " average w " + averageW);
    }

    public static void hillclimbing(){
        int numberOfRuns = 0;
        int[] current = {1,1,1};

        o.run();
        int[] bestScore = o.gameLoop();
        int[] best = current;
        while (numberOfRuns < 100){

            ArrayList<int[]> neighbours = neighbours(current);
            for(int[] n : neighbours ){
                o.white.getEvalFunction().setW1(n[0]);
                o.white.getEvalFunction().setW2(n[1]);
                o.white.getEvalFunction().setW3(n[2]);
                int[] temp = o.gameLoop();
               // System.out.println (" testing weights " + n[0] + " " + n[1] + " " + n[2]);
                //System.out.println(" has a score of b " + temp[0] + " w " + temp[1]);
                if(temp[1] > bestScore[1]){
                    bestScore = temp;
                    best = n;
                }
            }
            current = best;
            System.out.println(" best of iteration weights w1 " + current[0] + " w2 " + current[1] + " w3 " + current[2] );
             System.out.println(" with scores b " + bestScore[0] + " w " + bestScore[1] );
            numberOfRuns++;
        }
        System.out.println(" weight 1 = " + current[0] + " w2 " + current[1] + " w3 " + current[2]);
        weights = current;
        runTest();
    }

    public static ArrayList<int[]> neighbours (int[] current) {
        ArrayList<int[]> neighbours = new ArrayList<>();
        for (int i = -1; i < 2; i=i+2) {
            int[] t0 = {current[0] + i, current[1], current[2]};
            int[] t1 = {current[0], current[1] + i, current[2]};
            int[] t2 = {current[0], current[1], current[2] + i};
            int[] t3 = {current[0] + i, current[1] + i, current[2]};
            int[] t4 = {current[0] + 1, current[1], current[2] + i};
            int[] t5 = {current[0], current[1] + i, current[2] + i};
            int[] t6 = {current[0] + i, current[1] + i, current[2] + i};
            neighbours.add(t0);
            neighbours. add(t1);
            neighbours. add(t2);
            neighbours. add(t3);
            neighbours. add(t4);
            neighbours. add(t5);
            neighbours. add(t6);

        }
        return neighbours;
    }


}
