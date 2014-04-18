package com.example.wildwalk.model;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.example.wildwalk.LocationDataInterface;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

public class LocationData implements
		GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener {
	LocationClient m_locationClient;
	Context m_context;
	LocationDataInterface m_activity;

	public LocationData(LocationDataInterface act,Context context) {
		m_context = context;
		m_activity = act;
		m_locationClient = new LocationClient(context, this, this);
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
	
	public void startRando() {
		if (m_locationClient.isConnected()) {
			Location currentLocation = m_locationClient.getLastLocation();
			Point start = new Point(1,currentLocation.getLatitude(),currentLocation.getAltitude(), currentLocation.getLongitude() );
			Section first = new Section(1,start);
			Hike hike = new Hike(0,first);
		} else {
			this.onDisconnected();
		}
		
	}


	public void connect() {
		m_locationClient.connect();
	}

	@Override
	public void onConnected(Bundle dataBundle) {
		this.getLocation();
	}

	@Override
	public void onDisconnected() {
		m_activity.disconnected();
	}

	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
		m_activity.connectionFailed(connectionResult);
	}
}