package com.example.hackmanexe.fieldobject;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.example.hackmanexe.PanelInfo;
/**
 * テスト用オブジェクト 挙動を確認したいときに使ってー
 *
 * @author meem
 *
 */
public class TestObject extends Enemy {

	Timer timer;
	Random random;

	public TestObject(PanelInfo currentFrameInfo, int HP) {
		super(currentFrameInfo, HP);

		random = new Random();

		// 動作アルゴリズム
		// 1秒毎に処理
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				int rnd = random.nextInt(18);
				warp(rnd);
			}
		}, 2000, 1000);
	}

	@Override
	public void deathProcess() {
		super.deathProcess();
		if (timer != null) {
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
