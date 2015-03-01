package com.example.hackmanexe.action;

import com.example.hackmanexe.MainActivity;
import com.example.hackmanexe.fieldobject.Enemy;
import com.example.hackmanexe.fieldobject.FieldObject;
import com.example.hackmanexe.fieldobject.Player;

public class PaladinSword extends RelativePositionAttack {

	public PaladinSword(MainActivity activity,FieldObject fieldObject) {
		super(activity, 10, 0, null, fieldObject);
		if (this.fieldObject instanceof Player)
			this.range = "pPaladinSword";
		else if (this.fieldObject instanceof Enemy)
			this.range = "ePaladinSword";
	}
}
