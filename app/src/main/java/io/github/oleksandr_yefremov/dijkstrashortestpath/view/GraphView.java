package io.github.oleksandr_yefremov.dijkstrashortestpath.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amulyakhare.textdrawable.TextDrawable;

import io.github.oleksandr_yefremov.dijkstrashortestpath.R;
import io.github.oleksandr_yefremov.dijkstrashortestpath.presenter.MainPresenter;
import io.github.oleksandr_yefremov.dijkstrashortestpath.view.ViewInterface.GraphViewInterface;
import io.github.oleksandr_yefremov.dijkstrashortestpath.view.drawable.CircularImageView;

/**
 * A placeholder fragment containing a simple view.
 */
public class GraphView extends Fragment implements GraphViewInterface {

  private View[] vertices;
  private MainPresenter presenter;

  public void setPresenter(MainPresenter presenter) {
    this.presenter = presenter;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    ViewGroup containerLayout = (ViewGroup) inflater.inflate(R.layout.fragment_graph, container, false);

    createVertex("1", containerLayout);
    createVertex("2", containerLayout);
    createVertex("3", containerLayout);
    createVertex("4", containerLayout);
    return containerLayout;
  }

  private void createVertex(String number, ViewGroup containerLayout) {
//    CircularImageView vertButton = (CircularImageView) containerLayout.findViewById(R.id.vertButton);

    CircularImageView vertButton = (CircularImageView) getLayoutInflater(null).inflate(R.layout.view_vertex, containerLayout, false);

    TextDrawable textAvatarDrawable = TextDrawable
      .builder()
      .beginConfig()
      .width(40)
      .height(40)
      .textColor(Color.BLACK)
//      .bold()
      .endConfig()
      .buildRect(number, Color.WHITE);

    vertButton.setImageDrawable(textAvatarDrawable);
    containerLayout.addView(vertButton);
  }

  @Override
  public void hideEdges() {

  }

  @Override
  public void updateEdges() {

  }

  @Override
  public void updateVertices() {

  }

}
