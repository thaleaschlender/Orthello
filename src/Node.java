import java.util.ArrayList;

public class Node {
    private Player t; // explained this in TreeSearch // make sure we initialize this before creating evaluation function oor ese null pointer
    //private static EvaluationFunction e;
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
    public Board board;
    private int cost;
    private int currentPlayer;
    private ArrayList<Piece> actions = new ArrayList<>();


    public Node(Board state , Node parent, Piece p){
        board = state;
        this.parent = parent;
        if(parent == null) this.depth =0;
        else{
            this.depth = parent.getDepth() +1;
            this.actions.addAll(this.parent.getActions());
            this.actions.add(p);
            this.t = parent.getT();
            if(parent.getPlayer()== 1)this.currentPlayer =2;
            else this.currentPlayer =1;
            cost = t.getEvalFunction().evaluateBoard(board);
        }

    }

    public Player getT() {
        return t;
    }

    //getter and setter Methods
    public void setTreeSearch(Player tree){
        t = tree;

       // e = new EvaluationFunction1(t);
    }
    // 1 - eval one
    // 2 - eval two
    /*public static void setEvalFunction(int n){
        if(t.getColour() == 1) e = new EvaluationFunction1(t);
       else e = new EvaluationFunction2(t);
    }

    public static EvaluationFunction getEval() {
        return e;
    }
*/
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


    @Override
    public String toString() {
        if(this == null) return "Null";
        else return Integer.toString(this.cost);
    }
}
