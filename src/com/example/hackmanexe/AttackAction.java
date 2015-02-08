package com.example.hackmanexe;

import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.animation.AlphaAnimation;

/**
 * @author meem 攻撃の情報を
 */
public class AttackAction extends Action {
	private int power; // 攻撃力
	private float msec; // 有効時間
	private String range; // 範囲
	private Canvas canvas;
	private AlphaAnimation aa;
	public AttackAction(int power, float time, String range) {
		super();
		setAttackPower(power);
		setTime(time);
		setRange(range);
	}

	public void attack(final SurfaceHolder holder) {
		aa = new AlphaAnimation(0.2f, 0.2f);
		aa.setDuration(3000);

		for (int i = 0; i < 9; i++) {
			if (range.charAt(i) == '1') {
//				multiThread mt = new multiThread(i);
//				mt.run();
				MainActivity.t[i].setVisibility(View.VISIBLE);
				MainActivity.t[i].setAnimation(aa);
				MainActivity.t[i].setVisibility(View.INVISIBLE);
				if (ObjectSurfaceView.metall.getCurrentFrameInfo().getIndex() == i) {// 当たり判定
					if (ObjectSurfaceView.metall.getHP() - power > 0) {
						ObjectSurfaceView.metall.setHP(ObjectSurfaceView.metall.getHP() - power);
					} else {
						ObjectSurfaceView.metall.setHP(0);
					}
				}
			}
		}
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

	class multiThread extends Thread {
		// 変数の宣言
		private int multiThreadInt;

		// スレッド作成時に実行される処理
		public multiThread(int multiThreadInt) {
			this.multiThreadInt = multiThreadInt;
		}

		// スレッド実行時の処理
		public void run() {
			MainActivity.t[multiThreadInt].setVisibility(View.VISIBLE);
			MainActivity.t[multiThreadInt].setAnimation(aa);
			MainActivity.t[multiThreadInt].setVisibility(View.INVISIBLE);
		}

		// スレッド終了時に呼び出し
		public void stopThread() {
		}
	}
}
