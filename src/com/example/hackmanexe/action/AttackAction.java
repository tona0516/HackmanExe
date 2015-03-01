package com.example.hackmanexe.action;

import android.app.Activity;

import com.example.hackmanexe.ObjectSurfaceView;
import com.example.hackmanexe.fieldobject.Enemy;
import com.example.hackmanexe.fieldobject.FieldItem;
import com.example.hackmanexe.fieldobject.FieldObject;
import com.example.hackmanexe.fieldobject.Player;

/**
 * 攻撃チップの情報を保持するクラス
 *
 * @author meem
 */
abstract class AttackAction extends Action{
	protected int power; // 攻撃力
	protected long interval; // 攻撃範囲が移動するスピード
	protected Activity activity; // UIを描画するActivity
	protected FieldObject fieldObject; // 攻撃者オブジェクト
	protected final static long msec = 1000; // 描画時間

	public AttackAction(Activity activity, int power, long interval,
			FieldObject fieldObject) {
		super();
		this.activity = activity;
		this.power = power;
		this.interval = interval;
		this.fieldObject = fieldObject;
	}

	/**
	 *
	 * @param index
	 *            パネルインデックス 当たり判定メソッド
	 */
	protected boolean judgeConfliction(int index) {
		FieldObject o = ObjectSurfaceView.field.getPanelInfo()[index].getObject();
		// 攻撃者が自分自身の攻撃に当たらないようにする処理
		if ((o instanceof Enemy || o instanceof FieldItem) && fieldObject instanceof Player) { // 攻撃者がプレイヤーで敵orアイテムにあたれば
			calcurateHP(o);
			return true;
		} else if ((o instanceof Player || o instanceof FieldItem) && fieldObject instanceof Enemy) { // 攻撃者が敵でプレイヤーorアイテムにあたれば
			calcurateHP(o);
			return true;
		}
		return false;
	}

	private void calcurateHP(FieldObject o) {
		if (o.getHP() - power > 0) { // HP計算
			o.setHP(o.getHP() - power);
		} else {
			ObjectSurfaceView.objectList.remove(o);
			o.setHP(0);
		}
	}

	abstract protected void intervalProcess();
	abstract protected void nonIntervalProcess();
}
