package com.example.wildwalk;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class StatsActivity extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View stats = inflater.inflate(R.layout.activity_stats, container, false);
        ((TextView)stats.findViewById(R.id.textView)).setText("STATS");
        return stats;
    }
}
