package io.github.oleksandr_yefremov.dijkstrashortestpath.interactor;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.Arrays;
import java.util.List;

import io.github.oleksandr_yefremov.dijkstrashortestpath.BuildConfig;
import io.github.oleksandr_yefremov.dijkstrashortestpath.entity.Graph;
import io.github.oleksandr_yefremov.dijkstrashortestpath.entity.Graph.Edge;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

/**
 * Created by Oleksandr Yefremov.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class GraphInteractorTest {

  private Graph graph;
  private Edge[] edges = {
    new Edge(0, 1, 0),
    new Edge(0, 2, 2),
    new Edge(1, 3, 29),
    new Edge(3, 4, 4),
    new Edge(1, 5, 14),
    new Edge(2, 5, 1),
  };

  private List<Integer> shortestPath = Arrays.asList(1, 0, 2, 5);
  private List<Integer> validButLongerPath = Arrays.asList(1, 5);

  @Before
  public void setUp() throws Exception {
    graph = new Graph(edges);
  }

  @Test
  public void testCalculateShortestPath() throws Exception {
    graph.findShortestPaths(1);
    assertThat(graph.getPath(5), contains(shortestPath.toArray()));
  }

}