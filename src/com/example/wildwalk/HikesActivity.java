package com.example.wildwalk;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import com.example.wildwalk.model.Hike;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class HikesActivity extends Fragment {
	
	private ListView hikeListView;
	List<Hike> hikeList = new ArrayList<Hike>();
	
	private void HikeFiller(){
		
		hikeList.clear();
		Date now = new Date();
		hikeList.add(Hike.getHikeFromDB(1,this.getActivity()));
		if(Hike.getHikeFromDB(1,this.getActivity())==null){
			int i =1;
		}
		/*for (int i=1; i<=100;i++){
		hikeList.add(new Hike(i,"Hike "+i, now, 50, this.getActivity()));
		}*/
	}
	
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View stats = inflater.inflate(R.layout.activity_hike_list, container, false);
        
		hikeListView = (ListView)stats.findViewById(R.id.hikeListView);
		
		HikeFiller();
				
		final HikeListAdapter adapter = new HikeListAdapter(getActivity(),R.layout.activity_hike_list, hikeList);

        hikeListView.setAdapter(adapter);
        
        hikeListView.setOnItemClickListener( new OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position,
                    long id) 
            {
            	
            	Intent intent = new Intent(v.getContext(), HikeStatActivity.class);
            	intent.putExtra("Hike", adapter.getItem(position));
                startActivity(intent);
            }
        } );
        
        return stats;
        
        
         
    }
    
    
    
    
}
