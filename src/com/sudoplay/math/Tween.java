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
 * This class contains code derived from http://www.robertpenner.com/easing/, 
 * copyright Robert Penner and licensed under BSD.
 * 
 * =\/==========================================================================
 * 
 * Copyright (c) 2001 Robert Penner
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of the author nor the names of its contributors
 *   may be used to endorse or promote products derived from this software
 *   without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 */
package com.sudoplay.math;

/**
 * Common tweening functions.
 * 
 * @author Jason Taylor
 */
public abstract class Tween {

  public abstract float tween(float t, float b, float c, float d);

  /**
   * Simple linear tweening, no easing.
   * 
   * @param t
   *          current time (milliseconds or frames)
   * @param b
   *          beginning value
   * @param c
   *          change in value
   * @param d
   *          duration
   * @return
   */
  public static final Tween LINEAR = new Tween() {
    @Override
    public float tween(float t, float b, float c, float d) {
      return c * t / d + b;
    }
  };

  /**
   * Simple linear tweening, no easing.
   * 
   * @param t
   *          current time (milliseconds or frames)
   * @param b
   *          beginning value
   * @param c
   *          change in value
   * @param d
   *          duration
   * @return
   */
  public static final float linear(float t, float b, float c, float d) {
    return c * t / d + b;
  }

  /**
   * Qadratic easing in, accelerating from zero velocity.
   * 
   * @param t
   *          current time (milliseconds or frames)
   * @param b
   *          beginning value
   * @param c
   *          change in value
   * @param d
   *          duration
   * @return
   */
  public static final Tween EASE_IN_QUADRATIC = new Tween() {
    @Override
    public float tween(float t, float b, float c, float d) {
      return c * (t /= d) * t + b;
    }
  };

  /**
   * Qadratic easing in, accelerating from zero velocity.
   * 
   * @param t
   *          current time (milliseconds or frames)
   * @param b
   *          beginning value
   * @param c
   *          change in value
   * @param d
   *          duration
   * @return
   */
  public static final float easeInQuadratic(float t, float b, float c, float d) {
    return c * (t /= d) * t + b;
  }

  /**
   * Quadratic easing out, decelerating to zero velocity.
   * 
   * @param t
   *          current time (milliseconds or frames)
   * @param b
   *          beginning value
   * @param c
   *          change in value
   * @param d
   *          duration
   * @return
   */
  public static final Tween EASE_OUT_QUADRATIC = new Tween() {
    @Override
    public float tween(float t, float b, float c, float d) {
      return -c * (t /= d) * (t - 2) + b;
    }
  };

  /**
   * Quadratic easing out, decelerating to zero velocity.
   * 
   * @param t
   *          current time (milliseconds or frames)
   * @param b
   *          beginning value
   * @param c
   *          change in value
   * @param d
   *          duration
   * @return
   */
  public static final float easeOutQuadratic(float t, float b, float c, float d) {
    return -c * (t /= d) * (t - 2) + b;
  }

  /**
   * Quadratic easing in/out, acceleration until halfway, then deceleration.
   * 
   * @param t
   *          current time (milliseconds or frames)
   * @param b
   *          beginning value
   * @param c
   *          change in value
   * @param d
   *          duration
   * @return
   */
  public static final Tween EASE_INOUT_QUADRATIC = new Tween() {
    @Override
    public float tween(float t, float b, float c, float d) {
      if ((t /= d / 2) < 1) {
        return c / 2 * t * t + b;
      }
      return -c / 2 * ((--t) * (t - 2) - 1) + b;
    }
  };

  /**
   * Quadratic easing in/out, acceleration until halfway, then deceleration.
   * 
   * @param t
   *          current time (milliseconds or frames)
   * @param b
   *          beginning value
   * @param c
   *          change in value
   * @param d
   *          duration
   * @return
   */
  public static final float easeInOutQuadratic(float t, float b, float c, float d) {
    if ((t /= d / 2) < 1) {
      return c / 2 * t * t + b;
    }
    return -c / 2 * ((--t) * (t - 2) - 1) + b;
  }

  /**
   * Cubic easing in, accelerating from zero velocity.
   * 
   * @param t
   *          current time (milliseconds or frames)
   * @param b
   *          beginning value
   * @param c
   *          change in value
   * @param d
   *          duration
   * @return
   */
  public static final Tween EASE_IN_CUBIC = new Tween() {
    @Override
    public float tween(float t, float b, float c, float d) {
      return c * (t /= d) * t * t + b;
    }
  };

