package de.jakes_co.cellsim.sim.cell;

import de.jakes_co.cellsim.engine.data.AbstractCell;

import java.awt.*;

public class WaterCell extends AbstractCell {
    public static final WaterCell INSTANCE = new WaterCell();

    private WaterCell() {}

    public Color getColor() {
        return Color.BLUE;
    }
}
