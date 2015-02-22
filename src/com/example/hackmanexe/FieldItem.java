package com.example.hackmanexe;

/**
 * エリア上のアイテムを保持するクラス 破壊せずにバトルに勝つとアイテムがゲットできるアレに使用
 *
 * @author meem
 *
 */
public class FieldItem extends FieldObject {

	public FieldItem(PanelInfo _currentPanelInfo) {
		super(_currentPanelInfo, 1);
	}

	@Override
	void deathProcess() {
	}
}