  /**
   * Cubic easing in, accelerating from zero velocity.
   * 
   * @param t
   *          current time (milliseconds or frames)
   * @param b
   *          beginning value
   * @param c
   *          change in value
   * @param d
   *          duration
   * @return
   */
  public static final float easeInCubic(float t, float b, float c, float d) {
    return c * (t /= d) * t * t + b;
  }

  /**
   * Cubic easing out, decelerating to zero velocity.
   * 
   * @param t
   *          current time (milliseconds or frames)
   * @param b
   *          beginning value
   * @param c
   *          change in value
   * @param d
   *          duration
   * @return
   */
  public static final Tween EASE_OUT_CUBIC = new Tween() {
    @Override
    public float tween(float t, float b, float c, float d) {
      return c * ((t = t / d - 1) * t * t + 1) + b;
    }
  };

  /**
   * Cubic easing out, decelerating to zero velocity.
   * 
   * @param t
   *          current time (milliseconds or frames)
   * @param b
   *          beginning value
   * @param c
   *          change in value
   * @param d
   *          duration
   * @return
   */
  public static final float easeOutCubic(float t, float b, float c, float d) {
    return c * ((t = t / d - 1) * t * t + 1) + b;
  }

  /**
   * Cubic easing in/out, acceleration until halfway, then deceleration.
   * 
   * @param t
   *          current time (milliseconds or frames)
   * @param b
   *          beginning value
   * @param c
   *          change in value
   * @param d
   *          duration
   * @return
   */
  public static final Tween EASE_INOUT_CUBIC = new Tween() {
    @Override
    public float tween(float t, float b, float c, float d) {
      if ((t /= d / 2) < 1) {
        return c / 2 * t * t * t + b;
      }
      return c / 2 * ((t -= 2) * t * t + 2) + b;
    }
  };

  /**
   * Cubic easing in/out, acceleration until halfway, then deceleration.
   * 
   * @param t
   *          current time (milliseconds or frames)
   * @param b
   *          beginning value
   * @param c
   *          change in value
   * @param d
   *          duration
   * @return
   */
  public static final float easeInOutCubic(float t, float b, float c, float d) {
    if ((t /= d / 2) < 1) {
      return c / 2 * t * t * t + b;
    }
    return c / 2 * ((t -= 2) * t * t + 2) + b;
  }

  /**
   * Quartic easing in, accelerating from zero velocity.
   * 
   * @param t
   *          current time (milliseconds or frames)
   * @param b
   *          beginning value
   * @param c
   *          change in value
   * @param d
   *          duration
   * @return
   */
  public static final Tween EASE_IN_QUARTIC = new Tween() {
    @Override
    public float tween(float t, float b, float c, float d) {
      return c * (t /= d) * t * t * t + b;
    }
  };

  /**
   * Quartic easing in, accelerating from zero velocity.
   * 
   * @param t
   *          current time (milliseconds or frames)
   * @param b
   *          beginning value
   * @param c
   *          change in value
   * @param d
   *          duration
   * @return
   */
  public static final float easeInQuartic(float t, float b, float c, float d) {
    return c * (t /= d) * t * t * t + b;
  }

  /**
   * Quartic easing out, decelerating to zero velocity.
   * 
   * @param t
   *          current time (milliseconds or frames)
   * @param b
   *          beginning value
   * @param c
   *          change in value
   * @param d
   *          duration
   * @return
   */
  public static final Tween EASE_OUT_QUARTIC = new Tween() {
    @Override
    public float tween(float t, float b, float c, float d) {
      return -c * ((t = t / d - 1) * t * t * t - 1) + b;
    }
  };

  /**
   * Quartic easing out, decelerating to zero velocity.
   * 
   * @param t
   *          current time (milliseconds or frames)
   * @param b
   *          beginning value
   * @param c
   *          change in value
   * @param d
   *          duration
   * @return
   */
  public static final float easeOutQuartic(float t, float b, float c, float d) {
    return -c * ((t = t / d - 1) * t * t * t - 1) + b;
  }

  /**
   * Quartic easing in/out, acceleration until halfway, then deceleration.
   * 
   * @param t
   *          current time (milliseconds or frames)
   * @param b
   *          beginning value
   * @param c
   *          change in value
   * @param d
   *          duration
   * @return
   */
  public static final Tween EASE_INOUT_QUARTIC = new Tween() {
    @Override
    public float tween(float t, float b, float c, float d) {
      if ((t /= d / 2) < 1) {
        return c / 2 * t * t * t * t + b;
      }
      return -c / 2 * ((t -= 2) * t * t * t - 2) + b;
    }
  };

