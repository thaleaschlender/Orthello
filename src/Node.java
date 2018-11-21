import java.util.ArrayList;

public class Node {
    private static Player t; // explained this in TreeSearch
    /*
    A tree node has:
        -a parent,
         -a depth,
         -a cost,
         -a currentplayer (the checkfor variable, for whoevers turn it is right now)
         -list of actions happend sofar (pieces placed until now)
     */
    private Node parent;
    private int depth;
    private Board board;
    private int cost;
    private int currentPlayer;
    private ArrayList<Piece> actions = new ArrayList<>();

    public Node(Board state , Node parent, Piece p){
        board = state;
        this.parent = parent;
        if(parent == null) this.depth =0; // we changed this, if the code fucks up, this is where
        else{
            this.depth = parent.getDepth() +1;
            this.actions.addAll(this.parent.getActions());
            this.actions.add(p);
            if(parent.getPlayer()== 1)this.currentPlayer =2;
            else this.currentPlayer =1;
        }
        cost = evaluateBoard();
    }


    //getter and setter Methods
    public static void setTreeSearch(Player tree){
        t = tree;
    }
    public ArrayList<Piece> getActions() {
        return actions;
    }
    public Board getBoard(){
        return board;
    }
    public int getCost(){
        return cost;
    }
    public Piece getFirstPiece(){
        return actions.get(0);
    }
    public void setPlayer(int p){
        this.currentPlayer = p;
    }
    public int getDepth(){
        return depth;
    }
    public int getPlayer(){
        return currentPlayer;
    }

    //our evaluation method:)
  /*  private int evaluateBoard(){
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
    }*/
    private int evaluateBoard(){
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
    @Override
    public String toString() {
        if(this == null) return "Null";
        else return Integer.toString(this.cost);
    }
}
