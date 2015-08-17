/**
 * Created by Oleksandr Yefremov.
 */
package io.github.oleksandr_yefremov.dijkstrashortestpath.view.drawable;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Represents graph vertex view. Holds vertex index and responds to click events.
 */
public class VertexImageButton extends CircularImageView {
  /** Index of current vertex in graph (sometimes called <em>name</em>) */
  private int index = -1;

  public VertexImageButton(Context context) {
    super(context);
  }

  public VertexImageButton(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public VertexImageButton(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  public VertexImageButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
  }

  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
  }

}
