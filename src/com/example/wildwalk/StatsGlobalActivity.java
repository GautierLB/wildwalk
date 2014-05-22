package com.example.wildwalk;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.wildwalk.model.Hike;
import com.example.wildwalk.model.Section;

public class StatsGlobalActivity extends Fragment{
	
	private ListView gStatListView;
	
	
    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
   	 
	    View statsGlobal = inflater.inflate(R.layout.activity_stats_global, container, false);
	    
	    GlobalStats globalStats_data[] = new GlobalStats[]
	            {
	    		new GlobalStats("Total Hikes Done : ", Integer.toString(Hike.getNbHike(this.getActivity()))),
	    		new GlobalStats("Total Difference in Height : ", Integer.toString(Section.getAllDifferenceInHieght(this.getActivity()))),
	    		new GlobalStats("Total Distance : ", Integer.toString(Section.getAlldistance(this.getActivity()))),
	    		new GlobalStats("Average Speed : ", Integer.toString(Section.getAllAverageSpeed(this.getActivity())))
	            };
        
	    gStatListView = (ListView)statsGlobal.findViewById(R.id.globalListView);
				
	    GlobalStatsAdapter adapter = new GlobalStatsAdapter(getActivity(),R.layout.activity_stats_global, globalStats_data);

		gStatListView.setAdapter(adapter);
        
        //Hike test = Hike.getHikeFromDB(1, this.getActivity());
        int i = 1;
        return statsGlobal;
    }
}


