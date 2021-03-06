import java.util.ArrayList;
import java.util.Comparator;

public class MontecarloAlgorithm extends Player {
    final int numberOfSimulations = 200;
    int opponentscolour;
    Roxanne rox;
    boolean tournement;
    public MontecarloAlgorithm (int c, boolean tournement){
        super(c);
        if( c == 1)  opponentscolour = 2;
        else opponentscolour = 1;
        rox = new Roxanne(this);
        this.tournement = tournement;
    }
    public void play (int x, int y){
        Board initBoard = new Board(game.board);
        Piece piece;
       // if(tournement) piece = tournementPlayMonteCarlo(initBoard);
        //else
            piece = montecarlo(initBoard);
        game.makeMove(piece.getX(),piece.getY());
        game.updateBoard();
    }

    /*

1. Find out all possible moves to make.
2. Divide the total number of simulations by how many possible moves there
are.
3. Run random simulations this many times for each move.
4. Find the move that had the highest chance of winning. From this, infer
that such a move is the best in this situation.
     */
    public Piece montecarlo (Board initBoard){

        ArrayList<Piece> possMoves;
        if(tournement) possMoves = preProcess(possibleMoves(initBoard, switchplayer(colour)));
        else possMoves = possibleMoves(initBoard, switchplayer(colour));
        int numberOfSim = numberOfSimulations/possMoves.size();
        Piece best = null;
        double bestScore = 0;

            for (Piece p:possMoves) {
                double effectiveness = 0;
                for (int i = 0; i < numberOfSim; i++){
                    if(randomSimulation(initBoard,p) == colour) effectiveness++;
                }
                //System.out.println("ef " + effectiveness + " sim no " + numberOfSim);
                effectiveness = (effectiveness/numberOfSim);
                //System.out.println("total ef " + effectiveness);
                if( effectiveness >= bestScore){
                    best = p;
                    bestScore = effectiveness;
                }
                //numberOfSim += 5;
            }

        return best;
    }
    public ArrayList<Piece> preProcess(ArrayList<Piece> possibleMoves){
       // for(Piece p : possibleMoves) System.out.println(" piece x: " + p.getX() + " y: " + p.getY() + " eval " + rox.getValue(p.getX(),p.getY()));
        boolean swapped = true;
        while(swapped){
            swapped = false;
            for(int j = 0; j <possibleMoves.size()-1; j++) {

                if (rox.getValue(possibleMoves.get(j).getX(), possibleMoves.get(j).getY()) > rox.getValue(possibleMoves.get(j + 1).getX(), possibleMoves.get(j + 1).getY())) {
                    Piece temp = possibleMoves.get(j);
                    possibleMoves.set(j, possibleMoves.get(j + 1));
                    possibleMoves.set(j + 1, temp);
                    swapped = true;

                }
            }
        }
        int delete = possibleMoves.size()/ 5; // 20%
        for(int i = 0; i < delete; i++){
            possibleMoves.remove(i);
        }
        return possibleMoves;
    }

    /*
1. Pick a certain move.
2. Make random moves until the game is finished.
3. Calculate the winner and report back the result up the tree to the initial
move.

*/
    public int randomSimulation (Board initBoard, Piece p){
        Board board = new Board (initBoard);
        board = makeMove(p.getX(),p.getY(),board,colour);
        int current = switchplayer(colour);
        while(isGameover(board, switchplayer(current))==0) {
            current = switchplayer(current);
            ArrayList<Piece> posMoves = possibleMoves(board, current);
            int random = (int) (Math.random() * (posMoves.size()));
            board = makeMove(posMoves.get(random).getX(), posMoves.get(random).getY(), board, current);
        }
        if(isGameover(board,switchplayer(current))==1){
            return switchplayer(current);
        }
        else
        return board.getWinner();
        }
    public int switchplayer(int current){
        if(current == colour ) current = opponentscolour;
        else current = colour;
        return current;
    }

    public int isGameover(Board board, int current){
        boolean gameover = true;
        for(int i = 0; i < board.getBoard().length; i++)
            for(int j = 0; j < board.getBoard()[0].length; j++)
                if(board.getBoard()[i][j].getColour() == 0) gameover= false;

        if(gameover) return 2; // GAME OVER DUE TO FULL BOARD
        else if(possibleMoves(board,current).size()== 0) return 1; // GAME OVER DUE TO PLAYER FAIL
        return 0; // NOT GAME OVER
    }



    public Piece tournementPlayMonteCarlo (Board initBoard){

        ArrayList<Piece> possMoves = possibleMoves(initBoard, switchplayer(colour));
        int numberOfSim = numberOfSimulations/possMoves.size();
        Piece worst = null;
        double worstScore = 1;
        while(possMoves.size() != 1) {
            for (Piece p : possMoves) {
                double effectiveness = 0;
                for (int i = 0; i < numberOfSim; i++) {
                    if (randomSimulation(initBoard, p) == colour) effectiveness++;
                }
                //System.out.println("ef " + effectiveness + " sim no " + numberOfSim);
                effectiveness = effectiveness / numberOfSim;
                //System.out.println("total ef " + effectiveness);
                if (effectiveness <= worstScore) {
                    worst = p;
                    worstScore = effectiveness;
                }
            }
            //System.out.println(" worst score " + worstScore);
            possMoves.remove(worst);
            worstScore = 1;
            worst = null;
        }
        return possMoves.get(0);
    }
}



