import java.util.ArrayList;
//yes, I know the class is called TreeSearch, but it is a backtracking-minimax algorithm exploring nodes of a tree
//Fyi, i do already have a general tree search algorithm, maybe we need it later
public class TreeSearch extends Player{
        EvaluationFunction e;
    // (note: we only have the int x and int y input in play, because player was initially part of a clicklistener, we do not use this input here at all)
    public TreeSearch(int c){
        super(c); // calls the constructor of player
        e = new EvaluationFunction1(this);

        /*
            I've added a static variable TreeSearch to Node, so that I can let two minimax players, play against each other.
            This is necessary, because minimax is only optimal against an optimal player, but can be deleted once we figure the evaluation function out
         */

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
        //call the minimax method with the initial state and the depth limit
        Node node = miniMax_Value(initState,2);
        //get the x and y coordinates on piece we actually want to place, and place it on the board
        Piece piece = node.getFirstPiece();
        game.makeMove(piece.getX(),piece.getY());
        game.updateBoard();
    }

    public Node miniMax_Value(Node initialState, int depthLimit){
        //if we are at a leaf node, return this node.
        if(initialState.getDepth()==depthLimit) return initialState;
        else if(numberOfpossibleMoves(initialState.getBoard(),initialState.getPlayer()) == 0) return initialState;
        else {
            Node bestNode = null;

            //expand the node (find all the possible next moves)
            ArrayList<Node> successors = expand(initialState);

            //BackTracking bit. Call this algorithm for each of the successors in order to get their minimax values
            //for(Node n : successors) n = miniMax_Value(n,depthLimit);

            //if max is playing, pick the maximum minimax value of the successors
            if(initialState.getDepth()%2==0){
                for(Node n : successors)
                {n = miniMax_Value(n,depthLimit);
                bestNode = compareMax(bestNode,n);}
                return bestNode;
            }
            //if min is playing, pick the minimum minimax value of the successors
            else{
                for(Node n : successors)
                {n = miniMax_Value(n,depthLimit);
                bestNode = compareMin(bestNode,n);}
                return bestNode;
            }
        }

    }
    //simple compare methods:

    //find smaller node
    public Node compareMin (Node node1, Node node2){
        Node result;
        if(node1 == null) result = node2;
        else if (node2 == null) result = node1;
        else if (node1.getCost() < node2.getCost()) result = node1;
        else if (node1.getCost() > node2.getCost()) result = node2;
        else{
            double x = Math.random();
            if(x < 0.5) result = node2;
            else result = node1;
        }
        return result;
    }
    //find bigger node
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

    //Expand method:
    // returns an arraylist of nodes, one for each next possible move (placement of a tile)
    public ArrayList<Node> expand (Node node){
        ArrayList<Node> expantions = new ArrayList<>();
        ArrayList<Piece> validMoves = possibleMoves(node.getBoard(),node.getPlayer());
        for (Piece p: validMoves) {
            expantions.add(new Node(makeMove(p.getX(),p.getY(),new Board(node.getBoard()),node.getPlayer()),node,p));
        }
        return expantions;
    }
}
