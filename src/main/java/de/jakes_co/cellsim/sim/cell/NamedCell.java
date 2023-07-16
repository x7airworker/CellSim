package de.jakes_co.cellsim.sim.cell;

import de.jakes_co.cellsim.engine.data.AbstractCell;
import de.jakes_co.cellsim.util.NameGenerator;

public abstract class NamedCell extends AbstractCell {

    protected String name;

    {
        name = NameGenerator.INSTANCE.next();
    }

    public String getName() {
        return name;
    }
}
