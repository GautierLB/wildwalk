package com.example.wildwalk;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GlobalStatsAdapter extends BaseAdapter {

	private Context m_context; 
    private int m_layoutResourceId;
    GlobalStats data[] = null;
    public ViewHolder m_holder;
    
    public GlobalStatsAdapter(Context context, int layoutResourceId,  GlobalStats data[]) {
        this.m_layoutResourceId = layoutResourceId;
        this.m_context = context;
        this.data = data;
    }
    
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	
    	
        
        if(convertView == null)
        {
        	m_holder = new ViewHolder();	
        	LayoutInflater inflater = (LayoutInflater)m_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        	convertView = inflater.inflate(R.layout.activity_listview_global_cell, parent, false);
            
        	m_holder.statName = (TextView)convertView.findViewById(R.id.statName);
            m_holder.statValue = (TextView)convertView.findViewById(R.id.statValue);
              
            convertView.setTag(m_holder);
        }
        
        else {
            m_holder = (ViewHolder) convertView.getTag();
        }
        
        
        GlobalStats globalStats = data[position];
        m_holder.statName.setText(globalStats.statName);
        m_holder.statValue.setText(globalStats.statValue);
        

        return convertView;
        
        
        
    }

    public long getItemId(int position) {
        return position;
    }
   
	public class ViewHolder {
		TextView statName;
		TextView statValue;
	    
	}

	@Override
	public int getCount() {
		return data.length;
	}


	@Override
	public Object getItem(int position) {
		return data[position];
	}

    

}
