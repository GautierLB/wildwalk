package com.example.wildwalk;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
public class TabPagerAdapter extends FragmentStatePagerAdapter{

	
    public TabPagerAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int i) {
		switch (i) {
        case 0:
        	//Fragment for Stats Tab
            return new HikesActivity();
        case 1:
        	//Fragement for Start Tab
            return new StartActivity();
        case 2:
            //Fragment for StatsGlobal Tab
            return new StatsGlobalActivity();
        }
		return null;
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 3; //No of Tabs
	}
}

