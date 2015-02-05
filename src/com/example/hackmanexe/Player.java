package com.example.hackmanexe;

public class Player extends FieldObject{
	private int HP;

	public Player(FrameInfo frameInfo) {
		super(frameInfo);
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
}
