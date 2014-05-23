package com.example.wildwalk;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import com.example.wildwalk.model.Hike;
import com.example.wildwalk.model.Section;

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

	public ListView hikeListView;
	List<Hike> hikeList = new ArrayList<Hike>();
	private HikeListAdapter adapter;

	public void HikeFiller() {

		hikeList.clear();
		hikeList.addAll(Hike.getAllHikes(getActivity()));
	}
	


	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View stats = inflater.inflate(R.layout.activity_hike_list, container,
				false);

		hikeListView = (ListView) stats.findViewById(R.id.hikeListView);
		if (hikeList != null) {

			HikeFiller();

			adapter = new HikeListAdapter(getActivity(),
					R.layout.activity_hike_list, hikeList);

			hikeListView.setAdapter(adapter);
			adapter.notifyDataSetChanged();
			

			hikeListView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View v,
						int position, long id) {

					Intent intent = new Intent(v.getContext(),
							HikeStatActivity.class);
					intent.putExtra("Hike", adapter.getItem(position));
					refill(hikeList);
					startActivity(intent);
				}
			});
			
			
			
		}

		return stats;

	}
	
	private void refill(List<Hike> hikeList) {
		hikeList.clear();
		hikeList.addAll(Hike.getAllHikes(getActivity()));
		adapter.notifyDataSetChanged();
	}
	
	@Override
	public void onResume(){
		super.onResume();
		refill(hikeList);
	}
	
	@Override
	public void onPause(){
		super.onPause();
		refill(hikeList);
	}


	

}
