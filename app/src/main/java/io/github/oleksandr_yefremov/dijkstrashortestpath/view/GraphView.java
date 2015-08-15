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
    View inflatedView = inflater.inflate(R.layout.fragment_graph, container, false);
    CircularImageView vertButton = (CircularImageView) inflatedView.findViewById(R.id.vertButton);

    TextDrawable textAvatarDrawable = TextDrawable
      .builder()
      .beginConfig()
      .width(110)
      .height(110)
      .textColor(Color.BLACK)
//      .fontSize(40)
//      .toUpperCase()
//      .bold()
      .endConfig()
      .buildRect("1", Color.WHITE);

    vertButton.setImageDrawable(textAvatarDrawable);
    return inflatedView;
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
