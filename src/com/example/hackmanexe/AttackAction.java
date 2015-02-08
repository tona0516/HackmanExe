package com.example.hackmanexe;

import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.animation.AlphaAnimation;

/**
 * @author meem 攻撃の情報を
 */
public class AttackAction extends Action implements Runnable {
	private int power; // 攻撃力
	private float msec; // 有効時間
	private String range; // 範囲
	private Canvas canvas;
	public AttackAction(int power, float time, String range) {
		super();
		setAttackPower(power);
		setTime(time);
		setRange(range);
	}

	public void attack(final SurfaceHolder holder) {
		AlphaAnimation aa = new AlphaAnimation(0, 1);
		aa.setDuration(1000);
		MainActivity.t.setText("A");
		MainActivity.t.setTextColor(Color.BLACK);
		MainActivity.t.setTextSize(50);
		MainActivity.t.setX(DrawingPosition.enemyArea.pointF[4].x);
		MainActivity.t.setY(DrawingPosition.enemyArea.pointF[4].y-300);
		MainActivity.t.setVisibility(View.VISIBLE);
		MainActivity.t.setAnimation(aa);
		MainActivity.t.setVisibility(View.INVISIBLE);
//		canvas = holder.lockCanvas();
//		Paint paint = new Paint();
//		paint.setTextSize(100);
//		canvas.drawText("bomb", DrawingPosition.enemyArea.pointF[4].x, DrawingPosition.enemyArea.pointF[4].y, paint);
//		holder.unlockCanvasAndPost(canvas);

		for (int i = 0; i < 9; i++) {
			if (range.charAt(i) == '1') {
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

	@Override
	public void run() {
		Log.d(this.toString(), "run");
	}
}
