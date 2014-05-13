package com.example.wildwalk;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.example.wildwalk.model.Hike;

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
        
        //Hike test = Hike.getHikeFromDB(1, this.getActivity());
        int i = 1;
        return statsGlobal;
    }
}


