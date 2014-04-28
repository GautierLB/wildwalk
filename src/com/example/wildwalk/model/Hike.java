package com.example.wildwalk.model;

import java.util.ArrayList;
import java.util.Date;

public class Hike {
	private String m_nameHike;
	private Date m_dateHike;
	private Section m_currentSection;
	private double m_kmHike;
	protected int m_sectionLength;
	private ArrayList<Section> m_sections;

	public Hike(int id, Section section) {
		this.m_dateHike = new Date();
		this.m_nameHike = this.m_dateHike.toString();
		this.m_kmHike = 0;
		this.m_sectionLength = 50;
		this.m_sections = new ArrayList<Section>();
		this.m_currentSection = section;
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
		m_currentSection = new Section (point);
	}

}
