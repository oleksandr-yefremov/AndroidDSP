/**
 * Created by Oleksandr Yefremov.
 */
package io.github.oleksandr_yefremov.dijkstrashortestpath.interactor;

import java.util.List;

import io.github.oleksandr_yefremov.dijkstrashortestpath.entity.Graph;

/**
 * Contains business logic for graph manipulation as specified by a use case.
 */
public class GraphInteractor {

  private Graph graph;

  public GraphInteractor() {
//    graph = new Graph(4);
  }

  public Graph getGraph() {
    return graph;
  }

  public List<Integer> calculateShortestPath(int v1Index, int v2Index) {
    graph.findShortestPaths(v1Index);
//    graph.printPathString(v2Index);
    return graph.getPath(v2Index);
  }

  public void updateGraph(int numOfVertices) {
    graph = new Graph(numOfVertices);
  }
}
