package com.example.hackmanexe.fieldobject;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.util.Log;

import com.example.hackmanexe.VirusBattleActivity;
import com.example.hackmanexe.PanelInfo;
import com.example.hackmanexe.action.Punch;
import com.example.hackmanexe.action.Shockwave;

/**
 * メットールクラス 移動はできる。攻撃はまだ
 *
 * @author meem
 *
 */

public class Ghosler extends Enemy {

	private final static int HP = 40;
	//private int prePlayerLine = -1;
	//private int preOwnLine = -1;
	private Ghosler ghosler;
	private Player player;
	private Activity activity;
	private Punch punch = null;

	private int sameLineTime = 0;
	private int waitTime1 = 0;
	private int waitTime2 = 0;
	private int phase = 0;
	private int preOwnIndex = 0;
	
	private Timer timer1;
	private Timer timer2;

	public Ghosler(Activity _mainActivity, PanelInfo _panelInfo,
			Player _player) {
		super(_panelInfo, HP);

		ghosler = this;
		player = _player;
		activity = _mainActivity;

		// 動作アルゴリズム
		timer1 = new Timer();
		//timer1Task();
		timer2 = new Timer();
		changeTimer(); // Timerを切り替えるメソッド（ここでは1に切り替えを行っている）

	}

	/**
	 * ランダム移動動作を行うタイマーをセット
	 */
	private void timer1Task() {
		timer1 = new Timer();
		timer1.scheduleAtFixedRate(new TimerTask() { // 毎秒ごとに実行
			@Override
			public void run() {
				// 自分・プレイヤーの位置を取得
				int currentPlayerIndex = player.currentPanelInfo.getIndex();
				int currentPlayerLine = player.getCurrentPanelInfo().getLine();
				int currentOwnLine = ghosler.getCurrentPanelInfo().getLine();
				PanelInfo pi = ghosler.getCurrentPanelInfo();
				//if (ghosler) {
				if(currentPlayerLine == currentOwnLine){
					sameLineTime++;
				}
				else{
					sameLineTime = 0;
				}

				if(sameLineTime >= 3){
					sameLineTime = 0;
					escapeWarp(pi);
				}
				//}
				if (waitTime1 >= 60) { // 一定時間待機したら、消失＆攻撃に移行
					waitTime1 = 0;
					changeTimer();
				}
				waitTime1++;
				
			}
		}, 1000, 100);
	}

	/**
	 * 追跡動作と攻撃動作を行うタイマーをセット
	 */
	private void timer2Task() {
		timer2 = new Timer();
		timer2.scheduleAtFixedRate(new TimerTask() { // 0.5秒ごとに実行
			@Override
			public void run() {
				if(phase == 0){
					preOwnIndex = ghosler.currentPanelInfo.getIndex();	//現在位置を攻撃前の位置として保存
					phase++;
					if(!warp(player.currentPanelInfo.getRight().getIndex())){	//目の前に移動
						changeTimer();
					}
				}
				if(waitTime2 >= 6 && phase == 1){
					punch = new Punch(activity, ghosler);
					ghosler.addAction(punch);
					ghosler.action();	//攻撃!
					phase++;
				}
				if(waitTime2 >= 7 && phase==2){
					warp(preOwnIndex);
					waitTime2 = 0;
					phase = 0;
					changeTimer();
				}
				waitTime2++;
			}
		}, 0, 500);

	}

	@Override
	public void deathProcess() {
		super.deathProcess();
		if (timer1 != null) {
			timer1.cancel();
			timer1 = null;
		}
		if (timer2 != null) {
			timer2.cancel();
			timer2 = null;
		}
	}

	/**
	 * ランダムに上下左右のどこかへ移動するメソッド
	 *
	 * @param pi
	 */
	void escapeWarp(PanelInfo pi) {
		int[] list = new int[2]; //
		int listnum = 0;
		int index;

		for(int i = -2; i <= 2; i++){
			index = pi.getIndex() + (i * 6);
			if(index < 18 && 2 < index && index != pi.getIndex()){
				list[listnum] = index;
				listnum++;
			}
		}

		Random random = new Random();
		int listind = random.nextInt(listnum); // 移動可能な方向の数が、乱数の候補の数
		int warpind = list[listind]; // 方向を決定

		warp(warpind);
	}

	@Override
	public void pause() {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void restart() {
		// TODO 自動生成されたメソッド・スタブ

	}

	/**
	 * 実行するタイマーの切り替えを行う
	 */
	private void changeTimer() {
		if (timer2 != null) { // タイマー2が起動しているなら
			timer2.cancel(); // タイマー2をキャンセル
			timer2 = null; // タイマー2を未セット状態に
			timer1Task(); // 代わりにタイマー1をセット
		} else if (timer1 != null) {
			timer1.cancel();
			timer1 = null;
			timer1 = new Timer();
			timer2Task();
		}
	}

}

