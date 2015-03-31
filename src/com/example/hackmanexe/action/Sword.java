package com.example.hackmanexe.action;

import android.app.Activity;

import com.example.hackmanexe.fieldobject.Enemy;
import com.example.hackmanexe.fieldobject.FieldObject;
import com.example.hackmanexe.fieldobject.Opponent;
import com.example.hackmanexe.fieldobject.Player;

public class Sword extends RelativePositionAttack {

	public Sword(Activity activity, FieldObject fieldObject) {
		super(activity, 10, 0, null, fieldObject);
		if (this.fieldObject instanceof Player)
			this.range = "pSword"; //Player攻撃時の攻撃範囲
		else if (this.fieldObject instanceof Enemy|| this.fieldObject instanceof Opponent)
			this.range = "eSword"; //Enemy攻撃時の攻撃範囲
	}
}
