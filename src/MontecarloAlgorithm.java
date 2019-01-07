import java.util.ArrayList;

public class MontecarloAlgorithm extends Player {
    final int numberOfSimulations = 20;
    int opponentscolour;
    public MontecarloAlgorithm (int c){
        super(c);
        if( c == 1)  opponentscolour = 2;
        else opponentscolour = 1;
    }
    public void play (int x, int y){
        Node initState = new Node(new Board(game.board), null, null);
        initState.setPlayer(checkfor);
        initState.setTreeSearch(this);
        //get the x and y coordinates on piece we actually want to place, and place it on the board
        Piece piece = montecarlo();
        //System.out.println(" we took this one " + node);
        game.makeMove(piece.getX(),piece.getY());
        game.updateBoard();
    }

    public Piece montecarlo (){
        ArrayList<Piece> possMoves = possibleMoves(game.board, colour);
        int numberOfSim = numberOfSimulations/possMoves.size();
        Piece best = null;
        int bestScore = 0;
        for (Piece p:possMoves) {
            int effectiveness = 0;
            for (int i = 0; i < numberOfSim; i++){
                if(randomSimulation(p) == colour) effectiveness++;
            }
            if( effectiveness >= bestScore){
                best = p;
                bestScore = effectiveness;
            }
        }
        return best;
    }
    //returns winner
    public int randomSimulation (Piece p){
        Board board = makeMove(p.getX(),p.getY(),game.board,colour);
        int current = colour;
        while(!gameOver(board)) {
            current = switchplayer(current);
            ArrayList<Piece> posMoves = possibleMoves(board, current);
            int random = (int) (Math.random() * ((posMoves.size()) + 1));
            board = makeMove(posMoves.get(random).getX(), posMoves.get(random).getY(), board, current);
        }
        return board.getWinner();
        }
    public int switchplayer(int current){
        if(current == colour ) current = opponentscolour;
        else current = colour;
    }
    }
    /*
   1. Pick a certain move.
2. Make random moves until the game is finished.
3. Calculate the winner and report back the result up the tree to the initial
move.
Using many of these simulations, a simple MC algorithm would be to:

1. Find out all possible moves to make.
2. Divide the total number of simulations by how many possible moves there
are.
3. Run random simulations this many times for each move.
4. Find the move that had the highest chance of winning. From this, infer
that such a move is the best in this situation.
     */
}
