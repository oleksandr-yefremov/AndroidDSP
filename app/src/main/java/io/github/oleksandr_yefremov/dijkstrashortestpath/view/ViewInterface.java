/**
 * Created by Oleksandr Yefremov.
 */
package io.github.oleksandr_yefremov.dijkstrashortestpath.view;

import java.util.List;

import io.github.oleksandr_yefremov.dijkstrashortestpath.entity.Graph;

public class ViewInterface {
  public interface GraphViewInterface {
    void updateEdges();
    void updateGraph(Graph graph);

//    void updateVertices(int count);

    void updateSelectedVertices(List<Integer> selectedVertices);
  }

  public interface ControlViewInterface {
    int updateVertCount(boolean incr);
  }
}