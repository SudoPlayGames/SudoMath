package com.sudoplay.math;

import java.util.regex.Pattern;

/**
 * {@code ColorRGBA} defines a color made from a collection of red, green and
 * blue values. An alpha value determines is transparency. All values must be
 * between 0 and 1. If any value is set higher or lower than these constraints
 * they are clamped to the min or max. That is, if a value smaller than zero is
 * set the value clamps to zero. If a value higher than 1 is passed, that value
 * is clamped to 1. However, because the attributes r, g, b, a are public for
 * efficiency reasons, they can be directly modified with invalid values. The
 * client should take care when directly addressing the values. A call to clamp
 * will assure that the values are within the constraints.
 * 
 * @author Mark Powell (original for JME)
 * @author Jason Taylor (modified for LSS)
 */
public final class ColorRGBA implements Cloneable {

  /**
   * The color black (0,0,0).
   */
  public static final ColorRGBA Black = new ColorRGBA(0f, 0f, 0f, 1f);
  /**
   * The color white (1,1,1).
   */
  public static final ColorRGBA White = new ColorRGBA(1f, 1f, 1f, 1f);
  /**
   * The color gray (.2,.2,.2).
   */
  public static final ColorRGBA DarkGray = new ColorRGBA(0.2f, 0.2f, 0.2f, 1.0f);
  /**
   * The color gray (.5,.5,.5).
   */
  public static final ColorRGBA Gray = new ColorRGBA(0.5f, 0.5f, 0.5f, 1.0f);
  /**
   * The color gray (.8,.8,.8).
   */
  public static final ColorRGBA LightGray = new ColorRGBA(0.8f, 0.8f, 0.8f, 1.0f);
  /**
   * The color red (1,0,0).
   */
  public static final ColorRGBA Red = new ColorRGBA(1f, 0f, 0f, 1f);
  /**
   * The color green (0,1,0).
   */
  public static final ColorRGBA Green = new ColorRGBA(0f, 1f, 0f, 1f);
  /**
   * The color blue (0,0,1).
   */
  public static final ColorRGBA Blue = new ColorRGBA(0f, 0f, 1f, 1f);
  /**
   * The color yellow (1,1,0).
   */
  public static final ColorRGBA Yellow = new ColorRGBA(1f, 1f, 0f, 1f);
  /**
   * The color magenta (1,0,1).
   */
  public static final ColorRGBA Magenta = new ColorRGBA(1f, 0f, 1f, 1f);
  /**
   * The color cyan (0,1,1).
   */
  public static final ColorRGBA Cyan = new ColorRGBA(0f, 1f, 1f, 1f);
  /**
   * The color orange (251/255, 130/255,0).
   */
  public static final ColorRGBA Orange = new ColorRGBA(251f / 255f, 130f / 255f, 0f, 1f);
  /**
   * The color brown (65/255, 40/255, 25/255).
   */
  public static final ColorRGBA Brown = new ColorRGBA(65f / 255f, 40f / 255f, 25f / 255f, 1f);
  /**
   * The color pink (1, 0.68, 0.68).
   */
  public static final ColorRGBA Pink = new ColorRGBA(1f, 0.68f, 0.68f, 1f);
  /**
   * The black color with no alpha (0, 0, 0, 0).
   */
  public static final ColorRGBA BlackNoAlpha = new ColorRGBA(0f, 0f, 0f, 0f);
  /**
   * The red component of the color. 0 is none and 1 is maximum red.
   */
  private float r;
  /**
   * The green component of the color. 0 is none and 1 is maximum green.
   */
  private float g;
  /**
   * The blue component of the color. 0 is none and 1 is maximum blue.
   */
  private float b;
  /**
   * The alpha component of the color. 0 is transparent and 1 is opaque.
   */
  private float a;

  /**
   * hex base to convert numbers.
   */
  private static final int HEX_BASE = 16;

  /**
   * scale short mode factor (converts 0x5 to 0x55).
   */
  private static final int SCALE_SHORT_MODE = 0x11;

  private static final float MAX_INT_VALUE = 255.0f;

  private static ColorValidator colorValidator = new ColorValidator();

  private String colorString;

  private boolean updateColorString;

  public static class ColorValidator {
    /**
     * The pattern used to check if the string is valid to be a color
     * definition.
     */
    private static final Pattern colorPattern = Pattern.compile("#\\p{XDigit}{3,8}");

