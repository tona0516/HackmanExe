package com.example.hackmanexe.fieldobject;

import android.app.Activity;

import com.example.hackmanexe.PanelInfo;

/**
 * プレイヤークラス 自エリアにプレイヤー以外のオブジェクトがある場合を考慮していない
 *
 * @author meem
 *
 */
public class Player extends FieldObject {

	private Activity activity;

	public Player(Activity activity, PanelInfo _currentFrameInfo,
			int _HP) {
		super(_currentFrameInfo, _HP);
		this.activity = activity;
	}

	@Override
	public void deathProcess() {
		super.deathProcess();
	}
}
