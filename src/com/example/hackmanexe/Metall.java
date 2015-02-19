package com.example.hackmanexe;

import java.util.Timer;
import java.util.TimerTask;

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

	public Metall(final MainActivity mainActivity, PanelInfo f,
			final Player player) {
		super(mainActivity, f, HP);
		metall = this;

		// 動作アルゴリズム
		// 1秒毎に処理
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				// 自分・プレイヤーの位置を取得
				int currentPlayerLine = player.getCurrentPanelInfo().getLine();
				int currentOwnLine = metall.getCurrentPanelInfo().getLine();

				if (prePlayerLine == currentPlayerLine && preOwnLine == currentOwnLine) { // 1秒前と立ち位置が変わってなければ
					// 攻撃！
					RelativePositionAttack rpa = new RelativePositionAttack(mainActivity, 10, 500, "le", metall);
					metall.addAction(rpa);
					metall.action();
					// 攻撃が終了するまで待機
					while (rpa.isAtacking()) {
					}
				} else if (currentPlayerLine < currentOwnLine) { // 自身より上にプレイヤーいたら
					moveUp();
				} else if (currentPlayerLine > currentOwnLine) {// 下にいたら
					moveDown();
				}
				prePlayerLine = currentPlayerLine;
				preOwnLine = currentOwnLine;
			}
		}, 0, 1000);
	}

	@Override
	void deathProcess() {
		timer.cancel();
		timer = null;
	}


}
