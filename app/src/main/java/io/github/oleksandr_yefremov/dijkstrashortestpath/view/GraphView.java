package io.github.oleksandr_yefremov.dijkstrashortestpath.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.amulyakhare.textdrawable.TextDrawable;

import java.util.List;

import io.github.oleksandr_yefremov.dijkstrashortestpath.R;
import io.github.oleksandr_yefremov.dijkstrashortestpath.entity.Graph;
import io.github.oleksandr_yefremov.dijkstrashortestpath.entity.Graph.Vertex;
import io.github.oleksandr_yefremov.dijkstrashortestpath.presenter.PresenterInterface.MainPresenterInterface;
import io.github.oleksandr_yefremov.dijkstrashortestpath.view.ViewInterface.GraphViewInterface;
import io.github.oleksandr_yefremov.dijkstrashortestpath.view.drawable.VertexImageButton;
import io.github.oleksandr_yefremov.dijkstrashortestpath.view.layout.GraphLayout;

/**
 * Graph view. Shows clickable vertices and edges between them
 * (edges can be highlighted with showPath() method)
 */
public class GraphView extends Fragment implements GraphViewInterface {

  private MainPresenterInterface presenter;
  private GraphLayout graphLayout;

  public void setPresenter(@NonNull MainPresenterInterface presenter) {
    this.presenter = presenter;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    graphLayout = (GraphLayout) inflater.inflate(R.layout.fragment_graph, container, false);

    return graphLayout;
  }

  private void createVertex(Vertex vertex, ViewGroup containerLayout) {
    final VertexImageButton vertexButton = (VertexImageButton) getLayoutInflater(null)
      .inflate(R.layout.view_vertex, containerLayout, false);

    int size = getResources().getDimensionPixelSize(R.dimen.Default_Vertex_Size);
    TextDrawable textAvatarDrawable = TextDrawable
      .builder()
      .beginConfig()
      .width(size)
      .height(size)
      .textColor(Color.BLACK)
//      .bold()
      .endConfig()
      .buildRect(String.valueOf(vertex.index), Color.TRANSPARENT);

    vertexButton.setImageDrawable(textAvatarDrawable);
    vertexButton.setIndex(vertex.index);
    vertexButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        presenter.vertexClicked(vertexButton.getIndex());
      }
    });
    containerLayout.addView(vertexButton);
  }

  @Override
  public void updateGraph(Graph graph) {
    createVertices(graph);
    graphLayout.updateGraph(graph);
  }

  private void createVertices(Graph graph) {
    graphLayout.removeAllViewsInLayout();
    List<Vertex> vertices = graph.getAllVertices();
    for (int i = 0; i < vertices.size(); ++i) {
      createVertex(vertices.get(i), graphLayout);
    }
  }

  @Override
  public void updateSelectedVertices(List<Integer> selectedVertices) {
    View child;
    // deselect all views
    for (int i = 0; i < graphLayout.getChildCount(); ++i) {
      child = graphLayout.getChildAt(i);
      child.setSelected(false);
    }
    // update selection
    for (int selectedIndex : selectedVertices) {
      child = graphLayout.getChildAt(selectedIndex);
      child.setSelected(true);
    }
  }

  @Override
  public void showPath(List<Integer> vertices) {
    graphLayout.showPath(vertices);
  }

  @Override
  public void hidePath() {
    graphLayout.showPath(null);
  }

}
