package com.example.wildwalk;


import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("NewApi")
public class HikeStatActivity extends Activity{

	 @SuppressLint("NewApi")
	public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_hike_stat);
	        Intent i = getIntent();
	        Hike hike = (Hike)i.getSerializableExtra("Hike");
	        
	        TextView nameHike = (TextView) findViewById(R.id.nameHike);
	        ImageView imageHike = (ImageView) findViewById(R.id.imageHike);
	        TextView dateHike = (TextView) findViewById(R.id.dateHike);
	        TextView kmHike = (TextView) findViewById(R.id.kmHike);
	        
	        nameHike.setText(hike.getNameHike());
	        dateHike.setText(String.valueOf(hike.getDateHike()));
	        kmHike.setText(String.valueOf(hike.getKmHike()));
	        imageHike.setId(hike.getImageHike());
	        
	        
	        
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
