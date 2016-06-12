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

import com.davidbracewell.SystemInfo;
import com.davidbracewell.atlas.Edge;
import com.davidbracewell.atlas.Graph;
import com.davidbracewell.atlas.Vertex;
import com.davidbracewell.collection.HashMapIndex;
import com.davidbracewell.collection.Index;
import com.davidbracewell.config.Config;
import com.davidbracewell.io.Resources;
import com.davidbracewell.io.resource.Resource;
import com.davidbracewell.logging.Logger;
import com.davidbracewell.string.StringUtils;
import com.google.common.base.Charsets;
import com.google.common.base.Throwables;
import com.google.common.collect.Multimap;
import lombok.Builder;
import lombok.NonNull;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Map;

/**
 * <p>An implementation of a <code>GraphRender</code> and <code>GraphWriter</code> for GraphViz</p>
 *
 * @param <V> the vertex type
 */
public class GraphViz<V> implements GraphWriter<V>, GraphRenderer<V> {

  enum Format {
    JPG("jpg"),
    PNG("png"),
    SVG("svg");

    private final String extension;

    Format(String extension) {
      this.extension = extension;
    }

    public String getExtension() {
      return extension;
    }

  }

  private static final Logger log = Logger.getLogger(GraphViz.class);
  private static String DOT = "/usr/bin/dot";
  private EdgeEncoder<V> edgeEncoder;
  private VertexEncoder<V> vertexEncoder;
  private Format format = Format.PNG;

  /**
   * Instantiates a new GraphViz writer/renderer.
   */
  public GraphViz() {
    this(null, null);
  }

  /**
   * Instantiates a new GraphViz writer/renderer.
   *
   * @param vertexEncoder the vertex encoder
   */
  public GraphViz(VertexEncoder<V> vertexEncoder) {
    this(vertexEncoder, null);
  }

  /**
   * Instantiates a new GraphViz writer/renderer.
   *
   * @param edgeEncoder the edge encoder
   */
  public GraphViz(EdgeEncoder<V> edgeEncoder) {
    this(null, edgeEncoder);
  }

  /**
   * Instantiates a new GraphViz writer/renderer.
   *
   * @param vertexEncoder the vertex encoder
   * @param edgeEncoder   the edge encoder
   */
  public GraphViz(VertexEncoder<V> vertexEncoder, EdgeEncoder<V> edgeEncoder) {
    this(vertexEncoder, edgeEncoder, Format.PNG);
  }

  /**
   * Instantiates a new GraphViz writer/renderer.
   *
   * @param vertexEncoder the vertex encoder
   * @param edgeEncoder   the edge encoder
   * @param format        the format
   */
  @Builder
  public GraphViz(VertexEncoder<V> vertexEncoder, EdgeEncoder<V> edgeEncoder, Format format) {
    setEdgeEncoder(edgeEncoder);
    setVertexEncoder(vertexEncoder);
    setFormat(format);
    String configName = "graphviz.dot." + SystemInfo.OS_NAME;
    log.fine("Looking for dot in config {0}", configName);
    if (Config.hasProperty(configName)) {
      DOT = Config.get(configName).asString();
      log.fine("Setting DOT location to {0} from config {1}", DOT, configName);
    }
  }

  @Override
  public void render(@NonNull Graph<V> graph, @NonNull Resource location, @NonNull Multimap<String, String> parameters) throws IOException {
    Resource tempLoc = Resources.temporaryFile();
    tempLoc.deleteOnExit();
    write(graph, tempLoc, parameters);


    Runtime rt = Runtime.getRuntime();
    String[] args = {DOT, "-T" + format.getExtension(), tempLoc.asFile().get().getAbsolutePath(), "-o", location.asFile().get().getAbsolutePath()};
    Process p = rt.exec(args);

    try {
      p.waitFor();
    } catch (InterruptedException e) {
      throw Throwables.propagate(e);
    }
  }

  private String escape(String input) {
    if (input == null || input.length() == 0) {
      return "\"" + StringUtils.EMPTY + "\"";
    }
    if (input.length() == 1) {
      return "\"" + input + "\"";
    }
    if (input.charAt(0) == '"' && input.charAt(input.length() - 1) == '"') {
      return input;
    }
    return "\"" + input + "\"";
  }

  @Override
  public void setVertexEncoder(VertexEncoder<V> serializer) {
    if (serializer == null) {
      this.vertexEncoder = DefaultEncodersDecoders.defaultVertexEncoder();
    } else {
      this.vertexEncoder = serializer;
    }
  }

  @Override
  public void setEdgeEncoder(EdgeEncoder<V> serializer) {
    if (serializer == null) {
      this.edgeEncoder = DefaultEncodersDecoders.defaultEdgeEncoder();
    } else {
      this.edgeEncoder = serializer;
    }
  }

  @Override
  public void write(@NonNull Graph<V> graph, @NonNull Resource location, @NonNull Multimap<String, String> parameters) throws IOException {
    location.setCharset(Charsets.UTF_8);
    try (BufferedWriter writer = new BufferedWriter(location.writer())) {

      //Write the header
      if (graph.isDirected()) {
        writer.write("digraph G {");
      } else {
        writer.write("graph G {");
      }
      writer.newLine();

      writer.write("rankdir = BT;\n");
      if (parameters.containsKey("graph")) {
        writer.write("graph [");
        for (String property : parameters.get("graph")) {
          writer.write(property);
        }
        writer.write("];");
        writer.newLine();
      }

      Index<V> vertexIndex = new HashMapIndex<>(graph.vertices());

      for (V vertex : graph.vertices()) {
        Vertex vertexProps = vertexEncoder.encode(vertex);
        writer.write(Integer.toString(vertexIndex.indexOf(vertex)));
        writer.write(" [");
        writer.write("label=" + escape(vertexProps.getLabel()) + " ");
        for (Map.Entry<String, String> entry : vertexProps.getProperties().entrySet()) {
          writer.write(entry.getKey() + "=" + escape(entry.getValue()) + " ");
        }
        writer.write("];");
        writer.newLine();
      }

      for (Edge<V> edge : graph.edges()) {
        writer.write(Integer.toString(vertexIndex.indexOf(edge.getFirstVertex())));
        if (graph.isDirected()) {
          writer.write(" -> ");
        } else {
          writer.write(" -- ");
        }
        writer.write(Integer.toString(vertexIndex.indexOf(edge.getSecondVertex())));

        Map<String, String> edgeProps = edgeEncoder.encode(edge);
        if (edgeProps != null && !edgeProps.isEmpty()) {
          writer.write(" [");
          for (Map.Entry<String, String> entry : edgeProps.entrySet()) {
            writer.write(entry.getKey() + "=" + escape(entry.getValue()) + " ");
          }
          writer.write("];");
        }

        writer.newLine();
      }

      //write the footer
      writer.write("}");
      writer.newLine();
    }
  }

  /**
   * Sets format.
   *
   * @param format the format
   */
  public void setFormat(Format format) {
    this.format = format == null ? Format.PNG : format;
  }


}//END OF GraphViz
