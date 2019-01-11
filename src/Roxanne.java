public class Roxanne implements EvaluationFunction {
    private int[][] boardValues;
    Player t;
    int colour;
    int opponent;

    public Roxanne (Player t){
        this.t = t;
        colour = t.colour;
        if(colour == 1) opponent = 2;
        else opponent = 1;
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

    public int getValue(int x, int y){
        return boardValues[x][y];
    }
    public void print(){
        for(int i = 0; i < boardValues.length; i++){
            for(int j =0; j < boardValues[0].length; j++){
                System.out.print(boardValues[i][j]);
            }
            System.out.println();
        }
    }
    @Override
    public int evaluateBoard(Board board) {
        int evaluation = 0;
        for(int i = 0; i < board.getBoard().length; i++){
            for(int j = 0; j < board.getBoard()[0].length; j++){
                //System.out.print(board.getBoard()[i][j].getColour());
                if(board.getBoard()[i][j].getColour()== colour){
                    evaluation += boardValues[i][j];
                   // System.out.println("at " + i + " " + j + " we add " +  boardValues[i][j]);
                }
                else if(board.getBoard()[i][j].getColour() == opponent) {
                    evaluation -= boardValues[i][j];
                   // System.out.println("at " + i + " " + j + " we subtract " +  boardValues[i][j]);
                }
            }
            //System.out.println();
        }
       // System.out.println("Evaluation : " + evaluation);
        return evaluation;
    }
    @Override
    public void setW1(int w1) {

    }
    @Override
    public void setW2(int w2) {

    }
    @Override
    public void setW3(int w3) {

    }
    @Override
    public int getW1() {
        return 0;
    }
    @Override
    public int getW2() {
        return 0;
    }
    @Override
    public int getW3() {
        return 0;
    }
}
