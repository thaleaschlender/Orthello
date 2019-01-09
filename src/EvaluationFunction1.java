public class EvaluationFunction1 implements EvaluationFunction {
    Player t;
    int w1 = 1;
    int w2 = 1;
    int w3 = 1;
    public EvaluationFunction1(Player t){
        this.t = t;
    }
    public int getW1(){return w1;}
    public int getW2(){return w2;}
    public int getW3(){return w3;}
    @Override
    public int evaluateBoard(Board board) {
        int player = t.getColour();
        int opponent;
        int evaluation = 0;
        int dMoves = 0;
        int dPosition = 0;
        int dScore = 0;


        if (player == 1) opponent = 2;
        else
            opponent = 1;

        dScore = Math.abs(board.printScore(player) - board.printScore(opponent));

        if(board.getBoard()[0][0].getColour()==player) dPosition += 100;
        if(board.getBoard()[0][7].getColour()==player) dPosition += 100;
        if(board.getBoard()[7][0].getColour()==player) dPosition += 100;
        if(board.getBoard()[7][7].getColour()==player) dPosition += 100;

        dMoves = Math.abs(t.numberOfpossibleMoves(board, player) - t.numberOfpossibleMoves(board, opponent));

        evaluation = w1*dScore + w2*dPosition + w3*dMoves;

        return evaluation;
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
}
