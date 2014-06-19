/*
 * Copyright (C) 2014 Jason Taylor.
 * Released as open-source under the Apache License, Version 2.0.
 * 
 * =\/==========================================================================
 * 
 * Copyright (C) 2014 Jason Taylor
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package com.sudoplay.math;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for hexagonal math.
 * <p>
 * This class wouldn't be possible without this awesome page:<br>
 * http://www.redblobgames.com/grids/hexagons/
 * 
 * @author Jason Taylor
 */
public class HexMath {

  private static final float ONE_THIRD = 1f / 3f;
  private static final float TWO_THIRDS = 2f / 3f;
  private static final float ONE_THIRD_SQRT3 = ONE_THIRD * FastMath.SQRT3;

  private static final Vector3i[] CUBE_NEIGHBORS = new Vector3i[] { new Vector3i(1, -1, 0), new Vector3i(1, 0, -1), new Vector3i(0, 1, -1),
      new Vector3i(-1, 1, 0), new Vector3i(-1, 0, 1), new Vector3i(0, -1, 1) };

  private static final Vector3i[] CUBE_DIAGONAL_NEIGHBORS = new Vector3i[] { new Vector3i(2, -1, -1), new Vector3i(1, 1, -2), new Vector3i(-1, 2, -1),
      new Vector3i(-2, 1, 1), new Vector3i(-1, -1, 2), new Vector3i(1, -2, 1) };

  private static final Hex2i[] AXIAL_NEIGHBORS = new Hex2i[] { new Hex2i(1, 0), new Hex2i(1, -1), new Hex2i(0, -1), new Hex2i(-1, 0), new Hex2i(-1, 1),
      new Hex2i(0, 1) };

  private static final Hex2i[][] ODD_R_NEIGHBORS = new Hex2i[][] {
      new Hex2i[] { new Hex2i(1, 0), new Hex2i(0, -1), new Hex2i(-1, -1), new Hex2i(-1, 0), new Hex2i(-1, 1), new Hex2i(0, 1) },
      new Hex2i[] { new Hex2i(1, 0), new Hex2i(1, -1), new Hex2i(0, -1), new Hex2i(-1, 0), new Hex2i(0, 1), new Hex2i(1, 1) } };

  private static final Hex2i[][] EVEN_R_NEIGHBORS = new Hex2i[][] {
      new Hex2i[] { new Hex2i(1, 0), new Hex2i(1, -1), new Hex2i(0, -1), new Hex2i(-1, 0), new Hex2i(0, 1), new Hex2i(1, 1) },
      new Hex2i[] { new Hex2i(1, 0), new Hex2i(0, -1), new Hex2i(-1, -1), new Hex2i(-1, 0), new Hex2i(-1, 1), new Hex2i(0, 1) } };

  private static final Hex2i[][] ODD_Q_NEIGHBORS = new Hex2i[][] {
      new Hex2i[] { new Hex2i(1, 0), new Hex2i(1, -1), new Hex2i(0, -1), new Hex2i(-1, 1), new Hex2i(-1, 0), new Hex2i(0, 1) },
      new Hex2i[] { new Hex2i(1, 1), new Hex2i(1, 0), new Hex2i(0, -1), new Hex2i(-1, 0), new Hex2i(-1, 1), new Hex2i(0, 1) } };

  private static final Hex2i[][] EVEN_Q_NEIGHBORS = new Hex2i[][] {
      new Hex2i[] { new Hex2i(1, 1), new Hex2i(1, 0), new Hex2i(0, -1), new Hex2i(-1, 0), new Hex2i(-1, 1), new Hex2i(0, 1) },
      new Hex2i[] { new Hex2i(1, 0), new Hex2i(1, -1), new Hex2i(0, -1), new Hex2i(-1, -1), new Hex2i(-1, 0), new Hex2i(0, 1) } };

  private HexMath() {
    //
  }

  /**
   * Gets the nth corner of a pointy-top hexagon with center and size. The
   * resulting coordinates are stored in store.
   * 
   * @param center
   *          the center of the hexagon
   * @param size
   *          distance from center to corner
   * @param index
   *          the index of the corner (0-5)
   * @param store
   *          the result
   * @return
   */
  public static Vector2f getCornerFlatSide(float size, int index, Vector2f store) {
    if (index < 0 || index > 5) {
      throw new IndexOutOfBoundsException();
    }
    if (store == null) {
      store = new Vector2f();
    }
    return FastMath.polarToCartesian(size, FastMath.TWO_PI / 6 * (index + 0.5f), store);
  }

