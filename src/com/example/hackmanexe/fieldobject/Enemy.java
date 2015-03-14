package com.example.hackmanexe.fieldobject;

import com.example.hackmanexe.PanelInfo;

/**
 * 敵クラス 複数敵を考慮していないため。敵の位置が重複する可能性あり
 *
 * @author meem
 */
abstract public class Enemy extends FieldObject {
	public Enemy(PanelInfo _panelInfo, int _HP) {
		super(_panelInfo, _HP);
	}

	abstract public void pause();
	abstract public void restart();
}
