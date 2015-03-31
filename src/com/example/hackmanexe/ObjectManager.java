package com.example.hackmanexe;

import java.util.ArrayList;

import com.example.hackmanexe.fieldobject.FieldObject;
import com.example.hackmanexe.fieldobject.Opponent;
import com.example.hackmanexe.fieldobject.Player;

public class ObjectManager {

	private static ArrayList<FieldObject> objectList;
	private static Player player;
	private static Opponent opponent;
	private static ObjectManager instance;

	private ObjectManager() {
		instance = null;
		setPlayer(null);
		setOpponent(null);
		setObjectList(null);
	}

	public static ObjectManager getInstance() {
		if (instance == null) {
			instance = new ObjectManager();
			objectList = new ArrayList<FieldObject>();
		}
		return instance;
	}

	public void clear() {
		instance = null;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		ObjectManager.player = player;
	}

	public Opponent getOpponent() {
		return opponent;
	}

	public void setOpponent(Opponent opponent) {
		ObjectManager.opponent = opponent;
	}

	public ArrayList<FieldObject> getObjectList() {
		return objectList;
	}

	public void setObjectList(ArrayList<FieldObject> objectList) {
		ObjectManager.objectList = objectList;
	}
}
