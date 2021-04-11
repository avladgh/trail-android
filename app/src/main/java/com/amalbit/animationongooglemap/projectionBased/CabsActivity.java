package com.amalbit.animationongooglemap.projectionBased;

import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.amalbit.animationongooglemap.R;
import com.amalbit.animationongooglemap.U;
import com.amalbit.animationongooglemap.data.CarData.Car;
import com.amalbit.animationongooglemap.data.LatlngData;
import com.amalbit.animationongooglemap.marker.LatLngInterpolator;
import com.amalbit.animationongooglemap.marker.Repeat;
import com.amalbit.trail.marker.MarkerOverlayView;
import com.amalbit.trail.marker.OverlayMarker;
import com.amalbit.trail.marker.OverlayMarker.OnMarkerUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class CabsActivity extends BaseActivity implements OnMapReadyCallback, OnMarkerUpdate {
    private GoogleMap mMap;
    private MarkerOverlayView markerOverlayView;
    private ImageView imgTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cabs);

        markerOverlayView = findViewById(R.id.mapMarkerOverlayView);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;

        mMap.setOnMapLoadedCallback(() -> {
            mMap.setOnCameraMoveListener(() -> markerOverlayView.onCameraMove(mMap));

            setMapBounds(map);
            ArrayList<Car> indiranagarRoutes = LatlngData.getIndiranagarRoutes();
            addMarkerWithAnimation(indiranagarRoutes);
        });
    }

    public void addMarkerWithAnimation(List<Car> cars) {
        runOnUiThread(() -> {
            for (Car car : cars) {
                final OverlayMarker overlayMarker = markerOverlayView.findMarkerById(car.getCarId());
                if (overlayMarker == null) {
                    OverlayMarker overlayMarker1 = new OverlayMarker();
                    overlayMarker1.setLatLng(car.getLatLng());
                    overlayMarker1.setOnMarkerUpdate(CabsActivity.this);
                    markerOverlayView.addMarker(overlayMarker1, mMap.getProjection());
                } else {
                }
            }
        });
    }

    @Override
    public void onMarkerUpdate() {
        markerOverlayView.invalidate();
    }
}
