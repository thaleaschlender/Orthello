import java.util.ArrayList;

public class Node {
    private Node parent;
    private int depth;
    private ArrayList<Node> children;
    public Board board;
    public ArrayList<Piece> possibilities;
    public int currentPlayer;
    public int cost;
    private ArrayList<Piece> actions = new ArrayList<>();

    public Node(Board state , Node parent, Piece p){
        board = state;
        this.parent = parent;
        if(parent == null){
            this.depth =1;
            if(p != null) this.actions.add(p);
        }
        else{
            this.depth = parent.getDepth() +1;
        this.actions.addAll(this.parent.getActions());
        this.actions.add(p);
        if(parent.getPlayer()== 1)this.currentPlayer =2;
        else this.currentPlayer =1;}
        cost = evaluateBoard();
    }

    public ArrayList<Piece> getActions() {
        return actions;
    }

    public int getCost(){
        return cost;
    }
    public Piece getFirstPiece(){
        return actions.get(0);
    }

    public ArrayList<Piece> getactions() {
        return actions;
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
    public int evaluateBoard(){
        int evaluation = 0;
        int w = 0; int b = 0;
        for(int i = 0; i < board.getBoard().length; i++){
            for(int j = 0; j < board.getBoard()[0].length; j++){
                if(board.getBoard()[i][j].getColour()== 1) w++;
                if(board.getBoard()[i][j].getColour()== 2) b++;
            }
        }
        evaluation = (w-b);
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
