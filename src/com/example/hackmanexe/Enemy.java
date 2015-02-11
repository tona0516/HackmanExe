package com.example.hackmanexe;

/**
 * 敵クラス
 * 複数敵を考慮していないため。敵の位置が重複する可能性あり
 * @author meem
 */
public class Enemy extends FieldObject{
	private int HP;

	public Enemy(FrameInfo frameInfo,int HP) {
		super(frameInfo,HP);
	}

	public boolean moveUp() {
		if (currentFrameInfo.getUp() != null) { //もし上にパネルがあれば
			FieldObject o = currentFrameInfo.getObject(); //現在のパネル上のオブジェクトを取得
			currentFrameInfo.setObject(null); //現在のパネル上のオブジェクトをnullにして
			currentFrameInfo.getUp().setObject(o); //移動先に移す
			currentFrameInfo = currentFrameInfo.getUp(); //現在のパネルを書き換える
			return true;
		} else {
			return false;
		}
	}
	public boolean moveDown() {
		if (currentFrameInfo.getDown() != null) {
			FieldObject o = currentFrameInfo.getObject();
			currentFrameInfo.setObject(null);
			currentFrameInfo.getDown().setObject(o);
			currentFrameInfo = currentFrameInfo.getDown();
			return true;
		} else {
			return false;
		}
	}
	public boolean moveRight() {
		if (currentFrameInfo.getRight() != null) {
			FieldObject o = currentFrameInfo.getObject();
			currentFrameInfo.setObject(null);
			currentFrameInfo.getRight().setObject(o);
			currentFrameInfo = currentFrameInfo.getRight();
			return true;
		} else {
			return false;
		}
	}
	public boolean moveLeft() {
		if (currentFrameInfo.getLeft() != null) {
			FieldObject o = currentFrameInfo.getObject();
			currentFrameInfo.setObject(null);
			currentFrameInfo.getLeft().setObject(o);
			currentFrameInfo = currentFrameInfo.getLeft();
			return true;
		} else {
			return false;
		}
	}
}
