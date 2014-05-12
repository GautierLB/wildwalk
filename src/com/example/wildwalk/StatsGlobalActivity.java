package com.example.wildwalk;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class StatsGlobalActivity extends Fragment{
	
	private ListView gStatListView;
	
	
    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
   	 
	    View statsGlobal = inflater.inflate(R.layout.activity_stats_global, container, false);
	    
	    GlobalStats globalStats_data[] = new GlobalStats[]
	            {
	    		new GlobalStats("Total Hikes Done : ", "100"),
	    		new GlobalStats("Foot Hikes Done : ", "50"),
	    		new GlobalStats("Bike Hikes Done : ", "30"),
	    		new GlobalStats("Car Hikes Done : ", "20"),
	    		new GlobalStats("Total Difference in Height : ", "10 000 m"),
	    		new GlobalStats("Total Distance : ", "3000 Km"),
	    		new GlobalStats("Average Speed : ", "12 Km/h")
	            };
        
	    gStatListView = (ListView)statsGlobal.findViewById(R.id.globalListView);
				
	    GlobalStatsAdapter adapter = new GlobalStatsAdapter(getActivity(),R.layout.activity_stats_global, globalStats_data);

		gStatListView.setAdapter(adapter);
        
        
        return statsGlobal;
    }
}


