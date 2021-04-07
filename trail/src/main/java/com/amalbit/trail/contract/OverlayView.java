package com.amalbit.trail.contract;


import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.CameraPosition;

public interface OverlayView {
  void onMapReady();
  void onCameraMove(Projection projection, CameraPosition cameraPosition);
}

