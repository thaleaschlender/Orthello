import java.util.ArrayList;
public class MiniMax extends Player{
    public MiniMax (int c){
        super(c);
    }
    @Override
    public void play (int x, int y){
        Piece piece = minimax(game.board,0,3,null,checkfor);
        game.makeMove(piece.getX(),piece.getY());
        game.updateBoard();
    }
    private Piece minimax (Board board, int depth, int max_depth, Piece piece, int current) {
        //current is black or white, what we check
        Piece chosen_move = piece;
        int chosen_score;
        if (depth == max_depth) {
            chosen_score = evaluation(board);
            chosen_move = piece;
        }
        else {
            ArrayList<Piece> possible_moves = possibleMoves(board, current);
            if (possible_moves.size() == 0)
                chosen_score = evaluation(board);//if its for our player -> score should be really bad, for our opponent really high
            else {
                int best_score;
                Piece best_move = possible_moves.get(0);
                if(current == 1){
                    current = 2;
                    best_score = 2147483647;
                }
                else {
                    current = 1;
                    best_score = 0;
                }
                for (int i = 0; i < possible_moves.size(); i++) {
                    Board new_board = new Board(board);
                    new_board = makeMove( possible_moves.get(i).getX(), possible_moves.get(i).getY(),new_board,current);
                    int score = minimax(new_board, depth + 1, max_depth,possible_moves.get(i),current).getScore();
                    if(current == 1){
                        if (score > best_score) {
                            best_score = score;
                            best_move = possible_moves.get(i);
                            best_move.setScore(best_score);
                        }
                    }
                    else if (current == 2){
                        if (score < best_score) {
                            best_score = score;
                            best_move = possible_moves.get(i);
                            best_move.setScore(best_score);
                        }
                    }
                }
                chosen_score = best_score;
                chosen_move = best_move;
                chosen_move.setScore(best_score);

            }
        }
        return chosen_move;
    }
    private Board makeMove(int x, int y, Board board, int current){
        ArrayList<Piece> flip;
        if(board.getBoard()[x][y].getColour()!=0) return board;
        int colour;
        if(current == 1) colour = 1;
        else colour = 2;
        board.getBoard()[x][y].changeColour(colour);
        int check = current;
        for(int i = x-1;i <= x+1; i++ )
            for (int j = y - 1; j <= y + 1 ; j++) {
                if (i>-1 && i<8 && j >-1 && j<8 &&((i-x)!=0||(j-y)!=0)&&(board.getBoard()[i][j].getColour() == check)){
                    flip = checkLine(x, y, (i - x), (j - y), check,board);
                    if(flip.size() != 0){
                        for(Piece f: flip) f.flip();
                    }

                }}
        return board;
    }
    private int validMove(int x, int y, Board board, int current) {
        int valid = 0;
        if (board.getBoard()[x][y].getColour() != 0) return 0;
        int colour;
        if(current == 1) colour = 2;
        else colour = 1;
        board.getBoard()[x][y].changeColour(colour);
        int check = current;
        for (int i = x - 1; i <= x + 1; i++){
            for (int j = y - 1; j <= y + 1; j++) {
                if (i > -1 && i < 8 && j > -1 && j < 8
                        && ((i - x) != 0 || (j - y) != 0) &&
                        (board.getBoard()[i][j].getColour() == check) && checkLine(x, y, (i - x), (j - y), check,board).size() != 0) {
                    valid += checkLine(x, y, (i - x), (j - y), check,board).size();

                }
            }
        }
        board.getBoard()[x][y].changeColour(0);
        return valid;
    }
    private ArrayList<Piece> possibleMoves(Board board,int current){
        ArrayList<Piece> possibleMoves = new ArrayList<Piece>();
        for(int i =0; i < board.getBoard().length;i++){
            for(int j=0; j < board.getBoard()[i].length;j++){
                if(validMove(i,j,board,current)>0){
                    possibleMoves.add(board.getBoard()[i][j]);
                }
            }
        }
        return possibleMoves;
    }
    private ArrayList<Piece> checkLine(int x, int y, int directionX, int directionY, int check, Board board) {
        ArrayList<Piece> flippable = new ArrayList<>();
        boolean finished = false;
        if(x+directionX<0 || x+directionX>7 || y+directionY< 0 || y+directionY >7) return new ArrayList<>();
        while(!finished){
            if(x+directionX<0 || x+directionX>7 || y+directionY< 0 || y+directionY >7) return new ArrayList<>();
            else{
                x = x + directionX;
                y = y + directionY;
                if(board.getBoard()[x][y].getColour()== 0) return new ArrayList<>();
                else if(board.getBoard()[x][y].getColour()==check) flippable.add(board.getBoard()[x][y]);
                else if(board.getBoard()[x][y].getColour()!=check) finished = true;
                else return new ArrayList<>();

            }}
        return flippable;
    }
    private int evaluation(Board board){
        int w = 0; int b = 0;
        for(int i = 0; i < board.getBoard().length; i++){
            for(int j = 0; j < board.getBoard()[0].length; j++){
                if(board.getBoard()[i][j].getColour()== 1) w++;
                if(board.getBoard()[i][j].getColour()== 2) b++;
            }
        }
        int evaluation = (w-b);
        if(board.getBoard()[0][0].getColour()==2) evaluation += 100;
        if(board.getBoard()[0][7].getColour()==2) evaluation += 100;
        if(board.getBoard()[7][0].getColour()==2) evaluation += 100;
        if(board.getBoard()[7][7].getColour()==2) evaluation += 100;

        if(board.getBoard()[0][1].getColour()==2) evaluation -= 100;
        if(board.getBoard()[1][0].getColour()==2) evaluation -= 100;
        if(board.getBoard()[1][1].getColour()==2) evaluation -= 100;

        if(board.getBoard()[6][0].getColour()==2) evaluation -= 100;
        if(board.getBoard()[7][1].getColour()==2) evaluation -= 100;
        if(board.getBoard()[6][1].getColour()==2) evaluation -= 100;

        if(board.getBoard()[0][6].getColour()==2) evaluation -= 100;
        if(board.getBoard()[1][6].getColour()==2) evaluation -= 100;
        if(board.getBoard()[1][7].getColour()==2) evaluation -= 100;

        if(board.getBoard()[7][6].getColour()==2) evaluation -= 100;

        if(board.getBoard()[6][7].getColour()==2) evaluation -= 100;
        if(board.getBoard()[6][6].getColour()==2) evaluation -= 100;

        return evaluation;
        //Assuming the aim of white is to have the biggest ratio?
    }
}