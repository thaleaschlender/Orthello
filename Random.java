import java.util.ArrayList;

public class Random extends Player {
    public Random(int c, boolean b){
        super(c, b);
    }
    public void play(int x, int y){
        randomPlayer();
        game.updateBoard();
    }
    public void randomPlayer(){
        ArrayList<Piece> possibilities = game.possibleMoves();
        int r= (int) Math.random()*(possibilities.size());
        game.makeMove(possibilities.get(r).getX(), possibilities.get(r).getY());
    }
}