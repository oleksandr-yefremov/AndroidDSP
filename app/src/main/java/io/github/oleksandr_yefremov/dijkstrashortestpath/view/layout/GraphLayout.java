package io.github.oleksandr_yefremov.dijkstrashortestpath.view.layout;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build.VERSION_CODES;
import android.support.v4.util.Pair;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Oleksandr Yefremov.
 */
public class GraphLayout extends FrameLayout {

  private static ArrayList<Pair<Integer, Integer>> VE = new ArrayList<>();
  private HashMap<Integer, VertexPos> vPos = new HashMap<>();
  private static Paint linePaint = new Paint();

  static {
    VE.add(new Pair<>(0, 1));
    VE.add(new Pair<>(1, 2));
    VE.add(new Pair<>(3, 0));

    linePaint.setColor(Color.BLACK);
  }

  public GraphLayout(Context context) {
    super(context);
    setWillNotDraw(false);
  }

  public GraphLayout(Context context, AttributeSet attrs) {
    super(context, attrs);
    setWillNotDraw(false);
  }

  public GraphLayout(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    setWillNotDraw(false);
  }

  @TargetApi(VERSION_CODES.LOLLIPOP)
  public GraphLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    setWillNotDraw(false);
  }

  @Override
  protected void onLayout(boolean changed, int l, int t, int r, int b) {
    final int paddingLeft = getPaddingLeft();
    final int paddingTop = getPaddingTop();

    final int childCount = getChildCount();
    int centerX = getMeasuredWidth() / 2;
    int centerY = getMeasuredHeight() / 2;

    int radius = centerX - paddingLeft - getChildAt(0).getMeasuredWidth() / 2;

    float fraction = (float) (2 * Math.PI / childCount);
    float angle = 0;
    int x0 = centerX;
    int y0 = centerY - radius;
    int rx = x0 - centerX;
    int ry = y0 - centerY;
    for (int i = 0; i < childCount; ++i) {
      // start at 0º
      double cos = Math.cos(angle);
      double sin = Math.sin(angle);
      int x1 = (int) (centerX + rx * cos - ry * sin);
      int y1 = (int) (centerY + rx * sin + ry * cos);

      View child = getChildAt(i);
      layoutView(i, child, x1 - child.getMeasuredWidth() / 2, y1 - child.getMeasuredHeight() / 2,
                 child.getMeasuredWidth(),
                 child.getMeasuredHeight());
      angle += fraction;
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

    vPos.put(vertIndex, new VertexPos(l, t, r, b));
  }

  @Override
  protected void onDraw(Canvas canvas) {
    // draw vertices
    super.onDraw(canvas);

    // draw edges
    for (Pair<Integer, Integer> pair : VE) {
      drawEdge(canvas, pair.first, pair.second);
    }
  }

  private void drawEdge(Canvas canvas, int v1Index, int v2Index) {
    VertexPos vertex1Pos = vPos.get(v1Index);
    VertexPos vertex2Pos = vPos.get(v2Index);

    if (vertex1Pos == null || vertex2Pos == null) {
//      throw new IllegalArgumentException();
      return;
    }

    canvas.drawLine(vertex1Pos.r - (vertex1Pos.r - vertex1Pos.l)/2,
                    vertex1Pos.b - (vertex1Pos.b - vertex1Pos.t)/2,
                    vertex2Pos.r - (vertex2Pos.r - vertex2Pos.l)/2,
                    vertex2Pos.b - (vertex2Pos.b - vertex2Pos.t)/2,
                    linePaint);
  }

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