  /**
   * Gets the nth corner of a flat-top hexagon with center and size. The
   * resulting coordinates are stored in store.
   * 
   * @param center
   *          the center of the hexagon
   * @param size
   *          distance from center to corner
   * @param index
   *          the index of the corner (0-5)
   * @param store
   *          the result
   * @return
   */
  public static Vector2f getCornerFlatTop(float size, int index, Vector2f store) {
    if (index < 0 || index > 5) {
      throw new IndexOutOfBoundsException();
    }
    if (store == null) {
      store = new Vector2f();
    }
    return FastMath.polarToCartesian(size, FastMath.TWO_PI / 6 * index, store);
  }

  /**
   * Convert cube coordinates to axial.
   * 
   * @param cube
   * @param store
   * @return
   */
  public static Hex2i convertCubeToAxial(Vector3i cube, Hex2i store) {
    store.q = cube.x;
    store.r = cube.z;
    return store;
  }

  /**
   * Convert axial coordinates to cube.
   * 
   * @param hex
   * @param store
   * @return
   */
  public static Vector3i convertAxialToCube(Hex2i hex, Vector3i store) {
    store.x = hex.q;
    store.y = -hex.q - hex.r;
    store.z = hex.r;
    return store;
  }

  /**
   * Convert cube coordinates to even-q.
   * 
   * @param cube
   * @param store
   * @return
   */
  public static Hex2i convertCubeToEvenQ(Vector3i cube, Hex2i store) {
    store.q = cube.x;
    store.r = cube.z + (cube.x + (cube.x & 1)) / 2;
    return store;
  }

  /**
   * Convert even-q coordinates to cube.
   * 
   * @param hex
   * @param store
   * @return
   */
  public static Vector3i convertEvenQToCube(Hex2i hex, Vector3i store) {
    store.x = hex.q;
    store.z = hex.r - (hex.q + (hex.q & 1)) / 2;
    store.y = -store.x - store.z;
    return store;
  }

  /**
   * Convert cube coordinates to odd-q.
   * 
   * @param cube
   * @param store
   * @return
   */
  public static Hex2i convertCubeToOddQ(Vector3i cube, Hex2i store) {
    store.q = cube.x;
    store.r = cube.z + (cube.x - (cube.x & 1)) / 2;
    return store;
  }

  /**
   * Convert odd-q coordinates to cube.
   * 
   * @param hex
   * @param store
   * @return
   */
  public static Vector3i convertOddQToCube(Hex2i hex, Vector3i store) {
    store.x = hex.q;
    store.z = hex.r - (hex.q - (hex.q & 1)) / 2;
    store.y = -store.x - store.z;
    return store;
  }

  /**
   * Convert cube coordinates to even-r.
   * 
   * @param cube
   * @param store
   * @return
   */
  public static Hex2i convertCubeToEvenR(Vector3i cube, Hex2i store) {
    store.q = cube.x + (cube.z + (cube.z & 1)) / 2;
    store.r = cube.z;
    return store;
  }

  /**
   * Convert even-r coordinates to cube.
   * 
   * @param hex
   * @param store
   * @return
   */
  public static Vector3i convertEvenRToCube(Hex2i hex, Vector3i store) {
    store.x = hex.q - (hex.r + (hex.r & 1)) / 2;
    store.z = hex.r;
    store.y = -store.x - store.z;
    return store;
  }

  /**
   * Convert cube coordinates to odd-r.
   * 
   * @param cube
   * @param store
   * @return
   */
  public static Hex2i convertCubeToOddR(Vector3i cube, Hex2i store) {
    store.q = cube.x + (cube.z - (cube.z & 1)) / 2;
    store.r = cube.z;
    return store;
  }

  /**
   * Convert odd-r coordinates to cube.
   * 
   * @param hex
   * @param store
   * @return
   */
  public static Vector3i convertOddRToCube(Hex2i hex, Vector3i store) {
    store.x = hex.q - (hex.r - (hex.r & 1)) / 2;
    store.z = hex.r;
    store.y = -store.x - store.z;
    return store;
  }

