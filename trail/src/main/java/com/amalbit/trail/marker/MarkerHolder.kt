package com.amalbit.trail.marker

import android.graphics.Point
import android.view.View
import com.google.android.gms.maps.model.LatLng

class MarkerHolder {
    var markerId = -1

    var latLng: LatLng? = null
        set(latLng) {
            onMarkerUpdate?.onMarkerUpdate()
            field = latLng
        }

    var bearing = 0f
        get() = field - mapBearing

    var mapBearing = 0f

    var screenPoint: Point? = null
    var markerRemoveListner: MarkerRemoveListner? = null
    var onMarkerUpdate: OnMarkerUpdate? = null

    var view: View? = null

    fun remove() {
        markerRemoveListner?.onRemove(this)
    }

    override fun equals(obj: Any?): Boolean {
        if (obj == null) {
            return false
        }
        if (!MarkerHolder::class.java.isAssignableFrom(obj.javaClass)) {
            return false
        }
        val objectToBeCompared = obj as MarkerHolder
        return markerId == objectToBeCompared.markerId
    }

    override fun hashCode(): Int {
        return markerId
    }

    interface MarkerRemoveListner {
        fun onRemove(markerHolder: MarkerHolder?)
    }

    interface OnMarkerUpdate {
        fun onMarkerUpdate()
    }
}