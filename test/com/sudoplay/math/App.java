package com.sudoplay.math;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class App {

  private static final int WIDTH = 640;
  private static final int HEIGHT = 640;

  private JPanel imagePanel;
  private JFrame frame = new JFrame();

  public static void main(String[] args) {
    App app = new App();
    app.run();
  }

  public void run() {

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    imagePanel = new PaintPanel();
    imagePanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));

    frame.add(imagePanel);

    frame.setResizable(false);
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);

    paint();
  }

  private void paint() {
    imagePanel.repaint();
  }

  private class PaintPanel extends JPanel {

    private Vector2f clicked = new Vector2f();

    private boolean flatTop = false;
    private float size = 20f;
    private int range = 5;
    private Hexagon hex = new Hexagon(size, 0, 0, flatTop);
    private Vector2f offset = new Vector2f(64, 64);
    private float height = HexMath.layoutRHeight(size);
    private float width = HexMath.layoutRWidth(size);
    private Hex2i h = new Hex2i();
    private Vector3i c = new Vector3i();
    private Vector2f d = new Vector2f();

    private Vector2f v1 = new Vector2f();
    private Vector2f v2 = new Vector2f();

    public PaintPanel() {
      addMouseListener(new MouseListener() {
        @Override
        public void mouseReleased(MouseEvent e) {
          clicked.set(e.getX(), e.getY());
          repaint();
        }

        @Override
        public void mousePressed(MouseEvent e) {
          //
        }

        @Override
        public void mouseExited(MouseEvent e) {
          //
        }

        @Override
        public void mouseEntered(MouseEvent e) {
          //
        }

        @Override
        public void mouseClicked(MouseEvent e) {
          //
        }
      });
    }

    @Override
    protected void paintComponent(Graphics gg) {
      super.paintComponent(gg);

      Graphics2D g = (Graphics2D) gg;

      g.setFont(new Font("TimesRoman", Font.PLAIN, 9));

      Polygon p;

      for (int q = 0; q < 16; q++) {
        for (int r = 0; r < 16; r++) {

          d = HexMath.layoutOddRCenter(q, r, size, d);
          hex.setCenter(d);
          p = getPolygon(hex);
          g.setPaint(Color.BLACK);
          g.drawPolygon(p);

        }
      }

      clicked.subtractLocal(offset);
      c = HexMath.pixelToRCube(clicked.x, clicked.y, size, c);
      h = HexMath.convertCubeToOddR(c, h);

      if (h.q >= 0 && h.q <= 15 && h.r >= 0 && h.r <= 15) {

        System.out.println(HexMath.directionCube(
            HexMath.convertOddRToCube(h.set(8, 8), new Vector3i()), c));

        List<Vector3i> line = new ArrayList<Vector3i>();
        Vector3i start = HexMath.convertOddRToCube(new Hex2i(8, 8),
            new Vector3i());
        HexMath.lineCube(start, c, line);

        for (Vector3i l : line) {

          h = HexMath.convertCubeToOddR(l, h);
          d = HexMath.layoutOddRCenter(h.q, h.r, size, d);
          hex.setCenter(d);
          p = getPolygon(hex);
          g.setPaint(Color.decode("#FFCCCC"));
          g.fillPolygon(p);
          g.setPaint(Color.BLACK);
          g.drawPolygon(p);

        }

      }

      g.setPaint(Color.BLACK);
      for (int q = 0; q < 16; q++) {
        for (int r = 0; r < 16; r++) {

          d = HexMath.layoutOddRCenter(q, r, size, d);
          g.drawString(q + "." + r, d.x + offset.x - width * 0.325f, d.y
              + offset.y + height * 0.125f);

        }
      }

    }

    private Polygon getPolygon(Hexagon hex) {
      int[] x = new int[6];
      int[] y = new int[6];
      for (int i = 0; i < 6; i++) {
        v1.set(hex.getCorner(i)).addLocal(hex.getCenter()).addLocal(offset);
        x[i] = (int) v1.x;
        y[i] = (int) v1.y;
      }
      return new Polygon(x, y, 6);
    }

  }

}
