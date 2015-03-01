package com.example.hackmanexe.action;

import android.app.Activity;

import com.example.hackmanexe.fieldobject.Enemy;
import com.example.hackmanexe.fieldobject.FieldObject;
import com.example.hackmanexe.fieldobject.Player;

public class BambooLance extends AbsolutePositionAttack {

	public BambooLance(Activity activity, FieldObject fieldObject) {
		super(activity, 10, 0, null, fieldObject);
		if(this.fieldObject instanceof Player)
			this.range = "5,11,17";
		else if(this.fieldObject instanceof Enemy)
			this.range = "0,5,12";
	}

}