  /**
   * Quartic easing in/out, acceleration until halfway, then deceleration.
   * 
   * @param t
   *          current time (milliseconds or frames)
   * @param b
   *          beginning value
   * @param c
   *          change in value
   * @param d
   *          duration
   * @return
   */
  public static final float easeInOutQuartic(float t, float b, float c, float d) {
    if ((t /= d / 2) < 1) {
      return c / 2 * t * t * t * t + b;
    }
    return -c / 2 * ((t -= 2) * t * t * t - 2) + b;
  }

  /**
   * Quintic easing in, accelerating from zero velocity.
   * 
   * @param t
   *          current time (milliseconds or frames)
   * @param b
   *          beginning value
   * @param c
   *          change in value
   * @param d
   *          duration
   * @return
   */
  public static final Tween EASE_IN_QUINTIC = new Tween() {
    @Override
    public float tween(float t, float b, float c, float d) {
      return c * (t /= d) * t * t * t * t + b;
    }
  };

  /**
   * Quintic easing in, accelerating from zero velocity.
   * 
   * @param t
   *          current time (milliseconds or frames)
   * @param b
   *          beginning value
   * @param c
   *          change in value
   * @param d
   *          duration
   * @return
   */
  public static final float easeInQuintic(float t, float b, float c, float d) {
    return c * (t /= d) * t * t * t * t + b;
  }

  /**
   * Quintic easing out, decelerating to zero velocity.
   * 
   * @param t
   *          current time (milliseconds or frames)
   * @param b
   *          beginning value
   * @param c
   *          change in value
   * @param d
   *          duration
   * @return
   */
  public static final Tween EASE_OUT_QUINTIC = new Tween() {
    @Override
    public float tween(float t, float b, float c, float d) {
      return c * ((t = t / d - 1) * t * t * t * t + 1) + b;
    }
  };

  /**
   * Quintic easing out, decelerating to zero velocity.
   * 
   * @param t
   *          current time (milliseconds or frames)
   * @param b
   *          beginning value
   * @param c
   *          change in value
   * @param d
   *          duration
   * @return
   */
  public static final float easeOutQuintic(float t, float b, float c, float d) {
    return c * ((t = t / d - 1) * t * t * t * t + 1) + b;
  }

  /**
   * Quintic easing in/out, acceleration until halfway, then deceleration.
   * 
   * @param t
   *          current time (milliseconds or frames)
   * @param b
   *          beginning value
   * @param c
   *          change in value
   * @param d
   *          duration
   * @return
   */
  public static final Tween EASE_INOUT_QUINTIC = new Tween() {
    @Override
    public float tween(float t, float b, float c, float d) {
      if ((t /= d / 2) < 1) {
        return c / 2 * t * t * t * t * t + b;
      }
      return c / 2 * ((t -= 2) * t * t * t * t + 2) + b;
    }
  };

  /**
   * Quintic easing in/out, acceleration until halfway, then deceleration.
   * 
   * @param t
   *          current time (milliseconds or frames)
   * @param b
   *          beginning value
   * @param c
   *          change in value
   * @param d
   *          duration
   * @return
   */
  public static final float easeInOutQuintic(float t, float b, float c, float d) {
    if ((t /= d / 2) < 1) {
      return c / 2 * t * t * t * t * t + b;
    }
    return c / 2 * ((t -= 2) * t * t * t * t + 2) + b;
  }

  /**
   * Sinusoidal easing in, accelerating from zero velocity.
   * 
   * @param t
   *          current time (milliseconds or frames)
   * @param b
   *          beginning value
   * @param c
   *          change in value
   * @param d
   *          duration
   * @return
   */
  public static final Tween EASE_IN_SINUSOIDAL = new Tween() {
    @Override
    public float tween(float t, float b, float c, float d) {
      return -c * TrigLUT.cos(t / d * FastMath.HALF_PI) + c + b;
    }
  };

  /**
   * Sinusoidal easing in, accelerating from zero velocity.
   * 
   * @param t
   *          current time (milliseconds or frames)
   * @param b
   *          beginning value
   * @param c
   *          change in value
   * @param d
   *          duration
   * @return
   */
  public static final float easeInSinusoidal(float t, float b, float c, float d) {
    return -c * TrigLUT.cos(t / d * FastMath.HALF_PI) + c + b;
  }

  /**
   * Sinusoidal easing out, decelerating to zero velocity.
   * 
   * @param t
   *          current time (milliseconds or frames)
   * @param b
   *          beginning value
   * @param c
   *          change in value
   * @param d
   *          duration
   * @return
   */
  public static final Tween EASE_OUT_SINUSOIDAL = new Tween() {
    @Override
    public float tween(float t, float b, float c, float d) {
      return c * TrigLUT.sin(t / d * FastMath.HALF_PI) + b;
    }
  };

