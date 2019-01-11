import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import java.util.ArrayList;
//direction x is vertical, direction y horizontal
public class Othello extends Application {
    //general game information
    public static Board board = new Board();
    public static Player white = new AlphaBeta(1);
    public static Player black = new GreedyAlgorithm(2,2);
    public static Player current = black;
    //window frame information
    private static final int TILE_SIZE = 60;
    private static final int WIDTH = 8;
    private static final int HEIGHT = 8;

    private static Group tileGroup = new Group();
    private static Group pieceGroup = new Group();
    public static HBox hbox = new HBox(10);
    private static Stage stage;

    public boolean gameover = false;
    public int winner = 0;

    private static Tile[][] boardUi = new Tile[HEIGHT][WIDTH];

    public int[] gameLoop(){
        //board = new Board();
        board.clearBoard();
        while(!isGameover()){
            current.play(0, 0);
            hbox.getChildren().clear();
            updateScore();
        }
        return board.returnScores();
    }
    public void turn (int x, int y){
        //black.getEvalFunction().setW1(96);
        //black.getEvalFunction().setW2(-14);
        //black.getEvalFunction().setW3(-15);
        if(!isGameover()){
            current.play(x, y);
            hbox.getChildren().clear();
            updateScore();
        }
        else gameOverScreen();
    }
    public boolean isGameover(){
        if(board.gameOver()) return true;
        else if(possibleMoves().size()== 0)return true;
        return false;
    }

    public void run(){
        Application.launch();

    }
    //public static void main(String[] args) {launch(args); }
    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.setTitle("Othello");
        primaryStage.show();
        updateBoard();
    }
    private Parent createContent() {
        Tile.setGame(this);
        Player.setGame(this);
        BorderPane root = new BorderPane();
        root.setPrefSize(480, 500);
        root.getChildren().addAll(tileGroup, pieceGroup);
        root.setBottom(hbox);
        root.setStyle("-fx-background-color: #A9A9A9;;");
        fillBoard();
        return root;
    }
    public void gameOverScreen(){
        gameover = true;
        winner = board.getWinner();
        System.out.println("GAME OVERRRRRRRRRRRR");
        /*Text text = new Text();
        text.setText("Game Over");
        text.setX(210);
        text.setY(248);
        Group gameover = new Group(text);
        Scene gameOverScene = new Scene(gameover, 480, 500);
        stage.setScene(gameOverScene);*/
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
    public void updateBoard(){
        possibleMoves();
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                boardUi[x][y].removeCircle();
                if(board.getBoard()[x][y].getColour() == 1) boardUi[x][y].drawWhite();
                else if(board.getBoard()[x][y].getColour() == 2) boardUi[x][y].drawBlack();
                else if(board.getBoard()[x][y].getValidity())boardUi[x][y].drawYellow();
            }
        }
    }
    public void updateScore(){
        int playerID = current.getColour();

        Label lable1= new Label("Player: " + playerID);
        lable1.setTextFill(Color.BLACK);
        lable1.setFont(Font.font("Verdans", FontWeight.BOLD, 15));
        Label lable2= new Label("Score: " + board.printScore(1));
        lable2.setTextFill(Color.WHITE);
        lable2.setFont(Font.font("Verdans",FontWeight.BOLD, 15));
        Label lable3= new Label("Score: " + board.printScore(2));
        lable3.setTextFill(Color.BLACK);
        lable3.setFont(Font.font("Verdans", FontWeight.BOLD, 15));
        //Button button1 = new Button("Restart");

        hbox.getChildren().addAll(lable1, lable2, lable3);

    }
    public void switchplayer(){
        if(current == black ) current = white;
        else current = black;
    }
    public boolean makeMove(int x, int y) {
        boolean valid = false;
        ArrayList<Piece> flips;
        //System.out.println("x" + x + " y " + y);
        if(board.getBoard()[x][y].getColour()!=0) return false;
        board.getBoard()[x][y].changeColour(current.getColour());
        int check = current.getNumber();
        for(int i = x-1;i <= x+1; i++ )
            for (int j = y - 1; j <= y + 1 ; j++) {
                if (i>-1 && i<8 && j >-1 && j<8 &&((i-x)!=0||(j-y)!=0)&&(board.getBoard()[i][j].getColour() == check)) {
                    flips = checkLine(x, y, (i - x), (j - y), check);

                    if (flips.size() != 0) {
                        valid = true;
                        for(Piece f: flips) f.flip();
                    }
                }
            }
        if(!valid) {
            System.out.println("invalid");
            board.getBoard()[x][y].changeColour(0);
            return false;
        }
        else switchplayer();
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
                        (board.getBoard()[i][j].getColour() == check) && checkLine(x, y, (i - x), (j - y), check).size() != 0) {
                    valid += checkLine(x, y, (i - x), (j - y), check).size();
                }
            }
        }
        board.getBoard()[x][y].changeColour(0);
        return valid;
    }
    public ArrayList<Piece> possibleMoves(){
        ArrayList<Piece> possibleMoves = new ArrayList<Piece>();
        for(int i =0; i < board.getBoard().length;i++){
            for(int j=0; j < board.getBoard()[i].length;j++){
                board.getBoard()[i][j].setValid(false);
                if(validMove(i,j)>0) {
                    possibleMoves.add(board.getBoard()[i][j]);
                    board.getBoard()[i][j].setValid(true);
                }
            }
        }
        //if(possibleMoves.size() == 0) gameOverScreen();

        return possibleMoves;
    }
    private ArrayList<Piece> checkLine(int x, int y, int directionX, int directionY, int check) {
        ArrayList<Piece> tobeflipped = new ArrayList<>();
        boolean finished = false;
        if(x+directionX<0|| x+directionX>7 || y+directionY< 0 || y+directionY >7) return new ArrayList<>();
        while(!finished){
            if (x+directionX<0 || x+directionX>7 || y+directionY< 0 || y+directionY >7) return new ArrayList<>();
            else{
                x = x + directionX;
                y = y + directionY;
                if(board.getBoard()[x][y].getColour()==check) tobeflipped.add(board.getBoard()[x][y]);
                else if(board.getBoard()[x][y].getColour()== 0) return new ArrayList<>();
                else finished = true;
            }}
        return tobeflipped;
    }


}