  /**
   * Moves the cube coordinates to the neighboring coordinates indicated by
   * direction. Direction is an integer ranging from 0 to 5 with 0 being
   * directly right of the hex if the sides are flat or to the right and down if
   * the tops are flat. The direction moves counter-clockwise around the hex.
   * 
   * @param cube
   *          the original cube coordinates
   * @param direction
   *          an integer from 0 to 5 indicating direction
   * @param store
   *          result
   * @return
   */
  public static Vector3i neighborCube(Vector3i cube, int direction, Vector3i store) {
    return store.set(cube).addLocal(CUBE_NEIGHBORS[direction]);
  }

  public static Vector3i neighborCubeDiagonal(Vector3i cube, int direction, Vector3i store) {
    return store.set(cube).addLocal(CUBE_DIAGONAL_NEIGHBORS[direction]);
  }

  /**
   * Moves the axial coordinates to the neighboring coordinates indicated by
   * direction. Direction is an integer ranging from 0 to 5 with 0 being
   * directly right of the hex if the sides are flat or to the right and down if
   * the tops are flat. The direction moves counter-clockwise around the hex.
   * 
   * @param hex
   *          the original axial coordinates
   * @param direction
   *          an integer from 0 to 5 indicating direction
   * @param store
   *          result
   * @return
   */
  public static Hex2i neighborAxial(Hex2i hex, int direction, Hex2i store) {
    return store.set(hex).addLocal(AXIAL_NEIGHBORS[direction]);
  }

  public static Hex2i neighborOddR(Hex2i hex, int direction, Hex2i store) {
    return store.set(hex).addLocal(ODD_R_NEIGHBORS[hex.r & 1][direction]);
  }

  public static Hex2i neighborEvenR(Hex2i hex, int direction, Hex2i store) {
    return store.set(hex).addLocal(EVEN_R_NEIGHBORS[hex.r & 1][direction]);
  }

  public static Hex2i neighborOddQ(Hex2i hex, int direction, Hex2i store) {
    return store.set(hex).addLocal(ODD_Q_NEIGHBORS[hex.q & 1][direction]);
  }

  public static Hex2i neighborEvenQ(Hex2i hex, int direction, Hex2i store) {
    return store.set(hex).addLocal(EVEN_Q_NEIGHBORS[hex.q & 1][direction]);
  }

  /**
   * Returns the Manhattan distance between two hexagons using the cube
   * coordinate system.
   * 
   * @param cube1
   * @param cube2
   * @return
   */
  public static int distanceCube(Vector3i cube1, Vector3i cube2) {
    return (FastMath.abs(cube1.x - cube2.x) + FastMath.abs(cube1.y - cube2.y) + FastMath.abs(cube1.z - cube2.z)) / 2;
  }

  /**
   * Returns the Manhattan distance between two hexagons using the axial
   * coordinate system.
   * 
   * @param axial1
   * @param axial2
   * @return
   */
  public static int distanceAxial(Hex2i hex1, Hex2i hex2) {
    return (FastMath.abs(hex1.q - hex2.q) + FastMath.abs(hex1.r - hex2.r) + FastMath.abs(hex1.q + hex1.r - hex2.q - hex2.r)) / 2;
  }

  /**
   * Returns the Manhattan distance between two hexagons using the Odd-R
   * coordinate system.
   * 
   * @param hex1
   * @param hex2
   * @return
   */
  public static int distanceOddR(Hex2i hex1, Hex2i hex2) {
    int x1 = hex1.q - (hex1.r - (hex1.r & 1)) / 2;
    int x2 = hex2.q - (hex2.r - (hex2.r & 1)) / 2;
    return (FastMath.abs(x1 - x2) + FastMath.abs(-x1 - hex1.r + x2 + hex2.r) + FastMath.abs(hex1.r - hex2.r)) / 2;
  }

