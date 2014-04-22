package com.example.wildwalk;

import java.io.Serializable;

import android.database.Cursor;

public class Hike implements Serializable{

	public static final String TABLE_NAME = "HIKE";
	public static final String COL_ID = "id_hike";
	public static final String COL_IMAGE = "image_hike";
	public static final String COL_NAME = "name_hike";
	public static final String COL_DATE = "date_hike";
	public static final String COL_KM = "km_hike";
	private static final int NUM_COL_ID = 0;
	private static final int NUM_COL_IMAGE = 1;
	private static final int NUM_COL_NAME = 2;
	private static final int NUM_COL_DATE = 3;
	private static final int NUM_COL_KM = 4;
	
	private int m_idHike;
	private int m_imageHike;
	private String m_nameHike;
	private int m_dateHike;
	private int m_kmHike;
	
	public Hike() {}

	public Hike(int id,int image, String name, int date, int km) {
		this.m_idHike = id;
		this.m_imageHike = image;
		this.m_nameHike = name;
		this.m_dateHike = date;
		this.m_kmHike = km;
	}

	public int getIdHike() {
		return this.m_idHike;
	}
	
	public int getImageHike() {
		return this.m_imageHike;
	}

	public String getNameHike() {
		return this.m_nameHike;
	}

	public int getDateHike() {
		return this.m_dateHike;
	}

	public int getKmHike() {
		return this.m_kmHike;
	}

	public void setIdHike(int id) {
		this.m_idHike = id;
	}
	
	public void setImageHike(int image) {
		this.m_idHike = image;
	}

	public void setNameHike(String name) {
		this.m_nameHike = name;
	}

	public void setDateHike(int date) {
		this.m_dateHike = date;
	}

	public void setKmHike(int km) {
		this.m_kmHike = km;
	}

	public static Hike getHikeFromDB() {
		return null;
	}

	public void saveHike() {

	}

	public static Hike cursorToHike(Cursor c) {
		if (c.getCount() == 0)
			return null;

		c.moveToFirst();
		Hike hike = new Hike();
		
		hike.setIdHike(c.getInt(NUM_COL_ID));
		hike.setImageHike(c.getInt(NUM_COL_IMAGE));
		hike.setNameHike(c.getString(NUM_COL_NAME));
		hike.setDateHike(c.getInt(NUM_COL_DATE));
		hike.setKmHike(c.getInt(NUM_COL_KM));
		
		c.close();

		return hike;
	}
}
