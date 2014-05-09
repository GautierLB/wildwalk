package com.example.wildwalk.model;

import com.example.wildwalk.dal.DBController;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class Section {

	public static final String TABLE_NAME = "SECTION";
	public static final String COL_ID = "id_section";
	public static final String COL_ID_HIKE = "id_hike";
	public static final String COL_FIRST_POINT = "first_point";
	public static final String COL_LAST_POINT = "last_point";

	private static final int NUM_COL_ID = 0;
	private static final int NUM_COL_ID_HIKE = 1;
	private static final int NUM_COL_FIRST_POINT = 2;
	private static final int NUM_COL_LAST_POINT = 3;

	private int m_idSection;
	private Point m_firstPoint;
	private Point m_lastPoint;
	private Context m_context;
	private Boolean m_exctractedFromDB;

	public Section(Point firstPoint, Context context) {
		this.m_firstPoint = firstPoint;
		this.m_idSection = this.getLastId();
		this.m_exctractedFromDB = false;
	}

	public Section(int id, int id_first_point, int id_last_point, Context context) {
		this.m_idSection = id;
		this.m_firstPoint = Point.getPointFromDB(id_first_point, context);
		this.m_lastPoint = Point.getPointFromDB(id_last_point, context);
		this.m_context = context;
		this.m_exctractedFromDB = true;
	}

	private int getLastId() {
		DBController db = DBController.Get(this.m_context);
		db.open();
		String query = "SELECT MAX(" + Section.COL_ID + ") FROM "
				+ Section.TABLE_NAME;
		int retour = 1;
		try {
			retour = (db.execRawQuery(query).getInt(Section.NUM_COL_ID))+1;
		} catch (IndexOutOfBoundsException e) {
		}
		db.close();
		return retour;
	}

	public static Section getPointFromDB(int id, Context context) {
		DBController db = DBController.Get(context);
		db.open();
		String selection = Section.COL_ID + " = " + id;
		String[] columns = { Section.COL_ID, Section.COL_ID_HIKE,
				Section.COL_FIRST_POINT, Section.COL_LAST_POINT };
		Cursor result = db.execSelect(Section.TABLE_NAME, columns, selection,
				null, "", "", "");
		Section retour = new Section(result.getInt(Section.NUM_COL_ID),
				result.getInt(Section.NUM_COL_FIRST_POINT),
				result.getInt(Section.NUM_COL_LAST_POINT), context);
		db.close();
		return retour;
	}

	/**
	 * @return the m_firstPoint
	 */
	public Point getfirstPoint() {
		return m_firstPoint;
	}

	/**
	 * @return the m_lastPoint
	 */
	public Point getLastPoint() {
		return m_lastPoint;
	}

	public void setLastPoint(Point m_lastPoint) {
		this.m_lastPoint = m_lastPoint;
	}

	public void saveSection(int id_hike) {
		if (m_exctractedFromDB) {
			this.updateSection();
		} else {
			DBController db = DBController.Get(this.m_context);
			db.open();
			ContentValues sectionContent = new ContentValues();
			sectionContent.putNull(Section.COL_ID);
			sectionContent.put(Section.COL_ID_HIKE, id_hike);
			sectionContent.put(Section.COL_FIRST_POINT,
					this.m_firstPoint.getIdPoint());
			sectionContent.put(Section.COL_LAST_POINT,
					this.m_lastPoint.getIdPoint());
			db.execInsert("HIKE", sectionContent);
			this.m_firstPoint.savePoint();
			this.m_lastPoint.savePoint();
			db.close();
		}

	}

	private void updateSection() {
		// TODO Auto-generated method stub
		
	}

}