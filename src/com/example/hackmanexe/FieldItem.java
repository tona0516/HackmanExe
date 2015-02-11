package com.example.hackmanexe;

/**
 * エリア上のアイテムを保持するクラス
 * 破壊せずにバトルに勝つとアイテムがゲットできるアレに使用
 * @author meem
 *
 */
public class FieldItem extends  FieldObject{
	public FieldItem(FrameInfo currentFrameInfo) {
		super(currentFrameInfo,1);
	}
}