    /**
     * Check if a string fits any type of color definition string.
     * 
     * @param toCheck
     *          the text to check
     * @return <code>true</code> in case the text is a color definition
     */
    public boolean isValid(final String toCheck) {
      if (toCheck == null) {
        return false;
      }

      final int digits = toCheck.length() - 1;

      if (digits == 3 || digits == 4 || digits == 6 || digits == 8) {
        return checkSyntax(toCheck);
      }

      return false;
    }

    /**
     * Check if the color text is written in the short form with alpha. The text
     * would have to look like this: <code>#rgba</code>
     * 
     * @param toCheck
     *          the text to check
     * @return <code>true</code> in case the text is a short form color
     *         definition with alpha
     */
    public boolean isShortMode(final String toCheck) {
      return isColor(toCheck, 4);
    }

    /**
     * Check if the color text is written in the short form without alpha. The
     * text would have to look like this: <code>#rgb</code>
     * 
     * @param toCheck
     *          the text to check
     * @return <code>true</code> in case the text is a short form color
     *         definition without alpha
     */
    public boolean isShortModeWithoutAlpha(final String toCheck) {
      return isColor(toCheck, 3);
    }

    /**
     * Check if the color text is written in the long form with alpha. The text
     * would have to look like this: <code>#rrggbbaa</code>
     * 
     * @param toCheck
     *          the text to check
     * @return <code>true</code> in case the text is a long form color
     *         definition with alpha
     */
    public boolean isLongMode(final String toCheck) {
      return isColor(toCheck, 8);
    }

    /**
     * Check if the color text is written in the long form without alpha. The
     * text would have to look like this: <code>#rrggbb</code>
     * 
     * @param toCheck
     *          the text to check
     * @return <code>true</code> in case the text is a long form color
     *         definition without alpha
     */
    public boolean isLongModeWithoutAlpha(final String toCheck) {
      return isColor(toCheck, 6);
    }

    /**
     * Test of a text contains a color definition with a specified amount of
     * color digits.
     * 
     * @param toCheck
     *          the text to check
     * @param components
     *          the amount of digits to be used to define the color
     * @return <code>true</code> if the text fits a color text and meets the
     *         required length exactly
     */
    private boolean isColor(final String toCheck, final int components) {
      return (toCheck != null && toCheck.length() == (components + 1) && checkSyntax(toCheck));
    }

    /**
     * Check if the general syntax of the color string fits. That test does not
     * validate that the string has the required length.
     * 
     * @param toCheck
     *          the text to check
     * @return <code>true</code> in case the text matches the required syntax
     */
    private boolean checkSyntax(final String toCheck) {
      return colorPattern.matcher(toCheck).matches();
    }
  }

  /**
   * Constructor instantiates a new {@code ColorRGBA} object. This color is the
   * default "white" with all values 1.
   */
  public ColorRGBA() {
    r = g = b = a = 1.0f;
    updateColorString = true;
  }

  public ColorRGBA(Vector3f v) {
    this(v.x, v.y, v.z, 1.0f);
  }

  /**
   * Constructor instantiates a new {@code ColorRGBA} object. The values are
   * defined as passed parameters. These values are then clamped to insure that
   * they are between 0 and 1.
   * 
   * @param r
   *          The red component of this color.
   * @param g
   *          The green component of this {@code ColorRGBA}.
   * @param b
   *          The blue component of this {@code ColorRGBA}.
   * @param a
   *          The alpha component of this {@code ColorRGBA}.
   */
  public ColorRGBA(float r, float g, float b, float a) {
    this.r = r;
    this.g = g;
    this.b = b;
    this.a = a;
    updateColorString = true;
  }

  /**
   * Copy constructor creates a new {@code ColorRGBA} object, based on a
   * provided color.
   * 
   * @param rgba
   *          The {@code ColorRGBA} object to copy.
   */
  public ColorRGBA(ColorRGBA rgba) {
    this.a = rgba.a;
    this.r = rgba.r;
    this.g = rgba.g;
    this.b = rgba.b;
    updateColorString = true;
  }

  /**
   * {@code set} sets the RGBA values of this {@code ColorRGBA}. The values are
   * then clamped to insure that they are between 0 and 1.
   * 
   * @param r
   *          The red component of this color.
   * @param g
   *          The green component of this color.
   * @param b
   *          The blue component of this color.
   * @param a
   *          The alpha component of this color.
   * @return this
   */
  public ColorRGBA set(float r, float g, float b, float a) {
    this.r = r;
    this.g = g;
    this.b = b;
    this.a = a;
    updateColorString = true;
    return this;
  }

