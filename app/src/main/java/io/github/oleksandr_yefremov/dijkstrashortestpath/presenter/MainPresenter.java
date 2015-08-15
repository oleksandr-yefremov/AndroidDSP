package io.github.oleksandr_yefremov.dijkstrashortestpath.presenter;

import android.support.annotation.NonNull;

import io.github.oleksandr_yefremov.dijkstrashortestpath.presenter.PresenterInterface.MainPresenterInterface;
import io.github.oleksandr_yefremov.dijkstrashortestpath.view.ViewInterface.ControlViewInterface;
import io.github.oleksandr_yefremov.dijkstrashortestpath.view.ViewInterface.GraphViewInterface;


/**
 * Created by Oleksandr Yefremov.
 */
public class MainPresenter implements MainPresenterInterface {
  private final GraphViewInterface graphView;
  private final ControlViewInterface controlView;

  public MainPresenter(@NonNull GraphViewInterface graphView, @NonNull ControlViewInterface controlView) {
    this.graphView = graphView;
    this.controlView = controlView;
  }

  @Override
  public void minusButtonClicked() {
    updateGraph(false);
  }

  @Override
  public void plusButtonClicked() {
    updateGraph(true);
  }

  private void updateGraph(boolean increment) {
    int count = controlView.updateVertCount(increment);
    graphView.hideEdges();
    graphView.updateVertices(count);
    graphView.updateEdges();
  }
}
