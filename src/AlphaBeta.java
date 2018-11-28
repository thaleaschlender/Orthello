import java.util.ArrayList;

public class AlphaBeta extends Player {
    Node alpha;
    Node beta;
    public AlphaBeta (int c){
        super(c);
        Node.setTreeSearch(this);
    }

    @Override
    public void play (int x, int y){
        // root or initial state (this is, what the board looks like before we've made our move)
        Node initState = new Node(new Board(game.board), null, null);
        initState.setPlayer(checkfor);
        alpha = null;
        beta = null;
        //call the minimax method with the initial state and the depth limit
        Node node = alphaBetaSearch(initState,2);

        //get the x and y coordinates on piece we actually want to place, and place it on the board
        Piece piece = node.getFirstPiece();
        System.out.println(" we took this one " + node);
        game.makeMove(piece.getX(),piece.getY());
        game.updateBoard();
    }
    public Node alphaBetaSearch(Node initialState, int depthLimit) {
        //if we are at a leaf node, return this node.
        Node result = null;
        System.out.println("depth rn : " + initialState.getDepth());
        if (initialState.getDepth() == depthLimit) {
            result = initialState;
        }
        else {
            Node bestNode = null;
            //expand the node (find all the possible next moves)
            ArrayList<Node> successors = expand(initialState);
            ArrayList<Node> evaluations = new ArrayList<>();
            //BackTracking bit. Call this algorithm for each of the successors in order to get their minimax values
            System.out.println("al = " + alpha  + " be " + beta);
            for (Node n : successors) evaluations.add(alphaBetaSearch(n, depthLimit));

            //if max is playing, pick the maximum minimax value of the successors
            if (initialState.getDepth() % 2 == 0) {
                for (Node n : evaluations) bestNode = compareMax(bestNode, n);
                alpha = compareMax(alpha, bestNode);
                System.out.println("alpha = " + alpha+ " vs bestnode = " + bestNode);
                if (beta != null && compareMax(alpha, beta) == alpha) {
                    System.out.println("BREAK and return alpha :" + alpha);
                    result = alpha;

                } else result = bestNode;

            }
            //if min is playing, pick the minimum minimax value of the successors
            else {
                for (Node n : evaluations){ bestNode = compareMin(bestNode, n);}
                System.out.println(" beta = " + beta + " vs bestnode = " + bestNode);
                beta = compareMin(beta, bestNode);
                if (alpha != null && compareMin(beta, alpha) == beta) {
                    System.out.println("BREAK and return beta : " + beta);
                    result = beta;}
                else result = bestNode;}
            }

        System.out.println(" WE GIVE BACK " + result);
        return result;
    }
    // look at which value to return in the case of a break; maybe initial state??
    //simple compare methods:

    //find smaller node
    public Node compareMin (Node node1, Node node2){
        Node result;
        if(node1 == null) result = node2;
        else if (node2 == null) result = node1;
        else if (node1.getCost() <= node2.getCost()) result = node1;
        else  result = node2;
        return result;
    }
    //find bigger node
    public Node compareMax (Node node1, Node node2){
        Node result;
        if(node1 == null) result = node2;
        else if (node2 == null) result = node1;
        else if (node1.getCost() >= node2.getCost()) result = node1;
        else  result = node2;
        return result;
    }

    //Expand method:
    // returns an arraylist of nodes, one for each next possible move (placement of a tile)
    public ArrayList<Node> expand (Node node){
        ArrayList<Node> expantions = new ArrayList<>();
        ArrayList<Piece> validMoves = possibleMoves(node.getBoard(),node.getPlayer());
        for (Piece p: validMoves) {
            expantions.add(new Node(makeMove(p.getX(),p.getY(),node.getBoard(),node.getPlayer()),node,p));
        }
        return expantions;
    }
}

