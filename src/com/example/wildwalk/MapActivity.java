package com.example.wildwalk;

import java.util.Timer;

import android.app.ActionBar;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.SyncStateContract.Constants;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Chronometer;
import android.widget.Chronometer.OnChronometerTickListener;
import android.support.v4.app.Fragment;

import com.example.wildwalk.model.LocationData;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends FragmentActivity implements
		LocationDataInterface {
	private Context context = this;
	GoogleMap map;
	Location location;
	LatLng myLocation;
	LocationData loc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.activity_map);
		/*
		 * this.map =
		 * ((MapFragment)getFragmentManager().findFragmentById(R.id.map
		 * )).getMap();​​
		 * map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-18.142,
		 * 178.431), 2));
		 */

		map = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map)).getMap();
		map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		map.setMyLocationEnabled(true);
		map.getUiSettings().setCompassEnabled(true);
		LatLng Lyon = new LatLng(45.7500000, 4.8500000);
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(Lyon, 13));

		Log.e("Maps", "------EOC-------");
	}

	private void centerMapOnMyLocation() {

		map.setMyLocationEnabled(true);

		location = map.getMyLocation();

		if (location != null) {
			myLocation = new LatLng(location.getLatitude(),
					location.getLongitude());
		}
		map.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 10));
	}

	// Back button
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map, menu);
		return true;

	}

	public void centerPosition(Location loc) { // fonction qui marche surement
												// pas !
		LatLng coordinate = new LatLng(loc.getLatitude(), loc.getLongitude());
		CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(
				coordinate, 5);
		map.animateCamera(yourLocation);
	}

	@Override
	public void disconnected() {
		// TODO Auto-generated method stub

	}

	@Override
	public void connectionFailed(ConnectionResult co) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateLocation(LatLng location) {
		// TODO Auto-generated method stub

	}

}