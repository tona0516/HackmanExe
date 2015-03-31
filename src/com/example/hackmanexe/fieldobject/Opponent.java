package com.example.hackmanexe.fieldobject;

import android.app.Activity;

import com.example.hackmanexe.PanelInfo;

public class Opponent extends FieldObject {

	private Activity activity;

	public Opponent(Activity activity, PanelInfo _currentFrameInfo, int _HP) {
		super(_currentFrameInfo, _HP);
		this.activity = activity;
	}

	@Override
	public void deathProcess() {
		super.deathProcess();
	}
}
