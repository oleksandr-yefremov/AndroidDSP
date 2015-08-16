package io.github.oleksandr_yefremov.dijkstrashortestpath.presenter;

import android.support.annotation.NonNull;

import java.util.ArrayList;

import io.github.oleksandr_yefremov.dijkstrashortestpath.presenter.PresenterInterface.MainPresenterInterface;
import io.github.oleksandr_yefremov.dijkstrashortestpath.view.ViewInterface.ControlViewInterface;
import io.github.oleksandr_yefremov.dijkstrashortestpath.view.ViewInterface.GraphViewInterface;


/**
 * Created by Oleksandr Yefremov.
 */
public class MainPresenter implements MainPresenterInterface {
  private final GraphViewInterface graphView;
  private final ControlViewInterface controlView;

  private final ArrayList<Integer> selectedVerticesList = new ArrayList<>(2);

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
    graphView.updateVertices(count);
    graphView.updateEdges();
  }

  @Override
  public void vertexClicked(int index) {
    // if selected vertex is clicked, deselect it
    if (selectedVerticesList.contains(index)) {
      selectedVerticesList.remove((Integer)index);
      graphView.updateSelectedVertices(selectedVerticesList);
      return;
    }
    if (selectedVerticesList.size() == 2) {
      // when 2 vertices already selected, replace last selected with new one
      selectedVerticesList.remove(1);
    }
    selectedVerticesList.add(index);
    graphView.updateSelectedVertices(selectedVerticesList);
  }

  private void calculateShortestPath() {

  }
}
