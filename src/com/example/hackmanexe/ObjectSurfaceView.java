package com.example.hackmanexe;

import java.util.ArrayList;

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

/**
 * オブジェクトを描画するクラス
 * 実質のメインクラス
 * @author meem
 *
 */
class ObjectSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
	private float width, height;
	private float downX, downY, upX, upY; //タッチ座標,離れた座標
	private String logDirection, logShape;
	private final int flickSensitivity = 20;
    private Player player;
	private Thread thread;
	private SurfaceHolder holder;
	public static Field field;
	private ArrayList<FieldObject> objectList;

	public ObjectSurfaceView(Context context, float width, float height) {
		super(context);
		this.width = width;
		this.height = height;
		// 半透明を設定
		getHolder().setFormat(PixelFormat.TRANSLUCENT);
		// コールバック登録
		getHolder().addCallback(this);
		// フォーカス可
		setFocusable(true);
		// このViewをトップにする
		setZOrderOnTop(true);

		// フィールドオブジェクトの生成
		field = new Field();
		// 中央にプレイヤーオブジェクトの生成
		player = new Player(field.getPlayerFrameInfo()[4],320);
		// 中央にエネミーオブジェクトの生成
		Metall metall = new Metall(field.getEmemyFrameInfo()[5],player);

		//オブジェクトリストに加える(描画時に使用)
		objectList = new ArrayList<FieldObject>();
		objectList.add(player);
		objectList.add(metall);
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

	/**
	 * ユーザのタッチ動作の検知
	 */
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
							player.addAction(new AbsolutePositionAttack(10, 1000, "111001111")); // ex)ブーメラン
							player.action(holder);
						}
					} else {
						if (width / 2 > downX) {
							logDirection = "right";
							player.moveRight();
						} else {
							logShape = "○";
							player.addAction(new AbsolutePositionAttack(10, 1000, "001001001")); // ex)バンブーランス
							player.action(holder);
						}
					}
				} else if (Math.abs(downY - upY) - Math.abs(downX - upX) > flickSensitivity) { // 上下(ry
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
				Log.d(this.toString(), player.getCurrentFrameInfoToString());
				break;
		}
		return true;
	}

	/**
	 * 別のスレッドで描画
	 */
	@Override
	public void run() {
		Paint paint  = new Paint();
		while (thread != null) {
			doDraw(paint);
		}
	}

	/**
	 * オブジェクトの描画
	 * @param paint
	 */
	private void doDraw(Paint paint) {
		Canvas canvas = holder.lockCanvas();
		if (canvas != null) {
			canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR); // 透明色で塗りつぶす
			for(FieldObject o : objectList){
				paint.reset();
				paint.setStrokeWidth(10);
				if(o instanceof Player){
					paint.setStyle(Paint.Style.STROKE);
				}else if(o instanceof Enemy){
					paint.setStyle(Paint.Style.FILL);
				}
				canvas.drawCircle(o.getCurrentFrameInfo().getDrawX(), o.getCurrentFrameInfo().getDrawY(), 100, paint);
				paint.setTextSize(100);
				canvas.drawText("" + o.getHP(), o.getCurrentFrameInfo().getDrawX(), o.getCurrentFrameInfo().getDrawY() + 200, paint);
			}
			holder.unlockCanvasAndPost(canvas);
		}
	}
}