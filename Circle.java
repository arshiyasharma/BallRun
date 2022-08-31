// Represents a circle
import java.awt.*;
import java.util.Random;

public class Circle{
    private int centerX, centerY, radius;
    private Color color;
    private int direction, speed;
    private boolean filled;
    private    Random gen;

    //public Circle(int x, int y, int r, Color c){
    public Circle(int x, int y, int r){
        centerX = x;
        centerY = y;
        radius = r;
        color = Color.black;
        direction = 0;
        speed = 0;
        filled = false;
        gen = new Random();
    }


    public void draw(Graphics g){
        Color oldColor = g.getColor();
        g.setColor(color);
        // Translates circle's center to rectangle's origin for drawing.
        if (filled)

            g.fillOval(centerX - radius, centerY - radius, radius * 2, radius * 2);

        else

            g.drawOval(centerX - radius, centerY - radius, radius * 2, radius * 2);
        g.setColor(oldColor);
    }

    public void fill(Graphics g){
        Color oldColor = g.getColor();
        g.setColor(color);
        // Translates circle's center to rectangle's origin for drawing.
        g.fillOval(centerX - radius, centerY - radius, radius * 2, radius * 2);
        g.setColor(oldColor);
    }

    public boolean containsPoint(int x, int y){
        int xSquared = (x - centerX) * (x - centerX);
        int ySquared = (y - centerY) * (y - centerY);
        int radiusSquared = radius * radius;
        return xSquared + ySquared - radiusSquared <= 0;
    }

    public void move(int xAmount, int yAmount){
        centerX = centerX + xAmount;
        centerY = centerY + yAmount;
    }

    public void setColor(){
        int red = gen.nextInt(256);
        int green = gen.nextInt(256);
        int blue = gen.nextInt(256);
        color = new Color(red, green, blue);
    }

    public int getRadius(){
        return radius;
    }

    public void setRadius(){
        //set random radius here at 10 min
        radius = gen.nextInt(31) + 10;
    }

    public int getX(){
        return centerX;
    }

    public int getY(){
        return centerY;
    }

    public void setY(){
        centerY = 0 - radius;
    }

    public int getDirection(){
        return direction;
    }

    public void setSpeed(){
        speed = gen.nextInt(6) + 5;
        //speed = 1;   //for testing
    }   

    public void setDirection(int d){
        direction = d % 360;
    }

    public void turn(int degrees){
        direction = (direction + degrees) % 360;
    }

    //Moves the circle in the current direction using its current speed
    public void move(){

        move((int)(speed * Math.cos(Math.toRadians(direction))), (int)(speed * Math.sin(Math.toRadians(direction))));
    }

    public void moveScreen(){
        move((int)(speed * Math.cos(Math.toRadians(direction))), (int)(-1 * speed * Math.sin(Math.toRadians(direction))));
    }

    public void setFilled(boolean b){
        filled = b;
    }
}