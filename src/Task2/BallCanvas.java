package Task2;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class BallCanvas extends JPanel{
    public static final ArrayList<Ball> balls = new ArrayList<>();
    public static final ArrayList<Hole> holes = new ArrayList<>();
    public void add(Ball b){
        BallCanvas.balls.add(b);
    }
    public void addHole(Hole h){
        BallCanvas.holes.add(h);
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        for (Ball b : balls) {
            if (b != null)
                b.draw(g2);
        }

        for (Hole h : holes) {
            h.draw(g2);
        }

        balls.removeIf(Objects::isNull);
    }
}