  /**
   * {@code set} sets the values of this {@code ColorRGBA} to those set by a
   * parameter color.
   * 
   * @param rgba
   *          The color to set this {@code ColorRGBA} to.
   * @return this
   */
  public ColorRGBA set(ColorRGBA rgba) {
    if (rgba == null) {
      r = 0;
      g = 0;
      b = 0;
      a = 0;
    } else {
      r = rgba.r;
      g = rgba.g;
      b = rgba.b;
      a = rgba.a;
    }
    updateColorString = true;
    return this;
  }

  public String getColorString() {
    updateColorString();
    return colorString;
  }

  public String getColorStringWithoutAlpha() {
    updateColorString();
    return colorString.substring(0, colorString.length() - 2);
  }

  private void updateColorString() {
    if (updateColorString) {
      StringBuilder sb = new StringBuilder();
      sb.append("#");
      sb.append(toHex(r));
      sb.append(toHex(g));
      sb.append(toHex(b));
      sb.append(toHex(a));
      colorString = sb.toString();
      updateColorString = false;
    }
  }

  private String toHex(final float value) {
    return Integer.toHexString((int) (value * 15));
  }

  /**
   * {@code clamp} insures that all values are between 0 and 1. If any are less
   * than 0 they are set to zero. If any are more than 1 they are set to one.
   */
  public void clamp() {
    if (r < 0) {
      r = 0;
    } else if (r > 1) {
      r = 1;
    }

    if (g < 0) {
      g = 0;
    } else if (g > 1) {
      g = 1;
    }

    if (b < 0) {
      b = 0;
    } else if (b > 1) {
      b = 1;
    }

    if (a < 0) {
      a = 0;
    } else if (a > 1) {
      a = 1;
    }
    updateColorString = true;
  }

  /**
   * {@code getColorArray} retrieves the color values of this {@code ColorRGBA}
   * as a four element {@code float} array in the order: r,g,b,a.
   * 
   * @return The {@code float} array that contains the color components.
   */
  public float[] getColorArray() {
    return new float[] { r, g, b, a };
  }

  /**
   * Stores the current r,g,b,a values into the given array. The given array
   * must have a length of 4 or greater, or an array index out of bounds
   * exception will be thrown.
   * 
   * @param store
   *          The {@code float} array to store the values into.
   * @return The {@code float} array after storage.
   */
  public float[] getColorArray(float[] store) {
    store[0] = r;
    store[1] = g;
    store[2] = b;
    store[3] = a;
    return store;
  }

  /**
   * Retrieves the alpha component value of this {@code ColorRGBA}.
   * 
   * @return The alpha component value.
   */
  public float getAlpha() {
    return a;
  }

  public ColorRGBA setAlpha(float alpha) {
    a = alpha;
    updateColorString = true;
    return this;
  }

  /**
   * Retrieves the red component value of this {@code ColorRGBA}.
   * 
   * @return The red component value.
   */
  public float getRed() {
    return r;
  }

  public ColorRGBA setRed(float red) {
    r = red;
    updateColorString = true;
    return this;
  }

  /**
   * Retrieves the blue component value of this {@code ColorRGBA}.
   * 
   * @return The blue component value.
   */
  public float getBlue() {
    return b;
  }

  public ColorRGBA setBlue(float blue) {
    b = blue;
    updateColorString = true;
    return this;
  }

  /**
   * Retrieves the green component value of this {@code ColorRGBA}.
   * 
   * @return The green component value.
   */
  public float getGreen() {
    return g;
  }

  public ColorRGBA setGreen(float green) {
    g = green;
    updateColorString = true;
    return this;
  }

  /**
   * Sets this {@code ColorRGBA} to the interpolation by changeAmnt from this to
   * the finalColor: this=(1-changeAmnt)*this + changeAmnt * finalColor
   * 
   * @param finalColor
   *          The final color to interpolate towards.
   * @param changeAmnt
   *          An amount between 0.0 - 1.0 representing a percentage change from
   *          this towards finalColor.
   */
  public ColorRGBA interpolate(ColorRGBA finalColor, float changeAmnt) {
    this.r = (1 - changeAmnt) * this.r + changeAmnt * finalColor.r;
    this.g = (1 - changeAmnt) * this.g + changeAmnt * finalColor.g;
    this.b = (1 - changeAmnt) * this.b + changeAmnt * finalColor.b;
    this.a = (1 - changeAmnt) * this.a + changeAmnt * finalColor.a;
    updateColorString = true;
    return this;
  }

