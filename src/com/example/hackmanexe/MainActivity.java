package com.example.hackmanexe;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

	private float downX, downY, upX, upY;
	private TextView textdirection, textShape, textPosition;
	private String logDirection, logShape, logPosition;
	private int height, width;
	private final int flickSensitivity = 20;
	private Field field;
	//private int flag = -1;
	private Player player;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		WindowManager wm = getWindowManager();
		Display disp = wm.getDefaultDisplay();
		Point point = new Point();
		disp.getSize(point);
		height = point.y;
		width = point.x;
		textdirection = (TextView) findViewById(R.id.log_direction);
		textShape = (TextView) findViewById(R.id.log_shape);
		textPosition = (TextView) findViewById(R.id.log_position);
		FrameLayout frameLayout = (FrameLayout) findViewById(R.id.root_layout);
		View drawGrid = new DrawGrid(this);
		frameLayout.addView(drawGrid);
		DrawingPosition.prepareDrawing(width, height);
		field = new Field();
		player = new Player(field.getCurrentFrameInfo());
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN :
				downX = event.getX();
				downY = event.getY();
				break;
			case MotionEvent.ACTION_UP :
				upX = event.getX();
				upY = event.getY();
				// 上下左右の判定
				if (Math.abs(downX - upX) - Math.abs(downY - upY) > flickSensitivity) { // 左右
					if (downX > upX) {
						if (width / 2 > downX) {
							logDirection = "left";
							player.moveLeft();
						} else {
							logShape = "□";
							//flag *= -1;
						}
					} else {
						if (width / 2 > downX) {
							logDirection = "right";
							player.moveRight();
						} else {
							logShape = "○";
						}
					}
				} else if (Math.abs(downY - upY) - Math.abs(downX - upX) > flickSensitivity) { // 上下
					if (downY > upY) {
						if (width / 2 > downX) {
							logDirection = "up";
							player.moveUp();
						} else {
							logShape = "△";
						}
					} else {
						if (width / 2 > downX) {
							logDirection = "down";
							player.moveDown();
						} else {
							logShape = "×";
						}
					}
				} else {
					if (width / 2 > downX)
						logDirection = "leftTap";
					else
						logShape = "rightTap";
				}
				if (width / 2 > downX) {
					textdirection.setText(logDirection);
					textShape.setText("");
				} else {
					textShape.setText(logShape);
					textdirection.setText("");
				}
				Log.d("position", player.getCurrentFrameInfoToString());
				textPosition.setText(player.getCurrentFrameInfoToString());
				break;
		}
		return true;
	}

	private class DrawGrid extends View {
		private Paint paint;
		public DrawGrid(Context context) {
			super(context);
			paint = new Paint();
		}
		@Override
		protected void onDraw(Canvas canvas) {
			super.onDraw(canvas);
			paint.setStrokeWidth(10);

			paint.setColor(Color.RED);
			//横線
			canvas.drawLine(0, height / 3, width / 2 - 5, height / 3, paint);
			canvas.drawLine(0, height * 2 / 3, width / 2 - 5, height * 2 / 3, paint);
			//縦線
			canvas.drawLine(width / 6, 0, width / 6, height, paint);
			canvas.drawLine(width * 2 / 6, 0, width * 2 / 6, height, paint);
			canvas.drawLine(width / 2 - 5, 0, width / 2 - 5, height, paint);

			paint.setColor(Color.BLUE);
			//横線
			canvas.drawLine(width / 2 + 5, height / 3, width, height / 3, paint);
			canvas.drawLine(width / 2 + 5, height * 2 / 3, width, height * 2 / 3, paint);
			//縦線
			canvas.drawLine(width / 2 + 5, 0, width / 2 + 5, height, paint);
			canvas.drawLine(width * 4 / 6, 0, width * 4 / 6, height, paint);
			canvas.drawLine(width * 5 / 6, 0, width * 5 / 6, height, paint);

			paint.reset();

//			if (flag == 1) {
//				paint.setColor(Color.RED);
//				paint.setStrokeWidth(20);
//				canvas.drawLine(width / 2, 0, width / 2, height, paint);
//				canvas.drawLine(width * 2 / 3, 0, width * 2 / 3, height, paint);
//				canvas.drawLine(width / 2, 0, width * 2 / 3, 0, paint);
//				canvas.drawLine(width / 2, height, width * 2 / 3, height, paint);
//				paint.reset();
//			}

			// 自機位置描画
			paint.setStyle(Paint.Style.STROKE);
			paint.setStrokeWidth(10);
			canvas.drawCircle(player.getCurrentFrameInfo().getDrawX(), player.getCurrentFrameInfo().getDrawY(), 100, paint);

			// 敵描画
			paint.setStyle(Paint.Style.FILL);
			canvas.drawCircle(width * 9 / 12, height / 2, 100, paint);

			// 再描画
			invalidate();
		}
	}
}
