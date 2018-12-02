public class Test {

    private static int testruns = 100;
    static Othello game;

    public static void main(String args[]) {
    }


    private void results() {
        int sp1 = 0;
        int sp2 = 0;

        for (int i = 0; i < testruns; i++) {
            Board board = new Board();
            Player white = new TreeSearch(1, true);
            Player black = new TreeSearch(2, true);
            Player current = black;

            if (!game.gameOver) {current.play(0, 0); }
            else {sp1 = game.board.printScore(1);  }
        }
    }
}