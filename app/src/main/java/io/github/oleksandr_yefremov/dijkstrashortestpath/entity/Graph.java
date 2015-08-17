package io.github.oleksandr_yefremov.dijkstrashortestpath.entity;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.TreeSet;

public class Graph {
  private final static String TAG = "GRAPH";

  private final TreeMap<Integer, Vertex> graph;

  public static class Edge {
    public final int vertex1, vertex2;
    public final int weight;

    public Edge(int vertex1, int vertex2, int weight) {
      this.vertex1 = vertex1;
      this.vertex2 = vertex2;
      this.weight = weight;
    }
  }

  /**
   * Vertex. Has mappings to neighbouring vertices.
   */
  public static class Vertex implements Comparable<Vertex> {
    public final Integer index;
    public int weight = Integer.MAX_VALUE;

    public final Map<Vertex, Integer> neighbours = new HashMap<>();
    public Vertex previous = null;

    public Vertex(int index) {
      this.index = index;
    }

    private void getPath(List<Integer> vertices) {
      if (this == this.previous) {
        vertices.add(this.index);
      } else if (this.previous == null) {
        vertices.add(-1); // unreachable vertex
      } else {
        this.previous.getPath(vertices);
        vertices.add(this.index);
      }
    }

    private void printPath() {
      if (this == this.previous) {
        Log.d(TAG, " " + this.index);
      } else if (this.previous == null) {
        Log.d(TAG, "%" + this.index + "(unreachable)");
      } else {
        this.previous.printPath();
        Log.d(TAG, "-> %" + this.index + "(" + this.weight + ")");
      }
    }

    @Override
    public int compareTo(@NonNull Vertex other) {
      return weight < other.weight ? -1 : (weight == other.weight ? 0 : 1);
    }
  }

  /**
   * Builds graph from randomly generated edges with given number of vertices
   */
  public Graph(int numOfVertices) {
    this(generateEdges(numOfVertices));
  }

  /**
   * Builds a graph from a set of edges
   */
  public Graph(Edge[] edges) {
    graph = new TreeMap<>();

    // First pass to find all vertices
    for (Edge e : edges) {
      if (!graph.containsKey(e.vertex1)) {
        graph.put(e.vertex1, new Vertex(e.vertex1));
      }
      if (!graph.containsKey(e.vertex2)) {
        graph.put(e.vertex2, new Vertex(e.vertex2));
      }
    }

    // Second pass to set neighbouring vertices
    for (Edge e : edges) {
      graph.get(e.vertex1).neighbours.put(graph.get(e.vertex2), e.weight);
      graph.get(e.vertex2).neighbours.put(graph.get(e.vertex1), e.weight); // undirected graph
    }
  }

  private static Edge[] generateEdges(int numOfVertices) {
    Edge[] edges = new Edge[numOfVertices];
    Edge edge;
    for (int i = 0; i < numOfVertices; ++i) {
      edge = new Edge(i,
                      new Random().nextInt(numOfVertices),
                      new Random().nextInt(30));
      edges[i] = edge;
    }
    return edges;
  }

  public List<Vertex> getAllVertices() {
    ArrayList<Vertex> result = new ArrayList<>();
    for (int index : graph.keySet()) {
      Vertex v = graph.get(index);
      result.add(v);
    }
    return result;
  }

  /**
   * Runs DSP using a specified source vertex
   */
  public void findShortestPaths(int sourceIndex) {
    if (!graph.containsKey(sourceIndex)) {
      Log.e(TAG, "Graph doesn't contain start vertex : " + sourceIndex);
      return;
    }
    final Vertex source = graph.get(sourceIndex);
    TreeSet<Vertex> queue = new TreeSet<>();

    // setup vertices
    for (Vertex v : graph.values()) {
      v.previous = v == source ? source : null;
      v.weight = v == source ? 0 : Integer.MAX_VALUE;
      queue.add(v);
    }

    findShortestPaths(queue);
  }

  /**
   * DSP using a binary heap.
   */
  private void findShortestPaths(final TreeSet<Vertex> queue) {
    Vertex vertex, neighbour;
    while (!queue.isEmpty()) {

      // get vertex with smallest weight (first iteration will return source)
      vertex = queue.pollFirst();
      if (vertex.weight == Integer.MAX_VALUE) {
        break;
      }

      // go through distances to each neighbour
      for (Map.Entry<Vertex, Integer> a : vertex.neighbours.entrySet()) {
        neighbour = a.getKey();

        final int alternateDist = vertex.weight + a.getValue();
        if (alternateDist < neighbour.weight) { // shorter path to neighbour found
          queue.remove(neighbour);
          neighbour.weight = alternateDist;
          neighbour.previous = vertex;
          queue.add(neighbour);
        }
      }
    }
  }

  /**
   * Returns path (list of vertices) from the source to the specified vertex.
   */
  public List<Integer> getPath(int endIndex) {
    if (!graph.containsKey(endIndex)) {
      Log.e(TAG, "Graph doesn't contain end vertex : " + endIndex);
    }

    ArrayList<Integer> vertices = new ArrayList<>();
    graph.get(endIndex).getPath(vertices);
    return vertices;
  }

  /**
   * Prints a path from the source to the specified vertex to Log.d
   */
  public List<Integer> printPathString(int endIndex) {
    if (!graph.containsKey(endIndex)) {
      Log.e(TAG, "Graph doesn't contain end vertex : " + endIndex);
    }

    ArrayList<Integer> vertices = new ArrayList<>();
    graph.get(endIndex).printPath();
    return vertices;
  }

}