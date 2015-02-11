package com.example.hackmanexe;


/**
 * 攻撃チップの情報を保持するクラス
 * @author meem
 */
public class AttackAction extends Action {
	protected int power; // 攻撃力

	public AttackAction(int power) {
		super();
		this.power = power;
	}

	public int getAttackPower() {
		return power;
	}

	public void setAttackPower(int attackPower) {
		this.power = attackPower;
	}
}
