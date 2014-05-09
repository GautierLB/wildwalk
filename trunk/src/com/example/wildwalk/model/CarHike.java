package com.example.wildwalk.model;

import android.content.Context;


public class CarHike extends Hike {

	public CarHike(Section section, Context context) {
		super(section, context);
		this.m_sectionLength = 200;
	}

}
