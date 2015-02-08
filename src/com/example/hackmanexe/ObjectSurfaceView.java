package com.example.hackmanexe;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

class ObjectSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
	private Paint paint;
	private float width, height;
	private float downX, downY, upX, upY;
	// private TextView textdirection, textShape, textPosition;
	private String logDirection, logShape, logPosition;
	private final int flickSensitivity = 20;
	private Field field;
	public static Player player;
	public static Enemy metall;
	private Thread thread;
	private SurfaceHolder holder;
	public ObjectSurfaceView(Context context, float width, float height) {
		super(context);
		paint = new Paint();
		// 半透明を設定
		getHolder().setFormat(PixelFormat.TRANSLUCENT);
		// コールバック登録
		getHolder().addCallback(this);
		// フォーカス可
		setFocusable(true);
		// このViewをトップにする
		setZOrderOnTop(true);
		this.width = width;
		this.height = height;
		Log.d(this.toString(), width + "," + height);

		// textdirection = (TextView) findViewById(R.id.log_direction);
		// textShape = (TextView) findViewById(R.id.log_shape);
		// textPosition = (TextView) findViewById(R.id.log_position);
		// フィールドオブジェクトの生成
		field = new Field();
		// 中央にプレイヤーオブジェクトの生成
		player = new Player(field.getPlayerFrameInfo()[4]);
		// 中央にエネミーオブジェクトの生成
		metall = new Enemy(field.getEmemyFrameInfo()[4]);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		thread = new Thread(this);
		thread.start();
		this.holder = holder;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		thread = null;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN : // 画面に触れた時
				Log.d(this.toString(), event.getX() + "," + event.getY());
				downX = event.getX();
				downY = event.getY();
				break;
			case MotionEvent.ACTION_UP : // 画面から離れた時
				Log.d(this.toString(), event.getX() + "," + event.getY());
				upX = event.getX();
				upY = event.getY();
				// 上下左右の判定
				if (Math.abs(downX - upX) - Math.abs(downY - upY) > flickSensitivity) { // 左右の移動量が多い
					if (downX > upX) {
						if (width / 2 > downX) { // タッチ座標が画面左
							logDirection = "left";
							player.moveLeft(); // プレイヤーを左に動かす
						} else {
							logShape = "□";
							player.addAction(new AttackAction(10, -1, "111001111")); // ex)ブーメラン
							player.action(holder);
						}
					} else {
						if (width / 2 > downX) {
							logDirection = "right";
							player.moveRight();
						} else {
							logShape = "○";
							player.addAction(new AttackAction(10, -1, "001001001")); // ex)バンブーランス
							player.action(holder);
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
					Log.d(this.toString(), "" + logDirection);
				} else {
					Log.d(this.toString(), "" + logShape);
				}
				break;
		}
		return true;
	}

	@Override
	public void run() {
		while (thread != null) {
			doDraw();
		}
	}

	private void doDraw() {
		Canvas canvas = holder.lockCanvas();
		if (canvas != null) {
			canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR); // 透明色で塗りつぶす
			// 自機描画
			paint.setStyle(Paint.Style.STROKE);
			paint.setStrokeWidth(10);
			canvas.drawCircle(player.getCurrentFrameInfo().getDrawX(), player.getCurrentFrameInfo().getDrawY(), 100, paint);
			paint.reset();
			// 自機HP描画
			paint.setTextSize(100);
			canvas.drawText("" + player.getHP(), player.getCurrentFrameInfo().getDrawX(), player.getCurrentFrameInfo().getDrawY() + 200, paint);

			// 敵描画
			paint.setStyle(Paint.Style.FILL);
			paint.setStrokeWidth(10);
			canvas.drawCircle(metall.getCurrentFrameInfo().getDrawX(), metall.getCurrentFrameInfo().getDrawY(), 100, paint);
			paint.reset();
			// 敵HP描画
			paint.setTextSize(100);
			canvas.drawText("" + metall.getHP(), metall.getCurrentFrameInfo().getDrawX(), metall.getCurrentFrameInfo().getDrawY() + 200, paint);
			holder.unlockCanvasAndPost(canvas);
		}
	}
}