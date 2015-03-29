package com.example.hackmanexe.action;

import com.example.hackmanexe.ObjectManager;
import com.example.hackmanexe.PanelInfo;
import com.example.hackmanexe.fieldobject.Enemy;
import com.example.hackmanexe.fieldobject.FieldItem;
import com.example.hackmanexe.fieldobject.FieldObject;
import com.example.hackmanexe.fieldobject.Player;

public class StoneCube extends SupportAction {
	public StoneCube(FieldObject o) {
		super(o);
	}

	@Override
	public void support() {
		if (fieldObject instanceof Player && fieldObject.getCurrentPanelInfo().getRight().getObject() == null)
			ObjectManager.getInstance().getObjectList().add(new StoneCubeObject(fieldObject.getCurrentPanelInfo().getRight()));
		else if (fieldObject instanceof Enemy && fieldObject.getCurrentPanelInfo().getLeft().getObject() == null)
			ObjectManager.getInstance().getObjectList().add(new StoneCubeObject(fieldObject.getCurrentPanelInfo().getLeft()));
	}

	private class StoneCubeObject extends FieldItem {
		public StoneCubeObject(PanelInfo p) {
			super(p, 200);
			ObjectManager.getInstance().getObjectList().add(this);
		}
	}
}
