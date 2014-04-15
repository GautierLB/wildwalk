package com.example.wildwalk;

import com.google.android.gms.common.ConnectionResult;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class StartActivity extends Fragment implements LocationDataInterface {
	private Button btnStart;
	private Context context;
	private Spinner spinner1;
	private TextView location;
	LocationData loc;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		context= this.getActivity();
		
		View start = inflater.inflate(R.layout.activity_start, container, false);
		location =((TextView) start.findViewById(R.id.textView));
		btnStart = (Button) start.findViewById(R.id.btnStart);
		loc = new LocationData(this, context);
		loc.connect();
		
		btnStart.setOnClickListener(new View.OnClickListener() {
			@SuppressLint("NewApi")
			@Override
			public void onClick(View view) {

				Toast toast = Toast.makeText(context, "START", Toast.LENGTH_SHORT);
				toast.show();
				this.getLocation();
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




