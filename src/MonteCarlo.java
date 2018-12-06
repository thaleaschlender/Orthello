import java.util.ArrayList;

public class MonteCarlo extends Player {

    //Constructor
    public MonteCarlo(int c){
        super(c);
    }
    @Override
    public void play (int x, int y){
        // root or initial state (this is, what the board looks like before we've made our move)
        Node initState = new Node(new Board(game.board), null, null);
        initState.setPlayer(checkfor);

        //call the minimax method with the initial state and the depth limit
        Node node = monteCarloSearch(initState, 5);
        //get the x and y coordinates on piece we actually want to place, and place it on the board
        Piece piece = node.getFirstPiece();
        game.makeMove(piece.getX(),piece.getY());
        game.updateBoard();
    }

    public Node monteCarloSearch(Node init, int depthLimit){
        Node bestnode = null;
        ArrayList<Node> toBeExplored = expand(init);
        // this is the arraylist which returns the the nodes that could result from the initial state.

        while(toBeExplored.size()>0){
            Node n = toBeExplored.remove(0);
            if(n.getDepth() != depthLimit) {//if the node we are looking at is not a leaf node
                toBeExplored.addAll(expand(n)); // this adds the children of node n to the END of the tobeexplored list (breadth-first search)
                toBeExplored.addAll(0, expand(n));// this adds the children of node n to the Start of the tobeexplored list (depth-first search)
            }
            else{
                //if n is a leaf node, do something....
            }
        }
        return bestnode;
    }

    public ArrayList<Node> expand (Node node){
        ArrayList<Node> expantions = new ArrayList<>();
        ArrayList<Piece> validMoves = possibleMoves(node.getBoard(),node.getPlayer());
        for (Piece p: validMoves) {
            expantions.add(new Node(makeMove(p.getX(),p.getY(),node.getBoard(),node.getPlayer()),node,p));
        }
        return expantions;
    }


}
