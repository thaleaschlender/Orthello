import javafx.application.Application;

public class Run {
    static double winner = 0;
    static int numberOfRuns;
    public static void main (String[] args){
        runTest();
        System.out.println("avg winner & better eval " + (winner/numberOfRuns) );
    }
    public static void runTest(){
       while (numberOfRuns < 100) {
           Othello o = new Othello();
           o.run();
           if (o.gameover)
               winner += o.winner;
           numberOfRuns++;
       }
       //literally does one fucking run and gives up

    }
}
