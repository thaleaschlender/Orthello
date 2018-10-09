import java.util.ArrayList;

public class Player {
    private int colour;
    private int checkfor;
    private static Othello game;
    public static void setGame(Othello g){
        game = g;
    }
    public Player(int colour){
        this.colour = colour;
        if(this.colour == 1) checkfor = 2;
        else if (this.colour==2) checkfor = 1;
    }

    public int getNumber(){
        return checkfor;
    }

    public int getColour(){
        //if(colour ==1 ) System.out.println("white");
        //else System.out.println("black");
        return colour;
    }

    public void randomPlayer(){
        ArrayList<Piece> possibilities = game.possibleMoves();
        int r= (int) Math.random()*(possibilities.size());
        game.makeMove(possibilities.get(r).getX(), possibilities.get(r).getY());
    }
    //greedy Algorithm
    public void mostFlippedPlayer(){
        if(colour ==1 ) System.out.println("white");
        else System.out.println("black");
        ArrayList<Piece> possibilities = game.possibleMoves();
        int max = 0;
        int best = 0;
        for (int i = 0; i< possibilities.size();i++) {
            if(game.validMove(possibilities.get(i).getX(),possibilities.get(i).getY())> max) {
                best = i;
                max = game.validMove(possibilities.get(i).getX(),possibilities.get(i).getY());
            }
        }
        System.out.println("best position: "+ possibilities.get(best).getX() + " "+ possibilities.get(best).getY() + " with flipped "+max);
        game.makeMove(possibilities.get(best).getX(),possibilities.get(best).getY());
    }
}
