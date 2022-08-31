//Emerson Racca

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.Random;

public class ColorPanel extends JPanel implements ActionListener, KeyListener
{
    Timer timer = new Timer(40, this);

    private Circle[] circles;

    private int lifeLeft;
    private int gameScore, finalScore;

    //ship
    private double sx, sy, dx, dy;

    public ColorPanel(Color backColor, int width, int height){
        setBackground(backColor);
        setPreferredSize(new Dimension(width, height));

        Random gen = new Random();

        lifeLeft = 3;
        gameScore = 0;

        circles = new Circle[7];

        for(int c = 0; c < 7; c++){
            circles[c] = new Circle(c * 100 + 50, -50, 5);
            circles[c].setRadius();
            circles[c].setColor();
            circles[c].setFilled(true);
            circles[c].setDirection(270);
            circles[c].setSpeed();
        }
        timer.start();
        //Added for ship
        addKeyListener(this);
        setFocusable(true);
        sx = 350 - 20;
        sy = 400 - 75;
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        circles[0].fill(g);
        circles[1].fill(g);
        circles[2].fill(g);
        circles[3].fill(g);
        circles[4].fill(g);
        circles[5].fill(g);
        circles[6].fill(g);
        //Added for ship
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.RED);
        if(lifeLeft >= 1){
            g2.fill(new Rectangle2D.Double(sx, sy, 40, 40));
            g.drawString("Score: " + gameScore, 550, 10);
        }
        if(lifeLeft == 3){
            g2.fill(new Rectangle2D.Double(10, 10, 40, 40));
            g2.fill(new Rectangle2D.Double(60, 10, 40, 40));
            g2.fill(new Rectangle2D.Double(110, 10, 40, 40));
        }
        if(lifeLeft == 2){
            g2.fill(new Rectangle2D.Double(10, 10, 40, 40));
            g2.fill(new Rectangle2D.Double(60, 10, 40, 40));
        }
        if(lifeLeft == 1){
            g2.fill(new Rectangle2D.Double(10, 10, 40, 40));
        }
        if(lifeLeft <= 0){
            g.drawString("Game Over!!!", 300, 200);
            g.drawString("Final Score: " + finalScore, 550, 10);
        }
    }


    public void actionPerformed(ActionEvent e){
        for(int k = 0; k < 7; k++){
            int y = circles[k].getY();
            int radius = circles[k].getRadius();
            int height = getHeight();
            if(y - radius >= height){
                //Circle has passed through, so update score proportionate to the radius size.
                gameScore = gameScore + radius;
                //Start over at the top with a new circle, different size, speed, color
                resetCircle(circles[k]);
            }    
            circles[k].moveScreen();
            //ship
            sx += dx;
            sy += dy;
            dx = 0;
            dy = 0;
            //check for collision only when there is life left
            if(lifeLeft != 0){
                boolean collide = checkCollision(circles[k]);
                if(collide){
                    //System.out.println("Collided!");
                    lifeLeft--;
                    if(lifeLeft == 0)
                        finalScore = gameScore;
                    resetCircle(circles[k]);
                }
            }
        }    
        repaint();
    }

    public void resetCircle(Circle c){
        c.setSpeed();
        c.setColor();
        c.setRadius();
        c.setY();
    }

    public boolean checkCollision(Circle circ){
        //top and bottom
        if((double)circ.getX() >= sx && (double)circ.getX() <= sx + 40){
            if(Math.abs((double)circ.getY() - sy) <= (double)circ.getRadius()){
                return true;  //top
            }
            if(Math.abs((double)circ.getY() - (sy + 40)) <= (double)circ.getRadius()){
                return true;  //bottom
            }
        }
        //left top and left bottom
        if((double)circ.getX() > (sx - (double)circ.getRadius()) && (double)circ.getX() < sx){
            if((double)circ.getY() > (sy - (double)circ.getRadius()) && (double)circ.getY() < sy){
                if(Math.sqrt(Math.pow(sx - (double)circ.getX(), 2.0) + Math.pow(sy - (double)circ.getY(), 2.0 )) <= (double)circ.getRadius()){
                    return true;  //top left
                }
            }
            if((double)circ.getY() < (sy + 40 + (double)circ.getRadius()) && (double)circ.getY() > (sy + 40)){
                if(Math.sqrt(Math.pow(sx - (double)circ.getX(), 2.0) + Math.pow((double)circ.getY() - (sy + 40), 2.0)) <= (double)circ.getRadius()){
                    return true;  //bottom left
                } 
            }
        }
        //left and right
        if((double) circ.getY() >= sy && (double)circ.getY() <= (sy + 40)){
            if(Math.abs((double)circ.getX() - sx) <= (double)circ.getRadius()){
                return true;  //left
            }
            if(Math.abs((double) circ.getX() - (sx + 40)) <= (double)circ.getRadius()){
                return true; //right
            }
        }
        //right top and right bottom
        if((double)circ.getX() < (sx + 40 + (double)circ.getRadius()) && (double)circ.getX() > (sx + 40)){    
            if((double)circ.getY() > (sy - (double)circ.getRadius()) && (double)circ.getY() < sy){
                if(Math.sqrt(Math.pow((double)circ.getX() - (sx + 40), 2.0) + Math.pow(sy - (double)circ.getY(), 2.0)) <= (double)circ.getRadius()){
                    return true;  //top right
                }
            }
            if((double)circ.getY() < (sy + 40 + (double)circ.getRadius()) && (double)circ.getY() > (sy + 40)){
                if(Math.sqrt(Math.pow((double)circ.getX() - (sx + 40), 2.0) + Math.pow((double)circ.getY() - (sy + 40), 2.0)) <= (double)circ.getRadius()){
                    return true;  //bottom right
                }
            }
        }
        //otherwise, no collision
        return false;
    }

    public void up() {
        if (sy != 0){
            //dy = -3.5;
            dy = -10.0;
            dx = 0;
        }
    }

    public void down() {
        if (sy <= 320) {
            //dy = 3.5;
            dy = 10.0;
            dx = 0;
        }
    }

    public void left() {
        if (sx >=0) {
            //dx = -3.5;
            dx = -10.0;
            dy = 0;
        }
    }

    public void right() {
        if (sx <= 650) {
            //dx = 3.5;
            dx = 10.0;
            dy = 0;
        }
    }

    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_UP) {
            up();
        }
        if (code == KeyEvent.VK_DOWN) {
            down();
        }
        if (code == KeyEvent.VK_RIGHT) {
            right();
        }
        if (code == KeyEvent.VK_LEFT) {
            left();
        }
    }

    //Note the two methods below are empty. 
    //We don't need them but a keylistener must have all three of its methods typed out even if some are empty
    public void keyTyped(KeyEvent e) {
    }
    public void keyReleased(KeyEvent e) {
    }
}