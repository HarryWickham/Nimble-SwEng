package uk.ac.york.nimblefitness.MediaHandlers.Video;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

public class CustomVideoView extends VideoView {

    protected int _overrideWidth = 480;
    protected int _overrideHeight = 360;

    public CustomVideoView(Context context) {
        super(context);
    }

    public CustomVideoView(Context context, AttributeSet set) {
        super(context, set);
    }

    public void resizeVideo(int width, int height) {
        _overrideHeight = height;
        _overrideWidth = width;
        // Not necessarily useful but safe to do so.
        getHolder().setFixedSize(width, height);
        requestLayout();
        invalidate(); // Very important, so that onMeasure will be triggered

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        setMeasuredDimension(_overrideWidth, _overrideHeight);
    }

}