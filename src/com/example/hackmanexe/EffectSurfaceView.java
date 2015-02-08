package com.example.hackmanexe;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class EffectSurfaceView extends SurfaceView implements SurfaceHolder.Callback,Runnable{

	private long startTime;
	private SurfaceHolder holder;
	private Thread thread;

	public EffectSurfaceView(Context context) {
		super(context);
		// 半透明を設定
		getHolder().setFormat(PixelFormat.TRANSLUCENT);
		// コールバック登録
		getHolder().addCallback(this);
		// フォーカス可
		setFocusable(true);
		// このViewをトップにする
		setZOrderOnTop(true);

		}

	@Override
	public void run() {
		Canvas canvas = holder.lockCanvas();
		Paint paint = new Paint();
		paint.setTextSize(100);
		Log.d(this.toString(), "run");
		canvas.drawText("hit", DrawingPosition.enemyArea.centerPoint[4].x, DrawingPosition.enemyArea.centerPoint[4].y, paint);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		holder.unlockCanvasAndPost(canvas);
		thread = null;
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO 自動生成されたメソッド・スタブ
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		// TODO 自動生成されたメソッド・スタブ
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO 自動生成されたメソッド・スタブ
	}

	public void start(){
		thread = new Thread();
		thread.start();
		startTime = System.currentTimeMillis();
	}
}
