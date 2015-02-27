package com.example.hackmanexe;

import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;
/**
 *
 * @author meem キャノーダム。常に照準を飛ばしてそれに引っかかったプレイヤーを打つ
 */
public class Cannodam extends Enemy {

	private Player player;
	private MainActivity mainActivity;
	private Cannodam cannodam;
	private CannoTarget ct;
	private boolean isScope; // 照準を飛ばしているかどうか
	private LinkedList<Integer> attackRangeList;
	Timer timer;

	public Cannodam(MainActivity _mainActivity, PanelInfo _panelInfo, int _HP,
			Player _player) {
		super(_panelInfo, _HP);
		cannodam = this;
		mainActivity = _mainActivity;
		player = _player;
		attackRangeList = new LinkedList<Integer>();

		// いきなり攻撃し始めないように
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				int ownLine = cannodam.getCurrentPanelInfo().getLine();
				int playerLine = player.getCurrentPanelInfo().getLine();
				if (ct == null || !ct.isAtacking()) {
					ct = new CannoTarget(mainActivity, cannodam);
					cannodam.addAction(ct);
					cannodam.action();
				}
				// if(apa == null || !apa.isAtacking()){
				// apa = new AbsolutePositionAttack(mainActivity, 10, 0,
				// String.valueOf(player.getCurrentPanelInfo().getIndex()),
				// cannodam);
				// }
			}
		}, 0, 1000);
	}

	@Override
	void deathProcess() {
		this.getCurrentPanelInfo().setObject(null);
	}

}
