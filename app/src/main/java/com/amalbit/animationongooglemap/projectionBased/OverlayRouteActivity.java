package com.amalbit.animationongooglemap.projectionBased;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import com.amalbit.animationongooglemap.R;
import com.amalbit.animationongooglemap.data.CarData;
import com.amalbit.animationongooglemap.data.LatlngData;
import com.amalbit.trail.OverlayLayout;
import com.amalbit.trail.OverlayPolyline;
import com.amalbit.trail.RouteOverlayView;
import com.amalbit.trail.RouteOverlayView.RouteType;
import com.amalbit.trail.marker.MarkerOverlayView;
import com.amalbit.trail.marker.OverlayMarker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.CancelableCallback;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;

import java.util.ArrayList;
import java.util.List;

public class OverlayRouteActivity extends BaseActivity implements OnMapReadyCallback, OverlayMarker.OnMarkerUpdate {

    private static String TAG = "OverlayRouteActivity";

    private GoogleMap mMap;

    private MapStyleOptions mapStyle;

    private List<LatLng> mRoute;

    private OverlayLayout overlayLayout;

    private MarkerOverlayView markerOverlayView;

    private RouteOverlayView mRouteOverlayView;

    private SupportMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projection_route);
        initUI();
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mRoute = LatlngData.getRoute();
        mapStyle = MapStyleOptions.loadRawResourceStyle(getApplicationContext(), R.raw.map_style);
    }

    public void onClick1(View view) {
        switch (view.getId()) {
            case R.id.btnAdd:
                mRouteOverlayView.removeRoutes();
                zoomRoute(mRoute);
                break;
            case R.id.btnRemove:
                mRouteOverlayView.removeRoutes();
                break;
        }
    }

    private void initUI() {
        overlayLayout = findViewById(R.id.mapOverlayLayout);
        mRouteOverlayView = overlayLayout.getRouteOverlayView();
        markerOverlayView = findViewById(R.id.markerOverlay);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.array_place, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    @Override
    public void onMapReady(final GoogleMap map) {
        mMap = map;
        mMap.setMapStyle(mapStyle);
        mMap.getUiSettings().setTiltGesturesEnabled(false);

        mMap.setOnMapLoadedCallback(() -> {
            overlayLayout.addGoogleMap(mMap);
            zoomRoute(mRoute);
            drawRoutes();
            mMap.setOnCameraMoveListener(() -> {
                        overlayLayout.onCameraMoved();
                        markerOverlayView.onCameraMove(mMap);
                    }
            );
            drawMarkers();
        });
    }

    private void drawMarkers() {
        OverlayMarker overlayMarker1 = new OverlayMarker();
        overlayMarker1.setLatLng(mRoute.get(0));
        overlayMarker1.setOnMarkerUpdate(OverlayRouteActivity.this);
        markerOverlayView.addMarker(overlayMarker1, mMap.getProjection());

        OverlayMarker overlayMarker2 = new OverlayMarker();
        overlayMarker2.setLatLng(mRoute.get(mRoute.size() - 1));
        overlayMarker2.setOnMarkerUpdate(OverlayRouteActivity.this);
        markerOverlayView.addMarker(overlayMarker2, mMap.getProjection());
    }

    public void zoomRoute(List<LatLng> lstLatLngRoute) {

        if (mMap == null || lstLatLngRoute == null || lstLatLngRoute.isEmpty()) {
            return;
        }
        LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();
        for (LatLng latLngPoint : lstLatLngRoute) {
            boundsBuilder.include(latLngPoint);
        }
        for (LatLng latLng : LatlngData.getRouteB()) {
            boundsBuilder.include(latLng);
        }

        LatLngBounds latLngBounds = boundsBuilder.build();

        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 100), 200, new CancelableCallback() {
            @Override
            public void onFinish() {
                drawRoutes();
            }

            @Override
            public void onCancel() {

            }
        });
    }

    private void drawRoutes() {

//        OverlayPolyline normalOverlayPolygon = new OverlayPolyline.Builder(mRouteOverlayView)
//                .setRouteType(RouteType.PATH)
//                .setCameraPosition(mMap.getCameraPosition())
//                .setProjection(mMap.getProjection())
//                .setLatLngs(LatlngData.getClosedRouteB())
//                .setBottomLayerColor(Color.DKGRAY)
//                .setTopLayerColor(Color.GREEN)
//                .create();


        OverlayPolyline normalOverlayPolyline = new OverlayPolyline.Builder(mRouteOverlayView)
                .setRouteType(RouteType.PATH)
                .setCameraPosition(mMap.getCameraPosition())
                .setProjection(mMap.getProjection())
                .setLatLngs(mRoute)
                .setBottomLayerColor(Color.YELLOW)
                .setTopLayerColor(Color.RED)
                .create();

//        OverlayPolyline dashOverlayPolyline = new OverlayPolyline.Builder(mRouteOverlayView)
//                .setRouteType(RouteType.DASH)
//                .setCameraPosition(mMap.getCameraPosition())
//                .setProjection(mMap.getProjection())
//                .setLatLngs(LatlngData.getRouteB())
//                .setDashColor(Color.BLACK)
//                .create();
//
        OverlayPolyline arcOverlayPolyline = new OverlayPolyline.Builder(mRouteOverlayView)
                .setRouteType(RouteType.ARC)
                .setCameraPosition(mMap.getCameraPosition())
                .setProjection(mMap.getProjection())
                .setLatLngs(LatlngData.getRouteB())
                .setBottomLayerColor(Color.GRAY)
                .setTopLayerColor(Color.BLACK)
                .setRouteShadowColor(Color.GRAY)
                .create();
    }

    @Override
    public void onMarkerUpdate() {
        markerOverlayView.invalidate();
    }
}
