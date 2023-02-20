package Task4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class BounceFrame extends JFrame {
    static BallCanvas canvas;
    public static final int WIDTH = 600;
    public static final int HEIGHT = 450;
    private static BallThread prevThread = null;
    private static int caught = 0;
    private static final JLabel caughtText = new JLabel("Caught balls: 0");
    public BounceFrame() {
        this.setSize(WIDTH, HEIGHT);
        this.setTitle("Bounce program");
        BounceFrame.canvas = new BallCanvas();

        System.out.println("In Frame Thread name = "
                + Thread.currentThread().getName());

        Container content = this.getContentPane();
        content.add(this.canvas, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.lightGray);
        JButton buttonAddHole = new JButton("Add hole");
        JButton buttonAddOne = new JButton("Add 1");
        JButton buttonStop = new JButton("Stop");
        buttonAddHole.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Hole h = new Hole(canvas);
                canvas.addHole(h);
                canvas.repaint();
            }
        });
        buttonAddOne.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    addBall();
                } catch (InterruptedException ex) {

                }
            }
        });

        buttonStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        buttonPanel.add(buttonAddHole);
        buttonPanel.add(buttonAddOne);
        buttonPanel.add(buttonStop);
        buttonPanel.add(caughtText);
        content.add(buttonPanel, BorderLayout.SOUTH);
    }
    public void addBall() throws InterruptedException {
            Ball b = new Ball(canvas);
            canvas.add(b);
            if(prevThread != null)
                prevThread.join();
            BallThread thread = new BallThread(b);
            thread.start();
            prevThread = thread;
            System.out.println("Thread name = " +
                    thread.getName());
    }

    public static void incCaughtText() {
        BounceFrame.caught += 1;
        caughtText.setText("Caught balls: " + caught);
    }
}