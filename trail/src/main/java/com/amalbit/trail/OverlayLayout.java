package com.amalbit.trail;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.amalbit.trail.contract.GooglemapProvider;
import com.amalbit.trail.marker.ViewOverlayView;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.CameraPosition;

import java.lang.ref.WeakReference;

public class OverlayLayout extends FrameLayout {

    private RouteOverlayView routeOverlayView;

    private ViewOverlayView viewOverlayView;

    public OverlayLayout(@NonNull Context context) {
        super(context);
        init(context);
    }

    public OverlayLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public OverlayLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutParams matchParentParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        routeOverlayView = new RouteOverlayView(context);
        routeOverlayView.setLayoutParams(matchParentParams);
        addView(routeOverlayView);

        viewOverlayView = new ViewOverlayView(context);
        viewOverlayView.setLayoutParams(matchParentParams);
        addView(viewOverlayView);
    }

    public void onMapReady() {
        viewOverlayView.onMapReady();
        routeOverlayView.onMapReady();
    }

    public RouteOverlayView getRouteOverlayView() {
        return routeOverlayView;
    }

    public ViewOverlayView getViewOverlayView() {
        return viewOverlayView;
    }


    public void onCameraMoved(Projection projection, CameraPosition cameraPosition) {
        routeOverlayView.onCameraMove(projection, cameraPosition);
        viewOverlayView.onCameraMove(projection, cameraPosition);
    }
}
