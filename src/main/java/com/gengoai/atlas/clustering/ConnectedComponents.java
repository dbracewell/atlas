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
import com.gengoai.atlas.search.BreadthFirst;
import com.gengoai.guava.common.base.Preconditions;
import com.gengoai.guava.common.collect.Iterators;
import com.gengoai.guava.common.collect.Lists;
import com.gengoai.guava.common.collect.Sets;

import java.util.List;
import java.util.Set;

/**
 * Connected components clustering
 *
 * @param <V> the vertex type
 */
public class ConnectedComponents<V> implements Clusterer<V> {

  @Override
  public List<Set<V>> cluster(Graph<V> g) {
    Preconditions.checkNotNull(g);
    List<Set<V>> rval = Lists.newArrayList();
    Set<V> seen = Sets.newHashSet();

    for (V v : g.vertices()) {
      if (seen.contains(v)) continue;
      Set<V> cluster = Sets.newHashSet();
      Iterators.addAll(cluster, new BreadthFirst<>(g).iterator(v));
      rval.add(cluster);
      seen.addAll(cluster);
    }

    return rval;
  }

}//END OF ConnectedComponents