package com.example.hackmanexe;

import android.graphics.PointF;

/**
 *
 * @author meem 描画位置を定数として管理するクラス
 */
public class DrawingPosition {
	public static float width;
	public static float height;
	public static Area playerArea, enemyArea;

	public static void prepareDrawing(float width, float height) {
		DrawingPosition dp = new DrawingPosition();
		DrawingPosition.width = width;
		DrawingPosition.height = height;
		playerArea = dp.newArea();
		enemyArea = dp.newArea();

		// 以下画面サイズから描画位置の計算
		for (int i = 0; i < 9; i++) {
			// y
			if (i > -1 && i < 3) {
				playerArea.centerPoint[i].y = height / 6;
				enemyArea.centerPoint[i].y = height / 6;
				playerArea.upperLeftPoint[i].y = 0;
				enemyArea.upperLeftPoint[i].y = 0;
			} else if (i > 2 && i < 6) {
				playerArea.centerPoint[i].y = height / 2;
				enemyArea.centerPoint[i].y = height / 2;
				playerArea.upperLeftPoint[i].y = height / 3;
				enemyArea.upperLeftPoint[i].y = height / 3;
			} else {
				playerArea.centerPoint[i].y = height * 5 / 6;
				enemyArea.centerPoint[i].y = height * 5 / 6;
				playerArea.upperLeftPoint[i].y = height * 2 / 3;
				enemyArea.upperLeftPoint[i].y = height * 2 / 3;
			}
			// x
			if (i % 3 == 0) {
				playerArea.centerPoint[i].x = width / 12;
				enemyArea.centerPoint[i].x = width * 7 / 12;
				playerArea.upperLeftPoint[i].x = 0;
				enemyArea.upperLeftPoint[i].x = width / 2;
			} else if ((i - 1) % 3 == 0) {
				playerArea.centerPoint[i].x = width / 4;
				enemyArea.centerPoint[i].x = width * 3 / 4;
				playerArea.upperLeftPoint[i].x = width / 6;
				enemyArea.upperLeftPoint[i].x = width * 2 / 3;
			} else {
				playerArea.centerPoint[i].x = width * 5 / 12;
				enemyArea.centerPoint[i].x = width * 11 / 12;
				playerArea.upperLeftPoint[i].x = width / 3;
				enemyArea.upperLeftPoint[i].x = width * 5 / 6;
			}
		}
	}

	private Area newArea() {
		return new Area();
	}

	/**
	 *
	 * @author meem 0・・・左上 1・・・上中央 2・・・右上 3・・・中段左 4・・・中央 5・・・中段右 6・・・左下 7・・・下中央
	 *         8・・・右下
	 */
	public static class Area {
		public PointF[] centerPoint;
		public PointF[] upperLeftPoint;
		public Area() {
			centerPoint = new PointF[9];
			upperLeftPoint = new PointF[9];
			for (int i = 0; i < 9; i++) {
				centerPoint[i] = new PointF();
				upperLeftPoint[i] = new PointF();
			}
		}
	}
}
