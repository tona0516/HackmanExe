package com.example.hackmanexe;

public class Enemy extends FieldObject{
	private int HP = 100;

	public Enemy(FrameInfo frameInfo) {
		super(frameInfo);
	}

	public int getHP() {
		return HP;
	}

	public void setHP(int hP) {
		HP = hP;
	}
}
