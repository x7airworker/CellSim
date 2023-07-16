package de.jakes_co.cellsim.engine.data;

import java.awt.*;

public abstract class AbstractCell {

    public abstract Color getColor();

    public boolean tick(Coordinate currentLocation, World world) {
        return true;
    }

}
