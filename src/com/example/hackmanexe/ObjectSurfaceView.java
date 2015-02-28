package com.example.hackmanexe;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.hackmanexe.action.AbsolutePositionAttack;
import com.example.hackmanexe.action.RelativePositionAttack;
import com.example.hackmanexe.fieldobject.Cannodam;
import com.example.hackmanexe.fieldobject.Enemy;
import com.example.hackmanexe.fieldobject.FieldObject;
import com.example.hackmanexe.fieldobject.Player;

/**
 * オブジェクトを描画するクラス 実質のメインクラス
 *
 * @author meem
 *
 */
public class ObjectSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
	private float width, height;
	private float downX, downY, upX, upY; // タッチ座標,離れた座標
	private final int flickSensitivity = 20;
	private Player player;
	private Thread thread;
	private SurfaceHolder holder;
	public static Field field;
	public static ArrayList<FieldObject> objectList;
	private MainActivity mainActivity;

	public ObjectSurfaceView(Context context, MainActivity mainActivity,
			float width, float height) {
		super(context);
		this.width = width;
		this.height = height;
		this.mainActivity = mainActivity;
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
		player = new Player(mainActivity, field.getPanelInfo()[7], 320);
		// 中央にエネミーオブジェクトの生成
		//Metall metall = new Metall(mainActivity, field.getPanelInfo()[11], player);
		Cannodam cannodam = new Cannodam(mainActivity, field.getPanelInfo()[11], 40);
		//Rabbily rabbily = new Rabbily(mainActivity, field.getPanelInfo()[10], player);

		// オブジェクトリストに加える(描画時に使用)
		objectList = new ArrayList<FieldObject>();
		objectList.add(player);
		//objectList.add(metall);
		objectList.add(cannodam);
		//objectList.add(rabbily);
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
				downX = event.getX();
				downY = event.getY();
				break;
			case MotionEvent.ACTION_UP : // 画面から離れた時
				upX = event.getX();
				upY = event.getY();
				// 上下左右の判定
				if (Math.abs(downX - upX) - Math.abs(downY - upY) > flickSensitivity ) { // 左右の移動量が多い
					if (downX > upX) {
						if (width / 2 > downX) { // タッチ座標が画面左
							onLeftFlickOnLeftSide();
						} else {
							onLeftFlickOnRightSide();
						}
					} else {
						if (width / 2 > downX) {
							onRightFlickOnLeftSide();
						} else {
							onRightFlickOnRightSide();
						}
					}
				} else if (Math.abs(downY - upY) - Math.abs(downX - upX) > flickSensitivity) { // 上下(ry
					if (downY > upY) {
						if (width / 2 > downX) {
							onUpFlickOnLeftSide();
						} else {
							onUpFlickOnRightSide();
						}
					} else {
						if (width / 2 > downX) {
							onDownFlickOnLeftSide();
						} else {
							onDownFlickOnRightSide();
						}
					}
				} else {
					if (width / 2 > downX)
						onTapOnLeftSide();
					else
						onTapOnRightSide();
				}
				break;
			default :
				break;
		}
		return true;
	}

	private void onUpFlickOnRightSide() {

	}

	private void onDownFlickOnRightSide() {
		player.addAction(new RelativePositionAttack(mainActivity, 10, 500, "re", player)); // ex)ショックウェーブ
		player.action();
	}

	private void onRightFlickOnRightSide() {
		player.addAction(new AbsolutePositionAttack(mainActivity, 10, 0, "5,11,17", player)); // ex)バンブーランス
		player.action();
	}

	private void onLeftFlickOnRightSide() {
		player.addAction(new AbsolutePositionAttack(mainActivity, 10, 100, "12,13,14,15,16,17,11,5,4,3,2,1,0", player));
		player.action();
	}

	private void onTapOnRightSide() {

	}

	private void onUpFlickOnLeftSide() {
		player.moveUp();
	}

	private void onDownFlickOnLeftSide() {
		player.moveDown();
	}

	private void onRightFlickOnLeftSide() {
		player.moveRight();
	}

	private void onLeftFlickOnLeftSide() {
		player.moveLeft();
	}

	private void onTapOnLeftSide() {

	}

	/**
	 * 別のスレッドで描画
	 */
	@Override
	public void run() {
		Paint paint = new Paint();
		while (thread != null) {
			doDraw(paint);
		}
	}

	/**
	 * オブジェクトの描画
	 *
	 * @param paint
	 */
	private void doDraw(Paint paint) {
		Canvas canvas = holder.lockCanvas();
		if (canvas != null) {
			canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR); // 透明色で塗りつぶす
			for (FieldObject o : objectList) {
				if (o != null) { // これやっとかないとnull参照して落ちる
					paint.reset();
					if (o instanceof Player) {
						paint.setStyle(Paint.Style.STROKE);
						paint.setStrokeWidth(10);
					} else if (o instanceof Enemy) {
						paint.setStyle(Paint.Style.FILL);
					}
					canvas.drawCircle(o.getCurrentPanelInfo().getDrawX(), o.getCurrentPanelInfo().getDrawY(), 100, paint);
					paint.reset();
					paint.setTextSize(100);
					canvas.drawText("" + o.getHP(), o.getCurrentPanelInfo().getDrawX(), o.getCurrentPanelInfo().getDrawY() + 200, paint);
				}
			}
			holder.unlockCanvasAndPost(canvas);
		}
	}
}