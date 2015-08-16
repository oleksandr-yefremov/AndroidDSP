package io.github.oleksandr_yefremov.dijkstrashortestpath.entity;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

/**
 *  The <tt>EdgeWeightedGraph</tt> class represents an edge-weighted
 *  graph of vertices named 0 through <em>V</em> - 1, where each
 *  undirected edge is of type {@link Edge} and has a real-valued weight.
 *  It supports the following two primary operations: add an edge to the graph,
 *  iterate over all of the edges incident to a vertex. It also provides
 *  methods for returning the number of vertices <em>V</em> and the number
 *  of edges <em>E</em>. Parallel edges and self-loops are permitted.
 *  <p>
 *  This implementation uses an adjacency-lists representation, which
 *  is a vertex-indexed array of @link{Bag} objects.
 *  All operations take constant time (in the worst case) except
 *  iterating over the edges incident to a given vertex, which takes
 *  time proportional to the number of such edges.
 *  <p>
 *  For additional documentation,
 *  see <a href="http://algs4.cs.princeton.edu/43mst">Section 4.3</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public class EdgeWeightedGraph {
  private static final String NEWLINE = System.getProperty("line.separator");

  private final int V;
  private int E;
  private ArrayList<Edge>[] adj;

  /**
   * Initializes an empty edge-weighted graph with <tt>V</tt> vertices and 0 edges.
   *
   * @param  V the number of vertices
   * @throws IllegalArgumentException if <tt>V</tt> < 0
   */
  public EdgeWeightedGraph(int V) {
    if (V < 0) throw new IllegalArgumentException("Number of vertices must be nonnegative");
    this.V = V;
    this.E = 0;
    adj = (ArrayList<Edge>[]) new ArrayList[V];
    for (int v = 0; v < V; v++) {
      adj[v] = new ArrayList<>();
    }
  }

  /**
   * Initializes a random edge-weighted graph with <tt>V</tt> vertices and <em>E</em> edges.
   *
   * @param  V the number of vertices
   * @param  E the number of edges
   * @throws IllegalArgumentException if <tt>V</tt> < 0
   * @throws IllegalArgumentException if <tt>E</tt> < 0
   */
  public EdgeWeightedGraph(int V, int E) {
    this(V);
    if (E < 0) throw new IllegalArgumentException("Number of edges must be nonnegative");
    for (int i = 0; i < E; i++) {
      int v = new Random().nextInt(V);
      int w = new Random().nextInt(V);
      double weight = Math.round(100 * new Random().nextInt(100)) / 100.0;
      Edge e = new Edge(v, w, weight);
      addEdge(e);
    }
  }

  /**
   * Initializes a new edge-weighted graph that is a deep copy of <tt>G</tt>.
   *
   * @param  G the edge-weighted graph to copy
   */
  public EdgeWeightedGraph(EdgeWeightedGraph G) {
    this(G.V());
    this.E = G.E();
    for (int v = 0; v < G.V(); v++) {
      // reverse so that adjacency list is in same order as original
      Stack<Edge> reverse = new Stack<Edge>();
      for (Edge e : G.adj[v]) {
        reverse.push(e);
      }
      for (Edge e : reverse) {
        adj[v].add(e);
      }
    }
  }


  /**
   * Returns the number of vertices in this edge-weighted graph.
   *
   * @return the number of vertices in this edge-weighted graph
   */
  public int V() {
    return V;
  }

  /**
   * Returns the number of edges in this edge-weighted graph.
   *
   * @return the number of edges in this edge-weighted graph
   */
  public int E() {
    return E;
  }

  // throw an IndexOutOfBoundsException unless 0 <= v < V
  private void validateVertex(int v) {
    if (v < 0 || v >= V)
      throw new IndexOutOfBoundsException("vertex " + v + " is not between 0 and " + (V-1));
  }

  /**
   * Adds the undirected edge <tt>e</tt> to this edge-weighted graph.
   *
   * @param  e the edge
   * @throws IndexOutOfBoundsException unless both endpoints are between 0 and V-1
   */
  public void addEdge(Edge e) {
    int v = e.either();
    int w = e.other(v);
    validateVertex(v);
    validateVertex(w);
    adj[v].add(e);
    adj[w].add(e);
    E++;
  }

  /**
   * Returns the edges incident on vertex <tt>v</tt>.
   *
   * @param  v the vertex
   * @return the edges incident on vertex <tt>v</tt> as an Iterable
   * @throws IndexOutOfBoundsException unless 0 <= v < V
   */
  public Iterable<Edge> adj(int v) {
    validateVertex(v);
    return adj[v];
  }

  /**
   * Returns the degree of vertex <tt>v</tt>.
   *
   * @param  v the vertex
   * @return the degree of vertex <tt>v</tt>
   * @throws IndexOutOfBoundsException unless 0 <= v < V
   */
  public int degree(int v) {
    validateVertex(v);
    return adj[v].size();
  }

  /**
   * Returns all edges in this edge-weighted graph.
   * To iterate over the edges in this edge-weighted graph, use foreach notation:
   * <tt>for (Edge e : G.edges())</tt>.
   *
   * @return all edges in this edge-weighted graph, as an iterable
   */
  public Iterable<Edge> edges() {
    ArrayList<Edge> list = new ArrayList<Edge>();
    for (int v = 0; v < V; v++) {
      int selfLoops = 0;
      for (Edge e : adj(v)) {
        if (e.other(v) > v) {
          list.add(e);
        }
        // only add one copy of each self loop (self loops will be consecutive)
        else if (e.other(v) == v) {
          if (selfLoops % 2 == 0) list.add(e);
          selfLoops++;
        }
      }
    }
    return list;
  }

  /**
   * Returns a string representation of the edge-weighted graph.
   * This method takes time proportional to <em>E</em> + <em>V</em>.
   *
   * @return the number of vertices <em>V</em>, followed by the number of edges <em>E</em>,
   *         followed by the <em>V</em> adjacency lists of edges
   */
  public String toString() {
    StringBuilder s = new StringBuilder();
    s.append(V).append(" ").append(E).append(NEWLINE);
    for (int v = 0; v < V; v++) {
      s.append(v).append(": ");
      for (Edge e : adj[v]) {
        s.append(e).append("  ");
      }
      s.append(NEWLINE);
    }
    return s.toString();
  }

}