  /**
   * Returns the Manhattan distance between two hexagons using the Even-R
   * coordinate system.
   * 
   * @param hex1
   * @param hex2
   * @return
   */
  public static int distanceEvenR(Hex2i hex1, Hex2i hex2) {
    int x1 = hex1.q - (hex1.r + (hex1.r & 1)) / 2;
    int x2 = hex2.q - (hex2.r + (hex2.r & 1)) / 2;
    return (FastMath.abs(x1 - x2) + FastMath.abs(-x1 - hex1.r + x2 + hex2.r) + FastMath.abs(hex1.r - hex2.r)) / 2;
  }

  /**
   * Returns the Manhattan distance between two hexagons using the Odd-Q
   * coordinate system.
   * 
   * @param hex1
   * @param hex2
   * @return
   */
  public static int distanceOddQ(Hex2i hex1, Hex2i hex2) {
    int z1 = hex1.r - (hex1.q - (hex1.q & 1)) / 2;
    int z2 = hex2.r - (hex2.q - (hex2.q & 1)) / 2;
    return (FastMath.abs(hex1.q - hex2.q) + FastMath.abs(-hex1.q - z1 + hex2.q + z2) + FastMath.abs(z1 - z2)) / 2;
  }

  /**
   * Returns the Manhattan distance between two hexagons using the Even-Q
   * coordinate system.
   * 
   * @param hex1
   * @param hex2
   * @return
   */
  public static int distanceEvenQ(Hex2i hex1, Hex2i hex2) {
    int z1 = hex1.r - (hex1.q + (hex1.q & 1)) / 2;
    int z2 = hex2.r - (hex2.q + (hex2.q & 1)) / 2;
    return (FastMath.abs(hex1.q - hex2.q) + FastMath.abs(-hex1.q - z1 + hex2.q + z2) + FastMath.abs(z1 - z2)) / 2;
  }

  /**
   * Return the height for a hex in orientation R (pointy-top).
   * 
   * @param size
   * @return
   */
  public static float layoutRHeight(float size) {
    return size * 2;
  }

  /**
   * Return the height for a hex in orientation Q (flat-top).
   * 
   * @param size
   * @return
   */
  public static float layoutQHeight(float size) {
    return FastMath.SQRT3 * size;
  }

  /**
   * Return the width for a hex in orientation R (pointy-top).
   * 
   * @param size
   * @return
   */
  public static float layoutRWidth(float size) {
    return FastMath.SQRT3 * size;
  }

  /**
   * Return the width for a hex in orientation Q (flat-top).
   * 
   * @param size
   * @return
   */
  public static float layoutQWidth(float size) {
    return size * 2;
  }

  /**
   * Returns the vertical distance between adjacent hexes in orientation R
   * (pointy-top).
   * 
   * @param size
   * @return
   */
  public static float layoutRVerticalDistance(float size) {
    return 1.5f * size;
  }

  /**
   * Returns the vertical distance between adjacent hexes in orientation Q
   * (flat-top).
   * 
   * @param size
   * @return
   */
  public static float layoutQVerticalDistance(float size) {
    return FastMath.SQRT3 * size;
  }

  /**
   * Returns the horizontal distance between adjacent hexes in orientation R
   * (pointy-top).
   * 
   * @param size
   * @return
   */
  public static float layoutRHorizontalDistance(float size) {
    return FastMath.SQRT3 * size;
  }

  /**
   * Returns the horizontal distance between adjacent hexes in orientation Q
   * (flat-top).
   * 
   * @param size
   * @return
   */
  public static float layoutQHorizontalDistance(float size) {
    return 1.5f * size;
  }

  /**
   * Given Odd-R coordinates, q and r, returns the center of the hex in
   * {@code store}.
   * 
   * @param q
   * @param r
   * @param size
   * @param store
   * @return
   */
  public static Vector2f layoutOddRCenter(int q, int r, float size, Vector2f store) {
    store.x = FastMath.SQRT3 * size * (q + 0.5f * (r & 1));
    store.y = 1.5f * size * r;
    return store;
  }

  /**
   * Given Even-R coordinates, q and r, returns the center of the hex in
   * {@code store}.
   * 
   * @param q
   * @param r
   * @param size
   * @param store
   * @return
   */
  public static Vector2f layoutEvenRCenter(int q, int r, float size, Vector2f store) {
    store.x = FastMath.SQRT3 * size * (q - 0.5f * (r & 1));
    store.y = 1.5f * size * r;
    return store;
  }

