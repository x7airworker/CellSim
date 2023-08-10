package de.jakes_co.cellsim.engine.ui;

import de.jakes_co.cellsim.engine.Simulation;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class HotkeyListener implements KeyListener {
    private final Simulation simulation;

    public HotkeyListener(Simulation simulation) {
        this.simulation = simulation;
    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE)
            simulation.togglePaused();
    }

    public void keyReleased(KeyEvent e) {

    }
}
