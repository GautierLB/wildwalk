package com.example.wildwalk;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class StatsGlobalActivity extends Fragment{
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
   	 
	    View StatsGlobal = inflater.inflate(R.layout.activity_stats_global, container, false);
        ((TextView)StatsGlobal.findViewById(R.id.textView)).setText("Stats Global");
        return StatsGlobal;
}
}


