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

package com.gengoai.atlas.io;


import com.gengoai.guava.common.collect.ArrayListMultimap;
import com.gengoai.guava.common.collect.Multimap;
import com.gengoai.mango.io.resource.Resource;
import com.gengoai.atlas.Graph;

import java.io.IOException;

/**
 * <p>Common interface for rendering a graph</p>
 *
 * @param <V> the vertex type
 */
public interface GraphRenderer<V> {

  /**
   * Sets vertex encoder.
   *
   * @param serializer the serializer
   */
  void setVertexEncoder(VertexEncoder<V> serializer);

  /**
   * Sets edge encoder.
   *
   * @param serializer the serializer
   */
  void setEdgeEncoder(EdgeEncoder<V> serializer);

  /**
   * Renders a graph storing the rendering inthe given resource.
   *
   * @param graph    the graph
   * @param location the location
   * @throws IOException the iO exception
   */
  default void render(Graph<V> graph, Resource location) throws IOException {
    render(graph, location, ArrayListMultimap.create());
  }


  /**
   * Renders a graph storing the rendering in the given resource.
   *
   * @param graph      the graph
   * @param location   the location
   * @param parameters the parameters
   * @throws IOException the iO exception
   */
  void render(Graph<V> graph, Resource location, Multimap<String, String> parameters) throws IOException;

}//END OF GraphRenderer
