package Task1;

import Task1.Ball;

import java.awt.Graphics2D;
import java.awt.Graphics;
import javax.swing.JPanel;
import java.util.ArrayList;

public class BallCanvas extends JPanel{
    private final ArrayList<Ball> balls = new ArrayList<>();
    public void add(Ball b){
        this.balls.add(b);
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        for (Ball b : balls) {
            b.draw(g2);
        }
    }
}