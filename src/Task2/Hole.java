package Task2;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Random;

public class Hole {
    private Component canvas;
    public final int XSIZE = 25;
    public final int YSIZE = 25;
    public int x = 0;
    public int y = 0;

    public Hole(Component c){
        this.canvas = c;
        x = new Random().nextInt(this.canvas.getWidth() - XSIZE);
        y = new Random().nextInt(this.canvas.getHeight()- YSIZE);
    }
    public void draw (Graphics2D g2){
        g2.setColor(Color.black);
        g2.fill(new Ellipse2D.Double(x,y,XSIZE,YSIZE));
    }

    public boolean catched(int x, int y) {
        return x >= this.x && x < this.x + this.XSIZE && y >= this.y && y < this.y + this.YSIZE;
    }
}
