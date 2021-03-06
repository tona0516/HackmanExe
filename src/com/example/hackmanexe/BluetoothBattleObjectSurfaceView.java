package com.example.hackmanexe;

import java.util.Timer;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.hackmanexe.action.LongSword;
import com.example.hackmanexe.action.PaladinSword;
import com.example.hackmanexe.action.Sword;
import com.example.hackmanexe.action.WideSword;
import com.example.hackmanexe.fieldobject.FieldItem;
import com.example.hackmanexe.fieldobject.FieldObject;
import com.example.hackmanexe.fieldobject.Opponent;
import com.example.hackmanexe.fieldobject.Player;

/**
 * オブジェクトを描画するクラス 実質のメインクラス
 *
 * @author meem
 *
 */
public class BluetoothBattleObjectSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
	private float width, height;
	private float downX, downY, upX, upY; // タッチ座標,離れた座標
	private final int flickSensitivity = 20;
	private Player player;
	private Opponent opponent;
	private Thread thread;
	private SurfaceHolder holder;
	private Activity activity;
	public static String attackCommand = "null";
	public static String moveCommand = "null";
	private Timer sendTimer;

	public BluetoothBattleObjectSurfaceView(Context context, Activity activity,
			float width, float height) {
		super(context);
		setup(activity, width, height);

		// プレイヤーフィールド中央にプレイヤーオブジェクトの生成
		player = new Player(activity, Field.getInstance().getPanelInfo()[7], 320);
		opponent = new Opponent(activity, Field.getInstance().getPanelInfo()[10], 320);

		// オブジェクトリストに加える(描画時に使用)
		ObjectManager.getInstance().setPlayer(player);
		ObjectManager.getInstance().setOpponent(opponent);
		ObjectManager.getInstance().getObjectList().add(player);
		ObjectManager.getInstance().getObjectList().add(opponent);
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
		if (sendTimer != null) {
			sendTimer.cancel();
			sendTimer = null;
		}
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
				if (Math.abs(downX - upX) - Math.abs(downY - upY) > flickSensitivity) { // 左右の移動量が多い
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
			case MotionEvent.ACTION_MOVE : // ドラッグしている時
				break;
			default :
				break;
		}
		return true;
	}

	private void setup(Activity activity2, float width, float height) {
		this.width = width;
		this.height = height;
		this.activity = activity2;
		// 半透明を設定
		getHolder().setFormat(PixelFormat.TRANSLUCENT);
		// コールバック登録
		getHolder().addCallback(this);
		// フォーカス可
		setFocusable(true);
		// このViewをトップにする
		setZOrderOnTop(true);
	}

	private void onUpFlickOnRightSide() {
		player.addAction(new PaladinSword(activity, player)); // パラディンソード
		player.action();
		attackCommand = "PaladinSword"; // 各攻撃クラスに識別子を属性として持たせたほうがいいかも
	}

	private void onDownFlickOnRightSide() {
		player.addAction(new LongSword(activity, player)); // ロングソード
		player.action();
		attackCommand = "LongSword";
	}

	private void onRightFlickOnRightSide() {
		player.addAction(new WideSword(activity, player)); // ワイドソード
		player.action();
		attackCommand = "WideSword";
	}

	private void onLeftFlickOnRightSide() {
		player.addAction(new Sword(activity, player)); // ソード
		player.action();
		attackCommand = "Sword";
		// MainActivity.drawerLayout.openDrawer(Gravity.RIGHT); // チップ選択画面を表示
	}

	private void onTapOnRightSide() {

	}

	private void onUpFlickOnLeftSide() {
		// player.moveUp();
		player.moveUpSmoothly(250);
		// moveCommand = "moveUp";
		moveCommand = "moveUpSmoothly";
	}

	private void onDownFlickOnLeftSide() {
		// player.moveDown();
		player.moveDownSmoothly(250);
		// moveCommand = "moveDown";
		moveCommand = "moveDownSmoothly";
	}

	private void onRightFlickOnLeftSide() {
		// player.moveRight();
		player.moveRightSmoothly(250);
		// moveCommand = "moveRight";
		moveCommand = "moveRightSmoothly";
	}

	private void onLeftFlickOnLeftSide() {
		// player.moveLeft();
		player.moveLeftSmoothly(250);
		// moveCommand = "moveLeft";
		moveCommand = "moveLeftSmoothly";
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
	private void doDraw(final Paint paint) {
		final Canvas canvas = holder.lockCanvas();
		if (canvas != null) {
			canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR); // 透明色で塗りつぶす
			for (FieldObject o : ObjectManager.getInstance().getObjectList()) {
				if (o != null) { // これやっとかないとnull参照して落ちる
					paint.reset();
					paint.setStyle(Paint.Style.STROKE);
					if (o instanceof FieldItem) {
						float left = DrawingPosition.area.upperLeftPoint[o.getCurrentPanelInfo().getIndex()].x + width / 24;
						float top = DrawingPosition.area.upperLeftPoint[o.getCurrentPanelInfo().getIndex()].y + height / 12;
						float right = DrawingPosition.area.centerPoint[o.getCurrentPanelInfo().getIndex()].x + width / 24;
						float bottom = DrawingPosition.area.centerPoint[o.getCurrentPanelInfo().getIndex()].y + height / 12;
						canvas.drawRect(left, top, right, bottom, paint);
					} else {
						canvas.drawCircle(o.getX(), o.getY(), width / 20, paint);
					}
					paint.setStyle(Paint.Style.FILL);
					paint.setTextSize(100);
					canvas.drawText(String.valueOf(o.getClass().getSimpleName().charAt(0)), o.getX(), o.getY(), paint);
					canvas.drawText("" + o.getHP(), o.getX(), o.getY() + 200, paint);
				}
			}
			holder.unlockCanvasAndPost(canvas);
		}
	}
}