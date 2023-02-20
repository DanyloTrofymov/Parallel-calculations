package Task2;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class BallCanvas extends JPanel{
    public static ArrayList<Task2.Ball> balls = new ArrayList<>();
    public static ArrayList<Task2.Hole> holes = new ArrayList<>();
    public void add(Task2.Ball b){
        BallCanvas.balls.add(b);
    }
    public void addHole(Task2.Hole h){
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