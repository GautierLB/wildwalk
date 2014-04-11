package com.example.wildwalk;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends FragmentActivity{
	
	ViewPager Tab;
    TabPagerAdapter TabAdapter;
	ActionBar actionBar;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TabAdapter = new TabPagerAdapter(getSupportFragmentManager());
        
        Tab = (ViewPager)findViewById(R.id.pager);
        
        Tab.setOnPageChangeListener( new ViewPager.SimpleOnPageChangeListener() {
        	@Override
            public void onPageSelected(int position) { 
        		actionBar = getActionBar();
                actionBar.setSelectedNavigationItem(position);
            }
        });
        
        Tab.setAdapter(TabAdapter);
        
        actionBar = getActionBar();
        //Enable Tabs on Action Bar
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        ActionBar.TabListener tabListener = new ActionBar.TabListener(){
        	
			@Override
			public void onTabReselected(android.app.ActionBar.Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub	
			}

			@Override
			 public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
	            Tab.setCurrentItem(tab.getPosition());
	        }

			@Override
			public void onTabUnselected(android.app.ActionBar.Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub
			}};
			
			//Add New Tab
			actionBar.addTab(actionBar.newTab().setText("START").setTabListener(tabListener));
			actionBar.addTab(actionBar.newTab().setText("STATS").setTabListener(tabListener));
			actionBar.addTab(actionBar.newTab().setText("STATS GLOBALS").setTabListener(tabListener));

    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_settings:
			
			Context context = getApplicationContext();
			CharSequence text = "SETTINGS";
			int duration = Toast.LENGTH_SHORT;

			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
			
			// Comportement du bouton "Paramètres"
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}

