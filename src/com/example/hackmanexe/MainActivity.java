package com.example.hackmanexe;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
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
	private Player player;
	private View drawGrid;
	public static Canvas canvas;

	/**
	 * ここから実行
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//画面サイズの取得
		WindowManager wm = getWindowManager();
		Display disp = wm.getDefaultDisplay();
		Point point = new Point();
		disp.getSize(point);
		height = point.y;
		width = point.x;

		//height,widthから敵・味方エリアのの位置座標を計算
		DrawingPosition.prepareDrawing(width, height);
		//こんな感じで使う
		//味方エリアの左上の座標を取得
		float x =DrawingPosition.playerArea.pointF[0].x;
		//敵エリアの中央の座標を取得
		float y =DrawingPosition.enemyArea.pointF[4].y;

		//画面上部に表示する情報
		textdirection = (TextView) findViewById(R.id.log_direction);
		textShape = (TextView) findViewById(R.id.log_shape);
		textPosition = (TextView) findViewById(R.id.log_position);

		//描画を行うViewを加える
		FrameLayout frameLayout = (FrameLayout) findViewById(R.id.root_layout);
		drawGrid = new DrawGrid(this);
		frameLayout.addView(drawGrid);

		//フィールドオブジェクトの生成
		field = new Field();

		//枠の中央にプレイヤーオブジェクトの生成
		player = new Player(field.getFrameInfo()[4]);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN : //画面に触れた時
				downX = event.getX();
				downY = event.getY();
				break;
			case MotionEvent.ACTION_UP : //画面から離れた時
				upX = event.getX();
				upY = event.getY();
				// 上下左右の判定
				if (Math.abs(downX - upX) - Math.abs(downY - upY) > flickSensitivity) { // 左右の移動量が多い
					if (downX > upX) {
						if (width / 2 > downX) { //タッチ座標が画面左
							logDirection = "left";
							player.moveLeft(); //プレイヤーを左に動かす
						} else {
							logShape = "□";
							player.addAction(new AttackAction(80, 200, "100100100")); //ex)ソードのチップを加える
							player.action();
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
