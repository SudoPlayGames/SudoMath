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

public class Hex2i {

  public static final Hex2i ZERO = new Hex2i();

  public int q = 0;
  public int r = 0;

  public Hex2i() {
    //
  }

  public Hex2i(Hex2i h) {
    q = h.q;
    r = h.r;
  }

  public Hex2i(Vector2i v) {
    q = v.x;
    r = v.y;
  }

  public Hex2i(int q, int r) {
    this.q = q;
    this.r = r;
  }

  public Hex2i set(Vector2i v) {
    q = v.x;
    r = v.y;
    return this;
  }

  public Hex2i set(int q, int r) {
    this.q = q;
    this.r = r;
    return this;
  }

  public Hex2i set(Hex2i h) {
    q = h.q;
    r = h.r;
    return this;
  }

  public Hex2i addLocal(Hex2i h) {
    q += h.q;
    r += h.r;
    return this;
  }

  public Vector2i store(Vector2i store) {
    store.x = q;
    store.y = r;
    return store;
  }

  public Vector2f store(Vector2f store) {
    store.x = q;
    store.y = r;
    return store;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + q;
    result = prime * result + r;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Hex2i other = (Hex2i) obj;
    if (q != other.q)
      return false;
    if (r != other.r)
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "Hex2i [q=" + q + ", r=" + r + "]";
  }

}
