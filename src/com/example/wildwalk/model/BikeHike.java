package com.example.wildwalk.model;

import android.content.Context;


public class BikeHike extends Hike {

	public BikeHike(Section section, Context context) {
		super(section, context);
		this.m_sectionLength = 100;
	}

}
