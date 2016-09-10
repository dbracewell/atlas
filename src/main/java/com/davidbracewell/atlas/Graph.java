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


import com.davidbracewell.collection.Streams;
import com.davidbracewell.collection.counter.Counter;
import com.davidbracewell.collection.counter.Counters;
import com.davidbracewell.conversion.Cast;
import com.google.common.collect.Sets;
import lombok.NonNull;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Stream;

/**
 * <p>Interface defining a graph data structure.</p>
 *
 * @param <V> the vertex type
 * @author David B. Bracewell
 */
public interface Graph<V> extends Iterable<V> {

  /**
   * Adds a vertex to the graph
   *
   * @param vertex The vertex
   * @return True if the vertex was added, False if not
   */
  boolean addVertex(V vertex);

  /**
   * Adds all vertices in a collection to the graph
   *
   * @param vertices The vertices to add
   */
  void addVertices(Collection<V> vertices);

  /**
   * Adds an edge to the graph.
   *
   * @param edge the edge
   */
  void addEdge(Edge<V> edge);

  /**
   * Adds a collection of edges to the graph.
   *
   * @param edges the edges
   */
  default void addEdges(Collection<Edge<V>> edges) {
    if (edges != null) {
      edges.stream().filter(e -> !containsEdge(e)).forEach(this::addEdge);
    }
  }

  /**
   * Removes a vertex from the graph
   *
   * @param vertex The vertex to remove
   * @return True if the vertex was removed, false if not
   */
  boolean removeVertex(V vertex);

  /**
   * Number of vertices.
   *
   * @return Number of vertices in the graph
   */
  int numberOfVertices();

  /**
   * Number of edges.
   *
   * @return Number of edges in the graph
   */
  int numberOfEdges();

  /**
   * Adds an edge to the graph
   *
   * @param <T>        the type parameter
   * @param fromVertex The first (from) vertex
   * @param toVertex   The second (to) vertex
   * @return The edge that was created
   */
  <T extends Edge<V>> T addEdge(V fromVertex, V toVertex);

  /**
   * Adds an edge to the graph
   *
   * @param <T>        the type parameter
   * @param fromVertex The first (from) vertex
   * @param toVertex   The second (to) vertex
   * @param weight     The weight of the edge
   * @return The edge that was created
   */
  <T extends Edge<V>> T addEdge(V fromVertex, V toVertex, double weight);

  /**
   * Removes an edge from the graph
   *
   * @param <T>        the type parameter
   * @param fromVertex The first (from) vertex
   * @param toVertex   The second (to) vertex
   * @return The edge that was removed or null if there was no edge
   */
  <T extends Edge<V>> T removeEdge(V fromVertex, V toVertex);

  /**
   * Removes an edge from the graph
   *
   * @param edge The edge to remove
   * @return True if the edge was removed, false if not
   */
  boolean removeEdge(Edge<V> edge);

  /**
   * Checks if a vertex in the graph
   *
   * @param vertex The vertex
   * @return True if the vertex is in the graph, false if not
   */
  boolean containsVertex(V vertex);

  /**
   * Checks if an edge in the graph
   *
   * @param fromVertex The first (from) vertex
   * @param toVertex   The second (to) vertex
   * @return True if the edge is in the graph, false if not
   */
  boolean containsEdge(V fromVertex, V toVertex);

  /**
   * Checks if an edge in the graph
   *
   * @param edge The edge to check
   * @return True if the edge is in the graph, false if not
   */
  default boolean containsEdge(Edge<V> edge) {
    if (edge != null) {
      return containsEdge(edge.vertex1, edge.vertex2);
    }
    return false;
  }

  /**
   * Gets the edges coming out out of the given vertex (i.e. out-links)
   *
   * @param vertex The vertex
   * @return The set of outgoing edges
   */
  <T extends Edge<V>> Set<T> getOutEdges(V vertex);

  /**
   * Gets the edges coming in to the given vertex (i.e. in-links)
   *
   * @param vertex The vertex
   * @return The set of incoming edges
   */
  <T extends Edge<V>> Set<T> getInEdges(V vertex);

  /**
   * Gets all edges incident to the given vertex.
   *
   * @param vertex The vertex
   * @return The set of incident edges
   */
  default <T extends Edge<V>> Set<T> getEdges(V vertex) {
    return Cast.cast(Sets.union(getOutEdges(vertex), getInEdges(vertex)));
  }

  /**
   * Gets the neighbors associated with the outgoing edges for the given vertex.
   *
   * @param vertex The vertex
   * @return The set of vertices which contain an incoming edge from the given vertex.
   */
  Set<V> getSuccessors(V vertex);


