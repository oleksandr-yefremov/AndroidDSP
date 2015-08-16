package io.github.oleksandr_yefremov.dijkstrashortestpath.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.amulyakhare.textdrawable.TextDrawable;

import io.github.oleksandr_yefremov.dijkstrashortestpath.R;
import io.github.oleksandr_yefremov.dijkstrashortestpath.presenter.MainPresenter;
import io.github.oleksandr_yefremov.dijkstrashortestpath.view.ViewInterface.GraphViewInterface;
import io.github.oleksandr_yefremov.dijkstrashortestpath.view.drawable.VertexImageButton;

/**
 * A placeholder fragment containing a simple view.
 */
public class GraphView extends Fragment implements GraphViewInterface {

  private View[] vertices;
  private MainPresenter presenter;
  private ViewGroup containerLayout;

  public void setPresenter(MainPresenter presenter) {
    this.presenter = presenter;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    containerLayout = (ViewGroup) inflater.inflate(R.layout.fragment_graph, container, false);

    createVertices(4);
    return containerLayout;
  }

  private void createVertices(int number) {
    containerLayout.removeAllViewsInLayout();
    for (int i = 0; i < number; ++i) {
      createVertex(i, containerLayout);
    }
  }

  private void createVertex(int number, ViewGroup containerLayout) {
    final VertexImageButton vertButton = (VertexImageButton) getLayoutInflater(null)
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
      .buildRect(String.valueOf(number), Color.TRANSPARENT);

    vertButton.setImageDrawable(textAvatarDrawable);
    vertButton.setIndex(number);
    vertButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Log.d("GRAPH", "vertButton.getIndex : " + vertButton.getIndex());
      }
    });
    containerLayout.addView(vertButton);
  }

  @Override
  public void hideEdges() {

  }

  @Override
  public void updateEdges() {

  }

  @Override
  public void updateVertices(int count) {
    createVertices(count);
  }

}
