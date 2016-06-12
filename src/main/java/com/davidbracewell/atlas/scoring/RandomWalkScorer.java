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
import com.davidbracewell.atlas.algorithms.RandomWalk;
import com.davidbracewell.collection.Counter;
import com.davidbracewell.collection.HashMapCounter;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Random;

/**
 * @author David B. Bracewell
 */
public class RandomWalkScorer<V> extends AbstractVertexScorer<V> {

  private static final long serialVersionUID = -15522220356650235L;

  private final int numberOfIterations;
  private final int numberOfSteps;

  /**
   * Default Constructor
   *
   * @param numberOfIterations Number of iterations to run
   * @param numberOfSteps      Number of steps to take on each random walk
   */
  public RandomWalkScorer(int numberOfIterations, int numberOfSteps) {
    this.numberOfIterations = numberOfIterations;
    this.numberOfSteps = numberOfSteps;
  }

  @Override
  public Counter<V> score(Graph<V> g) {
    Preconditions.checkNotNull(g, "The graph must not be null.");
    Counter<V> scores = new HashMapCounter<>();
    Random random = new Random();
    RandomWalk<V> randomWalk = new RandomWalk<>(g);
    List<V> vertices = Lists.newArrayList(g.vertices());
    for (int i = 0; i < numberOfIterations; i++) {
      V startingPoint = vertices.get(random.nextInt(vertices.size()));
      scores.increment(randomWalk.walk(startingPoint, numberOfSteps));
    }

    return scores;
  }

}//END OF RandomWalkScorer