  /**
   * Gets the weights associated with the edges to the successors of the given vertex.
   *
   * @param vertex The vertex
   * @return The weights associated with the edges to the successors
   */
  default Counter<V> getSuccessorWeights(V vertex) {
    Counter<V> counter = Counters.newCounter();
    for (V v2 : getSuccessors(vertex)) {
      counter.set(v2, getEdge(vertex, v2).getWeight());
    }
    return counter;
  }

  /**
   * Gets the weights associated with the edges to the predecessors of the given vertex.
   *
   * @param vertex The vertex
   * @return The weights associated with the edges to the predecessors
   */
  default Counter<V> getPredecessorsWeights(V vertex) {
    Counter<V> counter = Counters.newCounter();
    for (V v2 : getPredecessors(vertex)) {
      counter.set(v2, getEdge(vertex, v2).getWeight());
    }
    return counter;
  }

  /**
   * Gets the weights associated with the edges of the given vertex.
   *
   * @param vertex The vertex
   * @return The weights associated with the edges
   */
  default Counter<V> getWeights(V vertex) {
    Counter<V> weights = getSuccessorWeights(vertex);
    if (isDirected()) {
      weights.merge(getPredecessorsWeights(vertex));
    }
    return weights;
  }

  /**
   * Gets the neighbors associated with the incoming edges for the given vertex.
   *
   * @param vertex The vertex
   * @return The set of vertices which contain an outgoing edge to the given vertex.
   */
  Set<V> getPredecessors(V vertex);

  /**
   * Gets the set of vertices that share an edge with the given vertex
   *
   * @param vertex The vertex
   * @return The set of vertices which share an edge with the given vertex.
   */
  default Set<V> getNeighbors(V vertex) {
    return Sets.union(getPredecessors(vertex), getSuccessors(vertex));
  }

  /**
   * Is directed.
   *
   * @return True if the graph is directed, false if it is undirected
   */
  boolean isDirected();

  /**
   * Vertices set.
   *
   * @return The set of vertices in the graph
   */
  Set<V> vertices();

  /**
   * Edges set.
   *
   * @param <T> the type parameter
   * @return The set of edges in the graph
   */
  <T extends Edge<V>> Set<T> edges();

  /**
   * The number of successors
   *
   * @param vertex The vertex
   * @return The out degree
   */
  int outDegree(V vertex);

  /**
   * The number of predecessors
   *
   * @param vertex The vertex
   * @return The in degree
   */
  int inDegree(V vertex);

  /**
   * The number of neighbors
   *
   * @param vertex The vertex
   * @return The degree
   */
  int degree(V vertex);

  /**
   * Gets the edge if one, between the two given vertices
   *
   * @param v1 vertex 1
   * @param v2 vertex 2
   * @return The edge if one, null otherwise
   */
  <T extends Edge<V>> T getEdge(V v1, V v2);

  /**
   * Gets the weight of the edge if one, between the two given vertices
   *
   * @param v1 vertex 1
   * @param v2 vertex 2
   * @return The weight if one, 0 otherwise
   */
  default double getWeight(V v1, V v2) {
    Edge<V> edge = getEdge(v1, v2);
    return edge == null ? 0 : edge.getWeight();
  }

  /**
   * Merges another graph into this one ignoring any duplicate edges
   *
   * @param other The graph to merge
   */
  default void merge(Graph<V> other) {
    merge(other, EdgeMergeFunctions.<V>keepOriginal());
  }


  /**
   * Gets the edge factory used in this grapy
   *
   * @return The edge factory
   */
  EdgeFactory<V> getEdgeFactory();

  /**
   * Merges another graph into this one combining edges using the supplied merge function
   *
   * @param other         The graph to merge
   * @param mergeFunction The function to use to merge duplicate edges
   */
  default void merge(Graph<V> other, @NonNull EdgeMergeFunction<V> mergeFunction) {
    if (other == null || other.isEmpty()) {
      return;
    }
    if (this.isEmpty()) {
      this.addVertices(other.vertices());
      this.addEdges(other.edges());
    } else {
      this.addVertices(other.vertices());
      for (Edge<V> edge : other.edges()) {
        if (this.containsEdge(edge)) {
          Edge<V> orig = this.removeEdge(edge.getFirstVertex(), edge.getSecondVertex());
          this.addEdge(mergeFunction.merge(orig, edge, getEdgeFactory()));
        } else {
          this.addEdge(edge);
        }
      }
    }
  }

  /**
   * Determines if the graph is empty (no vertices no edges)
   *
   * @return True if the graph is empty
   */
  boolean isEmpty();

  /**
   * Returns a stream of the vertices in this graph
   *
   * @return A stream of the vertices in this graph
   */
  default Stream<V> stream() {
    return Streams.asStream(this);
  }

  /**
   * Returns a stream of the vertices in this graph
   *
   * @return A stream of the vertices in this graph
   */
  default Stream<V> parallelstream() {
    return Streams.asParallelStream(this);
  }

}//END OF Graph
