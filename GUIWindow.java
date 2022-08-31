import javax.swing.*;
import java.awt.*;

class GUIWindow
{    
    public static void main(String[] args)
    {
        JFrame theGUI = new JFrame();
        theGUI.setTitle("Save yourself from the balls! (balls lol)");
        theGUI.setSize(700, 400);
        ColorPanel panel = new ColorPanel(Color.white, 700, 400);
        Container pane = theGUI.getContentPane();
        pane.add(panel);
        theGUI.setVisible(true);
        theGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
//By Arshiya Sharma