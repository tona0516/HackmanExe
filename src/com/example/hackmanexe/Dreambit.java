package com.example.hackmanexe;

import java.util.Timer;
import java.util.TimerTask;

public class Dreambit extends Enemy {
	
	private static final int HP = 200;
	private Player player;
	private Timer t;
	
	public Dreambit(PanelInfo panelinfo, Player _player) {
		super(panelinfo, HP);
		this.player = _player;
		
		t = new Timer();
		t.scheduleAtFixedRate(new TimerTask() {
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
	void deathProcess() {
		// TODO 自動生成されたメソッド・スタブ
		if (t != null) {
			t.cancel();
			t = null;
		}
	}

}
