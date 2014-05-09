package com.example.wildwalk.model;

import java.util.ArrayList;
import java.util.Date;

import com.example.wildwalk.dal.DBController;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class Hike {

	public static final String TABLE_NAME = "HIKE";
	public static final String COL_ID = "id_hike";
	public static final String COL_NAME = "name_hike";
	public static final String COL_DATE = "date_hike";
	public static final String COL_KM = "km_hike";

	private static final int NUM_COL_ID = 0;
	private static final int NUM_COL_NAME = 1;
	private static final int NUM_COL_DATE = 2;
	private static final int NUM_COL_KM = 3;

	private int m_idHike;
	private String m_nameHike;
	private Date m_dateHike;
	private Section m_currentSection;
	private double m_kmHike;
	protected int m_sectionLength;
	private Context m_context;
	private ArrayList<Section> m_sections;
	private Boolean m_exctractedFromDB;

	public Hike(Section section, Context context) {
		this.m_dateHike = new Date();
		this.m_nameHike = this.m_dateHike.toString();
		this.m_kmHike = 0;
		this.m_sectionLength = 50;
		this.m_sections = new ArrayList<Section>();
		this.m_currentSection = section;
		this.m_context = context;
		this.m_idHike = (this.getLastId());
		this.m_exctractedFromDB = false;
	}

	public Hike(int id, String name, String date, int km, Context context) {
		this.m_idHike = id;
		this.m_nameHike = name;
		this.m_kmHike = km;
		this.m_context = context;
		this.m_exctractedFromDB = true;
	}

	private int getLastId() {
		DBController db = DBController.Get(this.m_context);
		db.open();
		String query = "SELECT MAX(" + Hike.COL_ID + ") FROM " + Hike.TABLE_NAME;
		int retour = 1;
		try {
			retour = (db.execRawQuery(query).getInt(Hike.NUM_COL_ID))+1;
		} catch (IndexOutOfBoundsException e) {
		}
		db.close();
		return retour;
	}

	public static Hike getHikeFromDB(int id, Context context) {
		DBController db = DBController.Get(context);
		db.open();
		String selection = Hike.COL_ID + " = " + id;
		String[] columns = { Hike.COL_ID, Hike.COL_NAME, Hike.COL_DATE,
				Hike.COL_KM };
		Cursor result = db.execSelect(Hike.TABLE_NAME, columns, selection,
				null, "", "", "");
		Hike retour = new Hike(result.getInt(Hike.NUM_COL_ID),
				result.getString(Hike.NUM_COL_NAME),
				result.getString(Hike.NUM_COL_DATE),
				result.getInt(Hike.NUM_COL_KM), context);
		db.close();
		return retour;
	}

	/**
	 * @return the m_nameHike
	 */
	public String getnameHike() {
		return m_nameHike;
	}

	/**
	 * @return the m_dateHike
	 */
	public Date getdateHike() {
		return m_dateHike;
	}

	/**
	 * @return the m_kmHike
	 */
	public double getkmHike() {
		return m_kmHike;
	}

	/**
	 * @return the m_sectionLength
	 */
	public int getsectionLength() {
		return m_sectionLength;
	}

	/**
	 * @return the m_sections
	 */
	public ArrayList<Section> getsections() {
		return m_sections;
	}

	public void stop() {

	}

	public void addPoint(Point point) {
		m_currentSection.setLastPoint(point);
		m_sections.add(m_currentSection);
		m_currentSection = new Section(point, this.m_context);
	}

	public static Hike getHikeFromDB() {
		return null;
	}

	public void saveHike() {
		if (this.m_exctractedFromDB) {
			this.updateHike();
		} else {
			DBController db = DBController.Get(this.m_context);
			db.open();
			ContentValues hikeContent = new ContentValues();
			hikeContent.putNull(Hike.COL_ID);
			hikeContent.put(Hike.COL_NAME, this.m_nameHike);
			hikeContent.put(Hike.COL_DATE, this.m_dateHike.toString());
			hikeContent.put(Hike.COL_KM, this.m_kmHike);
			db.execInsert("HIKE", hikeContent);
			for (Section section : this.m_sections) {
				section.saveSection(this.m_idHike);
			}
			db.close();
		}

	}

	private void updateHike() {
		if (!this.m_exctractedFromDB) {
			DBController db = DBController.Get(this.m_context);
			db.open();
			ContentValues hikeContent = new ContentValues();
			hikeContent.put(Hike.COL_NAME, this.m_nameHike);
			String where = Hike.COL_ID + " = " + this.m_idHike;
			db.execUpdate("HIKE", hikeContent, where, null);
			db.close();
		} else {
			this.saveHike();
		}
	}

	public static Hike cursorToHike(Cursor c) {
		return null;
		/*
		 * if (c.getCount() == 0) return null;
		 * 
		 * c.moveToFirst(); Hike hike = new Hike();
		 * 
		 * hike.setIdHike(c.getInt(NUM_COL_ID));
		 * hike.setNameHike(c.getString(NUM_COL_NAME));
		 * hike.setDateHike(c.getInt(NUM_COL_DATE));
		 * hike.setKmHike(c.getInt(NUM_COL_KM));
		 * 
		 * c.close();
		 * 
		 * return hike;
		 */
	}

	public int getIdHike() {
		return m_idHike;
	}

	public void setIdHike(int m_idHike) {
		this.m_idHike = m_idHike;
	}

}
