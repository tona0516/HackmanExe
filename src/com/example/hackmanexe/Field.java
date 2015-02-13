package com.example.hackmanexe;
/**
 *
 * @author meem バトルフィールドの情報を保持するクラス
 */
class Field {
	private PanelInfo[] panelInfo = new PanelInfo[18];

	public Field() {
		// FrameInfoオブジェクトの初期化
		for (int i = 0; i < 18; i++) {
			// パネルオブジェクトの生成
			panelInfo[i] = new PanelInfo(i);
		}
		// 各パネルを繋げる
		makeNode();
	}

	public void setPlayerFrameInfo(PanelInfo[] panelInfo) {
		this.panelInfo = panelInfo;
	}

	public PanelInfo[] getPanelInfo() {
		return panelInfo;
	}

	/**
	 * 各パネルの位置関係を設定
	 */
	private void makeNode() {
		// プレイヤーエリアの初期ノード設定
		panelInfo[0].setRight(panelInfo[1]);
		panelInfo[0].setDown(panelInfo[6]);
		panelInfo[1].setLeft(panelInfo[0]);
		panelInfo[1].setDown(panelInfo[7]);
		panelInfo[1].setRight(panelInfo[2]);
		panelInfo[2].setLeft(panelInfo[1]);
		panelInfo[2].setDown(panelInfo[8]);
		panelInfo[6].setUp(panelInfo[0]);
		panelInfo[6].setRight(panelInfo[7]);
		panelInfo[6].setDown(panelInfo[12]);
		panelInfo[7].setUp(panelInfo[1]);
		panelInfo[7].setDown(panelInfo[13]);
		panelInfo[7].setRight(panelInfo[8]);
		panelInfo[7].setLeft(panelInfo[6]);
		panelInfo[8].setUp(panelInfo[2]);
		panelInfo[8].setLeft(panelInfo[7]);
		panelInfo[8].setDown(panelInfo[14]);
		panelInfo[12].setUp(panelInfo[6]);
		panelInfo[12].setRight(panelInfo[13]);
		panelInfo[13].setUp(panelInfo[7]);
		panelInfo[13].setRight(panelInfo[14]);
		panelInfo[13].setLeft(panelInfo[12]);
		panelInfo[14].setUp(panelInfo[8]);
		panelInfo[14].setLeft(panelInfo[13]);

		//敵エリアの初期ノード設定
		panelInfo[0 + 3].setRight(panelInfo[1 + 3]);
		panelInfo[0 + 3].setDown(panelInfo[6 + 3]);
		panelInfo[1 + 3].setLeft(panelInfo[0 + 3]);
		panelInfo[1 + 3].setDown(panelInfo[7 + 3]);
		panelInfo[1 + 3].setRight(panelInfo[2 + 3]);
		panelInfo[2 + 3].setLeft(panelInfo[1 + 3]);
		panelInfo[2 + 3].setDown(panelInfo[8 + 3]);
		panelInfo[6 + 3].setUp(panelInfo[0 + 3]);
		panelInfo[6 + 3].setRight(panelInfo[7 + 3]);
		panelInfo[6 + 3].setDown(panelInfo[12 + 3]);
		panelInfo[7 + 3].setUp(panelInfo[1 + 3]);
		panelInfo[7 + 3].setDown(panelInfo[13 + 3]);
		panelInfo[7 + 3].setRight(panelInfo[8 + 3]);
		panelInfo[7 + 3].setLeft(panelInfo[6 + 3]);
		panelInfo[8 + 3].setUp(panelInfo[2 + 3]);
		panelInfo[8 + 3].setLeft(panelInfo[7 + 3]);
		panelInfo[8 + 3].setDown(panelInfo[14 + 3]);
		panelInfo[12 + 3].setUp(panelInfo[6 + 3]);
		panelInfo[12 + 3].setRight(panelInfo[13 + 3]);
		panelInfo[13 + 3].setUp(panelInfo[7 + 3]);
		panelInfo[13 + 3].setRight(panelInfo[14 + 3]);
		panelInfo[13 + 3].setLeft(panelInfo[12 + 3]);
		panelInfo[14 + 3].setUp(panelInfo[8 + 3]);
		panelInfo[14 + 3].setLeft(panelInfo[13 + 3]);

		// エネミーエリアのノード設定

		// 描画位置設定
		for (int i = 0; i < 18; i++) {
			panelInfo[i].setDrawX(DrawingPosition.area.centerPoint[i].x);
			panelInfo[i].setDrawY(DrawingPosition.area.centerPoint[i].y);
		}
	}
}
