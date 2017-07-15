package yazdany.saeid.view.rippleview;

/**
 * Created by OneDeveloper on 7/15/2017.
 */

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/**
 * Created by OneDeveloper on 7/15/2017.
 */

public class RippleRelativeLayout extends RelativeLayout {

  private Paint paint;
  private int DEFAULT_RADIUS = 200;
  private int DEFAULT_RIPPLE_COUNT = 6;
  private float DEFAULT_RIPPLE_SCALE = 6.0f;
  private int DEFAULT_RIPPLE_DURATION = 3000;
  private int FILL = 0;
  private int STROKE = 1;
  private int FILL_AND_STROKE = 2;
  private int RIPPLE_DELAY = 500;
  private boolean isAnimationPlaying = false;
  private ArrayList<Animator> animators = new ArrayList<>();
  private ArrayList<RippleView> rippleViews = new ArrayList<>();
  private AnimatorSet animatorSet;
  private int color;
  private LayoutParams params;
  private boolean autoStart;
  private float strokeWidth;
  private int rippleCount;
  private int rippleDelay;
  private int rippleAnimationDuration;
  private float rippleRadius;
  private float rippleScale;
  private int rippleType;

  public RippleRelativeLayout(Context context) {
    super(context);
  }

  public RippleRelativeLayout(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context, attrs);
  }

  public RippleRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context, attrs);
  }

  private void init(Context context, AttributeSet attributeSet) {

    TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.RippleView);
    autoStart = typedArray.getBoolean(R.styleable.RippleView_autoStart, false);
    color = typedArray.getColor(R.styleable.RippleView_rippleColor, getResources().getColor(R.color.rippleColor));
    strokeWidth = typedArray.getDimension(R.styleable.RippleView_rippleStokeWidth, getResources().getDimension(R.dimen.rippleStrokeWidth));
    rippleCount = typedArray.getInt(R.styleable.RippleView_rippleCount, DEFAULT_RIPPLE_COUNT);
    rippleDelay = typedArray.getInt(R.styleable.RippleView_rippleDelay, RIPPLE_DELAY);
    rippleAnimationDuration = typedArray.getInt(R.styleable.RippleView_rippleAnimateDuration, DEFAULT_RIPPLE_DURATION);
    rippleRadius = typedArray.getDimension(R.styleable.RippleView_rippleRadius, getResources().getDimension(R.dimen.rippleRadius));
    rippleScale = typedArray.getFloat(R.styleable.RippleView_rippleScale, DEFAULT_RIPPLE_SCALE);
    rippleType = typedArray.getInt(R.styleable.RippleView_rippleType, FILL);
    typedArray.recycle();

    paint = new Paint();
    paint.setAntiAlias(true);
    paint.setColor(getColor());
    switch (rippleType) {
      case 0:
        paint.setStrokeWidth(0);
        strokeWidth = 0;
        paint.setStyle(Paint.Style.FILL);
        break;
      case 1:
        paint.setStrokeWidth(strokeWidth);
        paint.setStyle(Paint.Style.STROKE);
        break;
      case 2:
        paint.setStrokeWidth(strokeWidth);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        break;
    }
    if (rippleDelay == RIPPLE_DELAY) {
      rippleDelay = rippleAnimationDuration / rippleCount;
    }
    params = new LayoutParams((int) (rippleRadius + strokeWidth), (int) (rippleRadius + strokeWidth));
    params.addRule(CENTER_IN_PARENT, TRUE);

    animatorSet = new AnimatorSet();
    animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());

    for (int i = 0; i < rippleCount; i++) {
      RippleView rippleView = new RippleView(getContext());
      addView(rippleView, params);
      rippleViews.add(rippleView);
      ObjectAnimator xScaleAnimator = ObjectAnimator.ofFloat(rippleView, "scaleX", 1.0f, rippleScale);
      xScaleAnimator.setRepeatCount(ObjectAnimator.INFINITE);
      xScaleAnimator.setRepeatMode(ObjectAnimator.RESTART);
      if (rippleDelay != RIPPLE_DELAY) {
        xScaleAnimator.setStartDelay(i * rippleDelay);
      }
      xScaleAnimator.setDuration(rippleAnimationDuration);
      animators.add(xScaleAnimator);

      ObjectAnimator yScaleAnimator = ObjectAnimator.ofFloat(rippleView, "scaleY", 1.0f, rippleScale);
      yScaleAnimator.setRepeatCount(ObjectAnimator.INFINITE);
      yScaleAnimator.setRepeatMode(ObjectAnimator.RESTART);
      if (rippleDelay != RIPPLE_DELAY) {
        xScaleAnimator.setStartDelay(i * rippleDelay);
      }
      yScaleAnimator.setDuration(rippleAnimationDuration);
      animators.add(yScaleAnimator);

      ObjectAnimator alpha = ObjectAnimator.ofFloat(rippleView, "alpha", 1.0f, 0);
      alpha.setRepeatCount(ObjectAnimator.INFINITE);
      alpha.setRepeatMode(ObjectAnimator.RESTART);
      if (rippleDelay != RIPPLE_DELAY) {
        xScaleAnimator.setStartDelay(i * rippleDelay);
      }
      alpha.setDuration(rippleAnimationDuration);
      animators.add(alpha);
    }

    animatorSet.playTogether(animators);
    if (autoStart) {
      automaticStartAnimation();
    }
  }

  private void automaticStartAnimation() {
    for (RippleView rippleView : rippleViews) {
      rippleView.setVisibility(VISIBLE);
    }
    animatorSet.start();
    isAnimationPlaying = true;
  }


  public int getColor() {
    return color;
  }

  public void setColor(int color) {
    this.color = color;
    paint.setColor(color);
  }

  public void playRipple() {
    if (!isAnimationPlaying) {
      for (RippleView rippleView : rippleViews) {
        rippleView.setVisibility(VISIBLE);
      }
      animatorSet.start();
      isAnimationPlaying = true;
    }
  }

  public void stopRipple() {
    if (isAnimationPlaying) {
      for (RippleView rippleView : rippleViews) {
        rippleView.setVisibility(INVISIBLE);
      }
      animatorSet.end();
      isAnimationPlaying = false;
    }
  }

  private class RippleView extends View {

    public RippleView(Context context) {
      super(context);
      this.setVisibility(INVISIBLE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
      super.onDraw(canvas);
      int radius = (Math.min(getWidth(), getHeight())) / 2;
      canvas.drawCircle(radius, radius, radius, paint);
    }
  }
}
