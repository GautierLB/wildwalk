package com.example.wildwalk;

import java.util.ArrayList;
import java.util.List;

import com.example.wildwalk.model.Hike;
import com.example.wildwalk.model.Point;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;

import com.example.wildwalk.model.Section;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;
import android.widget.TextView;

@SuppressLint("NewApi")
public class HikeStatActivity extends FragmentActivity {

	@SuppressLint("NewApi")
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hike_stat);
		Bundle i = getIntent().getExtras();
		Hike hike = i.getParcelable("Hike");
		ArrayList<Section> sectionList = new ArrayList<Section>();
		List<LatLng> LatLngList = new ArrayList<LatLng>();
		Section ZoomSection;
		Section FinalSection;
		Point firstZoomPoint;
		Point finalPoint;
		Point firstPoint;
		Point lastPoint;

		TextView nameHike = (TextView) findViewById(R.id.nameHike);
		TextView dateHike = (TextView) findViewById(R.id.dateHike);
		TextView kmHike = (TextView) findViewById(R.id.kmHike);
		TextView altitudeHike = (TextView) findViewById(R.id.altitudeHike);
		TextView speedHike = (TextView) findViewById(R.id.speedHike);

		sectionList = Section.getSectionsForHike(hike.getIdHike(), this);
		hike.setSections(sectionList);
		nameHike.setText(hike.getNameHike());
		dateHike.setText(String.valueOf(hike.getDateHike()));
		kmHike.setText(String.valueOf(hike.getDistanceHike())+" m");
		
		altitudeHike.setText(String.valueOf(hike.getDifferenceInHeight())+" m");
		speedHike.setText(String.valueOf(hike.getAverageSpeed())+" km/h");

		GoogleMap map = ((MapFragment) getFragmentManager().findFragmentById(
				R.id.mapSection)).getMap();

		map.getUiSettings().setAllGesturesEnabled(false);
		
		if (sectionList != null){
			ZoomSection = sectionList.get(0);
			firstZoomPoint = ZoomSection.getfirstPoint();
			FinalSection = sectionList.get(sectionList.size() - 1);
			finalPoint = FinalSection.getfirstPoint();
			for (Section section : sectionList) {
				firstPoint = section.getfirstPoint();
				lastPoint = section.getLastPoint();
				LatLngList.add(new LatLng(firstPoint.getlatitude(), firstPoint
						.getlongitude()));
				LatLngList.add(new LatLng(lastPoint.getlatitude(), lastPoint
						.getlongitude()));
			}
	
			map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
					firstZoomPoint.getlatitude(), firstZoomPoint.getlongitude()), 14)); 
			map.addMarker(new MarkerOptions()
	        .position(new LatLng(firstZoomPoint.getlatitude(), firstZoomPoint.getlongitude()))
	        .title("Départ"));
			map.addMarker(new MarkerOptions()
	        .position(new LatLng(finalPoint.getlatitude(), finalPoint.getlongitude()))
	        .title("Arrivée"));
			map.addPolyline(new PolylineOptions().geodesic(true).addAll(LatLngList));
		}

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();

		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
