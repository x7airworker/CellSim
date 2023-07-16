package de.jakes_co.cellsim.sim.cell;

import de.jakes_co.cellsim.engine.data.AbstractCell;
import de.jakes_co.cellsim.engine.data.Coordinate;
import de.jakes_co.cellsim.engine.data.Direction;
import de.jakes_co.cellsim.engine.data.World;
import org.tinylog.Logger;

import java.awt.*;
import java.util.Random;

public class LivingCell extends NamedCell {
    private static final Random RANDOM = new Random();
    private State state = State.IDLE;
    private Direction direction = Direction.EAST;
    private int thirst = 0;
    private boolean male = RANDOM.nextBoolean();

    public Color getColor() {
        return male ? Color.GREEN : Color.PINK;
    }

    public boolean tick(Coordinate currentLocation, World world) {
        if (thirst > 25)
            return false;

        switch (state) {
            case IDLE:
                if (thirst > 10)
                    state = State.THIRSTY;
                break;
            case THIRSTY:
                if (search(world, currentLocation, WaterCell.class)) {
                    thirst = 0;
                    state = State.IDLE;
                    Logger.info(this + " drank water");
                }
                break;
        }

        thirst++;

        return true;
    }

    private boolean search(World world, Coordinate coordinate, Class<? extends AbstractCell> cellType) {
        Coordinate[] neighbourCoordinates = coordinate.getDirectNeighbours();
        AbstractCell[] neighbours = world.resolveCoordinates(neighbourCoordinates);
        for (int i = 0; i < neighbourCoordinates.length; i++) {
            if (neighbours[i] != null && neighbours[i].getClass().equals(cellType))
                return true;
        }

        // Turn to the right in case the position is obstructed
        if (direction == Direction.NORTH && neighbours[0] != null)
            direction = Direction.EAST;
        if (direction == Direction.EAST  && neighbours[1] != null)
            direction = Direction.SOUTH;
        if (direction == Direction.SOUTH && neighbours[2] != null)
            direction = Direction.WEST;
        if (direction == Direction.WEST  && neighbours[3] != null)
            direction = Direction.NORTH;

        switch (direction) {
            case NORTH:
                coordinate.setY(coordinate.getY() + 1);
                break;
            case EAST:
                coordinate.setX(coordinate.getX() + 1);
                break;
            case SOUTH:
                coordinate.setY(coordinate.getY() - 1);
                break;
            case WEST:
                coordinate.setX(coordinate.getX() - 1);
                break;
        }

        return false;
    }

    public enum State {
        IDLE,
        THIRSTY,
    }
}
