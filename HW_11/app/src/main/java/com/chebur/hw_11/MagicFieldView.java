package com.chebur.hw_11;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;

import java.util.Date;


/**
 * Created by nick on 02.12.2015.
 */
public class MagicFieldView extends View {

    private Handler _hdl;
    private Runnable _rnbl;
    private long _lastUpdate;
    private long _lastUIUpdate;
    private MagicDisk _md;

    public MagicFieldView(Context context) {
        super(context);

        Init();
    }

    public MagicFieldView(Context context, AttributeSet attrs) {
        super(context, attrs);

        Init();
    }

    public MagicFieldView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        Init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MagicFieldView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        Init();
    }

    private void Init() {
        _md = new MagicDisk();
        _lastUpdate = -1;
        _lastUIUpdate = -1;

        _hdl = new Handler();
        _rnbl = new Runnable() {
            @Override
            public void run() {
                Date currDate = new Date();
                long msTime = currDate.getTime();
                long updateDelta;
                if (_lastUpdate == -1) {
                    updateDelta = 0;
                }
                else {
                    updateDelta = msTime - _lastUpdate;
                }

                _lastUpdate = msTime;
                MagicFieldView.this.Update(updateDelta);
                if (_lastUIUpdate == -1) {
                    MagicFieldView.this.invalidate();
                    _lastUIUpdate = msTime;
                }
                else {
                    if (msTime - _lastUIUpdate >= 10) {
                        MagicFieldView.this.invalidate();
                        _lastUIUpdate = msTime;
                    }
                }
                _hdl.postDelayed(this, 0);
            }
        };
        _hdl.post(_rnbl);
    }

    private void Update(long deltaTime) {
        if (_md != null) {
            _md.Update(deltaTime);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (_md != null) {
            _md.Render(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return _md.OnTouch(event);
    }
}
