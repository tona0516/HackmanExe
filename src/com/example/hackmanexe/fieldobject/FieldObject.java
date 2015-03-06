package com.example.hackmanexe.fieldobject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.example.hackmanexe.ObjectSurfaceView;
import com.example.hackmanexe.PanelInfo;
import com.example.hackmanexe.action.AbsolutePositionAttack;
import com.example.hackmanexe.action.Action;
import com.example.hackmanexe.action.RelativePositionAttack;
import com.example.hackmanexe.action.SupportAction;

/**
 * エリア上のオブジェクトを保持するスーパークラス
 *
 * @author meem
 *
 */
abstract public class FieldObject {
	protected PanelInfo currentPanelInfo;
	protected int HP;
	protected ArrayList<Action> actionList;
	protected float x, y; // 現在座標
	protected Timer timer; // smoothlyに使うタイマー
	protected DummyObject dummyObject;

	public FieldObject(PanelInfo currentPanelInfo, int HP) {
		this.currentPanelInfo = currentPanelInfo;
		x = currentPanelInfo.getDrawX();
		y = currentPanelInfo.getDrawY();
		this.HP = HP;
		currentPanelInfo.setObject(this);
		setActionList(new ArrayList<Action>());

	}

	public PanelInfo getCurrentPanelInfo() {
		return currentPanelInfo;
	}

	public int getHP() {
		return HP;
	}

