
    import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Othello extends Application {

        public static Board board = new Board();
        static boolean playerID = true;

        private static final int TILE_SIZE = 80;
        private static final int WIDTH = 8;
        private static final int HEIGHT = 8;

        private static Group tileGroup = new Group();
        private static Group pieceGroup = new Group();

        private static Tile[][] boardUi = new Tile[HEIGHT][HEIGHT];

        private Parent createContent() {
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
        }

        public static boolean makeMove(int x, int y){
            boolean valid = false;
            board.getBoard()[x][y].changeColour(playerID);
            int check;
            if(playerID) check = 2;
            else check =1;
            //check array out of bounds
            for(int i = x-1;i <= x+1; i++ )
                for (int j = y - 1; j <= y + 1 ; j++) {
                    if(i < 0 || i > 7) break;
                    if(j < 0 || j > 7) break;

                    if ((board.getBoard()[i][j].getColour() == check) && checkLine(x, y, (i - x), (j - y), check)) {
                        valid = true;
                    }
                }
            playerID = !playerID;
            if(!valid) {
                board.getBoard()[x][y].changeColour(0);
                playerID = !playerID;
                System.out.println("INVALID");
                return false;
            }
            else
                System.out.println("VALID");
            board.print(); updateBoard();
            return valid;
        }

        //direction x is vertical, direction y horizontal
        private static boolean checkLine(int x, int y, int directionX, int directionY, int check) {
            //checks straight line
            //potentially considering the directions as integers
            // create Arraylist with intermediate pieces if there is a !PlayerID stone in the direection, flip them and quit method
            ArrayList<Piece> tobeflipped = new ArrayList<>();
            boolean finished = false;
            while(!finished&& x >-1 && x < 8 && y > -1 && y <8){
                System.out.println(x +" " + y);
                if(board.getBoard()[x+directionX][y+directionY].getColour()==check){

                    tobeflipped.add(board.getBoard()[x+directionX][y+directionY]);
                    x = x + directionX;
                    y = y + directionY;
                }
                else if(board.getBoard()[x+directionX][y+directionY].getColour()== 0)
                    return false;
                else finished = true;
            }
            flip(tobeflipped);

            System.out.println("CHECK " + x + " " + y + "with direction " + directionX + " " + directionY);
            return true;
        }

        private static void flip (ArrayList<Piece> pieces){
            for(int i = 0; i < pieces.size();i++){
                pieces.get(i).flip();
            }
        }

        public static void updateBoard(){
            for (int x = 0; x < WIDTH; x++) {
                for (int y = 0; y < HEIGHT; y++) {
                    if(board.getBoard()[x][y].getColour() == 1) boardUi[x][y].drawWhite();
                    else if(board.getBoard()[x][y].getColour() == 2) boardUi[x][y].drawBlack();
                }
            }
        }

        private static void fillBoard (){
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


