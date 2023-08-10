package de.jakes_co.cellsim.engine.state;

import java.util.Objects;

public class SimulationState {
    private long simulationDelay = 1000L;
    private boolean paused = false;
    private int cellAmount;
    private int cycleCount;

    public long getSimulationDelay() {
        return simulationDelay;
    }

    public SimulationState setSimulationDelay(long simulationDelay) {
        this.simulationDelay = simulationDelay;
        return this;
    }

    public boolean isPaused() {
        return paused;
    }

    public SimulationState setPaused(boolean paused) {
        this.paused = paused;
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
        return this;
    }

    public void incrementCycleCount() {
        cycleCount++;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimulationState that = (SimulationState) o;
        return simulationDelay == that.simulationDelay && paused == that.paused && cellAmount == that.cellAmount && cycleCount == that.cycleCount;
    }

    public int hashCode() {
        return Objects.hash(simulationDelay, paused, cellAmount, cycleCount);
    }

    public String toString() {
        return "SimulationState{" +
                "simulationDelay=" + simulationDelay +
                ", paused=" + paused +
                ", cellAmount=" + cellAmount +
                ", cycleCount=" + cycleCount +
                '}';
    }
}
