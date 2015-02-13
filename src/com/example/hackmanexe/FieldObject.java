package com.example.hackmanexe;

/**
 * エリア上のオブジェクトを保持するスーパークラス
 * @author meem
 *
 */
public class FieldObject {
	protected PanelInfo currentFrameInfo;
	protected int HP;

	public FieldObject(PanelInfo currentFrameInfo, int HP) {
		this.currentFrameInfo = currentFrameInfo;
		this.HP = HP;
		currentFrameInfo.setObject(this);
	}

	public PanelInfo getCurrentFrameInfo() {
		return currentFrameInfo;
	}

	public int getHP() {
		return HP;
	}
	public void setHP(int HP) {
		this.HP = HP;
	}
}
