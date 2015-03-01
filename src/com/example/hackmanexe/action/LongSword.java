package com.example.hackmanexe.action;

import android.app.Activity;

import com.example.hackmanexe.fieldobject.Enemy;
import com.example.hackmanexe.fieldobject.FieldObject;
import com.example.hackmanexe.fieldobject.Player;

public class LongSword extends RelativePositionAttack {

	public LongSword(Activity activity, FieldObject fieldObject) {
		super(activity, 10, 0, null, fieldObject);
		if (this.fieldObject instanceof Player)
			this.range = "pLongSword";
		else if (this.fieldObject instanceof Enemy)
			this.range = "eLongSword";
	}
}
