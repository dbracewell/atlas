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

package com.gengoai.atlas.search;

import com.gengoai.guava.common.base.Preconditions;
import com.gengoai.guava.common.collect.Lists;
import com.gengoai.atlas.Edge;
import com.gengoai.atlas.Graph;
import com.gengoai.atlas.algorithms.VertexTraversal;

import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @author David B. Bracewell
 */
public abstract class VisitorSearcher<V> implements GraphSearch<V>, VertexTraversal<V>, Serializable {

  private static final long serialVersionUID = -9189433692614954370L;
  protected final Graph<V> graph;

  /**
   * The default constructor
   *
   * @param graph The graph to visit
   */
  protected VisitorSearcher(Graph<V> graph) {
    this.graph = Preconditions.checkNotNull(graph);
  }

  @Override
  public List<Edge<V>> search(V startingPoint, V endingPoint) {
    Preconditions.checkNotNull(startingPoint);
    Preconditions.checkNotNull(endingPoint);
    Preconditions.checkArgument(graph.containsVertex(startingPoint), "The starting vertex must be in the graph");
    Preconditions.checkArgument(graph.containsVertex(endingPoint), "The ending vertex must be in the graph");

    if (startingPoint.equals(endingPoint)) {
      return Collections.emptyList();
    }


    Iterator<V> iterator = iterator(startingPoint);
    List<Edge<V>> edges = Lists.newArrayList();
    V last = null;
    while (iterator.hasNext()) {
      V v2 = iterator.next();
      if (last != null) {
        edges.add(graph.getEdge(last, v2));
      }
      last = v2;
    }

    return edges;
  }


}//END OF VisitorSearcher
