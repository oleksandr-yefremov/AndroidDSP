package io.github.oleksandr_yefremov.dijkstrashortestpath.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import io.github.oleksandr_yefremov.dijkstrashortestpath.R;
import io.github.oleksandr_yefremov.dijkstrashortestpath.interactor.GraphInteractor;
import io.github.oleksandr_yefremov.dijkstrashortestpath.presenter.MainPresenter;
import io.github.oleksandr_yefremov.dijkstrashortestpath.view.ControlView;
import io.github.oleksandr_yefremov.dijkstrashortestpath.view.GraphView;

/**
 * Activity is made as thin as possible:
 * - user interaction is handled by Views and propagated to Presenter(s)
 * - business logic is spread across Interactors
 */
public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    GraphView graphViewFragment = (GraphView) getSupportFragmentManager().findFragmentById(R.id.graphViewFragment);
    ControlView controlViewFragment = (ControlView) getSupportFragmentManager().findFragmentById(R.id.controlViewFragment);
    GraphInteractor graphInteractor = new GraphInteractor();

    MainPresenter presenter = new MainPresenter(graphViewFragment, controlViewFragment, graphInteractor);
    controlViewFragment.setPresenter(presenter);
    graphViewFragment.setPresenter(presenter);
  }

}
