package com.example.hackmanexe.action;

import java.util.Timer;
import java.util.TimerTask;

import com.example.hackmanexe.MainActivity;
import com.example.hackmanexe.fieldobject.FieldObject;

import android.app.Activity;
import android.view.View;

/**
 *
 * @author meem 照準を飛ばしてオブジェクトと重なったらそれに攻撃する
 */
public class CannoTarget extends RelativePositionAttack {

	private CannoAttack ca;
	private boolean lockOnFlag = false;

	public CannoTarget(Activity activity, FieldObject fieldObject) {
		super(activity, 0, 300, "le", fieldObject);
	}

	@Override
	protected boolean judgeConfliction(int index) {
		if (super.judgeConfliction(index)) {
			ca = new CannoAttack(activity, 10, String.valueOf(index), fieldObject);
			fieldObject.addAction(ca);
			fieldObject.action();
			return true;
		}
		return false;
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
						if (!iterator.hasNext() || lockOnFlag) {
							MainActivity.t2[prePanelIndex].setVisibility(View.INVISIBLE);
							timer.cancel();
							timer = null;
						}else if (iterator.hasNext()) {
							if (prePanelIndex != -1)
								MainActivity.t2[prePanelIndex].setVisibility(View.INVISIBLE);
							int index = Integer.valueOf(iterator.next());
							MainActivity.t2[index].setVisibility(View.VISIBLE);
							if (judgeConfliction(index)) {
								lockOnFlag = true;
							}
							prePanelIndex = index;
						}
					}
				});
			}
		}, 0, interval);
	}
}
