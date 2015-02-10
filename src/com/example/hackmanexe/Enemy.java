package com.example.hackmanexe;

public class Enemy extends FieldObject{
	private int HP;

	public Enemy(FrameInfo frameInfo,int HP) {
		super(frameInfo);
		setHP(HP);
	}

	public int getHP() {
		return HP;
	}

	public void setHP(int HP) {
		this.HP = HP;
	}
}
