package io.github.oleksandr_yefremov.dijkstrashortestpath.view.layout;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Build.VERSION_CODES;
import android.support.v4.util.Pair;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Oleksandr Yefremov.
 */
public class GraphLayout extends FrameLayout {

  /**
   * Map[Pair[Vertex1, Vertex2], Weight]
   */
  private static final Map<Pair<Integer, Integer>, Float> GRAPH = new HashMap<>();
  private static final float Y_OFFSET = -5f;

  private HashMap<Integer, VertexPos> verticesPositionMap = new HashMap<>();
  private Paint linePaint, weightPaint;

  public GraphLayout(Context context) {
    this(context, null);
  }

  public GraphLayout(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public GraphLayout(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    setWillNotDraw(false);
    initPaint();
  }

  @TargetApi(VERSION_CODES.LOLLIPOP)
  public GraphLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    setWillNotDraw(false);
    initPaint();
  }

  private void initPaint() {
    // sample graph.
    // TODO: move into file or separate class
    GRAPH.put(new Pair<>(0, 1), 10f);
    GRAPH.put(new Pair<>(7, 3), 40f);
    GRAPH.put(new Pair<>(3, 0), 100f);
    GRAPH.put(new Pair<>(3, 2), 20f);
    GRAPH.put(new Pair<>(5, 8), 20f);
    GRAPH.put(new Pair<>(9, 0), 30f);
    GRAPH.put(new Pair<>(4, 6), 50f);
    GRAPH.put(new Pair<>(3, 8), 80f);
    GRAPH.put(new Pair<>(9, 4), 10f);
    GRAPH.put(new Pair<>(6, 1), 10f);

    linePaint = new Paint();
    linePaint.setColor(Color.BLACK);
    linePaint.setAntiAlias(true);
    linePaint.setStyle(Style.STROKE);

    weightPaint = new Paint(linePaint);
    weightPaint.setColor(Color.BLUE);
    weightPaint.setTextSize(24);
  }

  @Override
  protected void onLayout(boolean changed, int l, int t, int r, int b) {
    verticesPositionMap.clear();
    final int paddingLeft = getPaddingLeft();
    final int paddingTop = getPaddingTop();

    final int childCount = getChildCount();
    int centerX = getMeasuredWidth() / 2;
    int centerY = getMeasuredHeight() / 2;

    int radius = centerX - paddingLeft - getChildAt(0).getMeasuredWidth() / 2;

    float sweepAngle = (float) (2 * Math.PI / childCount);
    float currentAngle = 0;
    // start at 0º
    int x0 = centerX;
    int y0 = centerY - radius;
    // radius
    int rx = x0 - centerX;
    int ry = y0 - centerY;

    View child;
    for (int i = 0; i < childCount; ++i) {
      double cos = Math.cos(currentAngle);
      double sin = Math.sin(currentAngle);
      int x1 = (int) (centerX + rx * cos - ry * sin);
      int y1 = (int) (centerY + rx * sin + ry * cos);

      child = getChildAt(i);
      layoutView(i, child, x1 - child.getMeasuredWidth() / 2, y1 - child.getMeasuredHeight() / 2,
                 child.getMeasuredWidth(),
                 child.getMeasuredHeight());
      currentAngle += sweepAngle;
    }
  }

  private void layoutView(int vertIndex, View view, int left, int top, int width, int height) {
    MarginLayoutParams margins = (MarginLayoutParams) view.getLayoutParams();
    final int leftWithMargins = left + margins.leftMargin;
    final int topWithMargins = top + margins.topMargin;

    int l = leftWithMargins;
    int t = topWithMargins;
    int r = leftWithMargins + width;
    int b = topWithMargins + height;
    view.layout(l, t, r, b);

    // save vertex position
    verticesPositionMap.put(vertIndex, new VertexPos(l, t, r, b));
  }

  @Override
  protected void onDraw(Canvas canvas) {
    // draw vertices
    super.onDraw(canvas);

    // draw edges and weights
    for (Pair<Integer, Integer> pair : GRAPH.keySet()) {
      float weight = GRAPH.get(pair);
      drawEdge(canvas, pair.first, pair.second, weight);
    }
  }

  private void drawEdge(Canvas canvas, int v1Index, int v2Index, float weight) {
    VertexPos vertex1Pos = verticesPositionMap.get(v1Index);
    VertexPos vertex2Pos = verticesPositionMap.get(v2Index);

    if (vertex1Pos == null || vertex2Pos == null) {
//      throw new IllegalArgumentException();
      return;
    }

    // TODO: reuse Path, Point and RectF objects,
    // i.e. do not allocate them in onDraw()
    Path path = new Path();
    PointF v1, v2;

    if (vertex1Pos.r < vertex2Pos.r) {
      // draw left to right
      v1 = new PointF(vertex1Pos.r - (vertex1Pos.r - vertex1Pos.l) / 2,
                      vertex1Pos.b - (vertex1Pos.b - vertex1Pos.t) / 2);
      v2 = new PointF(vertex2Pos.r - (vertex2Pos.r - vertex2Pos.l) / 2,
                      vertex2Pos.b - (vertex2Pos.b - vertex2Pos.t) / 2);
    } else {
      // draw right to left
      v2 = new PointF(vertex1Pos.r - (vertex1Pos.r - vertex1Pos.l) / 2,
                      vertex1Pos.b - (vertex1Pos.b - vertex1Pos.t) / 2);
      v1 = new PointF(vertex2Pos.r - (vertex2Pos.r - vertex2Pos.l) / 2,
                      vertex2Pos.b - (vertex2Pos.b - vertex2Pos.t) / 2);
    }
    path.moveTo(v1.x, v1.y);
    path.lineTo(v2.x, v2.y);

    // draw edge
    canvas.drawPath(path, linePaint);

    RectF bounds = new RectF();
    path.computeBounds(bounds, false);
    float weightPos = bounds.width() > bounds.height()
      ? bounds.width() / 2
      : bounds.height() / 3;

    // draw weight
    canvas.drawTextOnPath(String.valueOf((int)weight), path, weightPos, Y_OFFSET, weightPaint);
  }

  @Override
  public boolean onInterceptTouchEvent(MotionEvent event) {
    return false;
  }

  /**
   * Absolute vertex position in parent container
   */
  private static class VertexPos {
    public int l, t, r, b;

    public VertexPos(int l, int t, int r, int b) {
      this.l = l;
      this.t = t;
      this.r = r;
      this.b = b;
    }
  }

}
