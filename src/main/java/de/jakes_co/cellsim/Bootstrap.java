package de.jakes_co.cellsim;

import de.jakes_co.cellsim.engine.Simulation;
import de.jakes_co.cellsim.engine.data.Coordinate;
import de.jakes_co.cellsim.engine.data.World;
import de.jakes_co.cellsim.engine.gen.EntitySpawner;
import de.jakes_co.cellsim.engine.gen.NoiseGenerator;
import de.jakes_co.cellsim.engine.state.LoggingStateObserver;
import de.jakes_co.cellsim.engine.ui.HotkeyListener;
import de.jakes_co.cellsim.engine.ui.SimulationWindow;
import de.jakes_co.cellsim.sim.cell.LivingCell;
import de.jakes_co.cellsim.sim.cell.RockCell;
import de.jakes_co.cellsim.sim.cell.WaterCell;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;


public class Bootstrap {
    public static void main(String[] args) {
        List<String> listArgs = Arrays.asList(args);

        World world = new World();
        Simulation sim = new Simulation(world);
        sim.observe(new LoggingStateObserver());

        double[][] noiseMap = NoiseGenerator.rawNoiseMap(0.1, 0, 0, 1000, 1000);
        for (int y = 0; y < 1000; y++) {
            for (int x = 0; x < 1000; x++) {
                if (noiseMap[x][y] > 0.2)
                    world.add(new Coordinate(x, y), RockCell.INSTANCE);
                else if (noiseMap[x][y] < -0.35)
                    world.add(new Coordinate(x, y), WaterCell.INSTANCE);
            }
        }

        EntitySpawner spawner = new EntitySpawner(sim.getWorld(), LivingCell::new);
        spawner.spawn(100, 1000, 1000);

        if (!listArgs.contains("--no-gui")) {
            SimulationWindow simulationWindow = new SimulationWindow(world);
            simulationWindow.addKeyListener(new HotkeyListener(sim));
            sim.observe(simulationWindow);
            SwingUtilities.invokeLater(simulationWindow::showWindow);
        }

        sim.start();
    }
}
