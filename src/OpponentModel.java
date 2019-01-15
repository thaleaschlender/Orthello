import java.util.ArrayList;
public class OpponentModel extends Player{
    //public Player opponent;

    //Does the opponent model
    public EvaluationFunction1 e = new EvaluationFunction1(this);
    public int[] currentWeights = {1,1,1,1};
    public Board lastBoard;
    public ArrayList<int[]> illegal = new ArrayList<>();
    public OpponentModel(int c) {
        super(c);
        //this.opponent = opponent;
        lastBoard = new Board(game.board);
        e.setPlayerColour(checkfor);
        int[] il ={0,0,0,0};
        illegal.add(il);


    }
    public void reset(){
        currentWeights[0] = 1;
        currentWeights[1] = 1;
        currentWeights[2] = 1;
        currentWeights[3] = 1;
        illegal = new ArrayList<>();
        lastBoard = game.board;
    }
    public int evaluateBoard(Board board){
        return e.evaluateBoard(board);
    }
    public void setLastBoard(Board board){
        this.lastBoard = new Board(board);
    }
    //IDEA: play once, remember moves, learn evaluation function, play again using the learnt evaluation function of first game
    /*
    Learn weights
        get an example, i.e. the last move made.
        start with default weights 1,1,1,1
        evaluate all moves, which were possible, with these weights
        if the chosen move, is not the move with the highest evaluation
        then evaluate all moves with the neighbouring weghts
        as soon as the chosen move is superior according to the weights,
        break and make the weights the current default weights
     */
    public Piece getOpponentsMove(){
        for(int i = 0; i < lastBoard.getBoard().length; i++){
            for(int j = 0; j < lastBoard.getBoard()[0].length; j++){
                if(lastBoard.getBoard()[i][j].getColour() == 0 && game.board.getBoard()[i][j].getColour() != 0) {
                    //System.out.println(" opponents last move : x " + i + " y " + j);
                    return game.board.getBoard()[i][j];
                }
            }
        }
        return null;
    }






    public int[] learnWeights(){
        //for(int[] i: illegal) System.out.println("illegal "+i[0]+" "+i[1]+" "+i[2]+" "+i[3]);
        int[] actual = {6,2,3,4};
        Piece opponentsMove = getOpponentsMove();
        if(opponentsMove==null){
            ///System.out.println("opponentsmove is null");
            return currentWeights;
        }
       // System.out.println("oppenents move x " + opponentsMove.getX() + " y " + opponentsMove.getY());
        ArrayList<Piece> opponentsPossMoves = possibleMoves(lastBoard,colour); //opponents perspective !
        //for(Piece p : opponentsPossMoves) System.out.println(" poss move x " + p.getX() + " y " + p.getY());
        ArrayList<int[]> tempWeights = new ArrayList<>();
        ArrayList<int[]> tWeights = new ArrayList<>();
        tempWeights.add(currentWeights);
        int[] temp = currentWeights;

       //for(int[] temp: tempWeights){
        boolean finished = false;
        while(!finished){
            finished = true;
          // if(!illegal.contains(tempWeights.get(0)))
               temp = tempWeights.get(0);
           //else tempWeights.remove(tempWeights.get(0));
            System.out.println("testing weights " +  temp[0] + " " + temp[1] + " " + temp[2] + " " + temp[3]);
            e.setW1(temp[0]);
            e.setW2(temp[1]);
            e.setW3(temp[2]);
            e.setW4(temp[3]);
            int chosen = e.evaluateBoard(makeMove(opponentsMove.getX(), opponentsMove.getY(), new Board(lastBoard), colour));

            opponentsPossMoves.remove(opponentsMove);
        for(Piece p: opponentsPossMoves) {
            int t = e.evaluateBoard(makeMove(p.getX(), p.getY(), new Board(lastBoard), colour));
            if(temp[0] == actual[0] && temp[1] == actual[1] && temp[2] == actual[2] && temp[3] == actual[3]){
                 Board b = makeMove(opponentsMove.getX(), opponentsMove.getY(), new Board(lastBoard), colour);
                System.out.println("x1 "+e.dScore(b)+" x2 "+e.dPosition(b)+" x3 " +e.dMoves(b)+" x4 "+e.stableDiscs(b)+
                " chosen eval " + chosen);
                b = makeMove(p.getX(), p.getY(), new Board(lastBoard), colour) ;
                System.out.println("x1 "+e.dScore(b)+" x2 "+e.dPosition(b)+" x3 " +e.dMoves(b)+" x4 "+e.stableDiscs(b)+
                        " possmoves eval " + t);
                System.out.println("chosen eval " +  chosen + " vs " + t);
           }

                if (t > chosen ) {
                if(!contains(illegal,temp))illegal.add(temp);
                tempWeights.remove(temp);
                tWeights.addAll(neighbours(temp));
                finished = false;
                //break;
            }
        }
        ArrayList<int[]> temporary = new ArrayList<>();
        if(tempWeights.size() == 0 && !finished) {

            System.out.println("actual weights in illegal? "+contains(illegal,actual));

            while(tWeights.size()!= 0) {
                int[] t = tWeights.get(0);
                if (!contains(illegal, t) && !contains(tempWeights, t) && positive(t)) tempWeights.add(t);
                else if(positive(t) && !contains(tempWeights,t)) temporary.addAll(neighbours(t));
                tWeights.remove(t);
            }
          tWeights.addAll(temporary);
        }
        }
        System.out.println("returning weights " + temp[0] + " " + temp[1] + " " + temp[2] + " " + temp[3]);
        currentWeights = temp;
        return temp;
    }

