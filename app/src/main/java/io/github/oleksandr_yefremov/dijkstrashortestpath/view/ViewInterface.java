/**
 * Created by Oleksandr Yefremov.
 */
package io.github.oleksandr_yefremov.dijkstrashortestpath.view;

import java.util.List;

import io.github.oleksandr_yefremov.dijkstrashortestpath.entity.Graph;

public class ViewInterface {
  public interface GraphViewInterface {
    void updateGraph(Graph graph);

    void updateSelectedVertices(List<Integer> selectedVertices);

    void showPath(List<Integer> vertices);

    void hidePath();
  }

  public interface ControlViewInterface {
    int updateVertCount(boolean increment);
  }
}