  /**
   * Sets this {@code ColorRGBA} to the interpolation by changeAmnt from
   * beginColor to finalColor: this=(1-changeAmnt)*beginColor + changeAmnt *
   * finalColor
   * 
   * @param beginColor
   *          The begining color (changeAmnt=0).
   * @param finalColor
   *          The final color to interpolate towards (changeAmnt=1).
   * @param changeAmnt
   *          An amount between 0.0 - 1.0 representing a precentage change from
   *          beginColor towards finalColor.
   */
  public ColorRGBA interpolate(ColorRGBA beginColor, ColorRGBA finalColor, float changeAmnt) {
    this.r = (1 - changeAmnt) * beginColor.r + changeAmnt * finalColor.r;
    this.g = (1 - changeAmnt) * beginColor.g + changeAmnt * finalColor.g;
    this.b = (1 - changeAmnt) * beginColor.b + changeAmnt * finalColor.b;
    this.a = (1 - changeAmnt) * beginColor.a + changeAmnt * finalColor.a;
    updateColorString = true;
    return this;
  }

  /**
   * {@code randomColor} is a utility method that generates a random opaque
   * color.
   * 
   * @return a random {@code ColorRGBA} with an alpha set to 1.
   */
  public static ColorRGBA randomColor() {
    ColorRGBA rVal = new ColorRGBA(0, 0, 0, 1);
    rVal.r = FastMath.nextRandomFloat();
    rVal.g = FastMath.nextRandomFloat();
    rVal.b = FastMath.nextRandomFloat();
    return rVal;
  }

  /**
   * Multiplies each r,g,b,a of this {@code ColorRGBA} by the corresponding
   * r,g,b,a of the given color and returns the result as a new
   * {@code ColorRGBA}. Used as a way of combining colors and lights.
   * 
   * @param c
   *          The color to multiply by.
   * @return The new {@code ColorRGBA}. this*c
   */
  public ColorRGBA mult(ColorRGBA c) {
    return mult(c, new ColorRGBA());
  }

  public ColorRGBA mult(ColorRGBA c, ColorRGBA store) {
    store.r = r * c.r;
    store.g = g * c.g;
    store.b = b * c.b;
    store.a = a * c.a;
    return store;
  }

  public ColorRGBA multNoAlpha(ColorRGBA c) {
    return multNoAlpha(c, new ColorRGBA());
  }

  public ColorRGBA multNoAlpha(ColorRGBA c, ColorRGBA store) {
    store.r = r * c.r;
    store.g = g * c.g;
    store.b = b * c.b;
    return store;
  }

  /**
   * Multiplies each r,g,b,a of this {@code ColorRGBA} by the given scalar and
   * returns the result as a new {@code ColorRGBA}. Used as a way of making
   * colors dimmer or brighter.
   * 
   * @param scalar
   *          The scalar to multiply by.
   * @return The new {@code ColorRGBA}. this*scalar
   */
  public ColorRGBA mult(float scalar) {
    return mult(scalar, new ColorRGBA());
  }

  public ColorRGBA mult(float scalar, ColorRGBA store) {
    store.r = r * scalar;
    store.g = g * scalar;
    store.b = b * scalar;
    store.a = a * scalar;
    return store;
  }

  public ColorRGBA multNoAlpha(float scalar) {
    return multNoAlpha(scalar, new ColorRGBA());
  }

  public ColorRGBA multNoAlpha(float scalar, ColorRGBA store) {
    store.r = r * scalar;
    store.g = g * scalar;
    store.b = b * scalar;
    return store;
  }

  /**
   * Multiplies each r,g,b,a of this {@code ColorRGBA} by the given scalar and
   * returns the result (this). Used as a way of making colors dimmer or
   * brighter.
   * 
   * @param scalar
   *          The scalar to multiply by.
   * @return this*c
   */
  public ColorRGBA multLocal(float scalar) {
    this.r *= scalar;
    this.g *= scalar;
    this.b *= scalar;
    this.a *= scalar;
    updateColorString = true;
    return this;
  }

  public ColorRGBA multLocal(ColorRGBA c) {
    r *= c.r;
    g *= c.g;
    b *= c.b;
    a *= c.a;
    updateColorString = true;
    return this;
  }

