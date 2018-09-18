public class Board {
    Piece[][] board;

    public Board(){
        board = new Piece[8][8];
        for(int i = 0; i < board.length; i++)
            for(int j = 0; j < board[0].length; j++)
                board[i][j] = new Piece(0);


        board[3][3].changeColour(2);
        board[3][4].changeColour(1);
        board[4][3].changeColour(1);
        board[4][4].changeColour(2);
    }
    public Piece[][] getBoard (){
        return board;
    }
    public  void print (){
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[0].length; j++){
                System.out.print(board[i][j].getColour());
                //System.out.print(board[i][j]);
            }
            System.out.println();
        }
    }
    public void printScore(){

    }
}
