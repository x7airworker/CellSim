package de.jakes_co.cellsim;

import de.jakes_co.cellsim.engine.Simulation;
import de.jakes_co.cellsim.engine.data.Coordinate;
import de.jakes_co.cellsim.engine.gen.EntitySpawner;
import de.jakes_co.cellsim.engine.gen.NoiseGenerator;
import de.jakes_co.cellsim.sim.cell.LivingCell;
import de.jakes_co.cellsim.sim.cell.RockCell;
import de.jakes_co.cellsim.sim.cell.WaterCell;


public class Bootstrap {
    public static void main(String[] args) {
        Simulation sim = new Simulation();

        double[][] noiseMap = NoiseGenerator.rawNoiseMap(0.1, 0, 0, 1000, 1000);
        for (int y = 0; y < 1000; y++) {
            for (int x = 0; x < 1000; x++) {
                if (noiseMap[x][y] > 0.2)
                    sim.getWorld().add(new Coordinate(x, y), RockCell.INSTANCE);
                else if (noiseMap[x][y] < -0.35)
                    sim.getWorld().add(new Coordinate(x, y), WaterCell.INSTANCE);
            }
        }

        EntitySpawner spawner = new EntitySpawner(sim.getWorld(), LivingCell::new);
        spawner.spawn(10);

        sim.showGui();
        sim.start();
    }
}
