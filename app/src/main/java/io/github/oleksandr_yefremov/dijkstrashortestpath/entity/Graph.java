package io.github.oleksandr_yefremov.dijkstrashortestpath.entity;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public class Graph {
  private final Map<Integer, Vertex> graph; // mapping of vertex names to Vertex objects, built from a set of Edges

  /**
   * One edge of the graph (only used by Graph constructor)
   */
  public static class Edge {
    public final int v1, v2;
    public final int weight;

    public Edge(int v1, int v2, int weight) {
      this.v1 = v1;
      this.v2 = v2;
      this.weight = weight;
    }
  }

  /**
   * One vertex of the graph, complete with mappings to neighbouring vertices
   */
  public static class Vertex implements Comparable<Vertex> {
    public final Integer index;
    public int weight = Integer.MAX_VALUE; // MAX_VALUE assumed to be infinity
    public Vertex previous = null;
    public final Map<Vertex, Integer> neighbours = new HashMap<>();

    public Vertex(int index) {
      this.index = index;
    }

//    private void printPath() {
//      if (this == this.previous) {
//        System.out.printf("%s", this.index);
//      } else if (this.previous == null) {
//        System.out.printf("%s(unreached)", this.index);
//      } else {
//        this.previous.printPath();
//        System.out.printf(" -> %s(%d)", this.index, this.weight);
//      }
//    }

    private void printPath(List<Integer> vertices) {
      if (this == this.previous) {
        vertices.add(this.index);
      } else if (this.previous == null) {
        vertices.add(-1); // unreachable position
      } else {
        this.previous.printPath(vertices);
        vertices.add(this.index);
      }
    }

    @Override
    public int compareTo(@NonNull Vertex other) {
      return weight < other.weight ? -1 : (weight == other.weight ? 0 : 1);
    }
  }

  /**
   * Builds a graph from a set of edges
   */
  public Graph(Edge[] edges) {
    graph = new HashMap<>(edges.length);

    //one pass to find all vertices
    for (Edge e : edges) {
      if (!graph.containsKey(e.v1)) {
        graph.put(e.v1, new Vertex(e.v1));
      }
      if (!graph.containsKey(e.v2)) {
        graph.put(e.v2, new Vertex(e.v2));
      }
    }

    //another pass to set neighbouring vertices
    for (Edge e : edges) {
      graph.get(e.v1).neighbours.put(graph.get(e.v2), e.weight);
//      graph.get(e.v2).neighbours.put(graph.get(e.v1), e.weight); // also do this for an undirected graph
    }
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
   * Runs dijkstra using a specified source vertex
   */
  public void dijkstra(String startName) {
    if (!graph.containsKey(startName)) {
      System.err.printf("Graph doesn't contain start vertex \"%s\"\n", startName);
      return;
    }
    final Vertex source = graph.get(startName);
    TreeSet<Vertex> queue = new TreeSet<>();

    // set-up vertices
    for (Vertex v : graph.values()) {
      v.previous = v == source ? source : null;
      v.weight = v == source ? 0 : Integer.MAX_VALUE;
      queue.add(v);
    }

    dijkstra(queue);
  }

  /**
   * Runs dijkstra using a specified source vertex
   */
  public void dijkstra(int index) {
    if (!graph.containsKey(index)) {
      System.err.printf("Graph doesn't contain start vertex \"%s\"\n", index);
      return;
    }
    final Vertex source = graph.get(index);
    TreeSet<Vertex> queue = new TreeSet<>();

    // set-up vertices
    for (Vertex v : graph.values()) {
      v.previous = v == source ? source : null;
      v.weight = v == source ? 0 : Integer.MAX_VALUE;
      queue.add(v);
    }

    dijkstra(queue);
  }

  /**
   * Implementation of dijkstra's algorithm using a binary heap.
   */
  private void dijkstra(final TreeSet<Vertex> queue) {
    Vertex u, v;
    while (!queue.isEmpty()) {

      u = queue.pollFirst(); // vertex with smallest weight (first iteration will return source)
      if (u.weight == Integer.MAX_VALUE) {
        break; // we can ignore u (and any other remaining vertices) since they are unreachable
      }

      //look at distances to each neighbour
      for (Map.Entry<Vertex, Integer> a : u.neighbours.entrySet()) {
        v = a.getKey(); //the neighbour in this iteration

        final int alternateDist = u.weight + a.getValue();
        if (alternateDist < v.weight) { // shorter path to neighbour found
          queue.remove(v);
          v.weight = alternateDist;
          v.previous = u;
          queue.add(v);
        }
      }
    }
  }

  /**
   * Prints a path from the source to the specified vertex
   */
  public void printPath(String endName) {
    if (!graph.containsKey(endName)) {
      System.err.printf("Graph doesn't contain end vertex \"%s\"\n", endName);
      return;
    }

    ArrayList<Integer> vertices = new ArrayList<>();
    graph.get(endName).printPath(vertices);
    System.out.println();
  }

  /**
   * Prints a path from the source to the specified vertex
   */
//  public void printPath(int endIndex) {
//    if (!graph.containsKey(endIndex)) {
//      System.err.printf("Graph doesn't contain end vertex \"%s\"\n", endIndex);
//      return;
//    }
//
//    graph.get(endIndex).printPath();
//  }

  /**
   * Returns path (list of vertices) from the source to the specified vertex.
   */
  public List<Integer> printPath(int endIndex) {
    if (!graph.containsKey(endIndex)) {
      System.err.printf("Graph doesn't contain end vertex \"%s\"\n", endIndex);
    }

    ArrayList<Integer> vertices = new ArrayList<>();
    graph.get(endIndex).printPath(vertices);
    return vertices;
  }

  /**
   * Prints the path from the source to every vertex (output order is not guaranteed)
   */
//  public void printAllPaths() {
//    for (Vertex v : graph.values()) {
//      v.printPath();
//      System.out.println();
//    }
//  }
}