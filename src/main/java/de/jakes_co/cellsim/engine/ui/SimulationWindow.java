package de.jakes_co.cellsim.engine.ui;

import de.jakes_co.cellsim.engine.state.SimulationState;
import de.jakes_co.cellsim.engine.data.World;
import de.jakes_co.cellsim.engine.state.StateChangeObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;

public class SimulationWindow extends JFrame implements StateChangeObserver {

    private final SimulationRenderer renderer;
    private final JSplitPane root;
    private final JCheckBox pausedCheckbox = new JCheckBox("Paused");
    private final JLabel cellAmountLabel = new JLabel();
    private final JSlider delaySlider = new JSlider(10, 10000);

    public SimulationWindow(World world) throws HeadlessException {
        super("Cell Simulation");
        renderer = new SimulationRenderer(world);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        JPanel optionsPanel = new JPanel();
        optionsPanel.add(new JLabel("Simulation Options"));
        optionsPanel.add(new JSeparator());

        optionsPanel.add(cellAmountLabel);

        optionsPanel.add(new JSeparator());

        JLabel delayLabel = new JLabel("Delay");
        optionsPanel.add(delayLabel);
        optionsPanel.add(delaySlider);

        optionsPanel.add(pausedCheckbox);

        root = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, renderer, optionsPanel);

        getContentPane().add(root);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void showWindow() {
        pack();
        setVisible(true);
        setSize(1280, 720);
        root.setDividerLocation(getWidth() - 150);
    }

    public void accept(SimulationState state) {
        pausedCheckbox.setSelected(state.isPaused());
        cellAmountLabel.setText("Cells alive " + state.getCellAmount());

        pausedCheckbox.setSelected(state.isPaused());
        pausedCheckbox.addChangeListener(e -> state.setPaused(pausedCheckbox.isSelected()));

        delaySlider.setValue((int) state.getSimulationDelay());
        delaySlider.addChangeListener(e -> state.setSimulationDelay(delaySlider.getValue()));
        renderer.repaint(0);
    }

    public synchronized void addKeyListener(KeyListener l) {
        super.addKeyListener(l);
    }
}
