import java.util.ArrayList;
//yes, I know the class is called TreeSearch, but it is a backtracking-minimax algorithm exploring nodes of a tree
//Fyi, i do already have a general tree search algorithm, maybe we need it later
public class TreeSearch extends Player{
    /*
        This is a backtracking minimax algorithm traversing through Nodes
        It is taken from the lecture slides of reasoning techniques (I can post a picture on the whatsapp group)
        TreeSearch extends Player
            - it overrides the "Play" method
            - it inherits the fields:
                -colour (black or white),
                -checkfor(what are we checking for according to the colour),
                -and game ( a reference to the board, so that we can make a move on the actual board and the game can continue)
     */
    //TODO: implement timer to replace the clicking
    // (note: we only have the int x and int y input in play, because player was initially part of a clicklistener, we do not use this input here at all)
    public TreeSearch(int c){
        super(c); // calls the constructor of player
        Node.setTreeSearch(this);
        /*
            I've added a static variable TreeSearch to Node, so that I can let two minimax players, play against each other.
            This is necessary, because minimax is only optimal against an optimal player, but can be deleted once we figure the evaluation function out
         */

    }

    @Override
    public void play (int x, int y){
        // root or initial state (this is, what the board looks like before we've made our move)
        Node initState = new Node(new Board(game.board), null, null);
        initState.setPlayer(checkfor);

        //call the minimax method with the initial state and the depth limit
        Node node = miniMax_Value(initState,4);

        //get the x and y coordinates on piece we actually want to place, and place it on the board
        Piece piece = node.getFirstPiece();
        game.makeMove(piece.getX(),piece.getY());
        game.updateBoard();
    }

    public Node miniMax_Value(Node initialState, int depthLimit){
        //if we are at a leaf node, return this node.
        if(initialState.getDepth()==depthLimit) return initialState;
        else {
            Node bestNode = null;

            //expand the node (find all the possible next moves)
            ArrayList<Node> successors = expand(initialState);

            //BackTracking bit. Call this algorithm for each of the successors in order to get their minimax values
            for(Node n : successors) n = miniMax_Value(n,depthLimit);

            //if max is playing, pick the maximum minimax value of the successors
            if(initialState.getDepth()%2==0){
                for(Node n : successors) bestNode = compareMax(bestNode,n);
                return bestNode;
            }
            //if min is playing, pick the minimum minimax value of the successors
            else{
                for(Node n : successors) bestNode = compareMin(bestNode,n);
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
            expantions.add(new Node(makeMove(p.getX(),p.getY(),node.getBoard(),node.getPlayer()),node,p));
        }
        return expantions;
    }

    /*
    These are the methods from the main logic in our game.
    Since we dont actually want to place a piece, but rather hypothetically want to test it,
    they are here again, but return a board (rather than changing the main static game board)
     */
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
