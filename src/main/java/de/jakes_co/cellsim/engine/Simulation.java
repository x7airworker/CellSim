package de.jakes_co.cellsim.engine;

import de.jakes_co.cellsim.engine.data.Coordinate;
import de.jakes_co.cellsim.engine.data.World;
import de.jakes_co.cellsim.engine.ui.SimulationWindow;
import org.tinylog.Logger;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;


public class Simulation {

    private final SimulationState state = new SimulationState();
    private final World world = new World();
    private final SimulationWindow simulationWindow = new SimulationWindow(state, world);
    private Thread simulationThread;
    private int cycleCount = 0;

    public void cycle() {
        Logger.debug("Tick @ " + System.currentTimeMillis());
        Map<Coordinate, Coordinate> moves = new HashMap<>();
        world.forEach((coord, cell) -> {
            Coordinate oldCoords  = coord.clone();
            cell.tick(coord);
            if (!coord.equals(oldCoords))
                moves.put(oldCoords, coord);
        });
        synchronized (world) {
            moves.forEach(world::move);
        }
        simulationWindow.getRenderer().repaint(0);
        cycleCount++;
    }


    public void start() {
        simulationThread = new Thread(() -> {
            while (true) {
                if (!state.isPaused())
                    cycle();
                try {
                    Thread.sleep(state.getSimulationDelay());
                } catch (InterruptedException ignored) {}

            }
        });
        simulationThread.setName("Simulation");
        simulationThread.start();
        Logger.info("Simulation started");
    }

    public void stop() {
        if (simulationThread != null && simulationThread.isAlive())
            simulationThread.interrupt();
    }

    public void showGui() {
        SwingUtilities.invokeLater(simulationWindow::showWindow);
    }

    public World getWorld() {
        return world;
    }

    public int getCycleCount() {
        return cycleCount;
    }

    public SimulationState getState() {
        return state;
    }
}
