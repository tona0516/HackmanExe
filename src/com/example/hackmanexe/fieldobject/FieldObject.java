package com.example.hackmanexe.fieldobject;

import java.util.ArrayList;

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

	public FieldObject(PanelInfo currentFrameInfo, int HP) {
		this.currentPanelInfo = currentFrameInfo;
		this.HP = HP;
		currentFrameInfo.setObject(this);
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
		if (currentPanelInfo.getUp() == null)
			return false;
		if (currentPanelInfo.getUp().getObject() != null)
			return false;
		if ((currentPanelInfo.getUp().isPlayerPanel() && this instanceof Player) || (currentPanelInfo.getUp().isEnemyPanel() && this instanceof Enemy)) {
			currentPanelInfo.setObject(null);
			currentPanelInfo.getUp().setObject(this);
			currentPanelInfo = currentPanelInfo.getUp();
			return true;
		} else {
			return false;
		}
	}
	public boolean moveDown() {
		if (currentPanelInfo.getDown() == null)
			return false;
		if (currentPanelInfo.getDown().getObject() != null)
			return false;
		if ((currentPanelInfo.getDown().isPlayerPanel() && this instanceof Player) || (currentPanelInfo.getDown().isEnemyPanel() && this instanceof Enemy)) {
			currentPanelInfo.setObject(null);
			currentPanelInfo.getDown().setObject(this);
			currentPanelInfo = currentPanelInfo.getDown();
			return true;
		} else {
			return false;
		}
	}
	public boolean moveRight() {
		if (currentPanelInfo.getRight() == null)
			return false;
		if (currentPanelInfo.getRight().getObject() != null)
			return false;
		if ((currentPanelInfo.getRight().isPlayerPanel() && this instanceof Player) || (currentPanelInfo.getRight().isEnemyPanel() && this instanceof Enemy)) {
			currentPanelInfo.setObject(null);
			currentPanelInfo.getRight().setObject(this);
			currentPanelInfo = currentPanelInfo.getRight();
			return true;
		} else {
			return false;
		}
	}
	public boolean moveLeft() {
		if (currentPanelInfo.getLeft() == null)
			return false;
		if (currentPanelInfo.getLeft().getObject() != null)
			return false;
		if ((currentPanelInfo.getLeft().isPlayerPanel() && this instanceof Player) || (currentPanelInfo.getLeft().isEnemyPanel() && this instanceof Enemy)) {
			currentPanelInfo.setObject(null);
			currentPanelInfo.getLeft().setObject(this);
			currentPanelInfo = currentPanelInfo.getLeft();
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
		return true;
	}
}
