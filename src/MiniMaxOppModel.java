import java.util.ArrayList;

public class MiniMaxOppModel extends Player {
    EvaluationFunction e;
    OpponentModel opponentModell = new OpponentModel(colour);
    EvaluationFunction1 opponentEval = new EvaluationFunction1(this);
    public MiniMaxOppModel(int colour) {
        super(colour);
        e = new EvaluationFunction1(this);
    }
    public void reset(){
        opponentModell.reset();
    }
    public EvaluationFunction getEvalFunction(){
        return e;
    }
    public OpponentModel getOppEvalFunction(){
        return opponentModell;
    }
    @Override
    public void play (int x, int y){
        opponentModell.learnWeights();
        Node initState = new Node(new Board(game.board), null, null);
        initState.setPlayer(checkfor);
        initState.setTreeSearch(this);
        Node node = miniMax_Value(initState,2);
        Piece piece = node.getFirstPiece();
        game.makeMove(piece.getX(),piece.getY());
        opponentModell.setLastBoard(new Board(game.board));
        game.updateBoard();
    }

    public Node miniMax_Value(Node initialState, int depthLimit){
        if(initialState.getDepth()==depthLimit) return initialState;
        else if(numberOfpossibleMoves(initialState.getBoard(),initialState.getPlayer()) == 0) return initialState;
        else {
            Node bestNode = null;
            ArrayList<Node> successors = expand(initialState);
            if(initialState.getDepth()%2==0){
                for(Node n : successors)
                {n = miniMax_Value(n,depthLimit);
                    bestNode = compareMax(bestNode,n);}
                return bestNode;
            }
            else{
                //here opp model
                for(Node n : successors) {
                    n = miniMax_Value(n,depthLimit);
                    bestNode = compareMin(bestNode,n);}
                return bestNode;
            }
        }

    }

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

    public ArrayList<Node> expand (Node node){
        ArrayList<Node> expantions = new ArrayList<>();
        ArrayList<Piece> validMoves = possibleMoves(node.getBoard(),node.getPlayer());
        for (Piece p: validMoves) {
            expantions.add(new Node(makeMove(p.getX(),p.getY(),new Board(node.getBoard()),node.getPlayer()),node,p));
        }
        return expantions;
    }

}
