package com.example.hackmanexe;

import java.util.Timer;
import java.util.TimerTask;

import android.R;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.SeekBar;

public class MainActivity extends Activity {
	public static SurfaceView[] t = new SurfaceView[18]; // 攻撃範囲を描画するViewをパネル数作成
	public static SurfaceView[] t2 = new SurfaceView[18]; // 攻撃範囲を描画するViewをパネル数作成
	public static SeekBar costomGaugeSeekBar;

	/**
	 * ここから実行
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		DrawerLayout dl = (DrawerLayout) findViewById(R.id.drawer_layout);
//		ActionBarDrawerToggle abdt = new ActionBarDrawerToggle(this, dl, R.string.drawer_open, R.string.drawer_close) {
//			@Override
//			public void onDrawerClosed(View view) {
//			}
//
//			@Override
//			public void onDrawerOpened(View drawerView) {
//			}
//		};
		// 画面サイズの取得
		Point point = getWindowSize();

		// height,widthから敵・味方エリアの位置座標を計算
		DrawingPosition.prepareDrawing(point.x, point.y);

		// 描画を行うViewを加える
		setView((FrameLayout) findViewById(R.id.root_layout), point.x, point.y);
	}

	/**
	 *
	 * @return 画面の縦横サイズ
	 */
	private Point getWindowSize() {
		Point p = new Point();
		WindowManager wm = getWindowManager();
		Display disp = wm.getDefaultDisplay();
		disp.getSize(p);
		return p;
	}

	/**
	 * 各種Viewの追加
	 *
	 * @param frameLayout
	 * @param width
	 *            画面横サイズ
	 * @param height
	 *            画面縦サイズ
	 */
	private void setView(FrameLayout frameLayout, float width, float height) {
		// エリアの区切り線を描画するView
		FieldView fieldView = new FieldView(this, width, height);
		frameLayout.addView(fieldView);

		// エリア上のオブジェクト(プレイヤー、敵)を描画するView
		ObjectSurfaceView objectSurfaceView = new ObjectSurfaceView(this, this, width, height);
		frameLayout.addView(objectSurfaceView);

		// カスタムゲージを描画するSeekBar
		// costomGaugeSeekBar = (SeekBar)findViewById(R.id.costom_gauge);
		costomGaugeSeekBar = new SeekBar(this);
		FrameLayout.LayoutParams layoutParamsCostomGauge = new FrameLayout.LayoutParams((int) width / 2, (int) height / 15, Gravity.CENTER_HORIZONTAL);
		costomGaugeSeekBar.setLayoutParams(layoutParamsCostomGauge);
		costomGaugeSeekBar.setMax(1000);
		costomGaugeSeekBar.setClickable(false);
		costomGaugeSeekBar.setThumb(null);
		frameLayout.addView(costomGaugeSeekBar);
		Timer costomGaugetimer = new Timer();
		costomGaugetimer.scheduleAtFixedRate(new TimerTask() {
			private int progressValue = 0;
			@Override
			public void run() {
				if (progressValue < 1000) {
					costomGaugeSeekBar.setProgress(progressValue);
					progressValue = progressValue + 1;
				} else if (progressValue == 1000) {
					// ゲージが溜まった時の処理
				}
			}
		}, 0, 10);

		// 攻撃範囲を描画するView
		for (int i = 0; i < 18; i++) {
			t[i] = new SurfaceView(this);
			t[i].setLayoutParams(new LayoutParams((int) width / 6, (int) height / 3)); // パネルの大きさにする
			t[i].setBackgroundColor(Color.YELLOW); // 黄色で描画
			t[i].setAlpha(0.5f); // 半透明に
			// 描画位置を設定
			t[i].setX(DrawingPosition.area.upperLeftPoint[i].x);
			t[i].setY(DrawingPosition.area.upperLeftPoint[i].y);
			t[i].setVisibility(View.GONE); // 普段は非表示に
			frameLayout.addView(t[i]);
		}

		for (int i = 0; i < 18; i++) {
			t2[i] = new SurfaceView(this);
			t2[i].setLayoutParams(new LayoutParams((int) width / 6, (int) height / 3)); // パネルの大きさにする
			t2[i].setBackgroundColor(Color.CYAN); // 黄色で描画
			t2[i].setAlpha(0.5f); // 半透明に
			// 描画位置を設定
			t2[i].setX(DrawingPosition.area.upperLeftPoint[i].x);
			t2[i].setY(DrawingPosition.area.upperLeftPoint[i].y);
			t2[i].setVisibility(View.GONE); // 普段は非表示に
			frameLayout.addView(t2[i]);
		}
	}
}
