package com.example.hackmanexe.fieldobject;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.example.hackmanexe.MainActivity;
import com.example.hackmanexe.PanelInfo;

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
	private Timer timer2;

	private Player player;
	private MainActivity mainActivity;

	//private int phase = 1;
	private int sameLineTime = 0;

	public Ghosler(MainActivity _mainActivity, PanelInfo _panelInfo,
			Player _player) {
		super(_panelInfo, HP);

		ghosler = this;
		player = _player;
		mainActivity = _mainActivity;

		// 動作アルゴリズム
		timer = new Timer();
		timer1Task(); // 代わりにタイマー1をセット
		//timer2 = new Timer();
		//changeTimer(); // Timerを切り替えるメソッド（ここでは1に切り替えを行っている）

	}

	/**
	 * ランダム移動動作を行うタイマーをセット
	 */
	private void timer1Task() {
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() { // 毎秒ごとに実行
			@Override
			public void run() {
				// 自分・プレイヤーの位置を取得
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

				//if (moveTime >= 5) { // 一定回数移動したら
					//changeTimer(); // 追跡＆攻撃動作に切り替え
				//}
			}
		}, 1000, 100);
	}

	/**
	 * 追跡動作と攻撃動作を行うタイマーをセット
	 */
	/*private void timer2Task() {
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

	}*/

	@Override
	public void deathProcess() {
		super.deathProcess();
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
		/*if (timer2 != null) {
			timer2.cancel();
			timer2 = null;
		}*/
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

	/**
	 * 実行するタイマーの切り替えを行う
	 */
	/*private void changeTimer() {
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
	}*/

}