    public boolean contains(ArrayList<int[]> list, int[] e){
        for(int[] i : list) {
            if (i[0] == e[0]
                    && i[1] == e[1]
                    && i[2] == e[2]
                    && i[3] == e[3])
                return true;
        }
        return false;
    }
    public boolean positive(int[] e){
        if(e[0] >= 0 && e[1] >= 0 && e[2] >= 0 && e[3] >= 0) return true;
        else return false;
    }
    public ArrayList<int[]> neighbours(int[] current){
        ArrayList<int[]> neighbours = new ArrayList<>();
        int i = 1;
        int[] t0 = {current[0], current[1], current[2], current[3] + i};
        int[] t1 = {current[0], current[1], current[2] + i, current[3]};
        int[] t2 = {current[0], current[1], current[2] + i, current[3] + i};
        int[] t3 = {current[0], current[1] + i, current[2], current[3]};
        int[] t4 = {current[0], current[1] + i, current[2], current[3] + i};
        int[] t5 = {current[0], current[1] + i, current[2] + i, current[3]};
        int[] t6 = {current[0], current[1] + i, current[2] + i, current[3] + i};

        int[] t7 = {current[0] + i, current[1], current[2], current[3]};
        int[] t8 = {current[0] + i, current[1], current[2], current[3] + i};
        int[] t9 = {current[0] + i, current[1], current[2] + i, current[3]};
        int[] t10 = {current[0] + i, current[1], current[2] + i, current[3] + i};
        int[] t11 = {current[0] + i, current[1] + i, current[2], current[3]};
        int[] t12 = {current[0] + i, current[1] + i, current[2], current[3] + i};
        int[] t13 = {current[0] + i, current[1] + i, current[2] + i, current[3]};
        int[] t14 = {current[0] + i, current[1] + i, current[2] + i, current[3] + i};
        neighbours.add(t0);
        neighbours. add(t1);
        neighbours. add(t2);
        neighbours. add(t3);
        neighbours. add(t4);
        neighbours. add(t5);
        neighbours. add(t6);
        neighbours. add(t7);
        neighbours. add(t8);
        neighbours. add(t9);
        neighbours. add(t10);
        neighbours. add(t11);
        neighbours. add(t12);
        neighbours. add(t13);
        neighbours. add(t14);

        i = -1;
        int[] t15 = {current[0], current[1], current[2], current[3] + i};
        int[] t16 = {current[0], current[1], current[2] + i, current[3]};
        int[] t17 = {current[0], current[1], current[2] + i, current[3] + i};
        int[] t18= {current[0], current[1] + i, current[2], current[3]};
        int[] t19 = {current[0], current[1] + i, current[2], current[3] + i};
        int[] t20 = {current[0], current[1] + i, current[2] + i, current[3]};
        int[] t21 = {current[0], current[1] + i, current[2] + i, current[3] + i};

        int[] t22 = {current[0] + i, current[1], current[2], current[3]};
        int[] t23 = {current[0] + i, current[1], current[2], current[3] + i};
        int[] t24 = {current[0] + i, current[1], current[2] + i, current[3]};
        int[] t25 = {current[0] + i, current[1], current[2] + i, current[3] + i};
        int[] t26 = {current[0] + i, current[1] + i, current[2], current[3]};
        int[] t27 = {current[0] + i, current[1] + i, current[2], current[3] + i};
        int[] t28 = {current[0] + i, current[1] + i, current[2] + i, current[3]};
        int[] t29 = {current[0] + i, current[1] + i, current[2] + i, current[3] + i};
        neighbours.add(t15);
        neighbours. add(t16);
        neighbours. add(t17);
        neighbours. add(t18);
        neighbours. add(t19);
        neighbours. add(t20);
        neighbours. add(t21);
        neighbours. add(t22);
        neighbours. add(t23);
        neighbours. add(t24);
        neighbours. add(t25);
        neighbours. add(t26);
        neighbours. add(t27);
        neighbours. add(t28);
        neighbours. add(t29);
       /* for(int j = 0; j <5; j++){
            int max = 15;
            int min = 0;
            int a = (int)(Math.random() * ((max - min) + 1)) + min;
            int b = (int)(Math.random() * ((max - min) + 1)) + min;
            int c = (int)(Math.random() * ((max - min) + 1)) + min;
            int d = (int)(Math.random() * ((max - min) + 1)) + min;
            int[] temp = {a,b,c,d};
            neighbours.add(temp);
        }*/
        return neighbours;
    }
}
