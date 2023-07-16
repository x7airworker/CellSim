package de.jakes_co.cellsim.engine.ui;

import de.jakes_co.cellsim.engine.data.World;
import de.jakes_co.cellsim.sim.cell.NamedCell;
import org.tinylog.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SimulationRenderer extends JPanel {
    private static final int SCALE_NAME_THRESHOLD = 700;
    private static final int INITIAL_SCALE = 100; // Initial scale percentage
    private static final int SCALE_FACTOR = 10; // Scale factor for each mouse wheel rotation
    private int scale = INITIAL_SCALE;
    private final World world;
    private double camX = 0;
    private double camY = 0;

    public SimulationRenderer(World world) {
        super();
        this.world = world;
        setFocusable(true);
        requestFocusInWindow();
        addMouseWheelListener(new ScaleListener());
        MoveListener moveListener = new MoveListener();
        addMouseListener(moveListener);
        addMouseMotionListener(moveListener);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D g = (Graphics2D) graphics.create();
        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

        Font font = g.getFont();
        g.setFont(font.deriveFont(6f));

        double scaleFactor = scale / 100.0;
        if (scaleFactor % 2 != 0)
            scaleFactor--;
        g.scale(scaleFactor, scaleFactor);
        g.translate(-camX, -camY);

        //Rectangle rect = g.getClipBounds();


        synchronized (world) {
            world.forEach((coord, cell) -> {
                g.setColor(cell.getColor());
                g.drawLine(coord.getX(), coord.getY(), coord.getX(), coord.getY());

                if (scale >= SCALE_NAME_THRESHOLD && cell instanceof NamedCell) {
                    String name = ((NamedCell) cell).getName();
                    float width = g.getFontMetrics().stringWidth(name);
                    g.drawString(name, coord.getX() - (width / 2), coord.getY() - 1);
                }
            });
        }

        g.dispose();
    }

    private class ScaleListener implements MouseWheelListener {
        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            int rotation = e.getWheelRotation();

            // Adjust the scale based on mouse wheel rotation
            scale += SCALE_FACTOR * rotation;

            // Limit the scale within a specified range
            scale = Math.max(10, Math.min(1000, scale));
            Logger.debug("Scale changed " + scale);

            // Repaint the panel to reflect the new scale
            repaint();
        }
    }

    private class MoveListener implements MouseListener, MouseMotionListener {
        private int startX;
        private int startY;
        private int previousX;
        private int previousY;

        public void mouseClicked(MouseEvent e) {

        }

        public void mousePressed(MouseEvent e) {
            startX = e.getX();
            startY = e.getY();
        }

        public void mouseReleased(MouseEvent e) {
        }

        public void mouseEntered(MouseEvent e) {

        }

        public void mouseExited(MouseEvent e) {

        }

        public void mouseDragged(MouseEvent e) {
            int currentX = e.getX();
            int currentY = e.getY();

            int deltaX = currentX - previousX;
            int deltaY = currentY - previousY;

            // Update previous position
            previousX = currentX;
            previousY = currentY;

            camX += deltaX;
            camY += deltaY;
            repaint();
            Logger.debug("Dragged to " + e.getX() + " " + e.getY());
        }

        public void mouseMoved(MouseEvent e) {

        }
    }

}
