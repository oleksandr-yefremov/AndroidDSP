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
 * TODO: javadoc
 */
public class MainPresenter implements MainPresenterInterface {
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

  private void updateGraph(boolean increment) {
    int count = controlView.updateVertCount(increment);
    graphInteractor.updateGraph(count);
    graphView.updateGraph(graphInteractor.getGraph());
    graphView.updateEdges();
  }

  @Override
  public void vertexClicked(int index) {
    // if selected vertex is clicked, deselect it
    if (selectedVerticesList.contains(index)) {
      selectedVerticesList.remove((Integer) index);
      graphView.updateSelectedVertices(selectedVerticesList);
      return;
    }
    if (selectedVerticesList.size() == 2) {
      // when 2 vertices are already selected, replace last selected with new one
      selectedVerticesList.remove(1);
    }
    selectedVerticesList.add(index);
    graphView.updateSelectedVertices(selectedVerticesList);
    if (selectedVerticesList.size() == 2) {
      graphInteractor.calculateShortestPath(selectedVerticesList.get(0), selectedVerticesList.get(1));
    }
  }


}
