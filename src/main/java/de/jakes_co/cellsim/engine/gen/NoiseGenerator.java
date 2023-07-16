package de.jakes_co.cellsim.engine.gen;

import java.util.Random;

public class NoiseGenerator {
    public static double[][] rawNoiseMap(double scale, int xStart, int yStart, int xEnd, int yEnd) {
        double increment = 0.5; // Increment value for each step

        int width = (int) Math.ceil((xEnd - xStart) / increment) + 1;
        int height = (int) Math.ceil((yEnd - yStart) / increment) + 1;

        double[][] noiseMap = new double[width][height];

        Random random = new Random();

        // Generate random gradients
        double[][] gradients = new double[width][height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double angle = random.nextDouble() * 2 * Math.PI;
                gradients[x][y] = Math.cos(angle);
            }
        }

        // Generate noise values
        double xCurrent = xStart;
        double yCurrent = yStart;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                noiseMap[x][y] = generateNoiseValue(scale, xCurrent, yCurrent, gradients);
                xCurrent += increment;
            }
            xCurrent = xStart;
            yCurrent += increment;
        }

        return noiseMap;
    }

    private static double generateNoiseValue(double scale, double x, double y, double[][] gradients) {
        double nx = x * scale;
        double ny = y * scale;
        int x0 = (int) Math.floor(nx);
        int y0 = (int) Math.floor(ny);
        int x1 = x0 + 1;
        int y1 = y0 + 1;

        double sx = nx - x0;
        double sy = ny - y0;

        double n0 = dotGridGradient(x0, y0, nx, ny, gradients);
        double n1 = dotGridGradient(x1, y0, nx, ny, gradients);
        double ix0 = interpolate(n0, n1, sx);

        n0 = dotGridGradient(x0, y1, nx, ny, gradients);
        n1 = dotGridGradient(x1, y1, nx, ny, gradients);
        double ix1 = interpolate(n0, n1, sx);

        return interpolate(ix0, ix1, sy);
    }

    private static double dotGridGradient(int ix, int iy, double x, double y, double[][] gradients) {
        double dx = x - ix;
        double dy = y - iy;
        return dx * gradients[ix][iy] + dy * gradients[ix][iy];
    }

    private static double interpolate(double a, double b, double x) {
        double ft = x * Math.PI;
        double f = (1 - Math.cos(ft)) * 0.5;
        return a * (1 - f) + b * f;
    }

}
