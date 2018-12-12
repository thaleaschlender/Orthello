public class EvaluationFunction2 implements EvaluationFunction {
    Player t;
    int w1 = 1;
    int w2 = 1;
    int w3 = 1;
    public EvaluationFunction2(Player t){
       this.t = t;
    }
    public int evaluateBoard(Board board){
        int evaluation = 0;
        int w = 0; int b = 0;
        for(int i = 0; i < board.getBoard().length; i++){
            for(int j = 0; j < board.getBoard()[0].length; j++){
                if(board.getBoard()[i][j].getColour()== 1) w++;
                if(board.getBoard()[i][j].getColour()== 2) b++;
            }
        }
        if(t.checkfor ==1) evaluation = (b-w);
        else evaluation = (w-b);

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
