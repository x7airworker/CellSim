package de.jakes_co.cellsim.engine.state;


import java.util.function.Consumer;

public interface StateChangeObserver extends Consumer<SimulationState> {

}
