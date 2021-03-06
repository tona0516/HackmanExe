package com.example.hackmanexe.fieldobject;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;

import com.example.hackmanexe.PanelInfo;
import com.example.hackmanexe.action.Shockwave;

/**
 * メットールクラス 移動はできる。攻撃はまだ
 *
 * @author meem
 *
 */
public class Metall extends Enemy {

	private final static int HP = 40;
	private int prePlayerLine = -1;
	private int preOwnLine = -1;
	private Metall metall;
	private Shockwave shockWave = null;
	private Player player;
	private Activity activity;
	private Timer timer;

	public Metall(Activity activity, PanelInfo _panelInfo,
			Player _player) {
		super(_panelInfo, HP);
		metall = this;
		player = _player;
		this.activity = activity;

		// 動作アルゴリズム
		// 1秒毎に処理
		timer = new Timer();
		timer.scheduleAtFixedRate(new BehaviorPatternTask(), 2000, 1000);
	}

	@Override
	public void deathProcess() {
		super.deathProcess();
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
	}

	@Override
	public void pause() {
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
	}

	@Override
	public void restart() {
		if (timer == null) {
			timer = new Timer();
			timer.scheduleAtFixedRate(new BehaviorPatternTask(), 2000, 1000);
		}
	}

	private class BehaviorPatternTask extends TimerTask {
		@Override
		public void run() {
			// 自分・プレイヤーの位置を取得
			int currentPlayerLine = player.getCurrentPanelInfo().getLine();
			int currentOwnLine = metall.getCurrentPanelInfo().getLine();
			if (shockWave == null || !shockWave.isActing()) {
				if (prePlayerLine == currentPlayerLine && preOwnLine == currentOwnLine) { // 1秒前と立ち位置が変わってなければ
					// 攻撃！
					shockWave = new Shockwave(activity, metall);
					metall.addAction(shockWave);
					metall.action();
				} else if (currentPlayerLine < currentOwnLine) { // 自身より上にプレイヤーいたら
					moveUp();
				} else if (currentPlayerLine > currentOwnLine) {// 下にいたら
					moveDown();
				}
				prePlayerLine = currentPlayerLine;
				preOwnLine = currentOwnLine;
			}
		}
	}
}
