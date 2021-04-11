package com.amalbit.trail.marker;

import android.content.Context;
import android.graphics.Canvas;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.util.AttributeSet;
import android.view.View;

import com.amalbit.trail.marker.OverlayMarker.MarkerRemoveListner;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.Projection;

import java.util.ArrayList;
import java.util.List;

public class MarkerOverlayView extends ConstraintLayout implements MarkerRemoveListner {

//    private final Object mSvgLock = new Object();

    /**
     * TO be converted to a HashMap.
     **/
    private List<OverlayMarker> overlayMarkers;

    public MarkerOverlayView(Context context) {
        super(context);
        init();
    }

    public MarkerOverlayView(Context context,
                             @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        overlayMarkers = new ArrayList<>();
    }

    public void addMarker(OverlayMarker overlayMarker, Projection projection) {
        overlayMarker.setScreenPoint(projection.toScreenLocation(overlayMarker.getLatLng()));
        overlayMarker.setMarkerRemoveListner(this);
        overlayMarkers.add(overlayMarker);
        overlayMarker.anchorView2 = new AnchorView2(getContext());
        addMarkerToLayout(overlayMarker);
    }

    public OverlayMarker findMarkerById(int markerId) {
        for (OverlayMarker marker : overlayMarkers) {
            if (marker.getMarkerId() == markerId) {
                return marker;
            }
        }
        return null;
    }

    @Override
    public void onRemove(OverlayMarker overlayMarker) {
        overlayMarkers.remove(overlayMarker);
        invalidate();
    }

    public void onCameraMove(GoogleMap googleMap) {
        for (OverlayMarker overlayMarker : overlayMarkers) {
            overlayMarker.setScreenPoint(googleMap.getProjection().toScreenLocation(overlayMarker.getLatLng()));
            overlayMarker.setMapBearing(googleMap.getCameraPosition().bearing);
            updateMarkerPosition(overlayMarker);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }


    private void updateMarkerPosition(OverlayMarker overlayMarker) {
        overlayMarker.anchorView2.setAbsolutePosition(overlayMarker.getScreenPoint().x, overlayMarker.getScreenPoint().y);
    }

    private void addMarkerToLayout(OverlayMarker overlayMarker) {
        addView(overlayMarker.anchorView2, 1, 1);
        ConstraintSet set = new ConstraintSet();
        set.clone(this);
        set.connect(overlayMarker.anchorView2.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 0);
        set.connect(overlayMarker.anchorView2.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, 0);
        set.applyTo(this);
        updateMarkerPosition(overlayMarker);
    }
}
