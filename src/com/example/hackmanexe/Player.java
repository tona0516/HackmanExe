package com.example.hackmanexe;

import java.util.ArrayList;

import android.view.SurfaceHolder;

public class Player extends FieldObject{
	private int HP;
	private ArrayList<Action> actionList;

	public Player(FrameInfo currentFrameInfo, int HP) {
		super(currentFrameInfo);
		setActionList(new ArrayList<Action>());
		setHP(HP);
	}

	public boolean action(SurfaceHolder holder){
		if(actionList.get(0) instanceof AttackAction){
			AttackAction aa = (AttackAction)actionList.get(0);
			aa.attack(holder);
			actionList.remove(0);
			return true;
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
			currentFrameInfo = currentFrameInfo.getUp();
			return true;
		} else {
			return false;
		}
	}
	public boolean moveDown() {
		if (currentFrameInfo.getDown() != null) {
			currentFrameInfo = currentFrameInfo.getDown();
			return true;
		} else {
			return false;
		}
	}
	public boolean moveRight() {
		if (currentFrameInfo.getRight() != null) {
			currentFrameInfo = currentFrameInfo.getRight();
			return true;
		} else {
			return false;
		}
	}
	public boolean moveLeft() {
		if (currentFrameInfo.getLeft() != null) {
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

	public int getHP() {
		return HP;
	}

	public void setHP(int HP) {
		this.HP = HP;
	}
}
