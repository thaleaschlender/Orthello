public class StableDisks implements EvaluationFunction {
    Player t;
    int player;
    int opponent;
    public StableDisks (Player t){
        this.t = t;
        player = t.colour;
        if(player == 1) opponent = 2;
        else opponent = 1;
    }
    /*
    Plan of Action:

     */
    public void setPlayer(int n ){
        this.player = n;
        if (player == 1) opponent = 2;
        else opponent = 1;
    }
    public boolean[][] stableBoard(Board board){
        boolean[][] stable = new boolean[8][8];
        for(int x = 0; x < stable.length; x++){
            for(int y = 0; y < stable[0].length; y++) {
                stable[x][y] = stable(board,x,y);
            }
        }
        for(int i = 0; i < stable.length; i++){
            for(int j = 0; j < stable[0].length; j++){
                System.out.print(stable[i][j]);
            }
            System.out.println();
        }
        boolean unchanged = false;
        while (!unchanged){
            unchanged = true;
        for(int x = 0; x < stable.length; x++){
            for(int y = 0; y < stable[0].length; y++) {
                if(!stable[x][y] && board.getBoard()[x][y].getColour() == player && surroundedByStable(stable,x,y,board)){
                    stable[x][y] = true;
                    unchanged = false;
                }
            }
        }}
            return stable;
    }
    public boolean stable (Board board, int x, int y){
        //empty places on the board are never stable
        if(board.getBoard()[x][y].getColour()==0) return false;
        else if(board.getBoard()[x][y].getColour() == opponent) return false;
        else{
             //corner pieces are always stable
             if(board.getBoard()[x][y].getColour()==player && x == 0 && (y == 0 || y == 7)) return true;
             else if(board.getBoard()[x][y].getColour()==player && x == 7 && (y == 0 || y == 7)) return true;
             //pieces in a complete row (in all directions) are stable
             else if(isLineFull(x,y,1,1,board)
                        && isLineFull(x,y, 0,1, board)
                        && isLineFull(x,y,1,0,board)
                        && isLineFull(x,y,1,-1,board))
                    return true;


             }
        return false;
    }
    // if a disk is surrounded by stable disks of its own colour, then it is stable
    public boolean surroundedByStable(boolean[][] board, int x, int y, Board initboard){
        if(stableLine(x,y,1,1,board,initboard)
                && stableLine(x,y,1,0,board, initboard)
                && stableLine(x,y,0,1,board,initboard)
                && stableLine(x,y,-1,1,board,initboard))
            return true;
        else if((sorroundedByOpponent(x,y,1,1,board,initboard)|| isLineFull(x,y,1,1,initboard))
                && (sorroundedByOpponent(x,y,1,0,board,initboard)|| isLineFull(x,y,1,0,initboard))
                && (sorroundedByOpponent(x,y,0,1,board,initboard)|| isLineFull(x,y,0,1,initboard))
                && (sorroundedByOpponent(x,y,-1,1,board,initboard)|| isLineFull(x,y,-1,1,initboard)))
            return true;
        else return false;
    }

    public boolean sorroundedByOpponent(int x, int y, int directionX, int directionY, boolean[][] board, Board initboard) {
        boolean finished = false;
        boolean result = false;
        int initX = x, initY = y;
        if(x+directionX<1 || x+directionX>6 || y+directionY< 1 || y+directionY >6){
            result = true;
            finished = true;
        }
        while(!finished){
            if(x+directionX<1 || x+directionX>6 || y+directionY< 1 || y+directionY >6) {
                result = true;
                finished = true;
            }
            else{
                x = x + directionX;
                y = y + directionY;
                if(!board[x][y] ||initboard.getBoard()[x][y].getColour()!= opponent){
                    result = false;
                    finished = true;
                }
            }}
        finished=false;
        if(!result) return false;
        if(result) {
            // doing the same but multiplying direction x and direction y by -1
            directionX *= -1;
            directionY *= -1;
            x = initX;
            y = initY;
            if(x+directionX<1 || x+directionX>6 || y+directionY< 1 || y+directionY >6){
                result = true;
                finished = true;
            }
            while(!finished){
                if(x+directionX<1 || x+directionX>6 || y+directionY< 1 || y+directionY >6) {
                    result = true;
                    finished = true;
                }
                else{
                    x = x + directionX;
                    y = y + directionY;
                    if(!board[x][y] ||initboard.getBoard()[x][y].getColour()!= opponent){
                        result = false;
                        finished = true;
                    }
                }}
        }

        if(result) return true;
        else return false;
    }
    public boolean stableLine(int x, int y, int directionX, int directionY, boolean[][] board, Board initboard) {
        boolean result = false;
        int initX = x, initY = y;
        if(x+directionX<0 || x+directionX>7 || y+directionY< 0 || y+directionY >7){
            result = true;
        }
            else{
                x = x + directionX;
                y = y + directionY;
                if(board[x][y]){
                    result = true;
                }
                else result = false;
            }

        if(!result) { // deleted !
            // doing the same but multiplying direction x and direction y by -1
            directionX *= -1;
            directionY *= -1;
            x = initX;
            y = initY;
            if (x + directionX < 0 || x + directionX > 7 || y + directionY < 0 || y + directionY > 7) {
                result = true;
            }
            else {
                x = x + directionX;
                y = y + directionY;
                if (board[x][y]) {
                        result = true;
                }
                else result= false;
                }
            }

        if(result) return true;
        else return false;
    }



    public boolean isLineFull(int x, int y, int directionX, int directionY, Board board) {
        boolean finished = false;
        boolean result = false;
        int initX = x, initY = y;
        if(x+directionX<0 || x+directionX>7 || y+directionY< 0 || y+directionY >7){
            result = true;
            finished = true;
        }
        while(!finished){
            if(x+directionX<0 || x+directionX>7 || y+directionY< 0 || y+directionY >7) {
                result = true;
                finished = true;
            }
            else{
                x = x + directionX;
                y = y + directionY;
                if(board.getBoard()[x][y].getColour()== 0){
                    result = false;
                    finished = true;
                }
            }}
            finished=false;
        if(!result) return false;
         if(result) {
             // doing the same but multiplying direction x and direction y by -1
             directionX *= -1;
             directionY *= -1;
             x = initX;
             y = initY;
             if (x + directionX < 0 || x + directionX > 7 || y + directionY < 0 || y + directionY > 7) {
                 result = true;
                 finished = true;
             }
             while (!finished) {
                 if (x + directionX < 0 || x + directionX > 7 || y + directionY < 0 || y + directionY > 7) {
                     result = true;
                     finished = true;
                 } else {
                     x = x + directionX;
                     y = y + directionY;
                     if (board.getBoard()[x][y].getColour() == 0) {
                         result = false;
                         finished = true;
                     }
                 }
             }
         }
        if(result) return true;
        else return false;

    }


    @Override
    public int evaluateBoard(Board board) {
        boolean[][] stableDisks = stableBoard(board);
        int evaluation = 0;
        board.print();
        System.out.println("_______");
        for(int i = 0; i < stableDisks.length;i++){
            for(int j = 0; j < stableDisks[0].length; j++){
                System.out.print(stableDisks[i][j]);
                if(stableDisks[i][j]) {
                    evaluation++;
                    //System.out.println(" x " + i + " y " + j);
                }
            }
            System.out.println();
        }
        System.out.println("Evaluation " + evaluation);
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
    public void setW4(int w4) {

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

    @Override
    public int getW4() {
        return 0;
    }
}
