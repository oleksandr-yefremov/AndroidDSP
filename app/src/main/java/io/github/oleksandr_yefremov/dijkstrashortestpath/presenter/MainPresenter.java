/**
 * Created by Oleksandr Yefremov.
 */
package io.github.oleksandr_yefremov.dijkstrashortestpath.presenter;

import android.support.annotation.NonNull;

import java.util.ArrayList;

import io.github.oleksandr_yefremov.dijkstrashortestpath.interactor.GraphInteractor;
import io.github.oleksandr_yefremov.dijkstrashortestpath.presenter.PresenterInterface.MainPresenterInterface;
import io.github.oleksandr_yefremov.dijkstrashortestpath.view.ViewInterface.ControlViewInterface;
import io.github.oleksandr_yefremov.dijkstrashortestpath.view.ViewInterface.GraphViewInterface;

/**
 * MV<strong>P</strong> : <em>Presenter</em> contains view logic for preparing content for display
 * (as received from the <em>Interactor</em>) and for reacting to user inputs
 * (by requesting new data from the <em>Interactor</em>).
 */
public class MainPresenter implements MainPresenterInterface {

  // Explicit dependencies. Set via setter injection
  private final GraphViewInterface graphView;
  private final ControlViewInterface controlView;
  private final GraphInteractor graphInteractor;

  private final ArrayList<Integer> selectedVerticesList = new ArrayList<>(2);

  public MainPresenter(@NonNull GraphViewInterface graphView, @NonNull ControlViewInterface controlView,
                       @NonNull GraphInteractor graphInteractor) {
    this.graphView = graphView;
    this.controlView = controlView;
    this.graphInteractor = graphInteractor;
  }

  @Override
  public void minusButtonClicked() {
    updateGraph(false);
  }

  @Override
  public void plusButtonClicked() {
    updateGraph(true);
  }

  /**
   * Update graph by adding/substituting one vertex
   * @param increment
   */
  private void updateGraph(boolean increment) {
    selectedVerticesList.clear();
    int count = controlView.updateVertCount(increment);
    graphInteractor.updateGraph(count);
    graphView.updateGraph(graphInteractor.getGraph());
  }

  @Override
  public void vertexClicked(int index) {
    // if selected vertex is clicked, deselect it
    if (selectedVerticesList.contains(index)) {
      selectedVerticesList.remove((Integer) index);
      graphView.updateSelectedVertices(selectedVerticesList);
      graphView.hidePath();
      return;
    }
    if (selectedVerticesList.size() == 2) {
      // when 2 vertices are already selected, replace last selected with new one
      selectedVerticesList.remove(1);
    }
    selectedVerticesList.add(index);
    graphView.updateSelectedVertices(selectedVerticesList);
    // when 2 vertices are selected, calculate and show shortest path between them
    if (selectedVerticesList.size() == 2) {
      graphView.showPath(graphInteractor.calculateShortestPath(
        selectedVerticesList.get(0), selectedVerticesList.get(1)));
    }
  }

}
