package com.sudoplay.math;

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
