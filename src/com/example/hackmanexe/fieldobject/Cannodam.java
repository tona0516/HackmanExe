package com.example.hackmanexe.fieldobject;

import java.util.Timer;
import java.util.TimerTask;

import com.example.hackmanexe.VirusBattleActivity;
import com.example.hackmanexe.PanelInfo;
import com.example.hackmanexe.action.CannoTarget;
/**
 *
 * @author meem キャノーダム。常に照準を飛ばしてそれに引っかかったプレイヤーを打つ
 */
public class Cannodam extends Enemy {

	private VirusBattleActivity virusBattleActivity;
	private Cannodam cannodam;
	private CannoTarget ct;
	private Timer timer;

	public Cannodam(VirusBattleActivity _mainActivity, PanelInfo _panelInfo, int _HP) {
		super(_panelInfo, _HP);
		cannodam = this;
		virusBattleActivity = _mainActivity;

		timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				if (ct == null || !ct.isActing()) {
					ct = new CannoTarget(virusBattleActivity, cannodam);
					cannodam.addAction(ct);
					cannodam.action();
				}
			}
		}, 2000, 1000);
	}

	@Override
	public void deathProcess() {
		super.deathProcess();
		if(timer != null){
			timer.cancel();
			timer = null;
		}
	}

	@Override
	public void pause() {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void restart() {
		// TODO 自動生成されたメソッド・スタブ

	}

}
