package com.example.hackmanexe;

import android.view.SurfaceHolder;
import android.view.View;
import android.view.animation.AlphaAnimation;

/**
 * エリアを指定して攻撃するクラス
 * 攻撃範囲は今のところ敵エリアしか指定できない
 * @author meem
 */
public class AbsolutePositionAttack extends AttackAction {

	private long msec; // 有効時間
	private String range; // 範囲
	private AlphaAnimation aa; // 攻撃描画のアニメーション
	public AbsolutePositionAttack(int power, long msec, String range) {
		super(power);
		this.msec = msec;
		this.range = range;
	}

	public void attack(final SurfaceHolder holder) {
		aa = new AlphaAnimation(0.2f, 0.2f);
		aa.setDuration(msec);

		for (int i = 0; i < 9; i++) {
			if (range.charAt(i) == '1') { //もし攻撃範囲ならば
				multiThread mt = new multiThread(i);
				mt.run(); //描画
				FieldObject o = ObjectSurfaceView.field.getEmemyFrameInfo()[i].getObject();
				if (o != null) {// そのパネルにオブジェクトが存在すれば
					if (o.getHP() - power > 0) { //HP計算
						o.setHP(o.getHP() - power);
					} else {
						o.setHP(0);
					}
				}
			}
		}
	}

	public long getMsec() {
		return msec;
	}
	public void setMsec(long msec) {
		this.msec = msec;
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
