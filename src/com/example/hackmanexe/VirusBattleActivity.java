package com.example.hackmanexe;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;
import android.widget.FrameLayout;

public class VirusBattleActivity extends Activity {
	private FrameLayout frameLayout;

	/**
	 * ここから実行
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// 画面サイズの取得
		Point point = getWindowSize();

		// height,widthから敵・味方エリアの位置座標を計算
		DrawingPosition.prepareDrawing(point.x, point.y);

		// 描画を行うViewを加える
		frameLayout = (FrameLayout) findViewById(R.id.root_layout);
		setView(frameLayout, point.x, point.y);
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
		FieldView fieldView;
		fieldView = new FieldView(this, width, height);
		frameLayout.addView(fieldView);

		// エリア上のオブジェクト(プレイヤー、敵)を描画するView
		VirusBattleObjectSurfaceView virusBattleObjectSurfaceView;
		virusBattleObjectSurfaceView = new VirusBattleObjectSurfaceView(this, this, width, height);
		frameLayout.addView(virusBattleObjectSurfaceView);

		AttackRangeDrawManager arm = new AttackRangeDrawManager(this, frameLayout, width, height);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Field.getInstance().clear();
		ObjectManager.getInstance().clear();
	}
}