  public ColorRGBA multLocalNoAlpha(float scalar) {
    r *= scalar;
    g *= scalar;
    b *= scalar;
    updateColorString = true;
    return this;
  }

  public ColorRGBA multLocalNoAlpha(ColorRGBA c) {
    r *= c.r;
    g *= c.g;
    b *= c.b;
    updateColorString = true;
    return this;
  }

  /**
   * Adds each r,g,b,a of this {@code ColorRGBA} by the corresponding r,g,b,a of
   * the given color and returns the result as a new {@code ColorRGBA}. Used as
   * a way of combining colors and lights.
   * 
   * @param c
   *          The color to add.
   * @return The new {@code ColorRGBA}. this+c
   */
  public ColorRGBA add(ColorRGBA c) {
    return add(c, new ColorRGBA());
  }

  /**
   * Adds each r,g,b,a of this {@code ColorRGBA} by the corresponding r,g,b,a of
   * the given color and returns the result in the store {@code ColorRGBA}. Used
   * as a way of combining colors and lights.
   * 
   * @param c
   * @param store
   * @return
   */
  public ColorRGBA add(ColorRGBA c, ColorRGBA store) {
    store.r = r + c.r;
    store.g = g + c.g;
    store.b = b + c.b;
    store.a = a + c.a;
    return store;
  }

  public ColorRGBA addNoAlpha(ColorRGBA c) {
    return addNoAlpha(c, new ColorRGBA());
  }

  public ColorRGBA addNoAlpha(ColorRGBA c, ColorRGBA store) {
    store.r = r + c.r;
    store.g = g + c.g;
    store.b = b + c.b;
    return store;
  }

  /**
   * Adds each r,g,b,a of this {@code ColorRGBA} by the r,g,b,a the given color
   * and returns the result (this). Used as a way of combining colors and
   * lights.
   * 
   * @param c
   *          The color to add.
   * @return this+c
   */
  public ColorRGBA addLocal(ColorRGBA c) {
    r += c.r;
    g += c.g;
    b += c.b;
    a += c.a;
    updateColorString = true;
    return this;
  }

  public ColorRGBA addLocalNoAlpha(ColorRGBA c) {
    r += c.r;
    g += c.g;
    b += c.b;
    updateColorString = true;
    return this;
  }

  /**
   * {@code toString} returns the string representation of this
   * {@code ColorRGBA}. The format of the string is:<br>
   * <Class Name>: [R=RR.RRRR, G=GG.GGGG, B=BB.BBBB, A=AA.AAAA]
   * 
   * @return The string representation of this {@code ColorRGBA}.
   */
  @Override
  public String toString() {
    return "Color[" + r + ", " + g + ", " + b + ", " + a + "]";
  }

  @Override
  public ColorRGBA clone() {
    try {
      return (ColorRGBA) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new AssertionError(); // can not happen
    }
  }

  /**
   * Saves this {@code ColorRGBA} into the given {@code float} array.
   * 
   * @param floats
   *          The {@code float} array to take this {@code ColorRGBA}. If null, a
   *          new {@code float[4]} is created.
   * @return The array, with r,g,b,a float values in that order.
   */
  public float[] toArray(float[] floats) {
    if (floats == null) {
      floats = new float[4];
    }
    floats[0] = r;
    floats[1] = g;
    floats[2] = b;
    floats[3] = a;
    return floats;
  }

  /**
   * {@code equals} returns true if this {@code ColorRGBA} is logically
   * equivalent to a given color. That is, if all the components of the two
   * colors are the same. False is returned otherwise.
   * 
   * @param o
   *          The object to compare against.
   * @return true if the colors are equal, false otherwise.
   */
  @Override
  public boolean equals(Object o) {
    if (!(o instanceof ColorRGBA)) {
      return false;
    }

    if (this == o) {
      return true;
    }

    ColorRGBA comp = (ColorRGBA) o;
    if (Float.compare(r, comp.r) != 0) {
      return false;
    }
    if (Float.compare(g, comp.g) != 0) {
      return false;
    }
    if (Float.compare(b, comp.b) != 0) {
      return false;
    }
    if (Float.compare(a, comp.a) != 0) {
      return false;
    }
    return true;
  }

  /**
   * {@code hashCode} returns a unique code for this {@code ColorRGBA} based on
   * its values. If two colors are logically equivalent, they will return the
   * same hash code value.
   * 
   * @return The hash code value of this {@code ColorRGBA}.
   */
  @Override
  public int hashCode() {
    int hash = 37;
    hash += 37 * hash + Float.floatToIntBits(r);
    hash += 37 * hash + Float.floatToIntBits(g);
    hash += 37 * hash + Float.floatToIntBits(b);
    hash += 37 * hash + Float.floatToIntBits(a);
    return hash;
  }

