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
				playerArea.pointF[i].y = height / 6;
				enemyArea.pointF[i].y = height / 6;
			} else if (i > 2 && i < 6) {
				playerArea.pointF[i].y = height / 2;
				enemyArea.pointF[i].y = height / 2;
			} else {
				playerArea.pointF[i].y = height * 5 / 6;
				enemyArea.pointF[i].y = height * 5 / 6;
			}
			// x
			if (i % 3 == 0) {
				playerArea.pointF[i].x = width / 12;
				enemyArea.pointF[i].x = width * 7 / 12;
			} else if ((i - 1) % 3 == 0) {
				playerArea.pointF[i].x = width / 4;
				enemyArea.pointF[i].x = width * 3 / 4;
			} else {
				playerArea.pointF[i].x = width * 5 / 12;
				enemyArea.pointF[i].x = width * 11 / 12;
			}
		}
	}

	private Area newArea() {
		return new Area();
	}

	/**
	 *
	 * @author meem
	 * 0・・・左上
	 * 1・・・上中央
	 * 2・・・右上
	 * 3・・・中段左
	 * 4・・・中央
	 * 5・・・中段右
	 * 6・・・左下
	 * 7・・・下中央
	 * 8・・・右下
	 */
	public static class Area {
		public PointF[] pointF;
		public Area() {
			pointF = new PointF[9];
			for (int i = 0; i < 9; i++) {
				pointF[i] = new PointF();
			}
		}
	}
}
