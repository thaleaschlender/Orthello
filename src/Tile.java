
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

    public class Tile extends StackPane {

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
                    if(Othello.makeMove(x,y)){
                        Othello.board.print();Othello.updateBoard();
                    }
                    System.out.println("x:" + x);System.out.println("y:" + y);
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
    }
