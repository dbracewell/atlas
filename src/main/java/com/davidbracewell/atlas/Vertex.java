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

import com.davidbracewell.string.StringUtils;
import com.google.common.base.Preconditions;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.Singular;

import java.io.Serializable;
import java.util.Map;

/**
 * @author David B. Bracewell
 */
@Data
public class Vertex implements Serializable {
  private static final long serialVersionUID = 1L;
  private final String label;
  private final Map<String, String> properties;

  @Builder
  public Vertex(String label, @Singular @NonNull Map<String, String> properties) {
    Preconditions.checkArgument(!StringUtils.isNullOrBlank(label), "label cannot be null");
    this.label = label;
    this.properties = properties;
  }

}//END OF Vertex
