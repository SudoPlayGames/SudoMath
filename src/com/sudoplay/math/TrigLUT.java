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
 * =============================================================================
 * 
 * This class contains code from http://riven8192.blogspot.com, copyright Riven
 * and licensed under CC BY 3.0.
 * 
 * =\/==========================================================================
 * 
 * Copyright (c) 2009, 2012 + any other year Riven
 * All rights reserved.
 *
 * http://creativecommons.org/licenses/by/3.0/
 * 
 */
package com.sudoplay.math;

/**
 * Thanks to Riven:
 * <p>
 * http://riven8192.blogspot.com/2009/08/fastmath-sincos-lookup-tables.html
 * 
 * @author Riven
 */
public class TrigLUT {

  public static void main(String[] args) {
    System.out.println(cos((float) Math.PI));
    System.out.println(cosDeg(180f));
  }

  public static final float sin(float rad) {
    return sin[(int) (rad * radToIndex) & SIN_MASK];
  }

  public static final float cos(float rad) {
    return cos[(int) (rad * radToIndex) & SIN_MASK];
  }

  public static final float sinDeg(float deg) {
    return sin[(int) (deg * degToIndex) & SIN_MASK];
  }

  public static final float cosDeg(float deg) {
    return cos[(int) (deg * degToIndex) & SIN_MASK];
  }

  // private static final float RAD, DEG;
  private static final int SIN_BITS, SIN_MASK, SIN_COUNT;
  private static final float radFull, radToIndex;
  private static final float degFull, degToIndex;
  private static final float[] sin, cos;

  static {
    // RAD = (float) Math.PI / 180.0f;
    // DEG = 180.0f / (float) Math.PI;

    SIN_BITS = 12;
    SIN_MASK = ~(-1 << SIN_BITS);
    SIN_COUNT = SIN_MASK + 1;

    radFull = (float) (Math.PI * 2.0);
    degFull = (float) (360.0);
    radToIndex = SIN_COUNT / radFull;
    degToIndex = SIN_COUNT / degFull;

    sin = new float[SIN_COUNT];
    cos = new float[SIN_COUNT];

    for (int i = 0; i < SIN_COUNT; i++) {
      sin[i] = (float) Math.sin((i + 0.5f) / SIN_COUNT * radFull);
      cos[i] = (float) Math.cos((i + 0.5f) / SIN_COUNT * radFull);
    }
  }
}
