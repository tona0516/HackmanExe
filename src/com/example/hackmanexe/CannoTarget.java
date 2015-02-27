package com.example.hackmanexe;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.view.View;

public class CannoTarget extends RelativePositionAttack {

	private AbsolutePositionAttack apa;

	public CannoTarget(Activity activity, FieldObject fieldObject) {
		super(activity, 0, 300, "le", fieldObject);
	}

	@Override
	protected boolean judgeConfliction(int index) {
		if (super.judgeConfliction(index)) {
			apa = new AbsolutePositionAttack(activity, 10, 0, String.valueOf(index), fieldObject);
			fieldObject.addAction(apa);
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
						if (!iterator.hasNext()) {
							MainActivity.t2[prePanelIndex].setVisibility(View.INVISIBLE);
							timer.cancel();
							timer = null;
						}
						if (iterator.hasNext()) {
							if (prePanelIndex != -1)
								MainActivity.t2[prePanelIndex].setVisibility(View.INVISIBLE);
							int index = Integer.valueOf(iterator.next());
							MainActivity.t2[index].setVisibility(View.VISIBLE);
							if(judgeConfliction(index)){
								timer.cancel();
								timer = null;
							}else{
								prePanelIndex = index;
							}
						}
					}
				});
			}
		}, 0, interval);
	}
}
