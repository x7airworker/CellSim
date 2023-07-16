package de.jakes_co.cellsim.engine.gen;

import de.jakes_co.cellsim.engine.data.AbstractCell;
import de.jakes_co.cellsim.engine.data.Coordinate;
import de.jakes_co.cellsim.engine.data.World;
import de.jakes_co.cellsim.sim.cell.LivingCell;

import java.util.Random;
import java.util.function.Supplier;

public class EntitySpawner {

    private final World world;
    private final Supplier<AbstractCell> constructor;
    private final Random random = new Random();

    public EntitySpawner(World world, Supplier<AbstractCell> constructor) {
        this.world = world;
        this.constructor = constructor;
    }

    public void spawn(int amount, int boundX, int boundY) {
        for (int i = 0; i < amount; i++) {
            Coordinate coordinate;

            do {
                coordinate = new Coordinate(random.nextInt(boundX), random.nextInt(boundY));
            } while (world.isObstructed(coordinate));

            world.add(coordinate, constructor.get());
        }
    }

    public void spawn(int amount) {
        spawn(amount, Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

}
