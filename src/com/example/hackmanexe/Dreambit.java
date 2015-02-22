package com.example.hackmanexe;

import java.util.Timer;
import java.util.TimerTask;

public class Dreambit extends Enemy {
	
	private static final int HP = 200;

	public Dreambit(FrameInfo f, Player player) {
		super(f, HP);
		
		Timer t = new Timer();
		t.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				int playerLine = player.getCurrentFrameInfo().getLine(); // scope error can't fixed...
				int ownLine = getCurrentFrameInfo().getLine();
				if(playerLine < ownLine) //自身より上にプレイヤーいたら
					moveUp();
				else if(playerLine > ownLine) //下にいたら
					moveDown();
			}
		}, 0, 300);
	}

}
