package com.chebur.hw_11;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by nick on 03.12.2015.
 */
public class MagicDisk {
    double _offsetAngle;
    double _angleSpeed;
    double _angleAccel;
    double _defAccel = 180;
    List<Double> _lastMoveSpeed;
    boolean _isLastEventInside;
    PointF _ovalCenter;
    PointF _prevTouchPosition;
    double _ovalRadius;
    int _section_size = 45;
    int _section0_start = 0;
    int _section2_start = 90;
    int _section4_start = 180;
    int _section6_start = 270;
    int _section1_start = 45;
    int _section3_start = 135;
    int _section5_start = 225;
    int _section7_start = 315;
    long _lastTouchEventUpdate;

    Paint _pnt;
    int _colorBlack;
    int _colorYellow;

    public MagicDisk () {
        _offsetAngle = 0f;
        _angleSpeed = 0f;
        _angleAccel = 0f;

        _pnt = new Paint();
        _pnt.setAntiAlias(true);
        _colorYellow = Color.rgb(255, 239, 135);
        _colorBlack = Color.BLACK;

        _lastTouchEventUpdate = -1;

        _ovalCenter = new PointF();
        _prevTouchPosition = new PointF();
        _lastMoveSpeed = new ArrayList<>();
    }

    public void Update(long deltaTime) {
        boolean initSpeedSign = _angleSpeed > 0;

        _offsetAngle = _offsetAngle + _angleSpeed * (deltaTime / 1000f);
        _angleSpeed = _angleSpeed + _angleAccel * (deltaTime / 1000f);

        boolean currSpeedSign = _angleSpeed > 0;

        if (initSpeedSign ^ currSpeedSign) {
            _angleSpeed = 0f;
            _angleAccel = 0f;
        }

        if (_offsetAngle > 360) {
            _offsetAngle -= 360;
        }
        else if (_offsetAngle < 0) {
            _offsetAngle += 360;
        }
    }

    public void Render(Canvas can) {
        int size = Math.min(can.getHeight(), can.getWidth());
        RectF rf = new RectF(0, 0, size, size);

        _pnt.setColor(_colorBlack);
        can.drawArc(rf, _section0_start + (int) _offsetAngle, _section_size, true, _pnt);
        can.drawArc(rf, _section2_start + (int)_offsetAngle, _section_size, true, _pnt);
        can.drawArc(rf, _section4_start + (int)_offsetAngle, _section_size, true, _pnt);
        can.drawArc(rf, _section6_start + (int)_offsetAngle, _section_size, true, _pnt);

        _pnt.setColor(_colorYellow);
        can.drawArc(rf, _section1_start + (int) _offsetAngle, _section_size, true, _pnt);
        can.drawArc(rf, _section3_start + (int)_offsetAngle, _section_size, true, _pnt);
        can.drawArc(rf, _section5_start + (int)_offsetAngle, _section_size, true, _pnt);
        can.drawArc(rf, _section7_start + (int)_offsetAngle, _section_size, true, _pnt);

        _ovalCenter.set(rf.centerX(), rf.centerY());
        _ovalRadius = rf.width() / 2f;
    }

    private void Break() {
        _angleAccel = 0f;
        _angleSpeed = 0f;
    }

    private void SetSpeed(double speed) {
        _angleSpeed = speed;

        _angleAccel = _defAccel * (_angleSpeed > 0 ? -1 : 1);
    }

    private void Rotate(double angle) {
        _offsetAngle += angle;
    }

    void onRotateDisk(double angle, long updateTime) {
        Rotate(angle);
        double delta = (updateTime - _lastTouchEventUpdate) / 1000f;
        _lastMoveSpeed.add(angle / delta);
    }

    void onReleaseDisk() {
        double summ = 0;
        while (_lastMoveSpeed.size() > 4) {
            _lastMoveSpeed.remove(0);
        }
        for(Double ss : _lastMoveSpeed) {
            summ += ss;
        }
        if (_lastMoveSpeed.size() > 0) {
            summ /= _lastMoveSpeed.size();
        }

        SetSpeed(summ);
        _lastMoveSpeed.clear();
    }

    public boolean OnTouch(MotionEvent event) {
        long updateTime = new Date().getTime();
        PointF touchPosition = new PointF(event.getX(), event.getY());
        if (_lastTouchEventUpdate > 0) {
            int action = event.getAction();
            double dist = Math.sqrt(Math.pow(_ovalCenter.x - event.getX(), 2) +
                    Math.pow(_ovalCenter.y - event.getY(), 2));
            boolean isInside = dist < _ovalRadius;
            boolean isLeft = !isInside && _isLastEventInside;
            boolean isEntered = isInside && !_isLastEventInside;
            _isLastEventInside = isInside;

            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    if (isInside) {
                        Break();
                    }
                    break;

                case MotionEvent.ACTION_UP:
                    if (isInside) {
                        onReleaseDisk();
                    }
                    break;

                case MotionEvent.ACTION_MOVE:
                    Log.d("tag", "isEntered:" + isEntered);
                    Log.d("tag", "isLeft:" + isLeft);
                    Log.d("tag", "isInside:" + isInside);
                    if (isEntered) {
                        Break();
                    }
                    else if (isLeft) {
                        onReleaseDisk();
                    }
                    else if (isInside) {
                        double angle = getMoveAngle(_prevTouchPosition,
                                new PointF(event.getX(), event.getY()), _ovalCenter);
                        onRotateDisk(angle, new Date().getTime());
                    }
                    break;
            }
        }
        _lastTouchEventUpdate = updateTime;
        _prevTouchPosition.set(touchPosition.x, touchPosition.y);

        return true;
    }

    double getMoveAngle(PointF prevP, PointF currP, PointF centreP) {
        double v1x = prevP.x - centreP.x;
        double v1y = prevP.y - centreP.y;
        double v2x = currP.x - centreP.x;
        double v2y = currP.y - centreP.x;

        double v1angle = Math.atan(v1y / v1x);
        double v2angle = Math.atan(v2y / v2x);

        double delta = v2angle - v1angle;
        if (delta > Math.PI/2) {
            delta = delta - Math.PI;
        }

        if (delta < -Math.PI/2) {
            delta = delta+Math.PI;
        }

        return Math.toDegrees(delta);
    }
}
