
public class EvaluationFunction2 implements EvaluationFunction {
    Player t;
    int player;
    int opponent;
    int[] earlygame ={10,14,8,9};
    int[] midgame = {10,14,8,9};
    int[] endgame = {10,14,8,9};
    int[] current = earlygame;
    int gameState =1;
    StableDisks e;

    int[][] boardValues;
    public EvaluationFunction2(Player t)
    {
        this.t = t;
        player = t.getColour();
        if (player == 1) opponent = 2;
        else opponent = 1;
        e = new StableDisks(t);

        boardValues = new int[8][8];
        // corners
        boardValues[0][0] = 5;
        boardValues[0][7] = 5;
        boardValues[7][0] = 5;
        boardValues[7][7] = 5;

        // around the corners
        boardValues[1][0] = 1;
        boardValues[0][1] = 1;
        boardValues[1][1] = 1;

        boardValues[7][1] = 1;
        boardValues[6][1] = 1;
        boardValues[6][0] = 1;

        boardValues[1][7] = 1;
        boardValues[1][6] = 1;
        boardValues[0][6] = 1;

        boardValues[7][6] = 1;
        boardValues[6][7] = 1;
        boardValues[6][6] = 1;

        //outer edges
        for(int i = 2; i < 6; i++){
            boardValues[i][0] = 3;
            boardValues[i][7] = 3;
            boardValues[0][i] = 3;
            boardValues[7][i] = 3;
        }

        // edges
        for(int i = 2; i < 6; i++){
            boardValues[i][1] = 2;
            boardValues[i][6] = 2;
            boardValues[1][i] = 2;
            boardValues[6][i] = 2;
        }
        //inner circle
        for(int i = 2; i < 6; i++){
            boardValues[i][2] = 4;
            boardValues[i][5] = 4;
            boardValues[2][i] = 4;
            boardValues[5][i] = 4;
        }
        boardValues[3][3] = 1;
        boardValues[3][4] = 1;
        boardValues[4][3] = 1;
        boardValues[4][4] = 1;
    }

    public int getW1(){return current[0];}
    public int getW2(){return current[1];}
    public int getW3(){return current[2];}
    public int getW4(){return current[3];}
    @Override
    public int evaluateBoard(Board board) {
        if(gameState(board) == 2){
            current = midgame;
            gameState=2;
        }
        else if(gameState(board) == 3 ){
            current = endgame;
            gameState =3;
        }
       return current[0]*dScore(board) + current[1]*dPosition(board) + current[2]*dMoves(board) + current[3] * stableDiscs(board);
    }
    public int gameState(Board board) {
        int gameState = 1;

        for(int i = 0; i < board.getBoard().length; i++) {
            if(board.getBoard()[0][i].hasPiece() || board.getBoard()[i][0].hasPiece() ||
                    board.getBoard()[7][i].hasPiece() || board.getBoard()[i][7].hasPiece()) gameState = 2;
        }

        int cntCorner = 0;

        if(board.getBoard()[0][0].hasPiece()){ if(board.getBoard()[0][0].getColour() == 1){ cntCorner++;}}
        if(board.getBoard()[0][7].hasPiece()){ if(board.getBoard()[0][7].getColour() == 1){ cntCorner++;}}
        if(board.getBoard()[7][7].hasPiece()){ if(board.getBoard()[7][7].getColour() == 1){ cntCorner++;}}
        if(board.getBoard()[7][0].hasPiece()){ if(board.getBoard()[7][0].getColour() == 1){ cntCorner++;}}

        if(cntCorner >= 2) gameState = 3;
        return gameState;
    }

    public int dScore(Board board){
        int ourScore =board.printScore(player);
        int opponentsScore = board.printScore(opponent);
        return (ourScore-opponentsScore);
    }
    public int dPosition(Board board){
        int dPosition = 0;
        for(int i = 0; i < board.getBoard().length; i++){
            for(int j = 0; j < board.getBoard()[0].length; j++) {
                if (board.getBoard()[i][j].getColour() == player) {
                    dPosition += boardValues[i][j];
                }
                else if (board.getBoard()[i][j].getColour() == opponent) {
                    dPosition -= boardValues[i][j];

                }
            }
        }
        return dPosition;
    }
    public int dMoves(Board board){
        int r = - t.numberOfpossibleMoves(board, player);
        return r;

    }
    public int stableDiscs(Board board){
        return e.evaluateBoard(board);
    }


    public void setW1(int w1) {
        this.current[0] = w1;
    }

    public void setW2(int w2) {
        this.current[1] = w2;
    }

    public void setW3(int w3) {
        this.current[2] = w3;
    }
    public void setW4(int w4) {
        this.current[3]= w4;
    }
    public void setW5(int w1) {
        this.midgame[0] = w1;
    }

    public void setW6(int w2) {
        this.midgame[1] = w2;
    }

    public void setW7(int w3) {
        this.midgame[2] = w3;
    }
    public void setW8(int w4) {
        this.midgame[3]= w4;
    }
    public void setW9(int w1) {
        this.endgame[0] = w1;
    }

    public void setW10(int w2) {
        this.endgame[1] = w2;
    }

    public void setW11(int w3) {
        this.endgame[2] = w3;
    }
    public void setW12(int w4) {
        this.endgame[3]= w4;
    }

}