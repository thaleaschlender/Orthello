
public class Board {
    Piece[][] board;
    int currentWinner = 0;
    public Board(){
        board = new Piece[8][8];
        for(int i = 0; i < board.length; i++)
            for(int j = 0; j < board[0].length; j++)
                board[i][j] = new Piece(0,i,j);


        board[3][3].changeColour(2);
        board[3][4].changeColour(1);
        board[4][3].changeColour(1);
        board[4][4].changeColour(2);
    }
    public Board(Board b) {
        this.board = new Piece[8][8];
        for (int i = 0; i < this.board.length; i++){
            for (int j = 0; j < this.board[0].length; j++) {
                this.board[i][j] = new Piece(0, i, j);
                this.board[i][j].changeColour(b.getBoard()[i][j].getColour());
            }
        }

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
    public int getWinner(){
        int w = 0; int b = 0;
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[0].length; j++){
                if(board[i][j].getColour()== 1) w++;
                if(board[i][j].getColour()== 2) b++;
            }
        }
        if(w > b) return w;
        else if ( b < w) return b;
        else return 0;
    }
    public int printScore(int p){
        int w = 0; int b = 0;
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[0].length; j++){
                if(board[i][j].getColour()== 1) w++;
                if(board[i][j].getColour()== 2) b++;
            }
        }
        if(p == 1) return w;
        else  return b;
    }
    public boolean gameOver(){
        boolean gameover = true;
        for(int i = 0; i < board.length; i++)
            for(int j = 0; j < board[0].length; j++)
                if(board[i][j].getColour() != 0) gameover= false;
        return gameover;
    }
}
