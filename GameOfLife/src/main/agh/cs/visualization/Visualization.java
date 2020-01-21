package agh.cs.visualization;

import agh.cs.board.Board;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Visualization {
    private JFrame frame;
    private Timer timer;
    private JTextArea textArea;
    private JButton startStopButton;
    private JButton stopSimulationButton;
    private Board board;

    public Visualization(Board board){
        this.board = board;
        this.init();
    }

    private void init(){
        this.frame = new JFrame("Game of life");
        frame.setSize(1200, 800);
        frame.setLayout(null);
        frame.setVisible(true);

        this.textArea=new JTextArea("");
        textArea.setBounds(100,100, 1000,700);
        textArea.setEditable(false);


        this.startStopButton = new JButton("");
        startStopButton.setBounds(50,20,80,20);
        frame.add(startStopButton);

        startStopButton.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (timer == null)
                    startAnimation();
                else
                    stopAnimation();
            }
        });

        this.stopSimulationButton = new JButton("End");
        stopSimulationButton.setBounds(50,60,80,20);
        stopSimulationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(0);
            }
        });

        frame.add(startStopButton);
        frame.add(stopSimulationButton);

    }

    ActionListener timerListener = new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            if(!board.isAnyCellAlive()){
                textArea.setText(board.toString());
                startStopButton.setVisible(false);
                timer.stop();
            }
            textArea.setText(board.toString());
            frame.add(textArea);
            SwingUtilities.updateComponentTreeUI(frame);
            board.start();
        }
    };

    public void startAnimation(){
        if (timer == null) {
            timer = new Timer(0, timerListener);
            timer.start();
            startStopButton.setText("Pause");
        }
    }

    private void stopAnimation() {
        if (timer != null) {
            timer.stop();
            timer = null;
            startStopButton.setText("Start");
        }
    }
}