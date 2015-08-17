/**
 * Created by Oleksandr Yefremov.
 */
package io.github.oleksandr_yefremov.dijkstrashortestpath.presenter;

/**
 * Holder for all presenter interfaces.
 */
public class PresenterInterface {

  public interface MainPresenterInterface {
    void minusButtonClicked();

    void plusButtonClicked();

    void vertexClicked(int index);
  }
}
