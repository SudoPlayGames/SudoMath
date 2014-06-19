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

/**
 * {@code Vector4i} defines a vector for a four int value tuple.
 * 
 * @author Jason Taylor
 */
public class Vector4i {

  public static final Vector4i ZERO = new Vector4i(0);
  public static final Vector4i UNIT_X = new Vector4i(1, 0, 0, 0);
  public static final Vector4i UNIT_Y = new Vector4i(0, 1, 0, 0);
  public static final Vector4i UNIT_Z = new Vector4i(0, 0, 1, 0);
  public static final Vector4i UNIT_W = new Vector4i(0, 0, 0, 1);
  public static final Vector4i UNIT_XYZW = new Vector4i(1);
  public static final Vector4i MAX = new Vector4i(Integer.MAX_VALUE);
  public static final Vector4i MIN = new Vector4i(Integer.MIN_VALUE);

  /**
   * The X value of the vector
   */
  public int x;

  /**
   * The Y value of the vector
   */
  public int y;

  /**
   * The Z value of the vector
   */
  public int z;

  /**
   * The W value of the vector
   */
  public int w;

  /**
   * Constructor instantiates a new {@code Vector3i} with default values of
   * (0,0,0)
   */
  public Vector4i() {
    x = y = z = w = 0;
  }

  public Vector4i(Vector4i v) {
    set(v);
  }

  public Vector4i(int x, int y, int z, int w) {
    this.x = x;
    this.y = y;
    this.z = z;
    this.w = w;
  }

  public Vector4i(int value) {
    x = y = z = w = value;
  }

  public void set(int x, int y, int z, int w) {
    this.x = x;
    this.y = y;
    this.z = z;
    this.w = w;
  }

  public void set(Vector4i v) {
    x = v.x;
    y = v.y;
    z = v.z;
    w = v.w;
  }

  public void set(Vector4f v) {
    x = (int) v.x;
    y = (int) v.y;
    z = (int) v.z;
    w = (int) v.w;
  }

  @Override
  public int hashCode() {
    int hash = 37;
    hash += 37 * hash + this.x;
    hash += 37 * hash + this.y;
    hash += 37 * hash + this.z;
    hash += 37 * hash + this.w;
    return hash;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null)
      return false;
    if (o == this)
      return true;
    if (o.getClass() != getClass())
      return false;
    Vector4i v = (Vector4i) o;
    return v.x == this.x && v.y == this.y && v.z == this.z && v.w == this.w;
  }

  @Override
  public String toString() {
    return "(" + x + ", " + y + ", " + z + ", " + w + ")";
  }

}
