package com.amalbit.animationongooglemap.projectionBased;

import android.os.Bundle;
import android.widget.ImageView;

import com.amalbit.animationongooglemap.R;
import com.amalbit.animationongooglemap.data.CarData.Car;
import com.amalbit.animationongooglemap.data.LatlngData;
import com.amalbit.trail.marker.MarkerOverlayView;
import com.amalbit.trail.marker.MarkerHolder;
import com.amalbit.trail.marker.MarkerHolder.OnMarkerUpdate;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

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
                final MarkerHolder markerHolder = markerOverlayView.findMarkerById(car.getCarId());
                if (markerHolder == null) {
                    MarkerHolder markerHolder1 = new MarkerHolder();
                    markerHolder1.setLatLng(car.getLatLng());
                    markerHolder1.setOnMarkerUpdate(CabsActivity.this);
                    markerOverlayView.addMarker(markerHolder1, mMap.getProjection());
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
