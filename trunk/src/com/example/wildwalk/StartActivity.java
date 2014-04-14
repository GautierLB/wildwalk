package com.example.wildwalk;

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

public class StartActivity extends Fragment {
	private Button btnStart;
	private Context context;
	private Spinner spinner1;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		context= this.getActivity();
		
		View start = inflater.inflate(R.layout.activity_start, container, false);
		((TextView) start.findViewById(R.id.textView)).setText("Gautier Lebissonnais");
		btnStart = (Button) start.findViewById(R.id.btnStart);
		
		btnStart.setOnClickListener(new View.OnClickListener() {
			@SuppressLint("NewApi")
			@Override
			public void onClick(View view) {

				Toast toast = Toast.makeText(context, "START", Toast.LENGTH_SHORT);
				toast.show();
			}
		});

		return start;

	}

}




