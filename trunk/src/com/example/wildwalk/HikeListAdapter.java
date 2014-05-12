package com.example.wildwalk;

import java.util.List;

import com.example.wildwalk.model.Hike;

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
    public ViewHolder m_holder;
    
    public HikeListAdapter(Context context, int layoutResourceId, List<Hike> hikeList) {
        this.m_layoutResourceId = layoutResourceId;
        this.m_context = context;
        this.m_hikeList = hikeList;
    }
    
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	
    	
        
        if(convertView == null)
        {
        	m_holder = new ViewHolder();	
        	LayoutInflater inflater = (LayoutInflater)m_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        	convertView = inflater.inflate(R.layout.activity_listview_cell_left, parent, false);
            
        	m_holder.imgHike = (ImageView)convertView.findViewById(R.id.imageHike);
            m_holder.nameHike = (TextView)convertView.findViewById(R.id.nameHike);
            m_holder.dateHike = (TextView)convertView.findViewById(R.id.dateHike);
            m_holder.kmHike = (TextView)convertView.findViewById(R.id.kmHike);
              
            convertView.setTag(m_holder);
        }
        
        else {
            m_holder = (ViewHolder) convertView.getTag();
        }
        
        m_holder.imgHike.setId(R.id.imageHike);
        m_holder.nameHike.setText(m_hikeList.get(position).getNameHike());
        m_holder.dateHike.setText(String.valueOf(m_hikeList.get(position).getDateHike()));
        m_holder.kmHike.setText(String.valueOf(m_hikeList.get(position).getKmHike()));
        

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
   
	public class ViewHolder {
		ImageView imgHike;
	    TextView nameHike;
	    TextView dateHike;
	    TextView kmHike;
	    TextView position;
	    
	}

    

}
