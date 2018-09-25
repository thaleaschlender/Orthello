import javax.swing.*;
import java.awt.*;

public class Ui {
        public static void main(String[]args){
            JFrame frame= new JFrame();
            frame.setSize(1000,1000);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setTitle("Othello");
            frame.setLayout(new GridLayout(8,8));
            for (int i=0; i<8; i++){
                for (int j=0; j<7; j++){
                    JButton cell= new JButton();
                    frame.add(cell);
                    cell.setBackground(Color.RED);
                }
                JButton cell= new JButton();
                frame.add(cell);
                cell.setBackground(Color.RED);
            }
            frame.setVisible(true);
        }
    }
