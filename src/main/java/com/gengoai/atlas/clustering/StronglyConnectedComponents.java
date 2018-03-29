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

package com.gengoai.atlas.clustering;

import com.gengoai.atlas.Graph;
import com.gengoai.guava.common.base.Preconditions;

import java.util.*;

/**
 * Strongly Connected components clustering
 *
 * @param <V> the vertex type
 */
public class StronglyConnectedComponents<V> implements Clusterer<V> {

  private int index;
  private Stack<V> vertexStack;
  private Map<V, Integer> vertexIndex;
  private Map<V, Integer> vertexLowLink;
  private Graph<V> graph;
  private List<Set<V>> clusters;

  @Override
  public List<Set<V>> cluster(Graph<V> g) {
    Preconditions.checkNotNull(g);
    graph = g;
    index = 0;
    vertexStack = new Stack<>();
    vertexIndex = new HashMap<>();
    vertexLowLink = new HashMap<>();
    clusters = new ArrayList<>();
    g.vertices().stream().filter(v -> !vertexIndex.containsKey(v)).forEach(this::strongConnect);
    return clusters;
  }

  private void strongConnect(V vertex) {
    vertexIndex.put(vertex, index);
    vertexLowLink.put(vertex, index);

    this.index++;
    vertexStack.push(vertex);

    for (V v2 : graph.getSuccessors(vertex)) {
      if (!vertexIndex.containsKey(v2)) {
        strongConnect(v2);
        vertexLowLink.put(vertex, Math.min(vertexLowLink.get(vertex), vertexLowLink.get(v2)));
      } else {
        vertexLowLink.put(vertex, Math.min(vertexLowLink.get(vertex), vertexLowLink.get(v2)));
      }
    }

    if (vertexIndex.get(vertex).intValue() == vertexLowLink.get(vertex).intValue()) {
      Set<V> cluster = new HashSet<>();
      V w;
      do {
        w = vertexStack.pop();
        cluster.add(w);
      } while (!w.equals(vertex) && vertexStack.size() > 0);
      if (!cluster.isEmpty()) {
        clusters.add(cluster);
      }
    }
  }


}//END OF ConnectedComponents
