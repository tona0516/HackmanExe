package com.example.hackmanexe.action;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.view.View;
import android.view.animation.AlphaAnimation;

import com.example.hackmanexe.AttackRangeDrawManager;
import com.example.hackmanexe.fieldobject.FieldObject;

/**
 * エリアを指定して攻撃するクラス
 *
 * @author meem
 */
public class AbsolutePositionAttack extends AttackAction {
	protected String range; // 範囲
	protected int prePanelIndex = -1;
	protected Iterator<Integer> iterator;
	protected Timer timer;

	public AbsolutePositionAttack(Activity activity, int power, long interval,
			String range, FieldObject fieldObject) {
		super(activity, power, interval, fieldObject);
		this.range = range;
	}

	public void attack() {
		String[] indexArrayStr = range.split(",");
		LinkedList<Integer> rangeList = new LinkedList<>();
		for(String str : indexArrayStr){
			rangeList.add(Integer.valueOf(str));
		}
		if(rangeList != null){
			iterator = rangeList.iterator();
			if (interval != 0)
				intervalProcess();
			else
				nonIntervalProcess();
		}
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
							if (prePanelIndex != -1)
								AttackRangeDrawManager.t[prePanelIndex].setVisibility(View.INVISIBLE);
							if (timer != null) {
								timer.cancel();
								timer = null;
							}
						}
						if (iterator.hasNext()) {
							if (prePanelIndex != -1)
								AttackRangeDrawManager.t[prePanelIndex].setVisibility(View.INVISIBLE);
							int index = Integer.valueOf(iterator.next());
							AttackRangeDrawManager.t[index].setVisibility(View.VISIBLE);
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
							AttackRangeDrawManager.t[index].setVisibility(View.VISIBLE);
							judgeConfliction(index);
							AttackRangeDrawManager.t[index].startAnimation(aa);
							AttackRangeDrawManager.t[index].setVisibility(View.INVISIBLE);
						}
					}
				});
			}
		});
		thread.run();
	}

	@Override
	protected boolean isActing() {
		if (timer != null)
			return true;
		return false;
	}

}
