package de.jakes_co.cellsim.engine.data;

import java.util.Objects;

public class Coordinate {
    public static final Coordinate ZERO = new Coordinate(0, 0);

    private int x;
    private int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public long toLongValue() {
        return ((long) x << 32) | y;
    }

    public static Coordinate fromLong(long v) {
        return new Coordinate((int) (v >> 32), (int) v);
    }

    public int getX() {
        return x;
    }

    public Coordinate setX(int x) {
        this.x = x;
        return this;
    }

    public int getY() {
        return y;
    }

    public Coordinate setY(int y) {
        this.y = y;
        return this;
    }

    public Coordinate clone() {
        return new Coordinate(x, y);
    }

    public Coordinate[] getDirectNeighbours() {
        return new Coordinate[] {
                new Coordinate(x, y + 1),
                new Coordinate(x + 1, y),
                new Coordinate(x, y - 1),
                new Coordinate(x - 1, y)
        };
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return x == that.x && y == that.y;
    }

    public int hashCode() {
        return Objects.hash(x, y);
    }

    public String toString() {
        return "[" + x + "," + y + "]";
    }
}
