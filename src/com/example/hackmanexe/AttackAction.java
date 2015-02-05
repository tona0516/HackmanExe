package com.example.hackmanexe;
/**
 * @author meem
 * 攻撃の情報を
 */
public class AttackAction extends Action{
	private int power; //攻撃力
	private boolean[] range; //範囲
	private float time; //有効時間
	public AttackAction(int power, boolean[] range, float time) {
		super();
		this.power = power;
		this.range = range;
		this.setTime(time);
	}

	public int getAttackPower() {
		return power;
	}

	public void setAttackPower(int attackPower) {
		this.power = attackPower;
	}

	public boolean[] getRange() {
		return range;
	}

	public void setRange(boolean[] range) {
		this.range = range;
	}

	public float getTime() {
		return time;
	}

	public void setTime(float time) {
		this.time = time;
	}
}