  /**
   * Given Odd-Q coordinates, q and r, returns the center of the hex in
   * {@code store}.
   * 
   * @param q
   * @param r
   * @param size
   * @param store
   * @return
   */
  public static Vector2f layoutOddQCenter(int q, int r, float size, Vector2f store) {
    store.x = 1.5f * size * q;
    store.y = FastMath.SQRT3 * size * (r + 0.5f * (q & 1));
    return store;
  }

  /**
   * Given Even-Q coordinates, q and r, returns the center of the hex in
   * {@code store}.
   * 
   * @param q
   * @param r
   * @param size
   * @param store
   * @return
   */
  public static Vector2f layoutEvenQCenter(int q, int r, float size, Vector2f store) {
    store.x = 1.5f * size * q;
    store.y = FastMath.SQRT3 * size * (r - 0.5f * (q & 1));
    return store;
  }

  /**
   * Returns a list of hex cube coordinates that fall within {@code range} from
   * center {@code cube}; a new list is created to store the results.
   * 
   * @param range
   * @return
   */
  public static List<Vector3i> rangeCube(Vector3i cube, int range) {
    return rangeCube(cube, range, new ArrayList<Vector3i>());
  }

  /**
   * Returns a list of hex cube coordinates that fall within {@code range} from
   * center {@code cube}; stores the results in {@code store}.
   * 
   * @param cube
   * @param range
   * @param store
   * @return
   */
  public static List<Vector3i> rangeCube(Vector3i cube, int range, List<Vector3i> store) {
    for (int x = -range; x <= range; x++) {
      for (int y = Math.max(-range, -x - range); y <= Math.min(range, -x + range); y++) {
        int z = -x - y;
        store.add(new Vector3i(x, y, z).addLocal(cube));
      }
    }
    return store;
  }

  /**
   * Returns a list of hex cube coordinates that fall within range {@code r1} of
   * center {@code c1} and range {@code r2} of center {@code c2}; creates and
   * returns a new list to store the results.
   * 
   * @param c1
   * @param c2
   * @param r1
   * @param r2
   * @return
   */
  public static List<Vector3i> intersectCube(Vector3i c1, Vector3i c2, int r1, int r2) {
    return intersectCube(c1, c2, r1, r2, new ArrayList<Vector3i>());
  }

  /**
   * Returns a list of hex cube coordinates that fall within range {@code r1} of
   * center {@code c1} and range {@code r2} of center {@code c2}; stores the
   * results in {@code store}.
   * 
   * @param c1
   * @param c2
   * @param r1
   * @param r2
   * @param store
   * @return
   */
  public static List<Vector3i> intersectCube(Vector3i c1, Vector3i c2, int r1, int r2, List<Vector3i> store) {

    int xmin = Math.max(c1.x - r1, c2.x - r2);
    int xmax = Math.min(c1.x + r1, c2.x + r2);
    int ymin = Math.max(c1.y - r1, c2.y - r2);
    int ymax = Math.min(c1.y + r1, c2.y + r2);
    int zmin = Math.max(c1.z - r1, c2.z - r2);
    int zmax = Math.min(c1.z + r1, c2.z + r2);

    for (int x = xmin; x <= xmax; x++) {
      for (int y = Math.max(ymin, -x - zmax); y <= Math.min(ymax, -x - zmin); y++) {
        int z = -x - y;
        store.add(new Vector3i(x, y, z));
      }
    }
    return store;
  }

  /**
   * Creates a list of hex cube coordinates in a ring with radius {@code range}
   * and center {@code c}; creates and returns a new list.
   * 
   * @param c
   * @param range
   * @return
   */
  public static List<Vector3i> ringCube(Vector3i c, int range) {
    return ringCube(c, range, new ArrayList<Vector3i>());
  }

  /**
   * Creates a list of hex cube coordinates in a ring with radius {@code range}
   * and center {@code c}; stores the result in {@code store}.
   * 
   * @param c
   * @param range
   * @param store
   * @return
   */
  public static List<Vector3i> ringCube(Vector3i c, int range, List<Vector3i> store) {
    Vector3i v = new Vector3i(CUBE_NEIGHBORS[4]).multLocal(range).addLocal(c);
    for (int i = 0; i < 6; i++) {
      for (int j = 0; j < range; j++) {
        store.add(new Vector3i(v));
        v.addLocal(CUBE_NEIGHBORS[i]);
      }
    }
    return store;
  }

