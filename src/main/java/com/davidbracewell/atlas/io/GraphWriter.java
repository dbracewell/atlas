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

package com.davidbracewell.atlas.io;

import com.davidbracewell.atlas.Graph;
import com.davidbracewell.guava.common.collect.ArrayListMultimap;
import com.davidbracewell.guava.common.collect.Multimap;
import com.davidbracewell.io.resource.Resource;

import java.io.IOException;

/**
 * <p>The interface Graph writer.</p>
 *
 * @param <V> the vertex type
 */
public interface GraphWriter<V> {


  /**
   * Sets vertex encoder.
   *
   * @param encoder the encoder
   */
  void setVertexEncoder(VertexEncoder<V> encoder);

  /**
   * Sets edge encoder.
   *
   * @param encoder the encoder
   */
  void setEdgeEncoder(EdgeEncoder<V> encoder);


  /**
   * Write void.
   *
   * @param graph    the graph
   * @param location the location
   * @throws IOException the iO exception
   */
  default void write(Graph<V> graph, Resource location) throws IOException {
    write(graph, location, ArrayListMultimap.create());
  }


  /**
   * Write graph.
   *
   * @param graph      the graph
   * @param location   the location
   * @param parameters the parameters
   * @throws IOException the iO exception
   */
  void write(Graph<V> graph, Resource location, Multimap<String, String> parameters) throws IOException;

}//END OF GraphWriter
