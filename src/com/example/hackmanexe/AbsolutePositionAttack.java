package com.example.hackmanexe;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.view.View;
import android.view.animation.AlphaAnimation;

/**
 * エリアを指定して攻撃するクラス
 *
 * @author meem
 */
public class AbsolutePositionAttack extends AttackAction {
	private String range; // 範囲
	private int prePanelIndex = -1;
	private Iterator<String> iterator;
	private Timer timer;
	private int niki;

	public AbsolutePositionAttack(Activity activity, int power, long interval,
			String range, FieldObject fieldObject) {
		super(activity, power, interval, fieldObject);
		this.range = range;
	}

	public void attack() {
		String[] indexArrayStr = range.split(",");
		iterator = Arrays.asList(indexArrayStr).iterator();
		if (interval != 0)
			intervalProcess();
		else
			nonIntervalProcess();
	}

	@Override
	protected void intervalProcess() {
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				activity.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						if (!iterator.hasNext()) {
							MainActivity.t[prePanelIndex].setVisibility(View.INVISIBLE);
							timer.cancel();
							timer = null;
						}
						if (iterator.hasNext()) {
							if (prePanelIndex != -1)
								MainActivity.t[prePanelIndex].setVisibility(View.INVISIBLE);
							int index = Integer.valueOf(iterator.next());
							MainActivity.t[index].setVisibility(View.VISIBLE);
							judgeConfliction(index);
							prePanelIndex = index;
						}
					}
				});
			}
		}, 0, interval);
	}

	@Override
	protected void nonIntervalProcess() {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				activity.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						AlphaAnimation aa = new AlphaAnimation(1, 0);
						aa.setDuration(msec);
						while (iterator.hasNext()) {
							int index = Integer.valueOf(iterator.next());
							MainActivity.t[index].setVisibility(View.VISIBLE);
							judgeConfliction(index);
							MainActivity.t[index].startAnimation(aa);
							MainActivity.t[index].setVisibility(View.INVISIBLE);
						}
					}
				});
			}
		});
		thread.run();
	}

	/**
	 *
	 * @return 攻撃中…true, 違う・・・false
	 */
	public boolean isAtacking() {
		if (timer != null)
			return true;
		else
			return false;
	}
}
