/**
 * Created by Oleksandr Yefremov.
 */
package io.github.oleksandr_yefremov.dijkstrashortestpath.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import io.github.oleksandr_yefremov.dijkstrashortestpath.R;
import io.github.oleksandr_yefremov.dijkstrashortestpath.presenter.PresenterInterface.MainPresenterInterface;
import io.github.oleksandr_yefremov.dijkstrashortestpath.view.ViewInterface.ControlViewInterface;

/**
 * Control view: number of graph vertices and incr/decr buttons.
 */
public class ControlView extends Fragment implements ControlViewInterface {

  private static final int MIN_VERTICES = 4;
  private static final int MAX_VERTICES = 10;

  private MainPresenterInterface presenter;
  private TextView vertCounter;

  public void setPresenter(MainPresenterInterface presenter) {
    this.presenter = presenter;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View inflatedView = inflater.inflate(R.layout.fragment_control, container, false);
    Button minusButton = (Button) inflatedView.findViewById(R.id.minusButton);
    Button plusButton = (Button) inflatedView.findViewById(R.id.plusButton);
    vertCounter = (TextView) inflatedView.findViewById(R.id.vertCounter);

    minusButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        presenter.minusButtonClicked();
      }
    });
    plusButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        presenter.plusButtonClicked();
      }
    });
    return inflatedView;
  }

  @Override
  public int updateVertCount(boolean increment) {
    int count = Integer.parseInt(vertCounter.getText().toString());
    if (increment && count < MAX_VERTICES) {
      count++;
    } else if (!increment && count > MIN_VERTICES) {
      count--;
    }
    vertCounter.setText(String.valueOf(count));
    return count;
  }
}
