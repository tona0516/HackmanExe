package com.example.hackmanexe;

import android.view.View;
import android.view.animation.AlphaAnimation;

/**
 * エリアを指定して攻撃するクラス 攻撃範囲は今のところ敵エリアしか指定できない
 *
 * @author meem
 */
public class AbsolutePositionAttack extends AttackAction {

	private long msec; // 有効時間
	private String range; // 範囲
	private AlphaAnimation aa; // 攻撃描画のアニメーション
	private long interval; // 攻撃範囲が移動する場合のスピード
	public AbsolutePositionAttack(int power, long msec, String range,
			long interval) {
		super(power);
		this.msec = msec;
		this.range = range;
		this.interval = interval;
		aa = new AlphaAnimation(0.2f, 0.2f);
		aa.setDuration(msec);
	}

	public void attack() {
		String[] indexArrayStr = range.split(",");
		for (int i = 0; i < indexArrayStr.length; i++) {
			int panelIndex = Integer.valueOf(indexArrayStr[i]);
			MainActivity.t[panelIndex].setVisibility(View.VISIBLE);
			MainActivity.t[panelIndex].setAnimation(aa);
			MainActivity.t[panelIndex].setVisibility(View.INVISIBLE);
			FieldObject o = ObjectSurfaceView.field.getPanelInfo()[panelIndex].getObject();
			if (o instanceof Enemy || o instanceof FieldItem) {// そのパネルに敵が存在すれば
				if (o.getHP() - power > 0) { // HP計算
					o.setHP(o.getHP() - power);
				} else {
					o.setHP(0);
					ObjectSurfaceView.objectList.remove(o);
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
	private class MultiThread  extends Thread{
		public MultiThread() {
		}
		@Override
		public void run() {
			super.run();
		}
	}
}
