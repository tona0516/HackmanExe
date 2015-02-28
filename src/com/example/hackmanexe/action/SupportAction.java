package com.example.hackmanexe.action;

import android.util.Log;

/**
 *
 * @author meem
 * 回復・補助技の情報を保持するクラス
 */
public class SupportAction extends Action{
	public SupportAction() {
		super();
	}
	public void support(){
		Log.d(this.toString(), "support");
	}
	@Override
	protected boolean isActing() {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}
}
