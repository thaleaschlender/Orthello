public class EvaluationFunction1 implements EvaluationFunction {
    Player t;
    int w1 = 1;
    int w2 = 1;
    int w3 = 1;
    int w4 = 1;
    int[][] boardValues;
    StableDisks e;
    int player;
    int opponent;
    public EvaluationFunction1(Player t)
    {
        this.t = t;
        this.player = t.getColour();
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

    @Override
    public int evaluateBoard(Board board) {
        return w1*dScore(board) + w2*dPosition(board) + w3*dMoves(board) + w4 * 0;//stableDiscs(board);

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
        return t.numberOfpossibleMoves(board, opponent) - t.numberOfpossibleMoves(board, player);

    }
    public int stableDiscs(Board board){
        return e.evaluateBoard(board);
        // maybe this doesnt work because we have to change the colour for e too
        //return 0;
    }
    public int getW1(){return w1;}
    public int getW2(){return w2;}
    public int getW3(){return w3;}
    public void setPlayerColour (int n){
        this.player = n;
        if (player == 1) opponent = 2;
        else opponent = 1;
        e.setPlayer(n);
    }
    public int getW4() {
        return 0;
    }
    public void setW1(int w1) {
        this.w1 = w1;
    }
    public void setW2(int w2) {
        this.w2 = w2;
    }
    public void setW3(int w3) {
        this.w3 = w3;
    }
    public void setW4(int w4) {

    }
}
