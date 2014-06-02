package com.sudoplay.math;

public class Hexagon {

  private float size, width, height;
  private Vector2f center = new Vector2f();
  private Vector2f[] corners = new Vector2f[6];
  private boolean flatTop;

  public Hexagon(float size, Vector2f center) {
    this(size, center.x, center.y, false);
  }

  public Hexagon(float size, float x, float y) {
    this(size, x, y, false);
  }

  public Hexagon(float size, Vector2f center, boolean flatTop) {
    this(size, center.x, center.y, flatTop);
  }

  public Hexagon(float size, float x, float y, boolean flatTop) {
    this.center.set(x, y);
    this.flatTop = flatTop;
    for (int i = 0; i < 6; i++) {
      corners[i] = new Vector2f();
    }
    setSize(size);
  }

  public Vector2f getCenter() {
    return center;
  }

  public Vector2f getCenter(Vector2f store) {
    return store.set(center);
  }

  public void setCenter(Vector2f center) {
    this.center.set(center);
  }

  public void setCenter(float x, float y) {
    this.center.set(x, y);
  }

  public float getSize() {
    return size;
  }

  public void setSize(float size) {
    this.size = size;
    if (flatTop) {
      for (int i = 0; i < 6; i++) {
        HexMath.getCornerFlatTop(size, i, corners[i]);
      }
      width = HexMath.layoutQWidth(size);
      height = HexMath.layoutQHeight(size);
    } else {
      for (int i = 0; i < 6; i++) {
        HexMath.getCornerFlatSide(size, i, corners[i]);
      }
      width = HexMath.layoutRWidth(size);
      height = HexMath.layoutRHeight(size);
    }
  }

  public float getWidth() {
    return width;
  }

  public float getHeight() {
    return height;
  }

  public Vector2f getCorner(int index, Vector2f store) {
    return store.set(corners[index]);
  }

  public Vector2f getCorner(int index) {
    return corners[index];
  }

  public Vector2f[] getCorners() {
    return corners;
  }

}
