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
 * Common shaper functions.
 * 
 * @author Jason Taylor
 */
public abstract class ShaperFunction {

  public abstract float shape(float x);

  public static final ShaperFunction LINEAR = new ShaperFunction() {
    @Override
    public float shape(float x) {
      return x;
    }
  };

  public static final float linear(float x) {
    return x;
  }

  public static final ShaperFunction QUADRATIC = new ShaperFunction() {
    @Override
    public float shape(float x) {
      return x * x;
    }
  };

  public static final float quadratic(float x) {
    return x * x;
  }

  public static final ShaperFunction INVERSE_QUADRATIC = new ShaperFunction() {
    @Override
    public float shape(float x) {
      return FastMath.pow(x, 0.5f);
    }
  };

  public static final float inverseQuadratic(float x) {
    return FastMath.pow(x, 0.5f);
  }

  public static final ShaperFunction CUBIC = new ShaperFunction() {
    @Override
    public float shape(float x) {
      return x * x * x;
    }
  };

  public static final float cubic(float x) {
    return x * x * x;
  }

  public static final ShaperFunction INVERSE_CUBIC = new ShaperFunction() {
    @Override
    public float shape(float x) {
      return FastMath.pow(x, FastMath.ONE_THIRD);
    }
  };

  public static final float inverseCubic(float x) {
    return FastMath.pow(x, FastMath.ONE_THIRD);
  }

  public static final ShaperFunction QUARTIC = new ShaperFunction() {
    @Override
    public float shape(float x) {
      return x * x * x * x;
    }
  };

  public static final float quartic(float x) {
    return x * x * x * x;
  }

  public static final ShaperFunction INVERSE_QUARTIC = new ShaperFunction() {
    @Override
    public float shape(float x) {
      return FastMath.pow(x, 0.25f);
    }
  };

  public static final float inverseQuartic(float x) {
    return FastMath.pow(x, 0.25f);
  }

  public static final ShaperFunction QUINTIC = new ShaperFunction() {
    @Override
    public float shape(float x) {
      return x * x * x * x * x;
    }
  };

  public static final float quintic(float x) {
    return x * x * x * x * x;
  }

  public static final ShaperFunction INVERSE_QUINTIC = new ShaperFunction() {
    @Override
    public float shape(float x) {
      return FastMath.pow(x, 0.2f);
    }
  };

  public static final float inverseQuintic(float x) {
    return FastMath.pow(x, 0.2f);
  }

  public static final ShaperFunction createNthOrderFunction(final float order) {
    return new ShaperFunction() {
      @Override
      public float shape(float x) {
        return FastMath.pow(x, order);
      }
    };
  }

}
