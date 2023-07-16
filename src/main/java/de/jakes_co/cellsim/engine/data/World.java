package de.jakes_co.cellsim.engine.data;

import org.tinylog.Logger;

import java.util.TreeMap;
import java.util.function.BiConsumer;

public class World {
    private final TreeMap<Long, AbstractCell> cells = new TreeMap<>();

    public boolean isObstructed(Coordinate coordinate) {
        return cells.containsKey(coordinate.toLongValue());
    }

    public void add(Coordinate coordinate, AbstractCell cell) {
        cells.put(coordinate.toLongValue(), cell);
        Logger.debug("Cell created at " + coordinate);
    }

    public void move(Coordinate oldCoordinate, Coordinate newCoordinate) {
        AbstractCell cell = cells.get(oldCoordinate.toLongValue());
        if (cell == null)
            return;

        AbstractCell otherCell = cells.get(newCoordinate.toLongValue());
        if (otherCell != null)
            return;

        cells.remove(oldCoordinate.toLongValue());
        cells.put(newCoordinate.toLongValue(), cell);
        Logger.debug("Moved cell " + oldCoordinate + " to " + newCoordinate);
    }

    public AbstractCell[] resolveCoordinates(Coordinate[] coords) {
        AbstractCell[] resolvedCells = new AbstractCell[coords.length];

        for (int i = 0; i < coords.length; i++) {
            resolvedCells[i] = cells.get(coords[i].toLongValue());
        }

        return resolvedCells;
    }

    public AbstractCell destroy(Coordinate coordinate) {
        Logger.info("Destroyed cell at " + coordinate);
        return cells.remove(coordinate.toLongValue());
    }

    public void forEach(BiConsumer<Coordinate, AbstractCell> consumer) {
        cells.forEach((l, cell) -> consumer.accept(Coordinate.fromLong(l), cell));
    }

    public int size() {
        return cells.size();
    }
}
