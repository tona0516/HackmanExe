package com.example.hackmanexe.fieldobject;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import android.util.Log;

import com.example.hackmanexe.MainActivity;
import com.example.hackmanexe.PanelInfo;
import com.example.hackmanexe.action.Rabbiling;

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
	private Timer timer1, timer2;
	private Rabbiling rabbiling;
	private Player player;
	private MainActivity mainActivity;

	private int phase = 1;
	private int moveTime = 0;

	public Rabbily(MainActivity _mainActivity, PanelInfo _panelInfo,
			Player _player) {
		super(_panelInfo, HP);

		rabbily = this;
		player = _player;
		mainActivity = _mainActivity;

		// 動作アルゴリズム
		timer1 = new Timer();
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
				int currentPlayerLine = player.getCurrentPanelInfo().getLine();
				int currentOwnLine = rabbily.getCurrentPanelInfo().getLine();
				PanelInfo pi = rabbily.getCurrentPanelInfo();
				if (rabbiling == null || !rabbiling.isActing()) {

					randomMoveUDLR(pi); // ランダム移動
					moveTime++; // 移動回数が増加
				}

				if (moveTime >= 5) { // 一定回数移動したら
					changeTimer(); // 追跡＆攻撃動作に切り替え
				}
			}
		}, 2000, 1000);
	}

	/**
	 * 追跡動作と攻撃動作を行うタイマーをセット
	 */
	private void timer2Task() {
		timer2 = new Timer();
		timer2.scheduleAtFixedRate(new TimerTask() { // 0.3秒ごとに実行
			@Override
			public void run() {
				// 自分・プレイヤーの位置を取得
				int currentPlayerLine = player.getCurrentPanelInfo().getLine();
				int currentOwnLine = rabbily.getCurrentPanelInfo().getLine();
				PanelInfo pi = rabbily.getCurrentPanelInfo();

				if (rabbiling == null || !rabbiling.isActing()) {
					if (currentPlayerLine == currentOwnLine) { // 相手が同じラインに居れば
						try {
							TimeUnit.MILLISECONDS.sleep(150); // 振りかぶって（少し待って）
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						// 攻撃!!
						rabbiling = new Rabbiling(mainActivity, rabbily);
						rabbily.addAction(rabbiling);
						rabbily.action();
						moveTime = 0; // 移動回数をリセットして
						changeTimer(); // ランダム移動動作に切り替え

					} else if (currentPlayerLine < currentOwnLine) { // 自身より上にプレイヤーいたら
						moveUp();
						Log.d(this.toString(), "up");
					} else if (currentPlayerLine > currentOwnLine) {// 下にいたら
						moveDown();
						Log.d(this.toString(), "down");
					}
					prePlayerLine = currentPlayerLine;
					preOwnLine = currentOwnLine;
				}
			}
		}, 300, 300);

	}

	@Override
	protected void deathProcess() {
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
	void randomMoveUDLR(PanelInfo pi) {
		int listnum = 0; // 移動可能な方向の数（最大4）
		int[] list = new int[4]; // 移動可能な方向のインデックスを保存（up:1 right:2 down:3
									// left:4）

		if (rabbily.canMove(pi.getUp())) { // 上のパネルがnullじゃない場合のみ考える
			list[listnum] = 1; // 移動可能な方向として、リストにインデックスを追加
			listnum++; // 移動可能な方向の数が増える
		}
		if (rabbily.canMove(pi.getRight())) { // 下のパネルがnullじゃない場合のみ考える
			list[listnum] = 2; // 移動可能な方向として、リストにインデックスを追加
			listnum++; // 移動可能な方向の数が増える
		}
		if (rabbily.canMove(pi.getDown())) { // 上のパネルがnullじゃない場合のみ考える
			list[listnum] = 3; // 移動可能な方向として、リストにインデックスを追加
			listnum++; // 移動可能な方向の数が増える
		}
		if (rabbily.canMove(pi.getLeft())) { // 上のパネルがnullじゃない場合のみ考える
			list[listnum] = 4; // 移動可能な方向として、リストにインデックスを追加
			listnum++; // 移動可能な方向の数が増える
		}

		Random random = new Random();
		int listind = random.nextInt(listnum); // 移動可能な方向の数が、乱数の候補の数
		int directnum = list[listind]; // 方向を決定

		switch (directnum) {
			case 1 : // 選ばれたインデックスに応じて移動
				moveUp();
				break;
			case 2 :
				moveRight();
				break;
			case 3 :
				moveDown();
				break;
			case 4 :
				moveLeft();
				break;
			default :
				Log.d(this.toString(), "move error");
				break;
		}
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
