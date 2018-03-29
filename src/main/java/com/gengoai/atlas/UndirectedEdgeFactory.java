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

package com.gengoai.atlas;

import java.io.Serializable;

/**
 * The type Undirected edge factory.
 * @author David B. Bracewell
 */
public class UndirectedEdgeFactory<V> implements EdgeFactory<V>, Serializable {
  private static final long serialVersionUID = 5428309979947800172L;

  @Override
  public Edge.UndirectedEdge<V> createEdge(V from, V to, double weight) {
    return Edge.undirectedEdge(from, to, weight);
  }

  @Override
  public boolean isDirected() {
    return false;
  }

}//END OF UndirectedEdgeFactory