  /**
   * Rotates a hexVector (difference between two hex cube coordinates) left and
   * stores the result in {@code store}.
   * 
   * @param hexVector
   * @param store
   * @return
   */
  public static Vector3i rotateLeft(Vector3i hexVector, Vector3i store) {
    store.x = -hexVector.y;
    store.y = -hexVector.z;
    store.z = -hexVector.x;
    return store;
  }

  /**
   * Rotates a hexVector (difference between two hex cube coordinates) right and
   * stores the result in {@code store}.
   * 
   * @param hexVector
   * @param store
   * @return
   */
  public static Vector3i rotateRight(Vector3i hexVector, Vector3i store) {
    store.x = -hexVector.z;
    store.y = -hexVector.x;
    store.z = -hexVector.y;
    return store;
  }

  /**
   * Rounds floating point cube coordinates to the nearest integer hex cube
   * coordinates; creates a new {@link Vector3i}.
   * 
   * @param v
   * @return
   */
  public static Vector3i roundCube(Vector3f v) {
    return roundCube(v.x, v.y, v.z, new Vector3i());
  }

  /**
   * Rounds floating point cube coordinates to the nearest integer hex cube
   * coordinates.
   * 
   * @param v
   * @param store
   * @return
   */
  public static Vector3i roundCube(Vector3f v, Vector3i store) {
    return roundCube(v.x, v.y, v.z, store);
  }

  /**
   * Rounds floating point cube coordinates to the nearest integer hex cube
   * coordinates.
   * 
   * @param x
   * @param y
   * @param z
   * @param store
   * @return
   */
  public static Vector3i roundCube(float x, float y, float z, Vector3i store) {
    int rx = Math.round(x);
    int ry = Math.round(y);
    int rz = Math.round(z);

    if (rx + ry + rx != 0) {

      float dx = Math.abs(rx - x);
      float dy = Math.abs(ry - y);
      float dz = Math.abs(rz - z);

      if (dx > dy && dx > dz) {
        rx = -ry - rz;
      } else if (dy > dz) {
        ry = -rx - rz;
      } else {
        rz = -rx - ry;
      }

    }
    return store.set(rx, ry, rz);
  }

  /**
   * Converts floating point pixel coordinates to integer hex cube coordinates
   * for a hex grid in the R orientation (pointy-top hexes) and stores the
   * result in {@code store}.
   * 
   * @param x
   * @param y
   * @param size
   * @param store
   * @return
   */
  public static Vector3i pixelToRCube(float x, float y, float size, Vector3i store) {
    float q = (ONE_THIRD_SQRT3 * x - ONE_THIRD * y) / size;
    float r = TWO_THIRDS * y / size;
    return roundCube(q, -q - r, r, store);
  }

  /**
   * Converts floating point pixel coordinates to integer hex cube coordinates
   * for a hex grid in the R orientation (flat-top hexes) and stores the result
   * in {@code store}.
   * 
   * @param x
   * @param y
   * @param size
   * @param store
   * @return
   */
  public static Vector3i pixelToQCube(float x, float y, float size, Vector3i store) {
    float q = TWO_THIRDS * x / size;
    float r = (ONE_THIRD_SQRT3 * y - ONE_THIRD * x) / size;
    return roundCube(q, -q - r, r, store);
  }

