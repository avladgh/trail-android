package com.amalbit.trail.marker

import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.amalbit.trail.marker.AnchorView.MarkerHolder as MarkerHolder1

class AnchorView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    val markerHolder: MarkerHolder

    init {
        markerHolder = MarkerHolder1(this)
        (parent as? ConstraintLayout)?.addView(markerHolder.layoutHolder)
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    class MarkerHolder(val anchorView: AnchorView) {
        val layoutHolder: FrameLayout

        init {
            anchorView.id = generateViewId()
            layoutHolder = FrameLayout(anchorView.context)
            layoutHolder.setBackgroundColor(Color.RED)
            layoutHolder.layoutParams = getCenterLayoutParam()
        }

        private fun getCenterLayoutParam(): ConstraintLayout.LayoutParams {
            val lp = ConstraintLayout.LayoutParams(100, 100)
            lp.bottomToBottom = anchorView.id
            lp.topToTop = anchorView.id
            lp.leftToLeft = anchorView.id
            lp.rightToRight = anchorView.id
            return lp
        }
    }
}