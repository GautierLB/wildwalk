package com.example.wildwalk;

import com.example.wildwalk.model.LocationData;
import com.google.android.gms.common.ConnectionResult;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
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
	LocationData loc;
	private String hikeType;
	private Chronometer chrono;
	private int chronoState = 0;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		context= this.getActivity();
		
		View start = inflater.inflate(R.layout.activity_start, container, false);
		location =((TextView) start.findViewById(R.id.textView));
		btnStart = (Button) start.findViewById(R.id.btnStart);
		spinner = (Spinner) start.findViewById(R.id.spinner1);
		loc = new LocationData(this, context);
		chrono = (Chronometer)start.findViewById(R.id.chronometer1);
		loc.connect();
		
		chrono.setOnChronometerTickListener(new OnChronometerTickListener() {
		    public void onChronometerTick(Chronometer cArg) {
		        long t = SystemClock.elapsedRealtime() - cArg.getBase();
		        cArg.setText(DateFormat.format("kk:mm:ss", t));
		    }
		});
		
		// On récupère l'élément selectionné dans le spinner
				spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
				    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				        Object item = parent.getItemAtPosition(pos);
				        hikeType = spinner.getSelectedItem().toString();
				    }
				    public void onNothingSelected(AdapterView<?> parent) {
				    }
				});
				
		// long press button
		btnStart.setOnLongClickListener(new OnLongClickListener() { 
			@Override
			public boolean onLongClick(View v) {
					// TODO Auto-generated method stub
					Toast toastStop = Toast.makeText(context, "La randonnée à durée : " + chrono.getText(), Toast.LENGTH_SHORT);
					toastStop.show();
					btnStart.setText("START");
					chrono.stop();
					chrono.setBase(SystemClock.elapsedRealtime()); // on reset à 00:00
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
					chrono.start();
					btnStart.setText("PAUSE");
					chronoState = 1;
					this.getLocation();
					break;
					
				case 1: 
					// PAUSE
					Toast toastPause = Toast.makeText(context, "Maintenez appuyer pour Stopper", Toast.LENGTH_SHORT);
					toastPause.show();			
					chrono.stop();
					btnStart.setText("CONTINUE");
					chronoState = 0;
					break;
				}		
				loc.startRando();
			}

			private void getLocation() {
				location.setText(loc.getLocation());				
			}
		});

		
		
		return start;

	}

	@Override
	public void disconnected() {
		Toast.makeText(this.context,"Location client disconnected",Toast.LENGTH_SHORT).show();	
	}

	@Override
	public void connectionFailed(ConnectionResult co) {
		Toast.makeText(this.context, "Connection Failure : " + 
			      co.getErrorCode(),
			      Toast.LENGTH_SHORT).show();
		
	}
	

}




