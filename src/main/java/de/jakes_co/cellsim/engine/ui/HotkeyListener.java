package de.jakes_co.cellsim.engine.ui;

import de.jakes_co.cellsim.engine.Simulation;
import de.jakes_co.cellsim.engine.SimulationState;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class HotkeyListener implements KeyListener {
    private final SimulationState state;

    public HotkeyListener(SimulationState state) {
        this.state = state;
    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE)
            state.togglePaused();
    }

    public void keyReleased(KeyEvent e) {

    }
}
