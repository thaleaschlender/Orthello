import java.util.ArrayList;

public class Othello {
    public static Board board = new Board();
    static boolean playerID = true;
    public static void main (String[] args){
    startGame();

    }
    public static void startGame(){
        //board.print();
        makeMove(3,2);

        makeMove(4,2);
        makeMove(4,1);
        makeMove(5,1);

       // makeMove(0,0);
       // board.print();
    }
    public static boolean makeMove(int x, int y){
        boolean valid = false;
        board.getBoard()[x][y].changeColour(playerID);
        int check;
        if(playerID) check = 2;
        else check =1;
        //check array out of bounds
        for(int i = x-1;i <= x+1; i++ ){
            for(int j = y-1; j <= y+1; j++){
                if(board.getBoard()[i][j].getColour() == check ){
                   if(checkLine(x,y,(i-x),(j-y),check)) valid = true;
                }}}

        playerID = !playerID;
        if(!valid) {
            board.getBoard()[x][y].changeColour(0);
            playerID = !playerID;
            System.out.println("INVALID");
        }
        else
            System.out.println("VALID");
        board.print();
        return valid;
    }
//direction x is vertical, direction y horizontal
    public static boolean checkLine(int x, int y, int directionX, int directionY, int check) {
        //checks straight line
        //potentially considering the directions as integers
        // create Arraylist with intermediate pieces if there is a !PlayerID stone in the direection, flip them and quit method
        ArrayList<Piece> tobeflipped = new ArrayList<>();
        boolean finished = false;
        while(!finished&& x>-1 && x < 8 && y > -1 && y <8){
            if(board.getBoard()[x+directionX][y+directionY].getColour()==check){
                tobeflipped.add(board.getBoard()[x+directionX][y+directionY]);
                x = x + directionX;
                y = y + directionY;
            }
            else if(board.getBoard()[x+directionX][y+directionY].getColour()== 0)
                return false;
            else finished = true;
        }
        flip(tobeflipped);

        System.out.println("CHECK " + x + " " + y + "with direction " + directionX + " " + directionY);
        return true;
    }
    public static void flip (ArrayList<Piece> pieces){
        for(int i = 0; i < pieces.size();i++){
            pieces.get(i).flip();

        }
    }

    }