	public void setHP(int HP) {
		this.HP = HP;
		if (this.HP == 0) {
			deathProcess();
			try {
				finalize();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getX() {
		return x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getY() {
		return y;
	}

	public boolean action() {
		if (actionList.get(0) instanceof AbsolutePositionAttack) {
			AbsolutePositionAttack aa = (AbsolutePositionAttack) actionList.get(0);
			aa.attack();
			actionList.remove(0);
			return true;
		} else if (actionList.get(0) instanceof RelativePositionAttack) {
			RelativePositionAttack aa = (RelativePositionAttack) actionList.get(0);
			aa.attack();
			actionList.remove(0);
		} else if (actionList.get(0) instanceof SupportAction) {
			SupportAction sa = (SupportAction) actionList.get(0);
			sa.support();
			actionList.remove(0);
			return true;
		}
		return false;
	}

	public void addAction(Action a) {
		actionList.add(a);
	}

	public ArrayList<Action> getActionList() {
		return actionList;
	}

	public void setActionList(ArrayList<Action> actionList) {
		this.actionList = actionList;
	}

	/**
	 * 死んだ時の処理
	 */
	protected void deathProcess() {
		this.getCurrentPanelInfo().setObject(null); // 死ぬときはパネル上から自分の存在を消す
	}

	public boolean moveUp() {
		if (dummyObject != null)
			return false;
		if (currentPanelInfo.getUp() == null)
			return false;
		if (currentPanelInfo.getUp().getObject() != null)
			return false;
		if ((currentPanelInfo.getUp().isPlayerPanel() && this instanceof Player) || (currentPanelInfo.getUp().isEnemyPanel() && this instanceof Enemy)) {
			currentPanelInfo.setObject(null);
			currentPanelInfo.getUp().setObject(this);
			currentPanelInfo = currentPanelInfo.getUp();
			y = currentPanelInfo.getDrawY();
			return true;
		} else {
			return false;
		}
	}
	public boolean moveDown() {
		if (dummyObject != null)
			return false;
		if (currentPanelInfo.getDown() == null)
			return false;
		if (currentPanelInfo.getDown().getObject() != null)
			return false;
		if ((currentPanelInfo.getDown().isPlayerPanel() && this instanceof Player) || (currentPanelInfo.getDown().isEnemyPanel() && this instanceof Enemy)) {
			currentPanelInfo.setObject(null);
			currentPanelInfo.getDown().setObject(this);
			currentPanelInfo = currentPanelInfo.getDown();
			y = currentPanelInfo.getDrawY();
			return true;
		} else {
			return false;
		}
	}
	public boolean moveRight() {
		if (dummyObject != null)
			return false;
		if (currentPanelInfo.getRight() == null)
			return false;
		if (currentPanelInfo.getRight().getObject() != null)
			return false;
		if ((currentPanelInfo.getRight().isPlayerPanel() && this instanceof Player) || (currentPanelInfo.getRight().isEnemyPanel() && this instanceof Enemy)) {
			currentPanelInfo.setObject(null);
			currentPanelInfo.getRight().setObject(this);
			currentPanelInfo = currentPanelInfo.getRight();
			x = currentPanelInfo.getDrawX();
			return true;
		} else {
			return false;
		}
	}
	public boolean moveLeft() {
		if (dummyObject != null)
			return false;
		if (currentPanelInfo.getLeft() == null)
			return false;
		if (currentPanelInfo.getLeft().getObject() != null)
			return false;
		if ((currentPanelInfo.getLeft().isPlayerPanel() && this instanceof Player) || (currentPanelInfo.getLeft().isEnemyPanel() && this instanceof Enemy)) {
			currentPanelInfo.setObject(null);
			currentPanelInfo.getLeft().setObject(this);
			currentPanelInfo = currentPanelInfo.getLeft();
			x = currentPanelInfo.getDrawX();
			return true;
		} else {
			return false;
		}
	}

	public boolean moveUpSmoothly(long durationMillis) {
		if (dummyObject != null)
			return false;
		if (currentPanelInfo.getUp() == null)
			return false;
		if (currentPanelInfo.getUp().getObject() != null)
			return false;
		if ((currentPanelInfo.getUp().isPlayerPanel() && this instanceof Player) || (currentPanelInfo.getUp().isEnemyPanel() && this instanceof Enemy)) {
			final PanelInfo up = currentPanelInfo.getUp();
			dummyObject = new DummyObject(up);
			currentPanelInfo.getUp().setObject(dummyObject);
			long dt = durationMillis / 100;
			final float startY = currentPanelInfo.getDrawY();
			final float endY = up.getDrawY();
			final float dy = (endY - startY) / 100;
			final FieldObject o = this;
			timer = new Timer();
			timer.schedule(new TimerTask() {
				int count = 0;
				@Override
				public void run() {
					y += dy;
					if (count == 49) {
						dummyObject.moveDown();
						currentPanelInfo.setObject(dummyObject);
						up.setObject(o);
						currentPanelInfo = up;
					}
					if (count == 99) {
						currentPanelInfo.getDown().setObject(null);
						dummyObject = null;
						if (timer != null) {
							timer.cancel();
							timer = null;
						}
					}
					count++;
				}
			}, 0, dt);
			return true;
		} else {
			return false;
		}
	}

	public boolean moveDownSmoothly(long durationMillis) {
		if (dummyObject != null)
			return false;
		if (currentPanelInfo.getDown() == null)
			return false;
		if (currentPanelInfo.getDown().getObject() != null)
			return false;
		if ((currentPanelInfo.getDown().isPlayerPanel() && this instanceof Player) || (currentPanelInfo.getDown().isEnemyPanel() && this instanceof Enemy)) {
			final PanelInfo down = currentPanelInfo.getDown();
			dummyObject = new DummyObject(down);
			currentPanelInfo.getDown().setObject(dummyObject);
			long dt = durationMillis / 100;
			final float startY = currentPanelInfo.getDrawY();
			final float endY = down.getDrawY();
			final float dy = (endY - startY) / 100;
			final FieldObject o = this;
			timer = new Timer();
			timer.schedule(new TimerTask() {
				int count = 0;
				@Override
				public void run() {
					y += dy;
					if (count == 49) {
						dummyObject.moveDown();
						currentPanelInfo.setObject(dummyObject);
						down.setObject(o);
						currentPanelInfo = down;
					}
					if (count == 99) {
						currentPanelInfo.getUp().setObject(null);
						dummyObject = null;
						if (timer != null) {
							timer.cancel();
							timer = null;
						}
					}
					count++;
				}
			}, 0, dt);
			return true;
		} else {
			return false;
		}
	}

	public boolean moveRightSmoothly(long durationMillis) {
		if (dummyObject != null)
			return false;
		if (currentPanelInfo.getRight() == null)
			return false;
		if (currentPanelInfo.getRight().getObject() != null)
			return false;
		if ((currentPanelInfo.getRight().isPlayerPanel() && this instanceof Player) || (currentPanelInfo.getRight().isEnemyPanel() && this instanceof Enemy)) {
			final PanelInfo right = currentPanelInfo.getRight();
			dummyObject = new DummyObject(right);
			currentPanelInfo.getRight().setObject(dummyObject);
			long dt = durationMillis / 100;
			final float startX = currentPanelInfo.getDrawX();
			final float endX = right.getDrawX();
			final float dx = (endX - startX) / 100;
			final FieldObject o = this;
			timer = new Timer();
			timer.schedule(new TimerTask() {
				int count = 0;
				@Override
				public void run() {
					x += dx;
					if (count == 49) {
						dummyObject.moveLeft();
						currentPanelInfo.setObject(dummyObject);
						right.setObject(o);
						currentPanelInfo = right;
					}
					if (count == 99) {
						currentPanelInfo.getLeft().setObject(null);
						dummyObject = null;
						if (timer != null) {
							timer.cancel();
							timer = null;
						}
					}
					count++;
				}
			}, 0, dt);
			return true;
		} else {
			return false;
		}
	}

	public boolean moveLeftSmoothly(long durationMillis) {
		if (dummyObject != null)
			return false;
		if (currentPanelInfo.getLeft() == null)
			return false;
		if (currentPanelInfo.getLeft().getObject() != null)
			return false;
		if ((currentPanelInfo.getLeft().isPlayerPanel() && this instanceof Player) || (currentPanelInfo.getLeft().isEnemyPanel() && this instanceof Enemy)) {
			final PanelInfo left = currentPanelInfo.getLeft();
			dummyObject = new DummyObject(left);
			currentPanelInfo.getLeft().setObject(dummyObject);
			long dt = durationMillis / 100;
			final float startX = currentPanelInfo.getDrawX();
			final float endX = left.getDrawX();
			final float dx = (endX - startX) / 100;
			final FieldObject o = this;
			timer = new Timer();
			timer.schedule(new TimerTask() {
				int count = 0;
				@Override
				public void run() {
					x += dx;
					if (count == 49) {
						dummyObject.moveRight();
						currentPanelInfo.setObject(dummyObject);
						left.setObject(o);
						currentPanelInfo = left;
					}
					if (count == 99) {
						currentPanelInfo.getRight().setObject(null);
						dummyObject = null;
						if (timer != null) {
							timer.cancel();
							timer = null;
						}
					}
					count++;
				}
			}, 0, dt);
			return true;
		} else {
			return false;
		}
	}

	/**
	 *
	 * @param panelIndex
	 * @return 指定したインデックスのパネルにオブジェクトを移動させる
	 */
	public boolean warp(int panelIndex) {
		if (!(panelIndex > -1 && panelIndex < 18)) // 不正な値ならはじく
			return false;
		PanelInfo pi = ObjectSurfaceView.field.getPanelInfo()[panelIndex]; // 移動先のpanelInfoを取得
		if (pi == null) // nullでもはじく
			return false;
		if (pi.getObject() != null) // 移動先に何かいてもはじく
			return false;
		// 晴れて移動
		currentPanelInfo.setObject(null);
		pi.setObject(this);
		currentPanelInfo = pi;
		x = currentPanelInfo.getDrawX();
		y = currentPanelInfo.getDrawY();
		return true;
	}
}
