package com.example.hackmanexe;


/**
 * オブジェクトの位置から攻撃場所を指定するクラス
 * パラディンソードとかの実装にこれをextends
 * @author meem
 *
 */
public class RelativePositionAttack extends AttackAction{

	public RelativePositionAttack(int power, Player player) {
		super(power);
	}

	public void attack(){
	}
}
