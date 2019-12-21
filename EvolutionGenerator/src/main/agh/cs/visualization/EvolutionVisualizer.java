package agh.cs.visualization;
import agh.cs.map.WorldSimulator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EvolutionVisualizer {
    private JFrame frame;
    private Timer timer;
    private JTextArea textArea;
    private JTextArea statistics;
    private JButton startStopButton;
    private JButton stopSimulationButton;
    private WorldSimulator worldSimulator;

    public EvolutionVisualizer(WorldSimulator worldSimulator){
        this.init();
        this.worldSimulator = worldSimulator;
    }

    private void init(){
        this.frame = new JFrame("Evolution generator");
        frame.setSize(1200,900);
        frame.setLayout(null);
        frame.setVisible(true);

        this.textArea=new JTextArea("");
        textArea.setBounds(50,120, 1000,800);
        textArea.setEditable(false);

        this.statistics=new JTextArea("");
        statistics.setBounds(600,10, 200,80);
        statistics.setEditable(false);

        this.startStopButton = new JButton("");
        startStopButton.setBounds(50,20,80,20);
        frame.add(startStopButton);

        startStopButton.addActionListener( new ActionListener() {
            // The action listener that responds to the
            // button starts or stops the animation.  It
            // checks the value of timer to find out which
            // to do.  Timer is non-null when the animation
            // is running, so if timer is null, the
            // animation needs to be started.
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
        // Define an action listener to respond to events
        // from the timer.
        public void actionPerformed(ActionEvent evt) {
            if(!worldSimulator.getMap().areAnimalsAlive()){
                textArea.setText(worldSimulator.getMap().toString());
                startStopButton.setVisible(false);
                timer.stop();
            }
            statistics.setText(worldSimulator.getStatistics());
            frame.add(statistics);
            SwingUtilities.updateComponentTreeUI(frame);
            textArea.setText(worldSimulator.getMap().toString());
            frame.add(textArea);
            SwingUtilities.updateComponentTreeUI(frame);
            worldSimulator.anotherDay();
        }
    };

    public void startAnimation(){
        if (timer == null) {
            // Start the animation by creating a Timer that
            // will fire an event every 100 milliseconds, and
            // will send those events to timerListener.
            timer = new Timer(100, timerListener);
            timer.start();  // Make the time start running.
            startStopButton.setText("Pause");
        }
    }

    private void stopAnimation() {
        // Stop the animation by stopping the timer, unless the
        // animation is not running.
        if (timer != null) {
            timer.stop();   // Stop the timer.
            timer = null;   // Set timer variable to null, so that we
            //   can tell that the animation isn't running.
            startStopButton.setText("Start");
        }
    }
}
