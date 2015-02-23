package com.example.hackmanexe;

import android.graphics.PointF;

/**
 * 描画位置を定数として管理するクラス
 *
 * @author meem
 */
class DrawingPosition {
	public static float width;
	public static float height;
	public static Area area;

	public static void prepareDrawing(float width, float height) {
		DrawingPosition dp = new DrawingPosition();
		DrawingPosition.width = width;
		DrawingPosition.height = height;
		area = dp.newArea();

		// 以下画面サイズから描画位置の計算
		for (int i = 0; i < 18; i++) {
			// y
			if (i > -1 && i < 6) {
				area.centerPoint[i].y = height / 6;
				area.upperLeftPoint[i].y = 0;
			} else if (i > 5 && i < 12) {
				area.centerPoint[i].y = height / 2;
				area.upperLeftPoint[i].y = height / 3;
			} else {
				area.centerPoint[i].y = height * 5 / 6;
				area.upperLeftPoint[i].y = height * 2 / 3;
			}
			if (i % 6 == 0) {
				area.centerPoint[i].x = width / 12;
				area.upperLeftPoint[i].x = 0;
			} else if ((i - 1) % 6 == 0) {
				area.centerPoint[i].x = width / 4;
				area.upperLeftPoint[i].x = width / 6;
			} else if ((i - 2) % 6 == 0) {
				area.centerPoint[i].x = width * 5 / 12;
				area.upperLeftPoint[i].x = width / 3;
			} else if ((i - 3) % 6 == 0) {
				area.centerPoint[i].x = width * 7 / 12;
				area.upperLeftPoint[i].x = width / 2;
			} else if ((i - 4) % 6 == 0) {
				area.centerPoint[i].x = width * 3 / 4;
				area.upperLeftPoint[i].x = width * 2 / 3;
			} else if ((i - 5) % 6 == 0) {
				area.centerPoint[i].x = width * 11 / 12;
				area.upperLeftPoint[i].x = width * 5 / 6;
			}
		}
	}

	private Area newArea() {
		return new Area();
	}

	/**
	 *
	 * @author meem
	 */
	public static class Area {
		public PointF[] centerPoint;
		public PointF[] upperLeftPoint;
		public Area() {
			centerPoint = new PointF[18];
			upperLeftPoint = new PointF[18];
			for (int i = 0; i < 18; i++) {
				centerPoint[i] = new PointF();
				upperLeftPoint[i] = new PointF();
			}
		}
	}
}
