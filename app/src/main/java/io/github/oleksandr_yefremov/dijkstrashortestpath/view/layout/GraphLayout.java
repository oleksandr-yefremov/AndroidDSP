package io.github.oleksandr_yefremov.dijkstrashortestpath.view.layout;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build.VERSION_CODES;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by Oleksandr Yefremov.
 */
public class GraphLayout extends FrameLayout {

  public GraphLayout(Context context) {
    super(context);
  }

  public GraphLayout(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public GraphLayout(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @TargetApi(VERSION_CODES.LOLLIPOP)
  public GraphLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
  }

  @Override
  protected void onLayout(boolean changed, int l, int t, int r, int b) {
    final int paddingLeft = getPaddingLeft();
    final int paddingTop = getPaddingTop();

    final int childCount = getChildCount();
    int centerX = getMeasuredWidth() / 2;
    int centerY = getMeasuredHeight() / 2;

    int radius = centerX - paddingLeft - getChildAt(0).getMeasuredWidth()/2;

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
      layoutView(child, x1 - child.getMeasuredWidth() / 2, y1 - child.getMeasuredHeight() / 2,
                 child.getMeasuredWidth(),
                 child.getMeasuredHeight());
      angle += fraction;
    }
  }

  private void layoutView(View view, int left, int top, int width, int height) {
    MarginLayoutParams margins = (MarginLayoutParams) view.getLayoutParams();
    final int leftWithMargins = left + margins.leftMargin;
    final int topWithMargins = top + margins.topMargin;

    view.layout(leftWithMargins, topWithMargins,
                leftWithMargins + width, topWithMargins + height);
  }

}
