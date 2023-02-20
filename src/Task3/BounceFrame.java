package Task3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class BounceFrame extends JFrame {
    private BallCanvas canvas;
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
        JButton buttonAddRed = new JButton("Add red");
        JButton buttonAddHundred = new JButton("Add 100");
        JButton buttonStop = new JButton("Stop");
        buttonAddRed.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addBall(Color.red);
            }
        });
        buttonAddHundred.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for( int i = 0; i < 100; i++){
                    addBall(Color.cyan);
                }
            }
        });
        buttonStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        buttonPanel.add(buttonAddRed);
        buttonPanel.add(buttonAddHundred);
        buttonPanel.add(buttonStop);
        content.add(buttonPanel, BorderLayout.SOUTH);
    }

    public void addBall(Color color){
        Ball b = new Ball(canvas, color);
        canvas.add(b);
        BallThread thread = new BallThread(b);
        thread.start();
        System.out.println("Thread name = " +
                thread.getName());
    }
}