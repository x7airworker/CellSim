package de.jakes_co.cellsim.engine.state;

import org.tinylog.Logger;

public class LoggingStateObserver implements StateChangeObserver {
    public void accept(SimulationState state) {
        Logger.info(state);
    }
}
