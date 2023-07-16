package de.jakes_co.cellsim.engine.data;

import java.awt.*;

public abstract class AbstractCell {

    public abstract Color getColor();

    public void tick(Coordinate currentLocation) {}

}
