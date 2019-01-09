import java.util.ArrayList;

public class GreedyAlgorithm extends Player {
    EvaluationFunction e;
    public GreedyAlgorithm(int c){
        super(c);
        e = new EvaluationFunction2(this);
    }
    public EvaluationFunction getEvalFunction(){
        return e;
    }
    @Override
    public void play (int x, int y){
        // root or initial state (this is, what the board looks like before we've made our move)
        Node initState = new Node(new Board(game.board), null, null);
        initState.setPlayer(checkfor);
        initState.setTreeSearch(this);
        Node node = greedyPlayer(initState);
        //get the x and y coordinates on piece we actually want to place, and place it on the board
        Piece piece = node.getFirstPiece();
        game.makeMove(piece.getX(),piece.getY());
        game.updateBoard();
    }

    public Node greedyPlayer(Node initialState){
            Node bestNode = null;
            ArrayList<Node> successors = expand(initialState);
                for(Node n : successors) bestNode = compareMax(bestNode,n);
           return bestNode;
        }
    public ArrayList<Node> expand (Node node){
        ArrayList<Node> expantions = new ArrayList<>();
        ArrayList<Piece> validMoves = possibleMoves(node.getBoard(),node.getPlayer());
        for (Piece p: validMoves) {
            expantions.add(new Node(makeMove(p.getX(),p.getY(),node.getBoard(),node.getPlayer()),node,p));
        }
        return expantions;
    }
    public Node compareMax (Node node1, Node node2){
        Node result;
        if(node1 == null) result = node2;
        else if (node2 == null) result = node1;
        else if (node1.getCost() < node2.getCost()) result = node2;
        else if (node1.getCost() > node2.getCost()) result = node1;
        else{
            double x = Math.random();
            if(x < 0.5) result = node2;
            else result = node1;
        }
        return result;
    }
}
