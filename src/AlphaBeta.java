import java.util.ArrayList;

public class AlphaBeta extends Player {
   // Node alpha;
    //Node beta;
    public AlphaBeta (int c){
        super(c);
        Node.setTreeSearch(this);
    }

    @Override
    public void play (int x, int y){
        // root or initial state (this is, what the board looks like before we've made our move)
        Node initState = new Node(new Board(game.board), null, null);
        initState.setPlayer(checkfor);
        //alpha = null;
        //beta = null;
        //call the minimax method with the initial state and the depth limit
       // Node node = alphaBetaSearch(initState,2);
        Node node = alphaBeta(initState,5, null, null);
        //get the x and y coordinates on piece we actually want to place, and place it on the board
        Piece piece = node.getFirstPiece();
        System.out.println(" we took this one " + node);
        game.makeMove(piece.getX(),piece.getY());
        game.updateBoard();
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

    public Node alphaBeta(Node initialState, int depthLimit, Node alpha, Node beta){
        ArrayList<Node> successors = expand(initialState);
        Node value, bestNode = null;
        for (int i = 0; i < successors.size(); i++) {
            System.out.println("alpha beta call at zerooooo1oo1oo1o1oo1o1o1ooo" );
            value = MinPlayer(successors.get(i), depthLimit, alpha, beta);
            bestNode = compareMax(bestNode, value);
            System.out.println("at zero the bestnode is . " + bestNode);
        }

        return bestNode;

    }

        public Node MinPlayer (Node initialState, int depthLimit, Node alpha, Node beta) {
            System.out.println("MIN: depth = " + initialState.getDepth() + " alpha is " + alpha + " beta is " + beta);
            Node result = null;
            if(initialState.getDepth()== depthLimit) {
                beta = compareMin(beta,initialState);
                System.out.println("MIN: leaf with cost : " + beta.getCost());
                return beta;
            }
            else if(numberOfpossibleMoves(initialState.getBoard(),initialState.getPlayer()) == 0) {
                System.out.println("No successors: returns init of " + initialState.getCost());
                return initialState;
            }
            else {
                Node bestNode = null;
                ArrayList<Node> successors = expand(initialState);
                Node value;
                for (int i = 0; i < successors.size(); i++) {
                    System.out.println("report back to min at depth " + initialState.getDepth());
                    value = maxPlayer(successors.get(i), depthLimit, alpha, beta);
                    bestNode = compareMin(bestNode, value);
                    beta = compareMin(beta, bestNode); // this should be alpha if beta is not changed before (i.e. beta=11 still) NO WRONG WRONG WRONG
                    if (alpha != null && compareMin(beta, alpha) == beta) {
                        result = beta;
                        System.out.println("MIN: BREEEEEEEEEEAAAAAAAAAAK and return beta : " + beta);
                        break;
                    }
                else result = bestNode;
                }
            }
            System.out.println("Min at depth: "+ initialState.getDepth() + " has chosen " + result+ " (depth : " + result.getDepth());

            return result;
        }


        public Node maxPlayer (Node initialState, int depthLimit, Node alpha, Node beta){
            System.out.println("MAX: depth = " + initialState.getDepth() + " alpha is " + alpha + " beta is " + beta);
            Node result = null;
            if(initialState.getDepth()== depthLimit) {
                alpha = compareMax(alpha, initialState);
                System.out.println("MAX: leaf with cost : " + alpha.getCost());
                return alpha;
            }
            else if(numberOfpossibleMoves(initialState.getBoard(),initialState.getPlayer()) == 0){
                System.out.println("No successors: returns init of " + initialState.getCost());
                return initialState;
            }
            else {
                Node bestNode = null;
                ArrayList<Node> successors = expand(initialState);
                Node value;
                for (int i = 0; i < successors.size(); i++) {
                    System.out.println("report back to max at depth " + initialState.getDepth());
                    value = MinPlayer(successors.get(i), depthLimit, alpha, beta);
                    bestNode = compareMax(bestNode, value);
                    alpha = compareMax(alpha, bestNode);
                    if (beta != null && compareMax(alpha, beta) == alpha) {
                        result = alpha;
                        System.out.println("MAX: BREEEEAAAAK and return alpha : " + alpha);
                        break;
                    }
                    else result = bestNode;
                }
            }
            System.out.println("Max at depth: "+ initialState.getDepth() + " has chosen " + result + " (depth : " + result.getDepth());

            return result;
        }

}

