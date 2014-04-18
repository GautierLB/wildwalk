package com.example.wildwalk;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("NewApi")
public class HikeListAdapter extends BaseAdapter {
	
	private Context m_context; 
    private int m_layoutResourceId;
    List<Hike> m_hikeList;
    
    public HikeListAdapter(Context context, int layoutResourceId, List<Hike> hikeList) {
        this.m_layoutResourceId = layoutResourceId;
        this.m_context = context;
        this.m_hikeList = hikeList;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	
    	ViewHolder holder;
        
        if(convertView == null)
        {
        	holder = new ViewHolder();	
        	LayoutInflater inflater = (LayoutInflater)m_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        	
        	if (position%2 == 0){
        		convertView = inflater.inflate(R.layout.activity_listview_cell_left, parent, false);
        	}
        	else if (position%2 == 1){
        		convertView = inflater.inflate(R.layout.activity_listview_cell_right, parent, false);
        	}
            
            holder.imageHike = (ImageView)convertView.findViewById(R.id.imageHike);
            holder.nameHike = (TextView)convertView.findViewById(R.id.nameHike);
            holder.dateHike = (TextView)convertView.findViewById(R.id.dateHike);
            holder.kmHike = (TextView)convertView.findViewById(R.id.kmHike);
            holder.position = (TextView)convertView.findViewById(R.id.position);
              
            convertView.setTag(holder);
        }
        
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        
        
        holder.imageHike.setId(m_hikeList.get(position).getImageHike());
        holder.nameHike.setText(m_hikeList.get(position).getNameHike());
        holder.dateHike.setText(String.valueOf(m_hikeList.get(position).getDateHike()));
        holder.kmHike.setText(String.valueOf(m_hikeList.get(position).getKmHike()));
        holder.position.setText(String.valueOf(position));
        

        return convertView;
        
    }
        
    public int getCount() {
        return m_hikeList.size();
    }

    public Hike getItem(int position) {
        return m_hikeList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }
   
	private class ViewHolder {
		ImageView imageHike;
	    TextView nameHike;
	    TextView dateHike;
	    TextView kmHike;
	    TextView position;
	    
	}

    

}
