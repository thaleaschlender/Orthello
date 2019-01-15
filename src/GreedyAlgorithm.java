import java.util.ArrayList;

public class GreedyAlgorithm extends Player {
    EvaluationFunction e;

    //OpponentModel opponentModell = new OpponentModel(colour);
    //boolean oppMod;
    public GreedyAlgorithm(int c, int eval){
        super(c);
        //this.oppMod = oppMod;
        if(eval == 1) {
            e = new EvaluationFunction1(this);
        }
        else e = new StableDisks(this);
        int max = 20;
        int a = (int)(Math.random() * max);
        int b = (int)(Math.random() * max);
        int f = (int)(Math.random() * max);
        int d = (int)(Math.random() * max);
        System.out.println("weights "+a+" "+b+" "+f+" "+d);
        e.setW1(a);
        e.setW2(b);
        e.setW3(f);
        e.setW4(d);
    }
    public EvaluationFunction getEvalFunction(){
        return e;
    }
    @Override
    public void play (int x, int y){
        // root or initial state (this is, what the board looks like before we've made our move)
        //if(oppMod) opponentModell.learnWeights();
        Node initState = new Node(new Board(game.board), null, null);
        initState.setPlayer(checkfor);
        initState.setTreeSearch(this);
        Node node = greedyPlayer(initState);
        //get the x and y coordinates on piece we actually want to place, and place it on the board
        Piece piece = node.getFirstPiece();
        if(colour == 2) System.out.println("eval " + node.getCost());
        //System.out.println("TAKEN x: " + piece.getX()+" y: "+piece.getY());
        game.makeMove(piece.getX(),piece.getY());
      //  if(oppMod) opponentModell.setLastBoard(new Board(game.board));
        game.updateBoard();

    }

    public Node greedyPlayer(Node initialState){
            Node bestNode = null;
            ArrayList<Node> successors = expand(initialState);
                for(Node n : successors){
                  /*  System.out.println("x: "+n.getFirstPiece().getX()+ " y: "+n.getFirstPiece().getY()
                    +" x1 "+e.dScore(n.getBoard())+" x2 "+e.dPosition(n.getBoard())
                            +" x3 "+e.dMoves(n.getBoard())+" x4 "+e.stableDiscs(n.getBoard())+
                            " with cost " + n.getCost());*/
                    bestNode = compareMax(bestNode,n);
                }
           return bestNode;
        }
    public ArrayList<Node> expand (Node node){
        ArrayList<Node> expantions = new ArrayList<>();
        ArrayList<Piece> validMoves = possibleMoves(node.getBoard(),node.getPlayer());
        for (Piece p: validMoves) {
            expantions.add(new Node(makeMove(p.getX(),p.getY(),new Board(node.getBoard()),node.getPlayer()),node,p));
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
