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


import com.davidbracewell.atlas.Edge;

import java.util.List;

/**
 * The interface Shortest path.
 *
 * @param <V> the type parameter
 * @author David B. Bracewell
 */
public interface ShortestPath<V> {

  /**
   * Distance double.
   *
   * @param from the from
   * @param to   the to
   * @return the double
   */
  default double distance(V from, V to) {
    List<Edge<V>> path = path(from, to);
    if (path == null || path.isEmpty()) {
      return Double.POSITIVE_INFINITY;
    }
    return path.size() - 1;
  }

  /**
   * Path list.
   *
   * @param from the from
   * @param to   the to
   * @return the list
   */
  List<Edge<V>> path(V from, V to);

}//END OF ShortestPath
