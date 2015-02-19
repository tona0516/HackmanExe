package com.example.hackmanexe;

/**
 * プレイヤークラス 自エリアにプレイヤー以外のオブジェクトがある場合を考慮していない
 *
 * @author meem
 *
 */
public class Player extends FieldObject {

	public Player(final MainActivity mainActivity, PanelInfo currentFrameInfo, int HP) {
		super(mainActivity, currentFrameInfo, HP);
	}

	@Override
	void deathProcess() {
	}
}