  /**
   * Sinusoidal easing out, decelerating to zero velocity.
   * 
   * @param t
   *          current time (milliseconds or frames)
   * @param b
   *          beginning value
   * @param c
   *          change in value
   * @param d
   *          duration
   * @return
   */
  public static final float easeOutSinusoidal(float t, float b, float c, float d) {
    return c * TrigLUT.sin(t / d * FastMath.HALF_PI) + b;
  }

  /**
   * Sinusoidal easing in/out, acceleration until halfway, then deceleration.
   * 
   * @param t
   *          current time (milliseconds or frames)
   * @param b
   *          beginning value
   * @param c
   *          change in value
   * @param d
   *          duration
   * @return
   */
  public static final Tween EASE_INOUT_SINUSOIDAL = new Tween() {
    @Override
    public float tween(float t, float b, float c, float d) {
      return -c / 2 * (TrigLUT.cos(FastMath.PI * t / d) - 1) + b;
    }
  };

  /**
   * Sinusoidal easing in/out, acceleration until halfway, then deceleration.
   * 
   * @param t
   *          current time (milliseconds or frames)
   * @param b
   *          beginning value
   * @param c
   *          change in value
   * @param d
   *          duration
   * @return
   */
  public static final float easeInOutSinusoidal(float t, float b, float c, float d) {
    return -c / 2 * (TrigLUT.cos(FastMath.PI * t / d) - 1) + b;
  }

  /**
   * Exponential easing in, accelerating from zero velocity.
   * 
   * @param t
   *          current time (milliseconds or frames)
   * @param b
   *          beginning value
   * @param c
   *          change in value
   * @param d
   *          duration
   * @return
   */
  public static final Tween EASE_IN_EXPONENTIAL = new Tween() {
    @Override
    public float tween(float t, float b, float c, float d) {
      return (t == 0) ? b : c * FastMath.pow(2, 10 * (t / d - 1)) + b;
    }
  };

  /**
   * Exponential easing in, accelerating from zero velocity.
   * 
   * @param t
   *          current time (milliseconds or frames)
   * @param b
   *          beginning value
   * @param c
   *          change in value
   * @param d
   *          duration
   * @return
   */
  public static final float easeInExponential(float t, float b, float c, float d) {
    return (t == 0) ? b : c * FastMath.pow(2, 10 * (t / d - 1)) + b;
  }

  /**
   * Exponential easing out, decelerating to zero velocity.
   * 
   * @param t
   *          current time (milliseconds or frames)
   * @param b
   *          beginning value
   * @param c
   *          change in value
   * @param d
   *          duration
   * @return
   */
  public static final Tween EASE_OUT_EXPONENTIAL = new Tween() {
    @Override
    public float tween(float t, float b, float c, float d) {
      return (t == d) ? b + c : c * (-FastMath.pow(2, -10 * t / d) + 1) + b;
    }
  };

  /**
   * Exponential easing out, decelerating to zero velocity.
   * 
   * @param t
   *          current time (milliseconds or frames)
   * @param b
   *          beginning value
   * @param c
   *          change in value
   * @param d
   *          duration
   * @return
   */
  public static final float easeOutExponential(float t, float b, float c, float d) {
    return (t == d) ? b + c : c * (-FastMath.pow(2, -10 * t / d) + 1) + b;
  }

  /**
   * Exponential easing in/out, acceleration until halfway, then deceleration.
   * 
   * @param t
   *          current time (milliseconds or frames)
   * @param b
   *          beginning value
   * @param c
   *          change in value
   * @param d
   *          duration
   * @return
   */
  public static final Tween EASE_INOUT_EXPONENTIAL = new Tween() {
    @Override
    public float tween(float t, float b, float c, float d) {
      if (t == 0)
        return b;
      if (t == d)
        return b + c;
      if ((t /= d / 2) < 1)
        return c / 2 * FastMath.pow(2, 10 * (t - 1)) + b;
      return c / 2 * (-FastMath.pow(2, -10 * --t) + 2) + b;
    }
  };

  /**
   * Exponential easing in/out, acceleration until halfway, then deceleration.
   * 
   * @param t
   *          current time (milliseconds or frames)
   * @param b
   *          beginning value
   * @param c
   *          change in value
   * @param d
   *          duration
   * @return
   */
  public static final float easeInOutExponential(float t, float b, float c, float d) {
    if (t == 0)
      return b;
    if (t == d)
      return b + c;
    if ((t /= d / 2) < 1)
      return c / 2 * FastMath.pow(2, 10 * (t - 1)) + b;
    return c / 2 * (-FastMath.pow(2, -10 * --t) + 2) + b;
  }

