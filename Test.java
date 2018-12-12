import java.util.List;

public class Test {

    private static int testruns = 10;
    static Othello game;
    private static int[][] r = new int[testruns][3];
    private static int cnt = 1;
    static int wf = 0;
    static int bf = 0;
    static int wr = 0;
    static int br = 0;
    //static boolean score = true;%


    public static void results() {

        for (int i = 0; i < testruns; i++) {

                if (game.gameOver) { wr = game.board.printScore(1);  br = game.board.printScore(2);
                    if(wr<br)bf = bf + 1; if(br<wr)wf++;}
                else {game.run();}

                if (i + 1 == testruns && game.gameOver )  printResults(wr, br);//printResults(r);
        }
    }
    public static void printResults(int wr, int br){
        System.out.println(wr + " " + br);restart();
    }
    public static void printResults(int[][] r)
    { for(int i = 0; i < r.length; i++)
        { for(int j = 0; j < r[i].length; j++)
            { System.out.printf("%5d ", r[i][j]); }
            System.out.println(); }
        restart();
    }
    public static void restart(){
        System.out.println("_________________");
        cnt ++;
        if (cnt <= testruns){game.gameOver = false; /*System.exit(0);*/}
        game.board = new Board();
        game.run();
        if(cnt == testruns);// System.out.println(bf);
    }
    public static void setGame(Othello g){
        game = g;
    }
}
