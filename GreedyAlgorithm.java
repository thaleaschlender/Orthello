import java.util.ArrayList;

public class GreedyAlgorithm extends Player {
    public GreedyAlgorithm(int c, boolean b){
        super(c, b);
    }
    public void play(int x, int y){
        mostFlippedPlayer();
        game.updateBoard();
    }
    public void mostFlippedPlayer(){
        ArrayList<Piece> possibilities = game.possibleMoves();
        int max = 0;
        int best = 0;
        for (int i = 0; i< possibilities.size();i++) {
            if(game.validMove(possibilities.get(i).getX(),possibilities.get(i).getY())> max) {
                best = i;
                max = game.validMove(possibilities.get(i).getX(),possibilities.get(i).getY());
            }
        }
        game.makeMove(possibilities.get(best).getX(),possibilities.get(best).getY());
    }
}