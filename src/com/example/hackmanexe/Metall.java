package com.example.hackmanexe;

import java.util.Timer;
import java.util.TimerTask;

/**
 * メットールクラス
 * 移動はできる。攻撃はまだ
 * @author meem
 *
 */
public class Metall extends Enemy {

	private static int HP = 40;

	public Metall(PanelInfo f,final Player player) {
		super(f, 40);
		//移動アルゴリズム
		Timer t = new Timer();
		t.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				int playerLine = player.getCurrentFrameInfo().getLine();
				int ownLine = getCurrentFrameInfo().getLine();
				if(playerLine < ownLine) //自身より上にプレイヤーいたら
					moveUp();
				else if(playerLine > ownLine) //下にいたら
					moveDown();
			}
		}, 0, 1000);
	}
}
