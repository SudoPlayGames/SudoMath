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
 * Derived from:<br>
 * http://gamedevelopment.tutsplus.com/tutorials/quick-tip-use-the-ring-buffer-
 * data-structure-to-smooth-jittery-values--gamedev-14373
 * 
 * @author Jason Taylor
 * 
 */
public class RingBuffer extends ShaperFunction {

  private float[] buffer;
  private float sum;
  private int lastIndex;

  public RingBuffer(final int sampleCount, final float initialValue) {
    buffer = new float[sampleCount];
    lastIndex = 0;
    reset(initialValue);
  }

  public void reset(final float value) {
    sum = value * buffer.length;
    for (int i = 0; i < buffer.length; i++) {
      buffer[i] = value;
    }
  }

  public void push(final float newValue) {
    sum -= buffer[lastIndex];
    sum += newValue;
    buffer[lastIndex] = newValue;
    lastIndex = ++lastIndex >= buffer.length ? 0 : lastIndex;
  }

  public float getSmoothedValue() {
    return sum / buffer.length;
  }

  @Override
  public float shape(float x) {
    push(x);
    return sum / buffer.length;
  }

}