  /**
   * Retrieves the component values of this {@code ColorRGBA} as a four element
   * {@code byte} array in the order: r,g,b,a.
   * 
   * @return the {@code byte} array that contains the color components.
   */
  public byte[] toBytesRGBA() {
    byte[] store = new byte[4];
    store[0] = (byte) ((int) (r * 255) & 0xFF);
    store[1] = (byte) ((int) (g * 255) & 0xFF);
    store[2] = (byte) ((int) (b * 255) & 0xFF);
    store[3] = (byte) ((int) (a * 255) & 0xFF);
    return store;
  }

  /**
   * Retrieves the component values of this {@code ColorRGBA} as an {@code int}
   * in a,r,g,b order. Bits 24-31 are alpha, 16-23 are red, 8-15 are green, 0-7
   * are blue.
   * 
   * @return The integer representation of this {@code ColorRGBA} in a,r,g,b
   *         order.
   */
  public int toIntARGB() {
    int argb = (((int) (a * 255) & 0xFF) << 24) | (((int) (r * 255) & 0xFF) << 16) | (((int) (g * 255) & 0xFF) << 8) | (((int) (b * 255) & 0xFF));
    return argb;
  }

  /**
   * Retrieves the component values of this {@code ColorRGBA} as an {@code int}
   * in r,g,b,a order. Bits 24-31 are red, 16-23 are green, 8-15 are blue, 0-7
   * are alpha.
   * 
   * @return The integer representation of this {@code ColorRGBA} in r,g,b,a
   *         order.
   */
  public int toIntRGBA() {
    int rgba = (((int) (r * 255) & 0xFF) << 24) | (((int) (g * 255) & 0xFF) << 16) | (((int) (b * 255) & 0xFF) << 8) | (((int) (a * 255) & 0xFF));
    return rgba;
  }

  /**
   * Retrieves the component values of this {@code ColorRGBA} as an {@code int}
   * in a,b,g,r order. Bits 24-31 are alpha, 16-23 are blue, 8-15 are green, 0-7
   * are red.
   * 
   * @return The integer representation of this {@code ColorRGBA} in a,b,g,r
   *         order.
   */
  public int toIntABGR() {
    int abgr = (((int) (a * 255) & 0xFF) << 24) | (((int) (b * 255) & 0xFF) << 16) | (((int) (g * 255) & 0xFF) << 8) | (((int) (r * 255) & 0xFF));
    return abgr;
  }

  /**
   * Sets the component values of this {@code ColorRGBA} with the given combined
   * ARGB {@code int}. Bits 24-31 are alpha, bits 16-23 are red, bits 8-15 are
   * green, bits 0-7 are blue.
   * 
   * @param color
   *          The integer ARGB value used to set this {@code ColorRGBA}.
   */
  public ColorRGBA fromIntARGB(int color) {
    a = ((byte) (color >> 24) & 0xFF) / 255f;
    r = ((byte) (color >> 16) & 0xFF) / 255f;
    g = ((byte) (color >> 8) & 0xFF) / 255f;
    b = ((byte) (color) & 0xFF) / 255f;
    updateColorString = true;
    return this;
  }

  /**
   * Sets the RGBA values of this {@code ColorRGBA} with the given combined RGBA
   * value Bits 24-31 are red, bits 16-23 are green, bits 8-15 are blue, bits
   * 0-7 are alpha.
   * 
   * @param color
   *          The integer RGBA value used to set this object.
   */
  public ColorRGBA fromIntRGBA(int color) {
    r = ((byte) (color >> 24) & 0xFF) / 255f;
    g = ((byte) (color >> 16) & 0xFF) / 255f;
    b = ((byte) (color >> 8) & 0xFF) / 255f;
    a = ((byte) (color) & 0xFF) / 255f;
    updateColorString = true;
    return this;
  }

  /**
   * Transform this {@code ColorRGBA} to a {@code Vector3f} using x = r, y = g,
   * z = b. The Alpha value is not used. This method is useful to use for
   * shaders assignment.
   * <p>
   * A new {@link Vector3f} is created using this method.
   * 
   * @return A {@code Vector3f} containing the RGB value of this
   *         {@code ColorRGBA}.
   */
  public Vector3f toVector3f() {
    return toVector3f(new Vector3f());
  }

