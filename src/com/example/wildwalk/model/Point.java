package com.example.wildwalk.model;

import java.util.Date;

import com.example.wildwalk.dal.DBController;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class Point {

	public static final String TABLE_NAME = "POINT";
	public static final String COL_ID = "id_point";
	public static final String COL_ALTITUDE = "altitude_point";
	public static final String COL_LATITUDE = "latitude_point";
	public static final String COL_LONGITUDE = "longitude_point";
	public static final String COL_DATE = "date_point";

	private static final int NUM_COL_ID = 0;
	private static final int NUM_COL_ALTITUDE = 1;
	private static final int NUM_COL_LATITUDE = 2;
	private static final int NUM_COL_LONGITUDE = 3;
	private static final int NUM_COL_DATE = 4;

	private int m_idPoint;
	private double m_latitude;
	private double m_altitude;
	private double m_longitude;
	private Date m_datePoint;
	private Context m_context;
	private Boolean m_exctractedFromDB;

	public Point(double latitude, double altitude, double longitude,
			Context context) {
		this.m_latitude = latitude;
		this.m_altitude = altitude;
		this.m_longitude = longitude;
		this.m_datePoint = new Date();
		this.m_context = context;
		this.m_idPoint = this.getLastId();
		this.m_exctractedFromDB = false;
	}

	public Point(int id, double altitude, double latitude, double longitude,
			String date, Context context) {
		this.m_idPoint = id;
		this.m_altitude = altitude;
		this.m_latitude = latitude;
		this.m_longitude = longitude;
		this.m_context = context;
		this.m_exctractedFromDB = true;

	}

	private int getLastId() {
		DBController db = DBController.Get(this.m_context);
		db.open();
		String query = "SELECT MAX(" + Point.COL_ID + ") FROM "
				+ Point.TABLE_NAME;
		int retour = 1;
		try {
			retour = (db.execRawQuery(query).getInt(Point.NUM_COL_ID))+1;
		} catch (IndexOutOfBoundsException e) {
		}
		db.close();
		return retour;
	}

	public static Point getPointFromDB(int id, Context context) {
		DBController db = DBController.Get(context);
		db.open();
		String selection = Point.COL_ID + " = " + id;
		String[] columns = { Point.COL_ID, Point.COL_ALTITUDE,
				Point.COL_LATITUDE, Point.COL_LONGITUDE, Point.COL_DATE };
		Cursor result = db.execSelect(Point.TABLE_NAME, columns, selection,
				null, "", "", "");
		Point retour = new Point(result.getInt(Point.NUM_COL_ID),
				result.getDouble(Point.NUM_COL_ALTITUDE),
				result.getDouble(Point.NUM_COL_LATITUDE),
				result.getDouble(Point.NUM_COL_LONGITUDE),
				result.getString(Point.NUM_COL_DATE), context);
		db.close();
		return retour;
	}

	/**
	 * @return the m_latitude
	 */
	public double getlatitude() {
		return m_latitude;
	}

	/**
	 * @return the m_altitude
	 */
	public double getaltitude() {
		return m_altitude;
	}

	/**
	 * @return the m_longitude
	 */
	public double getlongitude() {
		return m_longitude;
	}

	/**
	 * @return the m_datePoint
	 */
	public Date getdatePoint() {
		return m_datePoint;
	}

	public void savePoint() {
		if (this.m_exctractedFromDB) {
			this.updatePoint();
		} else {
			DBController db = DBController.Get(this.m_context);
			db.open();
			ContentValues pointContent = new ContentValues();
			pointContent.putNull(Point.COL_ID);
			pointContent.put(Point.COL_ALTITUDE, this.m_altitude);
			pointContent.put(Point.COL_LATITUDE, this.m_latitude);
			pointContent.put(Point.COL_LONGITUDE, this.m_longitude);
			pointContent.put(Point.COL_DATE, this.m_datePoint.toString());
			db.execInsert("POINT", pointContent);
			db.close();
		}

	}

	private void updatePoint() {
		// TODO Auto-generated method stub

	}

	public int getIdPoint() {
		return m_idPoint;
	}

	public void setIdPoint(int m_idPoint) {
		this.m_idPoint = m_idPoint;
	}

}
