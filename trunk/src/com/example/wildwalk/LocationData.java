package com.example.wildwalk;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
			(new GetAddressTask(m_context)).execute(currentLocation);
			return msg;
		} else {
			return "Erreur de connexion au service de localisation";
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

	public void displayCurrentLocation() {
		// Get the current location's latitude & longitude
		Location currentLocation = m_locationClient.getLastLocation();
		String msg = "Current Location: "
				+ Double.toString(currentLocation.getLatitude()) + ","
				+ Double.toString(currentLocation.getLongitude());

		// Display the current location in the UI
		// locationLabel.setText(msg);

		// To display the current address in the UI
		(new GetAddressTask(m_context)).execute(currentLocation);
	}

	/*
	 * Following is a subclass of AsyncTask which has been used to get address
	 * corresponding to the given latitude & longitude.
	 */
	private class GetAddressTask extends AsyncTask<Location, Void, String> {
		Context m_context;

		public GetAddressTask(Context context) {
			super();
			this.m_context = context;
		}

		/*
		 * When the task finishes, onPostExecute() displays the address.
		 */
		@Override
		protected void onPostExecute(String address) {
			// Display the current address in the UI
			// return address;
		}

		@Override
		protected String doInBackground(Location... params) {
			Geocoder geocoder = new Geocoder(m_context, Locale.getDefault());
			// Get the current location from the input parameter list
			Location loc = params[0];
			// Create a list to contain the result address
			List<Address> addresses = null;
			try {
				addresses = geocoder.getFromLocation(loc.getLatitude(),
						loc.getLongitude(), 1);
			} catch (IOException e1) {
				Log.e("LocationSampleActivity",
						"IO Exception in getFromLocation()");
				e1.printStackTrace();
				return ("IO Exception trying to get address");
			} catch (IllegalArgumentException e2) {
				// Error message to post in the log
				String errorString = "Illegal arguments "
						+ Double.toString(loc.getLatitude()) + " , "
						+ Double.toString(loc.getLongitude())
						+ " passed to address service";
				Log.e("LocationSampleActivity", errorString);
				e2.printStackTrace();
				return errorString;
			}
			// If the reverse geocode returned an address
			if (addresses != null && addresses.size() > 0) {
				// Get the first address
				Address address = addresses.get(0);
				/*
				 * Format the first line of address (if available), city, and
				 * country name.
				 */
				String addressText = String.format(
						"%s, %s, %s",
						// If there's a street address, add it
						address.getMaxAddressLineIndex() > 0 ? address
								.getAddressLine(0) : "",
						// Locality is usually a city
						address.getLocality(),
						// The country of the address
						address.getCountryName());
				// Return the text
				return addressText;
			} else {
				return "No address found";
			}
		}
	}// AsyncTask class

}