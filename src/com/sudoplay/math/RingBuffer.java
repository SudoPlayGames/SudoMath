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
