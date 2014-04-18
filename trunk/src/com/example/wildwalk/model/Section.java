package com.example.wildwalk.model;

public class Section {
	private int m_idSection;
	private Point m_firstPoint;
	private Point m_lastPoint;
	
	public Section (int id, Point firstPoint)
	{
		this.m_idSection = id;
		this.m_firstPoint = firstPoint;
	}
	
	/**
	 * @return the m_idSection
	 */
	public int getidSection() {
		return m_idSection;
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
	public Point getlastPoint() {
		return m_lastPoint;
	}
	
}
