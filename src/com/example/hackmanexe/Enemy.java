package com.example.hackmanexe;

/**
 * 敵クラス 複数敵を考慮していないため。敵の位置が重複する可能性あり
 *
 * @author meem
 */
abstract class Enemy extends FieldObject {

	public Enemy(PanelInfo frameInfo, int HP) {
		super(frameInfo, HP);
	}
}
