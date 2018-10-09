
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

    public class Tile extends StackPane {
        static Othello game;
        private Circle circle = new Circle();
        private static int tileSize = 80;

        public Tile(){
            Rectangle border = new Rectangle(tileSize, tileSize);
            border.setFill(Color.GREEN);
            border.setStroke(Color.BLACK);

            setAlignment(Pos.CENTER);
            getChildren().addAll(border, circle);

            setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.PRIMARY){
                    int x = (int)getTranslateY()/tileSize; int y = (int)getTranslateX()/tileSize;
                    
                    if(game.current.getColour() == 1)


                   if(game.makeMove(x,y)){
                   // else{
                        //game.current.mostFlippedPlayer();


                   // }*/
                   //game.current.randomPlayer();
                    game.updateBoard();
                    game.showOptions(game.possibleMoves());}
                }
            });
        }

        public void drawWhite(){
            circle.setRadius(30.00);
            circle.setFill(Color.WHITE);
        }

        public void drawBlack(){
            circle.setRadius(30.00);
            circle.setFill(Color.BLACK);
        }
        public void drawYellow(){
            circle.setRadius(15.00);
            circle.setFill(Color.YELLOW);
        }
        public void removeCircle(){
            circle.setFill(Color.TRANSPARENT);
        }
        public static void setGame(Othello g){
            game = g;
        }
    }
