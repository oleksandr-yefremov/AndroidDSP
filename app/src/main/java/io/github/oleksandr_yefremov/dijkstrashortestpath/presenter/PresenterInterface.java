package io.github.oleksandr_yefremov.dijkstrashortestpath.presenter;

/**
 * Created by Oleksandr Yefremov.
 */
public class PresenterInterface {
  public interface MainPresenterInterface {
    void minusButtonClicked();
    void plusButtonClicked();
    void vertexClicked(int index);
  }
}
