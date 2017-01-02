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


import com.davidbracewell.collection.counter.Counter;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * @author David B. Bracewell
 */
public abstract class ForwardingGraph<V> implements Graph<V>, Serializable {
  private static final long serialVersionUID = 8903009720469942081L;

  /**
   * Returns the graph that all calls are delegated to
   *
   * @return The graph to delegate calls to
   */
  protected abstract Graph<V> delegate();

  @Override
  public boolean addVertex(V vertex) {
    return delegate().addVertex(vertex);
  }

  @Override
  public boolean removeVertex(V vertex) {
    return delegate().removeVertex(vertex);
  }

  @Override
  public int numberOfVertices() {
    return delegate().numberOfVertices();
  }

  @Override
  public int numberOfEdges() {
    return delegate().numberOfEdges();
  }

  @Override
  public <T extends Edge<V>> T addEdge(V fromVertex, V toVertex) {
    return delegate().addEdge(fromVertex, toVertex);
  }

  @Override
  public <T extends Edge<V>> T addEdge(V fromVertex, V toVertex, double weight) {
    return delegate().addEdge(fromVertex, toVertex, weight);
  }

  @Override
  public <T extends Edge<V>> T removeEdge(V fromVertex, V toVertex) {
    return delegate().removeEdge(fromVertex, toVertex);
  }

  @Override
  public boolean removeEdge(Edge<V> edge) {
    return delegate().removeEdge(edge);
  }

  @Override
  public boolean containsVertex(V vertex) {
    return delegate().containsVertex(vertex);
  }

  @Override
  public boolean containsEdge(V fromVertex, V toVertex) {
    return delegate().containsEdge(fromVertex, toVertex);
  }

  @Override
  public boolean containsEdge(Edge<V> edge) {
    return delegate().containsEdge(edge);
  }

  @Override
  public <T extends Edge<V>> Set<T> getOutEdges(V vertex) {
    return delegate().getOutEdges(vertex);
  }

  @Override
  public <T extends Edge<V>> Set<T> getInEdges(V vertex) {
    return delegate().getInEdges(vertex);
  }

  @Override
  public <T extends Edge<V>> Set<T> getEdges(V vertex) {
    return delegate().getEdges(vertex);
  }

  @Override
  public Set<V> getSuccessors(V vertex) {
    return delegate().getSuccessors(vertex);
  }

  @Override
  public Counter<V> getSuccessorWeights(V vertex) {
    return delegate().getSuccessorWeights(vertex);
  }

  @Override
  public Counter<V> getPredecessorsWeights(V vertex) {
    return delegate().getPredecessorsWeights(vertex);
  }

  @Override
  public Counter<V> getWeights(V vertex) {
    return delegate().getWeights(vertex);
  }

  @Override
  public Set<V> getPredecessors(V vertex) {
    return delegate().getPredecessors(vertex);
  }

  @Override
  public Set<V> getNeighbors(V vertex) {
    return delegate().getNeighbors(vertex);
  }

  @Override
  public boolean isDirected() {
    return delegate().isDirected();
  }

  @Override
  public Set<V> vertices() {
    return delegate().vertices();
  }

  @Override
  public <T extends Edge<V>> Set<T> edges() {
    return delegate().edges();
  }

  @Override
  public int outDegree(V vertex) {
    return delegate().outDegree(vertex);
  }

  @Override
  public int inDegree(V vertex) {
    return delegate().inDegree(vertex);
  }

  @Override
  public int degree(V vertex) {
    return delegate().degree(vertex);
  }

  @Override
  public <T extends Edge<V>> T getEdge(V v1, V v2) {
    return delegate().getEdge(v1, v2);
  }

  @Override
  public double getWeight(V v1, V v2) {
    return delegate().getWeight(v1, v2);
  }

  @Override
  public Iterator<V> iterator() {
    return delegate().iterator();
  }

  @Override
  public void addVertices(Collection<V> vertices) {
    delegate().addVertices(vertices);
  }

  @Override
  public void addEdge(Edge<V> edge) {
    delegate().addEdge(edge);
  }

  @Override
  public void addEdges(Collection<Edge<V>> edges) {
    delegate().addEdges(edges);
  }

  @Override
  public void merge(Graph<V> other) {
    delegate().merge(other);
  }

  @Override
  public void merge(Graph<V> other, EdgeMergeFunction<V> mergeFunction) {
    delegate().merge(other, mergeFunction);
  }

  @Override
  public boolean isEmpty() {
    return delegate().isEmpty();
  }
}//END OF ForwardingGraph