  /**
   * Circular easing in, accelerating from zero velocity.
   * 
   * @param t
   *          current time (milliseconds or frames)
   * @param b
   *          beginning value
   * @param c
   *          change in value
   * @param d
   *          duration
   * @return
   */
  public static final Tween EASE_IN_CIRCLULAR = new Tween() {
    @Override
    public float tween(float t, float b, float c, float d) {
      return -c * (FastMath.sqrt(1 - (t /= d) * t) - 1) + b;
    }
  };

  /**
   * Circular easing in, accelerating from zero velocity.
   * 
   * @param t
   *          current time (milliseconds or frames)
   * @param b
   *          beginning value
   * @param c
   *          change in value
   * @param d
   *          duration
   * @return
   */
  public static final float easeInCircular(float t, float b, float c, float d) {
    return -c * (FastMath.sqrt(1 - (t /= d) * t) - 1) + b;
  }

  /**
   * Circular easing out, decelerating to zero velocity.
   * 
   * @param t
   *          current time (milliseconds or frames)
   * @param b
   *          beginning value
   * @param c
   *          change in value
   * @param d
   *          duration
   * @return
   */
  public static final Tween EASE_OUT_CIRCLULAR = new Tween() {
    @Override
    public float tween(float t, float b, float c, float d) {
      return c * FastMath.sqrt(1 - (t = t / d - 1) * t) + b;
    }
  };

  /**
   * Circular easing out, decelerating to zero velocity.
   * 
   * @param t
   *          current time (milliseconds or frames)
   * @param b
   *          beginning value
   * @param c
   *          change in value
   * @param d
   *          duration
   * @return
   */
  public static final float easeOutCircular(float t, float b, float c, float d) {
    return c * FastMath.sqrt(1 - (t = t / d - 1) * t) + b;
  }

  /**
   * Circular easing in/out, acceleration until halfway, then deceleration.
   * 
   * @param t
   *          current time (milliseconds or frames)
   * @param b
   *          beginning value
   * @param c
   *          change in value
   * @param d
   *          duration
   * @return
   */
  public static final Tween EASE_INOUT_CIRCLULAR = new Tween() {
    @Override
    public float tween(float t, float b, float c, float d) {
      if ((t /= d / 2) < 1) {
        return -c / 2 * (FastMath.sqrt(1 - t * t) - 1) + b;
      }
      return c / 2 * (FastMath.sqrt(1 - (t -= 2) * t) + 1) + b;
    }
  };

  /**
   * Circular easing in/out, acceleration until halfway, then deceleration.
   * 
   * @param t
   *          current time (milliseconds or frames)
   * @param b
   *          beginning value
   * @param c
   *          change in value
   * @param d
   *          duration
   * @return
   */
  public static final float easeInOutCircular(float t, float b, float c, float d) {
    if ((t /= d / 2) < 1) {
      return -c / 2 * (FastMath.sqrt(1 - t * t) - 1) + b;
    }
    return c / 2 * (FastMath.sqrt(1 - (t -= 2) * t) + 1) + b;
  }

  /**
   * Elastic easing in, accelerating from zero velocity.
   * 
   * @param t
   *          current time (milliseconds or frames)
   * @param b
   *          beginning value
   * @param c
   *          change in value
   * @param d
   *          duration
   * @return
   */
  public static final Tween EASE_IN_ELASTIC = new Tween() {
    @Override
    public float tween(float t, float b, float c, float d) {
      if (t == 0)
        return b;
      if ((t /= d) == 1)
        return b + c;
      float p = d * 0.3f;
      float s = p / 4;
      return -(c * FastMath.pow(2, 10 * (t -= 1)) * TrigLUT.sin((t * d - s) * FastMath.TWO_PI / p)) + b;
    }
  };

  /**
   * Elastic easing in, accelerating from zero velocity.
   * 
   * @param t
   *          current time (milliseconds or frames)
   * @param b
   *          beginning value
   * @param c
   *          change in value
   * @param d
   *          duration
   * @return
   */
  public static final float easeInElastic(float t, float b, float c, float d) {
    return easeInElastic(t, b, c, d, 0, 0);
  }

