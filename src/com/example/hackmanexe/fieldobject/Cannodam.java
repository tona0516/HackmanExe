package com.example.hackmanexe.fieldobject;

import java.util.Timer;
import java.util.TimerTask;

import com.example.hackmanexe.MainActivity;
import com.example.hackmanexe.PanelInfo;
import com.example.hackmanexe.action.CannoTarget;
/**
 *
 * @author meem キャノーダム。常に照準を飛ばしてそれに引っかかったプレイヤーを打つ
 */
public class Cannodam extends Enemy {

	private MainActivity mainActivity;
	private Cannodam cannodam;
	private CannoTarget ct;
	Timer timer;

	public Cannodam(MainActivity _mainActivity, PanelInfo _panelInfo, int _HP) {
		super(_panelInfo, _HP);
		cannodam = this;
		mainActivity = _mainActivity;

		timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				if (ct == null || !ct.isActing()) {
					ct = new CannoTarget(mainActivity, cannodam);
					cannodam.addAction(ct);
					cannodam.action();
				}
			}
		}, 2000, 1000);
	}

	@Override
	protected void deathProcess() {
		super.deathProcess();
		if(timer != null){
			timer.cancel();
			timer = null;
		}
	}

}
