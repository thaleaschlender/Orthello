import java.util.ArrayList;

public class Run {

    static Othello o = new Othello();
    public static void main (String[] args){
        o.run();

        int[] weights = tournementHillClimbing();
        //int[] weights = {2,3,5}; //TODO : test these weights against
        runTest(weights);
       //int[] weights2 = {10,-20,100};
       //runTest(weights2);

        //runTest();

    }
    public static void runTest(){
        int numberOfRuns= 0;
        int[][] results = new int[100][2];
        while (numberOfRuns <100) {
            int[] temp = o.gameLoop();
            results[numberOfRuns][0] = temp[0];
            results[numberOfRuns][1] = temp[1];
            numberOfRuns++;
        }
        double averageB = 0;
        double averageW = 0;
        for(int i = 0; i < results.length; i++){
            averageB += results[i][0];
            averageW += results[i][1];
            // System.out.println(" b " + results[i][0] + " w " + results[i][1]);
        }
        averageB = averageB/ results.length;
        averageW = averageW/ results.length;
        System.out.println(" average b " + averageB + " average w " + averageW);
    }
    public static void runTest(int[] weights){
            int numberOfRuns= 0;
            int[][] results = new int[200][2];
           while (numberOfRuns < 200) {
               o.white.getEvalFunction().setW1(weights[0]);
               o.white.getEvalFunction().setW2(weights[1]);
               o.white.getEvalFunction().setW3(weights[2]);
               int[] temp = o.gameLoop();
               results[numberOfRuns][0] = temp[0];
               results[numberOfRuns][1] = temp[1];

               numberOfRuns++;
           }
           double averageB = 0;
           double averageW = 0;
           for(int i = 0; i < results.length; i++){
               averageB += results[i][0];
               averageW += results[i][1];
               System.out.println(" b " + results[i][0] + " w " + results[i][1]);
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

        while (numberOfRuns < 5) {
            o.white.getEvalFunction().setW1(w[0]);
            o.white.getEvalFunction().setW2(w[1]);
            o.white.getEvalFunction().setW3(w[2]);


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
        int max = 15;
        int min = 0;
      int a = (int)(Math.random() * ((max - min) + 1)) + min;
        int b = (int)(Math.random() * ((max - min) + 1)) + min;
        int c = (int)(Math.random() * ((max - min) + 1)) + min;
        //int[] current = {a,b,c};
       //int[] current = randomproportionedweights();
        int[] current = {2,3,5};
        boolean unchanged = false;

        int[] bestScore = o.gameLoop();
        int[] best = current;
        System.out.println(" start " + current[0] + " " + current[1] + " " + current[2]);
        while (numberOfRuns < 20){
            ArrayList<int[]> neighbours;
            if (unchanged) neighbours=  randomNeighbours();
           else neighbours = neighbours(current);
           int internalrun = 0;
            for(int[] n : neighbours ){
                int[] temp = runInternalTest(n);
               //System.out.println (" testing weights " + n[0] + " " + n[1] + " " + n[2]);
               //System.out.println(" has a score of b " + temp[0] + " w " + temp[1]);
                if((temp[1]-temp[0]) > (bestScore[1] - bestScore[0])){
                //if(temp[1] > bestScore[1]){
                    bestScore = temp;
                    best = n;
                }
                internalrun++;
                System.out.println("internal " + internalrun);
            }
            if(current == best && unchanged) break;
            else if(current == best) unchanged = true;
            else if(current != best) unchanged = false;
            current = best;

            System.out.println("unchanged " + unchanged);
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
        for (int i = -1; i < 4; i=i+2) {
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
        for(int i = 0; i < 20; i++){
            int max = 15;
            int min = 0;
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
        for(int i = 0; i < 30; i++){
                int max = 15;
                int min = 00;
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
            for(int i= 0; i < 10 ; i++){
                tournement.add(hillclimbing());
            }
            int[] worst = null;
            int worstScore = 100;
            while(tournement.size() > 1){
                for(int[] i: tournement){
                    int[] simulation = runInternalTest(i);
                    int eval = (simulation[1] - simulation[0]);
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
