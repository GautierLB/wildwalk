package com.example.wildwalk.dal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "WildWalk";
	private static final int DATABASE_VERSION = 1;

	private static final String CREATE_TABLE_HIKE = "CREATE TABLE 'HIKE' (id_hike INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name_hike VARCHAR, date_hike TIMESTAMP, km_hike INTEGER);";
	private static final String CREATE_TABLE_POINT = "CREATE TABLE 'POINT' (id_point INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, altitude_point INTEGER, latitude_point INTEGER, longitude_point INTEGER, date_point TIMESTAMP);";
	private static final String CREATE_TABLE_SECTION = "CREATE TABLE 'SECTION' (id_section INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, id_hike INTEGER REFERENCES 'HIKE' (id_hike), first_point INTEGER REFERENCES 'POINT' (id_point), last_point INTEGER REFERENCES 'POINT' (id_point));";

	// "INSERT INTO 'HIKE' ( id_hike,name_hike,date_hike,km_hike ) VALUES ( '1','Balade en montagne','1396267800','300' );INSERT INTO 'POINT' ( id_point,altitude_point,latitude_point,longitude_point,date_point ) VALUES ( '1','10','10','10','1396267611' );INSERT INTO 'POINT' ( id_point,altitude_point,latitude_point,longitude_point,date_point ) VALUES ( '2','11','10','11','1396267703' );INSERT INTO 'POINT' ( id_point,altitude_point,latitude_point,longitude_point,date_point ) VALUES ( '3','12','10','12','1396267725' );INSERT INTO 'POINT' ( id_point,altitude_point,latitude_point,longitude_point,date_point ) VALUES ( '4','9','10','13','1396267752' );INSERT INTO 'POINT' ( id_point,altitude_point,latitude_point,longitude_point,date_point ) VALUES ( '5','8','10','14','1396267770' );INSERT INTO 'SECTION' ( id_section,id_hike,first_point,last_point ) VALUES ( '1','1','1','5' );";

	public MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_HIKE);
		db.execSQL(CREATE_TABLE_POINT);
		db.execSQL(CREATE_TABLE_SECTION);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(MySQLiteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS HIKE");
		db.execSQL("DROP TABLE IF EXISTS POINT");
		db.execSQL("DROP TABLE IF EXISTS SECTION");
		onCreate(db);
	}

}
