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
        if(parent == null) this.depth =1;
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
    private int evaluateBoard(){
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
}
