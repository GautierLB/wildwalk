package com.example.wildwalk;

import com.example.wildwalk.model.LocationData;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Chronometer.OnChronometerTickListener;
public class StartActivity extends Fragment implements LocationDataInterface {
	private Button btnStart;
	private Context context;
	private Spinner spinner;
	private TextView location;
	private String chronometre;
	private TextView hours;
	LocationData loc;
	private String hikeType;
	private Chronometer chrono;
	private int chronoState = 0;
	private int resetChrono=0;
	private CharSequence tempTime = "00:00";
	long timeWhenStopped=0;
	GoogleMap map;
	private int sec=0;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		context= this.getActivity();
		View start = inflater.inflate(R.layout.activity_start, container, false);
		//location =((TextView) start.findViewById(R.id.textView));
		btnStart = (Button) start.findViewById(R.id.btnStart);
		spinner = (Spinner) start.findViewById(R.id.spinner1);
		//hours = (long) start.findViewById(R.id.);
		hours = ((TextView) start.findViewById(R.id.hours));
		loc = new LocationData(this, context);
		loc.connect();
		chrono = (Chronometer)start.findViewById(R.id.chronometer1);
		//chrono.setFormat("00:00:00");
		//chrono.setBase(SystemClock.elapsedRealtime());
		chrono.setVisibility(View.INVISIBLE);
		map = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		map.setMyLocationEnabled(true);
		map.getUiSettings().setCompassEnabled(true);
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc.getLocationLatLng(), 13));
		
		
		
		// On récupère l'élément selectionné dans le spinner
				spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
				    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				        Object item = parent.getItemAtPosition(pos);
				        hikeType = spinner.getSelectedItem().toString();
				    }
				    public void onNothingSelected(AdapterView<?> parent) {
				    }
				});
		
		chrono.setOnChronometerTickListener(new OnChronometerTickListener(){

			@Override
			public void onChronometerTick(Chronometer arg0) {
				
				long secondes=((SystemClock.elapsedRealtime()-chrono.getBase())/1000)%60;		
				long minutes=(((SystemClock.elapsedRealtime()-chrono.getBase())/1000)/60)%60;
				long heures= ((SystemClock.elapsedRealtime()-chrono.getBase())/1000)/3600;
				chronometre = heures+"h, "+minutes+"m, "+secondes+"s ";
				hours.setText(chronometre);
				
			}
			
		});
				
		// long press button
		btnStart.setOnLongClickListener(new OnLongClickListener() { 
			@Override
			public boolean onLongClick(View v) {
					// TODO Auto-generated method stub
					Toast toastStop = Toast.makeText(context, "La randonnée a duré : " + chrono.getText(), Toast.LENGTH_SHORT);
					toastStop.show();
					loc.stopHike();
					btnStart.setText("START");
					chrono.setBase(SystemClock.elapsedRealtime()); // on reset à 00:00
					chrono.stop();
					chronoState = 0;
					timeWhenStopped =0;
					return true;
			}
		});
		// press button		
		btnStart.setOnClickListener(new View.OnClickListener() {
			@SuppressLint("NewApi")
			@Override
			public void onClick(View view) {
				
				switch (chronoState) {
				case 0:
					// START
					
					Toast toastStart = Toast.makeText(context, "New hike : "+ hikeType, Toast.LENGTH_SHORT);
					toastStart.show();
					
					chrono.setBase(SystemClock.elapsedRealtime()+timeWhenStopped);
					chrono.start();
					
					
					btnStart.setText("PAUSE");
					chronoState = 1;
					this.getLocation();
					map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc.getLocationLatLng(), 13));
					loc.startHike(hikeType,context);					
					break;
					
				case 1: 
					// PAUSE
					
					long minutes=((SystemClock.elapsedRealtime()-chrono.getBase())/1000)/60;
					
					Toast toastPause = Toast.makeText(context, "Maintenez appuyer pour Stopper ", Toast.LENGTH_SHORT);
					toastPause.show();
					timeWhenStopped = chrono.getBase() - SystemClock.elapsedRealtime();
					chrono.stop();
					btnStart.setText("CONTINUE");
					chronoState = 0;
					break;
				}		
				
			}
			private void getLocation() {
				//location.setText(loc.getLocation());
				
			}

			
		});

		
		
		return start;

	}
	
	 
	@Override
	public void disconnected() {
		Toast.makeText(this.context,"Location client disconnected",Toast.LENGTH_SHORT).show();	
	}

	public void connectionFailed(ConnectionResult co) {
		Toast.makeText(this.context, "Connection Failure : " + 
			      co.getErrorCode(),
			      Toast.LENGTH_SHORT).show();
		
	}


	@Override
	public void updateLocation(LatLng location) {
		this.map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc.getLocationLatLng(), 15));
		
	}
	

}




