package com.example.hackmanexe;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import android.view.View;
import android.view.animation.AlphaAnimation;

/**
 * エリアを指定して攻撃するクラス 攻撃範囲は今のところ敵エリアしか指定できない 範囲が可変・固定攻撃クラスで分けるか？インタフェースにするか？
 * 
 * @author meem
 */
public class AbsolutePositionAttack extends AttackAction {

	private long msec; // 有効時間
	private String range; // 範囲
	private long interval; // 攻撃範囲が移動する場合のスピード
	private int panelIndex = -1;
	private int prePanelIndex = -1;
	private Iterator<String> iterator;
	private MainActivity mainActivity;
	public AbsolutePositionAttack(MainActivity mainActivity, int power,
			long msec, String range, long interval) {
		super(power);
		this.mainActivity = mainActivity;
		this.msec = msec;
		this.range = range;
		this.interval = interval;
	}

	public void attack() {
		String[] indexArrayStr = range.split(",");
		iterator = Arrays.asList(indexArrayStr).iterator();
		if (interval != 0)
			intervalProcessing();
		else
			nonIntervalProcessing();
	}
	public long getMsec() {
		return msec;
	}
	public void setMsec(long msec) {
		this.msec = msec;
	}
	private void intervalProcessing() {
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				mainActivity.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						if (iterator.hasNext()) {
							if (prePanelIndex != -1)
								MainActivity.t[prePanelIndex].setVisibility(View.INVISIBLE);
							int index = Integer.valueOf(iterator.next());
							MainActivity.t[index].setVisibility(View.VISIBLE);
							judgeConfliction(index);
							prePanelIndex = index;
							if (!iterator.hasNext())
								MainActivity.t[prePanelIndex].setVisibility(View.INVISIBLE);
						}
					}
				});
			}
		}, 0, interval);
	}
	private void nonIntervalProcessing() {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				mainActivity.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						while (iterator.hasNext()) {
							int index = Integer.valueOf(iterator.next());
							MainActivity.t[index].setVisibility(View.VISIBLE);
							judgeConfliction(index);
						}
						String[] indexArrayStr = range.split(",");
						AlphaAnimation aa = new AlphaAnimation(1, 0);
						aa.setDuration(msec);
						for (String index2 : Arrays.asList(indexArrayStr)) {
							int index3 = Integer.valueOf(index2);
							MainActivity.t[index3].startAnimation(aa);
							MainActivity.t[index3].setVisibility(View.INVISIBLE);
						}
					}
				});
			}
		});
		thread.run();
	}

	private void judgeConfliction(int index) {
		FieldObject o = ObjectSurfaceView.field.getPanelInfo()[index].getObject();
		if (o instanceof Enemy || o instanceof FieldItem) {// そのパネルに敵が存在すれば
			if (o.getHP() - power > 0) { // HP計算
				o.setHP(o.getHP() - power);
			} else {
				o.setHP(0);
				ObjectSurfaceView.objectList.remove(o);
			}
		}
	}
}
