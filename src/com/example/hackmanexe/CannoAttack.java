package com.example.hackmanexe;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.view.View;
import android.view.animation.AlphaAnimation;

public class CannoAttack extends AbsolutePositionAttack {

	public CannoAttack(Activity activity, int power, String range,
			FieldObject fieldObject) {
		super(activity, power, 0, range, fieldObject);
	}

	@Override
	protected void nonIntervalProcess() {
		timer = new Timer();
		timer.schedule(new TimerTask() {
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
		}, 300);
	}
}
