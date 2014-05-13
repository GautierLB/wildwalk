package com.example.wildwalk;

import android.app.ActionBar;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.provider.SyncStateContract.Constants;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.Fragment;

import com.example.wildwalk.model.LocationData;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

public class MapActivity extends FragmentActivity {
	private Context context = this;
	GoogleMap map;
	Location location;
	LatLng myLocation;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.activity_map);
		
		
		
		GoogleMap gMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
		gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		gMap.setMyLocationEnabled(true);
		gMap.getUiSettings().setCompassEnabled(true);
		Log.e("Maps", "------EOC-------");
	}
	
	
	private void centerMapOnMyLocation() {

	    map.setMyLocationEnabled(true);

	    location = map.getMyLocation();

	    
		if (location != null) {
	        myLocation = new LatLng(location.getLatitude(),
	                location.getLongitude());
	    }
	    map.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation,10));
	}
	
	//Back button
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
	
	public void centerPosition(Location loc){ // fonction qui marche surement pas !
		LatLng coordinate = new LatLng(loc.getLatitude(), loc.getLongitude());
		CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(coordinate, 5);
		map.animateCamera(yourLocation);
	}
	

}