  public Vector3f toVector3f(Vector3f store) {
    return store.set(r, g, b);
  }

  /**
   * Transform this {@code ColorRGBA} to a {@code Vector4f} using x = r, y = g,
   * z = b, w = a. This method is useful to use for shaders assignment.
   * <p>
   * A new {@link Vector4f} is created using this method.
   * 
   * @return A {@code Vector4f} containing the RGBA value of this
   *         {@code ColorRGBA}.
   */
  public Vector4f toVector4f() {
    return toVector4f(new Vector4f());
  }

  public Vector4f toVector4f(Vector4f store) {
    return store.set(r, g, b, a);
  }

  public ColorRGBA fromString(final String color) {
    colorString = color;
    if (colorValidator.isShortModeWithoutAlpha(color)) {
      r = getRFromString(color);
      g = getGFromString(color);
      b = getBFromString(color);
      a = 1.0f;
    } else if (colorValidator.isLongModeWithoutAlpha(color)) {
      r = getRFromString(color);
      g = getGFromString(color);
      b = getBFromString(color);
      a = 1.0f;
    } else if (colorValidator.isValid(color)) {
      r = getRFromString(color);
      g = getGFromString(color);
      b = getBFromString(color);
      a = getAFromString(color);
    } else {
      r = g = b = a = 1.0f;
    }
    updateColorString = true;
    return this;
  }

  public ColorRGBA fromStringWithoutAlpha(final String color) {
    colorString = color + toHex(a);
    if (colorValidator.isShortModeWithoutAlpha(color)) {
      r = getRFromString(color);
      g = getGFromString(color);
      b = getBFromString(color);
    } else if (colorValidator.isLongModeWithoutAlpha(color)) {
      r = getRFromString(color);
      g = getGFromString(color);
      b = getBFromString(color);
    } else if (colorValidator.isValid(color)) {
      r = getRFromString(color);
      g = getGFromString(color);
      b = getBFromString(color);
    } else {
      r = g = b = 1.0f;
    }
    updateColorString = true;
    return this;
  }

  /**
   * Checks that the provided string is a valid hex color.
   * 
   * @param color
   * @return true or false
   */
  public static boolean check(final String color) {
    if (colorValidator.isShortModeWithoutAlpha(color) || colorValidator.isLongModeWithoutAlpha(color) || colorValidator.isValid(color)) {
      return true;
    }
    return false;
  }

  public ColorRGBA fromVector3f(Vector3f v) {
    r = v.x;
    g = v.y;
    b = v.z;
    updateColorString = true;
    return this;
  }

  public ColorRGBA fromVector4f(Vector4f v) {
    r = v.x;
    g = v.y;
    b = v.z;
    a = v.w;
    updateColorString = true;
    return this;
  }

  /**
   * Sets this color from the provided hue, saturation and value; also sets
   * alpha from the vector's w value.
   * 
   * @param v
   * @return
   */
  public ColorRGBA fromHSVA(Vector4f v) {
    fromHSV(v.x, v.y, v.z);
    return setAlpha(v.w);
  }

  /**
   * Sets this color from the provided hue, saturation and value.
   * <p>
   * This method expects 0 <= h, s, v <= 1
   * 
   * @see ColorRGBA#fromHSV(float, float, float)
   * @param v
   * @return
   */
  public ColorRGBA fromHSV(Vector3f v) {
    return fromHSV(v.x, v.y, v.z);
  }

  public ColorRGBA fromHSVA(Vector3f v, float alpha) {
    fromHSV(v.x, v.y, v.z);
    return setAlpha(alpha);
  }

  public ColorRGBA fromHSVA(float hue, float saturation, float value, float alpha) {
    fromHSV(hue, saturation, value);
    return setAlpha(alpha);
  }

