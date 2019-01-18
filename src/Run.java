import java.util.ArrayList;
public class Run {
    static int[][] bWeights = {{0,0,0,0},{0,0,0,1},{0,0,1,0},{0,0,1,1},{0,1,0,0},{0,1,0,1},{0,1,1,0},{0,1,1,1},
            {1,0,0,0},{1,0,0,1},{1,0,1,0},{1,0,1,1},{1,1,0,0},{1,1,0,1},{1,1,1,0},{1,1,1,1}};
    static Othello o = new Othello();
    public static void main (String[] args){
        o.run();

        //int[] weights = tournementHillClimbing();

        //int[][] weights = hillclimbing();
        //int[] weights = {1,5,15};
        //runTest(weights);
       //int[] weights2 = {10,-20,100};
       //runTest(weights2);

        runTest();
       // o.switchToRandom();
        //runTest();

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
        averageB = averageB/ numberOfRuns;
        averageW = averageW/ numberOfRuns;
        System.out.println(" average b " + averageB + " average w " + averageW);
        System.out.println(" wins black " + winsBlack +" wins white " + winsWhite +  " out of " + numberOfRuns + "runs");
    }
    public static void runTest(int[][] w){
            int numberOfRuns= 0;
            int[][] results = new int[100][2];
           while (numberOfRuns < 100) {
               o.white.getEvalFunction().setW1(w[0][0]);
               o.white.getEvalFunction().setW2(w[0][1]);
               o.white.getEvalFunction().setW3(w[0][2]);
               o.white.getEvalFunction().setW4(w[0][3]);
               o.white.getEvalFunction().setW5(w[1][0]);
               o.white.getEvalFunction().setW6(w[1][1]);
               o.white.getEvalFunction().setW7(w[1][2]);
               o.white.getEvalFunction().setW8(w[1][3]);
               o.white.getEvalFunction().setW9(w[2][0]);
               o.white.getEvalFunction().setW10(w[2][1]);
               o.white.getEvalFunction().setW11(w[2][2]);
               o.white.getEvalFunction().setW12(w[2][3]);

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
           for(int i = 0; i< w.length; i++){
               for(int j = 0; j < w[0].length;j++){
                   System.out.print(" " + w[i][j]);
               }
               System.out.println();
           }
           System.out.println(" average b " + averageB + " average w " + averageW);
           System.out.println(" wins black " + winsBlack +" wins white " + winsWhite +  " out of " + numberOfRuns + "runs");
    }
    public static int[] runInternalTest (int[][] w ){
        int numberOfRuns= 0;
        int winsWhite = 0, winsBlack = 0;
        while (numberOfRuns < 16) {
            o.white.getEvalFunction().setW1(w[0][0]);
            o.white.getEvalFunction().setW2(w[0][1]);
            o.white.getEvalFunction().setW3(w[0][2]);
            o.white.getEvalFunction().setW4(w[0][3]);
            o.white.getEvalFunction().setW5(w[1][0]);
            o.white.getEvalFunction().setW6(w[1][1]);
            o.white.getEvalFunction().setW7(w[1][2]);
            o.white.getEvalFunction().setW8(w[1][3]);
            o.white.getEvalFunction().setW9(w[2][0]);
            o.white.getEvalFunction().setW10(w[2][1]);
            o.white.getEvalFunction().setW11(w[2][2]);
            o.white.getEvalFunction().setW12(w[2][3]);

            o.black.getEvalFunction().setW1(bWeights[numberOfRuns][0]);
            o.black.getEvalFunction().setW2(bWeights[numberOfRuns][1]);
            o.black.getEvalFunction().setW3(bWeights[numberOfRuns][2]);
            o.black.getEvalFunction().setW4(bWeights[numberOfRuns][3]);
            int[] temp = o.gameLoop();
            if(temp[0] < temp[1]) winsWhite++;
            else winsBlack++;
            numberOfRuns++;;
        }
        int[] results = {winsBlack,winsWhite};
        return results;
    }

    public static int[][] hillclimbing(){
        int numberOfRuns = 0;
        int max = 15;
        int min = 0;
        int a = (int)(Math.random() * ((max - min) + 1)) + min;
        int b = (int)(Math.random() * ((max - min) + 1)) + min;
        int c = (int)(Math.random() * ((max - min) + 1)) + min;
        int d = (int)(Math.random() * ((max - min) + 1)) + min;
        int[] temp = {a,b,c,d};
        int[][] best = {temp,temp,temp};
       //int[] current = randomproportionedweights();

        boolean unchanged = false;
        int[] bestWins = runInternalTest(best);

        System.out.println(" start ");
        print(best);
        while (numberOfRuns < 20){
            ArrayList<int[][]> neighbours;
            if (unchanged) {
                neighbours = randomNeighbours();
                neighbours.add(best);
            }
           else neighbours = neighbours(best);
           int[][] tem = best;
            for(int[][] n : neighbours ){
                int[] tempo = runInternalTest(n);
               //System.out.println (" testing weights " + n[0] + " " + n[1] + " " + n[2]);
               //System.out.println(" has a score of b " + temp[0] + " w " + temp[1]);
                //if((temp[1]-temp[0]) > (bestScore[1] - bestScore[0])){
                if((temp[1]) > (bestWins[1])){
                    bestWins = tempo;
                    best = n;
                }
            }
            if(best == tem && unchanged) break;
            else if(best == tem && !unchanged) unchanged = true;
            else if(best != tem && !unchanged) unchanged = false;



            System.out.println("unchanged " + unchanged );
            print(best);
             //System.out.println(" best of iteration weights w1 " + best[0] + " w2 " + best[1] + " w3 " + best[2] + " w4 " + best[3] );
             System.out.println("black " + bestWins[0] + " white " + bestWins[1]);
             System.out.println("run number " + numberOfRuns);
            numberOfRuns++;
        }
        System.out.println("FINALLL");
        print(best);
        //System.out.println(" weight 1 = " + best[0] + " w2 " + best[1] + " w3 " + best[2] + " w4 " + best[3]);
        return best;

    }

    public static ArrayList<int[][]> neighbours (int[][] a) {
        ArrayList<int[][]> neighbours = new ArrayList<>();
        neighbours.add(a);
        System.out.println( " CCCC");
        print(a);
        int[][] c = new int[3][4];
        for (int x = 0; x < c.length; x++) {
            System.arraycopy(a[x], 0, c[x], 0, c[x].length);
        };
        for(int j = 0; j < 3 ; j++){
            int[] cur = {a[j][0],a[j][1],a[j][2],a[j][3]};
            int[]current= new int[4];
            System.arraycopy(cur, 0,current,0,cur.length);
            //for(int i = -1; i < 2; i+=2){
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

                int[][] t00 = new int[3][4];
                for (int x = 0; x < t00.length; x++) {
                    System.arraycopy(c[x], 0, t00[x], 0, c[x].length);
                }
                t00[j][0] = t0[0];
                t00[j][1] = t0[1];
                t00[j][2] = t0[2];
                t00[j][3] = t0[3];

                int[][] t01 = new int[3][4];
            for (int x = 0; x < t00.length; x++) {
                System.arraycopy(c[x], 0, t01[x], 0, c[x].length);
            }
                t01[j][0] = t1[0];
                t01[j][1] = t1[1];
                t01[j][2] = t1[2];
                t01[j][3] = t1[3];

                int[][] t02 = new int[3][4];
            for (int x = 0; x < t00.length; x++) {
                System.arraycopy(c[x], 0, t02[x], 0, c[x].length);
            };
                t02[j][0] = t2[0];
                t02[j][1] = t2[1];
                t02[j][2] = t2[2];
                t02[j][3] = t2[3];

                int[][] t03 = new int[3][4];
            for (int x = 0; x < t00.length; x++) {
                System.arraycopy(c[x], 0, t03[x], 0, c[x].length);
            };
                t03[j][0] = t3[0];
                t03[j][1] = t3[1];
                t03[j][2] = t3[2];
                t03[j][3] = t3[3];

                int[][] t04 = new int[3][4];
            for (int x = 0; x < t00.length; x++) {
                System.arraycopy(c[x], 0, t04[x], 0, c[x].length);
            };
                t04[j][0] = t4[0];
                t04[j][1] = t4[1];
                t04[j][2] = t4[2];
                t04[j][3] = t4[3];

                int[][] t05 = new int[3][4];
            for (int x = 0; x < t00.length; x++) {
                System.arraycopy(c[x], 0, t05[x], 0, c[x].length);
            };
                t05[j][0] = t5[0];
                t05[j][1] = t5[1];
                t05[j][2] = t5[2];
                t05[j][3] = t5[3];

                int[][] t06 = new int[3][4];
            for (int x = 0; x < t00.length; x++) {
                System.arraycopy(c[x], 0, t06[x], 0, c[x].length);
            };
                t06[j][0] = t6[0];
                t06[j][1] = t6[1];
                t06[j][2] = t6[2];
                t06[j][3] = t6[3];

                int[][] t07 = new int[3][4];
            for (int x = 0; x < t00.length; x++) {
                System.arraycopy(c[x], 0, t07[x], 0, c[x].length);
            };
                t07[j][0] = t7[0];
                t07[j][1] = t7[1];
                t07[j][2] = t7[2];
                t07[j][3] = t7[3];

                int[][] t08 = new int[3][4];
            for (int x = 0; x < t00.length; x++) {
                System.arraycopy(c[x], 0, t08[x], 0, c[x].length);
            };
                t08[j][0] = t8[0];
                t08[j][1] = t8[1];
                t08[j][2] = t8[2];
                t08[j][3] = t8[3];
                int[][] t09 = new int[3][4];
            for (int x = 0; x < t00.length; x++) {
                System.arraycopy(c[x], 0, t09[x], 0, c[x].length);
            };
                t09[j][0] = t9[0];
                t09[j][1] = t9[1];
                t09[j][2] = t9[2];
                t09[j][3] = t9[3];
                int[][] t010 = new int[3][4];
            for (int x = 0; x < t00.length; x++) {
                System.arraycopy(c[x], 0, t010[x], 0, c[x].length);
            };
                t010[j][0] = t10[0];
                t010[j][1] = t10[1];
                t010[j][2] = t10[2];
                t010[j][3] = t10[3];
                int[][] t011 = new int[3][4];
            for (int x = 0; x < t00.length; x++) {
                System.arraycopy(c[x], 0, t011[x], 0, c[x].length);
            };
                t011[j][0] = t11[0];
                t011[j][1] = t11[1];
                t011[j][2] = t11[2];
                t011[j][3] = t11[3];
                int[][] t012 = new int[3][4];
            for (int x = 0; x< t00.length; x++) {
                System.arraycopy(c[x], 0, t012[x], 0, c[x].length);
            };
                t012[j][0] = t12[0];
                t012[j][1] = t12[1];
                t012[j][2] = t12[2];
                t012[j][3] = t12[3];
                int[][] t013 = new int[3][4];
            for (int x = 0; x < t00.length; x++) {
                System.arraycopy(c[x], 0, t013[x], 0, c[x].length);
            };
                t013[j][0] = t13[0];
                t013[j][1] = t13[1];
                t013[j][2] = t13[2];
                t013[j][3] = t13[3];
                int[][] t014 = new int[3][4];
            for (int x = 0; x < t00.length; x++) {
                System.arraycopy(c[x], 0, t014[x], 0, c[x].length);
            };
                t014[j][0] = t14[0];
                t014[j][1] = t14[1];
                t014[j][2] = t14[2];
                t014[j][3] = t14[3];
                neighbours.add(t00);
                neighbours. add(t01);
                neighbours. add(t02);
                neighbours. add(t03);
                neighbours. add(t04);
                neighbours. add(t05);
                neighbours. add(t06);
                neighbours. add(t07);
                neighbours. add(t08);
                neighbours. add(t09);
                neighbours. add(t010);
                neighbours. add(t011);
                neighbours. add(t012);
                neighbours. add(t013);
                neighbours. add(t014);
            }
           //for(int[][] m : neighbours){
            //System.out.println("_____");
            //print(m);
          // }
        //System.out.println("CCCCCC");
        //print(c);





        //}
        for(int j = 0; j < 20; j++){
            int max = 15;
            int min = 0;
            int f = (int)(Math.random() * ((max - min) + 1)) + min;
            int b = (int)(Math.random() * ((max - min) + 1)) + min;
            int e = (int)(Math.random() * ((max - min) + 1)) + min;
            int d = (int)(Math.random() * ((max - min) + 1)) + min;
            int[] temp = {f,b,e,d};
            int[][]t = {temp,temp,temp,temp};
            neighbours.add(t);
        }
        return neighbours;
    }

        public static  ArrayList<int[][]> randomNeighbours(){
            ArrayList<int[][]> neighbours = new ArrayList<>();
        for(int i = 0; i < 30; i++){
                int max = 15;
                int min = 00;
                int a = (int)(Math.random() * ((max - min) + 1)) + min;
                int b = (int)(Math.random() * ((max - min) + 1)) + min;
                int c = (int)(Math.random() * ((max - min) + 1)) + min;
                int d = (int)(Math.random() * ((max - min) + 1)) + min;
                int[] temp = {a,b,c,d};
                int[][] t = {temp,temp,temp};
                neighbours.add(t);
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


       /* public static int[] tournementHillClimbing(){
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
                    System.out.println(" weights " +  i[0] + " " + i[1] + " " + i[2] +" "+i[3] + " has an evaluation of " + eval);
                    if(eval <= worstScore){
                        worstScore = eval;
                        worst = i;
                    }

                }
                System.out.println(" remove " + worst[0] + " " + worst[1] + " " + worst[2] + " " + worst[3]);
                tournement.remove(worst);
            }
            return tournement.get(0);
        }*/
       public static void print (int[][]a){
           for(int i = 0; i< a.length; i++){
               for(int j = 0; j < a[0].length; j++){
                   System.out.print(" " + a[i][j]);
               }
               System.out.println();
           }
       }
}
