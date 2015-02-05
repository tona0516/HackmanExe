package com.example.hackmanexe;
/**
 *
 * @author meem
 * バトルフィールドの情報を保持するクラス
 */
class Field {
	private FrameInfo[] frameInfo = new FrameInfo[9];
	private FrameInfo currentFrameInfo;

	public Field() {
		// FrameInfoオブジェクトの初期化
		for (int i = 0; i < 9; i++) {
			frameInfo[i] = new FrameInfo(i);
		}
		// 移動方向の割り当て
		makeNode();
		currentFrameInfo = frameInfo[4];
	}

	public FrameInfo getCurrentFrameInfo() {
		return currentFrameInfo;
	}

	public void setCurrentFrameInfo(FrameInfo currentFrameInfo) {
		this.currentFrameInfo = currentFrameInfo;
	}

	private void makeNode() {
		frameInfo[0].setRight(frameInfo[1]);
		frameInfo[0].setDown(frameInfo[3]);
		frameInfo[1].setLeft(frameInfo[0]);
		frameInfo[1].setDown(frameInfo[4]);
		frameInfo[1].setRight(frameInfo[2]);
		frameInfo[2].setLeft(frameInfo[1]);
		frameInfo[2].setDown(frameInfo[5]);
		frameInfo[3].setUp(frameInfo[0]);
		frameInfo[3].setRight(frameInfo[4]);
		frameInfo[3].setDown(frameInfo[6]);
		frameInfo[4].setUp(frameInfo[1]);
		frameInfo[4].setDown(frameInfo[7]);
		frameInfo[4].setRight(frameInfo[5]);
		frameInfo[4].setLeft(frameInfo[3]);
		frameInfo[5].setUp(frameInfo[2]);
		frameInfo[5].setLeft(frameInfo[4]);
		frameInfo[5].setDown(frameInfo[8]);
		frameInfo[6].setUp(frameInfo[3]);
		frameInfo[6].setRight(frameInfo[7]);
		frameInfo[7].setUp(frameInfo[4]);
		frameInfo[7].setRight(frameInfo[8]);
		frameInfo[7].setLeft(frameInfo[6]);
		frameInfo[8].setUp(frameInfo[5]);
		frameInfo[8].setLeft(frameInfo[7]);

		// 描画位置設定
		for (int i = 0; i < 9; i++) {
			frameInfo[i].setDrawX(DrawingPosition.playerArea.pointF[i].x);
			frameInfo[i].setDrawY(DrawingPosition.playerArea.pointF[i].y);
		}
	}
}
