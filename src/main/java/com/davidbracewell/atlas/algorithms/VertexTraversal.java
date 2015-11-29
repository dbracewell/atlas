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

package com.davidbracewell.atlas.algorithms;

import java.util.Iterator;

/**
 * The interface Vertex traversal.
 * @param <V>  the type parameter
 * @author David B. Bracewell
 */
public interface VertexTraversal<V> {

  /**
   * Starts traversing a graph at the given vertex
   *
   * @param startingVertex The starting vertex
   * @return An iterator to traverse the graph from the starting vertex
   */
  Iterator<V> iterator(V startingVertex);

}//END OF VertexTraversal
