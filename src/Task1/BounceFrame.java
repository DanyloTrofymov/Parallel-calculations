package Task1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class BounceFrame extends JFrame {
    private final BallCanvas canvas;
    public static final int WIDTH = 450;
    public static final int HEIGHT = 350;
    public BounceFrame() {
        this.setSize(WIDTH, HEIGHT);
        this.setTitle("Bounce program");
        this.canvas = new BallCanvas();
        System.out.println("In Frame Thread name = "
                + Thread.currentThread().getName());
        Container content = this.getContentPane();
        content.add(this.canvas, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.lightGray);
        JButton buttonAddOne = new JButton("Add 1");
        JButton buttonAddTen = new JButton("Add 10");
        JButton buttonAddHundred = new JButton("Add 100");
        JButton buttonStop = new JButton("Stop");
        buttonAddOne.addActionListener( e -> {
                addBall();
        });
        buttonAddTen.addActionListener(e -> {
                for( int i = 0; i < 10; i++){
                    addBall();
            }
        });
        buttonAddHundred.addActionListener(e -> {
                for( int i = 0; i < 100; i++){
                    addBall();
                }
        });
        buttonStop.addActionListener(e -> {
                System.exit(0);
        });
        buttonPanel.add(buttonAddOne);
        buttonPanel.add(buttonAddTen);
        buttonPanel.add(buttonAddHundred);
        buttonPanel.add(buttonStop);
        content.add(buttonPanel, BorderLayout.SOUTH);
    }

    public void addBall(){
        Ball b = new Ball(canvas);
        canvas.add(b);
        BallThread thread = new BallThread(b);
        thread.start();
        System.out.println("Thread name = " +
                thread.getName());
    }
}