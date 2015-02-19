package com.example.hackmanexe;
/**
 *
 * @author meem 各パネルの情報を保持するクラス
 */
class PanelInfo {
	private PanelInfo up = null;
	private PanelInfo down = null;
	private PanelInfo left = null;
	private PanelInfo right = null;
	private int index;
	private float drawX, drawY; // 描画位置
	private FieldObject object = null; // パネル上のオブジェクト
	private int belong = 0; // 1・・・playerエリア,2・・・enemyエリア

	public PanelInfo(int index) {
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
	public PanelInfo getUp() {
		return up;
	}
	public void setUp(PanelInfo up) {
		this.up = up;
	}
	public PanelInfo getDown() {
		return down;
	}
	public void setDown(PanelInfo down) {
		this.down = down;
	}
	public PanelInfo getLeft() {
		return left;
	}
	public void setLeft(PanelInfo left) {
		this.left = left;
	}
	public PanelInfo getRight() {
		return right;
	}
	public void setRight(PanelInfo right) {
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
		if (getIndex() > -1 && getIndex() < 6)
			return 0;
		else if (getIndex() > 5 && getIndex() < 12)
			return 1;
		else
			return 2;
	}

	/**
	 *
	 * @return 左から見て 0…1列目,1…2列目,2…3列目
	 */
	public int getRow() {
		if (getIndex() % 6 == 0) {
			return 0;
		} else if ((getIndex() - 1) % 6 == 0) {
			return 1;
		} else if ((getIndex() - 2) % 6 == 0) {
			return 2;
		} else if ((getIndex() - 3) % 6 == 0) {
			return 3;
		} else if ((getIndex() - 4) % 6 == 0) {
			return 4;
		} else {
			return 5;
		}
	}

	public FieldObject getObject() {
		return object;
	}
	public void setObject(FieldObject o) {
		this.object = o;
	}

	public void setBelong(int belong) {
		this.belong = belong;
	}

	public boolean isPlayerPanel() {
		if (belong == 1)
			return true;
		else
			return false;
	}

	public boolean isEnemyPanel() {
		if (belong == 2)
			return true;
		else
			return false;
	}
}