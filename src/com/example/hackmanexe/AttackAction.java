package com.example.hackmanexe;

import android.util.Log;


/**
 * @author meem 攻撃の情報を
 */
public class AttackAction extends Action {
	private int power; // 攻撃力
	private float msec; // 有効時間
	private String range; // 範囲
	public AttackAction(int power, float time, String range) {
		super();
		setAttackPower(power);
		setTime(time);
		setRange(range);
	}

	public void attack() {
		Log.d(this.toString(), "attack");
		}

	public int getAttackPower() {
		return power;
	}

	public void setAttackPower(int attackPower) {
		this.power = attackPower;
	}

	public float getTime() {
		return msec;
	}

	public void setTime(float time) {
		this.msec = time;
	}

	public String getRange() {
		return range;
	}

	public void setRange(String range) {
		this.range = range;
	}
}
