import java.util.ArrayList;

public class Player {
    private int colour;
    protected int checkfor;
    protected static Othello game;
    public static void setGame(Othello g){
        game = g;
    }
    public Player(int colour){
        this.colour = colour;
        if(this.colour == 1) checkfor = 2;
        else if (this.colour==2) checkfor = 1;
    }
    public int getNumber() {
        return checkfor;
    }
    public int getColour(){
        return colour;
    }
    public void play(int x, int y){
        game.makeMove(x,y);
        game.updateBoard();
    }

}