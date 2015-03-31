package com.example.hackmanexe.action;

import android.app.Activity;

import com.example.hackmanexe.fieldobject.Enemy;
import com.example.hackmanexe.fieldobject.FieldObject;
import com.example.hackmanexe.fieldobject.Opponent;
import com.example.hackmanexe.fieldobject.Player;

public class PaladinSword extends RelativePositionAttack {

	public PaladinSword(Activity activity,FieldObject fieldObject) {
		super(activity, 10, 0, null, fieldObject);
		if (this.fieldObject instanceof Player)
			this.range = "pPaladinSword";
		else if (this.fieldObject instanceof Enemy|| this.fieldObject instanceof Opponent)
			this.range = "ePaladinSword";
	}
}
