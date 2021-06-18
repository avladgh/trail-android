package com.amalbit.trail.marker

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import com.amalbit.trail.marker.MarkerHolder.MarkerRemoveListner
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.Projection
import java.util.*

class MarkerOverlayView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), MarkerRemoveListner {
    private var markerHolders: MutableList<MarkerHolder> = ArrayList()

    init {
        setLayerType(LAYER_TYPE_HARDWARE, null)
    }

    fun addMarker(markerHolder: MarkerHolder, projection: Projection) {
        markerHolder.screenPoint = projection.toScreenLocation(markerHolder.latLng)
        markerHolder.markerRemoveListner = this
        markerHolders.add(markerHolder)
        markerHolder.view = Button(context)
        addMarkerToLayout(markerHolder)
    }

    fun findMarkerById(markerId: Int): MarkerHolder? {
        for (marker in markerHolders) {
            if (marker.markerId == markerId) {
                return marker
            }
        }
        return null
    }

    fun onCameraMove(googleMap: GoogleMap) {
        for (overlayMarker in markerHolders) {
            overlayMarker.screenPoint = googleMap.projection.toScreenLocation(overlayMarker.latLng)
            overlayMarker.mapBearing = googleMap.cameraPosition.bearing
            updateMarkerPosition(overlayMarker)
        }
        requestLayout()
    }

    private fun updateMarkerPosition(markerHolder: MarkerHolder) {
        val dx = convertX(markerHolder.screenPoint!!.x.toFloat()).toInt()
        val dy = convertY(markerHolder.screenPoint!!.y.toFloat()).toInt()

        (markerHolder.view?.layoutParams as LayoutParams).run {
            setMargins(dx, dy, 0, 0)
        }
    }

    private fun addMarkerToLayout(markerHolder: MarkerHolder) {
        val layoutParams = LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        layoutParams.gravity = Gravity.CENTER
        addView(markerHolder.view, layoutParams)
        updateMarkerPosition(markerHolder)
    }

    private fun convertX(x: Float): Float {
        return x - width / 2f
    }

    private fun convertY(y: Float): Float {
        return y - height / 2f
    }

    override fun onRemove(markerHolder: MarkerHolder?) {
        markerHolders.remove(markerHolder)
        invalidate()
    }
}