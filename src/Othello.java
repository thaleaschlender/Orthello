public class Othello {
    public static Board board = new Board();
    static boolean playerID = true;
    public static void main (String[] args){
    startGame();

    }
    public static void startGame(){
        //board.print();
        makeMove(3,6);
        board.print();
        System.out.println();
        makeMove(0,0);
        board.print();
    }
    public static void makeMove(int x, int y){
        board.getBoard()[x][y].changeColour(playerID);
        int check;
        if(playerID) check = 2;
        else check =1;
        for(int i = x-1;i <= x+1; i++ )
            for(int j = y-1; j <= y+1; j++)
                if(board.getBoard()[i][j].getColour() == check ) checkLine();

        playerID = !playerID;

    }

    public static void checkLine(int x, int y, int directionX, int directionY) {
        //checks straight line
        //potentially considering the directions as integers
        // create Arraylist with intermediate pieces if there is a !PlayerID stone in the direection, flip them and quit method
    }
    public void flip (ArrayList<Piece> pieces){

    }

    }

