package utility;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import ir.yousefi.restaurant.R;

public class RatingBar extends View implements View.OnTouchListener {

  private static final String TAG = "RatingBar";

  private RatingBarListener listener;

  private int currentRating, maxRating;
  private boolean enable = true;
  private Bitmap defaultBitmap, selectedBitmap;
  private Paint defaultPaint = new Paint();
  private Rect bitmapRect;
  private Rect bitmapRectDest;

  private float bitmapRatio = 1f;
  private float globalWidth = 0;
  private float globalHeight = 0;
  private float bitmapWidth = 0;
  private float bitmapHeight = 0;
  private float drawableWidth = 0;
  private float drawableHeight = 0;
  private float drawablePadding = 0;

  /**
   * Constructor
   */
  public RatingBar(Context context) {
    super(context);
    init(context, null);
  }

  /**
   * Constructor
   */
  public RatingBar(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context, attrs);
  }

  /**
   * Constructor
   */
  public RatingBar(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context, attrs);
  }

  /**
   * Constructor
   */
  @TargetApi(21)
  public RatingBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init(context, attrs);
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

    int desiredWidth = (int) ((bitmapWidth + drawablePadding) * maxRating);
    int desiredHeight = (int) bitmapHeight;

    int widthMode = MeasureSpec.getMode(widthMeasureSpec);
    int widthSize = MeasureSpec.getSize(widthMeasureSpec);
    int heightMode = MeasureSpec.getMode(heightMeasureSpec);
    int heightSize = MeasureSpec.getSize(heightMeasureSpec);

    int width;
    int height;

    if (widthMode == MeasureSpec.EXACTLY) {
      width = widthSize;
    } else if (widthMode == MeasureSpec.AT_MOST) {
      width = Math.min(desiredWidth, widthSize);
    } else {
      width = desiredWidth;
    }

    if (heightMode == MeasureSpec.EXACTLY) {
      height = heightSize;
    } else if (heightMode == MeasureSpec.AT_MOST) {
      height = Math.min(desiredHeight, heightSize);
    } else {
      height = desiredHeight;
    }

    setMeasuredDimension(width, height);
  }


  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);

    globalWidth = w;
    globalHeight = h;

    if (drawableWidth != 0) {
      bitmapWidth = drawableWidth;
      bitmapHeight = bitmapWidth * bitmapRatio;
    } else if (drawableHeight != 0) {
      bitmapHeight = drawableHeight;
      bitmapWidth = bitmapHeight / bitmapRatio;
    } else {
      bitmapHeight = bitmapWidth * bitmapRatio;
    }

    if (bitmapHeight > globalHeight) {
      bitmapHeight = globalHeight;
      bitmapWidth = bitmapHeight / bitmapRatio;
    }

    if (bitmapWidth != 0 && bitmapHeight != 0) {
      invalidate();
    }
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);

    for (int i = 0; i < maxRating; i++) {

      float x = (globalWidth / 2) - (bitmapWidth * maxRating / 2) - 2 * drawablePadding + (bitmapWidth + drawablePadding) * i;
      float y = globalHeight / 2 - (bitmapHeight / 2);
      float width = bitmapWidth;
      float height = bitmapHeight;

      if (width < 0 || height < 0) {
        break;
      }

      bitmapRectDest.left = (int) x;
      bitmapRectDest.top = (int) y;
      bitmapRectDest.right = (int) (x + width);
      bitmapRectDest.bottom = (int) (y + height);

      canvas.drawBitmap(i < currentRating ? selectedBitmap : defaultBitmap,
        bitmapRect, bitmapRectDest, defaultPaint);
    }

  }

  @Override
  public boolean onTouch(View v, MotionEvent event) {
    manageTouch(event);
    return true;
  }

  /**
   * Init
   */
  private void init(Context context, AttributeSet attrs) {

    defaultPaint = new Paint(Paint.FILTER_BITMAP_FLAG);

    setOnTouchListener(this);
    bitmapRectDest = new Rect(0, 0, 0, 0);

    Resources res = getResources();
    int defaultDrawable, selectedDrawable;

    if (attrs != null) {
      TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RatingBar);
      currentRating = a.getInt(R.styleable.RatingBar_rating, 3);
      maxRating = a.getInt(R.styleable.RatingBar_maxRating, 5);
      defaultDrawable = a.getResourceId(R.styleable.RatingBar_defaultDrawable, R.drawable.star_empty);
      selectedDrawable = a.getResourceId(R.styleable.RatingBar_selectedDrawable, R.drawable.star);
      drawableWidth = a.getDimension(R.styleable.RatingBar_drawableWidth, 0);
      drawableHeight = a.getDimension(R.styleable.RatingBar_drawableHeight, 0);
      drawablePadding = a.getDimension(R.styleable.RatingBar_drawablePadding, 0);
      enable = a.getBoolean(R.styleable.RatingBar_enable, true);

      defaultBitmap = BitmapFactory.decodeResource(res, defaultDrawable);
      selectedBitmap = BitmapFactory.decodeResource(res, selectedDrawable);
      bitmapRect = new Rect(0, 0, defaultBitmap.getWidth(), defaultBitmap.getHeight());
      bitmapRatio = defaultBitmap.getWidth() * 1f / defaultBitmap.getHeight();
      bitmapWidth = drawableWidth != 0 ? drawableWidth : defaultBitmap.getWidth();
      bitmapHeight = drawableHeight > 0 ? drawableHeight : bitmapWidth * bitmapRatio;

      a.recycle();
    } else {
      currentRating = 3;
      maxRating = 5;
      defaultDrawable = R.drawable.star_empty;
      selectedDrawable = R.drawable.star;
      drawableWidth = 0;
      drawableHeight = 0;
      drawablePadding = 0;

      defaultBitmap = BitmapFactory.decodeResource(res, defaultDrawable);
      selectedBitmap = BitmapFactory.decodeResource(res, selectedDrawable);
      bitmapRect = new Rect(0, 0, defaultBitmap.getWidth(), defaultBitmap.getHeight());
      bitmapRatio = defaultBitmap.getWidth() * 1f / defaultBitmap.getHeight();

      bitmapWidth = drawableWidth != 0 ? drawableWidth : defaultBitmap.getWidth();
      bitmapHeight = drawableHeight > 0 ? drawableHeight : bitmapWidth * bitmapRatio;
    }

    invalidate();
  }

  /**
   * Manage touch
   */
  private void manageTouch(MotionEvent event) {

    if (!enable) {
      return;
    }

    float touchX = event.getX();
    for (int i = 0; i < maxRating; i++) {
      float x = (globalWidth / 2) - (bitmapWidth * maxRating / 2) - 2 * drawablePadding + (bitmapWidth + drawablePadding) * i;
      if ((i == 0 && touchX < (x + bitmapWidth / 5)) || touchX < x) {
        if (currentRating != i) {
          currentRating = i;
          if (listener != null) {
            listener.onValueChanged(currentRating);
          }
          invalidate();
        }
        break;
      } else if (i == maxRating - 1 && touchX > x ) {
        if (currentRating != (i + 1)) {
          currentRating = i + 1;
          if (listener != null) {
            listener.onValueChanged(currentRating);
          }
          invalidate();
        }
        break;
      }
    }
  }


  ////////////
  // PUBLIC //
  ////////////

  /**
   * Get rating
   */
  public int getRating() {
    return currentRating;
  }

  /**
   * Set the current rating
   */
  public void setRating(int currentRating) {
    if (currentRating >= 0 && currentRating <= maxRating) {
      this.currentRating = currentRating;
      invalidate();
    }
  }

  /**
   * Return if the rating bar is enable
   */
  public boolean isEnable() {
    return enable;
  }

  /**
   * Set the state of the rating bar
   */
  public void setEnable(boolean enable) {
    this.enable = enable;
  }

  /**
   * Get the listener
   */
  public RatingBarListener getListener() {
    return listener;
  }

  /**
   * Set the listener
   */
  public void setListener(RatingBarListener listener) {
    this.listener = listener;
  }

  /**
   * Interface
   */
  public interface RatingBarListener {
    void onValueChanged(int value);
  }

}