  /**
   * Returns the integer direction of the first neighboring hex in the line from
   * {@code cube1} to {@code cube2}. Direction is an integer ranging from 0 to 5
   * with 0 being directly right of the hex if the sides are flat or to the
   * right and down if the tops are flat. The direction moves counter-clockwise
   * around the hex.
   * <p>
   * If {@code cube1} equals {@code cube2}, -1 is returned.
   * 
   * @param cube1
   * @param cube2
   * @return
   */
  public static int directionCube(Vector3i cube1, Vector3i cube2) {

    int dir = -1;

    if (cube1.equals(cube2)) {
      return dir;
    }

    int rx, ry, rz;

    rx = cube2.x - cube1.x;
    ry = cube2.y - cube1.y;
    rz = cube2.z - cube1.z;

    dir = directionCubeOffset(rx, ry, rz);

    if (dir == -1) {

      float f, x, y, z;

      f = 1.0f / (float) distanceCube(cube1, cube2);

      x = cube1.x + (rx * f);
      y = cube1.y + (ry * f);
      z = cube1.z + (rz * f);

      rx = Math.round(x);
      ry = Math.round(y);
      rz = Math.round(z);

      if (rx + ry + rx != 0) {

        float dx, dy, dz;

        dx = Math.abs(rx - x);
        dy = Math.abs(ry - y);
        dz = Math.abs(rz - z);

        if (dx > dy && dx > dz) {
          rx = -ry - rz;
        } else if (dy > dz) {
          ry = -rx - rz;
        } else {
          rz = -rx - ry;
        }

      }

      rx -= cube1.x;
      ry -= cube1.y;
      rz -= cube1.z;

      dir = HexMath.directionCubeOffset(rx, ry, rz);

    }

    return dir;

  }

  /**
   * Returns the integer direction of a neighboring hex given the neighbor's
   * offset. Direction is an integer ranging from 0 to 5 with 0 being directly
   * right of the hex if the sides are flat or to the right and down if the tops
   * are flat. The direction moves counter-clockwise around the hex.
   * <p>
   * If the offset is not a direct neighbor, -1 is returned.
   * 
   * @param offset
   * @return
   */
  public static int directionCubeOffset(Vector3i offset) {
    return directionCubeOffset(offset.x, offset.y, offset.z);
  }

  /**
   * Returns the integer direction of a neighboring hex given the neighbor's
   * offset. Direction is an integer ranging from 0 to 5 with 0 being directly
   * right of the hex if the sides are flat or to the right and down if the tops
   * are flat. The direction moves counter-clockwise around the hex.
   * <p>
   * If the offset is not a direct neighbor, -1 is returned.
   * 
   * @param x
   * @param y
   * @param z
   * @return
   */
  public static int directionCubeOffset(int x, int y, int z) {
    for (int i = 0; i < 6; i++) {
      if (CUBE_NEIGHBORS[i].x == x && CUBE_NEIGHBORS[i].y == y && CUBE_NEIGHBORS[i].z == z) {
        return i;
      }
    }
    return -1;
  }

  /**
   * Returns true if {@code neighbor} is a direct neighbor to {@code cube}.
   * 
   * @param cube
   * @param neighbor
   * @return
   */
  public static boolean isNeighborCube(Vector3i cube, Vector3i neighbor) {
    if (cube.equals(neighbor)) {
      return false;
    }
    int dx, dy, dz;
    dx = neighbor.x - cube.x;
    dy = neighbor.y - cube.y;
    dz = neighbor.z - cube.z;
    if (dx + dy + dz != 0) {
      return false;
    } else if (FastMath.max(FastMath.abs(dx), FastMath.abs(dy), FastMath.abs(dz)) > 1) {
      return false;
    }
    return true;
  }

  /**
   * Returns a list of cube coordinates that form a line of hexes between
   * {@code start} and {@code end}; stores the result in {@code store}.
   * 
   * @param start
   * @param end
   * @param store
   * @return
   */
  public static List<Vector3i> lineCube(Vector3i start, Vector3i end, List<Vector3i> store) {

    if (start.equals(end)) {
      store.add(new Vector3i(start));
    } else if (isNeighborCube(start, end)) {
      store.add(new Vector3i(start));
      store.add(new Vector3i(end));
    } else {

      float f, x1, y1, z1, x2, y2, z2;
      int n;

      n = distanceCube(start, end);

      f = 1.0f / (float) n;

      x1 = start.x;
      y1 = start.y;
      z1 = start.z;

      x2 = (end.x - start.x) * f;
      y2 = (end.y - start.y) * f;
      z2 = (end.z - start.z) * f;

      store.add(new Vector3i(start));
      for (int i = 0; i < n; i++) {
        x1 += x2;
        y1 += y2;
        z1 += z2;
        store.add(roundCube(x1, y1, z1, new Vector3i()));
      }

    }

    return store;

  }

}
