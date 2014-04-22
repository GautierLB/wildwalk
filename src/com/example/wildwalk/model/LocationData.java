package com.example.wildwalk.model;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.example.wildwalk.LocationDataInterface;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;

import android.app.PendingIntent;
import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

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

	public void startRando(String hikeType) {
		if (m_locationClient.isConnected()) {
			Location currentLocation = m_locationClient.getLastLocation();
			Point start = new Point(1, currentLocation.getLatitude(),
					currentLocation.getAltitude(),
					currentLocation.getLongitude());
			Section first = new Section(1, start);
			m_updatesRequested = true;
			this.requestUpdates();
			if (hikeType.equals("Foot")) {
				FootHike m_hike = new FootHike(0, first);
				LocationData.UPDATE_INTERVAL_IN_SECONDS = 180;
				m_locationRequest.setInterval(UPDATE_INTERVAL);
			} else if (hikeType.equals("Bike")) {
				BikeHike m_hike = new BikeHike(0, first);
				LocationData.UPDATE_INTERVAL_IN_SECONDS = 60;
				m_locationRequest.setInterval(UPDATE_INTERVAL);
			} else if (hikeType.equals("Car")) {
				CarHike m_hike = new CarHike(0, first);
				LocationData.UPDATE_INTERVAL_IN_SECONDS = 30;
				m_locationRequest.setInterval(UPDATE_INTERVAL);
			}

		} else {
			this.onDisconnected();
		}

	}
	
	public void stopRando()
	{
		m_hike.stop();
		 // If the client is connected
        if (m_locationClient.isConnected()) {
            /*
             * Remove location updates for a listener.
             * The current Activity is the listener, so
             * the argument is "this".
             */
            m_locationClient.removeLocationUpdates(this);
        }
        /*
         * After disconnect() is called, the client is
         * considered "dead".
         */
        m_locationClient.disconnect();
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
		Point next = new Point(1, location.getLatitude(),
				location.getAltitude(), location.getLongitude());
		//m_hike.addPoint();

	}
}