package com.example.wildwalk.model;

import android.os.Parcel;
import android.os.Parcelable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.example.wildwalk.dal.DBController;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class Hike implements Parcelable {

	public static final String TABLE_NAME = "HIKE";
	public static final String COL_ID = "id_hike";
	public static final String COL_NAME = "name_hike";
	public static final String COL_DATE = "date_hike";
	public static final String COL_KM = "km_hike";

	private static final int NUM_COL_ID = 0;
	private static final int NUM_COL_NAME = 1;
	private static final int NUM_COL_DATE = 2;
	private static final int NUM_COL_KM = 3;
	// Mon May 12 14:24:46 CEST 2014
	public static SimpleDateFormat DF = new SimpleDateFormat(
			"EEE MMM dd HH:mm:ss yyyy");

	private int m_idHike;
	private String m_nameHike;
	private Date m_dateHike;
	private Section m_currentSection;
	private double m_kmHike;
	protected int m_sectionLength;
	private Context m_context;
	private ArrayList<Section> m_sections;
	private Boolean m_exctractedFromDB;
	private static int lastId = 1;

	public Hike(Section section, Context context) {
		this.m_dateHike = new Date();
		this.m_nameHike = this.m_dateHike.toString();
		this.m_kmHike = 0;
		this.m_sectionLength = 50;
		this.setSections(new ArrayList<Section>());
		this.m_currentSection = section;
		this.m_context = context;
		this.m_idHike = (this.getLastId());
		this.m_exctractedFromDB = false;
	}

	public Hike(int id, String name, Date date, int km, Context context) {
		this.m_idHike = id;
		this.m_nameHike = name;
		this.m_kmHike = km;
		this.m_dateHike = date;
		this.m_context = context;
		this.m_exctractedFromDB = true;
	}

	public Hike(Parcel in) {
		readFromParcel(in);
	}

	private int getLastId() {
		DBController db = DBController.Get(this.m_context);
		db.open();
		String query = "SELECT MAX(" + Hike.COL_ID + ") FROM "
				+ Hike.TABLE_NAME;
		if (Hike.lastId == 1) {
			Cursor lastHike = db.execRawQuery(query);
			lastHike.moveToFirst();
			Hike.lastId = lastHike.getInt(0) +1;		
		} else {
			Hike.lastId++;
		}
		db.close();
		return Hike.lastId;
	}

	public static Hike getHikeFromDB(int id, Context context) {
		DBController db = DBController.Get(context);
		db.open();
		String selection = Hike.COL_ID + " = " + id;
		String[] columns = { Hike.COL_ID, Hike.COL_NAME, Hike.COL_DATE,
				Hike.COL_KM };
		Cursor result = db.execSelect(Hike.TABLE_NAME, columns, selection,
				null, "", "", "");
		Hike retour;
		if (result.moveToFirst()) {
			try {
				String bete = result.getString(NUM_COL_DATE);
				retour = new Hike(result.getInt(Hike.NUM_COL_ID),
						result.getString(Hike.NUM_COL_NAME), DF.parse(bete),
						result.getInt(Hike.NUM_COL_KM), context);
			} catch (ParseException e) {
				e.printStackTrace();
				retour = new Hike(0, "hh", new Date(), 12, context);
			}
		} else {
			retour = new Hike(0, "hh", new Date(), 12, context);
		}
		db.close();
		return retour;

	}

	public static ArrayList<Hike> getAllHikes(Context context) {
		DBController db = DBController.Get(context);
		db.open();
		ArrayList<Hike> retour = new ArrayList<Hike>();
		String selection = "";
		String[] columns = { Hike.COL_ID, Hike.COL_NAME, Hike.COL_DATE,
				Hike.COL_KM };
		Cursor result = db.execSelect(Hike.TABLE_NAME, columns, selection,
				null, "", "", "");
		Hike actual = null;
		while (result.moveToNext()) {
			try {
				String bete = result.getString(NUM_COL_DATE);
				actual = new Hike(result.getInt(Hike.NUM_COL_ID),
						"Randonnée n°" + result.getInt(Hike.NUM_COL_ID), DF.parse(bete),
						result.getInt(Hike.NUM_COL_KM), context);
				ArrayList<Section> sections = Section.getSectionsForHike(
						result.getInt(Hike.NUM_COL_ID), context);
				actual.setSections(sections);
				retour.add(actual);
			} catch (ParseException e) {
				e.printStackTrace();
				actual = new Hike(0, "hh", new Date(), 12, context);
			}
		}
		db.close();
		return retour;
	}

	public static int getNbHike(Context context) {
		DBController db = DBController.Get(context);
		db.open();
		String query = "SELECT COUNT(" + Hike.COL_ID + ") FROM "
				+ Hike.TABLE_NAME;
		Cursor result = db.execRawQuery(query);
		int retour = 0;
		if (result.moveToFirst()) {
			retour = result.getInt(0);
		}
		return retour;
	}

	/**
	 * @return the m_nameHike
	 */
	public String getNameHike() {
		return m_nameHike;
	}

	/**
	 * @return the m_dateHike
	 */
	public Date getDateHike() {
		return m_dateHike;
	}

	/**
	 * @return the m_kmHike
	 */
	public double getKmHike() {
		return m_kmHike;
	}

	/**
	 * @return the m_sectionLength
	 */
	public int getSectionLength() {
		return m_sectionLength;
	}

	/**
	 * @return the m_sections
	 */
	public ArrayList<Section> getSections() {
		return m_sections;
	}

	public void stop(Point end) {
		this.m_currentSection.setLastPoint(end);
		this.m_sections.add(m_currentSection);
		this.saveHike();
	}

	public void addPoint(Point point) {
		m_currentSection.setLastPoint(point);
		getSections().add(m_currentSection);
		m_currentSection = new Section(point, this.m_context);
	}

	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(m_idHike);
		dest.writeString(m_nameHike);
		dest.writeSerializable(m_dateHike);
		dest.writeDouble(m_kmHike);
	}

	public void readFromParcel(Parcel in) {
		m_idHike = in.readInt();
		m_nameHike = in.readString();
		m_dateHike = (Date) in.readSerializable();
		m_kmHike = in.readDouble();
	}

	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
		public Hike createFromParcel(Parcel in) {
			return new Hike(in);
		}

		public Hike[] newArray(int size) {
			return new Hike[size];
		}
	};

	public void saveHike() {
		if (this.m_exctractedFromDB) {
			this.updateHike();
		} else {
			DBController db = DBController.Get(this.m_context);
			db.open();
			ContentValues hikeContent = new ContentValues();
			hikeContent.putNull(Hike.COL_ID);
			hikeContent.put(Hike.COL_NAME, this.m_nameHike);
			String bete = DF.format(this.m_dateHike);
			hikeContent.put(Hike.COL_DATE, DF.format(this.m_dateHike));
			hikeContent.put(Hike.COL_KM, this.m_kmHike);
			db.execInsert("HIKE", hikeContent);
			for (Section section : this.m_sections) {
				section.saveSection(this.m_idHike, this.m_context);
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

	public double getDifferenceInHeight() {
		double result = 0;
		for (Section section : this.getSections()) {
			result += section.getDifferenceInHeight();
		}
		return result;
	}

	public double getAverageSpeed() {
		double result = 0;
		long secondes = this.getLength();
		result = (this.getDistanceHike() / secondes)*3.6;	
		return result;
	}
	
	public long getLength()
	{
		long secondes = (this.m_sections.get(this.m_sections.size()-1).getLastPoint().getdatePoint().getTime()) - (this.m_sections.get(0).getfirstPoint().getdatePoint().getTime());
		return secondes;
	}

	public int getIdHike() {
		return m_idHike;
	}

	public void setIdHike(int m_idHike) {
		this.m_idHike = m_idHike;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setSections(ArrayList<Section> m_sections) {
		this.m_sections = m_sections;
	}
	
	public float getDistanceHike(){
		float retour = 0;
		for (Section section : this.m_sections) {
			retour += section.getDistance();
		}
		return retour;
	}

}
