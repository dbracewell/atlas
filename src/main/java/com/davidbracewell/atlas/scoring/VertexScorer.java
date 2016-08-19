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

package com.davidbracewell.atlas.scoring;


import com.davidbracewell.atlas.Graph;
import com.davidbracewell.collection.counter.Counter;

import java.util.List;
import java.util.Map;

/**
 * Interface for algorithms that rank vertices in a graph.
 *
 * @param <V> The vertex type
 */
public interface VertexScorer<V> {

  /**
   * Ranks the vertices based on the score
   *
   * @param g The graph
   * @return A sorted list of vertex - double pairs
   */
  public List<Map.Entry<V, Double>> rank(Graph<V> g);

  /**
   * Scores the vertices in the graph
   *
   * @param g The graph
   * @return A counter with vertex as key and score as value
   */
  public Counter<V> score(Graph<V> g);

}//END OF VertexRanker
