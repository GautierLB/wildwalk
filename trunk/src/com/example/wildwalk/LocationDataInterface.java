package com.example.wildwalk;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.maps.model.LatLng;

public interface LocationDataInterface {
	public void disconnected();
	public void connectionFailed(ConnectionResult co);
	public void updateLocation(LatLng location);
}
