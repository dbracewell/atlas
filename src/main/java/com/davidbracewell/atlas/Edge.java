/*
 * (c) 2005 David B. Bracewell
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.davidbracewell.atlas;

import java.io.Serializable;
import java.util.Objects;

/**
 * <p>Defines an edge in a graph</p>
 *
 * @author David B. Bracewell
 */
public abstract class Edge<V> implements Serializable {

  private static final long serialVersionUID = -6040455464153202892L;

  protected final V vertex1;
  protected final V vertex2;

  protected Edge(V vertex1, V vertex2) {
    this.vertex1 = vertex1;
    this.vertex2 = vertex2;
  }

  /**
   * @return The first (from) vertex
   */
  public V getFirstVertex() {
    return vertex1;
  }

  /**
   * @return The second (to) vertex
   */
  public V getSecondVertex() {
    return vertex2;
  }

  /**
   * Gets the vertex opposite of the one given
   *
   * @param vertex The vertex whose opposite we want
   * @return The other vertex in the edge
   */
  public V getOppositeVertex(V vertex) {
    if (vertex1.equals(vertex)) {
      return vertex2;
    } else if (vertex2.equals(vertex)) {
      return vertex1;
    }
    throw new IllegalArgumentException("Vertex is not in the edge.");
  }


  /**
   * @return The weight of the edge or 1 if none
   */
  public double getWeight() {
    return 1d;
  }

  /**
   * Sets the weight of the edge
   *
   * @param weight the weight
   */
  public void setWeight(double weight) {

  }

  /**
   * @return True if the edge has a weight, false otherwise
   */
  public boolean isWeighted() {
    return false;
  }


  /**
   * @return True if the edge is directed, False if not
   */
  public abstract boolean isDirected();

  /**
   * Creates a new directed edge
   *
   * @param from The from vertex
   * @param to   The to vertex
   * @param <V>  The vertex type
   * @return The created edge
   */
  public static <V> DirectedEdge<V> directedEdge(V from, V to) {
    return new DirectedEdge<>(from, to, 1d);
  }

  /**
   * Creates a new directed edge
   *
   * @param from   The from vertex
   * @param to     The to vertex
   * @param weight The edge weight
   * @param <V>    The vertex type
   * @return The created edge
   */
  public static <V> DirectedEdge<V> directedEdge(V from, V to, double weight) {
    return new DirectedEdge<>(from, to, weight);
  }

  /**
   * Creates a new undirected edge
   *
   * @param from The from vertex
   * @param to   The to vertex
   * @param <V>  The vertex type
   * @return The created edge
   */
  public static <V> UndirectedEdge<V> undirectedEdge(V from, V to) {
    return new UndirectedEdge<>(from, to, 1d);
  }

  /**
   * Creates a new undirected edge
   *
   * @param from   The from vertex
   * @param to     The to vertex
   * @param weight The edge weight
   * @param <V>    The vertex type
   * @return The created edge
   */
  public static <V> UndirectedEdge<V> undirectedEdge(V from, V to, double weight) {
    return new UndirectedEdge<>(from, to, weight);
  }

  /**
   * An directed weighted edge implementation.
   *
   * @param <V> The type of vertex
   */
  public static class DirectedEdge<V> extends Edge<V> {
    private static final long serialVersionUID = 961303328216002925L;
    private double weight = 1d;

    protected DirectedEdge(V vertex1, V vertex2, double weight) {
      super(vertex1, vertex2);
      this.weight = weight;
    }

    @Override
    public double getWeight() {
      return weight;
    }

    @Override
    public void setWeight(double weight) {
      this.weight = weight;
    }

    @Override
    public boolean isWeighted() {
      return true;
    }

    @Override
    public boolean isDirected() {
      return true;
    }

    @Override
    public int hashCode() {
      return 31 * (vertex1.hashCode()) + 37 * (vertex2.hashCode());
    }

    @Override
    public boolean equals(Object obj) {
      if (obj == null) {
        return false;
      }
      if (obj == this) {
        return true;
      }
      if (obj instanceof DirectedEdge) {
        DirectedEdge otherEdge = (DirectedEdge) obj;
        return (Objects.equals(vertex1, otherEdge.vertex1) && Objects.equals(vertex2, otherEdge.vertex2));
      }
      return false;
    }

    @Override
    public String toString() {
      return "DirectedEdge{ " + vertex1 + " -> " + vertex2 + (isWeighted() ? " : " + getWeight() : "") + "}";
    }
  }

  /**
   * An undirected weighted edge implementation.
   *
   * @param <V> The type of vertex
   */
  public static class UndirectedEdge<V> extends Edge<V> {
    private static final long serialVersionUID = 961303328216002925L;

    private double weight;


    protected UndirectedEdge(V vertex1, V vertex2, double weight) {
      super(vertex1, vertex2);
      this.weight = weight;
    }

    @Override
    public double getWeight() {
      return weight;
    }

    @Override
    public void setWeight(double weight) {
      this.weight = weight;
    }

    @Override
    public boolean isWeighted() {
      return true;
    }

    @Override
    public boolean isDirected() {
      return false;
    }

    @Override
    public int hashCode() {
      return 31 * (vertex1.hashCode()) + 31 * (vertex2.hashCode());
    }

    @Override
    public boolean equals(Object obj) {
      if (obj == null) {
        return false;
      }
      if (obj == this) {
        return true;
      }
      if (obj instanceof UndirectedEdge) {
        UndirectedEdge otherEdge = (UndirectedEdge) obj;
        return (Objects.equals(vertex1, otherEdge.vertex1) && Objects.equals(vertex2, otherEdge.vertex2)) ||
            (Objects.equals(vertex1, otherEdge.vertex2) && Objects.equals(vertex2, otherEdge.vertex1));
      }
      return false;
    }

    @Override
    public String toString() {
      return "UndirectedEdge{ " + vertex1 + " - " + vertex2 + (isWeighted() ? " : " + getWeight() : "") + "}";
    }
  }


}//END OF Edge
