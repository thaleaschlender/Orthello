public class EvaluationFunction1 implements EvaluationFunction {
    Player t;
    public EvaluationFunction1(Player t){
        this.t = t;
    }
    @Override
    public int evaluateBoard(Board board) {
        int player = t.getColour();
        int opponent;
        int evaluation = 0;
        int dMoves = 0;
        int dPosition = 0;
        int dScore = 0;
        int w1 = 1;
        int w2 = 1;
        int w3 = 1;

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
}
