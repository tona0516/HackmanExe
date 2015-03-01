package com.example.hackmanexe.action;

import android.app.Activity;

import com.example.hackmanexe.fieldobject.Enemy;
import com.example.hackmanexe.fieldobject.FieldObject;
import com.example.hackmanexe.fieldobject.Player;

public class WideSword extends RelativePositionAttack {

	public WideSword(Activity activity,FieldObject fieldObject) {
		super(activity, 10, 0, null, fieldObject);
		if (this.fieldObject instanceof Player)
			this.range = "pWideSword";
		else if (this.fieldObject instanceof Enemy)
			this.range = "eWideSword";
	}
}
