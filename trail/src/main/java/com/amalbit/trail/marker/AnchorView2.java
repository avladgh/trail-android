package com.amalbit.trail.marker;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;

public class AnchorView2 extends View {
    private FrameLayout layoutHolder;
    private MarkerHolder markerHolder;

    public AnchorView2(Context context) {
        super(context);
        init();
    }

    public AnchorView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public AnchorView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        setId(View.generateViewId());
    }

    class MarkerHolder {
        final FrameLayout layoutHolder = new FrameLayout(getContext());

        public MarkerHolder() {
            layoutHolder.setId(generateViewId());
            layoutHolder.setLayoutParams(getLeftLayoutParams());
            CheckBox button = new CheckBox(getContext());
            button.setText("Hello world");
            layoutHolder.addView(button);
        }

        private ConstraintLayout.LayoutParams getCenterLayoutParam() {
            ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.bottomToBottom = getId();
            lp.topToTop = getId();
            lp.leftToLeft = getId();
            lp.rightToRight = getId();
            return lp;
        }

        private ConstraintLayout.LayoutParams getLeftLayoutParams() {
            ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.bottomToBottom = getId();
            lp.topToTop = getId();
            lp.rightToLeft = getId();
            return lp;
        }

        private ConstraintLayout.LayoutParams getRightLayoutParams() {
            ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.bottomToBottom = getId();
            lp.topToTop = getId();
            lp.leftToRight = getId();
            return lp;
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        markerHolder = new MarkerHolder();
        ((ViewGroup) getParent()).addView(markerHolder.layoutHolder);
    }

    public void setAbsolutePosition(int x, int y) {
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) getLayoutParams();
        layoutParams.leftMargin = x;
        layoutParams.topMargin = y;
        setLayoutParams(layoutParams);
        requestLayout();

        long parentWidth = ((ViewGroup) getParent()).getWidth();
        if (x > parentWidth / 2) {
            markerHolder.layoutHolder.setLayoutParams(markerHolder.getLeftLayoutParams());
        } else {
            markerHolder.layoutHolder.setLayoutParams(markerHolder.getRightLayoutParams());
        }
    }
}
