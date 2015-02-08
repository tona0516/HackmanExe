package com.example.hackmanexe;
/**
 *
 * @author meem
 * バトルフィールドの情報を保持するクラス
 */
class Field {
	private FrameInfo[] playerFrameInfo = new FrameInfo[9];
	private FrameInfo[] ememyFrameInfo = new FrameInfo[9];

	public Field() {
		// FrameInfoオブジェクトの初期化
		for (int i = 0; i < 9; i++) {
			playerFrameInfo[i] = new FrameInfo(i);
			ememyFrameInfo[i] = new FrameInfo(i);
		}
		makeNode();
	}

	public void setPlayerFrameInfo(FrameInfo[] playerFrameInfo) {
		this.playerFrameInfo = playerFrameInfo;
	}

	public FrameInfo[] getPlayerFrameInfo() {
		return playerFrameInfo;
	}

	public void setEmemyFrameInfo(FrameInfo[] ememyFrameInfo) {
		this.ememyFrameInfo = ememyFrameInfo;
	}

	public FrameInfo[] getEmemyFrameInfo() {
		return ememyFrameInfo;
	}

	/**
	 * 各枠の位置関係を設定
	 */
	private void makeNode() {
		//プレイヤーエリアのノード設定
		playerFrameInfo[0].setRight(playerFrameInfo[1]);
		playerFrameInfo[0].setDown(playerFrameInfo[3]);
		playerFrameInfo[1].setLeft(playerFrameInfo[0]);
		playerFrameInfo[1].setDown(playerFrameInfo[4]);
		playerFrameInfo[1].setRight(playerFrameInfo[2]);
		playerFrameInfo[2].setLeft(playerFrameInfo[1]);
		playerFrameInfo[2].setDown(playerFrameInfo[5]);
		playerFrameInfo[3].setUp(playerFrameInfo[0]);
		playerFrameInfo[3].setRight(playerFrameInfo[4]);
		playerFrameInfo[3].setDown(playerFrameInfo[6]);
		playerFrameInfo[4].setUp(playerFrameInfo[1]);
		playerFrameInfo[4].setDown(playerFrameInfo[7]);
		playerFrameInfo[4].setRight(playerFrameInfo[5]);
		playerFrameInfo[4].setLeft(playerFrameInfo[3]);
		playerFrameInfo[5].setUp(playerFrameInfo[2]);
		playerFrameInfo[5].setLeft(playerFrameInfo[4]);
		playerFrameInfo[5].setDown(playerFrameInfo[8]);
		playerFrameInfo[6].setUp(playerFrameInfo[3]);
		playerFrameInfo[6].setRight(playerFrameInfo[7]);
		playerFrameInfo[7].setUp(playerFrameInfo[4]);
		playerFrameInfo[7].setRight(playerFrameInfo[8]);
		playerFrameInfo[7].setLeft(playerFrameInfo[6]);
		playerFrameInfo[8].setUp(playerFrameInfo[5]);
		playerFrameInfo[8].setLeft(playerFrameInfo[7]);

		//エネミーエリアのノード設定
		ememyFrameInfo[0].setRight(ememyFrameInfo[1]);
		ememyFrameInfo[0].setDown(ememyFrameInfo[3]);
		ememyFrameInfo[1].setLeft(ememyFrameInfo[0]);
		ememyFrameInfo[1].setDown(ememyFrameInfo[4]);
		ememyFrameInfo[1].setRight(ememyFrameInfo[2]);
		ememyFrameInfo[2].setLeft(ememyFrameInfo[1]);
		ememyFrameInfo[2].setDown(ememyFrameInfo[5]);
		ememyFrameInfo[3].setUp(ememyFrameInfo[0]);
		ememyFrameInfo[3].setRight(ememyFrameInfo[4]);
		ememyFrameInfo[3].setDown(ememyFrameInfo[6]);
		ememyFrameInfo[4].setUp(ememyFrameInfo[1]);
		ememyFrameInfo[4].setDown(ememyFrameInfo[7]);
		ememyFrameInfo[4].setRight(ememyFrameInfo[5]);
		ememyFrameInfo[4].setLeft(ememyFrameInfo[3]);
		ememyFrameInfo[5].setUp(ememyFrameInfo[2]);
		ememyFrameInfo[5].setLeft(ememyFrameInfo[4]);
		ememyFrameInfo[5].setDown(ememyFrameInfo[8]);
		ememyFrameInfo[6].setUp(ememyFrameInfo[3]);
		ememyFrameInfo[6].setRight(ememyFrameInfo[7]);
		ememyFrameInfo[7].setUp(ememyFrameInfo[4]);
		ememyFrameInfo[7].setRight(ememyFrameInfo[8]);
		ememyFrameInfo[7].setLeft(ememyFrameInfo[6]);
		ememyFrameInfo[8].setUp(ememyFrameInfo[5]);
		ememyFrameInfo[8].setLeft(ememyFrameInfo[7]);


		// 描画位置設定
		for (int i = 0; i < 9; i++) {
			playerFrameInfo[i].setDrawX(DrawingPosition.playerArea.centerPoint[i].x);
			playerFrameInfo[i].setDrawY(DrawingPosition.playerArea.centerPoint[i].y);
		}

		for(int j = 0;j < 9;j++){
			ememyFrameInfo[j].setDrawX(DrawingPosition.enemyArea.centerPoint[j].x);
			ememyFrameInfo[j].setDrawY(DrawingPosition.enemyArea.centerPoint[j].y);
		}
	}
}
