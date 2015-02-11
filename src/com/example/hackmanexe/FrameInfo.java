package com.example.hackmanexe;
/**
 *
 * @author meem
 * 各パネルの情報を保持するクラス
 */
class FrameInfo {
	private FrameInfo up = null;
	private FrameInfo down = null;
	private FrameInfo left = null;
	private FrameInfo right = null;
	private int index;
	private float drawX, drawY; // 描画位置
	private FieldObject object = null; //パネル上のオブジェクト

	public FrameInfo(int index) {
		this.setIndex(index);
	}
	public float getDrawX() {
		return drawX;
	}
	public void setDrawX(float drawX) {
		this.drawX = drawX;
	}
	public float getDrawY() {
		return drawY;
	}
	public void setDrawY(float drawY) {
		this.drawY = drawY;
	}
	public FrameInfo getUp() {
		return up;
	}
	public void setUp(FrameInfo up) {
		this.up = up;
	}
	public FrameInfo getDown() {
		return down;
	}
	public void setDown(FrameInfo down) {
		this.down = down;
	}
	public FrameInfo getLeft() {
		return left;
	}
	public void setLeft(FrameInfo left) {
		this.left = left;
	}
	public FrameInfo getRight() {
		return right;
	}
	public void setRight(FrameInfo right) {
		this.right = right;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 *
	 * @return 上から見て 0…上段,1…中段,2…下段
	 */
	public int getLine() {
		if (getIndex() > -1 && getIndex() < 3)
			return 0;
		else if (getIndex() > 2 && getIndex() < 6)
			return 1;
		else
			return 2;
	}

	/**
	 *
	 * @return 敵・味方の境界線から見て 0…1列目,1…2列目,2…3列目
	 */
	public int getRow() {
		if (getIndex() > -1 && getIndex() < 3)
			return 0;
		else if (getIndex() > 2 && getIndex() < 6)
			return 1;
		else
			return 2;
	}

	public FieldObject getObject() {
		return object;
	}
	public void setObject(FieldObject o) {
		this.object = o;
	}
}