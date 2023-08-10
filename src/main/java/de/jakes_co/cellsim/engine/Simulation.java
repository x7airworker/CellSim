package de.jakes_co.cellsim.engine;

import de.jakes_co.cellsim.engine.data.Coordinate;
import de.jakes_co.cellsim.engine.data.World;
import de.jakes_co.cellsim.engine.state.StateChangeObserver;
import de.jakes_co.cellsim.engine.state.SimulationState;
import de.jakes_co.cellsim.sim.cell.LivingCell;
import de.jakes_co.cellsim.util.ObserverManager;
import org.tinylog.Logger;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;


public class Simulation {

    private final ObserverManager<StateChangeObserver, SimulationState> observers = new ObserverManager<>();
    private final SimulationState state = new SimulationState();
    private final World world;
    private Thread simulationThread;

    public Simulation(World world) {
        this.world = world;
    }

    public void cycle() {
        Logger.debug("Tick @ " + System.currentTimeMillis());
        Map<Coordinate, Coordinate> moves = new HashMap<>();
        List<Coordinate> killed = new LinkedList<>();
        AtomicInteger aliveCells = new AtomicInteger();
        world.forEach((coord, cell) -> {
            if (cell instanceof LivingCell)
                aliveCells.getAndIncrement();
            Coordinate oldCoords  = coord.clone();
            boolean alive = cell.tick(coord, world);
            if (!alive) {
                killed.add(oldCoords);
            } else if (!coord.equals(oldCoords)) {
                moves.put(oldCoords, coord);
            }
        });
        state.setCellAmount(aliveCells.get());
        synchronized (world) {
            killed.forEach(world::destroy);
            moves.forEach(world::move);
        }

        state.incrementCycleCount();

        observers.change(state);
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

    public World getWorld() {
        return world;
    }

    public void observe(StateChangeObserver observer) {
        observers.observe(observer);
    }

    public void togglePaused() {
        state.togglePaused();
        observers.change(state);
    }
}
