package com.example.hackmanexe.action;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.view.View;
import android.view.animation.AlphaAnimation;

import com.example.hackmanexe.AttackRangeDrawManager;
import com.example.hackmanexe.PanelInfo;
import com.example.hackmanexe.fieldobject.FieldObject;

/**
 * オブジェクトの位置から攻撃場所を指定するクラス パラディンソードとかの実装にこれをextends
 *
 * @author meem
 *
 */
public class RelativePositionAttack extends AttackAction {
	protected String range; // 範囲
	protected int prePanelIndex = -1;
	protected Iterator<Integer> iterator;
	protected Timer timer;

	public RelativePositionAttack(Activity activity, int power, long interval,
			String range, FieldObject fieldObject) {
		super(activity, power, interval, fieldObject);
		this.range = range;
	}

	public void attack() {

		LinkedList<Integer> rangeList = calculateRange(range);
		if (rangeList != null) {
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
							int index = iterator.next();
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
							int index = iterator.next();
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

	private LinkedList<Integer> calculateRange(String range) {
		LinkedList<Integer> rangeList = new LinkedList<Integer>();
		PanelInfo pi = fieldObject.getCurrentPanelInfo();
		switch (range) {
			case "RightToEnd" :
				while ((pi = pi.getRight()) != null) {
					rangeList.add(pi.getIndex());
				}
				return rangeList;
			case "LeftToEnd" :
				while ((pi = pi.getLeft()) != null) {
					rangeList.add(pi.getIndex());
				}
				return rangeList;
			case "pSword" :
				if (pi.getRight() != null)
					rangeList.add(pi.getRight().getIndex());
				return rangeList;
			case "eSword" :
				if (pi.getLeft() != null)
					rangeList.add(pi.getLeft().getIndex());
				return rangeList;
			case "pWideSword" :
				if (pi.getRight() != null)
					rangeList.add(pi.getRight().getIndex());
				if (pi.getRight().getUp() != null)
					rangeList.add(pi.getRight().getUp().getIndex());
				if (pi.getRight().getDown() != null)
					rangeList.add(pi.getRight().getDown().getIndex());
				return rangeList;
			case "eWideSword" :
				if (pi.getLeft() != null)
					rangeList.add(pi.getLeft().getIndex());
				if (pi.getLeft().getUp() != null)
					rangeList.add(pi.getLeft().getUp().getIndex());
				if (pi.getLeft().getDown() != null)
					rangeList.add(pi.getLeft().getDown().getIndex());
				return rangeList;
			case "pLongSword" :
				if (pi.getRight() != null)
					rangeList.add(pi.getRight().getIndex());
				if (pi.getRight().getRight() != null)
					rangeList.add(pi.getRight().getRight().getIndex());
				return rangeList;
			case "eLongSword" :
				if (pi.getLeft() != null)
					rangeList.add(pi.getLeft().getIndex());
				if (pi.getLeft().getLeft() != null)
					rangeList.add(pi.getLeft().getLeft().getIndex());
				return rangeList;
			case "pPaladinSword" :
				if (pi.getRight() != null)
					rangeList.add(pi.getRight().getIndex());
				if (pi.getRight().getRight() != null)
					rangeList.add(pi.getRight().getRight().getIndex());
				if (pi.getRight().getRight().getRight() != null)
					rangeList.add(pi.getRight().getRight().getRight().getIndex());
				return rangeList;
			case "ePaladinSword" :
				if (pi.getLeft() != null)
					rangeList.add(pi.getLeft().getIndex());
				if (pi.getLeft().getLeft() != null)
					rangeList.add(pi.getLeft().getLeft().getIndex());
				if (pi.getLeft().getLeft().getLeft() != null)
					rangeList.add(pi.getLeft().getLeft().getLeft().getIndex());
				return rangeList;
			default :
				return null;
		}
	}

	@Override
	public boolean isActing() {
		if (timer != null)
			return true;
		return false;
	}
}
