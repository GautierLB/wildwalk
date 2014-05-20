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
import android.app.Activity;
import android.content.Intent;
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
		List<Section> sectionList = new ArrayList<Section>();
		List<LatLng> LatLngList = new ArrayList<LatLng>();
		Section ZoomSection;
		Point firstZoomPoint;
		Point lastZoomPoint;
		Point firstPoint;
		Point lastPoint;
		//Double altitude = hike.getDifferenceInHieght();
		//Double speed = hike.getAverageSpeed();

		TextView nameHike = (TextView) findViewById(R.id.nameHike);
		TextView dateHike = (TextView) findViewById(R.id.dateHike);
		TextView kmHike = (TextView) findViewById(R.id.kmHike);
		TextView altitudeHike = (TextView) findViewById(R.id.altitudeHike);
		TextView speedHike = (TextView) findViewById(R.id.speedHike);

		nameHike.setText(hike.getNameHike());
		dateHike.setText(String.valueOf(hike.getDateHike()));
		kmHike.setText(String.valueOf(hike.getKmHike()));
		/*if (altitude != null) {
			altitudeHike.setText(String.valueOf(hike.getDifferenceInHieght()));
		} else
			altitudeHike.setText("0");
		if (speed != null) {
			speedHike.setText(String.valueOf(hike.getAverageSpeed()));
		} else
			speedHike.setText("0");*/
		GoogleMap map = ((MapFragment) getFragmentManager().findFragmentById(
				R.id.mapSection)).getMap();

		/*
		 * sectionList = hike.getSections(); ZoomSection = sectionList.get(0);
		 * firstZoomPoint = ZoomSection.getfirstPoint(); lastZoomPoint =
		 * ZoomSection.getLastPoint(); for(Section section : sectionList){
		 * firstPoint = section.getfirstPoint(); lastPoint =
		 * section.getLastPoint(); LatLngList.add(new
		 * LatLng(firstPoint.getlatitude(),firstPoint.getlongitude()));
		 * LatLngList.add(new
		 * LatLng(lastPoint.getlatitude(),lastPoint.getlongitude())); }
		 * 
		 * map.moveCamera(CameraUpdateFactory.newLatLngZoom( new
		 * LatLng(firstZoomPoint.getlatitude(), lastZoomPoint.getlatitude()),
		 * 14)); //Polylines are useful for marking paths and routes on the map.
		 * map.addPolyline(new PolylineOptions().geodesic(true)
		 * .addAll(LatLngList));
		 */

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
