package com.amalbit.trail.marker

import android.support.constraint.ConstraintLayout
import android.view.View.generateViewId
import android.widget.FrameLayout

class MarkerHolder(val anchorView: AnchorView) {
    val layoutHolder: FrameLayout

    init {
        anchorView.id = generateViewId()
        layoutHolder = FrameLayout(anchorView.context)
    }

    fun getLayoutParams(): ConstraintLayout.LayoutParams {
        val lp = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
        lp.bottomToBottom = anchorView.id
        lp.topToTop = anchorView.id
        lp.leftToLeft = anchorView.id
        lp.rightToRight = anchorView.id
        return lp
    }
}