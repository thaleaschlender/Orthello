public class Piece {
    private int colour;
    private int x;
    private int y;
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

}
