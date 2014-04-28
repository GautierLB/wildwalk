package com.example.wildwalk.model;

public class Section {
	private Point m_firstPoint;
	private Point m_lastPoint;
	
	public Section (Point firstPoint)
	{
		this.m_firstPoint = firstPoint;
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
	
}
