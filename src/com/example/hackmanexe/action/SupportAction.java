package com.example.hackmanexe.action;

import com.example.hackmanexe.fieldobject.FieldObject;

/**
 *
 * @author meem 回復・補助技の情報を保持するクラス
 */
abstract public class SupportAction extends Action {
	protected FieldObject fieldObject;
	public SupportAction(FieldObject o) {
		super();
		fieldObject = o;
	}

	/**
	 * 動作処理はこのメソッドをOverrideして実装してください！
	 */
	public void support(){
	}

	@Override
	protected boolean isActing() {
		return false;
	}
}
