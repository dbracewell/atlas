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

import com.davidbracewell.atlas.io.DefaultEncodersDecoders;
import com.davidbracewell.atlas.io.GraphML;
import com.davidbracewell.io.resource.Resource;
import com.davidbracewell.io.resource.StringResource;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author David B. Bracewell
 */
public class GraphUtilsTest {


  Graph<String> d1;
  Graph<String> d2;

  @Before
  public void setUp() throws Exception {
    d1 = AdjacencyMatrix.directed();
    d1.addVertex("A");
    d1.addVertex("B");
    d1.addVertex("C");
    d1.addEdge("A", "B");
    d1.addEdge("A", "C");
    d1.addEdge("C", "B");

    d2 = AdjacencyMatrix.directed();
    d2.addVertex("A");
    d2.addVertex("B");
    d2.addVertex("C");
    d2.addVertex("D");
    d2.addEdge("A", "B", 20.0d);
    d2.addEdge("A", "D");
    d2.addEdge("B", "C");
  }

  @Test
  public void testMergeNonEmpty() throws Exception {
    d1.merge(d2, EdgeMergeFunctions.<String>keepOriginal());
    assertEquals(4, d1.numberOfVertices());
    assertEquals(1d, d1.getWeight("A", "B"), 0d);
    assertTrue(d1.containsEdge("C", "B"));
    assertTrue(d1.containsEdge("B", "C"));

    GraphML<String> graphViz = new GraphML<>();
    graphViz.setVertexEncoder(DefaultEncodersDecoders.jsonVertexEncoder());
    graphViz.setVertexDecoder(DefaultEncodersDecoders.jsonVertexDecoder(String.class));
    Resource r = new StringResource();
    graphViz.write(d1, r);
    System.out.println(r.readToString());


    graphViz.setVertexDecoder(DefaultEncodersDecoders.defaultVertexDecoder(String.class));
    Graph<String> g2 = graphViz.read(r);
    System.out.println(g2);

  }

  @Test
  public void testMergeFromEmpty() throws Exception {
    Graph<String> d3 = AdjacencyMatrix.directed();
    d1.merge(d3);
    assertEquals(3, d1.numberOfVertices());
  }

  @Test
  public void testMergeToEmpty() throws Exception {
    Graph<String> d3 = AdjacencyMatrix.directed();
    d3.merge(d1);
    assertFalse(d3.isEmpty());
    assertTrue(d3.containsEdge("A", "B"));
    assertEquals(3, d3.numberOfVertices());
  }


}//END OF GraphUtilsTest
