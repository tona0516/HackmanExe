package com.example.hackmanexe;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * パネルの区切り線を描画するクラス
 * エリアスチール作るときに改良やばい
 * @author meem
 *
 */
public class FieldView extends View{

	private Paint paint;
	public FieldView(Context context,float width,float height) {
		super(context);
		paint = new Paint();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		paint.setStrokeWidth(10);

		paint.setColor(Color.RED);
		// 横線
		canvas.drawLine(0, DrawingPosition.height / 3, DrawingPosition.width / 2 - 5, DrawingPosition.height / 3, paint);
		canvas.drawLine(0, DrawingPosition.height * 2 / 3, DrawingPosition.width / 2 - 5, DrawingPosition.height * 2 / 3, paint);
		// 縦線
		canvas.drawLine(DrawingPosition.width / 6, 0, DrawingPosition.width / 6, DrawingPosition.height, paint);
		canvas.drawLine(DrawingPosition.width * 2 / 6, 0, DrawingPosition.width * 2 / 6, DrawingPosition.height, paint);
		canvas.drawLine(DrawingPosition.width / 2 - 5, 0, DrawingPosition.width / 2 - 5, DrawingPosition.height, paint);

		paint.setColor(Color.BLUE);
		// 横線
		canvas.drawLine(DrawingPosition.width / 2 + 5, DrawingPosition.height / 3, DrawingPosition.width, DrawingPosition.height / 3, paint);
		canvas.drawLine(DrawingPosition.width / 2 + 5, DrawingPosition.height * 2 / 3, DrawingPosition.width, DrawingPosition.height * 2 / 3, paint);
		// 縦線
		canvas.drawLine(DrawingPosition.width / 2 + 5, 0, DrawingPosition.width / 2 + 5, DrawingPosition.height, paint);
		canvas.drawLine(DrawingPosition.width * 4 / 6, 0, DrawingPosition.width * 4 / 6, DrawingPosition.height, paint);
		canvas.drawLine(DrawingPosition.width * 5 / 6, 0, DrawingPosition.width * 5 / 6, DrawingPosition.height, paint);
	}
}
