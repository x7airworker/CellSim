package de.jakes_co.cellsim.sim.cell;

import de.jakes_co.cellsim.engine.data.Coordinate;

import java.awt.*;

public class LivingCell extends NamedCell {
    public Color getColor() {
        return Color.RED;
    }

    public void tick(Coordinate currentLocation) {
        currentLocation.setX(currentLocation.getX() + 1);
    }
}
