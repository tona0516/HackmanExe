package com.example.hackmanexe.action;

import android.app.Activity;

import com.example.hackmanexe.fieldobject.Enemy;
import com.example.hackmanexe.fieldobject.FieldObject;
import com.example.hackmanexe.fieldobject.Opponent;
import com.example.hackmanexe.fieldobject.Player;

public class Boomerang extends AbsolutePositionAttack {

	public Boomerang(Activity activity, FieldObject fieldObject) {
		super(activity, 10, 100, null, fieldObject);
		if(this.fieldObject instanceof Player)
			this.range = "12,13,14,15,16,17,11,5,4,3,2,1,0";
		else if(this.fieldObject instanceof Enemy|| this.fieldObject instanceof Opponent)
			this.range = "5,4,3,2,1,0,6,12,13,14,15,16,17";
	}

}
