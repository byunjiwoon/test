package com.si.simembers.Widget;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

public class VerticalOnlyNestedScrollView extends NestedScrollView {

    private static final String TAG = "VOnlyNestedScrollView";
    private AppBarLayout abl;


    public VerticalOnlyNestedScrollView(Context context) {
        super(context);
    }

    public VerticalOnlyNestedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VerticalOnlyNestedScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        if (!(ev.getAction()== MotionEvent.ACTION_DOWN)&& ev.getHistorySize() > 0) {
            isVerticalScrolling(true);
            float yVector = ev.getY() - ev.getHistoricalY(0);
            float xVector = ev.getX() - ev.getHistoricalX(0);
            Log.d(TAG, "onInterceptTouchEvent: " + xVector + "--" + yVector);

            return Math.abs(yVector) <= Math.abs(xVector) || super.onTouchEvent(ev);
        }
        isVerticalScrolling(false);
        return super.onTouchEvent(ev);
    }

    public void setAppbarLayer(AppBarLayout abl){
        this.abl = abl;
    }

    private void isVerticalScrolling(boolean isVerticalScrolling){
        if(abl!=null){
            abl.setEnabled(isVerticalScrolling);
        }
    }
}
