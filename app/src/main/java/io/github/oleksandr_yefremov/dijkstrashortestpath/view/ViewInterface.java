/**
 * Created by Oleksandr Yefremov.
 */
package io.github.oleksandr_yefremov.dijkstrashortestpath.view;

import java.util.List;

public class ViewInterface {
  public interface GraphViewInterface {
    void updateEdges();

    void updateVertices(int count);

    void updateSelectedVertices(List<Integer> selectedVertices);
  }

  public interface ControlViewInterface {
    int updateVertCount(boolean incr);
  }
}