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
import com.davidbracewell.collection.counter.Counter;
import com.davidbracewell.guava.common.collect.ArrayListMultimap;

/**
 * The interface Single source shortest path.
 * @param <V>  the type parameter
 * @author David B. Bracewell
 */
public interface SingleSourceShortestPath<V> {


  /**
   * Single source shortest distance.
   *
   * @param source the source
   * @return the counter
   */
  Counter<V> singleSourceShortestDistance(V source);

  /**
   * Single source shortest path.
   *
   * @param source the source
   * @return the array list multimap
   */
  ArrayListMultimap<V, Edge<V>> singleSourceShortestPath(V source);


}//END OF SingleSourceShortestPath
