

import java.util.ArrayList;

public class Run {
    //static int[] weights;
    //static int numberOfRuns;
    static Othello o = new Othello();
    public static void main (String[] args){
        o.run();
        //runTest();
       // System.out.println("avg winner & better eval " + (winner/numberOfRuns) );
        int[][] weights= new int[2][3];
        for(int i = 0; i < weights.length; i++)
            weights[i] = hillclimbing();
        for(int i = 0;i < weights.length; i++)
            runTest(weights[i]);

    }
    public static void runTest(int[] weights){
            int numberOfRuns= 0;
            int[][] results = new int[300][2];
           //Othello o = new Othello();
           //o.run();
        o.white.getEvalFunction().setW1(weights[0]);
        o.white.getEvalFunction().setW2(weights[1]);
        o.white.getEvalFunction().setW3(weights[2]);

           while (numberOfRuns < 300) {
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
              // System.out.println(" b " + results[i][0] + " w " + results[i][1]);
           }
           averageB = averageB/ results.length;
           averageW = averageW/ results.length;
           System.out.println("with weights w1 " + weights[0] + " w2 " + weights[1] + " w3 " + weights[2]);
           System.out.println(" average b " + averageB + " average w " + averageW);
    }
    public static int[] runInternalTest (int[] w ){
        int numberOfRuns= 0;
        int averageB = 0;
        int averageW = 0;
        o.white.getEvalFunction().setW1(w[0]);
        o.white.getEvalFunction().setW2(w[1]);
        o.white.getEvalFunction().setW3(w[2]);
        while (numberOfRuns < 5) {
            int[] temp = o.gameLoop();
            averageB += temp[0];
            averageW += temp[1];

            numberOfRuns++;
        }
        averageB = averageB/ (numberOfRuns);
        averageW = averageW/ (numberOfRuns);
        int[] results = {averageB, averageW};
        return results;
    }

    public static int[] hillclimbing(){
        int numberOfRuns = 0;
        int max = 100;
        int min = -50;
       /* int a = (int)(Math.random() * ((max - min) + 1)) + min;
        int b = (int)(Math.random() * ((max - min) + 1)) + min;
        int c = (int)(Math.random() * ((max - min) + 1)) + min;
        int[] current = {-50,60,32};*/
       int[] current = randomproportionedweights();
        boolean unchanged = false;

        int[] bestScore = o.gameLoop();
        int[] best = current;
        while (numberOfRuns < 30){
            ArrayList<int[]> neighbours;
            if (unchanged) neighbours=  randomNeighbours();
           else neighbours = neighbours(current);
            for(int[] n : neighbours ){
                int[] temp = runInternalTest(n);
              // System.out.println (" testing weights " + n[0] + " " + n[1] + " " + n[2]);
               //System.out.println(" has a score of b " + temp[0] + " w " + temp[1]);
                if((temp[1]-temp[0]) > (bestScore[1] - bestScore[0])){
                //if(temp[1] > bestScore[1]){
                    bestScore = temp;
                    best = n;
                }
            }
            if(current == best && unchanged) break;
            else if(current == best) unchanged = true;

            current = best;
            System.out.println(" best of iteration weights w1 " + current[0] + " w2 " + current[1] + " w3 " + current[2] );
             System.out.println(" with scores b " + bestScore[0] + " w " + bestScore[1] );
             System.out.println("run number " + numberOfRuns);
            numberOfRuns++;
        }
        System.out.println(" weight 1 = " + current[0] + " w2 " + current[1] + " w3 " + current[2]);
        return current;

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
        for(int i = 0; i < 5; i++){
            int max = 100;
            int min = -50;
            int a = (int)(Math.random() * ((max - min) + 1)) + min;
            int b = (int)(Math.random() * ((max - min) + 1)) + min;
            int c = (int)(Math.random() * ((max - min) + 1)) + min;
            int[] temp = {a,b,c};
            neighbours.add(temp);
        }
        return neighbours;
    }

        public static  ArrayList<int[]> randomNeighbours(){
            ArrayList<int[]> neighbours = new ArrayList<>();
        for(int i = 0; i < 10; i++){
                int max = 100;
                int min = -50;
                int a = (int)(Math.random() * ((max - min) + 1)) + min;
                int b = (int)(Math.random() * ((max - min) + 1)) + min;
                int c = (int)(Math.random() * ((max - min) + 1)) + min;
                int[] temp = {a,b,c};
                neighbours.add(temp);
            }
            return neighbours;
        }

        public static int[] randomproportionedweights(){
            int[] current = {-50,60,32};
            int max = 100;
            int min = -50;
            current [0] = (int)(Math.random() * ((max - min) + 1)) + min;
            current [1] = (int)(Math.random() * ((max - min) + 1)) + min;
            if(current[0] > current[1]){
                int temp = current[1];
                current[1] = current[0];
                current[0] = temp;
            }
            current[2] = (int)(Math.random() * ((max - min) + 1)) + min;
            if(current[0] > current[2]){
                int temp = current[2];
                current[2] = current[1];
                current[1] = current[0];
                current[0] = temp;
            }
            else if (current[1] > current[2]){
                int temp = current[2];
                current[2] = current[1];
                current[1] = temp;
            }
            int[] ordering = {-current[0], current[2], current[1]};

           return ordering;
        }
}
