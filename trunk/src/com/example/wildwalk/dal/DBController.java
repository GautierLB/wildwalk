package com.example.wildwalk.dal;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBController {

	private static DBController s_dbController;
	private SQLiteDatabase m_database;
	private MySQLiteHelper m_dbHelper;

	private DBController(Context context) {
		m_dbHelper = new MySQLiteHelper(context);
	}

	public static DBController Get(Context context) {
		if (s_dbController == null) {
			s_dbController = new DBController(context);
		}
		return s_dbController;
	}

	public void open() throws SQLException {
		m_database = m_dbHelper.getWritableDatabase();
	}

	public void close() {
		m_database.close();
	}

	public void execInsert(String table, ContentValues content) {
		m_database.insert(table, null, content);
	}

	public Cursor execRawQuery(String query) {
		return this.m_database.rawQuery(query, null);
	}

	public Cursor execSelect(String table, String[] columns, String selection, String[] selectionArgs,
			String groupBy, String having, String orderBy) {
		return this.m_database.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
		
	}

	public int execUpdate(String table, ContentValues content, String where, String[] whereArgs) {
		return this.m_database.update(table, content, where, whereArgs);	
	}

}
