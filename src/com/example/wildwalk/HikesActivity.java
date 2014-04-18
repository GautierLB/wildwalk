package com.example.wildwalk;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class HikesActivity extends Fragment {
	
	private ListView hikeListView;
	List<Hike> hikeList = new ArrayList<Hike>();
	
	private void HikeFiller(){
		
		hikeList.clear();
		for (int i=1; i<=100;i++){
		hikeList.add(new Hike(i, R.drawable.ic_launcher ,"Hike "+i, 18042014, 50));
		}
	}
	
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View stats = inflater.inflate(R.layout.activity_hike_list, container, false);
        //((TextView)stats.findViewById(R.id.textView)).setText("STATS");
        
		hikeListView = (ListView)stats.findViewById(R.id.hikeListView);
		
		HikeFiller();
				
		HikeListAdapter adapter = new HikeListAdapter(getActivity(),R.layout.activity_hike_list, hikeList);

        hikeListView.setAdapter(adapter);
        
        return stats;
    }
}
