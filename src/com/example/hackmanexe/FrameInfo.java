package com.example.hackmanexe;
/**
 *
 * @author meem
 * 各枠の位置関係を保持するクラス
 */
class FrameInfo {
	private FrameInfo up = null;
	private FrameInfo down = null;
	private FrameInfo left = null;
	private FrameInfo right = null;
	private int index;
	private float drawX, drawY; // 描画位置
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

}