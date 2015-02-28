package com.example.hackmanexe.fieldobject;

import com.example.hackmanexe.MainActivity;
import com.example.hackmanexe.PanelInfo;

/**
 * プレイヤークラス 自エリアにプレイヤー以外のオブジェクトがある場合を考慮していない
 *
 * @author meem
 *
 */
public class Player extends FieldObject {

	private MainActivity mainActivity;

	public Player(MainActivity _mainActivity, PanelInfo _currentFrameInfo,
			int _HP) {
		super(_currentFrameInfo, _HP);
		this.mainActivity = _mainActivity;
	}

	@Override
	protected void deathProcess() {
		super.deathProcess();
	}
}