  /**
   * /** Elastic easing in, accelerating from zero velocity.
   * 
   * @param t
   *          current time (milliseconds or frames)
   * @param b
   *          beginning value
   * @param c
   *          change in value
   * @param d
   *          duration
   * @param a
   *          amplitude
   * @param p
   *          period
   * @return
   */
  public static final float easeInElastic(float t, float b, float c, float d, float a, float p) {
    if (t == 0)
      return b;
    if ((t /= d) == 1)
      return b + c;
    if (p == 0)
      p = d * 0.3f;
    float s;
    if (a < FastMath.abs(c)) {
      a = c;
      s = p / 4;
    } else {
      s = p / FastMath.TWO_PI * FastMath.asin(c / a);
    }
    return -(a * FastMath.pow(2, 10 * (t -= 1)) * TrigLUT.sin((t * d - s) * FastMath.TWO_PI / p)) + b;
  }

  /**
   * Elastic easing out, decelerating to zero velocity.
   * 
   * @param t
   *          current time (milliseconds or frames)
   * @param b
   *          beginning value
   * @param c
   *          change in value
   * @param d
   *          duration
   * @return
   */
  public static final Tween EASE_OUT_ELASTIC = new Tween() {
    @Override
    public float tween(float t, float b, float c, float d) {
      if (t == 0)
        return b;
      if ((t /= d) == 1)
        return b + c;
      float p = d * 0.3f;
      float s = p / 4;
      return c * FastMath.pow(2, -10 * t) * TrigLUT.sin((t * d - s) * FastMath.TWO_PI / p) + c + b;
    }
  };

  /**
   * Elastic easing out, decelerating to zero velocity.
   * 
   * @param t
   *          current time (milliseconds or frames)
   * @param b
   *          beginning value
   * @param c
   *          change in value
   * @param d
   *          duration
   * @return
   */
  public static final float easeOutElastic(float t, float b, float c, float d) {
    return easeOutElastic(t, b, c, d, 0, 0);
  }

  /**
   * Elastic easing out, decelerating to zero velocity.
   * 
   * @param t
   *          current time (milliseconds or frames)
   * @param b
   *          beginning value
   * @param c
   *          change in value
   * @param d
   *          duration
   * @param a
   *          amplitude
   * @param p
   *          period
   * @return
   */
  public static final float easeOutElastic(float t, float b, float c, float d, float a, float p) {
    if (t == 0)
      return b;
    if ((t /= d) == 1)
      return b + c;
    if (p == 0)
      p = d * 0.3f;
    float s;
    if (a < FastMath.abs(c)) {
      a = c;
      s = p / 4;
    } else {
      s = p / FastMath.TWO_PI * FastMath.asin(c / a);
    }
    return a * FastMath.pow(2, -10 * t) * TrigLUT.sin((t * d - s) * FastMath.TWO_PI / p) + c + b;
  }

  /**
   * Elastic easing in/out, acceleration until halfway, then deceleration.
   * 
   * @param t
   *          current time (milliseconds or frames)
   * @param b
   *          beginning value
   * @param c
   *          change in value
   * @param d
   *          duration
   * @return
   */
  public static final Tween EASE_INOUT_ELASTIC = new Tween() {
    @Override
    public float tween(float t, float b, float c, float d) {
      if (t == 0)
        return b;
      if ((t /= d / 2) == 2)
        return b + c;
      float p = d * (0.3f * 1.5f);
      float s = p / 4;
      if (t < 1) {
        return -0.5f * (c * FastMath.pow(2, 10 * (t -= 1)) * TrigLUT.sin((t * d - s) * (2 * FastMath.PI) / p)) + b;
      }
      return c * FastMath.pow(2, -10 * (t -= 1)) * TrigLUT.sin((t * d - s) * FastMath.TWO_PI / p) * 0.5f + c + b;
    }
  };

  /**
   * Elastic easing in/out, acceleration until halfway, then deceleration.
   * 
   * @param t
   *          current time (milliseconds or frames)
   * @param b
   *          beginning value
   * @param c
   *          change in value
   * @param d
   *          duration
   * @return
   */
  public static final float easeInOutElastic(float t, float b, float c, float d) {
    return easeInOutElastic(t, b, c, d, 0, 0);
  }

  /**
   * Elastic easing in/out, acceleration until halfway, then deceleration.
   * 
   * @param t
   *          current time (milliseconds or frames)
   * @param b
   *          beginning value
   * @param c
   *          change in value
   * @param d
   *          duration
   * @param a
   *          amplitude
   * @param p
   *          period
   * @return
   */
  public static final float easeInOutElastic(float t, float b, float c, float d, float a, float p) {
    if (t == 0)
      return b;
    if ((t /= d / 2) == 2)
      return b + c;
    if (p == 0)
      p = d * (0.3f * 1.5f);
    float s;
    if (a < Math.abs(c)) {
      a = c;
      s = p / 4;
    } else {
      s = p / FastMath.TWO_PI * FastMath.asin(c / a);
    }
    if (t < 1) {
      return -0.5f * (a * FastMath.pow(2, 10 * (t -= 1)) * TrigLUT.sin((t * d - s) * FastMath.TWO_PI / p)) + b;
    }
    return a * FastMath.pow(2, -10 * (t -= 1)) * TrigLUT.sin((t * d - s) * FastMath.TWO_PI / p) * 0.5f + c + b;
  }

