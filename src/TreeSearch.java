import java.util.ArrayList;

public class TreeSearch extends Player{
    public TreeSearch(int c){
        super(c);
    }
    /*
    Strategy: the order we choose the next node to evaluate
    Evaluation Function:

     */
    @Override
    public void play (int x, int y){
        Board board = new Board(game.board);
        Piece piece = treeSearch(board,5,colour).getFirstPiece();
        System.out.println(piece.getX() + " and " + piece.getY());
        game.makeMove(piece.getX(),piece.getY());
        game.updateBoard();
    }

    public Node treeSearch(Board board, int depthLimit, int initPlayer){
        Node bestNode = null;
        ArrayList<Node> fringe = new ArrayList<>();
        fringe.add(new Node(board, null, null)); // root or initial state
        //System.out.println("PLaya " + initPlayer);
        fringe.get(0).setPlayer(2);//assuming black starts i.e. is the root
        while(fringe.size() > 0){
            Node current = fringe.remove(0);
            if(current.getDepth() != depthLimit) fringe.addAll(expand(current));
            else bestNode = compare(bestNode, current);
        }
        System.out.println("done " + bestNode.getFirstPiece().getX() + " "
        + bestNode.getFirstPiece().getY());
        return bestNode;
    }
    //compare will be different for minimax etc?

    public Node compare (Node node1, Node node2){
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
        ArrayList<Piece> validMoves = possibleMoves(node.board,node.getPlayer());
        for (Piece p: validMoves) {
            expantions.add(new Node(makeMove(p.getX(),p.getY(),node.board,node.getPlayer()),node,p));
        }
        return expantions;
    }
    private Board makeMove(int x, int y, Board board, int current){
        ArrayList<Piece> flip;
        if(board.getBoard()[x][y].getColour()!=0) return board;
        int colour;
        if(current == 1) colour = 1;
        else colour = 2;
        board.getBoard()[x][y].changeColour(colour);
        int check = current;
        for(int i = x-1;i <= x+1; i++ )
            for (int j = y - 1; j <= y + 1 ; j++) {
                if (i>-1 && i<8 && j >-1 && j<8 &&((i-x)!=0||(j-y)!=0)&&(board.getBoard()[i][j].getColour() == check)){
                    flip = checkLine(x, y, (i - x), (j - y), check,board);
                    if(flip.size() != 0){
                        for(Piece f: flip) f.flip();
                    }

                }}
        return board;
    }
    private ArrayList<Piece> possibleMoves(Board board,int current){
        ArrayList<Piece> possibleMoves = new ArrayList<Piece>();
        for(int i =0; i < board.getBoard().length;i++){
            for(int j=0; j < board.getBoard()[i].length;j++){
                if(validMove(i,j,board,current)>0){
                    possibleMoves.add(board.getBoard()[i][j]);
                }
            }
        }
        return possibleMoves;
    }
    private ArrayList<Piece> checkLine(int x, int y, int directionX, int directionY, int check, Board board) {
        ArrayList<Piece> flippable = new ArrayList<>();
        boolean finished = false;
        if(x+directionX<0 || x+directionX>7 || y+directionY< 0 || y+directionY >7) return new ArrayList<>();
        while(!finished){
            if(x+directionX<0 || x+directionX>7 || y+directionY< 0 || y+directionY >7) return new ArrayList<>();
            else{
                x = x + directionX;
                y = y + directionY;
                if(board.getBoard()[x][y].getColour()== 0) return new ArrayList<>();
                else if(board.getBoard()[x][y].getColour()==check) flippable.add(board.getBoard()[x][y]);
                else if(board.getBoard()[x][y].getColour()!=check) finished = true;
                else return new ArrayList<>();

            }}
        return flippable;
    }

    private int validMove(int x, int y, Board board, int current) {
        int valid = 0;
        if (board.getBoard()[x][y].getColour() != 0) return 0;
        int colour;
        if(current == 1) colour = 2;
        else colour = 1;
        board.getBoard()[x][y].changeColour(colour);
        int check = current;
        for (int i = x - 1; i <= x + 1; i++){
            for (int j = y - 1; j <= y + 1; j++) {
                if (i > -1 && i < 8 && j > -1 && j < 8
                        && ((i - x) != 0 || (j - y) != 0) &&
                        (board.getBoard()[i][j].getColour() == check) && checkLine(x, y, (i - x), (j - y), check,board).size() != 0) {
                    valid += checkLine(x, y, (i - x), (j - y), check,board).size();

                }
            }
        }
        board.getBoard()[x][y].changeColour(0);
        return valid;
    }
}