  /**
   * Sets this color from the provided hue, saturation and value.
   * <p>
   * This method expects 0 <= h, s, v <= 1
   * 
   * @param hue
   * @param saturation
   * @param value
   * @return
   */
  public ColorRGBA fromHSV(float hue, float saturation, float value) {

    float h = FastMath.clamp(hue, 0f, 1f);
    float s = FastMath.clamp(saturation, 0f, 1f);
    float v = FastMath.clamp(value, 0f, 1f);

    int i;
    float f, p, q, t;

    i = (int) FastMath.floor(h * 6);
    f = h * 6 - i;
    p = v * (1 - s);
    q = v * (1 - f * s);
    t = v * (1 - (1 - f) * s);
    switch (i % 6) {
      case 0:
        this.r = v;
        this.g = t;
        this.b = p;
        break;
      case 1:
        this.r = q;
        this.g = v;
        this.b = p;
        break;
      case 2:
        this.r = p;
        this.g = v;
        this.b = t;
        break;
      case 3:
        this.r = p;
        this.g = q;
        this.b = v;
        break;
      case 4:
        this.r = t;
        this.g = p;
        this.b = v;
        break;
      case 5:
        this.r = v;
        this.g = p;
        this.b = q;
        break;
    }
    updateColorString = true;
    return this;
  }

  /**
   * @return a new {@link Vector4f} object containing the converted HSVA values
   *         of this color
   */
  public Vector4f toHSVA() {
    return toHSV(new Vector4f());
  }

  /**
   * Converts this color into HSV color space and stores the results with the
   * alpha value in the provided {@link Vector4f}.
   * 
   * @param store
   * @return
   */
  public Vector4f toHSV(Vector4f store) {

    float d, max, min;

    max = FastMath.max(r, g, b);
    min = FastMath.min(r, g, b);

    store.w = a;
    store.z = max;

    d = max - min;
    store.y = max == 0 ? 0 : d / max;

    if (max == min) {
      store.x = 0; // achromatic
    } else {
      if (FastMath.compareFloat(r, max)) {
        store.x = (g - b) / d + (g < b ? 6 : 0);
      } else if (FastMath.compareFloat(g, max)) {
        store.x = (b - r) / d + 2;
      } else {
        store.x = (r - g) / d + 4;
      }
      store.x /= 6f;
    }

    return store;
  }

  /**
   * @return a new {@link Vector3f} object containing the converted HSV values
   *         of this color
   */
  public Vector3f toHSV() {
    return toHSV(new Vector3f());
  }

  /**
   * Converts this color into HSV color space and stores the results without the
   * alpha value in the provided {@link Vector3f}.
   * 
   * @param store
   * @return
   */
  public Vector3f toHSV(Vector3f store) {

    float d, max, min;

    max = FastMath.max(r, g, b);
    min = FastMath.min(r, g, b);

    store.z = max;

    d = max - min;
    store.y = max == 0 ? 0 : d / max;

    if (max == min) {
      store.x = 0; // achromatic
    } else {
      if (FastMath.compareFloat(r, max)) {
        store.x = (g - b) / d + (g < b ? 6 : 0);
      } else if (FastMath.compareFloat(g, max)) {
        store.x = (b - r) / d + 2;
      } else {
        store.x = (r - g) / d + 4;
      }
      store.x /= 6f;
    }

    return store;
  }

  private float getRFromString(final String color) {
    if (isShortMode(color)) {
      return (Integer.parseInt(color.substring(1, 2), HEX_BASE) * SCALE_SHORT_MODE) / MAX_INT_VALUE;
    } else {
      return Integer.parseInt(color.substring(1, 3), HEX_BASE) / MAX_INT_VALUE;
    }
  }

  private float getGFromString(final String color) {
    if (isShortMode(color)) {
      return (Integer.parseInt(color.substring(2, 3), HEX_BASE) * SCALE_SHORT_MODE) / MAX_INT_VALUE;
    } else {
      return Integer.parseInt(color.substring(3, 5), HEX_BASE) / MAX_INT_VALUE;
    }
  }

  private float getBFromString(final String color) {
    if (isShortMode(color)) {
      return (Integer.parseInt(color.substring(3, 4), HEX_BASE) * SCALE_SHORT_MODE) / MAX_INT_VALUE;
    } else {
      return Integer.parseInt(color.substring(5, 7), HEX_BASE) / MAX_INT_VALUE;
    }
  }

  private float getAFromString(final String color) {
    if (isShortMode(color)) {
      return (Integer.parseInt(color.substring(4, 5), HEX_BASE) * SCALE_SHORT_MODE) / MAX_INT_VALUE;
    } else {
      return Integer.parseInt(color.substring(7, 9), HEX_BASE) / MAX_INT_VALUE;
    }
  }

  /**
   * Returns true when the given string is from format: #ffff or #fff
   * 
   * @param color
   *          the color string
   * @return true or false
   */
  private boolean isShortMode(final String color) {
    return colorValidator.isShortMode(color) || colorValidator.isShortModeWithoutAlpha(color);
  }

}
