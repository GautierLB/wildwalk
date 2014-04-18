package com.example.wildwalk.model;

import java.util.Date;

public class Point {
	private int m_idPoint;
	private double m_latitude;
	private double m_altitude;
	private double m_longitude;
	private Date m_datePoint;
	
	public Point(int id, double latitude, double altitude, double longitude) {
		this.m_idPoint = id;
		this.m_latitude = latitude;
		this.m_altitude = altitude;
		this.m_longitude = longitude;
		this.m_datePoint = new Date();
	}

	/**
	 * @return the m_idPoint
	 */
	public int getidPoint() {
		return m_idPoint;
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
	
}
