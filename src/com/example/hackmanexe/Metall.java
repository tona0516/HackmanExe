package com.example.hackmanexe;

import java.util.Timer;
import java.util.TimerTask;

import android.util.Log;

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
	private Timer timer;
	private RelativePositionAttack rpa = null;
	private Player player;
	private MainActivity mainActivity;

	public Metall(MainActivity _mainActivity, PanelInfo _panelInfo,
			 Player _player) {
		super(_panelInfo, HP);
		metall = this;
		player = _player;
		mainActivity = _mainActivity;

		// いきなり攻撃し始めないように
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// 動作アルゴリズム
		// 1秒毎に処理
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				// 自分・プレイヤーの位置を取得
				int currentPlayerLine = player.getCurrentPanelInfo().getLine();
				int currentOwnLine = metall.getCurrentPanelInfo().getLine();
				if (rpa == null || !rpa.isAtacking()) {
					if (prePlayerLine == currentPlayerLine
							&& preOwnLine == currentOwnLine) { // 1秒前と立ち位置が変わってなければ
						// 攻撃！
						rpa = new RelativePositionAttack(mainActivity, 10, 500,
								"le", metall);
						metall.addAction(rpa);
						metall.action();
					} else if (currentPlayerLine < currentOwnLine) { // 自身より上にプレイヤーいたら
						moveUp();
						Log.d(this.toString(), "up");
					} else if (currentPlayerLine > currentOwnLine) {// 下にいたら
						moveDown();
						Log.d(this.toString(), "down");
					}
					prePlayerLine = currentPlayerLine;
					preOwnLine = currentOwnLine;
				}
			}
		}, 0, 1000);
	}

	@Override
	void deathProcess() {
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
	}

}
