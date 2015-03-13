package com.example.hackmanexe.fieldobject;

import java.util.Timer;
import java.util.TimerTask;

import com.example.hackmanexe.PanelInfo;

public class Dreambit extends Enemy {

	private static final int HP = 200;
	private Player player;

	public Dreambit(PanelInfo panelinfo, Player _player) {
		super(panelinfo, HP);
		this.player = _player;

		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				int playerLine = player.getCurrentPanelInfo().getLine(); // scope error can't fixed...
				int ownLine = getCurrentPanelInfo().getLine();
				if(playerLine < ownLine) //自身より上にプレイヤーいたら
					moveUp();
				else if(playerLine > ownLine) //下にいたら
					moveDown();
			}
		}, 0, 300);
	}

	@Override
	public void deathProcess() {
		super.deathProcess();
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
	}

}
