import java.util.ArrayList;

public class Run {

    static Othello o = new Othello();
    public static void main (String[] args){
        o.run();

        //int[] weights = tournementHillClimbing();
        //int[] weights = hillclimbing();
        //int[] weights = {1,5,15}; //TODO : test these weights against
        //runTest(weights);
       //int[] weights2 = {10,-20,100};
       //runTest(weights2);

        runTest();

    }
    public static void runTest(){
        int numberOfRuns= 0;
        int[][] results = new int[1000][2];
        while (numberOfRuns <1000) {
            int[] temp = o.gameLoop();
            results[numberOfRuns][0] = temp[0];
            results[numberOfRuns][1] = temp[1];
            numberOfRuns++;
            System.out.println("iteration: " + numberOfRuns);
            System.out.println("black: " + temp[0] + " white: " + temp[1]);
        }
        double averageB = 0;
        double averageW = 0;
        int winsWhite = 0, winsBlack = 0;
        for(int i = 0; i < results.length; i++){
            averageB += results[i][0];
            averageW += results[i][1];
            if(results[i][0] < results [i][1]) winsWhite++;
            if(results[i][0] > results [i][1]) winsBlack++;
            // System.out.println(" b " + results[i][0] + " w " + results[i][1]);
        }
        averageB = averageB/ results.length;
        averageW = averageW/ results.length;
        System.out.println(" average b " + averageB + " average w " + averageW);
        System.out.println(" wins black " + winsBlack +" wins white " + winsWhite +  " out of " + numberOfRuns + "runs");
    }
    public static void runTest(int[] weights){
            int numberOfRuns= 0;
            int[][] results = new int[200][2];
           while (numberOfRuns < 200) {
               o.white.getEvalFunction().setW1(weights[0]);
               o.white.getEvalFunction().setW2(weights[1]);
               o.white.getEvalFunction().setW3(weights[2]);
               o.white.getEvalFunction().setW4(weights[3]);
               int[] temp = o.gameLoop();
               results[numberOfRuns][0] = temp[0];
               results[numberOfRuns][1] = temp[1];

               numberOfRuns++;
           }
           double averageB = 0;
           double averageW = 0;
           int winsWhite = 0, winsBlack = 0;
           for(int i = 0; i < results.length; i++){
               averageB += results[i][0];
               averageW += results[i][1];
               if(results[i][0] < results [i][1]) winsWhite++;
               if(results[i][0] > results [i][1]) winsBlack++;
               System.out.println(" b " + results[i][0] + " w " + results[i][1]);
           }
           averageB = averageB/ results.length;
           averageW = averageW/ results.length;
           System.out.println("with weights w1 " + weights[0] + " w2 " + weights[1] + " w3 " + weights[2] + " w4 " + weights[3]);
           System.out.println(" average b " + averageB + " average w " + averageW);
           System.out.println(" wins black " + winsBlack +" wins white " + winsWhite +  " out of " + numberOfRuns + "runs");
    }
    public static double[] runInternalTest (int[] w ){
        int numberOfRuns= 0;
        double averageB = 0, averageW = 0;
        int winsWhite = 0, winsBlack = 0;
        while (numberOfRuns < 10) {
            o.white.getEvalFunction().setW1(w[0]);
            o.white.getEvalFunction().setW2(w[1]);
            o.white.getEvalFunction().setW3(w[2]);
            o.white.getEvalFunction().setW4(w[3]);


            int[] temp = o.gameLoop();
            averageB += temp[0];
            averageW += temp[1];
            if(temp[0] < temp[1]) winsWhite++;
            else winsBlack++;
            numberOfRuns++;
        }
        averageB = averageB/ (numberOfRuns);
        averageW = averageW/ (numberOfRuns);
        //double[] results = {averageB, averageW};
        double[] results = {winsBlack,winsWhite};
        return results;
    }

    public static int[] hillclimbing(){
        int numberOfRuns = 0;

        int max = 15;
        int min = 0;
        int a = (int)(Math.random() * ((max - min) + 1)) + min;
        int b = (int)(Math.random() * ((max - min) + 1)) + min;
        int c = (int)(Math.random() * ((max - min) + 1)) + min;
        int d = (int)(Math.random() * ((max - min) + 1)) + min;
        int[] best = {a,b,c,d};
       //int[] current = randomproportionedweights();

        boolean unchanged = false;
        int[] helper = o.gameLoop();
        double[] bestScore = new double[2];
        bestScore[0] = (double) helper[0];
        bestScore[1] = (double) helper[1];
        double[] bestWins= new double[2];
        if(bestScore[0] < bestScore[1]){
            bestWins[1] = 1;
            bestWins[0] = 0;
        }


        System.out.println(" start " + best[0] + " " + best[1] + " " + best[2] + " " + best[3]);
        while (numberOfRuns < 20){
            ArrayList<int[]> neighbours;
            if (unchanged) {
                neighbours = randomNeighbours();
                neighbours.add(best);
            }
           else neighbours = neighbours(best);
           int[] tem = best;
            for(int[] n : neighbours ){
                double[] temp = runInternalTest(n);
               //System.out.println (" testing weights " + n[0] + " " + n[1] + " " + n[2]);
               //System.out.println(" has a score of b " + temp[0] + " w " + temp[1]);
                //if((temp[1]-temp[0]) > (bestScore[1] - bestScore[0])){
                if((temp[1]-temp[0]) > (bestWins[1] - bestWins[0])){
                    bestScore = temp;
                    bestWins = temp;
                    best = n;
                }
            }
            if(best == tem && unchanged) break;
            else if(best == tem && !unchanged) unchanged = true;
            else if(best != tem && !unchanged) unchanged = false;



            System.out.println("unchanged " + unchanged );
             System.out.println(" best of iteration weights w1 " + best[0] + " w2 " + best[1] + " w3 " + best[2] + " w4 " + best[3] );
             System.out.println(" with scores b " + bestScore[0] + " w " + bestScore[1] );
             System.out.println("run number " + numberOfRuns);
            numberOfRuns++;
        }
        System.out.println(" weight 1 = " + best[0] + " w2 " + best[1] + " w3 " + best[2] + " w4 " + best[3]);
        return best;

    }

