package com.example.hackmanexe;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.util.Log;

/**
 * メットールクラス 移動はできる。攻撃はまだ
 *
 * @author meem
 *
 */

public class Rabbily extends Enemy {

	private final static int HP = 40;
	private int prePlayerLine = -1;
	private int preOwnLine = -1;
	private Rabbily rabbily;
	private Timer timer;
	private RelativePositionAttack rpa = null;
	private Player player;
	private MainActivity mainActivity;

	public Rabbily(MainActivity _mainActivity, PanelInfo _panelInfo,
			Player _player) {
		super(_panelInfo, HP);

		rabbily = this;
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
				int currentOwnLine = rabbily.getCurrentPanelInfo().getLine();
				PanelInfo pi = rabbily.getCurrentPanelInfo();
				if (rpa == null || !rpa.isAtacking()) {
					// ランダム移動
					getMovableDirection(pi);
					
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
	
	//
	void getMovableDirection(PanelInfo pi){
		int listnum = 0;
		int[] list = new int[4];
			
		if(pi.getUp()!=null){
			if(pi.getUp().isEnemyPanel()){
				list[listnum] = 1;
				listnum++;
			}
		}
		if(pi.getRight()!=null){
			if(pi.getRight().isEnemyPanel()){
				list[listnum] = 2;
				listnum++;
			}
		}
		if(pi.getDown()!=null){
			if(pi.getDown().isEnemyPanel()){
				list[listnum] = 3;
				listnum++;
			}
		}
		if(pi.getLeft()!=null){
			if(pi.getLeft().isEnemyPanel()){
				list[listnum] = 4;
				listnum++;
			}
		}
		
		int[] list2 = new int[listnum];
		
		for(int i = 0; i < listnum; i++){
			list2[i] = list[i];
		}
		
		Random random = new Random();
		int listind = random.nextInt(listnum); // 移動する方向をランダムで設定
		int directnum = list2[listind];
		Log.d(this.toString(), "" + directnum);
		switch (directnum) {
		case 1:
			moveUp();
			break;
		case 2:
			moveRight();
			break;
		case 3:
			moveDown();
			break;
		case 4:
			moveLeft();
			break;
		default:
			Log.d(this.toString(), "move error");
			break;
		}
	}

}