  /**
   * Back easing in, accelerating from zero velocity.
   * 
   * @param t
   *          current time (milliseconds or frames)
   * @param b
   *          beginning value
   * @param c
   *          change in value
   * @param d
   *          duration
   * @return
   */
  public static final Tween EASE_IN_BACK = new Tween() {
    @Override
    public float tween(float t, float b, float c, float d) {
      return c * (t /= d) * t * (2.70158f * t - 1.70158f) + b;
    }
  };

  /**
   * Back easing in, accelerating from zero velocity.
   * 
   * @param t
   *          current time (milliseconds or frames)
   * @param b
   *          beginning value
   * @param c
   *          change in value
   * @param d
   *          duration
   * @return
   */
  public static final float easeInBack(float t, float b, float c, float d) {
    return c * (t /= d) * t * (2.70158f * t - 1.70158f) + b;
  }

  /**
   * Back easing in, accelerating from zero velocity.
   * 
   * @param t
   *          current time (milliseconds or frames)
   * @param b
   *          beginning value
   * @param c
   *          change in value
   * @param d
   *          duration
   * @param s
   *          overshoot default 1.70158 = 10%
   * @return
   */
  public static final float easeInBack(float t, float b, float c, float d, float s) {
    return c * (t /= d) * t * ((s + 1) * t - s) + b;
  }

  /**
   * Back easing out, decelerating to zero velocity.
   * 
   * @param t
   *          current time (milliseconds or frames)
   * @param b
   *          beginning value
   * @param c
   *          change in value
   * @param d
   *          duration
   * @return
   */
  public static final Tween EASE_OUT_BACK = new Tween() {
    @Override
    public float tween(float t, float b, float c, float d) {
      return c * ((t = t / d - 1) * t * (2.70158f * t + 1.70158f) + 1) + b;
    }
  };

  /**
   * Back easing out, decelerating to zero velocity.
   * 
   * @param t
   *          current time (milliseconds or frames)
   * @param b
   *          beginning value
   * @param c
   *          change in value
   * @param d
   *          duration
   * @return
   */
  public static final float easeOutBack(float t, float b, float c, float d) {
    return c * ((t = t / d - 1) * t * (2.70158f * t + 1.70158f) + 1) + b;
  }

  /**
   * Back easing out, decelerating to zero velocity.
   * 
   * @param t
   *          current time (milliseconds or frames)
   * @param b
   *          beginning value
   * @param c
   *          change in value
   * @param d
   *          duration
   * @param s
   *          overshoot default 1.70158 = 10%
   * @return
   */
  public static final float easeOutBack(float t, float b, float c, float d, float s) {
    return c * ((t = t / d - 1) * t * ((s + 1) * t + s) + 1) + b;
  }

  /**
   * Back easing in/out, acceleration until halfway, then deceleration.
   * 
   * @param t
   *          current time (milliseconds or frames)
   * @param b
   *          beginning value
   * @param c
   *          change in value
   * @param d
   *          duration
   * @return
   */
  public static final Tween EASE_INOUT_BACK = new Tween() {
    @Override
    public float tween(float t, float b, float c, float d) {
      float s = 1.70158f;
      if ((t /= d / 2) < 1) {
        return c / 2 * (t * t * (((s *= (1.525f)) + 1) * t - s)) + b;
      }
      return c / 2 * ((t -= 2) * t * (((s *= (1.525f)) + 1) * t + s) + 2) + b;
    }
  };

  /**
   * Back easing in/out, acceleration until halfway, then deceleration.
   * 
   * @param t
   *          current time (milliseconds or frames)
   * @param b
   *          beginning value
   * @param c
   *          change in value
   * @param d
   *          duration
   * @return
   */
  public static final float easeInOutBack(float t, float b, float c, float d) {
    float s = 1.70158f;
    if ((t /= d / 2) < 1) {
      return c / 2 * (t * t * (((s *= (1.525f)) + 1) * t - s)) + b;
    }
    return c / 2 * ((t -= 2) * t * (((s *= (1.525f)) + 1) * t + s) + 2) + b;
  }

