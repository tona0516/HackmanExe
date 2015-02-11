package com.example.hackmanexe;

import java.util.ArrayList;

import android.view.SurfaceHolder;

/**
 * プレイヤークラス
 *  自エリアにプレイヤー以外のオブジェクトがある場合を考慮していない
 * @author meem
 *
 */
public class Player extends FieldObject{
	private int HP;
	private ArrayList<Action> actionList;

	public Player(FrameInfo currentFrameInfo, int HP) {
		super(currentFrameInfo,HP);
		setActionList(new ArrayList<Action>());
	}

	public boolean action(SurfaceHolder holder){
		if(actionList.get(0) instanceof AbsolutePositionAttack){
			AbsolutePositionAttack aa = (AbsolutePositionAttack)actionList.get(0);
			aa.attack(holder);
			actionList.remove(0);
			return true;
		}else if(actionList.get(0) instanceof RelativePositionAttack){
			RelativePositionAttack aa = (RelativePositionAttack)actionList.get(0);
			aa.attack(holder);
			actionList.remove(0);
		}else if(actionList.get(0) instanceof SupportAction){
			SupportAction sa = (SupportAction)actionList.get(0);
			sa.support();
			actionList.remove(0);
			return true;
		}
		return false;
	}

	public void addAction(Action a){
		actionList.add(a);
	}

	public ArrayList<Action> getActionList() {
		return actionList;
	}

	public void setActionList(ArrayList<Action> actionList) {
		this.actionList = actionList;
	}

	public boolean moveUp() {
		if (currentFrameInfo.getUp() != null) {
			FieldObject o = currentFrameInfo.getObject();
			currentFrameInfo.setObject(null);
			currentFrameInfo.getUp().setObject(o);
			currentFrameInfo = currentFrameInfo.getUp();
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

	public String getCurrentFrameInfoToString() {
		String FrameInfoStr;
		switch (currentFrameInfo.getIndex()) {
			case 0 :
				FrameInfoStr = "upperLeft";
				break;
			case 1 :
				FrameInfoStr = "upperCenter";
				break;
			case 2 :
				FrameInfoStr = "upperRight";
				break;
			case 3 :
				FrameInfoStr = "middleLeft";
				break;
			case 4 :
				FrameInfoStr = "center";
				break;
			case 5 :
				FrameInfoStr = "middleRight";
				break;
			case 6 :
				FrameInfoStr = "lowerLeft";
				break;
			case 7 :
				FrameInfoStr = "lowerCenter";
				break;
			case 8 :
				FrameInfoStr = "lowerRight";
				break;
			default :
				FrameInfoStr = null;
				break;
		}
		return FrameInfoStr;
	}
}
