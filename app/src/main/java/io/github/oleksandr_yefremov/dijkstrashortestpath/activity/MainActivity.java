package io.github.oleksandr_yefremov.dijkstrashortestpath.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import io.github.oleksandr_yefremov.dijkstrashortestpath.R;
import io.github.oleksandr_yefremov.dijkstrashortestpath.interactor.GraphInteractor;
import io.github.oleksandr_yefremov.dijkstrashortestpath.presenter.MainPresenter;
import io.github.oleksandr_yefremov.dijkstrashortestpath.view.ControlView;
import io.github.oleksandr_yefremov.dijkstrashortestpath.view.GraphView;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
//    ViewGroup contentLayout = (ViewGroup) findViewById(R.id.contentLayout);

    GraphView graphViewFragment = (GraphView) getSupportFragmentManager().findFragmentById(R.id.graphViewFragment);
    ControlView controlViewFragment = (ControlView) getSupportFragmentManager().findFragmentById(R.id.controlViewFragment);
    GraphInteractor graphInteractor = new GraphInteractor();

    MainPresenter presenter = new MainPresenter(graphViewFragment, controlViewFragment, graphInteractor);
    controlViewFragment.setPresenter(presenter);
    graphViewFragment.setPresenter(presenter);
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }
}
