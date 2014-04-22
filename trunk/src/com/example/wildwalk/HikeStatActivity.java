package com.example.wildwalk;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HikeStatActivity extends Activity{

	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_hike_stat);
	        Intent i = getIntent();
	        Hike hike = (Hike)i.getSerializableExtra("Hike");
	        
	        TextView nameHike = (TextView) findViewById(R.id.nameHike);
	        
	        

	        Button retour = (Button) findViewById(R.id.returnButton);
	        retour.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View view) {
	                Intent intent = new Intent();
	                setResult(RESULT_OK, intent);
	                finish();
	            }

	        });
	    }

	
	
}
