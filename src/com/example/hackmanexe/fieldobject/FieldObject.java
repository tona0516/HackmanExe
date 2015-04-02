package com.example.hackmanexe.fieldobject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.util.Log;

import com.example.hackmanexe.Field;
import com.example.hackmanexe.ObjectManager;
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
	protected volatile int HP;
	protected ArrayList<Action> actionList;
	protected float x, y;
	protected Timer smoothMovementTimer;
	protected DummyObject dummyObject;

	public FieldObject(PanelInfo currentPanelInfo, int HP) {
		this.currentPanelInfo = currentPanelInfo;
		this.HP = HP;
		x = currentPanelInfo.getDrawX();
		y = currentPanelInfo.getDrawY();
		currentPanelInfo.setObject(this);
		setActionList(new ArrayList<Action>());
	}

	public PanelInfo getCurrentPanelInfo() {
		return currentPanelInfo;
	}

	public synchronized int getHP() {
		return HP;
	}

	public synchronized void setHP(int HP) {
		this.HP = HP;
		if (this.HP == 0) {
			deathProcess();
		}
	}

	public float getX() {
		return x;
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

	public void deathProcess() {
		ObjectManager.getInstance().getObjectList().remove(this);
		this.getCurrentPanelInfo().setObject(null); // 死ぬときはパネル上から自分の存在を消す
	}

	public boolean canMove(PanelInfo destination) {
		if (destination == null) // 移動先のパネルがnull
			return false;
		if (destination.getObject() != null) // 移動先にオブジェクトがいる
			return false;
		if ((destination.isPlayerPanel() && this instanceof Player)) // 移動先がプレイヤーパネルかつ自分がプレイヤー
			return true;
		else if ((destination.isEnemyPanel() && this instanceof Enemy)) // 移動先がエネミーパネルかつ自分がエネミー
			return true;
		else if ((destination.isEnemyPanel() && this instanceof Opponent)) // 移動先がエネミーパネルかつ自分がエネミー
			return true;
		else if ((this instanceof FieldItem)) // 自分がアイテムなら
			return true;
		else
			return false;
	}

	public boolean moveUp() {
		PanelInfo p = currentPanelInfo.getUp();
		if (canMove(p)) {
			currentPanelInfo.setObject(null);
			p.setObject(this);
			currentPanelInfo = p;
			y = p.getDrawY();
			return true;
		} else {
			return false;
		}
	}
	public boolean moveDown() {
		PanelInfo p = currentPanelInfo.getDown();
		if (canMove(p)) {
			currentPanelInfo.setObject(null);
			p.setObject(this);
			currentPanelInfo = p;
			y = p.getDrawY();
			return true;
		} else {
			return false;
		}
	}
	public boolean moveRight() {
		PanelInfo p = currentPanelInfo.getRight();
		if (canMove(p)) {
			currentPanelInfo.setObject(null);
			p.setObject(this);
			currentPanelInfo = p;
			x = p.getDrawX();
			return true;
		} else {
			return false;
		}
	}
	public boolean moveLeft() {
		PanelInfo p = currentPanelInfo.getLeft();
		if (canMove(p)) {
			currentPanelInfo.setObject(null);
			p.setObject(this);
			currentPanelInfo = p;
			x = p.getDrawX();
			return true;
		} else {
			return false;
		}
	}

	public boolean moveUpSmoothly(long durationMillis) {
		if (dummyObject != null)
			return false;
		Log.d("dummyCheck", "ok");
		final PanelInfo p = currentPanelInfo.getUp();
		if (canMove(p)) {
			dummyObject = new DummyObject(p);
			p.setObject(dummyObject);
			long dt = durationMillis / 100;
			final float startY = currentPanelInfo.getDrawY();
			final float endY = p.getDrawY();
			final float dy = (endY - startY) / 100;
			final FieldObject o = this;
			smoothMovementTimer = new Timer();
			smoothMovementTimer.scheduleAtFixedRate(new TimerTask() {
				int count = 0;
				@Override
				public void run() {
					y += dy;
					if (count == 49) {
						p.setObject(o);
						currentPanelInfo = p;
						currentPanelInfo.getDown().setObject(dummyObject);
					}
					if (count == 99) {
						currentPanelInfo.getDown().setObject(null);
						dummyObject = null;
						if (smoothMovementTimer != null) {
							smoothMovementTimer.cancel();
							smoothMovementTimer = null;
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
		Log.d("dummyCheck", "ok");
		final PanelInfo p = currentPanelInfo.getDown();
		if (canMove(p)) {
			dummyObject = new DummyObject(p);
			p.setObject(dummyObject);
			long dt = durationMillis / 100;
			final float startY = currentPanelInfo.getDrawY();
			final float endY = p.getDrawY();
			final float dy = (endY - startY) / 100;
			final FieldObject o = this;
			smoothMovementTimer = new Timer();
			smoothMovementTimer.scheduleAtFixedRate(new TimerTask() {
				int count = 0;
				@Override
				public void run() {
					y += dy;
					if (count == 49) {
						p.setObject(o);
						currentPanelInfo = p;
						currentPanelInfo.getUp().setObject(dummyObject);
					}
					if (count == 99) {
						currentPanelInfo.getUp().setObject(null);
						dummyObject = null;
						if (smoothMovementTimer != null) {
							smoothMovementTimer.cancel();
							smoothMovementTimer = null;
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
		Log.d("dummyCheck", "ok");
		final PanelInfo p = currentPanelInfo.getRight();
		if (canMove(p)) {
			dummyObject = new DummyObject(p);
			p.setObject(dummyObject);
			long dt = durationMillis / 100;
			final float startX = currentPanelInfo.getDrawX();
			final float endX = p.getDrawX();
			final float dx = (endX - startX) / 100;
			final FieldObject o = this;
			smoothMovementTimer = new Timer();
			smoothMovementTimer.scheduleAtFixedRate(new TimerTask() {
				int count = 0;
				@Override
				public void run() {
					x += dx;
					if (count == 49) {
						p.setObject(o);
						currentPanelInfo = p;
						currentPanelInfo.getLeft().setObject(dummyObject);
					}
					if (count == 99) {
						currentPanelInfo.getLeft().setObject(null);
						dummyObject = null;
						if (smoothMovementTimer != null) {
							smoothMovementTimer.cancel();
							smoothMovementTimer = null;
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
		Log.d("dummyCheck", "ok");
		final PanelInfo p = currentPanelInfo.getLeft();
		if (canMove(p)) {
			dummyObject = new DummyObject(p);
			p.setObject(dummyObject);
			long dt = durationMillis / 100;
			final float startX = currentPanelInfo.getDrawX();
			final float endX = p.getDrawX();
			final float dx = (endX - startX) / 100;
			final FieldObject o = this;
			smoothMovementTimer = new Timer();
			smoothMovementTimer.scheduleAtFixedRate(new TimerTask() {
				int count = 0;
				@Override
				public void run() {
					x += dx;
					if (count == 49) {
						p.setObject(o);
						currentPanelInfo = p;
						currentPanelInfo.getRight().setObject(dummyObject);
					}
					if (count == 99) {
						currentPanelInfo.getRight().setObject(null);
						dummyObject = null;
						if (smoothMovementTimer != null) {
							smoothMovementTimer.cancel();
							smoothMovementTimer = null;
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
		PanelInfo p = Field.getInstance().getPanelInfo()[panelIndex]; // 移動先のpanelInfoを取得
		if (p == null) // nullでもはじく
			return false;
		if (p.getObject() != null) // 移動先に何かいてもはじく
			return false;
		// 晴れて移動
		currentPanelInfo.setObject(null);
		p.setObject(this);
		currentPanelInfo = p;
		x = currentPanelInfo.getDrawX();
		y = currentPanelInfo.getDrawY();
		return true;
	}
}
