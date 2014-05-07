package com.example.wildwalk;

import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MenuItem;

public class CompassActivity extends Activity {

	// vue boussole
	private CompassView compassView;
	// gestionnaire capteurs
	private SensorManager sensorManager;
	// capteur boussole
	private Sensor sensor;

	// listener sur capteur boussole
	private final SensorEventListener sensorListener = new SensorEventListener() {
		@Override
		public void onSensorChanged(SensorEvent event) {
			updateOrientation(event.values[SensorManager.DATA_X]);
		}

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}
	};

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
		
		setContentView(R.layout.activity_compass);
		compassView = (CompassView) findViewById(R.id.compassView);
		// Récupération du gestionnaire de capteurs
		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		// Demander au gestionnaire de capteur de nous retourner les capteurs de type boussole
		List<Sensor> sensors = sensorManager
				.getSensorList(Sensor.TYPE_ORIENTATION);
		// s’il y a plusieurs capteurs de ce type on garde uniquement le premier
		if (sensors.size() > 0) {
			sensor = sensors.get(0);
		}
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

	// MAJ orientation
	protected void updateOrientation(float rotation) {
		compassView.setNorthOrientation(rotation);
	}

	@Override
	protected void onResume() {
		super.onResume();
		// Lier les évènements de la boussole numérique au listener
		sensorManager.registerListener(sensorListener, sensor,
				SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	protected void onStop() {
		super.onStop();
		// Retirer le lien entre le listener et les évènements de la boussole
		sensorManager.unregisterListener(sensorListener);
	}
}
