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
import com.gengoai.guava.common.collect.Sets;
import com.gengoai.atlas.Graph;

import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * @author David B. Bracewell
 */
public class DepthFirst<V> extends VisitorSearcher<V> {
  private static final long serialVersionUID = -9189433692614954370L;

  /**
   * The default constructor
   *
   * @param graph The graph to visit
   */
  public DepthFirst(Graph<V> graph) {
    super(graph);
  }

  @Override
  public Iterator<V> iterator(V startingVertex) {
    return new DepthFirstIterator<>(graph, Preconditions.checkNotNull(startingVertex));
  }


  private static class DepthFirstIterator<V> implements Iterator<V> {

    private final Graph<V> graph;
    private final Deque<V> stack = Lists.newLinkedList();
    private final Set<V> visited = Sets.newHashSet();

    private DepthFirstIterator(Graph<V> graph, V startingVertex) {
      this.graph = graph;
      this.stack.push(startingVertex);
    }

    @Override
    public boolean hasNext() {
      return !stack.isEmpty();
    }

    private V advance() {
      V top = stack.pop();
      for (V v2 : graph.getSuccessors(top)) {
        if (!visited.contains(v2) && !stack.contains(v2)) {
          stack.push(v2);
        }
      }
      return top;
    }

    @Override
    public V next() {
      if (stack.isEmpty()) {
        throw new NoSuchElementException();
      }
      V popped = advance();
      visited.add(popped);
      return popped;
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException();
    }
  }

}//END OF DepthFirstVisitor
