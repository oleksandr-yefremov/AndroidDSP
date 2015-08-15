package io.github.oleksandr_yefremov.dijkstrashortestpath.view;

/**
 * Created by Oleksandr Yefremov.
 */
public class ViewInterface {
  public interface GraphViewInterface {
    public void hideEdges();

    public void updateEdges();

    public void updateVertices(int count);
  }

  public interface ControlViewInterface {
    public int updateVertCount(boolean incr);
  }
}