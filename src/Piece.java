public class Piece {
    private int colour;

    public Piece (int colour){
        this.colour = colour;
    }

    public void changeColour (int colour){
        this.colour = colour;
    }
    public void changeColour(boolean colour){
        if(colour == true) this.colour = 1;
        else this.colour = 2;
    }
    public int getColour (){
        return colour;
    }

}
