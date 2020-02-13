import javax.swing.*;
import java.awt.*;

public class MainPanel {
    public static void main(String[] args){
        //Create a frame
        JFrame frame = new JFrame("OOOOOZE");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        Color backdrop = new Color(255, 249, 248, 255);
        OOOOOZE mainPanel = new OOOOOZE(50,50);
        mainPanel.setPreferredSize(new Dimension(50*10,50*10));
        mainPanel.setBackground(backdrop);
        frame.getContentPane().add(mainPanel);
        frame.pack();
    }
}
