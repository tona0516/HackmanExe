package com.example.hackmanexe.fieldobject;

import com.example.hackmanexe.PanelInfo;

/**
 * エリア上のアイテムを保持するクラス 破壊せずにバトルに勝つとアイテムがゲットできるアレに使用
 *
 * @author meem
 *
 */
abstract public class FieldItem extends FieldObject {
	public FieldItem(PanelInfo _currentPanelInfo, int HP) {
		super(_currentPanelInfo, HP);
	}
}
