package de.jakes_co.cellsim.engine;

import org.tinylog.Logger;

import java.util.ArrayList;
import java.util.List;

public class SimulationState {

    private final List<Runnable> callbacks = new ArrayList<>();
    private long simulationDelay = 1000L;
    private boolean paused = false;
    private int cellAmount;

    public long getSimulationDelay() {
        return simulationDelay;
    }

    public SimulationState setSimulationDelay(long simulationDelay) {
        this.simulationDelay = simulationDelay;
        Logger.info("Simulation Delay changed to " + simulationDelay);
        fireCallbacks();
        return this;
    }

    public boolean isPaused() {
        return paused;
    }

    public SimulationState setPaused(boolean paused) {
        this.paused = paused;
        Logger.info(paused ? "Paused" : "Unpaused");
        fireCallbacks();
        return this;
    }

    public void togglePaused() {
        setPaused(!paused); // intentionally call setter to also have logging output
    }

    public int getCellAmount() {
        return cellAmount;
    }

    public SimulationState setCellAmount(int cellAmount) {
        this.cellAmount = cellAmount;
        fireCallbacks();
        return this;
    }

    public void subscribe(Runnable callback) {
        callbacks.add(callback);
    }

    private void fireCallbacks() {
        callbacks.forEach(Runnable::run);
    }

}