  /**
   * Back easing in/out, acceleration until halfway, then deceleration.
   * 
   * @param t
   *          current time (milliseconds or frames)
   * @param b
   *          beginning value
   * @param c
   *          change in value
   * @param d
   *          duration
   * @param s
   *          overshoot default 1.70158 = 10%
   * @return
   */
  public static final float easeInOutBack(float t, float b, float c, float d, float s) {
    if ((t /= d / 2) < 1) {
      return c / 2 * (t * t * (((s *= (1.525f)) + 1) * t - s)) + b;
    }
    return c / 2 * ((t -= 2) * t * (((s *= (1.525f)) + 1) * t + s) + 2) + b;
  }

  /**
   * Bounce easing in, accelerating from zero velocity.
   * 
   * @param t
   *          current time (milliseconds or frames)
   * @param b
   *          beginning value
   * @param c
   *          change in value
   * @param d
   *          duration
   * @return
   */
  public static final Tween EASE_IN_BOUNCE = new Tween() {
    @Override
    public float tween(float t, float b, float c, float d) {
      return c - Tween.EASE_OUT_BOUNCE.tween(d - t, 0, c, d) + b;
    }
  };

  /**
   * Bounce easing in, accelerating from zero velocity.
   * 
   * @param t
   *          current time (milliseconds or frames)
   * @param b
   *          beginning value
   * @param c
   *          change in value
   * @param d
   *          duration
   * @return
   */
  public static final float easeInBounce(float t, float b, float c, float d) {
    return c - Tween.EASE_OUT_BOUNCE.tween(d - t, 0, c, d) + b;
  }

  /**
   * Bounce easing out, decelerating to zero velocity.
   * 
   * @param t
   *          current time (milliseconds or frames)
   * @param b
   *          beginning value
   * @param c
   *          change in value
   * @param d
   *          duration
   * @return
   */
  public static final Tween EASE_OUT_BOUNCE = new Tween() {
    @Override
    public float tween(float t, float b, float c, float d) {
      if ((t /= d) < (1 / 2.75)) {
        return c * (7.5625f * t * t) + b;
      } else if (t < (2 / 2.75)) {
        return c * (7.5625f * (t -= (1.5f / 2.75f)) * t + 0.75f) + b;
      } else if (t < (2.5 / 2.75)) {
        return c * (7.5625f * (t -= (2.25f / 2.75f)) * t + 0.9375f) + b;
      } else {
        return c * (7.5625f * (t -= (2.625f / 2.75f)) * t + 0.984375f) + b;
      }
    }
  };

  /**
   * Bounce easing out, decelerating to zero velocity.
   * 
   * @param t
   *          current time (milliseconds or frames)
   * @param b
   *          beginning value
   * @param c
   *          change in value
   * @param d
   *          duration
   * @return
   */
  public static final float easeOutBounce(float t, float b, float c, float d) {
    if ((t /= d) < (1 / 2.75)) {
      return c * (7.5625f * t * t) + b;
    } else if (t < (2 / 2.75)) {
      return c * (7.5625f * (t -= (1.5f / 2.75f)) * t + 0.75f) + b;
    } else if (t < (2.5 / 2.75)) {
      return c * (7.5625f * (t -= (2.25f / 2.75f)) * t + 0.9375f) + b;
    } else {
      return c * (7.5625f * (t -= (2.625f / 2.75f)) * t + 0.984375f) + b;
    }
  }

  /**
   * Bounce easing in/out, acceleration until halfway, then deceleration.
   * 
   * @param t
   *          current time (milliseconds or frames)
   * @param b
   *          beginning value
   * @param c
   *          change in value
   * @param d
   *          duration
   * @return
   */
  public static final Tween EASE_INOUT_BOUNCE = new Tween() {
    @Override
    public float tween(float t, float b, float c, float d) {
      if (t < d / 2) {
        return Tween.EASE_IN_BOUNCE.tween(t * 2, 0, c, d) * 0.5f + b;
      }
      return Tween.EASE_OUT_BOUNCE.tween(t * 2 - d, 0, c, d) * 0.5f + c * 0.5f + b;
    }
  };

  /**
   * Bounce easing in/out, acceleration until halfway, then deceleration.
   * 
   * @param t
   *          current time (milliseconds or frames)
   * @param b
   *          beginning value
   * @param c
   *          change in value
   * @param d
   *          duration
   * @return
   */
  public static final float easeInOutBounce(float t, float b, float c, float d) {
    if (t < d / 2) {
      return Tween.EASE_IN_BOUNCE.tween(t * 2, 0, c, d) * 0.5f + b;
    }
    return Tween.EASE_OUT_BOUNCE.tween(t * 2 - d, 0, c, d) * 0.5f + c * 0.5f + b;
  }

}
