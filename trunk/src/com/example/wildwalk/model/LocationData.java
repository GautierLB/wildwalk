package com.example.wildwalk.model;


import com.example.wildwalk.LocationDataInterface;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.model.LatLng;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;

public class LocationData implements
		GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener, LocationListener {

	// Milliseconds per second
	private static int MILLISECONDS_PER_SECOND = 1000;
	// Update frequency in seconds
	public static int UPDATE_INTERVAL_IN_SECONDS = 5;
	// Update frequency in milliseconds
	private static long UPDATE_INTERVAL = MILLISECONDS_PER_SECOND
			* UPDATE_INTERVAL_IN_SECONDS;
	// The fastest update frequency, in seconds
	private static int FASTEST_INTERVAL_IN_SECONDS = 30;
	// A fast frequency ceiling in milliseconds
	private static long FASTEST_INTERVAL = MILLISECONDS_PER_SECOND
			* FASTEST_INTERVAL_IN_SECONDS;

	LocationClient m_locationClient;
	Context m_context;
	LocationDataInterface m_activity;
	LocationRequest m_locationRequest;
	Boolean m_updatesRequested;
	Hike m_hike;

	public LocationData(LocationDataInterface act, Context context) {
		m_context = context;
		m_activity = act;
		m_locationClient = new LocationClient(context, this, this);
		m_updatesRequested = false;
		m_hike = null;
		// Create the LocationRequest object
		m_locationRequest = LocationRequest.create();
		// Use high accuracy
		m_locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		// Set the update interval to 5 seconds
		m_locationRequest.setInterval(UPDATE_INTERVAL);
		// Set the fastest update interval to 1 second
		m_locationRequest.setFastestInterval(FASTEST_INTERVAL);
	}

	public String getLocation() {
		if (m_locationClient.isConnected()) {
			Location currentLocation = m_locationClient.getLastLocation();
			String msg = "Current Location: "
					+ Double.toString(currentLocation.getLatitude()) + ","
					+ Double.toString(currentLocation.getLongitude());
			return msg;
		} else {
			return "Erreur de connexion au service de localisation";
		}
	}
	public LatLng getLocationLatLng() {
		if (m_locationClient.isConnected()) {
			Location currentLocation = m_locationClient.getLastLocation();
			return new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
		} else {
			return new LatLng(45.7500000,4.8500000);
		}
	}

	public void startHike(String hikeType, Context context) {
		if (m_locationClient.isConnected()) {
			Location currentLocation = m_locationClient.getLastLocation();
			Point start = new Point(currentLocation.getLatitude(),
					currentLocation.getAltitude(),
					currentLocation.getLongitude(), this.m_context);
			Section first = new Section(start, this.m_context);
			m_updatesRequested = true;
			this.requestUpdates();
			if (hikeType.equals("Foot")) {
				m_hike = new FootHike(first, context);
				LocationData.UPDATE_INTERVAL_IN_SECONDS = 180;
				m_locationRequest.setInterval(UPDATE_INTERVAL);
			} else if (hikeType.equals("Bike")) {
				m_hike = new BikeHike(first, context);
				LocationData.UPDATE_INTERVAL_IN_SECONDS = 60;
				m_locationRequest.setInterval(UPDATE_INTERVAL);
			} else if (hikeType.equals("Car")) {
				m_hike = new CarHike(first,context);
				LocationData.UPDATE_INTERVAL_IN_SECONDS = 30;
				m_locationRequest.setInterval(UPDATE_INTERVAL);
			}

		} else {
			this.onDisconnected();
		}

	}

	public void stopHike() {
		Location currentLocation = m_locationClient.getLastLocation();
		Point end = new Point(currentLocation.getLatitude(),
				currentLocation.getAltitude(),
				currentLocation.getLongitude(), this.m_context);
		m_hike.stop(end);
		if (m_locationClient.isConnected()) {
			m_locationClient.removeLocationUpdates(this);
		}
	}
	
	public void connect() {
		m_locationClient.connect();
	}

	@Override
	public void onConnected(Bundle dataBundle) {
		this.getLocation();
		if (m_updatesRequested) {
			m_locationClient.requestLocationUpdates(m_locationRequest, this);
		}
	}

	public void requestUpdates() {
		m_locationClient.requestLocationUpdates(m_locationRequest, this);
	}

	@Override
	public void onDisconnected() {
		m_activity.disconnected();
	}

	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
		m_activity.connectionFailed(connectionResult);
	}

	@Override
	public void onLocationChanged(Location location) {
		if (m_hike != null) {
			Point next = new Point(location.getLatitude(),
					location.getAltitude(), location.getLongitude(), this.m_context);
			m_hike.addPoint(next);
		}
		m_activity.updateLocation(new LatLng(location.getLatitude(), location.getLongitude()));

	}
}