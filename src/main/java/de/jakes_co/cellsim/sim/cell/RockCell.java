package de.jakes_co.cellsim.sim.cell;

import de.jakes_co.cellsim.engine.data.AbstractCell;

import java.awt.*;

public class RockCell extends AbstractCell {
    public static final RockCell INSTANCE = new RockCell();

    private RockCell() {}

    @Override
    public Color getColor() {
        return Color.GRAY;
    }
}