    public static ArrayList<int[]> neighbours (int[] current) {
        ArrayList<int[]> neighbours = new ArrayList<>();
       // for (int i = -1; i < 4; i=i+2) {
        int i = 1;
            int[] t0 = {current[0], current[1], current[2], current[3] + i};
            int[] t1 = {current[0], current[1], current[2] + i, current[3]};
            int[] t2 = {current[0], current[1], current[2] + i, current[3] + i};
            int[] t3 = {current[0], current[1] + i, current[2], current[3]};
            int[] t4 = {current[0], current[1] + i, current[2], current[3] + i};
            int[] t5 = {current[0], current[1] + i, current[2] + i, current[3]};
            int[] t6 = {current[0], current[1] + i, current[2] + i, current[3] + i};

            int[] t7 = {current[0] + i, current[1], current[2], current[3]};
            int[] t8 = {current[0] + i, current[1], current[2], current[3] + i};
            int[] t9 = {current[0] + i, current[1], current[2] + i, current[3]};
            int[] t10 = {current[0] + i, current[1], current[2] + i, current[3] + i};
            int[] t11 = {current[0] + i, current[1] + i, current[2], current[3]};
            int[] t12 = {current[0] + i, current[1] + i, current[2], current[3] + i};
            int[] t13 = {current[0] + i, current[1] + i, current[2] + i, current[3]};
            int[] t14 = {current[0] + i, current[1] + i, current[2] + i, current[3] + i};

            neighbours.add(t0);
            neighbours. add(t1);
            neighbours. add(t2);
            neighbours. add(t3);
            neighbours. add(t4);
            neighbours. add(t5);
            neighbours. add(t6);
            neighbours. add(t7);
            neighbours. add(t8);
            neighbours. add(t9);
            neighbours. add(t10);
            neighbours. add(t11);
            neighbours. add(t12);
            neighbours. add(t13);
            neighbours. add(t14);



        //}
        for(int j = 0; j < 20; j++){
            int max = 15;
            int min = 0;
            int a = (int)(Math.random() * ((max - min) + 1)) + min;
            int b = (int)(Math.random() * ((max - min) + 1)) + min;
            int c = (int)(Math.random() * ((max - min) + 1)) + min;
            int d = (int)(Math.random() * ((max - min) + 1)) + min;
            int[] temp = {a,b,c,d};
            neighbours.add(temp);
        }
        return neighbours;
    }

        public static  ArrayList<int[]> randomNeighbours(){
            ArrayList<int[]> neighbours = new ArrayList<>();
        for(int i = 0; i < 30; i++){
                int max = 15;
                int min = 00;
                int a = (int)(Math.random() * ((max - min) + 1)) + min;
                int b = (int)(Math.random() * ((max - min) + 1)) + min;
                int c = (int)(Math.random() * ((max - min) + 1)) + min;
                int d = (int)(Math.random() * ((max - min) + 1)) + min;
                int[] temp = {a,b,c,d};
                neighbours.add(temp);
            }
            return neighbours;
        }

        public static int[] randomproportionedweights(){
            int[] current = {-50,60,32};
            int max = 15;
            int min = 0;
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
            int[] ordering = {current[2], current[1], -current[0]};

           return ordering;
        }


        public static int[] tournementHillClimbing(){
            ArrayList<int[]> tournement = new ArrayList<>();
            for(int i= 0; i < 3 ; i++){
                tournement.add(hillclimbing());
            }
            int[] worst = null;
            double worstScore = 100;
            while(tournement.size() > 1){
                for(int[] i: tournement){
                    double[] simulation = runInternalTest(i);
                    double eval = (simulation[1] - simulation[0]);
                    System.out.println(" weights " +  i[0] + " " + i[1] + " " + i[2] + " has an evaluation of " + eval);
                    if(eval <= worstScore){
                        worstScore = eval;
                        worst = i;
                    }

                }
                System.out.println(" remove " + worst[0] + " " + worst[1] + " " + worst[2]);
                tournement.remove(worst);
            }
            return tournement.get(0);
        }
}
