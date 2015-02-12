package com.example.hackmanexe;

/**
 * エリア上のオブジェクトを保持するスーパークラス
 * @author meem
 *
 */
public class FieldObject {
	protected FrameInfo currentFrameInfo;
	protected int HP;
	int i;

	public FieldObject(FrameInfo currentFrameInfo, int HP) {
		this.currentFrameInfo = currentFrameInfo;
		this.HP = HP;
		currentFrameInfo.setObject(this);
	}

	public FrameInfo getCurrentFrameInfo() {
		return currentFrameInfo;
	}

	public int getHP() {
		return HP;
	}
	public void setHP(int HP) {
		this.HP = HP;
	}
}
