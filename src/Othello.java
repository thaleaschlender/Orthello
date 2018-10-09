import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Othello extends Application {
        //general game information
        private static Board board = new Board();
        private static Player white = new Player(1);
        private static Player black = new Player(2);
        public static Player current = black;
        //window frame information
        private static final int TILE_SIZE = 80;
        private static final int WIDTH = 8;
        private static final int HEIGHT = 8;

        private static Group tileGroup = new Group();
        private static Group pieceGroup = new Group();

        private static Tile[][] boardUi = new Tile[HEIGHT][HEIGHT];

        private Parent createContent() {
            Tile.setGame(this);
            Player.setGame(this);
            Pane root = new Pane();
            root.setPrefSize(640, 680);
            root.getChildren().addAll(tileGroup, pieceGroup);
            root.setStyle("-fx-background-color: #A9A9A9;;");
            fillBoard();
            return root;
        }

        @Override
        public void start(Stage primaryStage) throws Exception {
            primaryStage.setScene(new Scene(createContent()));
            primaryStage.setTitle("Othello");
            primaryStage.show();
            updateBoard();
            showOptions(possibleMoves());
        }

        public boolean makeMove(int x, int y){
            boolean valid = false;
            if(board.getBoard()[x][y].getColour()!=0) return false;
            board.getBoard()[x][y].changeColour(current.getColour());
            int check = current.getNumber();
            for(int i = x-1;i <= x+1; i++ )
                for (int j = y - 1; j <= y + 1 ; j++) {
                    if (i>-1 && i<8 && j >-1 && j<8 &&((i-x)!=0||(j-y)!=0)&&(board.getBoard()[i][j].getColour() == check) && checkLine(x, y, (i - x), (j - y), check))
                        valid = true;
                }
            if(!valid) {
                board.getBoard()[x][y].changeColour(0);
                System.out.println("INVALID");
                return false;
            }
            else{
                if(current == black ) current = white;
                else current = black;
                System.out.println("VALID");
            }

            updateBoard();
            return valid;
        }
        public int validMove(int x, int y) {
            int valid = 0;
            if (board.getBoard()[x][y].getColour() != 0) return 0;
            board.getBoard()[x][y].changeColour(current.getColour());
            int check = current.getNumber();
            for (int i = x - 1; i <= x + 1; i++){
                for (int j = y - 1; j <= y + 1; j++) {
                    if (i > -1 && i < 8 && j > -1 && j < 8
                            && ((i - x) != 0 || (j - y) != 0) &&
                            (board.getBoard()[i][j].getColour() == check) && checkLineWithoutFlip(x, y, (i - x), (j - y), check).size() != 0) {
                        valid += checkLineWithoutFlip(x, y, (i - x), (j - y), check).size();

                    }
                }
            }
            if(valid != 0) System.out.println("x: " + x + " y: " + y + "with flippables = " + valid);
            board.getBoard()[x][y].changeColour(0);
            return valid;
        }
    public ArrayList<Piece> possibleMoves(){
            ArrayList<Piece> possibleMoves = new ArrayList<Piece>();
            for(int i =0; i < board.getBoard().length;i++){
                for(int j=0; j < board.getBoard()[i].length;j++){
                    if(validMove(i,j)>0){
                        possibleMoves.add(board.getBoard()[i][j]);
                        System.out.println("board: x. "+i+" y. "+j+" added to possibilities");
                    }
                }
            }
             //updateBoard();
            //showOptions(possibleMoves);
        return possibleMoves;
    }

        //direction x is vertical, direction y horizontal
        private boolean checkLine(int x, int y, int directionX, int directionY, int check) {
            System.out.println(check);
            ArrayList<Piece> tobeflipped = new ArrayList<>();
            boolean finished = false;
            if(x+directionX<0|| x+directionX>7 || y+directionY< 0 || y+directionY >7) return false;
            while(!finished){
                if (x+directionX<0 || x+directionX>7 || y+directionY< 0 || y+directionY >7) return false;
                else{x = x + directionX;
                y = y + directionY;
                if(board.getBoard()[x][y].getColour()==check)
                    tobeflipped.add(board.getBoard()[x][y]);
                else if(board.getBoard()[x][y].getColour()== 0)
                    return false;

                else finished = true;
            }}
            flip(tobeflipped);
            return true;
        }
    private ArrayList<Piece> checkLineWithoutFlip(int x, int y, int directionX, int directionY, int check) {
        ArrayList<Piece> flippable = new ArrayList<>();
        boolean finished = false;
        if(x+directionX<0 || x+directionX>7 || y+directionY< 0 || y+directionY >7) return new ArrayList<>();
        while(!finished&& x+directionX>-1 && x+directionX< 8 && y+directionY> -1 && y+directionY <8){
            if(x+directionX<0 || x+directionX>7 || y+directionY< 0 || y+directionY >7) return new ArrayList<>();
            else{
                x = x + directionX;
                y = y + directionY;
                if(board.getBoard()[x][y].getColour()== 0) return new ArrayList<>();
                else if(board.getBoard()[x][y].getColour()==check) flippable.add(board.getBoard()[x][y]);
                else if(board.getBoard()[x][y].getColour()!=check){
                finished = true;}
                else

                    return new ArrayList<>();

        }}
        return flippable;
    }

        private void flip (ArrayList<Piece> pieces){
            for(int i = 0; i < pieces.size();i++){
                pieces.get(i).flip();
            }
        }
    public void showOptions(ArrayList<Piece> options){
            for(int i = 0; i < options.size(); i++)
                boardUi[options.get(i).getX()][options.get(i).getY()].drawYellow();
    }
    public void updateBoard(){
            for (int x = 0; x < WIDTH; x++) {
                for (int y = 0; y < HEIGHT; y++) {
                    boardUi[x][y].removeCircle();
                    if(board.getBoard()[x][y].getColour() == 1) boardUi[x][y].drawWhite();
                    else if(board.getBoard()[x][y].getColour() == 2) boardUi[x][y].drawBlack();
                }
            }
        }

        private void fillBoard (){
            for (int x = 0; x < WIDTH; x++){
                for (int y = 0; y < HEIGHT; y++){
                    Tile tile = new Tile();
                    tile.setTranslateX(y * TILE_SIZE);
                    tile.setTranslateY(x * TILE_SIZE);
                    boardUi[x][y] = tile;
                    tileGroup.getChildren().add(boardUi[x][y]);
                }
            }
        }
        public static void main(String[] args) {launch(args); }


        }



