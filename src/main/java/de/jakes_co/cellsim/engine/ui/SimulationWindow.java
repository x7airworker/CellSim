package de.jakes_co.cellsim.engine.ui;

import de.jakes_co.cellsim.engine.Simulation;
import de.jakes_co.cellsim.engine.SimulationState;
import de.jakes_co.cellsim.engine.data.World;

import javax.swing.*;
import java.awt.*;

public class SimulationWindow extends JFrame {

    private final SimulationState state;
    private final SimulationRenderer renderer;
    private final JSplitPane root;
    private final JCheckBox pausedCheckbox = new JCheckBox("Paused");
    private final JLabel cellAmountLabel = new JLabel();

    public SimulationWindow(SimulationState state, World world) throws HeadlessException {
        super("Cell Simulation");
        this.renderer = new SimulationRenderer(world);
        this.state = state;
        state.subscribe(this::onStateUpdate);
        addKeyListener(new HotkeyListener(state));
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        JPanel optionsPanel = new JPanel();
        optionsPanel.add(new JLabel("Simulation Options"));
        optionsPanel.add(new JSeparator());

        cellAmountLabel.setText("Cells alive " + state.getCellAmount());
        optionsPanel.add(cellAmountLabel);

        optionsPanel.add(new JSeparator());

        JLabel delayLabel = new JLabel("Delay");
        JSlider delaySlider = new JSlider(10, 10000);
        delaySlider.setValue((int) state.getSimulationDelay());
        delaySlider.addChangeListener(e -> state.setSimulationDelay(delaySlider.getValue()));
        optionsPanel.add(delayLabel);
        optionsPanel.add(delaySlider);

        pausedCheckbox.setSelected(state.isPaused());
        pausedCheckbox.addChangeListener(e -> state.setPaused(pausedCheckbox.isSelected()));
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

    public SimulationRenderer getRenderer() {
        return renderer;
    }

    private void onStateUpdate() {
        pausedCheckbox.setSelected(state.isPaused());
        cellAmountLabel.setText("Cells alive " + state.getCellAmount());
    }
}
