public class Piece {
    private int colour;
    private int x;
    private int y;
    private int score;
    private boolean valid;
    public Piece (int colour,int x, int y) {
        this.colour = colour;
        this.x = x;
        this.y=y;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public void changeColour (int colour){
        this.colour = colour;
    }
    public int getColour (){
        return colour;
    }
    public void flip(){
        if(colour == 1) colour =2;
        else if(colour == 2) colour =1;
    }
    public void setScore(int score){
        this.score=score;
    }
    public int getScore(){
        return score;
    }
    public boolean getValidity(){
        return valid;
    }
    public void setValid(boolean v){
        this.valid=v;
    }
}