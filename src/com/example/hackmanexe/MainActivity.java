package com.example.hackmanexe;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.FrameLayout;

public class MainActivity extends Activity {
	private FrameLayout frameLayout;
	public static SurfaceView[] t = new SurfaceView[18];

	/**
	 * ここから実行
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Point point = getWindowSize();
		float height = point.y;
		float width = point.x;
		// height,widthから敵・味方エリアの位置座標を計算
		DrawingPosition.prepareDrawing(width, height);
		frameLayout = (FrameLayout) findViewById(R.id.root_layout);
		// 描画を行うViewを加える
		setView(width, height);
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
	 * 各種Viewの設定
	 *
	 * @param width
	 * @param height
	 */
	private void setView(float width, float height) {
		// エリアの区切り線を描画するView
		FieldView fieldView;
		fieldView = new FieldView(this, width, height);
		frameLayout.addView(fieldView);
		// エリア上のオブジェクト(プレイヤー、敵)を描画するView
		ObjectSurfaceView objectSurfaceView;
		objectSurfaceView = new ObjectSurfaceView(this, width, height);
		frameLayout.addView(objectSurfaceView);
		// 攻撃範囲を描画するView
		for (int i = 0; i < 18; i++) {
			t[i] = new SurfaceView(this);
			t[i].setLayoutParams(new LayoutParams((int) width / 6, (int) height / 3));
			t[i].setBackgroundColor(Color.YELLOW);
			t[i].setX(DrawingPosition.area.upperLeftPoint[i].x);
			t[i].setY(DrawingPosition.area.upperLeftPoint[i].y);
			t[i].setVisibility(View.GONE);
			frameLayout.addView(t[i]);
		}
	}
}
